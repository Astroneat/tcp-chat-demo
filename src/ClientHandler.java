import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            this.username = reader.readLine();
            System.out.println("Client " + username + " connected");
            Server.broadcast(username + " has joined the room");

            String message;
            while((message = reader.readLine()) != null) {
                String formattedMsg = "[%s] %s".formatted(username, message);
                Server.broadcast(formattedMsg);
                System.out.println(formattedMsg);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            Server.removeClient(this);
            Server.broadcast(username + " has left the room");
            socket.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}