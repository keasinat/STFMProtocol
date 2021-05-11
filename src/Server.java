

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.ServerHandler;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while(true) {
				// 2. Wait for connection from clients
				System.out.println("Waiting for client...");
				Socket connection = serverSocket.accept();
                
                Thread thread = new ServerHandler(connection);
                thread.start();
			}
        } catch (IOException e) {
            System.out.println("Error Connection:" + e.getMessage());
        }

    }
}

