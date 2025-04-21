import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.ArrayList;

public class Server {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket server = null;
		int count = 0;
		
		try {
			//create a server socket
			server = new ServerSocket(1234);
			server.getReuseAddress();
			
			System.out.println("Server Running");
			//will always be open to requests
			
			while(true) {
				Socket client = server.accept();
				
				System.out.println(client.getLocalAddress() + " Connected. " + (count + 1));
				count++;
				ClientHandler newClient = new ClientHandler(client);
				
				new Thread(newClient).start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

