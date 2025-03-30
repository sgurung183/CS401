package homework3;

public class Test {
	public static void main(String[] argStrings) {
		
		//creating a customer called "c"
		Customer c = new Customer(new Address("3123 Mindblown Ave, HELP, CA-911"),new Phone("510-234-0989"), "help@theumlisverybad.man" );
		c.printCustomerInfo();
			
		
		//creating a product with a supplier
		Product pen = new Product("Pen", new Supplier("Alibaba"));
		Product apple = new Product("Apple", new Supplier("Temu"));
		Product phone = new Product("Phone", new Supplier("Amazon"));
		Product nacho = new Product("Nachos Doritos", new Supplier("Frito Lays"));
		
		//to link price with the product i create a class
		ProductPrice p1info = new ProductPrice(pen, new Price(0.99));
		ProductPrice p2info = new ProductPrice(apple, new Price(1.99));
		ProductPrice p3info = new ProductPrice(phone, new Price(200));
		ProductPrice p4info = new ProductPrice(nacho, new Price(2.69));
		
		//a manager class that stores your product and their corresponding price
		ProductPriceManager ppManager = new ProductPriceManager(p1info);
		ppManager.newInfo(p2info);
		ppManager.newInfo(p3info);
		ppManager.newInfo(p4info);
		
		//add items to the order
		
		//*********First Order****************
		c.addItemToCart(pen);
		c.addItemToCart(pen);
		c.addItemToCart(pen);
		c.addItemToCart(pen);
		c.addItemToCart(phone);
		c.addItemToCart(phone);
		c.addItemToCart(apple);
		c.addItemToCart(apple);
		c.viewCart();
		c.placeTheOrder();
		c.showOrderHistory();
		
		System.out.println("*********************************************");
		//**********new order******************
		
		c.addItemToCart(phone);
		c.addItemToCart(apple);
		c.viewCart();
		c.placeTheOrder();
		c.showOrderHistory();
		//-----------------------------------------------------------------------------------------------------//
		System.out.println("");
		System.out.println("***********Testing for new customer**************************");
		//--Another customer "sam" ---------------
		Customer sam = new Customer(new Address("3203 Ave, EL Camino, CA-911"),new Phone("510-234-0129"), "sam@isbroke.edu" );
		
		//******sam adds item to the cart**********************
		sam.addItemToCart(nacho);
		sam.addItemToCart(pen);
		sam.addItemToCart(nacho);
		sam.addItemToCart(apple);
		sam.addItemToCart(pen);
		sam.viewCart();
		sam.placeTheOrder();
		sam.showOrderHistory();
		System.out.println();
		System.out.println("*********************************************");
		//**********new order******************
		
		sam.addItemToCart(nacho);
		sam.addItemToCart(apple);
		sam.viewCart();
		sam.placeTheOrder();
		sam.showOrderHistory();
		
		//------------Testing out the webuser------------------------//
		System.out.println();
		System.out.println("**********Testing out the Web User*************************8");
		WebUser webUser = new WebUser();
		webUser.addToCart(nacho);
		webUser.addToCart(nacho);
		webUser.addToCart(nacho);
		webUser.addToCart(nacho);
		webUser.addToCart(apple);
		webUser.showCart();
		webUser.placeOrder();
		webUser.viewOrderHistory();
	}
}
