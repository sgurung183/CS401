package homework3;

import java.security.PublicKey;

public class Phone {
	private String phoneNumber;
	
	public Phone(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	public void setContactInfo(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getContactInfo() {
		return this.phoneNumber;
	}
}
