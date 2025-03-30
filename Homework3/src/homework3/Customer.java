package homework3;

public class Customer {
	private static int count = 0;
	private String id; 
	private Address address;
	private Phone phone;
	private String email;
	private Account account;
	private WebUser web;
	
	public Customer() {
		this.id = "A" + count++;
		this.account = new Account(this.id);
	}
	public Customer(Address address, Phone phone, String email) {
		this.id = "A" + count++;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.account = new Account(this.id, this.address);
	}
	public void printCustomerInfo() {
		//only for this assignment
		System.out.println("ID: " + this.id + ", Email: " + this.email + ", Address: " + this.address.getAddress());
	}
	public String getId() {return this.id;}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public WebUser getWeb() {
		return web;
	}
	public void setWeb(WebUser web) {
		this.web = web;
	}
	public void addItemToCart(Product product) {
		account.addToCart(product);
	}
	public void placeTheOrder() {
		account.placeOrder();
	}
	public void viewCart() {
		account.showCart();
	}
	public void showOrderHistory() {
		account.viewOrderHistory();
	}
}
