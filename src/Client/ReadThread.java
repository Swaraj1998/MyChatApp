package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
	private Socket socket;
	private BufferedReader inServer;
	
	public ReadThread(Socket socket) throws IOException {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {	
			inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(!socket.isClosed()) {
				if(inServer.ready()) {
					String message = inServer.readLine();
					if(message != null) {
						System.out.println(message);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
