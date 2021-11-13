package app;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
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

public class FrmDangNhap extends JFrame implements ActionListener,MouseListener {

	
	private static final long serialVersionUID = 1L;
	private JTextField txtTaiKhoan;
	private JButton btnThoat;
	private JButton btnDangNhap;
	private Regex regex;
	private DAONhanVien daoNhanVien;
	private DAOTaiKhoan daoTK;
	private JPasswordField txtMatKhau;
	private JLabel lblQuenMK;
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					

					FrmDangNhap frame = new FrmDangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmDangNhap() {
		IconFontSwing.register(FontAwesome.getIconFont());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Đăng nhập Karaoke Fantasy");
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
		
		daoNhanVien = new DAONhanVien();
		daoTK = new DAOTaiKhoan();
		regex = new Regex();
		
		
		
		Image imgHeader = Toolkit.getDefaultToolkit ().getImage ("data\\imgDangNhap\\bgHeader.png");
		JLabel lbHeaderDN = new JLabel("");
		lbHeaderDN.setBounds(0, 0, 488, 87);
		getContentPane().add(lbHeaderDN);
		Image imgresize = imgHeader.getScaledInstance(lbHeaderDN.getWidth(), lbHeaderDN.getHeight(), 0);
		lbHeaderDN.setIcon(new ImageIcon(imgresize));
		
		JLabel lblTaiKhoan = new JLabel("Tài khoản:");
		lblTaiKhoan.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTaiKhoan.setForeground(new Color(255, 255, 255));
		lblTaiKhoan.setBounds(61, 194, 112, 20);
		getContentPane().add(lblTaiKhoan);
		
		txtTaiKhoan = new JTextField();
		txtTaiKhoan.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtTaiKhoan.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));

		txtTaiKhoan.setBounds(166, 188, 246, 33);
		getContentPane().add(txtTaiKhoan);
		txtTaiKhoan.setColumns(10);
		
		JLabel lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setForeground(Color.WHITE);
		lblMatKhau.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMatKhau.setBounds(61, 251, 112, 20);
		getContentPane().add(lblMatKhau);
		
		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(166, 244, 246, 33);
		txtMatKhau.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtMatKhau.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));
		getContentPane().add(txtMatKhau);
		
		JLabel lblNewLabel = new JLabel("Đăng nhập");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setBounds(170, 116, 156, 39);
		getContentPane().add(lblNewLabel);
		
		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDangNhap.setBackground(new Color(164, 44,167));
		btnDangNhap.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBounds(166, 324, 176, 33);
		
		Icon iconDangNhap = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 20, new Color(255, 255 ,255));
		btnDangNhap.setIcon(iconDangNhap);
		
		getContentPane().add(btnDangNhap);
		
		btnThoat = new JButton("Thoát");
		btnThoat.setForeground(Color.WHITE);
		btnThoat.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnThoat.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		btnThoat.setBackground(new Color(164, 44, 167));
		btnThoat.setBounds(166, 368, 176, 33);
		Icon iconThoat = IconFontSwing.buildIcon(FontAwesome.POWER_OFF, 20, new Color(255, 255 ,255));
		btnThoat.setIcon(iconThoat);
		
		getContentPane().add(btnThoat);
		
		JLabel lblNhac1 = new JLabel("");
		
		lblNhac1.setBounds(28, 299, 103, 122);
		
		
		Image imgNhac1 = Toolkit.getDefaultToolkit ().getImage ("data\\img\\IconNhac1.png");
		Image resizeNhac1 = imgNhac1.getScaledInstance(lblNhac1.getWidth(), lblNhac1.getHeight(), 0);
		lblNhac1.setIcon(new ImageIcon(resizeNhac1));
		getContentPane().add(lblNhac1);
		
		JLabel lblNhac2 = new JLabel("");
		lblNhac2.setBounds(356, 299, 103, 122);
		getContentPane().add(lblNhac2);
		Image imgNhac2 = Toolkit.getDefaultToolkit ().getImage ("data\\img\\IconNhac2.png");
		Image resizeNhac2 = imgNhac2.getScaledInstance(lblNhac2.getWidth(), lblNhac2.getHeight(), 0);
		lblNhac2.setIcon(new ImageIcon(resizeNhac2));
		
		lblQuenMK = new JLabel("Quên mật khẩu?");
		lblQuenMK.setForeground(Color.WHITE);
		lblQuenMK.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblQuenMK.setBounds(322, 288, 90, 14);
		getContentPane().add(lblQuenMK);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 488, 465);
		getContentPane().add(lblBackground);
		Image imgBackground = Toolkit.getDefaultToolkit ().getImage ("data\\img\\imgGradient.jpg");
		Image resizeBackground = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBackground));
		
		
		
		txtTaiKhoan.setText("NV002");
		txtMatKhau.setText("QL003");
		
		
		
		btnDangNhap.addActionListener(this);
		btnThoat.addActionListener(this);
		
		lblQuenMK.addMouseListener(this);
		
	}

	
	//Kiểm tra đăng nhập
	public void dangNhap() {
		
		String maTK = txtTaiKhoan.getText().toString().trim();
		String mk = txtMatKhau.getText().toString().trim();
		TaiKhoan tk = daoTK.getTaiKhoanTheoMa(maTK);
		
		
		if(tk.getMaTK() == null) {
			JOptionPane.showMessageDialog(this, "Tài khoản không đúng!\nVui lòng kiểm tra lại.");
		}
		else if(!tk.getMatKhau().equalsIgnoreCase(mk)){
			JOptionPane.showMessageDialog(this, "Mật khẩu không đúng!\nVui lòng kiểm tra lại.");
		}
		else {
			NhanVien nv = daoNhanVien.getNVTheoTK(tk.getMaTK());
			FrmQuanLy frmQL = new FrmQuanLy(nv);
			frmQL.setVisible(true);
			this.setVisible(false);
		}
		
		
		
	}
	
	//event
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThoat)) {
			System.exit(0);
		}
		if(o.equals(btnDangNhap)) {	
			
			dangNhap();
			
		}
		
		
		
	}
	
	public void loadFrmQuenMK() {
	
		FrmQuenMK frmMK = new FrmQuenMK();
		frmMK.setVisible(true);
		this.setVisible(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(lblQuenMK)) {
			loadFrmQuenMK();
		}
	
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
}
