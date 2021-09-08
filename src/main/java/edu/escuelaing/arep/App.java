package edu.escuelaing.arep;
import com.google.gson.Gson;

import edu.escuelaing.arep.http.HttpStockServiceImp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App {
	

	/**
	 * 
	 * @param args son los argumentos que entra en el main
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HttpStockServiceImp service = new HttpStockServiceImp();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(getPort());
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}
		Socket clientSocket = null;
		try {
			System.out.println("Listo para recibir ...");
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String inputLine, outputLine;
		
		String method ="";
		String path ="";
		String version="";
		List<String> headers = new ArrayList<String>();
		while ((inputLine = in.readLine()) != null) {
			System.out.println("Received: " + inputLine);
			if(method.isEmpty()) {
				String[] requestString = inputLine.split(" ");
				method = requestString[0];
				path = requestString[1];
				version = requestString[2];
				System.out.println("Request: "+method+ " " + path + " " + version);
			}
			if (!in.ready()) {
				break;
			}
		}

		String json  = service.getWeather("LONDON");
	
		outputLine = "HTTP/1.1 200 OK\r\n" 
				+"Content-Type: text/html\r\n" 
				+ "\r\n" 
				+ "<!DOCTYPE html>" 
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<title>Title of the document</title>\n"
				+ "</head>"
				+ "<body>"
				+ json
				+ "</body>" 
				+ "</html>";
		
		
		System.out.println(outputLine);

		out.println(outputLine);

		out.close();

		in.close();

		clientSocket.close();

		serverSocket.close();
		
		
	}
	
	public static int getPort() {
		 if (System.getenv("PORT") != null) {
			 return Integer.parseInt(System.getenv("PORT"));
			 }
			 return 4567;
	}
    
    
   
}
