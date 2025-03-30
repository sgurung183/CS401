package homework3;

import java.util.Date;

public class Payment {
	private static int num = 0;
	private String id;
	private Date paid;
	private double total;
	private String details;
	
	public Payment() {
		// TODO Auto-generated constructor stub
		this.id = "PYM" + num;
	}
	public Payment(String details) {
		this.details = details;
	}
	public String getId() {
		return id;
	}
	public Date getPaid() {
		return paid;
	}
	public void setPaid() {
		this.paid = new Date();
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
