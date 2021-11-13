package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOKhachHang;
import dao.DAOLoaiKH;
import dao.DAONhanVien;
import dao.DAOPhatSinhMa;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiKH;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class FrmKhachHang extends JPanel implements ActionListener, MouseListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV;
	private String sHeaderTenNV;
	private Date dNgayHienTai;
	private Panel pMain;
	private JTextField txtTK;
	private JButton btnTim;
	private JButton btnThemKH;
	private JButton btnSuaKH;
	private JButton btnXoaKH;
	private JButton btnReset;
	private JTextField txtCccd;
	private JTextField txtPoint;
	private JTable tableKH;
	private DAOLoaiKH daoLoaiKH;
	private DAOKhachHang daoKhachHang;
	private DAOPhatSinhMa daoMaKH;
	private DefaultTableModel modelKhachHang;
	private JComboBox<String> cbbloaiKH;
	private JComboBox<String> cbbgioiTinh;
	private JComboBox<String> cbbSort;
	private JDateChooser dateChooserNgaySinh;
	private JDateChooser dateChooserNgayDangKy;
	private SimpleDateFormat dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dfNgayDangKy = new SimpleDateFormat("dd/MM/yyyy");
	private JRadioButton rdoTheoMaKH;
	private JRadioButton rdoTheoLoaiKH;
	private JRadioButton rdoTheoTenKH;
	private JCheckBox chkAll = new JCheckBox("Tất cả");
	private Regex regex;
	private KhachHang kh;
	private ButtonGroup bg;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	private JTextField txtHoTen;
	private JTextField txtSDT;
	private JTextField txtDiaChi;

	public Panel getFrmKH() {
		return this.pMain;
	}

	public FrmKhachHang(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		// connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// khai bao dao
		daoLoaiKH = new DAOLoaiKH();
		daoKhachHang = new DAOKhachHang();
		daoMaKH = new DAOPhatSinhMa();
		regex = new Regex();

		// Giao dien
		setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		add(pMain);
		pMain.setLayout(null);
		
		
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 333, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);
		
		lblNhapThongTin = new JLabel("Nhập thông tin phòng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 313, 29);
		pNhapThongTin.add(lblNhapThongTin);
		
		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtHoTen.setBounds(153, 64, 170, 27);
		pNhapThongTin.add(txtHoTen);
		
				JLabel lblHoTen = new JLabel("Họ và tên:");
				lblHoTen.setBounds(10, 64, 90, 27);
				pNhapThongTin.add(lblHoTen);
				lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));
				
						JLabel lblSDT = new JLabel("SĐT:");
						lblSDT.setBounds(10, 102, 59, 23);
						pNhapThongTin.add(lblSDT);
						lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
						
								JLabel lblAddress = new JLabel("Địa chỉ:");
								lblAddress.setBounds(10, 133, 84, 27);
								pNhapThongTin.add(lblAddress);
								lblAddress.setFont(new Font("SansSerif", Font.BOLD, 15));
								
								txtSDT = new JTextField();
								txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 14));
								txtSDT.setColumns(10);
								txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
								txtSDT.setBounds(153, 100, 170, 27);
								pNhapThongTin.add(txtSDT);
								
								txtDiaChi = new JTextField();
								txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 14));
								txtDiaChi.setColumns(10);
								txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
								txtDiaChi.setBounds(153, 133, 170, 27);
								pNhapThongTin.add(txtDiaChi);
								
										txtCccd = new JTextField();
										txtCccd.setBounds(153, 167, 170, 27);
										pNhapThongTin.add(txtCccd);
										txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 14));
										txtCccd.setColumns(10);
										txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
										
												JLabel lblCccd = new JLabel("CCCD:");
												lblCccd.setBounds(10, 171, 65, 23);
												pNhapThongTin.add(lblCccd);
												lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));
												
														dateChooserNgaySinh = new JDateChooser();
														dateChooserNgaySinh.setBounds(153, 278, 170, 28);
														pNhapThongTin.add(dateChooserNgaySinh);
														dateChooserNgaySinh.setDateFormatString("dd/MM/yyyy");
														dateChooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
														dateChooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
														dateChooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
														dateChooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
														
																JLabel lblNgaySinh = new JLabel("Ngày sinh:");
																lblNgaySinh.setBounds(10, 278, 102, 28);
																pNhapThongTin.add(lblNgaySinh);
																lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
																
																		JLabel lblNgayDangKy = new JLabel("Ngày đăng ký:");
																		lblNgayDangKy.setBounds(10, 318, 102, 29);
																		pNhapThongTin.add(lblNgayDangKy);
																		lblNgayDangKy.setFont(new Font("SansSerif", Font.BOLD, 15));
																		
																				dateChooserNgayDangKy = new JDateChooser();
																				dateChooserNgayDangKy.setBounds(153, 319, 170, 28);
																				pNhapThongTin.add(dateChooserNgayDangKy);
																				dateChooserNgayDangKy.setDateFormatString("dd/MM/yyyy");
																				dateChooserNgayDangKy.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																				dateChooserNgayDangKy.setFont(new Font("SansSerif", Font.PLAIN, 15));
																				dateChooserNgayDangKy.getCalendarButton().setPreferredSize(new Dimension(30, 24));
																				dateChooserNgayDangKy.getCalendarButton().setBackground(new Color(102, 0, 153));
																				
																						JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
																						lblLoaiKH.setBounds(10, 211, 124, 19);
																						pNhapThongTin.add(lblLoaiKH);
																						lblLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 15));
																						
																								cbbloaiKH = new JComboBox<String>();
																								cbbloaiKH.setBounds(153, 203, 170, 27);
																								pNhapThongTin.add(cbbloaiKH);
																								cbbloaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
																								cbbloaiKH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																								cbbloaiKH.setBackground(new Color(255, 255, 255));
																								
																										JLabel lblGioiTinh = new JLabel("Giới tính:");
																										lblGioiTinh.setBounds(10, 241, 72, 26);
																										pNhapThongTin.add(lblGioiTinh);
																										lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
																										
																												cbbgioiTinh = new JComboBox<String>();
																												cbbgioiTinh.setBounds(153, 241, 170, 28);
																												pNhapThongTin.add(cbbgioiTinh);
																												cbbgioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
																												cbbgioiTinh.setBackground(Color.WHITE);
																												cbbgioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																												
																														JLabel lblDiem = new JLabel("Điểm tích lũy:");
																														lblDiem.setBounds(10, 358, 102, 28);
																														pNhapThongTin.add(lblDiem);
																														lblDiem.setFont(new Font("SansSerif", Font.BOLD, 15));
																														
																																txtPoint = new JTextField();
																																txtPoint.setBounds(153, 358, 170, 28);
																																pNhapThongTin.add(txtPoint);
																																txtPoint.setFont(new Font("SansSerif", Font.PLAIN, 14));
																																txtPoint.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
																																txtPoint.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
																																txtPoint.setEditable(isDisplayable());
																																txtPoint.setColumns(10);

		// lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(487, 12, 90, 35);
		pMain.add(lblTim);

		// txtTK
		txtTK = new JTextField();
		txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		txtTK.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTK.setBounds(577, 11, 526, 33);
		txtTK.addFocusListener(new FocusAdapter() { // place holder
			@Override
			public void focusGained(FocusEvent e) {
				if (txtTK.getText().equals("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.")) {
					txtTK.setText("");
					txtTK.setFont(new Font("SansSerif", Font.PLAIN, 15));
					txtTK.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtTK.getText().equals("")) {
					txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
					txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
					txtTK.setForeground(Colors.LightGray);
				}
			}
		});
		pMain.add(txtTK);

		// btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1125, 11, 132, 33);
		Image imgTim = Toolkit.getDefaultToolkit().getImage("data\\img\\iconKinhLup.png");
		Image resizeImgTim = imgTim.getScaledInstance(20, 20, 0);
		btnTim.setIcon(new ImageIcon(resizeImgTim));
		pMain.add(btnTim);
		Image imgNhac1 = Toolkit.getDefaultToolkit().getImage("data\\img\\IconNhac1.png");
		String cbbGioiTinh[] = { "Nam", "Nữ" };
		for (int i = 0; i < cbbGioiTinh.length; i++) {
			cbbgioiTinh.addItem(cbbGioiTinh[i]);
		}

		btnThemKH = new FixButton("Thêm");
		btnThemKH.setForeground(Color.WHITE);
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemKH.setBackground(new Color(114, 23, 153));
		btnThemKH.setBounds(10, 425, 313, 35);
		Image imgThemKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconGrayThem.png");
		Image resizeImgThemKH = imgThemKH.getScaledInstance(25, 25, 0);
		btnThemKH.setIcon(new ImageIcon(resizeImgThemKH));
		pNhapThongTin.add(btnThemKH);

		btnSuaKH = new FixButton("Sửa");
		btnSuaKH.setForeground(Color.WHITE);
		btnSuaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaKH.setBackground(new Color(114, 23, 153));
		btnSuaKH.setBounds(10, 469, 313, 35);
		Image imgSuaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconTool.png");
		Image resizeImgSuaKH = imgSuaKH.getScaledInstance(25, 25, 0);
		btnSuaKH.setIcon(new ImageIcon(resizeImgSuaKH));
		pNhapThongTin.add(btnSuaKH);

		btnXoaKH = new FixButton("Xóa");
		btnXoaKH.setForeground(Color.WHITE);
		btnXoaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXoaKH.setBackground(new Color(114, 23, 153));
		btnXoaKH.setBounds(10, 515, 313, 35);
		Image imgXoaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconRemove.png");
		Image resizeImgXoaKH = imgXoaKH.getScaledInstance(25, 25, 0);
		btnXoaKH.setIcon(new ImageIcon(resizeImgXoaKH));
		pNhapThongTin.add(btnXoaKH);

		btnReset = new FixButton("Làm mới");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnReset.setBackground(new Color(114, 23, 153));
		btnReset.setBounds(10, 561, 313, 35);
		Image imgLamMoiKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconReset.png");
		Image resizeImgLamMoiKH = imgLamMoiKH.getScaledInstance(25, 25, 0);
		btnReset.setIcon(new ImageIcon(resizeImgLamMoiKH));

		pNhapThongTin.add(btnReset);

		JScrollPane scrollPaneKH = new JScrollPane();
		scrollPaneKH.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneKH.setBackground(new Color(164, 44, 167));
		scrollPaneKH.setBounds(353, 106, 904, 512);
		pMain.add(scrollPaneKH);

		String col[] = { "Mã KH", "Họ và tên KH", "Loại KH", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "CCCD",
				"Ngày đăng ký", "Điểm tích lũy" };
		modelKhachHang = new DefaultTableModel(col, 0);
		tableKH = new JTable(modelKhachHang);

		JTableHeader tbHeader = tableKH.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

		tableKH.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableKH.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableKH.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(5).setPreferredWidth(400);
		tableKH.getColumnModel().getColumn(6).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(7).setPreferredWidth(100);
		tableKH.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableKH.getColumnModel().getColumn(9).setPreferredWidth(100);
		tableKH.setAutoResizeMode(tableKH.AUTO_RESIZE_OFF);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tableKH.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

		tableKH.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tableKH.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tableKH.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);

		tableKH.setShowGrid(true);
		tableKH.setShowHorizontalLines(true);
		tableKH.setBackground(Color.WHITE);
		tableKH.setSelectionBackground(new Color(164, 44, 167, 30));
		tableKH.setSelectionForeground(new Color(114, 23, 153));
		tableKH.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tableKH.setRowHeight(30);
		tableKH.setGridColor(getBackground());
		// tableKH.setOpaque(false);
		tableKH.setSelectionBackground(new Color(164, 44, 167, 30));
		scrollPaneKH.setViewportView(tableKH);

		JPanel pSapXep = new JPanel();
		pSapXep.setBackground(new Color(238, 239, 243, 90));
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23, 153), 1, true), "S\u1EAFp x\u1EBFp",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBounds(353, 50, 904, 51);
		pMain.add(pSapXep);
		// pSapXep.setLayout(null);

		cbbSort = new JComboBox<String>();
		cbbSort.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbSort.setBackground(Color.WHITE);
		cbbSort.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));

		String cbSort[] = { "Tăng dần", "Giảm dần" };
		for (int i = 0; i < cbSort.length; i++) {
			cbbSort.addItem(cbSort[i]);
		}
		pSapXep.setLayout(null);
		cbbSort.setBounds(26, 14, 167, 28);
		pSapXep.add(cbbSort);

		chkAll.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkAll.setBackground(new Color(220, 210, 239));
		chkAll.setBounds(205, 15, 109, 27);
		pSapXep.add(chkAll);
		chkAll.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==1) {
					loadDanhSachKH();
				}
				else
					clearTable();
			}
		});


		// rdoTheoMaKH.setSelected(true);
		rdoTheoMaKH = new JRadioButton("Theo mã khách hàng");
		rdoTheoMaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaKH.setBackground(new Color(220, 210, 239));
		rdoTheoMaKH.setBounds(329, 15, 167, 27);
		pSapXep.add(rdoTheoMaKH);

		rdoTheoTenKH = new JRadioButton("Theo tên khách hàng");
		rdoTheoTenKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenKH.setBackground(new Color(220, 210, 239));
		rdoTheoTenKH.setBounds(511, 15, 171, 27);
		pSapXep.add(rdoTheoTenKH);

		rdoTheoLoaiKH = new JRadioButton("Theo loại khách hàng");
		rdoTheoLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiKH.setBackground(new Color(220, 210, 239));
		rdoTheoLoaiKH.setBounds(702, 15, 171, 27);
		pSapXep.add(rdoTheoLoaiKH);

		bg = new ButtonGroup();
		bg.add(rdoTheoMaKH);
		bg.add(rdoTheoTenKH);
		bg.add(rdoTheoLoaiKH);
		bg.clearSelection();
		// rdoTheoMaKH.setSelected(true);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackground.setBounds(0, 0, 1281, 629);
		Image imgBackground = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackground);

		// end giao dien

		// Load tên loại KH
		ArrayList<LoaiKH> lsLoaiKH = daoLoaiKH.getAllLoaiKH();
		for (LoaiKH lkh : lsLoaiKH) {
			cbbloaiKH.addItem(lkh.getTenLoaiKH());
		}

		btnThemKH.addActionListener(this);
		btnXoaKH.addActionListener(this);
		btnSuaKH.addActionListener(this);
		tableKH.addMouseListener(this);
		btnReset.addActionListener(this);
		//chkAll.addActionListener(this);
		btnTim.addActionListener(this);
		rdoTheoMaKH.addActionListener(this);
		rdoTheoTenKH.addActionListener(this);
		rdoTheoLoaiKH.addActionListener(this);
	}

