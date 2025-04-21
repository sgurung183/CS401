import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	private static ArrayList<Socket> loggedInSockets = new ArrayList<Socket>();
	private Socket clientSocket = null;
	
	public ClientHandler(Socket client) {
		this.clientSocket = client;
	}
	public void run() {
		Boolean isLoggedIn = false;
		
		try {
			InputStream inputStream = clientSocket.getInputStream();
			ObjectInputStream objectRecieved = new ObjectInputStream(inputStream);
			
			try {
				
				Message recievedMessage = (Message) objectRecieved.readObject();
				
				
				for(Socket socket : loggedInSockets) {
					if(socket.getInetAddress().equals(clientSocket.getInetAddress())) {
						isLoggedIn = true;
					}
				}
				
				//check if the client is logged in or not
				if(isLoggedIn) {
					System.out.println("USER IS LOGGED IN ");
					//if user wants to log out
					if(recievedMessage.getType() == MessageType.LOGOUT) {
						for(Socket socket : loggedInSockets) {
							int index = 0;
							if(socket.getInetAddress().equals(clientSocket.getInetAddress())) {
								loggedInSockets.remove(index);
								break;
							}
							index++;
						}
						System.out.println("Successfully Logged out");
						Message returnMessage = new Message(MessageType.TEXT, null, "SUCCESSFULLY LOGGED OUT");
						System.out.println(loggedInSockets.size());
						OutputStream outputStream = clientSocket.getOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(returnMessage);
					}
					else {
						//if logged in, send the capitalized version of the message back to the client
						OutputStream outputStream = clientSocket.getOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(recievedMessage);
					}
					
					
				}
				else {
					//check the message type to see if it is a login message
					if(recievedMessage.getType() == MessageType.LOGIN) {
						loggedInSockets.add(clientSocket);
						System.out.println("New Client Added to the list");
						System.out.println(loggedInSockets.size());
						Message returnMessage = new Message(MessageType.TEXT, null, "SUCCESSFULLY LOGGED IN");
						OutputStream outputStream = clientSocket.getOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(returnMessage);
					}
					else {
						//not logged in and not a login type message so do nothing, let them know
						System.out.println("not logged in bro");
						Message returnMessage = new Message(MessageType.TEXT, null, "LOGIN FIRST TO SEND MESSAGES");
						OutputStream outputStream = clientSocket.getOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(returnMessage);
					}
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
