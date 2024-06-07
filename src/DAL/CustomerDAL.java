package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.CustomerDTO;

public class CustomerDAL {
	Connection connectDB = ConnectJDBC.openConnection();
	
//	public boolean insertCustomer(CustomerDTO x)
//	{
//		String sqlInsert = "INSERT INTO Customer ( phoneNumber) VALUES (?)";
//		try 
//		{
//			PreparedStatement cmd = connectDB.prepareStatement(sqlInsert);
//					
//			cmd.setString(1, x.getPhoneNumber());
//					
//			boolean Added = cmd.execute();
//			return Added;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public void insertCustomer(CustomerDTO customer) throws SQLException {
	    String insertCustomerSQL = "INSERT INTO Customer (phoneNumber) VALUES (?)";
	    try (PreparedStatement ps = connectDB.prepareStatement(insertCustomerSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, customer.getPhoneNumber());
	        ps.executeUpdate();
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            customer.setId(rs.getInt(1)); // Update the ID of the customer
	        }
	    }
	}

	
	public boolean updateCustomer(CustomerDTO x)
	{
		String sqlUpdate = "UPDATE Customer SET fullname = ?, phoneNumber = ? WHERE id = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
			
			cmd.setString(1, x.getFullname());
			cmd.setString(2, x.getPhoneNumber());
			cmd.setInt(3, x.getId());
			
			int rowsUpdated = cmd.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean checkIfCustomersExitsForOrders(int customerId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE idCustomer = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, customerId);
			try(ResultSet rs = cmd.executeQuery())
			{
				if(rs.next())
				{
					int total = rs.getInt("Total");
					return total > 0;
				}
			}
		}
		return false;
		
	}
	
	public boolean checkIfCustomersExitsForInvoices(int customerId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Invoices WHERE idCustomer = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, customerId);
			try(ResultSet rs = cmd.executeQuery())
			{
				if(rs.next())
				{
					int total = rs.getInt("Total");
					return total > 0;
				}
			}
		}
		return false;
		
	}
	
	public boolean deleteCustomer(int ID)
	{
		
		String sqlDelete = "DELETE FROM Customer WHERE id = ?";
		try {
			if(checkIfCustomersExitsForInvoices(ID) || checkIfCustomersExitsForOrders(ID))
				return false;
			PreparedStatement cmd = connectDB.prepareStatement(sqlDelete);
			
			cmd.setInt(1, ID);
			
			int rowDeleted = cmd.executeUpdate();
			return rowDeleted > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	public ArrayList <CustomerDTO> selectAll()
	{
		ArrayList <CustomerDTO> listCustomer = new ArrayList <CustomerDTO>();
		
		Statement stmt;
		try {
			stmt = connectDB.createStatement();
			String sqlSelectCustomer = "SELECT * FROM Customer";
			ResultSet tblCustomer = stmt.executeQuery(sqlSelectCustomer);
			while(tblCustomer.next())
			{
				int employeeID = tblCustomer.getInt("id");
				String employeeFullName = tblCustomer.getString("fullname");
				String employeePhoneNumber = tblCustomer.getString("phoneNumber");

				CustomerDTO customer = new CustomerDTO(employeeID, employeeFullName,employeePhoneNumber);
				listCustomer.add(customer);
			}
			return listCustomer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList <CustomerDTO> findByName(String customerName)
	{
		ArrayList <CustomerDTO> listCustomerFindByName = new ArrayList <CustomerDTO>();
		String sqlFindByName = "SELECT * FROM Customer WHERE fullname Like ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByName);
			
			cmd.setString(1, "%" + customerName + "%");
			ResultSet rs = cmd.executeQuery();
			
			while(rs.next())
			{
				int customerID = rs.getInt("id");
				String customerFullName = rs.getString("fullname");
                String customerPhoneNumber = rs.getString("phoneNumber");
     
                CustomerDTO customer = new CustomerDTO(customerID, customerFullName, customerPhoneNumber);
                listCustomerFindByName.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCustomerFindByName;
	}
	
	public CustomerDTO findByID(int ID)
	{
		CustomerDTO customer = null;
		String sqlFindByID = "SELECT * FROM Customer WHERE id = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
			cmd.setInt(1, ID);
			ResultSet rs = cmd.executeQuery();
			while(rs.next())
			{
				int customerID = rs.getInt("id");
				String customerFullName = rs.getString("fullname");
                String customerPhoneNumber = rs.getString("phoneNumber");
                
                customer = new CustomerDTO(customerID, customerFullName,customerPhoneNumber);
             
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
		
	}
	
	public boolean checkIfCustomerExitsForVipCustomer(int customerId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM VipCustomer WHERE idCustomer = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, customerId);
			try(ResultSet rs = cmd.executeQuery())
			{
				if(rs.next())
				{
					int total = rs.getInt("Total");
					return total > 0;
				}
			}
		}
		return false;
		
	}
	
	public CustomerDTO findByPhoneNumber(String phoneNumber)
	{
		CustomerDTO customer = null;
		String sqlFindByID = "SELECT * FROM Customer WHERE phoneNumber = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
			cmd.setString(1, phoneNumber);
			ResultSet rs = cmd.executeQuery();
			while(rs.next())
			{
				int customerID = rs.getInt("id");
				String customerFullName = rs.getString("fullname");
                String customerPhoneNumber = rs.getString("phoneNumber");
                
                customer = new CustomerDTO(customerID, customerFullName,customerPhoneNumber);
             
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
		
	}
	
	public boolean checkPhoneNumberExitsCustomer(String phoneNumber) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Customer WHERE phoneNumber = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setString(1, phoneNumber);
			try(ResultSet rs = cmd.executeQuery())
			{
				if(rs.next())
				{
					int total = rs.getInt("Total");
					return total > 0;
				}
			}
		}
		return false;
		
	}
}