//end main

	// Load danh sach tu du lieu
	public void loadDanhSachKH() {
		clearTable();
		ArrayList<KhachHang> lsKH = daoKhachHang.getDanhSachKH();
		for (KhachHang kh : lsKH) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
			modelKhachHang.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(),
					kh.getGioiTinh(), dfNgaySinh.format(kh.getNgaySinh()), kh.getDiaChi(), kh.getSdt(), kh.getCccd(),
					dfNgayDangKy.format(kh.getNgayDangKy()), kh.getDiemTichLuy() });
		}
	}

	// load thong tin 1 nguoi
	public void loadThongTin(KhachHang kh) {
		LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
		modelKhachHang.setRowCount(0);
		modelKhachHang.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(),
				kh.getGioiTinh(), dfNgaySinh.format(kh.getNgaySinh()), kh.getDiaChi(), kh.getSdt(), kh.getCccd(),
				dfNgayDangKy.format(kh.getNgayDangKy()), kh.getDiemTichLuy() });
	}

	// load theo ten kh
	private void loadDanhSachTenKH(ArrayList<KhachHang> kh1) {
		//clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getTenKH(txtTK.getText());
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			modelKhachHang.addRow(new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(),
					lskh.getGioiTinh(), dfNgaySinh.format(lskh.getNgaySinh()), lskh.getDiaChi(), lskh.getSdt(),
					lskh.getCccd(), dfNgayDangKy.format(lskh.getNgayDangKy()), lskh.getDiemTichLuy() });
		}
	}
	private void loadDanhSachTenKHTheoLoai(ArrayList<KhachHang> kh2) {
		//clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen(txtTK.getText().toString()));
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			modelKhachHang.addRow(new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(),
					lskh.getGioiTinh(), dfNgaySinh.format(lskh.getNgaySinh()), lskh.getDiaChi(), lskh.getSdt(),
					lskh.getCccd(), dfNgayDangKy.format(lskh.getNgayDangKy()), lskh.getDiemTichLuy() });
		}
	}

	// Lam moi danh sach
	public void clearTable() {
		while (tableKH.getRowCount() > 0) {
			modelKhachHang.removeRow(0);
		}
	}

	// Nut them
	public void themKHVaoDanhSach() {
		// int optThem = JOptionPane.showConfirmDialog(this, "Bạn có chắn chắn muốn thêm
		// khách hàng không?", "Thông báo", JOptionPane.YES_NO_OPTION );

		if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)
				&& regex.regexDiaChi(txtDiaChi)) {
			String maKH = daoMaKH.getMaKH();
			String tenKH = txtHoTen.getText().toString();
			String sdt = txtSDT.getText().toString();
			String diaChi = txtDiaChi.getText().toString();
			String cccd = txtCccd.getText().toString();
			String gioiTinh = cbbgioiTinh.getSelectedItem().toString();
			LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cbbloaiKH.getSelectedItem().toString()));

			int ngaySinh = dateChooserNgaySinh.getDate().getDate();
			int thangSinh = dateChooserNgaySinh.getDate().getMonth();
			int namSinh = dateChooserNgaySinh.getDate().getYear();

			int ngayDangKy = dateChooserNgayDangKy.getDate().getDate();
			int thangDangKy = dateChooserNgayDangKy.getDate().getMonth();
			int namDangKy = dateChooserNgayDangKy.getDate().getYear();
			int diemTichLuy = Integer.parseInt(txtPoint.getText().toString());

			@SuppressWarnings("deprecation")
			KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd, new Date(namSinh, thangSinh, ngaySinh),
					gioiTinh, diemTichLuy, new Date(ngayDangKy, thangDangKy, namDangKy), loaiKH);
			daoKhachHang.themDanhSachKH(kh);
			// tableKH.getRowCount()
			loadThongTin(kh);
			resetAll();
			JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
		}
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
		
	}

	// Nút sửa
	public void suaThongTin() {
		int row = tableKH.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin khách hàng  này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)
						&& regex.regexDiaChi(txtDiaChi)) {
					String maKH = modelKhachHang.getValueAt(row, 0).toString();
					String tenKH = txtHoTen.getText().toString();
					String sdt = txtSDT.getText().toString();
					String diaChi = txtDiaChi.getText().toString();
					String cccd = txtCccd.getText().toString();
					String gioiTinh = cbbgioiTinh.getSelectedItem().toString();
					LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cbbloaiKH.getSelectedItem().toString()));
					int ngaySinh = dateChooserNgaySinh.getDate().getDate();
					int thangSinh = dateChooserNgaySinh.getDate().getMonth();
					int namSinh = dateChooserNgaySinh.getDate().getYear();
					int ngayDangKy = dateChooserNgayDangKy.getDate().getDate();
					int thangDangKy = dateChooserNgayDangKy.getDate().getMonth();
					int namDangKy = dateChooserNgayDangKy.getDate().getYear();
					int diemTichLuy = Integer.parseInt(txtPoint.getText().toString());
					try {
						KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd,
								new Date(namSinh, thangSinh, ngaySinh), gioiTinh, diemTichLuy,
								new Date(ngayDangKy, thangDangKy, namDangKy), loaiKH);
						daoKhachHang.suaThongTinKhachHang(kh);
						loadDanhSachKH();
						resetAll();
						JOptionPane.showMessageDialog(this, "Thông tin khách hàng đã được sửa!", "Thông báo",
								JOptionPane.OK_OPTION);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thất bại!", "Thông báo",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin khách hàng cần sửa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// Làm mới
	public void resetAll() {
		txtTK.setText("");
		txtHoTen.setText("");
		txtSDT.setText("");
		txtCccd.setText("");
		txtDiaChi.setText("");
		dateChooserNgaySinh.setDate(new Date(0));
		dateChooserNgayDangKy.setDate(new Date(0));
		txtPoint.setText("");
	}

	// Tìm kiếm
	private void findKH() {
		KhachHang kh = daoKhachHang.getKH(txtTK.getText().toLowerCase().trim());
		ArrayList<KhachHang>kh1 = daoKhachHang.getTenKH(txtTK.getText().toLowerCase().trim());
		/*
		 * String loai = ; JTextField tam = new JTextField(); tam.setText(loai);
		 */
		ArrayList<KhachHang>kh2 = daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen(txtTK.getText().toString()));
		if (!txtTK.getText().equals("") && !txtTK.getText().equals("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.")) {
			String messTenKH = "\n - Họ tên. Ví dụ: Nguyễn Văn A";
			String messLKH = "\n - Tìm theo loại khách hàng: khách hàng thường, thành viên, VIP, không còn là khách hàng";
			String messSDT = "\n - SĐT gồm 10 chữ số và bắt đầu bằng số 0";
			//System.out.println(regex.regexTimKiemMaLoaiKH(txtTK));
			//loadDanhSachKH();
			if (regex.regexTimKiemMaKH(txtTK)) {
				try {
					clearTable();
					loadThongTin(kh);
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
			} else if (regex.regexTimNV(txtTK)) {
				try {
					System.out.println("alo 123 5");
					clearTable();
					loadDanhSachTenKH(kh1);
				} catch (Exception e) {
					// TODO: handle exception

					JOptionPane.showMessageDialog(null, "Không tìm thấy tên khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
			} else if (regex.regexSDT(txtTK)) {
				try {
					System.out.println(regex.regexSDT(txtTK));
					clearTable();
					loadThongTin(kh);
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Không tìm thấy số điện thoại khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
				}
			else if (regex.regexTimKiemMaLoaiKH(txtTK)) {

				System.out.println("alo 123 5");
				try {
					//System.out.println(regex.regexTimKiemMaLoaiKH(txtTK));
					//clearTable();
					loadDanhSachTenKHTheoLoai(kh2);
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Không tìm thấy loại khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
				}
			
			  else JOptionPane.showMessageDialog(null,
			  "Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã khách hàng. Ví dụ: KH001"
			  + messTenKH + messSDT + messLKH, "Thông báo", JOptionPane.ERROR_MESSAGE);
			 
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// Xoa khach hang
	private boolean cancelKH() {
		int row = tableKH.getSelectedRow();
		if (row >= 0) {
			int cancel = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa khách hàng này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (cancel == JOptionPane.YES_OPTION) {
				String maKH = tableKH.getValueAt(row, 0).toString();
				try {
					modelKhachHang.removeRow(row);
					clearTable();
					daoKhachHang.huyKH(maKH);
					loadDanhSachKH();
					JOptionPane.showMessageDialog(null, "Đã xóa khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "xóa khách hàng thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thông tin khách hàng cần hủy!", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	//Sap xep theo ma khach hang giam dan
	
	  public void sortMaKHGiamDan(KhachHang kh) {
					clearTable();
					ArrayList<KhachHang> lsKH = daoKhachHang.sortByMa();
					for (KhachHang khs : lsKH) {
						LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
						modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
								khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
								dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
					}
}
	  
	//Sap xep theo ten khach hang giam dan
	  public void sortTenKHGiamDan(KhachHang kh) {
			clearTable();
			ArrayList<KhachHang> lsKH = daoKhachHang.getDanhSachKH();		
			Collections.sort(lsKH, new Comparator<KhachHang>() {
				public int compare(KhachHang o1, KhachHang o2) {
					return o2.getTenKH().compareTo(o1.getTenKH());
				}
			});
			
			for (KhachHang khs : lsKH) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}
}
	  //Sap xep theo ten khach hang tang dan
	  public void sortTenKHTangDan(KhachHang kh) {
			clearTable();
			ArrayList<KhachHang> lsKH = daoKhachHang.getDanhSachKH();		
			Collections.sort(lsKH, new Comparator<KhachHang>() {
				public int compare(KhachHang o1, KhachHang o2) {
					return o1.getTenKH().compareTo(o2.getTenKH());
				}
			});
			
			for (KhachHang khs : lsKH) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}
}
	  //sap xep loaiKh giam dan
	  public void sortLoaiKHGiamDan(KhachHang kh) {
		  	clearTable();
			ArrayList<KhachHang> lsVip= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên VIP"));
			for (KhachHang khs : lsVip) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}

			ArrayList<KhachHang> lsThanhVien= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên thường"));
			for (KhachHang khs : lsThanhVien) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}

			ArrayList<KhachHang> lsKhachHang= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Khách hàng thường"));
			for (KhachHang khs : lsKhachHang) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}
	  }
	  
	  //sap xep loaiKH tang dan
	  public void sortLoaiKHTangDan(KhachHang kh) {
		  	clearTable();
			ArrayList<KhachHang> lsKhachHang= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Khách hàng thường"));
			for (KhachHang khs : lsKhachHang) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}

			ArrayList<KhachHang> lsThanhVien= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên thường"));
			for (KhachHang khs : lsThanhVien) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}


			
			ArrayList<KhachHang> lsVip= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên VIP"));
			for (KhachHang khs : lsVip) {
				LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
				modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
						khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
						dfNgayDangKy.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
			}
	  }
	


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	// Hiển thị thông tin khi chọn vào bảng
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tableKH)) {
			int row = tableKH.getSelectedRow();
			txtHoTen.setText(modelKhachHang.getValueAt(row, 1).toString());
			cbbloaiKH.setSelectedItem(modelKhachHang.getValueAt(row, 2).toString());
			txtSDT.setText(modelKhachHang.getValueAt(row, 6).toString());
			txtCccd.setText(modelKhachHang.getValueAt(row, 7).toString());
			txtDiaChi.setText(modelKhachHang.getValueAt(row, 5).toString());
			cbbgioiTinh.setSelectedItem(modelKhachHang.getValueAt(row, 3).toString());
			txtPoint.setText(modelKhachHang.getValueAt(row, 9).toString());
			String ngaySinh = modelKhachHang.getValueAt(row, 4).toString();
			String ngayDangKy = modelKhachHang.getValueAt(row, 8).toString();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				java.util.Date d = df.parse(ngaySinh);
				java.util.Date d1 = df.parse(ngayDangKy);
				dateChooserNgaySinh.setDate(d);
				dateChooserNgayDangKy.setDate(d1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThemKH)) {
			themKHVaoDanhSach();
		}
		if (o.equals(btnSuaKH)) {
			suaThongTin();
		}
		if (o.equals(btnReset)) {
			clearTable();
			resetAll();
		}
		if (o.equals(btnXoaKH)) {
			cancelKH();
		}  
		if (o.equals(btnTim)) {
			findKH();
		}
		if(cbbSort.getSelectedItem()=="Tăng dần") {
			if(o.equals(rdoTheoMaKH)) {
				loadDanhSachKH();
			}
			if(o.equals(rdoTheoTenKH))
				sortTenKHTangDan(kh);
			if(o.equals(rdoTheoLoaiKH))
				sortLoaiKHTangDan(kh);
		}
		if(cbbSort.getSelectedItem()=="Giảm dần"){
			clearTable();
			if(o.equals(rdoTheoMaKH)) {
				sortMaKHGiamDan(kh);
			}
			if(o.equals(rdoTheoTenKH))
				sortTenKHGiamDan(kh);
			if(o.equals(rdoTheoLoaiKH))
				sortLoaiKHGiamDan(kh);
		}

	}
}
