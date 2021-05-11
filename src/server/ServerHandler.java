package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler extends Thread {
    private Socket connection;

    public ServerHandler(Socket connection) {
        this.connection = connection;
    }
    @Override
    public void run() {
        // 3. Read data from client
        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
        	while (true) {
                String data = scanner.nextLine();
                if (!processRequest(connection, data)) {
                    break;
                }
            }
            
            scanner.close();
        } catch (IOException e) {
            System.out.println("connection error: "+ e.getMessage());
        }
        
        
    }

    private boolean processRequest(Socket connection, String request) throws IOException {
        String[] parts = request.split("##");
		if(parts.length < 2 || parts.length > 3 ) {
			sendResponse(connection, "STFMP/1.0##invalid##Invalid request");
			return true;
		}
        String action = parts[1];
   
        if (action.equals("write")) {
            String[] params = parts[2].split("#");
            String fileName = params[0];
            if (params.length != 2) {
                sendResponse(connection, "STFMP/1.0##invalid##Invalid request");
                return true;
            }else{
                String fileContents = params[1];
                if (FileHandler.Write(fileName, fileContents)) {
                    sendResponse(connection, "STFMP/1.0##ok##The file has been write");
                }else{
                    sendResponse(connection, "STFMP/1.0##invalid##Server error: this file cannot be saved.");
                }
            }
        }else if(action.equals("view")){
            String[] params = parts[2].split("#");
            String fileName = params[0];
            String content = FileHandler.Read(fileName);
            String[] conPart = content.split(",");
            sendResponse(connection, "STFMP/1.0##"+ conPart[0] +"##" + conPart[1]);
        }else if(action.equals("close")){
            connection.close();
			return false;
        }else{
            sendResponse(connection, "STFMP/1.0##invalid##Invalid request");
            return true;
        }
        return true;
    }

    private void sendResponse(Socket connection, String response) throws IOException {
		PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
		printWriter.write(response + "\r\n");
		printWriter.flush();
	}
}

