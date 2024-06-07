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
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
import DTO.EmployeeDTO;


import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;


public class employee_interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_hoVaTen;
	private JTextField textField_tenDangNhap;
	private JTextField textField_MK;
	private JTextField textField_hoVaTenTC;
	private JTable table = null;
	
	 ArrayList<EmployeeDTO> list= null;
	 JPanel panel_2 = new JPanel();
	
	

	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	
	Connection con = ConnectJDBC.openConnection();
	private JTable table_1;
	private JTextField textField_CCCD;
	
	
	public employee_interface(String username, String fullname, String access) {
	    this.userName = username;
	    this.fullName = fullname;
	    this.access = access;
	  
	    EmployeeDTO empl = findEmployeeByUsername(fullname);
	    if (empl != null) {
	        this.id = String.valueOf(empl.getId());
	    }
	    IsMain();
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employee_interface frame = new employee_interface(userName , fullName , access);
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
		panel.setBounds(10, 43, 686, 159);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Thông Tin Nhân Viên");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(234, 0, 253, 35);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel("Họ và tên nhân viên: ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(22, 39, 147, 35);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Tên đăng nhập:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(22, 73, 129, 28);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Mật Khẩu:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(22, 111, 112, 28);
		panel.add(lblNewLabel_7);
		
		textField_hoVaTen = new JTextField();
		textField_hoVaTen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_hoVaTen.setBounds(161, 45, 177, 29);
		panel.add(textField_hoVaTen);
		textField_hoVaTen.setColumns(10);
		
		textField_tenDangNhap = new JTextField();
		textField_tenDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_tenDangNhap.setBounds(161, 80, 177, 28);
		panel.add(textField_tenDangNhap);
		textField_tenDangNhap.setColumns(10);
		
		textField_MK = new JTextField();
		textField_MK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_MK.setBounds(161, 118, 177, 31);
		panel.add(textField_MK);
		textField_MK.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Ngày sinh:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_10.setBounds(371, 45, 88, 29);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Quyền:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(371, 79, 73, 28);
		panel.add(lblNewLabel_11);
		
		JComboBox <String> comboBox_1 = new JComboBox();
	    comboBox_1.setEditable(true);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Admin","employee"}));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox_1.setBounds(465, 79, 177, 29);
		comboBox_1.setSelectedIndex(-1);
		panel.add(comboBox_1);
		
		
		panel_2.setBounds(10, 212, 899, 278);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		comboBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel_2.removeAll();
            	panel_2.revalidate();
            	panel_2.repaint();	
            	
            	 String selectedItem = (String) comboBox_1.getSelectedItem();
            	 EmployeeDAL dalEmployee = new EmployeeDAL();
	             ArrayList<EmployeeDTO> list = dalEmployee.findByAccess(selectedItem);
	                    
	                if(list!=null) {
	                	String[] columnNames = {"Mã NV", "CCCD","Họ Tên", "Ngày Sinh", "Tên ĐN", "Quyền"};
	                	Object[][] data  = new Object[list.size()][6];
	                	int i =0; 
	                	for(EmployeeDTO p : list) {
	                		data[i][0] = p.getId();
	                		data[i][1] = p.getIdCard();
	                		data[i][2] = p.getFullname();
	                		data[i][3] = p.getBirthday();
	                		data[i][4] =p.getUserName();
	                		data[i][5] =p.getAccess();
	                		i++;
	                	}
	                	 table = new JTable();         
	                	 table.setModel(new DefaultTableModel(
	                             data,
	                             columnNames
	                     ));
	                     table.setFont(new Font("Tahoma", Font.PLAIN,15));
	                     table.setBounds(10, 25, 879, 243);
	                     JScrollPane scrollPane = new JScrollPane(table);
	             		scrollPane.setBounds(10, 36, 869, 232);
	             		panel_2.add(scrollPane);
	                     
	                     
	                  
	                }  
			}
			
			
		});
		
	
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(465, 45, 177, 29);
		panel.add(dateChooser);
		
		textField_CCCD = new JTextField();
		textField_CCCD.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_CCCD.setBounds(465, 118, 177, 31);
		panel.add(textField_CCCD);
		textField_CCCD.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("CCCD");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(371, 118, 73, 30);
		panel.add(lblNewLabel_12);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(713, 43, 369, 159);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tra cứu");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(152, 0, 78, 23);
		panel_1.add(lblNewLabel);
		
		JButton btnNewButton_11 = new JButton("Tìm");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_2.removeAll();
            	panel_2.revalidate();
            	panel_2.repaint();
				String ten = textField_hoVaTenTC.getText().trim();
				list = list = new EmployeeDAL().findByName(ten);
			       
                if(list!=null) {
                	String[] columnNames = {"Mã NV", "CCCD","Họ Tên", "Ngày Sinh", "Tên ĐN", "Quyền"};
                	Object[][] data  = new Object[list.size()][6];
                	int i =0; 
                	for(EmployeeDTO p : list) {
                		data[i][0] = p.getId();
                		data[i][1] = p.getIdCard();
                		data[i][2] = p.getFullname();
                		data[i][3] = p.getBirthday();
                		data[i][4] =p.getUserName();
                		data[i][5] =p.getAccess();
                		i++;
                	}
                	 table = new JTable();         
                	 table.setModel(new DefaultTableModel(
                             data,
                             columnNames
                     ));
                     table.setFont(new Font("Tahoma", Font.PLAIN,15));
                     table.setBounds(10, 25, 879, 243);
                     JScrollPane scrollPane = new JScrollPane(table);
             		scrollPane.setBounds(10, 36, 869, 232);
             		panel_2.add(scrollPane);
                }
				
				
			}
		});
		btnNewButton_11.setIcon(new ImageIcon(employee_interface.class.getResource("/image/Glass.png")));
		btnNewButton_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_11.setBounds(10, 102, 138, 47);
		panel_1.add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("Danh sách NV");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_2.removeAll();
            	panel_2.revalidate();
            	panel_2.repaint();
				
				list = list = new EmployeeDAL().selectAll();
			       
                if(list!=null) {
                	String[] columnNames = {"Mã NV", "CCCD","Họ Tên", "Ngày Sinh", "Tên ĐN", "Quyền"};
                	Object[][] data  = new Object[list.size()][6];
                	int i =0; 
                	for(EmployeeDTO p : list) {
                		data[i][0] = p.getId();
                		data[i][1] = p.getIdCard();
                		data[i][2] = p.getFullname();
                		data[i][3] = p.getBirthday();
                		data[i][4] =p.getUserName();
                		data[i][5] =p.getAccess();
                		i++;
                	}
                	 table = new JTable();         
                	 table.setModel(new DefaultTableModel(
                             data,
                             columnNames
                     ));
                     table.setFont(new Font("Tahoma", Font.PLAIN,15));
                     table.setBounds(10, 25, 879, 243);
                     JScrollPane scrollPane = new JScrollPane(table);
             		scrollPane.setBounds(10, 36, 869, 232);
             		panel_2.add(scrollPane);
				  }
				
				
			}
		});
		btnNewButton_12.setIcon(new ImageIcon(employee_interface.class.getResource("/image/list-icon.png")));
		btnNewButton_12.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_12.setBounds(161, 102, 179, 47);
		panel_1.add(btnNewButton_12);
		
		JLabel lblNewLabel_9 = new JLabel("Họ và tên:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_9.setBounds(29, 35, 96, 41);
		panel_1.add(lblNewLabel_9);
		
		textField_hoVaTenTC = new JTextField();
		textField_hoVaTenTC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_hoVaTenTC.setBounds(120, 41, 196, 34);
		panel_1.add(textField_hoVaTenTC);
		textField_hoVaTenTC.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Danh sách nhân viên");
		lblNewLabel_8.setForeground(new Color(255, 0, 0));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_8.setBounds(386, 0, 241, 26);
		panel_2.add(lblNewLabel_8);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(919, 212, 163, 278);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		
		
		JButton btnNewButton_8 = new JButton("Thêm NV");
		btnNewButton_8.setForeground(new Color(255, 0, 0));
		btnNewButton_8.setBackground(new Color(226, 223, 199));
		btnNewButton_8.setIcon(new ImageIcon(employee_interface.class.getResource("/image/user-add-icon.png")));
		btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_8.setBounds(9, 10, 150, 80);
		panel_3.add(btnNewButton_8);
		btnNewButton_8.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String fullName = textField_hoVaTen.getText().trim();
				Date ngaySinh =  (Date) dateChooser.getDate();
				String cccd = textField_CCCD.getText().trim();
				String userName = textField_tenDangNhap.getText().trim();
				String matKhau = textField_MK.getText().trim();
				String access = (String) comboBox_1.getSelectedItem();				 
				   if (fullName.isEmpty() || userName.isEmpty() || matKhau.isEmpty() || access.isEmpty() ||cccd.isEmpty()) {
			            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
			            return;
			        }
				    EmployeeDTO employeeDTO = new EmployeeDTO(cccd ,fullName, ngaySinh, userName, matKhau, access);
			        EmployeeDAL employeeDAL = new EmployeeDAL();
			        boolean success = employeeDAL.insertEmployee(employeeDTO);
			        if(success==true) {
			        	JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công.");
			            textField_hoVaTen.setText("");
		                textField_CCCD.setText("");
		                textField_tenDangNhap.setText("");
		                textField_MK.setText("");
			        }else{
			        	JOptionPane.showMessageDialog(null, "Thêm nhân viên không thành công.");
			        	
			        }
			}
			
		});

		JButton btnNewButton_9 = new JButton("Sửa NV");
		btnNewButton_9.setForeground(new Color(255, 0, 0));
		btnNewButton_9.setBackground(new Color(226, 223, 199));
		btnNewButton_9.setIcon(new ImageIcon(employee_interface.class.getResource("/image/user-edit-icon.png")));
		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_9.setBounds(10, 100, 143, 80);
		panel_3.add(btnNewButton_9);

		btnNewButton_9.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectRow = table.getSelectedRow();
		        if (selectRow != -1) {
		            int id = Integer.parseInt(table.getValueAt(selectRow, 0).toString());
		            String fullName = textField_hoVaTen.getText().trim();
		            Date ngaySinh = (Date) dateChooser.getDate();
		            String cccd = textField_CCCD.getText().trim();
		            String userName = textField_tenDangNhap.getText().trim();
		            String matKhau = textField_MK.getText().trim();
		            String access = (String) comboBox_1.getSelectedItem(); 
		            
		            if (fullName.isEmpty() || userName.isEmpty() || matKhau.isEmpty() || access.isEmpty() || cccd.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
		                return;
		            }
		            
		            EmployeeDTO employeeDTO = new EmployeeDTO(id, cccd, fullName, ngaySinh, userName, matKhau, access);
		            EmployeeDAL employeeDAL = new EmployeeDAL();
		            boolean success = employeeDAL.updateEmployee(employeeDTO);
		            if(success) {
		                JOptionPane.showMessageDialog(null, "Cập nhật thành công.");
		                textField_hoVaTen.setText("");
		                textField_CCCD.setText("");
		                textField_tenDangNhap.setText("");
		                textField_MK.setText("");
		            } else {
		                JOptionPane.showMessageDialog(null, "Cập nhật không thành công.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để sửa.");
		        }
		    }
		});
		
		
		JButton btnNewButton_10 = new JButton("Xóa NV");
		btnNewButton_10.setForeground(new Color(255, 0, 0));
		btnNewButton_10.setBackground(new Color(226, 223, 199));
		btnNewButton_10.setIcon(new ImageIcon(employee_interface.class.getResource("/image/remove-icon.png")));
		btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_10.setBounds(10, 190, 143, 78);
		panel_3.add(btnNewButton_10);
		btnNewButton_10.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectRow = table.getSelectedRow();
				  if (selectRow != -1) {
			            int id = Integer.parseInt(table.getValueAt(selectRow, 0).toString());
			            EmployeeDAL employeeDAL = new EmployeeDAL();
			            boolean success = employeeDAL.deleteEmployee(id);
			            if (success) {
			                JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công.");
			                // Cập nhật lại bảng 
			                ((DefaultTableModel) table.getModel()).removeRow(selectRow);
			            } else {
			                JOptionPane.showMessageDialog(null, "Xóa nhân viên không thành công.");
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa.");
			        }
				
				
			}
			
		});
	}
	
	public EmployeeDTO findEmployeeByUsername(String fullnames) {
	    EmployeeDTO employee = null;
	    
	    String sqlFindByUsername = "SELECT * FROM Employee WHERE username = ?";
	    try {
	        PreparedStatement cmd = con.prepareStatement(sqlFindByUsername);
	        cmd.setString(1, fullnames);
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
            InputStream imgStream = employee_interface.class.getResourceAsStream(path);
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
