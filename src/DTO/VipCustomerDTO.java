package DTO;

public class VipCustomerDTO {
	private int id;
	private CustomerDTO idCustomer;
	
	
	@Override
	public String toString() {
		return "VipCustomerDTO [id=" + id + ", idCustomer=" + idCustomer.getId() + "]";
	}
	public VipCustomerDTO() {
		super();
	}
	public VipCustomerDTO(CustomerDTO idCustomer) {
		super();
		this.idCustomer = idCustomer;
	}
	public VipCustomerDTO(int id, CustomerDTO idCustomer) {
		super();
		this.id = id;
		this.idCustomer = idCustomer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CustomerDTO getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(CustomerDTO idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	
}
