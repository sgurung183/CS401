package homework3;

import java.util.Date;

public class Order {
	private static int num = 0;
	private String number;
	private Date ordered; //when the order was created
	private Date shipped; //when the order was shipped
	private Address ship_to; //where to ship
	private OrderStatus status;
	private LineItem[] lineItems;//array of line items
	private double total = 0.0;//the total price of the order
	
	//
	private Payment payment;
	
	//create constructor
	public Order() {
		System.out.println("***New Order Created***");
		this.number = "" + Order.num++;//each order is assigned a unique id
		this.status = OrderStatus.New;//order status is first new when an order is first created
		this.lineItems = new LineItem[100];//for this assignment assume customers do not order too many items
		ordered = new Date();//new order
		this.payment = new Payment();
	}
	public Order(LineItem[] lineItems) {
		System.out.println("***New Order Created***");
		this.number = "" + Order.num++;//each order is assigned a unique id
		this.status = OrderStatus.New;//order status is first new when an order is first created
		this.lineItems = lineItems;//linItems we get from the shopping cart
		ordered = new Date();//new order
		this.payment = new Payment();
	}
	public String getOrderId() {
		return this.number;
	}
	public void addToOrder(Product newProduct) {
		//orders being sorted, 
		boolean found = false;
		for(int i = 0; i < lineItems.length; i++) {
			//product has a line
			if(lineItems[i].getProduct().getName().equalsIgnoreCase(newProduct.getName())) {
				lineItems[i].incrementQuantity();
				found = true;
			}
		}
		//line does not exist so need to add in the relevant position 
		if(!found) {
			//helper function to count number of lines
			int numOfLines = countLines();
			//check if line items is empty
			if(numOfLines == 0) {
				lineItems[0] = new LineItem(newProduct);
			}
			//at least one line
			else {
				boolean added = false;
				for(int i = 0; i < numOfLines; ++i) {
					//the new product line should be above the old one
					if((newProduct.getName().compareTo(lineItems[i].getProduct().getName()) < 0)){
						//we will drag other lines by one 
						for(int j = numOfLines; j > i; j--) {
							lineItems[j] = lineItems[j-1];
						}
						//place the new line in the relevant space
						lineItems[i] = new LineItem(newProduct);
						added = true;
						break;
					}
				}
				//if the no product name lexically larger than it was found we will insert at the bottom
				if(!added) {
					lineItems[numOfLines] = new LineItem(newProduct);
				}
			}
		}
		
	}
	public void calcOrderTotal() {
		for(int i = 0; i < lineItems.length; i++) {
			//this.total += lineItems[i].getPrice().getPrice();//get the price of the line
		}
	}
	public void showOrderSummary() {
		System.out.println("Order ID: " + this.number + ", Date: " + this.ordered.toString() + ", Status:" + this.status + ", Order Total: " + this.getotal());
	}
	//get the order summary of a specific order
	public String getOrderDetails() {
		String summary = "";
		for(int i = 0; i < countLines(); i++) {
			summary = lineItems[i].toString() + "\n";
		}
		return summary;
	}
	//get the total of the order
	public double getotal() {
		double total = 0.0;
		for(int i =0; i < countLines(); i++) {
			LineItem currLine = lineItems[i];
			double currLinetotal = currLine.getPrice().getPrice();
			total = total + currLinetotal;
		}
		this.total = total;
		return total;
	}
	
	//helper
	private int countLines() {
		int count = 0;
		while(lineItems[count] != null && count < lineItems.length) {
			count++;
		}
		return count;
	}
	
}
