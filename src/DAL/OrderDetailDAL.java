package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.OrderDetailDTO;


public class OrderDetailDAL {
	private Connection connectDB = ConnectJDBC.openConnection();
	
	public boolean checkIfOrderDetailsExistsForOrders(int idOrder) {
        String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE id = ?";
        try (PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
            cmd.setInt(1, idOrder);
            try (ResultSet rs = cmd.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("Total");
                    return total > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while checking if order details exist for Orders: " + e.getMessage());
        }
        return false;
    }

   
    
	public boolean insertOrderDetail(OrderDetailDTO x) {
	    String sqlInsert = "INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES (?, ?, ?)";

	    try (PreparedStatement cmd = connectDB.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

	        if (!checkIfOrderDetailsExistsForOrders(x.getIdOrder().getId())) {
	            System.out.println("No idOrder");
	            return false;
	        }

	        cmd.setInt(1, x.getIdOrder().getId());
	        cmd.setInt(2, x.getIdProduct().getId());
	        cmd.setInt(3, x.getQuantity());

	        int rowsInserted = cmd.executeUpdate();

	        if (rowsInserted > 0) {
	            try (ResultSet generatedKeys = cmd.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    x.setId(generatedKeys.getInt(1));
	                } else {
	                    throw new SQLException("Inserting order detail failed, no ID obtained.");
	                }
	            }
	            return true;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    
    public boolean updateOrderDetail(OrderDetailDTO x)
    {
    	String sqlUpdate = "UPDATE OrderDetail SET idOrder = ?, idProduct = ?, quantity = ? WHERE id = ?";
    	try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
			
			cmd.setInt(1, x.getIdOrder().getId());
			cmd.setInt(2, x.getIdProduct().getId());
			cmd.setInt(3, x.getQuantity());
			cmd.setInt(4, x.getId());
			
			int rowsUpdated = cmd.executeUpdate();
			return rowsUpdated > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
	public boolean deleteOrderDetail(int ID)
	{
		
		String sqlDelete = "DELETE FROM OrderDetail WHERE id = ?";
		try {
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
    
    public ArrayList <OrderDetailDTO> selectAll()
    {
    	ArrayList<OrderDetailDTO> listOrderDetail = new ArrayList<OrderDetailDTO>();
    	OrdersDAL dalOrders = new OrdersDAL();
    	ProductDAL dalProduct = new ProductDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectOrderDetail = "SELECT * FROM OrderDetail";
            ResultSet rs = stmt.executeQuery(sqlSelectOrderDetail);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idProduct = rs.getInt("idProduct");
                int quantity = rs.getInt("quantity");
                
                OrderDetailDTO orderDetail = new OrderDetailDTO(id, dalOrders.findById(idOrder),dalProduct.findById(idProduct) , quantity);
                listOrderDetail.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrderDetail;
    }
    
    public OrderDetailDTO findById(int id)
    {
    	String sqlFindById = "SELECT * FROM OrderDetail WHERE id = ?";
    	OrdersDAL dalOrder = new OrdersDAL();
    	ProductDAL dalProduct = new ProductDAL();
    	OrderDetailDTO orderDetail = null;
    	try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlFindById);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) 
            {
            	int iD = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idProduct = rs.getInt("idProduct");
                int quantity = rs.getInt("quantity");
                
                orderDetail = new OrderDetailDTO(iD, dalOrder.findById(idOrder), dalProduct.findById(idProduct), quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    	
    }
    
    public ArrayList<OrderDetailDTO> findByIdOrder(int id) {
        ArrayList<OrderDetailDTO> listOrderDetail = new ArrayList<>();
        OrdersDAL dalOrders = new OrdersDAL();
        ProductDAL dalProduct = new ProductDAL();
        try {
            String sqlSelectOrderDetail = "SELECT * FROM OrderDetail WHERE idOrder = ?";
            PreparedStatement cmd = connectDB.prepareStatement(sqlSelectOrderDetail);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            
            while (rs.next()) {
                int iD = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idProduct = rs.getInt("idProduct");
                int quantity = rs.getInt("quantity");
                
                OrderDetailDTO orderDetail = new OrderDetailDTO(iD, dalOrders.findById(idOrder), dalProduct.findById(idProduct), quantity);
                listOrderDetail.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrderDetail;
    }
    public ArrayList<OrderDetailDTO> findByIdTable() {
        ArrayList<OrderDetailDTO> listOrderDetail = new ArrayList<>();
        OrdersDAL dalOrders = new OrdersDAL();
        ProductDAL dalProduct = new ProductDAL();
        try {
        	String sqlSelectOrderDetail = "SELECT * " +
                    "FROM OrderDetail odt " +
                    "JOIN Product p ON p.id = odt.idProduct " +
                    "WHERE odt.idOrder = (" +
                    "    SELECT TOP 1 id " +
                    "    FROM Orders " +
                    "    ORDER BY orderTime DESC" +
                    ")";

            PreparedStatement cmd = connectDB.prepareStatement(sqlSelectOrderDetail);
            ResultSet rs = cmd.executeQuery();
            
            while (rs.next()) {
                int iD = rs.getInt("id");
                int idOrder = rs.getInt("idOrder");
                int idProduct = rs.getInt("idProduct");
                int quantity = rs.getInt("quantity");
                
                OrderDetailDTO orderDetail = new OrderDetailDTO(iD, dalOrders.findById(idOrder), dalProduct.findById(idProduct), quantity);
                listOrderDetail.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrderDetail;
    }
}
