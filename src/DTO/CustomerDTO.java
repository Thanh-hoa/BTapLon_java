package DTO;

public class CustomerDTO{
	private int id;
	private String fullname;
	private String phoneNumber;
	
	
	public CustomerDTO(int id, String fullname, String phoneNumber) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", fullname=" + fullname + ", phoneNumber=" + phoneNumber + "]";
	}

	
	public CustomerDTO(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}


	public CustomerDTO(String fullname, String phoneNumber) {
		super();
		this.fullname = fullname;
		this.phoneNumber = phoneNumber;
	}

	public CustomerDTO() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
