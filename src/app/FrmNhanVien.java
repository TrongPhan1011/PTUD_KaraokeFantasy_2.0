package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mindfusion.common.HorizontalAlignment;
import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import connection.ConnectDB;
import dao.*;
import entity.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FrmNhanVien extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnTim, btnThemNV, btnSuaNV, btnHuy, btnLamMoiNV;
	private Panel pMain;
	private String sHeaderTenNV, sHeaderMaNV;
	private Date dNgayHienTai;
	private LocalDate now;
	private Date dNow;
	private JLabel lblNVDaNghiViec, lblSubGioTheoCa;
	private JTextField txtTim, txtHoTen, txtSDT, txtCccd, txtDiaChi;
	private JComboBox<Object> cboChucVu, cboGioiTinh, cboCaLamViec, cboSapXep;
	private JCheckBox chkTatCa;
	private JRadioButton rdoTheoMaNV, rdoTheoTenNV, rdoTheoChucVuNV;
	private ButtonGroup bg;
	private JTable tblNV;
	private DefaultTableModel modelNV;
	private SimpleDateFormat dfNgaySinh=new SimpleDateFormat("dd/MM/yyyy"), dfSQLNgaySinh=new SimpleDateFormat("yyyy/MM/dd");
	private DecimalFormat dfLuong=new DecimalFormat("###,###"), dfPV=new DecimalFormat("PV"), dfTN=new DecimalFormat("TN"), dfQL=new DecimalFormat("QL");
	private JDateChooser chooserNgaySinh;

	private DAONhanVien daoNhanVien; 
	private DAOPhatSinhMa daoPhatSinhMa;
	private DAOTaiKhoan daoTaiKhoan;
	private Regex regex;

	private NhanVien nv;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;

	public Panel getPanel() {
		return pMain;
	}

	public  FrmNhanVien(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		//connect db
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//DAO
		daoNhanVien=new DAONhanVien();
		daoPhatSinhMa=new DAOPhatSinhMa();
		daoTaiKhoan=new DAOTaiKhoan();
		regex=new Regex();

		//Entity
		NhanVien nv=new NhanVien();

		//frameNV
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1278, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);
		
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 312, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);
		
		lblNhapThongTin = new JLabel("Nhập thông tin nhân viên");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 292, 29);
		pNhapThongTin.add(lblNhapThongTin);
		

		
				//thongtinNV
				//hoten
		JLabel lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setBounds(10, 61, 90, 29);
		pNhapThongTin.add(lblHoTen);
		lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));
		txtHoTen = new JTextField();
		txtHoTen.setBounds(110, 62, 191, 28);
		pNhapThongTin.add(txtHoTen);
		txtHoTen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				
				
				
				
						//test data nhanh
		txtHoTen.setText("Đinh Quang Tuấn");
						
								//sdt
		JLabel lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(10, 105, 46, 19);
		pNhapThongTin.add(lblSDT);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		txtSDT = new JTextField();
		txtSDT.setBounds(110, 100, 191, 28);
								pNhapThongTin.add(txtSDT);
								txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
								txtSDT.setColumns(10);
								txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
								txtSDT.setText("0944302210");
								
										//diachi
										JLabel lblDiaChi = new JLabel("Địa chỉ:");
										lblDiaChi.setBounds(10, 139, 72, 20);
										pNhapThongTin.add(lblDiaChi);
										lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
										txtDiaChi = new JTextField();
										txtDiaChi.setBounds(110, 136, 191, 28);
										pNhapThongTin.add(txtDiaChi);
										txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
										txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
										txtDiaChi.setText("118 Hoàng Văn Thụ, Q.Phú Nhuận, Tp.HCM");
										
												//cccc
												JLabel lblCccd = new JLabel("CCCD:");
												lblCccd.setBounds(10, 175, 72, 24);
												pNhapThongTin.add(lblCccd);
												lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));
												txtCccd = new JTextField();
												txtCccd.setBounds(110, 175, 191, 28);
												pNhapThongTin.add(txtCccd);
												txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 15));
												txtCccd.setColumns(10);
												txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
												txtCccd.setText("123456789012");
												
														//gioitinh
														JLabel lblGioiTinh = new JLabel("Giới tính:");
														lblGioiTinh.setBounds(11, 249, 88, 23);
														pNhapThongTin.add(lblGioiTinh);
														lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
														cboGioiTinh = new JComboBox<Object>(new Object[] {"Nam", "Nữ"});
														cboGioiTinh.setBounds(111, 247, 191, 25);
														pNhapThongTin.add(cboGioiTinh);
														cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
														cboGioiTinh.setBackground(new Color(235, 235, 235));
														cboGioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
														
																//ngaysinh
																JLabel lblNgaySinh = new JLabel("Ngày sinh:");
																lblNgaySinh.setBounds(10, 214, 90, 23);
																pNhapThongTin.add(lblNgaySinh);
																lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
																
																		//JDateChooser
																		chooserNgaySinh = new JDateChooser();
																		chooserNgaySinh.setBounds(110, 213, 191, 25);
																		pNhapThongTin.add(chooserNgaySinh);
																		chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
																		chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																		chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
																		chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
																		chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
																		
																				//chucvu
																				JLabel lblChucVu = new JLabel("Chức vụ:");
																				lblChucVu.setBounds(10, 286, 98, 19);
																				pNhapThongTin.add(lblChucVu);
																				lblChucVu.setFont(new Font("SansSerif", Font.BOLD, 15));
																				cboChucVu = new JComboBox<Object>(new Object[] {"Quản lý", "Phục vụ", "Thu ngân"});
																				cboChucVu.setBounds(110, 283, 191, 25);
																				pNhapThongTin.add(cboChucVu);
																				cboChucVu.setFont(new Font("SansSerif", Font.PLAIN, 15));
																				cboChucVu.setBackground(new Color(235, 235, 235));
																				cboChucVu.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																				
																						//calamviec
																						JLabel lblCaLamViec = new JLabel("Ca làm việc:");
		lblCaLamViec.setBounds(10, 323, 90, 20);
		pNhapThongTin.add(lblCaLamViec);
		lblCaLamViec.setFont(new Font("SansSerif", Font.BOLD, 15));
		cboCaLamViec = new JComboBox<Object>(new Object[] {"1", "2", "3"});
		cboCaLamViec.setBounds(110, 319, 56, 25);
		pNhapThongTin.add(cboCaLamViec);
		cboCaLamViec.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboCaLamViec.setBackground(new Color(235, 235, 235));
		cboCaLamViec.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																						
																								//sub gio lam viec theo ca
		lblSubGioTheoCa = new JLabel("08:00 AM - 13:00 PM");
		lblSubGioTheoCa.setBounds(167, 323, 145, 20);
		pNhapThongTin.add(lblSubGioTheoCa);
		lblSubGioTheoCa.setFont(new Font("SansSerif", Font.PLAIN, 15));
																						
																								//su kien
		cboCaLamViec.addActionListener(this);

		//lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(515, 11, 90, 35);
		pMain.add(lblTim);

		//txtTim
		txtTim = new JTextField();
		txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(597, 11, 526, 33);
		txtTim.addFocusListener(new FocusAdapter() {	//place holder
			@Override
			public void focusGained(FocusEvent e) {
				if(txtTim.getText().equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc.")) {
					txtTim.setFont(new Font("SansSerif", Font.PLAIN, 15));
					txtTim.setForeground(Color.BLACK);
					txtTim.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtTim.getText().equals("")) {
					txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
					txtTim.setForeground(Colors.LightGray);
					txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc.");
				}
			}
		});
		pMain.add(txtTim);

		//btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1133, 12, 127, 33);
		Image imgTim = Toolkit.getDefaultToolkit().getImage("data\\img\\iconKinhLup.png");
		Image resizeImgTim = imgTim.getScaledInstance(20, 20, 0);
		btnTim.setIcon(new ImageIcon(resizeImgTim));
		pMain.add(btnTim);

		//lbl NV da nghi viec
		lblNVDaNghiViec = new JLabel();
		lblNVDaNghiViec.setHorizontalAlignment(SwingConstants.CENTER);
		lblNVDaNghiViec.setForeground(Color.RED);
		lblNVDaNghiViec.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblNVDaNghiViec.setBounds(10, 355, 291, 28);
		pNhapThongTin.add(lblNVDaNghiViec);

		//btnthem,sua,xoa,lammoiNV
		btnThemNV = new FixButton("Thêm");
		btnThemNV.setForeground(Color.WHITE);
		btnThemNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemNV.setBackground(new Color(114, 23, 153));
		btnThemNV.setBounds(10, 423, 291, 35);
		Image imgThemNV = Toolkit.getDefaultToolkit().getImage("data\\img\\iconGrayThem.png");
		Image resizeImgThemNV = imgThemNV.getScaledInstance(25, 25, 0);
		btnThemNV.setIcon(new ImageIcon(resizeImgThemNV));
		pNhapThongTin.add(btnThemNV);

		btnSuaNV = new FixButton("Sửa");
		btnSuaNV.setForeground(Color.WHITE);
		btnSuaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaNV.setBackground(new Color(114, 23, 153));
		btnSuaNV.setBounds(10, 469, 291, 35);
		Image imgSuaNV = Toolkit.getDefaultToolkit().getImage("data\\img\\iconTool.png");
		Image resizeImgSuaNV = imgSuaNV.getScaledInstance(25, 25, 0);
		btnSuaNV.setIcon(new ImageIcon(resizeImgSuaNV));
		pNhapThongTin.add(btnSuaNV);

		btnHuy = new FixButton("Hủy");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHuy.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnHuy.setBackground(new Color(114, 23, 153));
		btnHuy.setBounds(10, 515, 291, 35);
		Image imgXoaNV = Toolkit.getDefaultToolkit().getImage("data\\img\\iconRemove.png");
		Image resizeImgXoaNV = imgXoaNV.getScaledInstance(25, 25, 0);
		btnHuy.setIcon(new ImageIcon(resizeImgXoaNV));
		pNhapThongTin.add(btnHuy);

		btnLamMoiNV = new FixButton("Làm mới");
		btnLamMoiNV.setForeground(Color.WHITE);
		btnLamMoiNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiNV.setBackground(new Color(114, 23, 153));
		btnLamMoiNV.setBounds(10, 561, 291, 35);
		Image imgLamMoiNV = Toolkit.getDefaultToolkit().getImage("data\\img\\iconReset.png");
		Image resizeImgLamMoiNV = imgLamMoiNV.getScaledInstance(25, 25, 0);
		btnLamMoiNV.setIcon(new ImageIcon(resizeImgLamMoiNV));
		pNhapThongTin.add(btnLamMoiNV);

		//sapxep
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(201, 194, 237));
		pSapXep.setBounds(332, 50, 928, 46);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);

		cboSapXep = new JComboBox<Object>(new Object[] {"Tăng dần", "Giảm dần"});
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBackground(Color.WHITE);
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBounds(26, 12, 121, 28);
		pSapXep.add(cboSapXep);

		chkTatCa = new JCheckBox("Tất cả");
		chkTatCa.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkTatCa.setBackground(new Color(201, 194, 237));
		chkTatCa.setBounds(195, 13, 113, 25);
		chkTatCa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1)
					loadDanhSachNV(nv);
				else
					removeDanhSachNV(modelNV);
			}
		});
		pSapXep.add(chkTatCa);

		rdoTheoMaNV = new JRadioButton("Theo mã nhân viên");
		rdoTheoMaNV.setBounds(305, 13, 159, 25);
		rdoTheoMaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaNV.setBackground(new Color(201, 194, 237));
		pSapXep.add(rdoTheoMaNV);

		rdoTheoTenNV = new JRadioButton("Theo tên nhân viên");
		rdoTheoTenNV.setBounds(508, 13, 161, 25);
		rdoTheoTenNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenNV.setBackground(new Color(201, 194, 237));
		pSapXep.add(rdoTheoTenNV);

		rdoTheoChucVuNV = new JRadioButton("Theo chức vụ nhân viên");
		rdoTheoChucVuNV.setBounds(705, 13, 195, 25);
		rdoTheoChucVuNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoChucVuNV.setBackground(new Color(201, 194, 237));
		pSapXep.add(rdoTheoChucVuNV);

		bg=new ButtonGroup();
		bg.add(btnThemNV); bg.add(btnSuaNV); bg.add(btnHuy); bg.add(btnLamMoiNV);
		bg.add(rdoTheoMaNV); bg.add(rdoTheoTenNV); bg.add(rdoTheoChucVuNV);

		//bangthongtinNV
		JScrollPane scrollPaneNV = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneNV.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneNV.setBackground(new Color(164, 44, 167));
		scrollPaneNV.setBounds(332, 102, 928, 516);
		scrollPaneNV.getHorizontalScrollBar();
		pMain.add(scrollPaneNV);

		String col[] = {"Mã NV", "Họ và tên nhân viên", "Chức vụ", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "CCCD", "Lương", "Ca làm việc", "Trạng thái làm việc", "Mật khẩu"};
		modelNV = new DefaultTableModel(col, 0);

		tblNV = new JTable(modelNV);
		tblNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblNV.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblNV.setShowHorizontalLines(true); 
		tblNV.setShowGrid(true);
		tblNV.setBackground(Color.white);
		tblNV.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblNV.setSelectionBackground(new Color(164, 44, 167, 30));
		tblNV.setSelectionForeground(new Color(114, 23, 153));
		tblNV.setRowHeight(30);
		tblNV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JTableHeader tbHeader = tblNV.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

		tblNV.getColumnModel().getColumn(0).setPreferredWidth(60); //maNV
		tblNV.getColumnModel().getColumn(1).setPreferredWidth(155);//tenNV
		tblNV.getColumnModel().getColumn(2).setPreferredWidth(80); //chucvu
		tblNV.getColumnModel().getColumn(3).setPreferredWidth(75); //gioitinh
		tblNV.getColumnModel().getColumn(4).setPreferredWidth(80); //ngaysinh
		tblNV.getColumnModel().getColumn(5).setPreferredWidth(270); //diachi
		tblNV.getColumnModel().getColumn(6).setPreferredWidth(90); //sdt
		tblNV.getColumnModel().getColumn(7).setPreferredWidth(100); //cccd
		tblNV.getColumnModel().getColumn(8).setPreferredWidth(70); //luong
		tblNV.getColumnModel().getColumn(9).setPreferredWidth(90); //calamviec
		tblNV.getColumnModel().getColumn(10).setPreferredWidth(145);//trangthai
		tblNV.getColumnModel().getColumn(11).setPreferredWidth(120);//matkhau

		DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tblNV.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);

		//tableNV.setOpaque(false);
		scrollPaneNV.setViewportView(tblNV);
		
