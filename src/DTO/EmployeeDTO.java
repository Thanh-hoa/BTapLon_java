package DTO;

import java.util.Date;

public class EmployeeDTO  
{
	private int id;
	private String idCard;
	private String fullname;
	private Date birthday;
	private String address;
	private String email;
	private boolean gender;
	private String phoneNumber;
	private String userName;
	private String password;
	private String access;
	
	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", idCard=" + idCard + ", fullname=" + fullname + ", birthday=" + birthday
				+ ", address=" + address + ", email=" + email + ", gender=" + gender + ", phoneNumber=" + phoneNumber
				+ ", userName=" + userName + ", password=" + password + ", access=" + access + "]";
	}
	public EmployeeDTO() {
		super();
	}
	
	public EmployeeDTO(String fullname) {
		super();
		this.fullname = fullname;
	}
	public EmployeeDTO(int id, String fullname) {
		super();
		this.id = id;
		this.fullname = fullname;
	}
	public EmployeeDTO(int id, String idCard, String fullname, Date birthday, String userName, String password, String access) {
		super();
		this.id = id;
		this.idCard = idCard;
		this.fullname = fullname;
		this.birthday = birthday;
		this.userName = userName;
		this.password = password;
		this.access = access;
	}
	public EmployeeDTO(String idCard, String fullname,Date birthday, String userName, String password, String access) {
		super();
		this.idCard = idCard;
		this.fullname = fullname;
		this.birthday = birthday;
		this.userName = userName;
		this.password = password;
		this.access = access;
	}
	public EmployeeDTO(String idCard, String fullname, Date birthday, String address, String email,
			boolean gender, String phoneNumber, String userName, String password, String access) {
		super();
		this.idCard = idCard;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.access = access;
	}
	public EmployeeDTO(int id, String idCard, String fullname, Date birthday, String address, String email,
			boolean gender, String phoneNumber, String userName, String password, String access) {
		super();
		this.id = id;
		this.idCard = idCard;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.access = access;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	
	
	
	
}