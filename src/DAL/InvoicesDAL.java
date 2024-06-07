package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.InvoicesDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class InvoicesDAL {
    Connection connectDB = ConnectJDBC.openConnection();
    public boolean checkIfInvoicesExitsForOrders(int invoiceId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE id = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1,	invoiceId);
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
    public boolean insertInvoices(InvoicesDTO x) {
        String sqlInsert = "INSERT INTO Invoices (idOrder, idEmployee, invoiceTime, paymentStatus, paymentMethod) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            
            if (!checkIfInvoicesExitsForOrders(x.getIdOrders().getId())) {
            	System.out.println("Invoices is not exitst for Orders");
                return false;
            }
            
            cmd.setInt(1, x.getIdOrders().getId());
            cmd.setInt(2, x.getIdEmployee().getId());
            LocalDateTime invoiceTime = x.getInvoiceTime();
            Timestamp timestamp = Timestamp.valueOf(invoiceTime);
            cmd.setTimestamp(3, timestamp);
            cmd.setBoolean(4, x.isPaymentStatus());
            cmd.setBoolean(5, x.isPaymentMethod());
            
            int rowsInserted = cmd.executeUpdate();
            
            if (rowsInserted > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                if(rs.next()) {
                    x.setId(rs.getInt(1));  // Set the generated ID to the InvoicesDTO object
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public boolean checkIfInvoicesExitsForOrders(int orderId) throws SQLException {
//        String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE id = ?";
//        try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
//            cmd.setInt(1, orderId);
//            try(ResultSet rs = cmd.executeQuery()) {
//                if(rs.next()) {
//                    int total = rs.getInt("Total");
//                    return total > 0;
//                }
//            }
//        }
//        return false;
//    }


    
    public boolean updateInvoices(InvoicesDTO x)
    {
    	String sqlUpdate = "UPDATE Invoices SET idOrder = ?, idEmployee = ?, invoiceTime = ?, paymentStatus = ?, paymentMethod = ? WHERE id = ?";
    	try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
			
			cmd.setInt(1, x.getIdOrders().getId());
			cmd.setInt(2, x.getIdEmployee().getId());
			LocalDateTime invoiceTime = x.getInvoiceTime();
		    Timestamp timestamp = Timestamp.valueOf(invoiceTime);
		    cmd.setTimestamp(3, timestamp);
		    cmd.setBoolean(4, x.isPaymentStatus());
		    cmd.setBoolean(5, x.isPaymentMethod());
			cmd.setInt(6, x.getId());
			
			int rowsUpdated = cmd.executeUpdate();
			return rowsUpdated > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
//	public boolean deleteInvoices(int ID)
//	{
//		
//		String sqlDelete = "DELETE FROM Invoices WHERE id = ?";
//		try {
//			PreparedStatement cmd = connectDB.prepareStatement(sqlDelete);
//			
//			cmd.setInt(1, ID);
//			
//			int rowDeleted = cmd.executeUpdate();
//			return rowDeleted > 0;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
    
    public ArrayList <InvoicesDTO> selectAll()
    {
    	ArrayList<InvoicesDTO> listInvoices = new ArrayList<InvoicesDTO>();
    	OrdersDAL dalOrder = new OrdersDAL();
    	CustomerDAL dalCustomer = new CustomerDAL();
    	EmployeeDAL dalEmployee = new EmployeeDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectInvoices = "SELECT * FROM Invoices";
            ResultSet rs = stmt.executeQuery(sqlSelectInvoices);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idCustomer = rs.getInt("idCustomer");
                int idEmployee = rs.getInt("idEmployee");
                Timestamp timestamp = rs.getTimestamp("invoiceTime");
                LocalDateTime invoiceTime = timestamp.toLocalDateTime();
                float calculateMoney = rs.getFloat("calculateMoney");
                int quantity = rs.getInt("quantity");
                boolean paymentStatus = rs.getBoolean("paymentStatus");
                boolean paymentMethod = rs.getBoolean("paymentMethod");
                
                InvoicesDTO invoice = new InvoicesDTO(id, dalOrder.findById(idOrder), dalCustomer.findByID(idCustomer), dalEmployee.findByID(idEmployee), invoiceTime, calculateMoney, quantity, paymentStatus, paymentMethod);
                listInvoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listInvoices;
    }
    
    public InvoicesDTO findById(int id)
    {
    	String sqlFindById = "SELECT * FROM Invoices WHERE id = ?";
    	OrdersDAL dalOrder = new OrdersDAL();
    	EmployeeDAL dalEmployee = new EmployeeDAL();
    	CustomerDAL dalCustomer = new CustomerDAL();
    	InvoicesDTO invoice = null;
    	try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlFindById);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) 
            {
            	int iD = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idCustomer = rs.getInt("idCustomer");
                int idEmployee = rs.getInt("idEmployee");
                Timestamp timestamp = rs.getTimestamp("invoiceTime");
                LocalDateTime invoiceTime = timestamp.toLocalDateTime();
                float calculateMoney = rs.getFloat("calculateMoney");
                int quantity = rs.getInt("quantity");
                boolean paymentStatus = rs.getBoolean("paymentStatus");
                boolean paymentMethod = rs.getBoolean("paymentMethod");
                
                invoice = new InvoicesDTO(iD, dalOrder.findById(idOrder), dalCustomer.findByID(idCustomer), dalEmployee.findByID(idEmployee), invoiceTime, calculateMoney, quantity, paymentStatus, paymentMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    	
    }
    
    public InvoicesDTO findByIdTable()
    {
    	String sqlFindByIdTable = "SELECT TOP 1 * FROM Invoices \r\n"
    			      + "ORDER BY invoiceTime DESC;";
    	OrdersDAL dalOrder = new OrdersDAL();
    	EmployeeDAL dalEmployee = new EmployeeDAL();
    	CustomerDAL dalCustomer = new CustomerDAL();
    	InvoicesDTO invoice = null;
    	try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlFindByIdTable);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) 
            {
            	int iD = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idCustomer = rs.getInt("idCustomer");
                int idEmployee = rs.getInt("idEmployee");
                Timestamp timestamp = rs.getTimestamp("invoiceTime");
                LocalDateTime invoiceTime = timestamp.toLocalDateTime();
                float calculateMoney = rs.getFloat("calculateMoney");
                int quantity = rs.getInt("quantity");
                boolean paymentStatus = rs.getBoolean(1);
                boolean paymentMethod = rs.getBoolean("paymentMethod");
                
                invoice = new InvoicesDTO(iD, dalOrder.findById(idOrder), dalCustomer.findByID(idCustomer), dalEmployee.findByID(idEmployee), invoiceTime, calculateMoney, quantity, paymentStatus, paymentMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    	
    }
}
