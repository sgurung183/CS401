package homework3;

public class LineItem {
	private Product product;
	private int quantity = 0;;
	private Price price;
	
	public LineItem(Product newProduct) {
		this.product = newProduct;
		this.quantity = 1;
		ProductPriceManager priceManager = new ProductPriceManager();
		this.price = new Price(priceManager.getPrice(newProduct));
	}
	public void incrementQuantity() {
		quantity = quantity + 1;
		calcPrice();//price is calculated every time an item is incremented
	}
	public Product getProduct() {
		return this.product;
	}
	public Price getPrice() {
		return this.price;
	}
	public void calcPrice() {
		ProductPriceManager priceManager = new ProductPriceManager();
		double unitPriceOfItem = priceManager.getPrice(product);
		double total = (this.quantity) * unitPriceOfItem;
		this.price = new Price(total);//price of the LineItem
	}
	@Override
	//get the line item info as a string
	//item name --- quantity ---- total
	public String toString() {
		return "[product=" + product.getName() + ", quantity=" + quantity + ", Total price= $" + price.getPrice() + "]";
	}
	
}
