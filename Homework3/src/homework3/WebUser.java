package homework3;

public class WebUser {
	private static int num = 0;
	private Account webAccount;
	private String id;
	private String password;
	private UserState state;
	private ShoppingCart cart;
	
	//customer creates a web account
	
	public WebUser() {
		this.id = "WU" + num++;
		//default password
		this.password = "ABCD";
		this.state = UserState.Active;
		this.cart = new ShoppingCart();
		this.webAccount = new Account(id);
		
	}
	public WebUser(Customer customer) {
		this.id = "" + num++;
		//this.webAccount = customer.get
		//default password
		this.password = "ABCD";
		this.state = UserState.Active;
		this.cart = new ShoppingCart();
	}
	
	//----Imported form accounts----
	public void addToCart(Product product) {
		cart.addToCart(product);
		System.out.println(product.getName() + " added to the cart by customer with id (" + this.id + ")" );
	}
	public void placeOrder() {
		Order[] orders = webAccount.getOrders();
		LineItem[] lineItems = cart.getLineItems(); //items you have in the cart
		//each order is unique so while placing an order you only have to find the relevant postiion
		//create an order of the items in the cart now
		Order newOrder = new Order(lineItems);
		//as orders are sorted in the order when they were placed, nono need to be sorted as only added to the array when order 
		//is placed
		orders[countOrders()] = newOrder;
		//now the cart is empty after order placed
		cart = new ShoppingCart();
		System.out.println("***Order (" + newOrder.getOrderId() + ") Successfully placed by user: " + this.id +" ***");
		
	}
	public void viewOrderHistory() {
		Order[] orders = webAccount.getOrders();
		System.out.println("Order History for: " + this.id);
		for(int i = 0; i < countOrders(); ++i) {
			orders[i].showOrderSummary();
		}
	}
	public void showCart() {
		System.out.println("Shopping cart for ID: " + this.id);
		cart.viewCart();
	}
	//helper function
	public int countOrders() {
		Order[] orders = webAccount.getOrders();
		int count = 0;
		while(orders[count] != null && count < orders.length) {
			count++;
		}
		return count;
	}
}
