package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TablesDTO;


public class TablesDAL {
	Connection connectDB = ConnectJDBC.openConnection();

    public boolean insertTable(TablesDTO x) {
        String sqlInsert = "INSERT INTO Tables (status, seats) VALUES (?, ?)";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlInsert);

            cmd.setBoolean(1, x.isStatus());
            cmd.setInt(2, x.getSeats());

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

    public boolean updateTable(TablesDTO x) {
        String sqlUpdate = "UPDATE Tables SET status = ?, seats = ? WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);

            cmd.setBoolean(1, x.isStatus());
            cmd.setInt(2, x.getSeats());
            cmd.setInt(3, x.getId());

            int rowsUpdated = cmd.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateStatusTable(int id) {
        String sqlUpdate = "UPDATE Tables SET status = 1 WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
            
            cmd.setInt(1, id);

            int rowsUpdated = cmd.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkIfTablesExitsForOrders(int tablesId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Orders WHERE idTable = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1,	tablesId);
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
	
	public boolean deleteTables(int ID)
	{
		
		String sqlDelete = "DELETE FROM Tables WHERE id = ?";
		try {
			if(checkIfTablesExitsForOrders(ID))
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

    public ArrayList<TablesDTO> selectAll() {
    	ArrayList<TablesDTO> listTables = new ArrayList<TablesDTO>();

        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectTables = "SELECT * FROM Tables";
            ResultSet rs = stmt.executeQuery(sqlSelectTables);
            while (rs.next()) {
                int id = rs.getInt("id");
                boolean status = rs.getBoolean("status");
                int seats = rs.getInt("seats");

				TablesDTO table = new TablesDTO(id, status,seats);
                listTables.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTables;
    }

	  public TablesDTO findById(int ID) {
	      TablesDTO table = null;
	      String sqlFindByID = "SELECT * FROM Tables WHERE id = ?";

	      try {
				PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
				cmd.setInt(1, ID);
				ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int id = rs.getInt("id");
	                boolean status = rs.getBoolean("status");
	                int seats = rs.getInt("seats");
	                
	                table = new TablesDTO(id, status,seats);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      return table;
	   }
	  
	  public ArrayList<TablesDTO> findListEmptyTables(){
		  ArrayList<TablesDTO> listTables = new ArrayList<TablesDTO>();
		  String sqlFindListEmptyTables = "SELECT * FROM Tables WHERE status = 0";
		  try {
		         PreparedStatement cmd = this.connectDB.prepareStatement(sqlFindListEmptyTables);
		         ResultSet rs = cmd.executeQuery();

		         while(rs.next()) 
		         {
		        	 int id = rs.getInt("id");
		             boolean status = rs.getBoolean("status");
		             int seats = rs.getInt("seats");
		            
		             TablesDTO tables = new TablesDTO(id, status, seats);
		             listTables.add(tables);
		         }
		      } catch (SQLException e) {
		          e.printStackTrace();
		      }
	        return listTables;
	  }
}

