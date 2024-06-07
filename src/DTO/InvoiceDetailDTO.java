package DTO;

public class InvoiceDetailDTO {
	private int id;
	private InvoicesDTO idInvoice;
	private String name;
	private int quantity;
	private String unit;
	private float calculateMoney;
	
	
	@Override
	public String toString() {
		return "InvoiceDetailDTO [id=" + id + ", idInvoice=" + idInvoice.getId() + ", name=" + name + ", quantity=" + quantity
				+ ", unit=" + unit + ", calculateMoney=" + calculateMoney + "]";
	}
	public InvoiceDetailDTO() {
		super();
	}
	public InvoiceDetailDTO(InvoicesDTO idInvoice, String name, int quantity, String unit, float calculateMoney) {
		super();
		this.idInvoice = idInvoice;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.calculateMoney = calculateMoney;
	}
	public InvoiceDetailDTO(int id, InvoicesDTO idInvoice, String name, int quantity, String unit,
			float calculateMoney) {
		super();
		this.id = id;
		this.idInvoice = idInvoice;
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
		this.calculateMoney = calculateMoney;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public InvoicesDTO getIdInvoice() {
		return idInvoice;
	}
	public void setIdInvoice(InvoicesDTO idInvoice) {
		this.idInvoice = idInvoice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getCalculateMoney() {
		return calculateMoney;
	}
	public void setCalculateMoney(float calculateMoney) {
		this.calculateMoney = calculateMoney;
	}
	
}
