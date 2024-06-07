package DTO;

import java.time.LocalDateTime;

public class OrdersDTO {
	private int id;
	private EmployeeDTO idEmployee;
	private CustomerDTO idCustomer;
	private TablesDTO idTable;
	private LocalDateTime orderTime;
	
	@Override
	public String toString() {
		return "OrdersDTO [id=" + id + ", idEmployee=" + idEmployee.getId() + ", idCustomer=" + idCustomer.getId() + ", idTable="
				+ idTable.getId() + ", orderTime=" + orderTime + "]";
	}

	public OrdersDTO(int id, EmployeeDTO idEmployee, CustomerDTO idCustomer, TablesDTO idTable,
			LocalDateTime orderTime) {
		super();
		this.id = id;
		this.idEmployee = idEmployee;
		this.idCustomer = idCustomer;
		this.idTable = idTable;
		this.orderTime = orderTime;
	}

	public OrdersDTO(EmployeeDTO idEmployee, CustomerDTO idCustomer, TablesDTO idTable, LocalDateTime orderTime) {
		super();
		this.idEmployee = idEmployee;
		this.idCustomer = idCustomer;
		this.idTable = idTable;
		this.orderTime = orderTime;
	}

	public OrdersDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmployeeDTO getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(EmployeeDTO idEmployee) {
		this.idEmployee = idEmployee;
	}

	public CustomerDTO getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(CustomerDTO idCustomer) {
		this.idCustomer = idCustomer;
	}

	public TablesDTO getIdTable() {
		return idTable;
	}

	public void setIdTable(TablesDTO idTable) {
		this.idTable = idTable;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}
	
	
}