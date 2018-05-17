package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {
	private static Socket socket;
	private static ServerSocket server;
	private static ClientThread client;
	private static ArrayList<ClientThread> clients;
	
	public ChatServer() {
		clients = new ArrayList<ClientThread>();
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Specify only the port number as an argument.");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		try {
			server = new ServerSocket(port);
			new ChatServer().acceptClients();
		} catch(IOException e) {
			System.err.println("Cannot listen to port " + port);
			System.exit(1);
		}	
	}
	
	private void acceptClients() {
		System.out.println("\nWaiting for clients to connect...");
		while(true) {
			try {
				socket = server.accept();
				System.out.println("\nClient Accepted with IP: " + socket.getInetAddress());
				
				client = new ClientThread(socket, this);
				Thread thread = new Thread(client);
				clients.add(client);
				thread.start();
			} catch(IOException e) {
				System.out.println("Cannot Accept Client!");
			}
		}
	}
	
	public void broadcast(String message, ClientThread thisClient) throws IOException {
		for(ClientThread client: clients) {
			if(client != thisClient) {
				client.sendMessage(message);
			}
		}
	}
}
