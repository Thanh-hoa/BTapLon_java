package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
//import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.Statement;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
//import javax.swing.JMenuBar;
//import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import DAL.ConnectJDBC;
import DAL.CustomerDAL;
import DAL.EmployeeDAL;
import DAL.OrderDetailDAL;
import DAL.OrdersDAL;
import DAL.ProductDAL;
import DAL.TablesDAL;
import DTO.CustomerDTO;
import DTO.EmployeeDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import DTO.OrderDetailDTO;


public class order_Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_soBan;
	private JTextField textField_SoLuong;
	JTable table = null;
    private int currentOrderId;
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	static String userName ="";
	static String fullName="";
	static String access="";
	static String id="";
	
	Connection con = ConnectJDBC.openConnection();
	private JTextField textField_STD;
	
	
	public order_Interface(String username, String fullname, String access) {
	    this.userName = username;
	    this.fullName = fullname;
	    this.access = access;
	  
	    EmployeeDTO empl = findEmployeeByfullName(fullname);
	    if (empl != null) {
	        this.id = String.valueOf(empl.getId());
	    }
	    IsMain();
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					order_Interface frame = new order_Interface(userName , fullName , access);
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
        setResizable(false);
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
		

		
		JLabel lblNewLabel_8 = new JLabel("Chọn bàn: ");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_8.setBounds(407, 193, 73, 29);
		contentPane.add(lblNewLabel_8);
		
		textField_soBan = new JTextField();
		textField_soBan.setBounds(490, 197, 96, 26);
		contentPane.add(textField_soBan);
		textField_soBan.setColumns(10);
		

		
		JLabel lblNewLabel_13 = new JLabel("");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_13.setBounds(480, 261, 87, 17);
		contentPane.add(lblNewLabel_13);
		
		JLabel lblNewLabel_11 = new JLabel("Số lượng : ");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_11.setBounds(413, 245, 87, 33);
		contentPane.add(lblNewLabel_11);
		
		textField_SoLuong = new JTextField();
		textField_SoLuong.setBounds(490, 250, 96, 28);
		contentPane.add(textField_SoLuong);
		textField_SoLuong.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Chọn Loại Thực Đơn :  ");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 63, 174, 33);
		contentPane.add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBounds(180, 63, 227, 33);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Coffee", "Sinh tố và trà", "Kem", "Tráng miệng"}));
		comboBox.setSelectedIndex(-1);
		contentPane.add(comboBox);
	       JPanel panel = new JPanel();
			panel.setBounds(10, 115, 392, 362);
			panel.setLayout(null);
			contentPane.add(panel);
			//chỗ click chuột để chọn
		   comboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	panel.removeAll();
	            	panel.revalidate();
	            	panel.repaint();
	            
	                // chọn coi comboBox đang là cái gì
	                String selectedItem = (String) comboBox.getSelectedItem();
	                ProductDAL dalProduct = new ProductDAL();
	                ArrayList<ProductDTO> list = dalProduct.listProductFindByName(selectedItem);

	                    	 
	                if(list!=null) {
	                	String[] columnNames = {"Mã TĐ" ,"Tên" ,"Giá"};
	                	Object[][] data  = new Object[list.size()][3];
	                	int i =0; 
	                	for(ProductDTO p : list) {
	                		data[i][0] = p.getId();
	                		data[i][1] = p.getName();
	                		data[i][2] = p.getPrice();
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
                    scrollPane.setBounds(10, 10,380 , 450);
                    panel.add(scrollPane);
	                }        	   
	              }
               });
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(616,115,461,362);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("Mã TĐ ");
			lblNewLabel_1.setBackground(new Color(128, 255, 255));
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(22, 0, 79, 23);
			panel_1.add(lblNewLabel_1);
			
			JLabel lblNewLabel_5 = new JLabel("Thực Đơn ");
			lblNewLabel_5.setBackground(new Color(128, 255, 255));
			lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_5.setBounds(124, 0, 89, 23);
			panel_1.add(lblNewLabel_5);
			
			JLabel lblNewLabel_6 = new JLabel("Đơn Giá ");
			lblNewLabel_6.setBackground(new Color(128, 255, 255));
			lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_6.setBounds(252, 0, 65, 23);
			panel_1.add(lblNewLabel_6);
			
			JLabel lblNewLabel_7 = new JLabel("Số Lượng ");
			lblNewLabel_7.setBackground(new Color(128, 255, 255));
			lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_7.setBounds(350, 2, 91, 18);
			panel_1.add(lblNewLabel_7);

			
		
	
			
			JButton btnNewButton_10 = new JButton("Chọn Món ");
			btnNewButton_10.setBackground(new Color(255, 128, 128));
			btnNewButton_10.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnNewButton_10.setBounds(423, 302, 163, 70);
			contentPane.add(btnNewButton_10);	
			//Lấy dữ liệu từ bảng table qua bên listView
		   // DefaultListModel<String> listModel = new DefaultListModel<>();
	        JList<String> listView = new JList<>(listModel);
	        listView.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        listView.setCellRenderer(new ListCellRenderer<String>() {
                @Override
                public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                    JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
                    String[] parts = value.split("\\t");          
                    JLabel labelId = new JLabel(parts[0]);
                    JLabel labelName = new JLabel(parts[1]);
                    JLabel labelPrice = new JLabel(parts[2]);
                    JLabel labelSoLuong = new JLabel(parts[3]);

                    labelId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Thụt lề cho ID
                    Font font = new Font("Tahoma", Font.PLAIN, 15);
                    labelId.setFont(font);
                    labelName.setFont(font);
                    labelPrice.setFont(font);
                    labelSoLuong.setFont(font);
                    
                    panel.add(labelId);
                    panel.add(labelName);
                    panel.add(labelPrice);
                    panel.add(labelSoLuong);
                    
                    if (isSelected) {
                        panel.setBackground(list.getSelectionBackground());
                        panel.setForeground(list.getSelectionForeground());
                    } else {
                        panel.setBackground(list.getBackground());
                        panel.setForeground(list.getForeground());
                    }
                    panel.setFont(list.getFont());
                    return panel;
                }
            });
               
	        JScrollPane scrollPane = new JScrollPane(listView);
	        scrollPane.setBounds(0, 33, 441, 327);
	        panel_1.add(scrollPane);   
	        
			btnNewButton_10.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 int selectRow = table.getSelectedRow();
					 if(selectRow != -1) {
						    String id = table.getValueAt(selectRow, 0).toString();
		                    String name = table.getValueAt(selectRow, 1).toString();
		                    String price = table.getValueAt(selectRow, 2).toString();
		                    String soBan = textField_soBan.getText();		        
		                    String soLuong = textField_SoLuong.getText();
		               	    String item = " "+id + "\t" + name + " \t " + price +" \t " + soLuong; 
		                    listModel.addElement(item);
		                    
					 }	
				}
			});
			 
		    JButton btnNewButton_11 = new JButton("Lưu Món");
		    btnNewButton_11.setFont(new Font("Tahoma", Font.BOLD, 16));
		    btnNewButton_11.setBounds(426, 410, 160, 53);
		    contentPane.add(btnNewButton_11);
		    

		    btnNewButton_11.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            ArrayList<OrderDetailDTO> orderDetails = new ArrayList<>();
		                // Process each item in the list model
		                for (int i = 0; i < listModel.getSize(); i++) {
		                    String[] parts = listModel.get(i).split("\\t");

		                    int idProduct = Integer.parseInt(parts[0].trim());
		                    int quantity = Integer.parseInt(parts[3].trim());

		                    // Retrieve product information
		                    ProductDTO product = new ProductDTO(idProduct);
		                    orderDetails.add(new OrderDetailDTO(product, quantity)); 
		                }
			            // Retrieve table ID from the text field
			            int idTable = Integer.parseInt(textField_soBan.getText().trim());
			            saveOrder(idTable, orderDetails);
		        }
		    });

	
	
		JButton btnNewButton_8 = new JButton("Xóa Thực Đơn Được Chọn ");
		btnNewButton_8.setBackground(new Color(128, 255, 255));
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_8.setBounds(635, 64, 207, 32);
		contentPane.add(btnNewButton_8);
		
		  btnNewButton_8.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              int selectedIndex = listView.getSelectedIndex();
              if (selectedIndex != -1) {
                  String selectedItem = listModel.get(selectedIndex);
                  String[] parts = selectedItem.split("\\t");
                  int idProduct = Integer.parseInt(parts[0].trim());

                  int idOrder = getCurrentOrderId();
                  deleteOrderDetail(idProduct, idOrder);

                  listModel.remove(selectedIndex);
              }
          }
      });
	
    JButton btnNewButton_9 = new JButton("Xóa Danh Sách Được Chọn");
    btnNewButton_9.setBackground(new Color(128, 255, 255));
    btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnNewButton_9.setForeground(new Color(0, 0, 0));
    btnNewButton_9.setBounds(857, 64, 216, 33);
    contentPane.add(btnNewButton_9);
    
    JLabel lblNewLabel_9 = new JLabel("SDT:");
    lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
    lblNewLabel_9.setBounds(414, 155, 52, 28);
    contentPane.add(lblNewLabel_9);
    
    textField_STD = new JTextField();
    textField_STD.setFont(new Font("Tahoma", Font.PLAIN, 15));
    textField_STD.setBounds(459, 153, 147, 30);
    contentPane.add(textField_STD);
    textField_STD.setColumns(10);
  

    btnNewButton_9.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int idOrder = getCurrentOrderId();
          //  deleteAllOrderDetails(idOrder);

            listModel.clear();
        }
    });

	
	}
	
	
	
	public EmployeeDTO findEmployeeByfullName(String fullnames) {
	    EmployeeDTO employee = null;
	    
	    String sqlFindByUsername = "SELECT * FROM Employee WHERE fullname = ?";
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
	
	  public int getCurrentOrderId() {
	        return currentOrderId;
	    }


	  public void saveOrder (int idTable, ArrayList<OrderDetailDTO> orderDetails) {
		    try {
		        con.setAutoCommit(false);
		        CustomerDAL dalCustomer = new CustomerDAL();
		        String phoneNumber = textField_STD.getText();
		        CustomerDTO dtoCustomer =null;
		        if(!dalCustomer.checkPhoneNumberExitsCustomer(phoneNumber))
		        {
		        	dtoCustomer = new CustomerDTO(phoneNumber);
		        	dalCustomer.insertCustomer(dtoCustomer);
		        
		        }
		        else 
		        {
		        	dtoCustomer = dalCustomer.findByPhoneNumber(phoneNumber);
		        }
		        System.out.println("Customer ID: " + dtoCustomer.getId());

		        // Bước 2: Tạo đơn hàng mới
		        EmployeeDAL dalEmployee = new EmployeeDAL();
		        String insertOrderSQL = "INSERT INTO Orders (idEmployee, idCustomer, idTable, orderTime) VALUES (?, ?, ?, GETDATE())";
		        PreparedStatement psOrder = con.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
		        psOrder.setInt(1, dalEmployee.findByUserName(userName).getId());
		        psOrder.setInt(2, dtoCustomer.getId());
		        psOrder.setInt(3, idTable);
		        psOrder.executeUpdate();

		        ResultSet rsOrder = psOrder.getGeneratedKeys();
		        int currentOrderId = -1;
		        if (rsOrder.next()) {
		            currentOrderId = rsOrder.getInt(1);
		        }

		     // Bước 3: Lưu chi tiết đơn hàng
		        String insertOrderDetailSQL = "INSERT INTO OrderDetail (idOrder, idProduct, quantity) VALUES (?, ?, ?)";
		        PreparedStatement psOrderDetail = con.prepareStatement(insertOrderDetailSQL);

		        for (OrderDetailDTO detail : orderDetails) {
		            psOrderDetail.setInt(1, currentOrderId);
		            psOrderDetail.setInt(2, detail.getIdProduct().getId());
		            psOrderDetail.setInt(3, detail.getQuantity());
		            psOrderDetail.addBatch();
		        }
		        psOrderDetail.executeBatch();
		        
		        // Bước 3: Cập nhật trạng thái của bàn
		        String updateTableSQL = "UPDATE Tables SET status = 1 WHERE id = ?";
		        PreparedStatement psUpdateTable = con.prepareStatement(updateTableSQL);
		        psUpdateTable.setInt(1, idTable);
		        psUpdateTable.executeUpdate();
		        
//		        // Hoàn tất giao dịch
		        con.commit();
		        System.out.println("Order saved successfully with ID: " + currentOrderId);
		    } catch (SQLException e) {
		        try {
		            if (con != null) {
		                con.rollback();
		            }
		        } catch (SQLException rollbackEx) {
		            rollbackEx.printStackTrace();
		        }
		        e.printStackTrace();
		        System.out.println("Failed to save order.");
		    } finally {
		        try {
		            if (con != null) {
		                con.setAutoCommit(true);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		}

	  
 
	 public void deleteOrderDetail(int idProduct, int idOrder) {
		    try {
		        String deleteOrderDetailSQL = "DELETE FROM OrderDetail WHERE idProduct = ? AND idOrder = ?";
		        PreparedStatement psDeleteOrderDetail = con.prepareStatement(deleteOrderDetailSQL);
		        psDeleteOrderDetail.setInt(1, idProduct);
		        psDeleteOrderDetail.setInt(2, idOrder);
		        psDeleteOrderDetail.executeUpdate();
		       //  System.out.println("Order detail with product ID " + idProduct + " deleted successfully.");
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		     //   System.out.println("Failed to delete order detail with product ID " + idProduct + ".");
		    }
		}

		public void deleteAllOrderDetails(int idOrder) {
		    try {
		        String deleteAllOrderDetailsSQL = "DELETE FROM OrderDetail WHERE idOrder = ?";
		        PreparedStatement psDeleteAllOrderDetails = con.prepareStatement(deleteAllOrderDetailsSQL);
		        psDeleteAllOrderDetails.setInt(1, idOrder);
		        psDeleteAllOrderDetails.executeUpdate();
		        //System.out.println("All order details for order ID " + idOrder + " deleted successfully.");
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		       // System.out.println("Failed to delete all order details for order ID " + idOrder + ".");
		    }
		}

	    
	

	
	private static ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            // Tải hình ảnh từ resource
            InputStream imgStream = order_Interface.class.getResourceAsStream(path);
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
