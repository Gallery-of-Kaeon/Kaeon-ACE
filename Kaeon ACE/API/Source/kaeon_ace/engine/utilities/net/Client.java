package kaeon_ace.engine.utilities.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	private InetAddress server;
	private int port;
	
	public Client(int port) {
		this.port = port;
	}
	
	public void connectTo(String serverName) {
        
        server = null;
		
		try {
			server = InetAddress.getByName(serverName);
		}
		
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void write(String data) {
		
		if(server != null) {
			
			try {
				
				Socket socket = new Socket(server.getHostAddress(), port);
            	
                PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
                
                out.println(data);
				
				socket.close();
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String read() {
		
		if(server == null)
			return null;
		
		Socket socket;
		String data = null;
		
		try {
			
			socket = new Socket(server.getHostAddress(), port);
			
	        BufferedReader input =
	            new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        data = input.readLine();
	        
	        socket.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
}