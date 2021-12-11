package app;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import connection.ConnectDB;
import dao.DAONhanVien;
import dao.DAOTaiKhoan;
import dao.Regex;
import entity.NhanVien;
import entity.TaiKhoan;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmQuenMK extends JFrame implements ActionListener,MouseListener,KeyListener {

	
	private static final long serialVersionUID = 1L;
	private JTextField txtTaiKhoan;
	private JButton btnQuayLai;
	private JButton btnDoiMK;
	private Regex regex;
	private DAONhanVien daoNhanVien;
	private DAOTaiKhoan daoTK;
	private JPasswordField pwMatKhauMoi;
	private JPasswordField pwXacNhan;
	private JTextField txtSDT;
	private FrmDangNhap frmDN;
	private JPopupMenu popUp;
	private JMenuItem popItem;


	public FrmQuenMK() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Đổi mật khẩu");
		setSize(500,500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.GRAY);
		
		
		
//connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//khai bao dao
		frmDN = new FrmDangNhap();
		daoNhanVien = new DAONhanVien();
		daoTK = new DAOTaiKhoan();
		regex = new Regex();
		IconFontSwing.register(FontAwesome.getIconFont());
		ImageIcon logoApp = (ImageIcon) IconFontSwing.buildIcon(FontAwesome.FOURSQUARE, 50, new Color(164, 44,167));
		setIconImage(logoApp.getImage());
		
		Image imgHeader = Toolkit.getDefaultToolkit ().getImage ("data\\imgDangNhap\\bgHeader.png");
		JLabel lbHeaderDN = new JLabel("");
		lbHeaderDN.setBounds(0, 0, 488, 87);
		getContentPane().add(lbHeaderDN);
		Image imgresize = imgHeader.getScaledInstance(lbHeaderDN.getWidth(), lbHeaderDN.getHeight(), 0);
		lbHeaderDN.setIcon(new ImageIcon(imgresize));
		
		JLabel lblTaiKhoan = new JLabel("Tài khoản:");
		lblTaiKhoan.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTaiKhoan.setForeground(new Color(255, 255, 255));
		lblTaiKhoan.setBounds(61, 156, 112, 20);
		getContentPane().add(lblTaiKhoan);
		
		txtTaiKhoan = new JTextField();
		txtTaiKhoan.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtTaiKhoan.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));

		txtTaiKhoan.setBounds(166, 150, 246, 33);
		getContentPane().add(txtTaiKhoan);
		txtTaiKhoan.setColumns(10);
		
		JLabel lblMatKhauCu = new JLabel("Số điện thoại:");
		lblMatKhauCu.setForeground(Color.WHITE);
		lblMatKhauCu.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMatKhauCu.setBounds(61, 209, 112, 20);
		getContentPane().add(lblMatKhauCu);
		
		JLabel lblSubQuenMK = new JLabel("Quên mật khẩu");
		lblSubQuenMK.setForeground(Color.WHITE);
		lblSubQuenMK.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblSubQuenMK.setLabelFor(this);
		lblSubQuenMK.setBounds(170, 100, 242, 39);
		getContentPane().add(lblSubQuenMK);
		
		btnDoiMK = new JButton("Đổi mật khẩu");
		btnDoiMK.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDoiMK.setBackground(new Color(164, 44,167));
		btnDoiMK.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnDoiMK.setForeground(Color.WHITE);
		btnDoiMK.setBounds(166, 360, 201, 33);
		
		Icon iconDoiMK = IconFontSwing.buildIcon(FontAwesome.EXCHANGE, 20, new Color(255, 255 ,255));
		btnDoiMK.setIcon(iconDoiMK);
		
		getContentPane().add(btnDoiMK);
		
		btnQuayLai = new JButton("Quay lại");
		btnQuayLai.setForeground(Color.WHITE);
		btnQuayLai.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnQuayLai.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		btnQuayLai.setBackground(new Color(164, 44, 167));
		btnQuayLai.setBounds(166, 404, 201, 33);
		
		Icon iconQuayLai = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 20, new Color(255, 255 ,255));
		btnQuayLai.setIcon(iconQuayLai);
		
		getContentPane().add(btnQuayLai);
		
		JLabel lblNhac1 = new JLabel("");
		
		lblNhac1.setBounds(28, 344, 83, 93);
		
		
		Image imgNhac1 = Toolkit.getDefaultToolkit ().getImage ("data\\img\\IconNhac1.png");
		Image resizeNhac1 = imgNhac1.getScaledInstance(lblNhac1.getWidth(), lblNhac1.getHeight(), 0);
		lblNhac1.setIcon(new ImageIcon(resizeNhac1));
		getContentPane().add(lblNhac1);
		
		JLabel lblNhac2 = new JLabel("");
		lblNhac2.setBounds(377, 344, 83, 91);
		getContentPane().add(lblNhac2);
		Image imgNhac2 = Toolkit.getDefaultToolkit ().getImage ("data\\img\\IconNhac2.png");
		Image resizeNhac2 = imgNhac2.getScaledInstance(lblNhac2.getWidth(), lblNhac2.getHeight(), 0);
		lblNhac2.setIcon(new ImageIcon(resizeNhac2));
		
		JLabel lblMKMoi = new JLabel("Mật khẩu mới:");
		lblMKMoi.setForeground(Color.WHITE);
		lblMKMoi.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMKMoi.setBounds(61, 262, 112, 20);
		getContentPane().add(lblMKMoi);
		
		pwMatKhauMoi = new JPasswordField();
		pwMatKhauMoi.setFont(new Font("SansSerif", Font.PLAIN, 14));
		pwMatKhauMoi.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));
		pwMatKhauMoi.setBounds(166, 255, 246, 33);
		getContentPane().add(pwMatKhauMoi);
		
		JLabel lblXacNhan = new JLabel("Xác nhận lại:");
		lblXacNhan.setForeground(Color.WHITE);
		lblXacNhan.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblXacNhan.setBounds(61, 313, 95, 20);
		getContentPane().add(lblXacNhan);
		
		pwXacNhan = new JPasswordField();
		pwXacNhan.setFont(new Font("SansSerif", Font.PLAIN, 14));
		pwXacNhan.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));
		pwXacNhan.setBounds(166, 307, 246, 33);
		getContentPane().add(pwXacNhan);
		
		txtSDT = new JTextField();
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtSDT.setColumns(10);
		txtSDT.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));
		txtSDT.setBounds(166, 203, 246, 33);
		getContentPane().add(txtSDT);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 488, 465);
		getContentPane().add(lblBackground);
		Image imgBackground = Toolkit.getDefaultToolkit ().getImage ("data\\img\\imgGradient.jpg");
		Image resizeBackground = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBackground));
		
		popUp = new JPopupMenu();
		popItem = new JMenuItem("Trợ giúp");
		popUp.add(popItem);
		
		addMouseListener(new MouseAdapter() {
	         public void mouseReleased(MouseEvent me) {
	            showPopup(me); 
	         }
	    });
		
		
		btnDoiMK.addActionListener(this);
		btnQuayLai.addActionListener(this);
		popItem.addActionListener(this);
		
		txtTaiKhoan.addKeyListener(this);
		txtSDT.addKeyListener(this);
		pwMatKhauMoi.addKeyListener(this);
		pwXacNhan.addKeyListener(this);
		btnDoiMK.addKeyListener(this);
		btnQuayLai.addKeyListener(this);
		
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				frmDN.setVisible(true);
			}
		});
		
	}
	
	 void showPopup(MouseEvent me) {
	      if(me.isPopupTrigger())
	         popUp.show(me.getComponent(), me.getX(), me.getY());
	   }

	
	public boolean kiemTraRong() {
		if(txtTaiKhoan.getText().trim().length() == 0||txtSDT.getText().trim().length() == 0||pwMatKhauMoi.getText().trim().length() == 0||pwXacNhan.getText().trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public void lamMoi() {
		txtTaiKhoan.setText("");
		txtSDT.setText("");
		pwMatKhauMoi.setText("");
		pwXacNhan.setText("");
	}

	
	//Kiểm tra đăng nhập
	public void doiMK() {
		
		String maTK = txtTaiKhoan.getText().toString().trim();
		String sdt = txtSDT.getText().toString().trim();
		String mkMoi = pwMatKhauMoi.getText().toString().trim();
		String xacNhan = pwXacNhan.getText().toString().trim();
		
		TaiKhoan tk = daoTK.getTaiKhoanTheoMa(maTK);
		
		
		if(!kiemTraRong()) {
			if(tk.getMaTK() == null) {
				JOptionPane.showMessageDialog(this, "Tài khoản không đúng!\nVui lòng kiểm tra lại.");
			}
			else {
				NhanVien nv = daoNhanVien.getNVTheoTK(tk.getMaTK());
				if(!nv.getSdt().equalsIgnoreCase(sdt)) {
					JOptionPane.showMessageDialog(this, "Số điện thoại của nhân viên không đúng!\n Vui lòng kiểm tra lại");
				}
				else if(regex.regexMatKhau(pwMatKhauMoi)) {
					if(mkMoi.equalsIgnoreCase(xacNhan)){
						tk.setMatKhau(mkMoi);
						if(daoTK.suaTK(tk)) {
							JOptionPane.showMessageDialog(this, "Mật khẩu đã được đổi thành công.\nVui lòng chọn quay lại để đăng nhập");
							
							frmDN.setVisible(true);
							this.setVisible(false);
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không đúng!\n vui lòng kiểm tra lại.");
						pwXacNhan.requestFocus();
						pwXacNhan.selectAll();
					}
				}
			}
			
		}
		else JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
		
		
		
		
	}
	
	//event
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnQuayLai)) {
			FrmDangNhap frmDN = new FrmDangNhap();
			frmDN.setVisible(true);
			this.setVisible(false);
		}
		if(o.equals(btnDoiMK)) {	
			
			doiMK();
			
		}
		 if(o.equals(popItem)) {
			String[] commands = {"cmd", "/c", "data\\help\\HELP.chm"};
			try {
				Runtime.getRuntime().exec(commands);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		if(o.equals(txtTaiKhoan)&& key == KeyEvent.VK_ENTER ) {
			txtSDT.requestFocus();
		}
		else if(o.equals(txtSDT)&& key == KeyEvent.VK_ENTER ) {
			pwMatKhauMoi.requestFocus();
		}
		else if(o.equals(pwMatKhauMoi)&& key == KeyEvent.VK_ENTER ) {
			pwXacNhan.requestFocus();
		}
		else if(o.equals(pwXacNhan)&& key == KeyEvent.VK_ENTER ) {
			btnDoiMK.doClick();
		}
		else if(key == KeyEvent.VK_ENTER ) {
			btnDoiMK.doClick();
		}
		else if(key == KeyEvent.VK_ESCAPE ) {
			frmDN.setVisible(true);
			this.setVisible(false);
		}
		
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
