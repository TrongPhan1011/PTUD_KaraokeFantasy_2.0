package app;


import java.awt.Color;
import java.awt.Cursor;
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

import com.formdev.flatlaf.FlatLightLaf;

import connection.ConnectDB;
import dao.DAONhanVien;
import dao.DAOTaiKhoan;
import entity.NhanVien;
import entity.TaiKhoan;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmDangNhap extends JFrame implements ActionListener,MouseListener, KeyListener {

	
	private static final long serialVersionUID = 1L;
	private JTextField txtTaiKhoan;
	private JButton btnThoat;
	private JButton btnDangNhap;
	
	private DAONhanVien daoNhanVien;
	private DAOTaiKhoan daoTK;
	private JPasswordField txtMatKhau;
	private JLabel lblQuenMK;
	private JPopupMenu popUp;
	private JMenuItem popItem;
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
					IconFontSwing.register(FontAwesome.getIconFont());

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
		ImageIcon logoApp = (ImageIcon) IconFontSwing.buildIcon(FontAwesome.FOURSQUARE, 50, new Color(164, 44,167));
		setIconImage(logoApp.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("????ng nh???p Karaoke Fantasy");
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
		
		
		
		Image imgHeader = Toolkit.getDefaultToolkit ().getImage("data\\imgDangNhap\\bgHeader.png");
		JLabel lbHeaderDN = new JLabel("");
		lbHeaderDN.setBounds(0, 0, 488, 87);
		getContentPane().add(lbHeaderDN);
		Image imgresize = imgHeader.getScaledInstance(lbHeaderDN.getWidth(), lbHeaderDN.getHeight(), 0);
		lbHeaderDN.setIcon(new ImageIcon(imgresize));
		
		JLabel lblTaiKhoan = new JLabel("T??i kho???n:");
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
		
		JLabel lblMatKhau = new JLabel("M???t kh???u:");
		lblMatKhau.setForeground(Color.WHITE);
		lblMatKhau.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMatKhau.setBounds(61, 251, 112, 20);
		getContentPane().add(lblMatKhau);
		
		txtMatKhau = new JPasswordField();
		txtMatKhau.setBounds(166, 244, 246, 33);
		txtMatKhau.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtMatKhau.setBorder(BorderFactory.createLineBorder(new Color(217,132,219)));
		getContentPane().add(txtMatKhau);
		
		JLabel lblNewLabel = new JLabel("????ng nh???p");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setBounds(170, 116, 156, 39);
		getContentPane().add(lblNewLabel);
		
		btnDangNhap = new JButton("????ng nh???p");
		btnDangNhap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDangNhap.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnDangNhap.setBackground(new Color(164, 44,167));
		btnDangNhap.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBounds(166, 324, 176, 33);
		
		Icon iconDangNhap = IconFontSwing.buildIcon(FontAwesome.SIGN_IN, 20, new Color(255, 255 ,255));
		btnDangNhap.setIcon(iconDangNhap);
		
		getContentPane().add(btnDangNhap);
		
		btnThoat = new JButton("Tho??t");
		btnThoat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		
		lblQuenMK = new JLabel("Qu??n m???t kh???u?");
		lblQuenMK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblQuenMK.setForeground(Color.WHITE);
		lblQuenMK.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lblQuenMK.setBounds(322, 288, 103, 14);
		getContentPane().add(lblQuenMK);
		
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 488, 465);
		getContentPane().add(lblBackground);
		Image imgBackground = Toolkit.getDefaultToolkit ().getImage ("data\\img\\imgGradient.jpg");
		Image resizeBackground = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBackground));
		
		popUp = new JPopupMenu();
		popItem = new JMenuItem("Tr??? gi??p");
		popUp.add(popItem);
		
		addMouseListener(new MouseAdapter() {
	         public void mouseReleased(MouseEvent me) {
	            showPopup(me); 
	         }
	    });
		
		
		txtTaiKhoan.setText("NV002");
		txtMatKhau.setText("NV002");
		
		
		
		btnDangNhap.addActionListener(this);
		btnThoat.addActionListener(this);
		
		lblQuenMK.addMouseListener(this);
		
		btnDangNhap.addKeyListener(this);
		btnThoat.addKeyListener(this);
		txtMatKhau.addKeyListener(this);
		txtTaiKhoan.addKeyListener(this);
		popItem.addActionListener(this);
	}
	
	 void showPopup(MouseEvent me) {
	      if(me.isPopupTrigger())
	         popUp.show(me.getComponent(), me.getX(), me.getY());
	   }

	
	//Ki???m tra ????ng nh???p
	@SuppressWarnings("deprecation")
	public void dangNhap() {
		
		String maTK = txtTaiKhoan.getText().toString().trim();
		String mk = txtMatKhau.getText().toString().trim();
		TaiKhoan tk = daoTK.getTaiKhoanTheoMa(maTK);
		
		
		if(tk.getMaTK() == null) {
			JOptionPane.showMessageDialog(this, "T??i kho???n kh??ng ????ng!\nVui l??ng ki???m tra l???i.");
		}
		else if(!tk.getMatKhau().equalsIgnoreCase(mk)){
			JOptionPane.showMessageDialog(this, "M???t kh???u kh??ng ????ng!\nVui l??ng ki???m tra l???i.");
		}
		else {
			NhanVien nv = daoNhanVien.getNVTheoTK(tk.getMaTK());
			if(!nv.getTrangThaiLamViec().equalsIgnoreCase("???? ngh??? vi???c")){
				FrmQuanLy frmQL = new FrmQuanLy(nv);
				frmQL.setVisible(true);
				this.setVisible(false);
			}
			else JOptionPane.showMessageDialog(this, "Nh??n vi??n ???? ngh??? vi???c!\n Vui l??ng ????ng nh???p t??i kho???n kh??c");
		}
		
		
		
	}
	
	//event
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnThoat)) {
			System.exit(0);
		}
		else if(o.equals(btnDangNhap)) {	
			
			dangNhap();
			
		}
		else if(o.equals(popItem)) {
			String[] commands = {"cmd", "/c", "data\\help\\HELP.chm"};
			try {
				Runtime.getRuntime().exec(commands);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		if(o.equals(txtTaiKhoan)&& key == KeyEvent.VK_ENTER ) {
			txtMatKhau.requestFocus();
		}
		else if(o.equals(txtMatKhau)&& key == KeyEvent.VK_ENTER ) {
			btnDangNhap.doClick();
		}
		else if(o.equals(txtTaiKhoan)&& key == KeyEvent.VK_TAB ) {
			txtMatKhau.requestFocus();
		}
		else if(o.equals(txtMatKhau)&& key == KeyEvent.VK_TAB ) {
			btnDangNhap.requestFocus();
		}
		else if(o.equals(btnDangNhap)&& key == KeyEvent.VK_TAB ) {
			btnThoat.requestFocus();
		}
		
		else if(key == KeyEvent.VK_Q) {
			loadFrmQuenMK();
		}
		
		else if(key == KeyEvent.VK_ENTER) {
			btnDangNhap.doClick();
		}
		else if(key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
