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
		try(Socket socket = new Socket("localhost", 1234)) {
			MessageType messageType = null;
			String textStrigString = "";
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Enter the message type (TEXT/LOGIN/LOGOUT): ");
			String typeString = scanner.nextLine();
			System.out.println();
			
			System.out.print("Enter the message content: ");
			textStrigString = scanner.nextLine();
			System.out.println();
			
			switch (typeString.toUpperCase()) {
			case "TEXT":
				messageType = MessageType.TEXT;
				break;
			case "LOGIN":
				messageType = MessageType.LOGIN;
				break;
			case "LOGOUT":
				messageType = MessageType.LOGOUT;
				break;
			default:
				messageType = null;
				break;
			}
			
			
			
			Message message = new Message(messageType, MessageStatus.DELIVERED, textStrigString);
			
			System.out.println("Socket connected");
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
			objectOutput.writeObject(message);
			
			ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
		    try {
				Message response = (Message) objectInput.readObject();
				System.out.println("The server responded, " + response.getText());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
}
