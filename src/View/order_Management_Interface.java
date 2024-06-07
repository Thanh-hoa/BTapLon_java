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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAL.ConnectJDBC;
import DAL.InvoicesDAL;
import DAL.OrderDetailDAL;
import DAL.OrdersDAL;
import DTO.EmployeeDTO;
import DTO.OrderDetailDTO;
import DTO.OrdersDTO;

import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class order_Management_Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	private JTable table_1;
	private JTable table_4;

	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	
	Connection con = ConnectJDBC.openConnection();
	
	
	public order_Management_Interface(String username, String fullname, String access) {
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
					order_Management_Interface frame = new order_Management_Interface(userName , fullName , access);
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
		panel.setBackground(new Color(255, 255, 208));
		panel.setBounds(10, 43, 296, 119);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tra cứu theo ngày ");
		lblNewLabel.setBackground(new Color(176, 216, 255));
		lblNewLabel.setForeground(new Color(128, 64, 64));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(71, 0, 170, 28);
		panel.add(lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(20, 65, 111, 28);
		panel.add(dateChooser);
		
		JLabel lblNewLabel_6 = new JLabel("Chọn ngày : ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(10, 36, 96, 19);
		panel.add(lblNewLabel_6);
		
		JButton btnNewButton_8 = new JButton("Tìm ");
		btnNewButton_8.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Date day = dateChooser.getDate();
		        displayOrdersByDate(day);
		       
		    }
		});


		btnNewButton_8.setBackground(new Color(255, 184, 113));
		btnNewButton_8.setForeground(new Color(0, 128, 255));
		btnNewButton_8.setIcon(new ImageIcon(order_Management_Interface.class.getResource("/image/Glass.png")));
		btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_8.setBounds(163, 49, 123, 44);
		panel.add(btnNewButton_8);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 208));
		panel_1.setBounds(316, 43, 296, 119);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Tra cứu theo tháng năm ");
		lblNewLabel_1.setForeground(new Color(128, 64, 64));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(35, 0, 209, 40);
		panel_1.add(lblNewLabel_1);
		
		
		
		JLabel lblNewLabel_7 = new JLabel("Tháng:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(10, 31, 67, 29);
		panel_1.add(lblNewLabel_7);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_1.setBounds(66, 38, 50, 23);
		panel_1.add(comboBox_1);
		
		JLabel lblNewLabel_8 = new JLabel("Năm :");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(154, 31, 64, 29);
		panel_1.add(lblNewLabel_8);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(202, 35, 67, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_8_1 = new JButton("Tìm ");
		btnNewButton_8_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int day = comboBox_1.getSelectedIndex() + 1;
				int year = Integer.parseInt(textField.getText());
				displayOrdersByMonthAndYear(day, year);
			}
		});
		btnNewButton_8_1.setBackground(new Color(255, 184, 113));
		btnNewButton_8_1.setIcon(new ImageIcon(order_Management_Interface.class.getResource("/image/Glass.png")));
		btnNewButton_8_1.setBounds(66, 66, 126, 43);
		panel_1.add(btnNewButton_8_1);
		btnNewButton_8_1.setForeground(new Color(0, 128, 255));
		btnNewButton_8_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 208));
		panel_2.setBounds(629, 43, 453, 119);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Tra cứu theo khoảng ngày ");
		lblNewLabel_5.setForeground(new Color(128, 64, 64));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_5.setBounds(117, 0, 256, 42);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_9 = new JLabel("Từ:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_9.setBounds(10, 32, 45, 26);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Đến:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(202, 34, 65, 22);
		panel_2.add(lblNewLabel_10);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(43, 32, 111, 26);
		panel_2.add(dateChooser_1);
		
		JDateChooser dateChooser_1_1 = new JDateChooser();
		dateChooser_1_1.setBounds(264, 39, 111, 19);
		panel_2.add(dateChooser_1_1);
		
		JButton btnNewButton_8_1_1 = new JButton("Tìm ");
		btnNewButton_8_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date day1 = dateChooser_1.getDate();
				Date day2 = dateChooser_1_1.getDate();
				displayOrdersByDateToDate(day1, day2);
			}
		});
		btnNewButton_8_1_1.setBackground(new Color(255, 184, 113));
		btnNewButton_8_1_1.setIcon(new ImageIcon(order_Management_Interface.class.getResource("/image/Glass.png")));
		btnNewButton_8_1_1.setForeground(new Color(0, 128, 255));
		btnNewButton_8_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_8_1_1.setBounds(80, 66, 126, 43);
		panel_2.add(btnNewButton_8_1_1);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(36, 264, 364, 0);
		contentPane.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(198, 255, 255));
		panel_4.setBounds(10, 172, 602, 318);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		
		
		JLabel lblNewLabel_11 = new JLabel("Danh Sách  Hóa Đơn ");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(213, 0, 192, 34);
		panel_4.add(lblNewLabel_11);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 38, 602, 284);
		panel_4.add(scrollPane);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            // Lấy chỉ số dòng của dòng được chọn
	            int selectedRow = table_1.getSelectedRow();
	            if (selectedRow != -1) {
	                int orderId = (int) table_1.getValueAt(selectedRow, 0);
	                displayOrderDetail(orderId);
	            }
	        }
	    });
		
		
		table_1.setForeground(new Color(0, 0, 0));
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null,null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"Số HD", "TG Lập", "MS Bàn", "Số Ghế", "Người Lập", "Phục Vụ", "Tổng Tiền"
			}
		));
		scrollPane.setViewportView(table_1);
		
	
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(198, 255, 255));
		panel_5.setBounds(636, 166, 456, 242);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblNewLabel_12 = new JLabel("Chi Tiết Hóa Đơn");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(156, 0, 130, 36);
		panel_5.add(lblNewLabel_12);
	
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 46, 423, 175);
		panel_5.add(scrollPane_1);
		
		table_4 = new JTable();
		scrollPane_1.setViewportView(table_4);
		table_4.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Tên TĐ", "Đơn Giá", "Số Lượng"
			}
		));
	}
	
	private void displayOrdersByDate(Date selectedDate) {
	    if (selectedDate != null) {
	        OrdersDAL dalOrder = new OrdersDAL();
	        InvoicesDAL dalInvoice = new InvoicesDAL();
	        DefaultTableModel model = (DefaultTableModel) table_1.getModel();
	        model.setRowCount(0);
	        
	        // Lấy danh sách các hóa đơn dựa trên ngày đã chọn
	        for (OrdersDTO order : dalOrder.listFindByDate(selectedDate)) {
	            // Thêm thông tin của hóa đơn vào bảng
	            model.addRow(new Object[]{
	                order.getId(),
	                order.getOrderTime(),
	                order.getIdTable().getId(),
	                order.getIdTable().getSeats(),
	                order.getIdEmployee().getFullname(),
	                order.getIdEmployee().getFullname(),
	                dalInvoice.findById(order.getId()).getCalculateMoney()
	            });
	        }
	    } else {
	        // Xử lý khi người dùng chưa chọn ngày
	        System.out.println("Chưa chọn ngày!");
	    }
	}
	private void displayOrdersByDateToDate(Date day1, Date day2) {
	    if (day1 != null && day2 != null) {
	        OrdersDAL dalOrder = new OrdersDAL();
	        InvoicesDAL dalInvoice = new InvoicesDAL();
	        DefaultTableModel model = (DefaultTableModel) table_1.getModel();
	        model.setRowCount(0);
	        
	        // Lấy danh sách các hóa đơn dựa trên ngày đã chọn
	        for (OrdersDTO order : dalOrder.listFindByDateToDate(day1, day2)) {
	            // Thêm thông tin của hóa đơn vào bảng
	            model.addRow(new Object[]{
	                order.getId(),
	                order.getOrderTime(),
	                order.getIdTable().getId(),
	                order.getIdTable().getSeats(),
	                order.getIdEmployee().getFullname(),
	                order.getIdEmployee().getFullname(),
	                dalInvoice.findById(order.getId()).getCalculateMoney()
	            });
	        }
	    } else {
	        // Xử lý khi người dùng chưa chọn ngày
	        System.out.println("Chưa chọn ngày!");
	    }
	}
	private void displayOrdersByMonthAndYear(int day, int year) {
	    if (day > 0 && year >= 1900) {
	        OrdersDAL dalOrder = new OrdersDAL();
	        InvoicesDAL dalInvoice = new InvoicesDAL();
	        DefaultTableModel model = (DefaultTableModel) table_1.getModel();
	        model.setRowCount(0);
	        
	        // Lấy danh sách các hóa đơn dựa trên ngày đã chọn
	        for (OrdersDTO order : dalOrder.listFindByMonthAndYear(day, year)) {
	            // Thêm thông tin của hóa đơn vào bảng
	            model.addRow(new Object[]{
	                order.getId(),
	                order.getOrderTime(),
	                order.getIdTable().getId(),
	                order.getIdTable().getSeats(),
	                order.getIdEmployee().getFullname(),
	                order.getIdEmployee().getFullname(),
	                dalInvoice.findById(order.getId()).getCalculateMoney()
	            });
	        }
	    } else {
	        // Xử lý khi người dùng chưa chọn ngày
	        System.out.println("Chưa chọn ngày!");
	    }
	}
	private void displayOrderDetail(int id) {
	    if (id >= 1) {
	        OrdersDAL dalOrder = new OrdersDAL();
	        OrderDetailDAL dalOrderDetail = new OrderDetailDAL();
	        InvoicesDAL dalInvoice = new InvoicesDAL();
	        DefaultTableModel model = (DefaultTableModel) table_4.getModel();
	        model.setRowCount(0);
	        
	        // Lấy danh sách các hóa đơn dựa trên ngày đã chọn
	        for(OrderDetailDTO x : dalOrderDetail.findByIdOrder(id))
	        {
	        	model.addRow(new Object[]{
		                x.getIdProduct().getName(),
		                x.getIdProduct().getPrice(),
		                x.getQuantity()
		            });
	        }
	            // Thêm thông tin của hóa đơn vào bảng 		
	        
	    } else {
	        // Xử lý khi người dùng chưa chọn ngày
	        System.out.println("Chưa chọn ngày!");
	    }
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
	            // Add other fields as necessary
	            employee = new EmployeeDTO(employeeID, fullname); // Modify constructor as per your EmployeeDTO
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return employee;
	}
	

	private static ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            // Tải hình ảnh từ resource
            InputStream imgStream = order_Management_Interface.class.getResourceAsStream(path);
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
