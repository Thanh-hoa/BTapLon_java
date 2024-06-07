package View;

import java.awt.EventQueue;
import DAL.ConnectJDBC;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.net.URL;


import DAL.ConnectJDBC;




public class loginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel Log_in;
	private JTextField txtUserName;
	private JPasswordField txtPassWord_1;

	/**
	 * Launch the application.
	 */
	Connection con =  ConnectJDBC.openConnection();
	String  fullname, access ;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginGUI frame = new loginGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}

	
	
	public loginGUI() {
		
		
		this.setTitle("Chương Trình Quản Lý Bán Coffee");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1040, 521);
		Log_in = new JPanel();
		Log_in.setForeground(new Color(0, 0, 255));
		Log_in.setBackground(new Color(192, 192, 192));
		Log_in.setBorder(new EmptyBorder(9, 8, 8, 8));
		setResizable(false);
		URL url = loginGUI.class.getResource("/image/Coffee-icon.png");
		Image img = Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(img);
		setContentPane(Log_in);
		Log_in.setLayout(null);
		
		JButton Log_In_Button = new JButton("Log in");
		Log_In_Button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					String userName= txtUserName.getText().trim();
					String passWord = new String(txtPassWord_1.getPassword()).trim();

					StringBuilder sb = new StringBuilder();
					
					if(userName.equals("")) {
						sb.append("Username is Empty\n");
					}
					if(passWord.equals("")) { 
						sb.append("Password is Empty");
					}
					if(sb.length()>0) {
						JOptionPane.showMessageDialog(loginGUI.this, sb.toString() , "Invalidation" ,
						JOptionPane.ERROR_MESSAGE );
						return;
					}
					String sql = "SELECT * fROM Employee where userName = ? and passWord = ?";
					try {
						PreparedStatement st = con.prepareStatement(sql);
 								st.setString(1,userName);
								st.setString(2,passWord);
								ResultSet rs = st.executeQuery();
								if(rs.next()) {
									fullname = rs.getString("fullname");
									access = rs.getString("access");
									new GiaDienChinh(userName ,fullname , access).setVisible(true);
								    dispose();
								   
								}else {
									JOptionPane.showMessageDialog( loginGUI.this , "Sai tài khoản" );
								}		
					} catch (Exception e2) {
						e2.printStackTrace();
						
					}
			}
		});
		
		//xét màu 
		Log_In_Button.setBackground(new Color(255, 170, 130));
		Log_In_Button.setForeground(new Color(128, 0, 0));
		Log_In_Button.setFont(new Font("Tahoma", Font.BOLD, 25));
		Log_In_Button.setBounds(235, 360, 250, 42);
		Log_in.add(Log_In_Button);
		
		//nhập userName
		txtUserName = new JTextField();
		txtUserName.setBackground(new Color(207, 207, 207));
		txtUserName.setBounds(326, 171, 239, 42);
		Log_in.add(txtUserName);
		txtUserName.setColumns(21);
		
		JLabel lblNewLabel = new JLabel("User Name : ");
		lblNewLabel.setBackground(new Color(128, 64, 64));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(143, 169, 187, 32);
		Log_in.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PassWord :");
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(143, 243, 187, 51);
		Log_in.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("ĐĂNG NHẬP");
		lblNewLabel_2.setBackground(new Color(0, 0, 0));
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel_2.setBounds(261, 88, 227, 42);
		Log_in.add(lblNewLabel_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Show");
		chckbxNewCheckBox.setBackground(new Color(204, 204, 204));
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxNewCheckBox.setForeground(new Color(0, 0, 0));
		chckbxNewCheckBox.setBounds(326, 306, 120, 21);
		Log_in.add(chckbxNewCheckBox);
		
		chckbxNewCheckBox.addActionListener(new ActionListener(){
			public void  actionPerformed(ActionEvent e) {
				if(chckbxNewCheckBox.isSelected()) {
					txtPassWord_1.setEchoChar((char) 0);
				}else {
					txtPassWord_1.setEchoChar('*');
				}
			}
			
		});
      	
		txtPassWord_1 = new JPasswordField();
		txtPassWord_1.setBackground(new Color(204, 204, 204));
		txtPassWord_1.setColumns(21);
		txtPassWord_1.setBounds(326, 255, 239, 42);
		Log_in.add(txtPassWord_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setForeground(new Color(0, 255, 64));
		lblNewLabel_3.setIcon(new ImageIcon(loginGUI.class.getResource("/image/Mokey.png")));
		lblNewLabel_3.setBounds(29, 22, 104, 84);
		Log_in.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(loginGUI.class.getResource("/image/bacgroupCafe.jpg")));
		lblNewLabel_4.setBounds(0, 0, 1039, 497);
		Log_in.add(lblNewLabel_4);
	}
}
