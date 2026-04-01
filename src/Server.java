import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter port number to start the server: ");
            int port = scanner.nextInt();
            startServer(port);
        }
    }

    private static void startServer(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while(true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                client.start();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void broadcast(String message) {
        for(ClientHandler client: clients) {
            client.sendMessage(message);
        }
    }
}