//		background 
		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);

		btnTim.addActionListener(this);
		btnThemNV.addActionListener(this);
		btnSuaNV.addActionListener(this);
		btnHuy.addActionListener(this);
		btnLamMoiNV.addActionListener(this);

		cboSapXep.addActionListener(this);
		chkTatCa.addActionListener(this);
		rdoTheoMaNV.addActionListener(this);
		rdoTheoTenNV.addActionListener(this);
		rdoTheoChucVuNV.addActionListener(this);

		tblNV.addMouseListener(this);
	}

	//xoa het data trong table
	private void removeDanhSachNV(DefaultTableModel defaultTableModel) {
		//		DefaultTableModel dtm = (DefaultTableModel) tableNV.getModel();
		//		dtm.getDataVector().removeAllElements();

		while(tblNV.getRowCount() > 0){
			modelNV.removeRow(0);
		}
	}

	//xoa trang textfield va textarea
	private void xoaTrang() {
		txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);

		txtHoTen.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCccd.setText("");
		//			dateChooserNgaySinh.setDate(new Date(0));
		lblNVDaNghiViec.setText("");

		chkTatCa.setSelected(false);
		rdoTheoMaNV.setSelected(false);
		rdoTheoTenNV.setSelected(false);
		rdoTheoChucVuNV.setSelected(false);
	}

	//chon va sub gio theo ca
	private void subGioTheoCa() {
		if(cboCaLamViec.getSelectedItem() == "1") {
			lblSubGioTheoCa.setText("08:00 AM - 13:00 PM");
		}
		if(cboCaLamViec.getSelectedItem() == "2") {
			lblSubGioTheoCa.setText("13:00 PM - 18:00 PM");
		}
		if(cboCaLamViec.getSelectedItem() == "3") {
			lblSubGioTheoCa.setText("18:00 PM - 24:00 PM");
		}
	}	

	//load dsNV
	private void loadDanhSachNV(NhanVien nv)  {
		//clearTable();
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.getDanhSachNV();
		for(NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//////////cac phuong thuc cua timNV
	//load 1 NV
	private void loadNV(NhanVien nv) {
		TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(nv.getMaNhanVien());
		modelNV.setRowCount(0);
		modelNV.addRow(new Object[] {
				nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(), 
				dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(), 
				dfLuong.format(Math.round(nv.getLuong())), nv.getCaLamViec(), nv.getTrangThaiLamViec(), tk.getMatKhau()	
		});
	}

	//load 1 NV da nghi viec
	private void loadNVDaNghiViec(NhanVien nvNghi) {
		TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(nvNghi.getMaNhanVien());
		modelNV.setRowCount(0);
		modelNV.addRow(new Object[] {
				nvNghi.getMaNhanVien(), nvNghi.getTenNhanVien(), nvNghi.getChucVu(), nvNghi.getGioiTinh(), 
				dfNgaySinh.format(nvNghi.getNgaySinh()), nvNghi.getDiaChi(), nvNghi.getSdt(), nvNghi.getCccd(), 
				dfLuong.format(Math.round(nvNghi.getLuong())), nvNghi.getCaLamViec(), nvNghi.getTrangThaiLamViec(), tk.getMatKhau()	
		});
	}

	private void loadDanhSachTenNV(ArrayList<NhanVien> nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstName = daoNhanVien.getTenNV(txtTim.getText());
		for(NhanVien infoNV : lstName) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}
	private void loadDanhSachChucVuNV(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstCV = daoNhanVien.getChucVuNV(txtTim.getText().toLowerCase().trim());
		for(NhanVien infoNV : lstCV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	private void loadDanhSachCaNV(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstCa = daoNhanVien.getCaNV(txtTim.getText());
		for(NhanVien infoNV : lstCa) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//timNV
	private void findNV() {
		String input = txtTim.getText().toLowerCase().trim();
		NhanVien nv1 = daoNhanVien.getMaVaSDTNV(input);
		ArrayList<NhanVien> nv2 = daoNhanVien.getTenNV(input);
		if(!input.equals("") && !input.equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc.")) {
			String messTenNV = "\n - Họ tên. Ví dụ: Nguyễn Văn A";
			String messCV =    "\n - Tìm theo chức vụ: phục vụ, thu ngân, quản lý";
			String messSDT =   "\n - SĐT gồm 10 chữ số và bắt đầu bằng số 0";
			String messCa =    "\n - Tìm theo ca: 1, 2, 3";

			if(regex.regexTimKiemMaNV(txtTim)) { //ok
				try {
					loadNV(nv1);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã nhân viên!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}
			else if(regex.regexTenNV(txtTim)) { 
				try {
					loadDanhSachTenNV(nv2);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy tên nhân viên!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}
			else if(regex.regexTimKiemChucVu(txtTim)) { 
				loadDanhSachChucVuNV(nv);
			}
			else if(regex.regexSDT(txtTim)) { //ok
				try {
					loadNV(nv1);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy số điện thoại nhân viên!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}
			else if(regex.regexTimKiemCa(txtTim)) { 
				loadDanhSachCaNV(nv);
			}
			else
				JOptionPane.showMessageDialog(null, "Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã nhân viên. Ví dụ: NV001" +messTenNV +messSDT +messCV +messCa, "Thông báo", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
	}
	//////////	

	//themNV vao sql
	private void addNV() {
		try {
			//năm hien tai
			now = LocalDate.now();
			int nam = now.getYear();
			int thang = now.getMonthValue();
			int ngay = now.getDayOfMonth();
			dNow = new Date(nam, thang, ngay);

			String phatSinhMaNV = daoPhatSinhMa.getMaNV();
			String hoTen = txtHoTen.getText();
			String sdt = txtSDT.getText();
			String diaChi = txtDiaChi.getText();
			String chucVu = cboChucVu.getSelectedItem().toString();
			String cccd = txtCccd.getText();
			String gioiTinh = cboGioiTinh.getSelectedItem().toString();

			java.util.Date date = chooserNgaySinh.getDate();

			Date date1 = new Date(date.getYear(), date.getMonth(), date.getDate());
			
			
			
			int age = nam - date1.getYear();
			//			int age = now.getYear() - date1.getYear();
			//			int age = dNow.getYear() - date1.getYear();
			//

			int caLamViec = Integer.parseInt((String) cboCaLamViec.getSelectedItem());

			TaiKhoan tk=new TaiKhoan(phatSinhMaNV);
			String matKhau = phatSinhMaNV.concat(sdt); //String matKhau = ""+phatSinhMaNV +sdt;

			if(age>=18) {
				if(regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi) && regex.regexCCCD(txtCccd)) {

					//					if(day>0 && day<=31 && month>0 && month<=12 && year>0 && year<nam) { 
					if(date1.getDate()>0 && date1.getDate()<=31 && date1.getMonth()>0 && date1.getMonth()<=12 && date1.getYear()>0 && date1.getYear()<nam) { 
						TaiKhoan tk1=new TaiKhoan();
						tk1.setMaTK(phatSinhMaNV);
						tk1.setMatKhau(matKhau);
						try {
							new DAOTaiKhoan().createTK(tk1);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}

						//them vao data
						NhanVien nv=new NhanVien();
						nv.setMaNhanVien(phatSinhMaNV);
						nv.setTaiKhoan(tk);
						nv.setTenNhanVien(hoTen);
						nv.setChucVu(chucVu);
						nv.setGioiTinh(gioiTinh);
//						nv.setNgaySinh((Date) ngaySinh);
						nv.setNgaySinh(date1);
						nv.setDiaChi(diaChi);
						nv.setSdt(sdt);
						nv.setCccd(cccd);
						nv.setLuong(200000);
						nv.setCaLamViec(caLamViec);
						nv.setTrangThaiLamViec("Đang làm việc");
						try {
							new DAONhanVien().themNV(nv);
						}catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
						}

						
						//them vao table  
						xoaTrang();
						modelNV.addRow(new Object[] {
								phatSinhMaNV, hoTen, chucVu, gioiTinh, 
								dfNgaySinh.format(chooserNgaySinh.getDate()), diaChi, sdt, cccd,
								dfLuong.format(Math.round(200000)), caLamViec, "Đang làm việc", matKhau
						});
						String mkTK = "\nMật khẩu: "+matKhau;
						JOptionPane.showMessageDialog(this, "Thêm thành công!\nMã tài khoản: "+phatSinhMaNV +mkTK, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					}
					//				}catch(Exception e1) {
					//					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Nhân viên làm việc phải trên 18 tuổi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
	}

	//huyTaiKhoanNV_chuyen trangThaiLamViec dang lam viec thanh da nghi viec
	private boolean cancelNV() {
		int row = tblNV.getSelectedRow();
		if(row>=0) {
			int cancel = JOptionPane.showConfirmDialog(null, "Bạn muốn hủy tài khoản nhân viên này?", "Thông báo", JOptionPane.YES_NO_OPTION);
			if(cancel == JOptionPane.YES_OPTION) {
				NhanVien nv=new NhanVien();
				String maNV = (String) tblNV.getValueAt(row, 0);
				try {
					modelNV.removeRow(row);
					removeDanhSachNV(modelNV);
					new DAONhanVien().huyNV(maNV);
					loadDanhSachNV(nv);
					JOptionPane.showMessageDialog(null, "Đã hủy tài khoản!", "Thông báo", JOptionPane.OK_OPTION);
				}catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Hủy tài khoản thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin tài khoản nhân viên cần hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

	//suaNV
	private void updateNV() {
		int row = tblNV.getSelectedRow();
		if(row>=0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin nhân viên này?", "Thông báo", JOptionPane.YES_NO_OPTION);
			if(update == JOptionPane.YES_OPTION) {
				NhanVien nv=new NhanVien();
				String maNV = (String) tblNV.getValueAt(row, 0);
				java.util.Date date = chooserNgaySinh.getDate();
				Date date1=new Date(date.getYear(), date.getMonth(), date.getDate());
				int caLamViec = Integer.parseInt((String) cboCaLamViec.getSelectedItem());
				try {
					if(regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi) && regex.regexCCCD(txtCccd)) {
						nv.setTenNhanVien(txtHoTen.getText());
						nv.setChucVu((String) cboChucVu.getSelectedItem());
						nv.setGioiTinh((String) cboGioiTinh.getSelectedItem());
						nv.setNgaySinh(date1);
						nv.setDiaChi(txtDiaChi.getText());
						nv.setSdt(txtSDT.getText());
						nv.setCccd(txtCccd.getText());
						nv.setCaLamViec(caLamViec);

						new DAONhanVien().capNhatNV(nv, maNV);
						removeDanhSachNV(modelNV);
						loadDanhSachNV(nv);

						JOptionPane.showMessageDialog(this, "Thông tin nhân viên đã được sửa!", "Thông báo", JOptionPane.OK_OPTION);
					}
				}catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin nhân viên cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}
	}

	//sapxep tang dan manv
	private void sortMaNVTangDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.sortMaNV("ASC");
		for(NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//sapxep giam dan manv
	private void sortMaNVGiamDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.sortMaNV("DESC");
		for(NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//sapxep tang dan tennv
	private void sortTenNVTangDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.getDanhSachNV();

		Collections.sort(lstNV, new Comparator<NhanVien>() {

			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				return o1.getTenNhanVien().compareTo(o2.getTenNhanVien());
			}
		});

		for(NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//sapxep giam dan tennv
	private void sortTenNVGiamDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.getDanhSachNV();

		Collections.sort(lstNV, new Comparator<NhanVien>() {

			@Override
			public int compare(NhanVien o1, NhanVien o2) {
				return o2.getTenNhanVien().compareTo(o1.getTenNhanVien());
			}
		});

		for(NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}	

	//sx chucvu tang dan: PV, TN, QL
	private void sortChucVuTangDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstPV = daoNhanVien.sortCV("Phục vụ");
		for(NhanVien infoNV : lstPV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstTN = daoNhanVien.sortCV("Thu ngân");
		for(NhanVien infoNV : lstTN) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstQL = daoNhanVien.sortCV("Quản lý");
		for(NhanVien infoNV : lstQL) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	//sx chucvu tang dan: PV, TN, QL
	private void sortChucVuGiamDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstQL = daoNhanVien.sortCV("Quản lý");
		for(NhanVien infoNV : lstQL) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstTN = daoNhanVien.sortCV("Thu ngân");
		for(NhanVien infoNV : lstTN) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstPV = daoNhanVien.sortCV("Phục vụ");
		for(NhanVien infoNV : lstPV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		//sub giờ làm việc theo ca
		if(o.equals(cboCaLamViec)) {
			subGioTheoCa();
		}

		//tìm NV
		if(o.equals(btnTim)) {
			findNV();
		}

		//thêm NV
		if(o.equals(btnThemNV)) {
			addNV();
		}

		//sửa NV
		if(o.equals(btnSuaNV)) {
			updateNV();
		}

		//hủy
		if(o.equals(btnHuy)) {
			cancelNV();
		}

		//làm mới
		if(o.equals(btnLamMoiNV)) {
			xoaTrang();
			removeDanhSachNV(modelNV);
		}

		//sapxep tang
		if((cboSapXep.getSelectedItem()=="Tăng dần")) {
			if(o.equals(rdoTheoMaNV))	
				sortMaNVTangDan(nv);

			if(o.equals(rdoTheoTenNV)) 
				sortTenNVTangDan(nv);		//sort ten dau cua nv theo a-z

			if(o.equals(rdoTheoChucVuNV)) {
				sortChucVuTangDan(nv);		//sx chucvu tang dan: PV, TN, QL
			}
		}

		//sapxep giam
		if((cboSapXep.getSelectedItem()=="Giảm dần")) {
			if(o.equals(rdoTheoMaNV))
				sortMaNVGiamDan(nv);

			if(o.equals(rdoTheoTenNV)) 
				sortTenNVGiamDan(nv);		//sort ten dau cua nv theo z-a

			if(o.equals(rdoTheoChucVuNV)) {
				sortChucVuGiamDan(nv);		//sx chucvu giam dan: QL, TN, PV
			}
		}

	}
	///////////
	//chon row NV
	private void choose1NV() {
		int selectedRow = tblNV.getSelectedRow();
		if(selectedRow >= 0) {
			String maNV = (String) tblNV.getValueAt(selectedRow, 0);
			String trangThai = (String) tblNV.getValueAt(selectedRow, 10);
			ArrayList<NhanVien> lstNV = daoNhanVien.getAllDanhSachNV();
			for(NhanVien nv : lstNV) {
				if(maNV.equals(nv.getMaNhanVien())) {
					txtHoTen.setText(nv.getTenNhanVien());
					txtSDT.setText(nv.getSdt());
					txtDiaChi.setText(nv.getDiaChi());
					cboChucVu.setSelectedItem(nv.getChucVu());
					txtCccd.setText(nv.getCccd());
					cboGioiTinh.setSelectedItem(nv.getGioiTinh());
					chooserNgaySinh.setDate(nv.getNgaySinh());
					cboCaLamViec.setSelectedItem(nv.getCaLamViec()+"");
					break;
				}
				if(trangThai.equals("Đã nghỉ việc")) {
					lblNVDaNghiViec.setText("ĐÃ NGHỈ VIỆC.");
				}
				if(trangThai.equals("Đang làm việc")) {
					lblNVDaNghiViec.setText("");
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		choose1NV();
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
		// TODO Auto-generated method stub

	}
}
