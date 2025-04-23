import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	
	private static class ClientHandler implements Runnable{
		private static ArrayList<Socket> loggedInSockets = new ArrayList<Socket>();
		private Socket clientSocket = null;
		
		public ClientHandler(Socket client) {
			this.clientSocket = client;
		}
		public void run() {
			Boolean isLoggedIn = false;
						
				try {
					ObjectInputStream objectReceived = new ObjectInputStream(clientSocket.getInputStream());
	                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	                
	                while(true) {
	                	Message recievedMessage = (Message) objectReceived.readObject();
						
						
						for(Socket socket : loggedInSockets) {
							if(socket.getInetAddress().equals(clientSocket.getInetAddress())) {
								isLoggedIn = true;
							}
						}
						
						//check if the client is logged in or not
						//if logged in then
						if(isLoggedIn) {
							
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
								System.out.println("SUCCESSFULLY LOGGED OUT");
								Message returnMessage = new Message(MessageType.TEXT, null, "SUCCESSFULLY LOGGED OUT");
								System.out.println(loggedInSockets.size());
								objectOutputStream.writeObject(returnMessage);
								break;
							}
							//if logged in and wants to send messages
							else {
								//if logged in, send the capitalized version of the message back to the client
								System.out.println("Message sent by client: " + recievedMessage.getText());
								String capMsgString = recievedMessage.getText().toUpperCase();
								System.out.println("Message being returned to the client: " + capMsgString);
								//changing the status of the message to delivered
								Message sendingMessage = new Message(MessageType.TEXT, MessageStatus.DELIVERED, capMsgString);
								objectOutputStream.writeObject(sendingMessage);
							}
							
							
						}
						//user is not logged in
						else {
							//check the message type to see if it is a login message
							if(recievedMessage.getType().equals(MessageType.LOGIN)) {
								loggedInSockets.add(clientSocket);
								System.out.println("New Client Added to the list");
								System.out.println(loggedInSockets.size());
								Message returnMessage = new Message(MessageType.TEXT, null, "SUCCESSFULLY LOGGED IN");
								objectOutputStream.writeObject(returnMessage);
							}
							else {
								//not logged in and not a login type message so do nothing, let them know
								System.out.println("not logged in bro");
								Message returnMessage = new Message(MessageType.TEXT, null, "LOGIN FIRST TO SEND MESSAGES");
								objectOutputStream.writeObject(returnMessage);
							}
						}
						
	                }
					
					
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

