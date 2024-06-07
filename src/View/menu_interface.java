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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAL.ConnectJDBC;
import DAL.ProductDAL;
import DTO.EmployeeDTO;
import DTO.ProductDTO;
import DTO.CategoryDTO;

import javax.swing.JScrollPane;


public class menu_interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField_DonGia;
	private JTextField textField_tenTD;
	private JTextField textField_DVT;
	private JTextField textField_TenTDTraCuu;

	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	JPanel panel_2 = new JPanel();
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	
	Connection con = ConnectJDBC.openConnection();
	
	
	public menu_interface(String username, String fullname, String access) {
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
					menu_interface frame = new menu_interface(userName , fullName , access);
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(711, 43, 369, 159);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tra cứu");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(152, 0, 78, 23);
		panel_1.add(lblNewLabel);
		
		JButton btnNewButton_11 = new JButton("Tìm ");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_TenTDTraCuu.getText().trim();
				panel_2.removeAll();
				panel_2.revalidate();
				panel_2.repaint();
				ProductDAL dalProduct = new ProductDAL();
	             ArrayList<ProductDTO> list= dalProduct.listProductFindByString(name);
                  if(list!=null) {
                	String[] columnNames = {"Mã TĐ" ,"Tên" ,"Giá","Đơn Vị Tính","Loại TĐ"};
                	Object[][] data  = new Object[list.size()][5];
                	int i =0; 
                	for(ProductDTO p : list) {
                		data[i][0] = p.getId();
                		data[i][1] = p.getName();
                		data[i][2] = p.getPrice();
                		data[i][3] = p.getUnit();
                		data[i][4] = p.getIdCategory().getName();
                		i++;
                	}
                    
			      table = new JTable();
              table.setModel(new DefaultTableModel(
                      data,
                      columnNames
              ));
              table.setFont(new Font("Tahoma", Font.PLAIN,15));
              table.setBounds(10, 10,400,400);
              JScrollPane scrollPane = new JScrollPane(table);
              scrollPane.setBounds(10, 30, 879, 238);
              panel_2.add(scrollPane);
              
           
      		
                }        	   
              }
         });
	
	
				
		
		btnNewButton_11.setIcon(new ImageIcon(menu_interface.class.getResource("/image/Glass.png")));
		btnNewButton_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_11.setBounds(105, 102, 138, 47);
		panel_1.add(btnNewButton_11);
		
		JLabel lblNewLabel_9 = new JLabel("Tên thực đơn:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_9.setBounds(10, 37, 96, 41);
		panel_1.add(lblNewLabel_9);
		
		textField_TenTDTraCuu = new JTextField();
		textField_TenTDTraCuu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_TenTDTraCuu.setBounds(120, 41, 196, 34);
		panel_1.add(textField_TenTDTraCuu);
		textField_TenTDTraCuu.setColumns(10);
		
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Loại TĐ:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(25, 56, 102, 33);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel( new String[]{"Coffee", "Sinh tố và trà", "Kem", "Tráng miệng" }));
		comboBox.setBounds(94, 56, 252, 33);
		contentPane.add(comboBox);
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_2.removeAll();
				panel_2.revalidate();
				panel_2.repaint();
				  String selectedItem = (String) comboBox.getSelectedItem();
					ProductDAL dalProduct = new ProductDAL();
		             ArrayList<ProductDTO> list= dalProduct.listProductFindByName(selectedItem);
	                  if(list!=null) {
	                	String[] columnNames = {"Mã TĐ" ,"Tên" ,"Giá","Đơn Vị Tính","Loại TĐ"};
	                	Object[][] data  = new Object[list.size()][5];
	                	int i =0; 
	                	for(ProductDTO p : list) {
	                		data[i][0] = p.getId();
	                		data[i][1] = p.getName();
	                		data[i][2] = p.getPrice();
	                		data[i][3] =p.getUnit();
	                		data[i][4] = selectedItem;
	                		i++;
	                	}
	                    
  			      table = new JTable();
                  table.setModel(new DefaultTableModel(
                          data,
                          columnNames
                  ));
                  table.setFont(new Font("Tahoma", Font.PLAIN,15));
                  table.setBounds(10, 10,400,400);
                  JScrollPane scrollPane = new JScrollPane(table);
                  scrollPane.setBounds(10, 30, 879, 238);
                  panel_2.add(scrollPane);
                  
               
          		
	                }        	   
	              }
             });
		
		
		JLabel lblNewLabel_5 = new JLabel("Đơn Giá:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(25, 152, 70, 20);
		contentPane.add(lblNewLabel_5);
		
		textField_DonGia = new JTextField();
		textField_DonGia.setBounds(102, 148, 163, 33);
		contentPane.add(textField_DonGia);
		textField_DonGia.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Tên TĐ:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(371, 56, 80, 33);
		contentPane.add(lblNewLabel_7);
		
		textField_tenTD = new JTextField();
		textField_tenTD.setBounds(443, 56, 246, 33);
		contentPane.add(textField_tenTD);
		textField_tenTD.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Đơn Vị Tính:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(371, 148, 96, 29);
		contentPane.add(lblNewLabel_10);
		
		textField_DVT = new JTextField();
		textField_DVT.setBounds(477, 150, 109, 29);
		contentPane.add(textField_DVT);
		textField_DVT.setColumns(10);
	
	
	
		panel_2.setBounds(10, 212, 899, 278);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		JLabel lblNewLabel_8 = new JLabel("Danh sách thực đơn");
		lblNewLabel_8.setForeground(new Color(255, 0, 0));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_8.setBounds(386, 0, 241, 26);
		panel_2.add(lblNewLabel_8);
		
		
		

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(919, 212, 163, 278);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewButton_8 = new JButton("Thêm ");
		btnNewButton_8.setForeground(new Color(255, 0, 0));
		btnNewButton_8.setBackground(new Color(226, 223, 199));
		btnNewButton_8.setIcon(new ImageIcon(menu_interface.class.getResource("/image/user-add-icon.png")));
		btnNewButton_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_8.setBounds(9, 10, 150, 80);
		panel_3.add(btnNewButton_8);
		
		btnNewButton_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String loaiTD = (String)comboBox.getSelectedItem();
				try {
				String tenTD = textField_tenTD.getText().trim();
				String DG =  textField_DonGia.getText().trim();
				String donViTinh = textField_DVT.getText().trim();
				CategoryDTO  category = new CategoryDTO(loaiTD);
				if(loaiTD =="Coffee")
					category.setId(1);
				if(loaiTD =="Sinh tố và trà")
					category.setId(2);
				if(loaiTD =="Kem")
					category.setId(3);
				if(loaiTD =="Tráng miệng")
					category.setId(4);
				 
				float donGia = Float.parseFloat(DG);
				if(tenTD.isEmpty()|| DG.isEmpty() || donViTinh.isEmpty()) {
					 JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
			            return;
				}
				
				ProductDTO productDTO = new ProductDTO(tenTD, donGia, donViTinh , category);
				ProductDAL  productDAL = new ProductDAL();
				 boolean rs =   productDAL.insertProduct(productDTO);
				 if(rs) {
		                JOptionPane.showMessageDialog(null, "Thêm thành công");
		                textField_tenTD.setText("");
		                textField_DonGia.setText("");
		                textField_DVT.setText("");
		            } else {
		                JOptionPane.showMessageDialog(null, "Thêm thất bại");
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Giá phải là một số");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + ex.getMessage());
		        }
			}
			
		});
		
		
		JButton btnNewButton_9 = new JButton("Sửa ");
		btnNewButton_9.setForeground(new Color(255, 0, 0));
		btnNewButton_9.setBackground(new Color(226, 223, 199));
		btnNewButton_9.setIcon(new ImageIcon(menu_interface.class.getResource("/image/user-edit-icon.png")));
		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_9.setBounds(10, 100, 143, 80);
		panel_3.add(btnNewButton_9);
		btnNewButton_9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    int selectRow = table.getSelectedRow();
			    if(selectRow!=-1) {
			    	String loaiTD = (String)comboBox.getSelectedItem(); 
			    	int id = Integer.parseInt( table.getValueAt(selectRow, 0).toString());
			    	String name = textField_tenTD.getText().trim();
			        String gia = textField_DonGia.getText().trim();
			        float price = Float.parseFloat(gia);
			        String unit = textField_DVT.getText().trim();
			        CategoryDTO  category = new CategoryDTO(loaiTD);
			        if(loaiTD =="Coffee")
						category.setId(1);
					if(loaiTD =="Sinh tố và trà")
						category.setId(2);
					if(loaiTD =="Kem")
						category.setId(3);
					if(loaiTD =="Tráng miệng")
						category.setId(4);
					
					if(loaiTD.isEmpty()|| gia.isEmpty() || unit.isEmpty()||name.isEmpty()) {
						 JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
				            return;
					}
					
					ProductDTO productDTO = new ProductDTO(id ,name,price,unit,category);
					ProductDAL  productDAL = new ProductDAL();
				    boolean rs =	productDAL.updateProduct(productDTO);
					if(rs) {
						JOptionPane.showConfirmDialog(null, "Cập nhật thành công.");
						textField_tenTD.setText("");
		                textField_DonGia.setText("");
		                textField_DVT.setText("");
					}else {
						JOptionPane.showMessageDialog(null, "Cập nhật không thành công.");
					}	
			    }else {
			    	JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để sửa.");
			    }
				
			}
			
		});
		
		JButton btnNewButton_10 = new JButton("Xóa ");
		btnNewButton_10.setForeground(new Color(255, 0, 0));
		btnNewButton_10.setBackground(new Color(226, 223, 199));
		btnNewButton_10.setIcon(new ImageIcon(menu_interface.class.getResource("/image/remove-icon.png")));
		btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_10.setBounds(10, 190, 143, 78);
		panel_3.add(btnNewButton_10);
		
		btnNewButton_10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectRow= table.getSelectedRow();
				if(selectRow!=-1) {
					int id = Integer.parseInt(table.getValueAt(selectRow, 0).toString());
					ProductDAL product = new ProductDAL();
					boolean rs = product.deleteProduct(id);
					if(rs) {
						JOptionPane.showMessageDialog(null, "Xoá thành công.");
						((DefaultTableModel) table.getModel()).removeRow(selectRow);
					}else {
						JOptionPane.showMessageDialog(null, "Xóa không thành công.");
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa.");
				}
				
				
			}
			
		});
		
		
	
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
            InputStream imgStream = menu_interface.class.getResourceAsStream(path);
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
