package homework3;

import java.util.Date;

public class ShoppingCart {
	private Date created;
	private LineItem[] lineItems;
	public ShoppingCart() {
		// TODO Auto-generated constructor stub
		created = new Date();
		lineItems = new LineItem[100]; //for this assignment only
	}
	//will get you the the lines that you got in the cart
	public LineItem[] getLineItems() {
		return lineItems;
	}
	
	//add item to the cart
	public void addToCart(Product newProduct) {
		//line items will be sorted
		//empty cart
		if(lineItems[0] == null) {
			lineItems[0] = new LineItem(newProduct);
		}
		//the cart is not empty
		else {
			boolean found = false;
			for(int i = 0; i < countLines(); i++) {
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
	}
	//print out the cart that you got
	public void viewCart() {
		for(int i = 0; i < countLines(); i++) {
			System.out.println(lineItems[i].toString());
		}
		System.out.println();
	}
	//count number of line items in the order
	private int countLines() {
		int count = 0;
		while(lineItems[count] != null) {
			count++;
		}
		return count;
	}
}
