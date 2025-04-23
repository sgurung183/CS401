import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			MessageType messageType = null;
			String textString = "";
			Scanner scanner = new Scanner(System.in);
			
			boolean quit = false;
			
			Socket socket = new Socket("localhost", 1234);
		
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
			
			while(!quit) {
				
						
				System.out.print("What action do you want to take? (TEXT/LOGIN/LOGOUT): ");
				String typeString = scanner.nextLine();
				
				
				switch (typeString.toUpperCase()) {
				case "TEXT":
					messageType = MessageType.TEXT;
					System.out.print("Enter the message content: ");
					textString = scanner.nextLine();
					System.out.println();
					break;
				case "LOGIN":
					messageType = MessageType.LOGIN;
					textString = "Trying to log in";
					break;
				case "LOGOUT":
					messageType = MessageType.LOGOUT;
					textString ="Logging out";
					quit = true;
					break;
				default:
					messageType = null;
					break;
				}
				
				
				
				Message message = new Message(messageType, MessageStatus.DELIVERED, textString);
				
				
				
				objectOutput.writeObject(message);
				
				
			    try {
					Message response = (Message) objectInput.readObject();
					System.out.println("The server responded, " + response.getText());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.println();
			}
			
			//close the streams, sockets and the scanner
			objectInput.close();
			objectOutput.close();
			socket.close();
			scanner.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	
	}
}
