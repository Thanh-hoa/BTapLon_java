package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.VipCustomerDTO;


public class VipCustomerDAL {
	Connection connectDB = ConnectJDBC.openConnection();

    public boolean insertVipCustomer(VipCustomerDTO x) {
        String sqlInsert = "INSERT INTO VipCustomer (idCustomer) VALUES (?)";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlInsert);

            cmd.setInt(1, x.getIdCustomer().getId());

            int rowsAffected = cmd.executeUpdate();
//            if (rowsAffected > 0) {
//                ResultSet generatedKeys = cmd.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    x.setId(generatedKeys.getInt(1));
//                }
//                return true;
//            }
//            return false;
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVipCustomer(VipCustomerDTO x) {
        String sqlUpdate = "UPDATE VipCustomer SET idCustomer = ? WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);

            cmd.setInt(1, x.getIdCustomer().getId());
            cmd.setInt(2, x.getId());


            int rowsUpdated = cmd.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
	
	public boolean deleteVipCustomer(int ID)
	{
		
		String sqlDelete = "DELETE FROM VipCustomer WHERE id = ?";
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

    public ArrayList<VipCustomerDTO> selectAll() {
    	ArrayList<VipCustomerDTO> listVipCustomer= new ArrayList<VipCustomerDTO>();
    	CustomerDAL dalCustomer = new CustomerDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectTables = "SELECT * FROM VipCustomer";
            ResultSet rs = stmt.executeQuery(sqlSelectTables);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCustomer = rs.getInt("idCustomer");

				VipCustomerDTO vipCustomer = new VipCustomerDTO(id, dalCustomer.findByID(idCustomer));
                listVipCustomer.add(vipCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listVipCustomer;
    }

	  public VipCustomerDTO findById(int ID) {
	      VipCustomerDTO vipCustomer = null;
	      String sqlFindByID = "SELECT * FROM VipCustomer WHERE id = ?";
	      CustomerDAL dalCustomer = new CustomerDAL();
	      try {
				PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
				cmd.setInt(1, ID);
				ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int id = rs.getInt("id");
					int idCustomer = rs.getInt("idCustomer");
	                
	                vipCustomer = new VipCustomerDTO(id, dalCustomer.findByID(idCustomer));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      return vipCustomer;
	   }
	  
}
