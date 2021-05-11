import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import client.STFMPRequest;
import client.STFMPResponse;
public class client {
    public static void main(String[] args) throws Exception {
    	try {
    		// 1. Connect to server
            Socket connection = new Socket("localhost", 9999);
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            
            Scanner scanner = new Scanner(connection.getInputStream());
            
            // 2. Show options to user
            System.out.println("Welcome to STFMP Protocol.");
            
            // 3. Read input from user
            InputStream keyboardInputStream = System.in;
            Scanner keyboardScanner = new Scanner(keyboardInputStream);
            while (true) {
                System.out.println("\nEnter:");
                System.out.println(" > R to write the file");
                System.out.println(" > V to view the file");
                System.out.println(" > C to Exit.");
                System.out.print("Enter here: ");
                String userInput = keyboardScanner.nextLine();
                if(connection.isClosed()) {
                    keyboardScanner.close();
                	break;
                }

                /* !process input */
                if (userInput.toUpperCase().equals("R")) {
                    System.out.print("Please enter file's name: ");
                    String fileName = keyboardScanner.nextLine();
                    System.out.print("Please write contents: ");
                    String fileContents = keyboardScanner.nextLine();
                    // Read the file from server.
                    STFMPRequest stfmpRequest = new STFMPRequest("write", fileName, fileContents);
                    String request = stfmpRequest.getRequest();
                    sendRequestToServer(printWriter, request);
                    readServerResponse(scanner);
                }else if(userInput.toUpperCase().equals("V")){
                    System.out.print("Please enter file's name: ");
                    String fileName = keyboardScanner.nextLine();
                    STFMPRequest stfmpRequest = new STFMPRequest("view", fileName, "");
                    String request = stfmpRequest.getRequest();
                    sendRequestToServer(printWriter, request);
                    readServerResponse(scanner);
                }else if(userInput.toUpperCase().equals("C")){
                    STFMPRequest stfmpRequest = new STFMPRequest("close", "", "");
                    String request = stfmpRequest.getRequest();
                    sendRequestToServer(printWriter, request);
                    connection.close();
                    keyboardScanner.close();
                    break;
                }else{
                    System.out.println("Wrong Command");
                }
            }
		} catch (SocketException e) {
			System.out.println("error: " + e.getMessage());
		}
        
    }

    private static void sendRequestToServer(PrintWriter writer, String request) {
		writer.write(request);
		writer.write("\r\n");
		writer.flush();
	}

    private static void readServerResponse(Scanner scanner) {
        String response = scanner.nextLine();
        STFMPResponse stfmpResponse = new STFMPResponse(response);
        System.out.println("Status: "+ stfmpResponse.getStatus());
        System.out.println("> "+ stfmpResponse.getContents());
    }
}
