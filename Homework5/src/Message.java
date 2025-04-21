import java.io.Serializable;

public class Message implements Serializable {
    protected MessageType type;
    protected MessageStatus status;
    protected String text;
    
    //to initialize
    //mostly testing purpose for others working on the program
    public Message(){
        this.type = null;
        this.status = null;
        this.text = "Undefined";
    }

    public Message(MessageType type, MessageStatus status, String text){
        this.type = type;
        this.status = status;
        this.text = text;
    }


    public MessageType getType(){
    	return this.type;
 
    }

    public MessageStatus getStatus(){
    	return this.status;
    }

    public String getText(){
    	return this.text;
    }

}
