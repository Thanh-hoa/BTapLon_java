package DAL;

import DTO.EmployeeDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeDAL {
   Connection connectDB = ConnectJDBC.openConnection();

   public boolean insertEmployee(EmployeeDTO x) {
	    String sqlInsert = "INSERT INTO Employee (idCard, fullname, birthday, address, email, gender, phoneNumber, username, password, access) VALUES (?,?,?,?,?,?,?,?,?,?)";

	    try (PreparedStatement cmd = this.connectDB.prepareStatement(sqlInsert)) {
	        cmd.setString(1, x.getIdCard());
	        cmd.setString(2, x.getFullname());
	        java.util.Date bd = x.getBirthday();
	        java.sql.Date birthday = new java.sql.Date(bd.getTime());
	        cmd.setDate(3, birthday);
	        cmd.setString(4, x.getAddress());
	        cmd.setString(5, x.getEmail());
	        cmd.setBoolean(6, x.isGender());
	        cmd.setString(7, x.getPhoneNumber());
	        cmd.setString(8, x.getUserName());
	        cmd.setString(9, x.getPassword());
	        cmd.setString(10, x.getAccess());

	        int rowsAffected = cmd.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


   public boolean updateEmployee(EmployeeDTO x) {
      String sqlUpdate = "UPDATE Employee SET idCard = ?, fullname = ?, birthday = ?, address = ?, email = ?, gender = ?, phoneNumber = ?, username = ?, password = ?, access = ? WHERE id = ?";

      try {
         PreparedStatement cmd = this.connectDB.prepareStatement(sqlUpdate);
         cmd.setString(1, x.getIdCard());
         cmd.setString(2, x.getFullname());
         java.util.Date bd = x.getBirthday();
         java.sql.Date birthday = new java.sql.Date(bd.getTime());
         cmd.setDate(3, birthday);
         cmd.setString(4, x.getAddress());
         cmd.setString(5, x.getEmail());
         cmd.setBoolean(6, x.isGender());
         cmd.setString(7, x.getPhoneNumber());
         cmd.setString(8, x.getUserName());
         cmd.setString(9, x.getPassword());
         cmd.setString(10, x.getAccess());
         cmd.setInt(11, x.getId());
         int rowsUpdated = cmd.executeUpdate();
         return rowsUpdated > 0;
      } catch (SQLException e) {
          e.printStackTrace();
         return false;
      }
   }

   public boolean checkIfEmployeesExitsForShifts(int employeeId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Shifts WHERE idEmployee = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, employeeId);
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
   public boolean checkIfEmployeesExitsForInvoices(int employeeId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Invoices WHERE idEmployee = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, employeeId);
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
   public boolean checkIfEmployeesExitsForOrders(int employeeId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE idEmployee = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, employeeId);
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
	public boolean deleteEmployee(int ID)
	{
		
		String sqlDelete = "DELETE FROM Employee WHERE id = ?";
		try {
			if(checkIfEmployeesExitsForInvoices(ID) || checkIfEmployeesExitsForOrders(ID) || checkIfEmployeesExitsForShifts(ID))
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
   
   public ArrayList<EmployeeDTO> selectAll() {
      ArrayList<EmployeeDTO> listEmployee = new ArrayList<EmployeeDTO>();

      try {
         Statement stmt = this.connectDB.createStatement();
         String sqlSelectEmmployee = "SELECT * FROM Employee";
         ResultSet tblEmployee = stmt.executeQuery(sqlSelectEmmployee);

         while(tblEmployee.next()) {
            int employeeID = tblEmployee.getInt("id");
            String employeeIdCard = tblEmployee.getString("idCard");
            String employeeFullName = tblEmployee.getString("fullname");
            
            Date employeeBirthday = tblEmployee.getDate("birthday");
            
            String employeeAddress = tblEmployee.getString("address");
            String employeeEmail = tblEmployee.getString("email");
            boolean employeeGender = tblEmployee.getBoolean("gender");
            String employeePhoneNumber = tblEmployee.getString("phoneNumber");
            String employeeUserName = tblEmployee.getString("username");
            String employeePassword = tblEmployee.getString("password");
            String employeeAccess = tblEmployee.getString("access");
           
            EmployeeDTO employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
            listEmployee.add(employee);
         }

         return listEmployee;
      } catch (SQLException e) {
          e.printStackTrace();
         return null;
      }
   }

   public ArrayList<EmployeeDTO> findByName(String employeeName) {
      ArrayList<EmployeeDTO> listEmployeeFindByName = new ArrayList<EmployeeDTO>();
      String sqlFindByName = "SELECT * FROM Employee WHERE fullname Like ?";

      try {
         PreparedStatement cmd = this.connectDB.prepareStatement(sqlFindByName);
         cmd.setString(1, "%" + employeeName + "%");
         ResultSet rs = cmd.executeQuery();

         while(rs.next()) {
            int employeeID = rs.getInt("id");
            String employeeIdCard = rs.getString("idCard");
            String employeeFullName = rs.getString("fullname");
            
            Date employeeBirthday = rs.getDate("birthday");
            
            String employeeAddress = rs.getString("address");
            String employeeEmail = rs.getString("email");
            boolean employeeGender = rs.getBoolean("gender");
            String employeePhoneNumber = rs.getString("phoneNumber");
            String employeeUserName = rs.getString("username");
            String employeePassword = rs.getString("password");
            String employeeAccess = rs.getString("access");
            
            EmployeeDTO employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
            listEmployeeFindByName.add(employee);
         }
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return listEmployeeFindByName;
   }

   public EmployeeDTO findByID(int id) {
      EmployeeDTO employee = null;
		String sqlFindByID = "SELECT * FROM Employee WHERE id = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
			cmd.setInt(1, id);
			ResultSet rs = cmd.executeQuery();
			while(rs.next())
			{
				int employeeID = rs.getInt("id");
				String employeeIdCard = rs.getString("idCard");
				String employeeFullName = rs.getString("fullName");
				Date employeeBirthday = rs.getDate("birthday");
				String employeeAddress = rs.getString("address");
	            String employeeEmail = rs.getString("email");
	            boolean employeeGender = rs.getBoolean("gender");
	            String employeePhoneNumber = rs.getString("phoneNumber");
	            String employeeUserName = rs.getString("userName");
	            String employeePassword = rs.getString("password");
	            String employeeAccess = rs.getString("access");
              
	            employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
           
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      return employee;
   }
   public EmployeeDTO findByUserName(String username) {
	      EmployeeDTO employee = null;
			String sqlFindByID = "SELECT * FROM Employee WHERE username = ?";
			try {
				PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
				cmd.setString(1, username);
				ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int employeeID = rs.getInt("id");
					String employeeIdCard = rs.getString("idCard");
					String employeeFullName = rs.getString("fullName");
					Date employeeBirthday = rs.getDate("birthday");
					String employeeAddress = rs.getString("address");
		            String employeeEmail = rs.getString("email");
		            boolean employeeGender = rs.getBoolean("gender");
		            String employeePhoneNumber = rs.getString("phoneNumber");
		            String employeeUserName = rs.getString("userName");
		            String employeePassword = rs.getString("password");
		            String employeeAccess = rs.getString("access");
	              
		            employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
	           
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      return employee;
	   }
   public EmployeeDTO findByFullName(String fullname) {
	      EmployeeDTO employee = null;
			String sqlFindByID = "SELECT * FROM Employee WHERE fullname = ?";
			try {
				PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
				cmd.setString(1, fullname);
				ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int employeeID = rs.getInt("id");
					String employeeIdCard = rs.getString("idCard");
					String employeeFullName = rs.getString("fullName");
					Date employeeBirthday = rs.getDate("birthday");
					String employeeAddress = rs.getString("address");
		            String employeeEmail = rs.getString("email");
		            boolean employeeGender = rs.getBoolean("gender");
		            String employeePhoneNumber = rs.getString("phoneNumber");
		            String employeeUserName = rs.getString("userName");
		            String employeePassword = rs.getString("password");
		            String employeeAccess = rs.getString("access");
	              
		            employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
	           
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      return employee;
	   }
   public ArrayList<EmployeeDTO> findByAccess(String access) {
	      ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
			String sqlFindByID = "SELECT * FROM Employee WHERE access = ?";
			try {
				PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
				cmd.setString(1, access);
				ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int employeeID = rs.getInt("id");
					String employeeIdCard = rs.getString("idCard");
					String employeeFullName = rs.getString("fullName");
					Date employeeBirthday = rs.getDate("birthday");
					String employeeAddress = rs.getString("address");
		            String employeeEmail = rs.getString("email");
		            boolean employeeGender = rs.getBoolean("gender");
		            String employeePhoneNumber = rs.getString("phoneNumber");
		            String employeeUserName = rs.getString("userName");
		            String employeePassword = rs.getString("password");
		            String employeeAccess = rs.getString("access");
	              
		            EmployeeDTO employee = new EmployeeDTO(employeeID, employeeIdCard, employeeFullName, employeeBirthday, employeeAddress, employeeEmail, employeeGender, employeePhoneNumber, employeeUserName, employeePassword, employeeAccess);
		            list.add(employee);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      return list;
	   }
   
	public ArrayList <String> selectTen()
	{
		ArrayList <String> listEmployee = new ArrayList <>();
		try {
			Statement stmt = connectDB.createStatement();
			String sql = "SELECT fullname FROM Employee Where access='employee'";
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next())
			{
				  
				
				String FullName = rs.getString("fullname");
				
//				EmployeeDTO employee = new EmployeeDTO( FullName );
				listEmployee.add(FullName);	
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return listEmployee;
	}
	
	
   public boolean checkLogin(EmployeeDTO x) {
      String sqlCheckLogin = "SELECT * FROM Employee WHERE username = ? AND password = ?";

      try {
         PreparedStatement cmd = connectDB.prepareStatement(sqlCheckLogin);
         cmd.setString(1, x.getUserName());
         cmd.setString(2, x.getPassword());
         ResultSet rs = cmd.executeQuery();
         return rs.next();
      } catch (SQLException e) {
          e.printStackTrace();
         return false;
      }
   }
}
