package homework3;
import java.net.PortUnreachableException;
import java.util.Date;


public class Account {
	private String id;
	private Address billing_address = null;
	private boolean is_closed = true;
	private Date open = null;
	private Date closed = null;
	private ShoppingCart cart;
	private Order[] orders;
	
	public Account(String id) {
		this.id = id;
		this.open = new Date();
		this.is_closed = false;
		this.cart = new ShoppingCart();
		this.orders = new Order[100];
	}
	public Account(String id, Address billing_Address) {
		this.id = id;
		this.billing_address = billing_Address;
		this.open = new Date();
		this.is_closed = false;
		this.cart = new ShoppingCart();
		this.orders = new Order[100]; //assuming no more than 100 orders per account
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Address getBilling_address() {
		return billing_address;
	}
	public void setBilling_address(Address billing_address) {
		this.billing_address = billing_address;
	}
	public boolean isIs_closed() {
		return is_closed;
	}
	public void setIs_closed(boolean is_closed) {
		this.is_closed = is_closed;
	}
	public Date getOpen() {
		return open;
	}
	public Date getClosed() {
		return closed;
	}
	public void setClosed(Date closed) {
		this.closed = closed;
	}
	public ShoppingCart getCart() {
		return cart;
	}
	public Order[] getOrders() {
		return orders;
	}
	public void setOrders(Order[] orders) {
		this.orders = orders;
	}
	public void addToCart(Product product) {
		cart.addToCart(product);
		System.out.println(product.getName() + " added to the cart by customer with id (" + this.id + ")" );
	}
	public void placeOrder() {
		
		LineItem[] lineItems = cart.getLineItems(); //items you have in the cart
		//each order is unique so while placing an order you only have to find the relevant postiion
		//create an order of the items in the cart now
		Order newOrder = new Order(lineItems);
		//as orders are sorted in the order when they were placed, nono need to be sorted as only added to the array when order 
		//is placed
		orders[countOrders()] = newOrder;
		//now the cart is empty after order placed
		cart = new ShoppingCart();
		System.out.println("***Order (" + newOrder.getOrderId() + ") Successfully placed by user: " + this.getId() +" ***");
		
	}
	public void viewOrderHistory() {
		System.out.println("Order History for: " + this.id);
		for(int i = 0; i < countOrders(); ++i) {
			orders[i].showOrderSummary();
		}
	}
	public void showCart() {
		System.out.println();
		System.out.println("Shopping cart for ID: " + this.getId());
		cart.viewCart();
	}
	//helper function
	public int countOrders() {
		int count = 0;
		while(orders[count] != null && count < orders.length) {
			count++;
		}
		return count;
	}
}
