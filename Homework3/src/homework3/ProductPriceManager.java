package homework3;

public class ProductPriceManager {
	private static int size = 0;//index where we have space
	//static to make only one instance of this attribut
	//array accessible always
	private static ProductPrice[] productPriceInfo = new ProductPrice[100]; //for this homework only
	
	public ProductPriceManager() {
		
	}
	public void newInfo(ProductPrice info) {
		productPriceInfo[size] = info;
		size++;
	}
	public ProductPriceManager(ProductPrice info) {
		// TODO Auto-generated constructor stub
		productPriceInfo[size] = info;
		size++;
	}
	public double getPrice(Product product) {
		for(int i = 0; i < size; ++i) {
			ProductPrice currInfo = productPriceInfo[i];
			if(currInfo.getProduct().getId().equalsIgnoreCase(product.getId())) {
				return currInfo.getPrice().getPrice();
			}
		}
		return -1;//meaning not found
	}
}
