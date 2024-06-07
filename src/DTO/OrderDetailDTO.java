package DTO;

public class OrderDetailDTO {
	private int id;
	private OrdersDTO idOrder;
	private ProductDTO idProduct;
	private int quantity;
	
	
	@Override
	public String toString() {
		return "OrderDetailDTO [id=" + id + ", idOrder=" + idOrder.getId() + ", idProduct=" + idProduct.getId() + ", quantity="
				+ quantity + "]";
	}
	public OrderDetailDTO(OrdersDTO idOrder, ProductDTO idProduct, int quantity) {
		super();
		this.idOrder = idOrder;
		this.idProduct = idProduct;
		this.quantity = quantity;
	}
	public OrderDetailDTO() {
		super();
	}
	public OrderDetailDTO(int id, OrdersDTO idOrder, ProductDTO idProduct, int quantity) {
		super();
		this.id = id;
		this.idOrder = idOrder;
		this.idProduct = idProduct;
		this.quantity = quantity;
	}
	public OrderDetailDTO( ProductDTO idProduct, int quantity) {
		this.idProduct = idProduct;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrdersDTO getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(OrdersDTO idOrder) {
		this.idOrder = idOrder;
	}
	public ProductDTO getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(ProductDTO idProduct) {
		this.idProduct = idProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
