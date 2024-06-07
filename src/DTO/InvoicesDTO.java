package DTO;

import java.time.LocalDateTime;

public class InvoicesDTO {
	private int id;
	private OrdersDTO idOrders;
	private CustomerDTO idCustomer;
	private EmployeeDTO idEmployee;
	private LocalDateTime invoiceTime; 
	private float calculateMoney;
	private int quantity;
	private boolean paymentStatus;
	private boolean paymentMethod;
	
	
	@Override
	public String toString() {
		return "InvoicesDTO [id=" + id + ", idOrders=" + idOrders.getId() + ", idCustomer=" + idCustomer.getId() + ", idEmployee="
				+ idEmployee.getId() + ", invoiceTime=" + invoiceTime + ", calculateMoney=" + calculateMoney + ", quantity="
				+ quantity + ", paymentStatus=" + paymentStatus + ", paymentMethod=" + paymentMethod + "]";
	}
	public InvoicesDTO() {
		super();
	}
	
	public InvoicesDTO(OrdersDTO idOrders, LocalDateTime invoiceTime) {
		super();
		this.idOrders = idOrders;
		this.invoiceTime = invoiceTime;
	}
	public InvoicesDTO(OrdersDTO idOrders, EmployeeDTO idEmployee, LocalDateTime invoiceTime, boolean paymentStatus,
			boolean paymentMethod) {
		super();
		this.idOrders = idOrders;
		this.idEmployee = idEmployee;
		this.invoiceTime = invoiceTime;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
	}
	public InvoicesDTO(OrdersDTO idOrders, CustomerDTO idCustomer, EmployeeDTO idEmployee, LocalDateTime invoiceTime,
			float calculateMoney, int quantity, boolean paymentStatus, boolean paymentMethod) {
		super();
		this.idOrders = idOrders;
		this.idCustomer = idCustomer;
		this.idEmployee = idEmployee;
		this.invoiceTime = invoiceTime;
		this.calculateMoney = calculateMoney;
		this.quantity = quantity;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
	}
	public InvoicesDTO(int id, OrdersDTO idOrders, CustomerDTO idCustomer, EmployeeDTO idEmployee,
			LocalDateTime invoiceTime, float calculateMoney, int quantity, boolean paymentStatus,
			boolean paymentMethod) {
		super();
		this.id = id;
		this.idOrders = idOrders;
		this.idCustomer = idCustomer;
		this.idEmployee = idEmployee;
		this.invoiceTime = invoiceTime;
		this.calculateMoney = calculateMoney;
		this.quantity = quantity;
		this.paymentStatus = paymentStatus;
		this.paymentMethod = paymentMethod;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrdersDTO getIdOrders() {
		return idOrders;
	}
	public void setIdOrders(OrdersDTO idOrders) {
		this.idOrders = idOrders;
	}
	public CustomerDTO getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(CustomerDTO idCustomer) {
		this.idCustomer = idCustomer;
	}
	public EmployeeDTO getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(EmployeeDTO idEmployee) {
		this.idEmployee = idEmployee;
	}
	public LocalDateTime getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(LocalDateTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public float getCalculateMoney() {
		return calculateMoney;
	}
	public void setCalculateMoney(float calculateMoney) {
		this.calculateMoney = calculateMoney;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public boolean isPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(boolean paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}