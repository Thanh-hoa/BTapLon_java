package DAL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.CategoryDTO;
//import DTO.InvoiceDetailDTO;
//import DTO.ProductDTO;


public class CategoryDAL {
	Connection connectDB = ConnectJDBC.openConnection();
	
	public boolean insertCategory(CategoryDTO x)
	{
		String sqlInsert = "INSERT INTO Category (name) VALUES (?)";
		try 
		{
			PreparedStatement cmd = connectDB.prepareStatement(sqlInsert);
			cmd.setString(1, x.getName());
					
			boolean Added = cmd.execute();
			return Added;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public int count()
	{
		int count = 0;
		for(CategoryDTO x : this.selectAll())
		{
			if(x != null)
				count++;
		}
		return count;
	}
	public boolean updateCategory(CategoryDTO x)
	{
		String sqlUpdate = "UPDATE Category SET name = ? WHERE id = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);
			
			cmd.setString(1, x.getName());
			cmd.setInt(2, x.getId());
			
			int rowsUpdated = cmd.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean checkIfProductsExitsForCategory(int categoryId) throws SQLException
	{
		String sqlCheck = "SELECT COUNT(*) AS Total FROM Product WHERE idCategory = ?";
		try(PreparedStatement cmd = connectDB.prepareStatement(sqlCheck)) {
			
			cmd.setInt(1, categoryId);
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
	
	public boolean deleteCategory(int ID)
	{
		
		String sqlDelete = "DELETE FROM Category WHERE id = ?";
		try {
			if(checkIfProductsExitsForCategory(ID))
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
	
	public ArrayList <CategoryDTO> selectAll()
	{
		ArrayList <CategoryDTO> listCategory = new ArrayList <CategoryDTO>();
		
		Statement stmt;
		try {
			stmt = connectDB.createStatement();
			String sqlSelectCategory = "SELECT * FROM Category";
			ResultSet tblCategory = stmt.executeQuery(sqlSelectCategory);
			while(tblCategory.next())
			{
				int categoryID = tblCategory.getInt("id");
				String categoryName = tblCategory.getString("name");

				CategoryDTO category = new CategoryDTO(categoryID, categoryName);
				listCategory.add(category);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCategory;
	}
	

	public CategoryDTO findByName(String name)
	{
		CategoryDTO category = null;
		String sqlFindByName = "SELECT * FROM Category WHERE name = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByName);
			cmd.setString(1, name);
			ResultSet rs = cmd.executeQuery();
			while(rs.next())
			{
				int categoryID = rs.getInt("id");
				String categoryName = rs.getString("name");

				category = new CategoryDTO(categoryID, categoryName);
             
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
		
	}
	
	  public CategoryDTO findById(int ID) {
	      CategoryDTO category = null;
	      String sqlFindByID = "SELECT * FROM Category WHERE id = ?";

	      try {
	         PreparedStatement cmd = this.connectDB.prepareStatement(sqlFindByID);
	         cmd.setInt(1, ID);

	         int categoryId;
	         String categoryName;
	         for(ResultSet rs = cmd.executeQuery(); rs.next(); category = new CategoryDTO(categoryId, categoryName)) {
	            categoryId = rs.getInt("id");
	            categoryName = rs.getString("name");
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }

	      return category;
	   }
	  
	 
	}


