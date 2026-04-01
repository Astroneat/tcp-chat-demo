import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public interface MessageListener {
        void onMessageReceived(String message);
    }
    
    public ChatClient(String host, int port, String username, MessageListener listener) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println(username);

        new Thread(() -> {
            try {
                String msg;
                while((msg = reader.readLine()) != null) {
                    listener.onMessageReceived(msg);
                }
            } 
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}

