package kaeon_ace_modules.ascent.utilities.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server extends Thread {

	private ServerSocket server;
	private String data;

	public Server(int port) {

		try {
			server = new ServerSocket(port);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

		try {
			server.setSoTimeout(10000);
		}
		
		catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run()
	{

		while(true)
		{

			try
			{

				Socket socket = server.accept();

				if(data != null) {

					DataOutputStream out =
							new DataOutputStream(socket.getOutputStream());

					out.writeUTF(data);
					data = null;
				}

				socket.close();
			}

			catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}

			catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}

	public void write(String data) {
		this.data = data;
	}
}