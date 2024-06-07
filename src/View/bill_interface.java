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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DAL.ConnectJDBC;
import DAL.InvoicesDAL;
import DAL.OrderDetailDAL;
import DTO.EmployeeDTO;
import DTO.OrderDetailDTO;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JEditorPane;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;

import javax.swing.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class bill_interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_soBan;
	JTable table;

	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	static String total;
	
	Connection con = ConnectJDBC.openConnection();
	
	
	public bill_interface(String username, String fullname, String access) {
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
					bill_interface frame = new bill_interface(userName,fullName,access);
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
					if(access.equals("admin"))
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
					if(access.equals("admin"))
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
					if(access.equals("admin"))
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
					if(access.equals("admin"))
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
					if(access.equals("admin"))
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(221, 255, 255));
		panel_1.setBounds(681, 96, 385, 188);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_8 = new JLabel("Thông tin hóa đơn ");
		lblNewLabel_8.setBounds(122, 10, 135, 20);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(lblNewLabel_8);
		
		JButton btnNewButton_8 = new JButton("Tính tiền ");
		btnNewButton_8.setBounds(10, 81, 112, 39);
		btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton_8.setBackground(new Color(201, 156, 112));
		panel_1.add(btnNewButton_8);
		
		JLabel lblNewLabel_Total = new JLabel();
		lblNewLabel_Total.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_Total.setBounds(160, 81, 170, 39);
		panel_1.add(lblNewLabel_Total);
		
		btnNewButton_8.addActionListener(new ActionListener() {
			//STring

			@Override
			public void actionPerformed(ActionEvent e) {
				
				InvoicesDAL dalInvoices = new  InvoicesDAL();
				total = dalInvoices.findByIdTable().getCalculateMoney() + "";
				 lblNewLabel_Total.setText(total);
			}
			
		});
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(232, 255, 255));
		panel_2.setBounds(681, 294, 385, 180);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_11 = new JLabel("Lập hóa đơn ");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_11.setBounds(128, 10, 179, 34);
		panel_2.add(lblNewLabel_11);
		
		JButton btnNewButton_9 = new JButton("");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  Connection con = null;
	                try {
	                    // Tải trình điều khiển JDBC (kết nối giả lập cho ví dụ này)
	                	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        			String url ="jdbc:sqlserver://MSI:1433;databaseName=QuanLyQuanCoffeeBTL;trustServerCertificate=true";
	        			String userName ="sa";
	        			String password ="13012004";
	        			 con = DriverManager.getConnection(url , userName , password);

	                    // Tải tệp JRXML
	                    File file = new File("C:\\Users\\User\\JaspersoftWorkspace\\MyReports\\Bill.jrxml");
	                    JasperDesign jdesign = JRXmlLoader.load(file);

	                    // Biên dịch báo cáo
	                    JasperReport jreport = JasperCompileManager.compileReport(jdesign);

	                    // Điền dữ liệu vào báo cáo
	                    JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con);

	                    // Xem báo cáo
	                    JasperViewer.viewReport(jprint, false);

	                } catch (Exception e1) {
	                    e1.printStackTrace();
	                } finally {
	                    try {
	                        if (con != null && !con.isClosed()) {
	                            con.close();
	                        }
	                    } catch (Exception e2) {
	                        e2.printStackTrace();
	                    }
	                }
	            }
	        });

		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_9.setBackground(new Color(128, 255, 255));
		btnNewButton_9.setIcon(new ImageIcon(bill_interface.class.getResource("/image/Ecommerce-Bill-icon.png")));
		btnNewButton_9.setBounds(95, 54, 179, 90);
		panel_2.add(btnNewButton_9);
		
		JLabel lblNewLabel = new JLabel("Xem Hóa Đơn ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(70, 44, 122, 36);
		contentPane.add(lblNewLabel);
		JButton btnNewButton_10 = new JButton("");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			            updateTableWithProducts();

			}
		});
		btnNewButton_10.setIcon(new ImageIcon(bill_interface.class.getResource("/image/Glass.png")));
		btnNewButton_10.setBounds(189, 43, 57, 37);
		contentPane.add(btnNewButton_10);
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID Sản Phẩm", "Tên Sản Phẩm", "Giá", "Số Lượng"}, 0);
		 table = new JTable(tableModel);
		 table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 table.setBackground(new Color(232, 255, 255));	 
	     JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 89, 600, 400);
		contentPane.add(scrollPane);
		
		
	}
	
	private void updateTableWithProducts() {
		OrderDetailDAL orderDetail = new OrderDetailDAL();
	    List<OrderDetailDTO> orderDetails = orderDetail.findByIdTable();
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // Clear existing rows

	    for (OrderDetailDTO detail : orderDetails) {
	        model.addRow(new Object[]{
	            detail.getIdProduct().getId(),
	            detail.getIdProduct().getName(),
	            detail.getIdProduct().getPrice(),
	            detail.getQuantity()
	        });
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
	
//	  public int getCurrentOrderId() {
//	        return currentOrderId;
//	    }
//	
	
	private static ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            // Tải hình ảnh từ resource
            InputStream imgStream = bill_interface.class.getResourceAsStream(path);
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
