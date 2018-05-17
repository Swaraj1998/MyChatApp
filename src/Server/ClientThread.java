package Server;

import java.net.*;
import java.io.*;

public class ClientThread implements Runnable {
	private Socket socket;
	private String name;
	private BufferedReader inClient;
	private ChatServer server;
	private PrintWriter out;
	
	public ClientThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			inClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			socket.setSoTimeout(30000);
			
			name = inClient.readLine();
			server.broadcast(name + " joined!", this);
			
			while(!socket.isClosed()) {
				if(true) {
					String input = inClient.readLine();
					if(input != null) {
						server.broadcast(input, this);
					}
				}
			}
			
		} catch(SocketTimeoutException e) {
			try {
				server.broadcast(name + " left.", this);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) throws IOException {
		out.println(message);
	}
}
