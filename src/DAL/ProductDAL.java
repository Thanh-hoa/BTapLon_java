package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import DTO.InvoiceDetailDTO;
import DTO.ProductDTO;

public class ProductDAL {
    Connection connectDB = ConnectJDBC.openConnection();

    public boolean insertProduct(ProductDTO x) {
        String sqlInsert = "INSERT INTO Product (name, thumbnail, description, price, unit, idCategory) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement cmd = connectDB.prepareStatement(sqlInsert)) {
            cmd.setString(1, x.getName());
            cmd.setString(2, x.getThumbnail());
            cmd.setString(3, x.getDescription());
            cmd.setFloat(4, x.getPrice());
            cmd.setString(5, x.getUnit());
            cmd.setInt(6, x.getIdCategory().getId());

            int rowsAffected = cmd.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<ProductDTO> findByCategoryOrder(String cname) {
        ArrayList<ProductDTO> listProduct = new ArrayList<ProductDTO>();
        try {
            String sqlFindByCategory = "SELECT Product.id, Product.name, Product.price   FROM Product JOIN Category ON Product.idCategory = Category.id WHERE Category.name Like ?";
            PreparedStatement cmd = this.connectDB.prepareStatement(sqlFindByCategory);
            cmd.setString(1,"%"+cname+"%");
            ResultSet rs = cmd.executeQuery();
            
                while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                
                ProductDTO product = new ProductDTO(id, name, price );
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProduct;
    }



    public boolean updateProduct(ProductDTO x) {
        String sqlUpdate = "UPDATE Product SET name = ?, thumbnail = ?, description = ?, price = ?, unit = ?, idCategory = ? WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);

            cmd.setString(1, x.getName());
			cmd.setString(2, x.getThumbnail());
			cmd.setString(3, x.getDescription());
			cmd.setFloat(4, x.getPrice());
			cmd.setString(5, x.getUnit());
			cmd.setInt(6, x.getIdCategory().getId());
			cmd.setInt(7, x.getId());

            int rowsUpdated = cmd.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int ID) {
        String sqlDelete = "DELETE FROM Product WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlDelete);

            cmd.setInt(1, ID);

            int rowsDeleted = cmd.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<ProductDTO> selectAll() {
        ArrayList<ProductDTO> listProduct = new ArrayList<ProductDTO>();
        CategoryDAL dalCategory = new CategoryDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectProduct = "SELECT * FROM Product";
            ResultSet rs = stmt.executeQuery(sqlSelectProduct);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String thumbnail = rs.getString("thumbnail");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String unit = rs.getString("unit");
                int idCategory = rs.getInt("idCategory");
                
                ProductDTO product = new ProductDTO(id, name, thumbnail, description, price, unit, dalCategory.findById(idCategory));
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listProduct;
    }
    
    public ProductDTO findByName(String productName)
    {
    	CategoryDAL dalCategory = new CategoryDAL();
    	ProductDTO product = null;
		String sqlFindByName = "SELECT * FROM Product WHERE name Like ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByName);
			
			cmd.setString(1, "%" + productName + "%");
			ResultSet rs = cmd.executeQuery();
			
			while(rs.next())
			{
				int productId = rs.getInt("id");
				String productNamee = rs.getString("name");
				String productThumbnail = rs.getString("thumbnail");
				String productDescription = rs.getString("description");
				float productPrice = rs.getFloat("price");
				String productUnit = rs.getString("unit");
				int productIdCategory = rs.getInt("idCategory");
				
				product = new ProductDTO(productId, productNamee, productThumbnail, productDescription, productPrice, productUnit, dalCategory.findById(productIdCategory));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return product;
    }
    
    public ProductDTO findById(int ID)
	{
		ProductDTO product = null;
		CategoryDAL dalCategory = new CategoryDAL();
		String sqlFindByID = "SELECT * FROM Product WHERE id = ?";
		try {
			PreparedStatement cmd = connectDB.prepareStatement(sqlFindByID);
			cmd.setInt(1, ID);
			ResultSet rs = cmd.executeQuery();
			while(rs.next())
			{
				int productId = rs.getInt("id");
				String productNamee = rs.getString("name");
				String productThumbnail = rs.getString("thumbnail");
				String productDescription = rs.getString("description");
				float productPrice = rs.getFloat("price");
				String productUnit = rs.getString("unit");
				int productIdCategory = rs.getInt("idCategory");
                
				product = new ProductDTO(productId, productNamee, productThumbnail, productDescription, productPrice, productUnit, dalCategory.findById(productIdCategory));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
		
	}
    
    public ArrayList<ProductDTO> listProductFindByName(String name) {
        CategoryDAL dalCategory = new CategoryDAL();
        ArrayList<ProductDTO> listProduct = new ArrayList<>();
        String sqlSelectProduct = "SELECT * FROM Product p INNER JOIN Category c ON c.id = p.idCategory WHERE c.name = ?";

        try (PreparedStatement cmd = connectDB.prepareStatement(sqlSelectProduct)) {
            cmd.setString(1, name);
            try (ResultSet rs = cmd.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("id");
                    String productName = rs.getString("name");
                    String productThumbnail = rs.getString("thumbnail");
                    String productDescription = rs.getString("description");
                    float productPrice = rs.getFloat("price");
                    String productUnit = rs.getString("unit");
                    int productIdCategory = rs.getInt("idCategory");

                    ProductDTO product = new ProductDTO(
                        productId,
                        productName,
                        productThumbnail,
                        productDescription,
                        productPrice,
                        productUnit,
                        dalCategory.findById(productIdCategory)
                    );
                    listProduct.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listProduct;
    }
    
    public ArrayList<ProductDTO> listProductFindByString(String name) {
        CategoryDAL dalCategory = new CategoryDAL();
        ArrayList<ProductDTO> listProduct = new ArrayList<>();
        String sqlSelectProduct = "SELECT * FROM Product where name like ? ";

        try (PreparedStatement cmd = connectDB.prepareStatement(sqlSelectProduct)) {
        	cmd.setString(1, name + " %");
            try (ResultSet rs = cmd.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("id");
                    String productName = rs.getString("name");
                    String productThumbnail = rs.getString("thumbnail");
                    String productDescription = rs.getString("description");
                    float productPrice = rs.getFloat("price");
                    String productUnit = rs.getString("unit");
                    int productIdCategory = rs.getInt("idCategory");

                    ProductDTO product = new ProductDTO(
                        productId,
                        productName,
                        productThumbnail,
                        productDescription,
                        productPrice,
                        productUnit,
                        dalCategory.findById(productIdCategory)
                    );
                    listProduct.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listProduct;
    }
}