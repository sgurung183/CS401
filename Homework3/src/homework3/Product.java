package homework3;


public class Product {
	private static int num = 0;
	private String id;
	private String name;
	private Supplier supplier;
	
	public Product() {
		this.id = "" + num++;
	}
	public Product(String name, Supplier supplier) {
		this.id = "" + num++;
		this.name = name;
		this.supplier = supplier;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	
}
