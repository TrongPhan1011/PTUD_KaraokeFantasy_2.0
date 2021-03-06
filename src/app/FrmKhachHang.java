package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import dao.DAOPhatSinhMa;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiKH;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmKhachHang extends JFrame implements ActionListener, MouseListener, ItemListener, FocusListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String sHeaderMaNV;
	@SuppressWarnings("unused")
	private String sHeaderTenNV;
	@SuppressWarnings("unused")
	private Date dNgayHienTai;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private Panel pMain;
	private JTextField txtTK;
	private JButton btnTim;
	private JButton btnThemKH;
	private JButton btnSuaKH;
	private JButton btnExcels;
	private JButton btnXoaKH;
	private JButton btnReset;
	private JTextField txtCccd;
	private JTextField txtPoint;
	private JTable tableKH;
	private DAOLoaiKH daoLoaiKH;
	private DAOKhachHang daoKhachHang;
	private DAOPhatSinhMa daoMaKH;
	private DefaultTableModel modelKhachHang;
	private JComboBox<String> cboloaiKH;
	private JComboBox<String> cbogioiTinh;
	private JComboBox<String> cboSort;
	private JDateChooser dateChooserNgaySinh;
	private JDateChooser dateChooserNgayDangKy;
	private Format dfNgaySinh ;
	private JRadioButton rdoTheoMaKH;
	private JRadioButton rdoTheoLoaiKH;
	private JRadioButton rdoTheoTenKH;
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

	@SuppressWarnings("deprecation")
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
		
		//dfNgaySinh  
		//dfNgayDangKy  = new SimpleDateFormat("dd/MM/yyyy");

		// Giao dien
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);

		
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 333, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);

		lblNhapThongTin = new JLabel("Nhập thông tin khách hàng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 313, 29);
		pNhapThongTin.add(lblNhapThongTin);
		
		//Nhap ho ten
		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtHoTen.setBounds(153, 64, 170, 27);
		pNhapThongTin.add(txtHoTen);
		txtHoTen.setText("Võ Kim Bản");

		JLabel lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setBounds(10, 64, 90, 27);
		pNhapThongTin.add(lblHoTen);
		lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));

		//Nhap so dien thoai
		JLabel lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(10, 102, 59, 23);
		pNhapThongTin.add(lblSDT);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtSDT.setColumns(10);
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtSDT.setBounds(153, 100, 170, 27);
		pNhapThongTin.add(txtSDT);
		txtSDT.setText("02822292211");
		
		//Nhap dia chi
		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setBounds(10, 133, 84, 27);
		pNhapThongTin.add(lblAddress);
		lblAddress.setFont(new Font("SansSerif", Font.BOLD, 15));

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtDiaChi.setBounds(153, 133, 170, 27);
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("23 Trần Não, quận 2");

		//Nhap cac cuoc cong dan
		txtCccd = new JTextField();
		txtCccd.setBounds(153, 167, 170, 27);
		pNhapThongTin.add(txtCccd);
		txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtCccd.setColumns(10);
		txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtCccd.setText("032144572012");

		JLabel lblCccd = new JLabel("CCCD:");
		lblCccd.setBounds(10, 171, 65, 23);
		pNhapThongTin.add(lblCccd);
		lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));

		
		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		
		dNow = new Date(nam,thang,ngay);
		//Chon ngay sinh cua khach hang
		dateChooserNgaySinh = new JDateChooser();
		dateChooserNgaySinh.setBounds(153, 278, 170, 28);
		pNhapThongTin.add(dateChooserNgaySinh);

		dateChooserNgaySinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		dateChooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		dateChooserNgaySinh.setDate(dNow);
		
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(10, 278, 102, 28);
		pNhapThongTin.add(lblNgaySinh);
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		//chon ngay dang ki
		JLabel lblNgayDangKy = new JLabel("Ngày đăng ký:");
		lblNgayDangKy.setBounds(10, 318, 102, 29);
		pNhapThongTin.add(lblNgayDangKy);
		lblNgayDangKy.setFont(new Font("SansSerif", Font.BOLD, 15));

		dateChooserNgayDangKy = new JDateChooser();
		dateChooserNgayDangKy.setBounds(153, 319, 170, 28);
		pNhapThongTin.add(dateChooserNgayDangKy);

		dateChooserNgayDangKy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserNgayDangKy.setDateFormatString("dd/MM/yyyy");
		dateChooserNgayDangKy.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserNgayDangKy.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserNgayDangKy.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserNgayDangKy.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar1 = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserNgayDangKy.setIcon((ImageIcon) iconCalendar1);
		dateChooserNgayDangKy.setDate(dNow);
		
		//Loai khach hang
		JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
		lblLoaiKH.setBounds(10, 211, 124, 19);
		pNhapThongTin.add(lblLoaiKH);
		lblLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 15));

		cboloaiKH = new JComboBox<String>();
		cboloaiKH.setToolTipText("Chọn loại khách hàng hàng");
		cboloaiKH.setBounds(153, 203, 170, 27);
		pNhapThongTin.add(cboloaiKH);
		cboloaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboloaiKH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboloaiKH.setBackground(new Color(255, 255, 255));
		
		//Chon gioi tinh
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(10, 241, 72, 26);
		pNhapThongTin.add(lblGioiTinh);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbogioiTinh = new JComboBox<String>();

		cbogioiTinh.setToolTipText("Chọn giới tính");
		cbogioiTinh.setBounds(153, 241, 170, 28);
		pNhapThongTin.add(cbogioiTinh);
		cbogioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbogioiTinh.setBackground(Color.WHITE);
		cbogioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		
		//diem tich cua khach hang
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
		lblTim.setBounds(353, 11, 90, 35);
		pMain.add(lblTim);

		// txtTK
		txtTK = new JTextField();
		txtTK.setToolTipText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng");
		txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		txtTK.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTK.setBounds(421, 11, 526, 33);
		pMain.add(txtTK);

		// btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);

		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(957, 12, 132, 33);

		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.white);
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);
		
		btnExcels = new FixButton("Xuất Excels");
		btnExcels.setForeground(Color.WHITE);
		btnExcels.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcels.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnExcels.setBackground(new Color(16, 124, 65));
		btnExcels.setBounds(1101, 12, 159, 33);
		Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);
		btnExcels.setIcon(iconExcel);
		pMain.add(btnExcels);
		
		
		String cbbGioiTinh[] = { "Nam", "Nữ" };
		for (int i = 0; i < cbbGioiTinh.length; i++) {
			cbogioiTinh.addItem(cbbGioiTinh[i]);
		}
		
		//btnThem
		btnThemKH = new FixButton("Thêm");

		btnThemKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemKH.setForeground(Color.WHITE);
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemKH.setBackground(new Color(57, 210, 247));
		btnThemKH.setBounds(10, 425, 313, 35);
		Icon iconThemKH = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemKH.setIcon(iconThemKH);
		pNhapThongTin.add(btnThemKH);

		//btn sua thong tin
		btnSuaKH = new FixButton("Sửa");
		btnSuaKH.setForeground(Color.WHITE);
		btnSuaKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSuaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaKH.setBackground(new Color(133, 217, 191));
		btnSuaKH.setBounds(10, 469, 313, 35);
		Icon iconSuaKH = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaKH.setIcon(iconSuaKH);
		pNhapThongTin.add(btnSuaKH);
		
		//btn set loaiKH khong con la khach hang
		btnXoaKH = new FixButton("Xóa");
		btnXoaKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoaKH.setForeground(Color.WHITE);
		btnXoaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXoaKH.setBackground(new Color(0xE91940));
		btnXoaKH.setBounds(10, 515, 313, 35);
		Icon iconHuyKH = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		btnXoaKH.setIcon(iconHuyKH);
		pNhapThongTin.add(btnXoaKH);
		
		//dua cac txt, rdo, cbo ve mac dinh
		btnReset = new FixButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setToolTipText("Làm mới tất cả thông tin");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnReset.setBackground(new Color(114, 23, 153));
		btnReset.setBounds(10, 561, 313, 35);
		Icon iconReset = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnReset.setIcon(iconReset);

		pNhapThongTin.add(btnReset);
		
		
		JScrollPane scrollPaneKH = new JScrollPane();
		scrollPaneKH.setToolTipText("Chọn khách hàng cần hiển thị thông tin");
		scrollPaneKH.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneKH.setBackground(new Color(164, 44, 167));
		scrollPaneKH.setBounds(353, 106, 904, 512);
		pMain.add(scrollPaneKH);

		String col[] = { "Mã KH", "Họ và tên KH", "Loại KH", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "CCCD",
				"Ngày đăng ký", "Điểm tích lũy" };
		modelKhachHang = new DefaultTableModel(col, 0);
		tableKH = new JTable(modelKhachHang);
		
		//header cua table
		JTableHeader tbHeader = tableKH.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		//do dai cua tung cot trong table
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
		tableKH.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//Chữ canh trái, số canh phải
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
		pSapXep.setBackground(new Color(171, 192, 238));
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23, 153), 1, true), "S\u1EAFp x\u1EBFp",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBounds(353, 50, 904, 51);
		pMain.add(pSapXep);
		// pSapXep.setLayout(null);
		
		//cbo sắp xếp tăng dần hoặc giảm dần
		cboSort = new JComboBox<String>();
		cboSort.setToolTipText("Chọn kiểu sắp xếp");
		cboSort.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSort.setBackground(Color.WHITE);
		cboSort.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));

		String cbSort[] = { "Tăng dần", "Giảm dần" };
		for (int i = 0; i < cbSort.length; i++) {
			cboSort.addItem(cbSort[i]);
		}
		pSapXep.setLayout(null);
		cboSort.setBounds(26, 14, 167, 28);
		pSapXep.add(cboSort);


		//Group rdo giúp sắp xếp
		
		rdoTheoMaKH = new JRadioButton("Theo mã khách hàng");
		
		rdoTheoMaKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaKH.setBackground(new Color(171, 192, 238));
		rdoTheoMaKH.setBounds(245, 15, 167, 27);
		pSapXep.add(rdoTheoMaKH);

		rdoTheoTenKH = new JRadioButton("Theo tên khách hàng");
		rdoTheoTenKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoTenKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenKH.setBackground(new Color(171, 192, 238));
		rdoTheoTenKH.setBounds(434, 15, 171, 27);
		pSapXep.add(rdoTheoTenKH);

		rdoTheoLoaiKH = new JRadioButton("Theo loại khách hàng");
		rdoTheoLoaiKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiKH.setBackground(new Color(171, 192, 238));
		rdoTheoLoaiKH.setBounds(626, 15, 171, 27);
		pSapXep.add(rdoTheoLoaiKH);

		bg = new ButtonGroup();
		bg.add(rdoTheoMaKH);
		bg.add(rdoTheoTenKH);
		bg.add(rdoTheoLoaiKH);
		bg.clearSelection();
		// rdoTheoMaKH.setSelected(true);

		//chèn background
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
			cboloaiKH.addItem(lkh.getTenLoaiKH());
		}
		
		// add actions
		dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
		loadDanhSachKH();
		btnThemKH.addActionListener(this);
		btnXoaKH.addActionListener(this);
		btnSuaKH.addActionListener(this);
		tableKH.addMouseListener(this);
		btnReset.addActionListener(this);
		btnExcels.addActionListener(this);
		btnTim.addActionListener(this);
		rdoTheoMaKH.addActionListener(this);
		rdoTheoTenKH.addActionListener(this);
		rdoTheoLoaiKH.addActionListener(this);
		cboSort.addActionListener(this);
		
		txtTK.addFocusListener(this);
		txtTK.addKeyListener(this);
		txtHoTen.addKeyListener(this);
		txtSDT.addKeyListener(this);
		txtDiaChi.addKeyListener(this);
		txtCccd.addKeyListener(this);
	}

	//end giao diện
	
	/**
	 * Load danh sách thông tin khách hàng
	 */
	
	// Load danh sach tu du lieu
	public void loadDanhSachKH() {
		clearTable();
		ArrayList<KhachHang> lsKH = daoKhachHang.getDanhSachKH();
		for (KhachHang kh : lsKH) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(kh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(kh.getNgaySinh());
			if(kh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(kh.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(),
					kh.getGioiTinh(), ngaySinh, kh.getDiaChi(), kh.getSdt(), kh.getCccd(),
					ngayDK, kh.getDiemTichLuy() });
		}
	}

	// load thong tin 1 nguoi
	public void loadThongTin(KhachHang kh) {
		LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
		String ngaySinh = "";
		String ngayDK = "";
		if(kh.getNgaySinh() != null)
			ngaySinh = dfNgaySinh.format(kh.getNgaySinh());
		if(kh.getNgayDangKy() != null)
			ngayDK = dfNgaySinh.format(kh.getNgayDangKy());
		

		modelKhachHang.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(),
				kh.getGioiTinh(), ngaySinh, kh.getDiaChi(), kh.getSdt(), kh.getCccd(),
				ngayDK, kh.getDiemTichLuy() });
	}

	// load theo ten kh
	private void loadDanhSachMaVaSoKH(ArrayList<KhachHang> kh1) {
		clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getMaVaSDTKH(txtTK.getText().toLowerCase().trim());
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if(lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(),
					lskh.getGioiTinh(), ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(),
					ngayDK, lskh.getDiemTichLuy() });
		}
	}
	private void loadDanhSachTenKH(ArrayList<KhachHang> kh1) {
		clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getTenKH(txtTK.getText().toLowerCase().trim());
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if(lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(),
					lskh.getGioiTinh(), ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(),
					ngayDK, lskh.getDiemTichLuy() });
		}
	}

	private void loadDanhSachTenKHTheoLoai(ArrayList<KhachHang> kh2) {
		clearTable();
		String maLoai = daoLoaiKH.getMaLoaiKHTheoTen(txtTK.getText());

		ArrayList<KhachHang> lstName = daoKhachHang.getKHTheoLoai(maLoai);
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if(lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(),
					lskh.getGioiTinh(), ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(),
					ngayDK, lskh.getDiemTichLuy() });
		}
	}

	// Lam moi danh sach
	/**
	 * @return danh sách trống
	 */
	public void clearTable() {
		while (tableKH.getRowCount() > 0) {
			modelKhachHang.removeRow(0);
		}
	}

	
	// Nut them

	/**
	 * Thêm khách hàng vào danh sách 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void themKHVaoDanhSach() {
		// int optThem = JOptionPane.showConfirmDialog(this, "Bạn có chắn chắn muốn thêm
		// khách hàng không?", "Thông báo", JOptionPane.YES_NO_OPTION );

		try {
			String maKH = daoMaKH.getMaKH();
			String tenKH = txtHoTen.getText().toString();
			String sdt = txtSDT.getText().toString();
			String diaChi = txtDiaChi.getText().toString();
			String cccd = txtCccd.getText().toString();
			String gioiTinh = cbogioiTinh.getSelectedItem().toString();
			LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cboloaiKH.getSelectedItem().toString()));

			int ngaySinh = dateChooserNgaySinh.getDate().getDate();
			int thangSinh = dateChooserNgaySinh.getDate().getMonth();
			int namSinh = dateChooserNgaySinh.getDate().getYear();

			int ngayDangKy = dateChooserNgayDangKy.getDate().getDate();
			int thangDangKy = dateChooserNgayDangKy.getDate().getMonth();
			int namDangKy = dateChooserNgayDangKy.getDate().getYear();
			int diemTichLuy = 0;
			int tuoi = nam - namSinh;

			if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)&& regex.regexDiaChi(txtDiaChi) ) {
				if(tuoi >= 13) {
					if(daoKhachHang.checkSdtKH(sdt)== false) {
						KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd, new Date(namSinh, thangSinh, ngaySinh),
								gioiTinh, diemTichLuy, new Date(ngayDangKy, thangDangKy, namDangKy), loaiKH);
						daoKhachHang.themDanhSachKH(kh);
						loadThongTin(kh);
						resetAll();
						JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");						
					}else {
						JOptionPane.showMessageDialog(this,"Số điện thoại đã đăng kí","Thông báo" , JOptionPane.ERROR_MESSAGE);
					}
				}else
				{
					JOptionPane.showMessageDialog(this,"Khách hàng chưa đủ 13 tuổi","Thông báo" , JOptionPane.ERROR_MESSAGE);
			}
			}


		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin","Thông báo" ,  JOptionPane.WARNING_MESSAGE);
		}

	}

	// Nút sửa
	/**
	 * Sửa thông tin khách hàng đã được chọn trong danh sách hoặc tìm kiểm
	 */

	@SuppressWarnings("deprecation")
	public void suaThongTin() {
		int row = tableKH.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin khách hàng  này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				java.util.Date date = dateChooserNgaySinh.getDate();
				Date ngaySinh=new Date(date.getYear(), date.getMonth(), date.getDate());
				java.util.Date date1 = dateChooserNgayDangKy.getDate();
				Date ngayDangKy=new Date(date1.getYear(), date1.getMonth(), date1.getDate());
				int tuoi = nam - ngaySinh.getYear();
				//System.out.println(tuoi);
				String maKH = modelKhachHang.getValueAt(row, 0).toString();
				String tenKH = txtHoTen.getText().toString();
				String sdt = txtSDT.getText().toString();
				String diaChi = txtDiaChi.getText().toString();
				String cccd = txtCccd.getText().toString();
				String gioiTinh = cbogioiTinh.getSelectedItem().toString();
				LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cboloaiKH.getSelectedItem().toString()));

				
				int diemTichLuy = Integer.parseInt(txtPoint.getText().toString());
				if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)
						&& regex.regexDiaChi(txtDiaChi)) {
					if(tuoi >=13) {
							try {
								KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd,
										ngaySinh, gioiTinh, diemTichLuy,
										ngayDangKy, loaiKH);
								daoKhachHang.suaThongTinKhachHang(kh);
								resetAll();
								loadThongTin(kh);
								JOptionPane.showMessageDialog(this, "Thông tin khách hàng đã được sửa!", "Thông báo",
										JOptionPane.OK_OPTION);
							} catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thất bại!", "Thông báo",
										JOptionPane.ERROR_MESSAGE);
							}	

					}
					else {
						JOptionPane.showMessageDialog(null, "Khách hàng chưa đủ 13 tuổi!", "Thông báo",
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
	/**
	 *Làm mới lại tất cả txt và cbo trong frm
	 */

	public void resetAll() {
		txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		
		txtHoTen.setText("");
		txtSDT.setText("");
		txtCccd.setText("");
		txtDiaChi.setText("");
		dateChooserNgaySinh.setDate(dNow);
		dateChooserNgayDangKy.setDate(dNow);
		txtPoint.setText("");
		loadDanhSachKH();
		bg.clearSelection();
	}

	/**
	 * Xóa 1 khách hàng đã được chon trong danh sách hoặc tìm kiếm
	 * @return
	 */

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
					JOptionPane.showMessageDialog(null, "Đã xóa khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "xóa khách hàng thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thông tin khách hàng cần hủy!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

	// Tìm kiếm
	/**
	 * Giúp tìm kiếm khách hàng dựa vào mã, tên, số điện thoại, loại khách hàng
	 */
	private void findKH() {

		String input = txtTK.getText().trim();
		ArrayList<KhachHang> lstKH = null;
		String regexMaKH = "^((KH|kh)[0-9]{3})$";
		String regexTenKH= "^[ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$";
		String regexSDT  = "^(0[0-9]{9})$";

		if(!txtTK.getText().equals("") && !txtTK.getText().equals("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.")) {
			if(regex.regexTimKH(txtTK)) {
				if(input.matches(regexMaKH)) {
					lstKH = daoKhachHang.getMaVaSDTKH(input);
					loadDanhSachMaVaSoKH(lstKH);
				}
				if(input.matches(regexSDT)) {
					lstKH = daoKhachHang.getMaVaSDTKH(input);
					loadDanhSachMaVaSoKH(lstKH);
				}
				if(input.matches(regexTenKH)) {
					lstKH = daoKhachHang.getTenKH(input);
					loadDanhSachTenKH(lstKH);
				}
				if(regex.regexTimKiemLoaiKH(txtTK)) {
					lstKH = daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen(input));
					loadDanhSachTenKHTheoLoai(lstKH);
				}
				if(lstKH.size() == 0) {
					JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tìm kiếm phù hợp!");
				}
			}
		}else {
			clearTable();
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		}

	}

	/**
	 * Sắp xếp sau khi chọn vào giảm dần
	 * @param kh
	 */

	//Sap xep theo ma khach hang giam dan

	public void sortMaKHGiamDan(KhachHang kh) {
		clearTable();
		ArrayList<KhachHang> lsKH = daoKhachHang.sortByMa();
		for (KhachHang khs : lsKH) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
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
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}
	}
	//sap xep loaiKh giam dan
	public void sortLoaiKHGiamDan(KhachHang kh) {
		clearTable();
		ArrayList<KhachHang> lsVip= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên VIP"));
		for (KhachHang khs : lsVip) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}

		ArrayList<KhachHang> lsThanhVien= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên thường"));
		for (KhachHang khs : lsThanhVien) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}

		ArrayList<KhachHang> lsKhachHang= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Khách hàng thường"));
		for (KhachHang khs : lsKhachHang) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), dfNgaySinh.format(khs.getNgaySinh()), khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					dfNgaySinh.format(khs.getNgayDangKy()), khs.getDiemTichLuy() });
		}
	}

	/**
	 * Sắp xếp sau khi chọn tăng dần dần
	 * @param kh
	 */
	//sap xep loaiKH tang dan
	public void sortLoaiKHTangDan(KhachHang kh) {
		clearTable();
		ArrayList<KhachHang> lsKhachHang= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Khách hàng thường"));
		for (KhachHang khs : lsKhachHang) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}

		ArrayList<KhachHang> lsThanhVien= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên thường"));
		for (KhachHang khs : lsThanhVien) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}



		ArrayList<KhachHang> lsVip= daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen("Thành viên VIP"));
		for (KhachHang khs : lsVip) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khs.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
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
			String ngaySinh = "";
			String ngayDK = "";
			if(khs.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(khs.getNgaySinh());
			if(khs.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(khs.getNgayDangKy());
			
	
			modelKhachHang.addRow(new Object[] { khs.getMaKhangHang(), khs.getTenKH(), loaiKH.getTenLoaiKH(),
					khs.getGioiTinh(), ngaySinh, khs.getDiaChi(), khs.getSdt(), khs.getCccd(),
					ngayDK, khs.getDiemTichLuy() });
		}
	}
	
	private void xuatExcel() throws IOException {
		XuatExcels xuat = new XuatExcels();
		FileDialog fileDialog  = new FileDialog(this, "Xuất thông tin khách hàng ra Excels", FileDialog.SAVE);
		fileDialog.setFile("Danh sách thông tin khách hàng");
		fileDialog .setVisible(true);
		String name = fileDialog.getFile();
		String fileName = fileDialog.getDirectory() + name;

		if (name == null) 
			return;
		
		if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) 
			fileName += ".xlsx";
		
		xuat.xuatTable(tableKH, "DANH SÁCH THÔNG TIN KHÁCH HÀNG", fileName);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	@Override
	// Hiển thị thông tin khi chọn vào bảng
	public void mouseClicked(MouseEvent e) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tableKH)) {
			int row = tableKH.getSelectedRow();
			txtHoTen.setText(modelKhachHang.getValueAt(row, 1).toString());
			cboloaiKH.setSelectedItem(modelKhachHang.getValueAt(row, 2).toString());
			txtSDT.setText(modelKhachHang.getValueAt(row, 6).toString());
			if(modelKhachHang.getValueAt(row, 7) == null) {
				txtCccd.setText("");
			}
			else
				txtCccd.setText(modelKhachHang.getValueAt(row, 7).toString());
			txtDiaChi.setText(modelKhachHang.getValueAt(row, 5).toString());
			if(modelKhachHang.getValueAt(row, 3)== null) {
				cbogioiTinh.setSelectedIndex(-1);
			}else
				cbogioiTinh.setSelectedItem(modelKhachHang.getValueAt(row, 3).toString());
			txtPoint.setText(modelKhachHang.getValueAt(row, 9).toString());

			java.util.Date d = dNow;
			java.util.Date d1 = dNow;
			
			// 

			try {
				if(!modelKhachHang.getValueAt(row, 4).equals("")) {
					d = df.parse(modelKhachHang.getValueAt(row, 4).toString());
				}
				if(!modelKhachHang.getValueAt(row, 8).equals("")) {
					d = df.parse(modelKhachHang.getValueAt(row, 8).toString());
				}
				
				
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
		if(cboSort.getSelectedItem()=="Tăng dần") {
			if(o.equals(rdoTheoMaKH)) {
				loadDanhSachKH();
			}
			if(o.equals(rdoTheoTenKH)) {
				sortTenKHTangDan(kh);

			}
			if(o.equals(rdoTheoLoaiKH))
			{
				sortLoaiKHTangDan(kh);
			}
		}
		if(cboSort.getSelectedItem()=="Giảm dần"){
			if(o.equals(rdoTheoMaKH)) {
				sortMaKHGiamDan(kh);
			}
			if(o.equals(rdoTheoTenKH)) {
				sortTenKHGiamDan(kh);
			}
			if(o.equals(rdoTheoLoaiKH)) {
				sortLoaiKHGiamDan(kh);
			}
		}
		if(o.equals(cboSort)) {
			bg.clearSelection();
			clearTable();
		}
		if(o.equals(btnExcels))
			try {
				xuatExcel();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		if(o.equals(txtHoTen)&& key == KeyEvent.VK_ENTER ) {
			txtSDT.requestFocus();
		}
		else if(o.equals(txtSDT)&& key == KeyEvent.VK_ENTER ) {
			txtDiaChi.requestFocus();
		}
		else if(o.equals(txtDiaChi)&& key == KeyEvent.VK_ENTER ) {
			txtCccd.requestFocus();
		}
		else if(o.equals(txtCccd)&& key == KeyEvent.VK_ENTER ) {
			btnThemKH.requestFocus();
		}
		else if(o.equals(txtTK)&& key == KeyEvent.VK_ENTER ) {
			btnTim.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

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
}
