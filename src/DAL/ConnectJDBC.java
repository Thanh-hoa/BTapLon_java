package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {
	public static Connection openConnection()
	{
		Connection c =null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url ="jdbc:sqlserver://MSI:1433;databaseName=QuanLyQuanCoffeeBTL;trustServerCertificate=true";
			String userName ="sa";
			String password ="13012004";
			 c = DriverManager.getConnection(url , userName , password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return c;
	  }
	
	public static void closeConnection(Connection c) {
		try {
			if(c!=null) {
				c.close();			}
		}catch (Exception e) {
			e.printStackTrace();

			}
	}
}

	
	
