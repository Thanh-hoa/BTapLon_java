package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAL.ConnectJDBC;
import DAL.EmployeeDAL;
import DAL.ShiftsDAL;
import DTO.EmployeeDTO;
import DTO.ShiftsDTO;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;


public class assignment_interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	
	
	JComboBox comboBox_CV = new JComboBox();
	JComboBox comboBox_ca = new JComboBox();
	JComboBox<String> comboBox_NV = new JComboBox<>();
	
	Connection con = ConnectJDBC.openConnection();
	
	
	public assignment_interface(String username, String fullname, String access) {
	    this.userName = username;
	    this.fullName = fullname;
	    this.access = access;
	  
	    EmployeeDTO empl = findEmployeeByUsername(username);
	    if (empl != null) {
	        this.id = String.valueOf(empl.getId());
	    }
	    IsMain();
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					assignment_interface frame = new assignment_interface(userName , fullName , access);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	  public void IsMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1106, 591);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(4, 8, 6, 7));
		setResizable(false);
		URL url = loginGUI.class.getResource("/image/Coffee-icon.png");
		Image img = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(img);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Chương Trình Quản Lý Bán Coffee");
		JButton btnNewButton = new JButton("Gọi Món");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(0, 128, 255));
		btnNewButton.setBounds(0, 0, 138, 33);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new order_Interface(userName , fullName , access).setVisible(true);
				    dispose();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		contentPane.add(btnNewButton);
		
		
		
		JButton btnNewButton_2 = new JButton("Lập Hóa Đơn");
		btnNewButton_2.setForeground(new Color(0, 0, 0));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(0, 128, 255));
		btnNewButton_2.setBounds(139, 0, 157, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new bill_interface(userName, fullName , access).setVisible(true);
					  dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				
				}
			}
		});
		contentPane.add(btnNewButton_2);
		
		
		
		JButton btnNewButton_3 = new JButton("Nhân Viên ");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_3.setBackground(new Color(0, 128, 255));
		btnNewButton_3.setBounds(475, 0, 157, 33);
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(access.equals("Admin"))
					{
						new employee_interface(userName, fullName , access).setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			
		});
		contentPane.add(btnNewButton_3);
		
		
		
		JButton btnNewButton_4 = new JButton("Phân Công");
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_4.setBackground(new Color(0, 128, 255));
		btnNewButton_4.setBounds(632, 0, 157, 33);
		btnNewButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(access.equals("Admin"))
					{
						new assignment_interface(userName, fullName , access).setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			
		});
		contentPane.add(btnNewButton_4);
		
		
		
		JButton btnNewButton_5 = new JButton("Thực Đơn");
		btnNewButton_5.setBackground(new Color(0, 128, 255));
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_5.setBounds(789, 0, 146, 33);
		btnNewButton_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(access.equals("Admin"))
					{
						new menu_interface(userName, fullName , access).setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
				    e2.printStackTrace();
				}
				
			}
			
		});
		contentPane.add(btnNewButton_5);
		
		
		
		JButton btnNewButton_6 = new JButton("Thông Kê");
		btnNewButton_6.setBackground(new Color(0, 128, 255));
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_6.setBounds(935, 0, 157, 33);
		btnNewButton_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(access.equals("Admin"))
					{
						new statistics_interface(userName, fullName , access).setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
			
		});
		contentPane.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Quản Lý Hóa Đơn");
		btnNewButton_7.setBackground(new Color(0, 128, 255));
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_7.setBounds(294, 0, 180, 33);
		btnNewButton_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(access.equals("Admin"))
					{
						new order_Management_Interface(userName, fullName , access).setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		});
		contentPane.add(btnNewButton_7);
		
		
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Tên Đăng Nhập : ");
		ImageIcon icon1 = createResizedImageIcon("/image/people-icon.png", 30, 30);
		lblNewLabel_2.setIcon(icon1);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(10, 500, 200, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_14 = new JLabel(userName);
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_14.setBounds(189, 500, 151, 33);
		contentPane.add(lblNewLabel_14);
		
		JLabel lblNewLabel_3 = new JLabel("Họ tên : ");
		ImageIcon icon2 = createResizedImageIcon("/image/people-sharp-icon.png", 30, 30);
		lblNewLabel_3.setIcon(icon2);
		lblNewLabel_3.setBackground(new Color(240, 240, 240));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(350, 500, 150, 30);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_15 = new JLabel(fullName);
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_15.setBounds(504, 500, 242, 30);
		contentPane.add(lblNewLabel_15);
		
		JLabel lblNewLabel_4 = new JLabel("Quyền : ");
		ImageIcon icon3 = createResizedImageIcon("/image/Action-arrow-icon.png" , 30 ,30);
	    lblNewLabel_4.setIcon(icon3);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_4.setBounds(721, 500, 150, 30);
		contentPane.add(lblNewLabel_4);
		JLabel lblNewLabel_16 = new JLabel(access);
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_16.setBounds(865, 500, 109, 33);
		contentPane.add(lblNewLabel_16);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 43, 1072, 104);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Thông Tin Nhân Viên");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel_1.setBounds(407, -3, 253, 35);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel("Nhân Viên : ");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(22, 39, 79, 35);
		panel.add(lblNewLabel_5);

		
		JLabel lblNewLabel = new JLabel("Ca : ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(372, 42, 58, 29);
		panel.add(lblNewLabel);
		
		JLabel lblBn = new JLabel("Công Việc :");
		lblBn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblBn.setBounds(851, 32, 96, 49);
		panel.add(lblBn);
		
		
		comboBox_ca.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_ca.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_ca.setBounds(407, 42, 79, 32);
		panel.add(comboBox_ca);

		comboBox_CV.setModel(new DefaultComboBoxModel(new String[] {"Pha Chế ", "Thu Ngân", "Phục Vụ "}));
		comboBox_CV.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_CV.setBounds(936, 38, 115, 35);
		panel.add(comboBox_CV);
		
		JComboBox<String> comboBox_NV = new JComboBox<>();
		comboBox_NV.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_NV.setBounds(101, 39, 234, 35);
		panel.add(comboBox_NV);
		EmployeeDAL employeeDAL = new EmployeeDAL();
		ArrayList<String> employeeNames = employeeDAL.selectTen();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(employeeNames.toArray(new String[0]));
        comboBox_NV.setModel(model);
        
        JLabel lblNewLabel_7 = new JLabel("Ngày Làm :");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_7.setBounds(523, 42, 96, 32);
        panel.add(lblNewLabel_7);
        
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(607, 42, 186, 32);
        panel.add(dateChooser);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 157, 899, 333);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_8 = new JLabel("Danh Sách Phân Công");
		lblNewLabel_8.setForeground(new Color(255, 0, 0));
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_8.setBounds(386, 0, 241, 26);
		panel_2.add(lblNewLabel_8);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 36, 854, 287);
        panel_2.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
			   "STT","Ca", "Tên Nhân Viên","Ngày Làm", "Công việc"
			}
		));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(919, 157, 163, 333);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewButton_8 = new JButton("Thêm ");
		btnNewButton_8.setForeground(new Color(255, 0, 0));
		btnNewButton_8.setBackground(new Color(226, 223, 199));
		btnNewButton_8.setIcon(new ImageIcon(assignment_interface.class.getResource("/image/user-add-icon.png")));
		btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_8.setBounds(10, 142, 143, 81);
		panel_3.add(btnNewButton_8);
		
		btnNewButton_8.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				String name = comboBox_NV.getSelectedItem().toString();
				String ca = comboBox_ca.getSelectedItem().toString();
				
				Date ngayLam = new Date( dateChooser.getDate().getTime());
				String cv = comboBox_CV.getSelectedItem().toString();
				
				Time startTime = null;
	            Time endTime = null;
		            if (ca.equals("1")) {
		                startTime = Time.valueOf("07:00:00");
		                endTime = Time.valueOf("12:00:00");
		            } else if (ca.equals("2")) {
		                startTime = Time.valueOf("12:00:00");
		                endTime = Time.valueOf("18:00:00");
		            } else if (ca.equals("3")) {
		                startTime = Time.valueOf("18:00:00");
		                endTime = Time.valueOf("23:00:00");

		            }
		            EmployeeDAL dalEmployee = new EmployeeDAL();
		            EmployeeDTO employee = dalEmployee.findByFullName(name);

		            ShiftsDTO shift = new ShiftsDTO(employee, cv, ngayLam, startTime, endTime);

		            ShiftsDAL shiftsDAL = new ShiftsDAL();
		            boolean success = shiftsDAL.insertShift(shift);

		            if (success) {
		                JOptionPane.showMessageDialog(null, "Thêm thành công ca làm việc.");
		                updateTableData();
		               
		            } else {
		                JOptionPane.showMessageDialog(null, "Thêm không thành công. Vui lòng thử lại.");
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
		        }
		    }

		});
		
		

		JButton btnNewButton_10 = new JButton("Xóa ");
		btnNewButton_10.setForeground(new Color(255, 0, 0));
		btnNewButton_10.setBackground(new Color(226, 223, 199));
		btnNewButton_10.setIcon(new ImageIcon(assignment_interface.class.getResource("/image/remove-icon.png")));
		btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_10.setBounds(10, 242, 143, 81);
		panel_3.add(btnNewButton_10);
		
		btnNewButton_10.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectRow = table.getSelectedRow();
		        if (selectRow != -1) { // Sửa thành -1, không phải 1
		            int ID = Integer.parseInt(table.getValueAt(selectRow, 0).toString());
		            // Lấy ID từ cột đầu tiên của hàng được chọn

		            ShiftsDAL shiftsDAL = new ShiftsDAL();
		            boolean success = shiftsDAL.deleteShifts(ID);

		            if (success) {
		                JOptionPane.showMessageDialog(null, "Xóa thành công.");
		                updateTableData(); // Cập nhật bảng sau khi xóa
		            } else {
		                JOptionPane.showMessageDialog(null, "Xóa không thành công.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.");
		        }
		    }

		});

		
		JLabel lblNewLabel_6 = new JLabel("Xử Lý");
		lblNewLabel_6.setForeground(new Color(255, 0, 0));
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_6.setBounds(55, 10, 78, 23);
		panel_3.add(lblNewLabel_6);
		
		JButton btnNewButton_9 = new JButton("Danh Sách PC ");
		btnNewButton_9.setForeground(new Color(255, 0, 0));
		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTableData();
			}
		});
		btnNewButton_9.setBounds(10, 49, 143, 81);
		panel_3.add(btnNewButton_9);
	}
	  
		
		
		private void updateTableData() {
		    ShiftsDAL shiftsDAL = new ShiftsDAL();
		    ArrayList<ShiftsDTO> shiftsList = shiftsDAL.selectAll();

		    DefaultTableModel model = (DefaultTableModel) table.getModel();
		    model.setRowCount(0); // Xóa tất cả các dòng hiện có

		    for (ShiftsDTO shift : shiftsList) {
		        model.addRow(new Object[]{
		        		shift.getId(),
		                getShiftTimeSlot(shift.getStartTime(), shift.getEndTime()),
		                shift.getIdEmployee().getFullname(),
		                shift.getWorkDate(),
		                shift.getJob()
		        });
		    }
		}
		private String getShiftTimeSlot(Time startTime, Time endTime) {
		    if (startTime != null && endTime != null) {
		        if (startTime.equals(Time.valueOf("07:00:00")) && endTime.equals(Time.valueOf("12:00:00"))) {
		            return "1";
		        } else if (startTime.equals(Time.valueOf("12:00:00")) && endTime.equals(Time.valueOf("18:00:00"))) {
		            return "2";
		        } else if (startTime.equals(Time.valueOf("18:00:00")) && endTime.equals(Time.valueOf("23:00:00"))) {
		            return "3";
		        }
		    }
		    return "";
		}


			
		
		
	
	public EmployeeDTO findEmployeeByUsername(String username) {
	    EmployeeDTO employee = null;
	    
	    String sqlFindByUsername = "SELECT * FROM Employee WHERE username = ?";
	    try {
	        PreparedStatement cmd = con.prepareStatement(sqlFindByUsername);
	        cmd.setString(1, username);
	        ResultSet rs = cmd.executeQuery();
	        
	        if (rs.next()) {
	            int employeeID = rs.getInt("id");
	            String fullname = rs.getString("fullname");
	           
	            employee = new EmployeeDTO(employeeID, fullname); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return employee;
	}
	
	 
	
	private static ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            // Tải hình ảnh từ resource
            InputStream imgStream = assignment_interface.class.getResourceAsStream(path);
            BufferedImage img = ImageIO.read(imgStream);
            
            // Thay đổi kích thước của hình ảnh
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            
            // Tạo ImageIcon từ hình ảnh đã thay đổi kích thước
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
