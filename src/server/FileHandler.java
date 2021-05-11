package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
    public static boolean Write(String fileName, String fileContents) {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("./assets/" + fileName);
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(fileContents);
            writer.flush();
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;    
        }

    }

    public static String Read(String fileName) {
        try {
			InputStream inputStream = new FileInputStream("./assets/" + fileName);
			Scanner scanner = new Scanner(inputStream);
			String content = scanner.nextLine();
			scanner.close();
			return "ok," + content;
		} catch (FileNotFoundException e) {
			String output = fileName+" is not fond.";
			return "not_found," + output;
		}
    }
}

