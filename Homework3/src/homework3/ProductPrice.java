package homework3;

public class ProductPrice {
	
	private Product product;
	private Price price;
	
	public ProductPrice(Product product, Price price) {
		this.product = product;
		this.price = price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	
}
