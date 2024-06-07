package DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import DTO.ShiftsDTO;

public class ShiftsDAL {
    Connection connectDB = ConnectJDBC.openConnection();

    public boolean insertShift(ShiftsDTO x) {
        String sqlInsert = "INSERT INTO Shifts (idEmployee, job, workDate, startTime, endTime) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

            cmd.setInt(1, x.getIdEmployee().getId());
            cmd.setString(2, x.getJob());
            cmd.setDate(3, x.getWorkDate());
            cmd.setTime(4, x.getStartTime());
            cmd.setTime(5, x.getEndTime());
            int rowsAffected = cmd.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = cmd.getGeneratedKeys();
                if (generatedKeys.next()) {
                    x.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateShifts(ShiftsDTO x) {
        String sqlUpdate = "UPDATE Shifts SET idEmployee = ?, job = ?, workDate = ?, startTime = ?, endTime = ?? WHERE id = ?";
        try {
            PreparedStatement cmd = connectDB.prepareStatement(sqlUpdate);

            cmd.setInt(1, x.getIdEmployee().getId());
            cmd.setString(2, x.getJob());
            cmd.setDate(3, x.getWorkDate());
            cmd.setTime(4, x.getStartTime());
            cmd.setTime(5, x.getEndTime());
            cmd.setInt(6, x.getId());

            int rowsUpdated = cmd.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteShifts(int ID) {
        String sqlDelete = "DELETE FROM Shifts WHERE id = ?";
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

    public ArrayList<ShiftsDTO> selectAll() {
        ArrayList<ShiftsDTO> listShifts = new ArrayList<ShiftsDTO>();
        EmployeeDAL dalEmployee = new EmployeeDAL();
        try {
            Statement stmt = connectDB.createStatement();
            String sqlSelectShifts = "SELECT * FROM Shifts";
            ResultSet rs = stmt.executeQuery(sqlSelectShifts);
            while (rs.next()) {
                int id = rs.getInt("id");
                int idEmployee = rs.getInt("idEmployee");
                String job = rs.getString("job");
                Date workDate = rs.getDate("workDate");
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                ShiftsDTO shift = new ShiftsDTO(id, dalEmployee.findByID(idEmployee), job, workDate, startTime, endTime);
                listShifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listShifts;
    }
    
	public ShiftsDTO findById(int ID) {
	      ShiftsDTO shift = null;
	      String sqlFindByID = "SELECT * FROM Shifts WHERE id = ?";
	      EmployeeDAL dalEmployee = new EmployeeDAL();
	      try {
	         PreparedStatement cmd = this.connectDB.prepareStatement(sqlFindByID);
	         cmd.setInt(1, ID);
	         ResultSet rs = cmd.executeQuery();
				while(rs.next())
				{
					int id = rs.getInt("id");
	                int idEmployee = rs.getInt("idEmployee");
	                String job = rs.getString("job");
	                Date workDate = rs.getDate("workDate");
	                Time startTime = rs.getTime("startTime");
	                Time endTime = rs.getTime("endTime");
	                
	                shift = new ShiftsDTO(id, dalEmployee.findByID(idEmployee), job, workDate, startTime, endTime);
					
				}
	         
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }

	      return shift;
	   }
}