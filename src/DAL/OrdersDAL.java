package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import DTO.CustomerDTO;
import DTO.EmployeeDTO;
import DTO.OrdersDTO;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrdersDAL {
    Connection connectDB = ConnectJDBC.openConnection();
    public boolean insertOrders(OrdersDTO x)
    {
    	String sqlInsert = "INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES (?, ?, ?, ?)";
    	try {
    		if(x.getIdTable().isStatus())
    		{
    			System.out.println("Ban da duoc Order");
    			return false;
    		}
			PreparedStatement cmd = connectDB.prepareStatement(sqlInsert, Statement.NO_GENERATED_KEYS);
			
			cmd.setInt(1, x.getIdEmployee().getId());
			cmd.setInt(2, x.getIdCustomer().getId());
			cmd.setInt(3, x.getIdTable().getId());
			
			 LocalDateTime orderTime = x.getOrderTime();
		     Timestamp timestamp = Timestamp.valueOf(orderTime);
		     cmd.setTimestamp(4, timestamp);
			
			int rowsInserted = cmd.executeUpdate();
			return rowsInserted > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    public boolean updateOrders(OrdersDTO x)
    {
    	String sqlUpdate = "UPDATE Orders SET idEmployee = ?, idCustomer = ?,idTable = ?, orderTime = ? WHERE id = ?";
    	try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
			
			cmd.setInt(1, x.getIdEmployee().getId());
			cmd.setInt(2, x.getIdCustomer().getId());
			cmd.setInt(3, x.getIdTable().getId());
			LocalDateTime orderTime = x.getOrderTime();
			Timestamp timestamp = Timestamp.valueOf(orderTime);
			cmd.setTimestamp(4, timestamp);
			cmd.setInt(5, x.getId());
			
			int rowsUpdated = cmd.executeUpdate();
			return rowsUpdated > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
   
    public boolean checkIfOrdersExitsForOrderDetails(int ordersId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM OrderDetail WHERE idOrder = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1,	ordersId);
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
    public boolean checkIfOrdersExitsForInvoices(int ordersId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Invoices WHERE idOrder = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1,	ordersId);
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
	public boolean deleteOrders(int ID)
	{
		
		String sqlDelete = "DELETE FROM Orders WHERE id = ?";
		try {
			if(checkIfOrdersExitsForInvoices(ID) || checkIfOrdersExitsForOrderDetails(ID))
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
    
    public ArrayList <OrdersDTO> selectAll()
    {
    	ArrayList<OrdersDTO> listOrders = new ArrayList<OrdersDTO>();
    	CustomerDAL dalCustomer = new CustomerDAL();
    	EmployeeDAL dalEmployee = new EmployeeDAL();
    	TablesDAL dalTable = new TablesDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectOrders = "SELECT * FROM Orders";
            ResultSet rs = stmt.executeQuery(sqlSelectOrders);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idEmployee = rs.getInt("idEmployee");
                int idCustomer = rs.getInt("idCustomer");
                int idTable = rs.getInt("idTable");

                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();
                
                OrdersDTO order = new OrdersDTO(id, dalEmployee.findByID(idEmployee), dalCustomer.findByID(idCustomer),dalTable.findById(idTable) , orderTime);
                listOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrders;
    }
    
    public OrdersDTO findById(int id)
    {
    	String sqlFindById = "SELECT * FROM Orders WHERE id = ?";
    	CustomerDAL dalCustomer = new CustomerDAL();
    	EmployeeDAL dalEmployee = new EmployeeDAL();
    	TablesDAL dalTable = new TablesDAL(); 
    	OrdersDTO order = null;
    	try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlFindById);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) 
            {
            	int iD = rs.getInt("id");
                int idEmployee = rs.getInt("idEmployee");
                int idCustomer = rs.getInt("idCustomer");
                int idTable = rs.getInt("idTable");
                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();
                
                order = new OrdersDTO(iD, dalEmployee.findByID(idEmployee), dalCustomer.findByID(idCustomer), dalTable.findById(idTable), orderTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return order;
    }
    
    public boolean insertOrdersUserName(String userName, int idTable, CustomerDTO customer) {
        String insertOrderSQL = "INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES (?, ?, ?, GETDATE())";

        try (PreparedStatement psOrder = connectDB.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            EmployeeDAL dalEmployee = new EmployeeDAL();

            // Retrieve the employee ID by username
            EmployeeDTO employee = dalEmployee.findByUserName(userName);
            if (employee == null) {
                System.err.println("Employee with username " + userName + " not found.");
                return false;
            }
            int employeeId = employee.getId();

            // Set parameters for the prepared statement
            psOrder.setInt(1, employeeId);
            psOrder.setInt(2, customer.getId());
            psOrder.setInt(3, idTable);

            // Execute the insertion
            int rowsAffected = psOrder.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = psOrder.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        // Update the newly inserted order's ID in the OrdersDTO object
                        OrdersDTO newestOrder = OrdersNewest();
                        if (newestOrder != null) {
                            newestOrder.setId(orderId);
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, handle the exception accordingly
        }
        return false;
    }


    
    public OrdersDTO OrdersNewest() {
        String sqlFindById = "SELECT TOP 1 * FROM Orders ORDER BY id DESC";
        CustomerDAL dalCustomer = new CustomerDAL();
        EmployeeDAL dalEmployee = new EmployeeDAL();
        TablesDAL dalTable = new TablesDAL();
        OrdersDTO order = null;
        
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlFindById);
            ResultSet rs = cmd.executeQuery();
            
            if (rs.next()) {
                int orderId = rs.getInt("id");
                int employeeId = rs.getInt("idEmployee");
                int customerId = rs.getInt("idCustomer");
                int tableId = rs.getInt("idTable");
                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();

                order = new OrdersDTO(
                    orderId,
                    dalEmployee.findByID(employeeId),
                    dalCustomer.findByID(customerId),
                    dalTable.findById(tableId),
                    orderTime
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return order;
    }


    public ArrayList<OrdersDTO> listFindByDate(Date day) {
        ArrayList<OrdersDTO> ordersList = new ArrayList<>();
        String sqlCheck = "SELECT * FROM Orders WHERE CONVERT(date, orderTime) = ?";
        EmployeeDAL dalEmployee = new EmployeeDAL();
        CustomerDAL dalCustomer = new CustomerDAL();
        TablesDAL dalTable = new TablesDAL();
        try {
            PreparedStatement stmt = connectDB.prepareStatement(sqlCheck);
            stmt.setDate(1, new java.sql.Date(day.getTime()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	int orderId = rs.getInt("id");
                int employeeId = rs.getInt("idEmployee");
                int customerId = rs.getInt("idCustomer");
                int tableId = rs.getInt("idTable");
                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();

                OrdersDTO order = new OrdersDTO(
                    orderId,
                    dalEmployee.findByID(employeeId),
                    dalCustomer.findByID(customerId),
                    dalTable.findById(tableId),
                    orderTime
                );
                ordersList.add(order);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ordersList;
    }
    
    public ArrayList<OrdersDTO> listFindByMonthAndYear(int month, int year) {
        ArrayList<OrdersDTO> ordersList = new ArrayList<>();
        String sqlCheck = "SELECT * FROM Orders WHERE MONTH(orderTime) = ? AND YEAR(orderTime) = ?";
        EmployeeDAL dalEmployee = new EmployeeDAL();
        CustomerDAL dalCustomer = new CustomerDAL();
        TablesDAL dalTable = new TablesDAL();
        try {
            PreparedStatement stmt = connectDB.prepareStatement(sqlCheck);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	int orderId = rs.getInt("id");
                int employeeId = rs.getInt("idEmployee");
                int customerId = rs.getInt("idCustomer");
                int tableId = rs.getInt("idTable");
                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();

                OrdersDTO order = new OrdersDTO(
                    orderId,
                    dalEmployee.findByID(employeeId),
                    dalCustomer.findByID(customerId),
                    dalTable.findById(tableId),
                    orderTime
                );
                ordersList.add(order);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ordersList;
    }
    
    public ArrayList<OrdersDTO> listFindByDateToDate(Date day1, Date day2) {
        ArrayList<OrdersDTO> ordersList = new ArrayList<>();
        String sqlCheck = "SELECT * FROM Orders WHERE CONVERT(date, orderTime) BETWEEN ? AND ?";
        EmployeeDAL dalEmployee = new EmployeeDAL();
        CustomerDAL dalCustomer = new CustomerDAL();
        TablesDAL dalTable = new TablesDAL();
        try {
            PreparedStatement stmt = connectDB.prepareStatement(sqlCheck);
            stmt.setDate(1, new java.sql.Date(day1.getTime()));
            stmt.setDate(2, new java.sql.Date(day2.getTime()));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	int orderId = rs.getInt("id");
                int employeeId = rs.getInt("idEmployee");
                int customerId = rs.getInt("idCustomer");
                int tableId = rs.getInt("idTable");
                Timestamp timestamp = rs.getTimestamp("orderTime");
                LocalDateTime orderTime = timestamp.toLocalDateTime();

                OrdersDTO order = new OrdersDTO(
                    orderId,
                    dalEmployee.findByID(employeeId),
                    dalCustomer.findByID(customerId),
                    dalTable.findById(tableId),
                    orderTime
                );
                ordersList.add(order);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ordersList;
    }
}
