package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAONhanVien;
import dao.DAOPhatSinhMa;
import dao.DAOTaiKhoan;
import dao.Regex;
import entity.NhanVien;
import entity.TaiKhoan;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 * @author DinhQuangTuan-19468641
 *
 */
public class FrmNhanVien extends JFrame implements ActionListener, MouseListener, FocusListener {

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

	
	/**
	 * @return pMain
	 */
	public Panel getPanel() {
		return pMain;
	}

	/**
	 * Kế thừa tên và mã nhân viên, ngày hiện tại của FrmQuanLy
	 * @param sHeaderTenNV
	 * @param sHeaderMaNV
	 * @param dNgayHienTai
	 */
	public  FrmNhanVien(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		/**
		 * Kết nối database
		 */
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/**
		 * Khai báo các DAO
		 */
		daoNhanVien=new DAONhanVien();
		daoPhatSinhMa=new DAOPhatSinhMa();
		daoTaiKhoan=new DAOTaiKhoan();
		regex=new Regex();

		/**
		 * Khai báo entity NhanVien
		 */
		NhanVien nv=new NhanVien();

		/**
		 * Frame NhanVien
		 */
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1278, 629);
		pMain.setLayout(null);
		getContentPane().add(pMain);

		/**
		 * Panel pNhapThongTin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 312, 607);
		pNhapThongTin.setLayout(null);
		pMain.add(pNhapThongTin);
		
		/**
		 * Label lblNhapThongTin
		 */
		lblNhapThongTin = new JLabel("Nhập thông tin nhân viên");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 292, 29);
		pNhapThongTin.add(lblNhapThongTin);

		/**
		 * Thông tin nhân viên
		 * Họ tên nhân viên
		 * Label lblHoTen
		 * JTextField txtHoTen
		 */
		JLabel lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setBounds(10, 61, 90, 29);
		lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblHoTen);
		txtHoTen = new JTextField();
		txtHoTen.setBounds(110, 62, 191, 28);
		txtHoTen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtHoTen);

		//test data nhanh
		txtHoTen.setText("Đinh Quang Tuấn");

		/**
		 * Số điện thoại nhân viên
		 * Label lblSDT
		 * JTextField txtSDT
		 */
		JLabel lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(10, 105, 46, 19);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblSDT);
		txtSDT = new JTextField();
		txtSDT.setBounds(110, 100, 191, 28);
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSDT.setColumns(10);
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtSDT);
		txtSDT.setText("0944302210");

		/**
		 * Địa chỉ nơi ở của nhân viên
		 * Label lblDiaChi
		 * JTextField txtDiaChi
		 */
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(10, 139, 72, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(110, 136, 191, 28);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("118 Hoàng Văn Thụ, Q.Phú Nhuận, Tp.HCM");

		/**
		 * Căn cước công dân của nhân viên
		 * Label lblCccd
		 * JTextField txtCccd
		 */
		JLabel lblCccd = new JLabel("CCCD:");
		lblCccd.setBounds(10, 175, 72, 24);
		lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblCccd);
		txtCccd = new JTextField();
		txtCccd.setBounds(110, 175, 191, 28);
		txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtCccd.setColumns(10);
		txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtCccd.setText("123456789012");
		pNhapThongTin.add(txtCccd);

		/**
		 * Giới tính
		 * Label lblGioiTinh
		 * JcomboBox cboGioiTinh
		 */
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(11, 249, 88, 23);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioiTinh);
		cboGioiTinh = new JComboBox<Object>(new Object[] {"Nam", "Nữ"});
		cboGioiTinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGioiTinh.setBounds(111, 247, 191, 25);
		cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGioiTinh.setBackground(Color.white);
		cboGioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(cboGioiTinh);

		/**
		 * Ngày sinh
		 * Label lblNgaySinh
		 * JDateChooser chooserNgaySinh
		 * Icon iconCalendar
		 */
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(10, 214, 90, 23);
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblNgaySinh);
		chooserNgaySinh = new JDateChooser();
		chooserNgaySinh.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chooserNgaySinh.setBounds(110, 213, 191, 25);
		chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		chooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgaySinh);

		/**
		 * Chức vụ nhân viên
		 * Label lblChucVu
		 * JComboBox cboChucVu
		 */
		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setBounds(10, 286, 98, 19);
		lblChucVu.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblChucVu);
		cboChucVu = new JComboBox<Object>(new Object[] {"Quản lý", "Phục vụ", "Thu ngân"});
		cboChucVu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboChucVu.setBounds(110, 283, 191, 25);
		cboChucVu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboChucVu.setBackground(Color.white);
		cboChucVu.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(cboChucVu);

		/**
		 * Ca làm việc
		 * Label lblCaLamViec
		 * JComboBox cboCaLamViec
		 */
		JLabel lblCaLamViec = new JLabel("Ca làm việc:");
		lblCaLamViec.setBounds(10, 323, 90, 20);
		lblCaLamViec.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblCaLamViec);
		cboCaLamViec = new JComboBox<Object>(new Object[] {"1", "2", "3"});
		cboCaLamViec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboCaLamViec.setBounds(110, 319, 56, 25);
		cboCaLamViec.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboCaLamViec.setBackground(Color.white);
		cboCaLamViec.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(cboCaLamViec);

		/**
		 * Chú thích giờ làm việc theo ca
		 * Label lblSubGioTheoCa
		 */
		lblSubGioTheoCa = new JLabel("08:00 AM - 13:00 PM");
		lblSubGioTheoCa.setBounds(167, 323, 145, 20);
		lblSubGioTheoCa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		pNhapThongTin.add(lblSubGioTheoCa);
		
		/**
		 * Thông báo nhân viên đã nghỉ việc
		 * JLabel lblNVDaNghiViec
		 */
		lblNVDaNghiViec = new JLabel();
		lblNVDaNghiViec.setHorizontalAlignment(SwingConstants.CENTER);
		lblNVDaNghiViec.setForeground(Color.RED);
		lblNVDaNghiViec.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblNVDaNghiViec.setBounds(10, 355, 291, 28);
		pNhapThongTin.add(lblNVDaNghiViec);

		/**
		 * Tìm kiếm nhân viên: mã nhân viên, tên nhân viên, sđt, chức vụ, ca làm việc
		 * Label lblTim
		 * JTextField txtTim
		 * Sự kiện placeholder tìm NV: FocusListener
		 * Nút tìm NV
		 * JButton btnTim
		 * Icon iconTim
		 */
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(515, 11, 90, 35);
		pMain.add(lblTim);
		txtTim = new JTextField();
		txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, ca làm việc.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(597, 11, 526, 33);
		pMain.add(txtTim);
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1133, 12, 127, 33);
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.white);
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);

		/**
		 * Thêm 1 nhân viên vào danh sách bảng NV
		 * Nút thêm NV
		 * JButton btnThemNV
		 * Icon iconThemNV
		 */
		btnThemNV = new FixButton("Thêm");
		btnThemNV.setForeground(Color.black);
		btnThemNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemNV.setBackground(new Color(57, 210, 247));
		btnThemNV.setBounds(10, 423, 291, 35);
		Icon iconThemNV = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemNV.setIcon(iconThemNV);
		pNhapThongTin.add(btnThemNV);

		/**
		 * Sửa thông tin nhân viên
		 * Nút sửa thông tin NV
		 * JButton btnSuaNV
		 * Icon iconSuaNV
		 */
		btnSuaNV = new FixButton("Sửa");
		btnSuaNV.setForeground(Color.black);
		btnSuaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaNV.setBackground(new Color(133, 217, 191));
		btnSuaNV.setBounds(10, 469, 291, 35);
		Icon iconSuaNV = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaNV.setIcon(iconSuaNV);
		pNhapThongTin.add(btnSuaNV);

		/**
		 * Hủy nhân viên đã nghỉ việc
		 * Nút hủy NV
		 * JButton btnHủyNV
		 * Icon iconThemNV
		 */
		btnHuy = new FixButton("Hủy");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHuy.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnHuy.setBackground(new Color(0xE91940));
		btnHuy.setBounds(10, 515, 291, 35);
		Icon iconHuyNV = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		btnHuy.setIcon(iconHuyNV);
		pNhapThongTin.add(btnHuy);

		/**
		 * Làm mới: xoắ trắng các text, xóa tất cả nội dung trong bảng, đât mặc định các combobox, bỏ chọn checkbox và các radiobutton
		 * Nút làm mới
		 * JButton btnHủyNV
		 * Icon iconThemNV
		 */
		btnLamMoiNV = new FixButton("Làm mới");
		btnLamMoiNV.setForeground(Color.WHITE);
		btnLamMoiNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiNV.setBackground(new Color(114, 23, 153));
		btnLamMoiNV.setBounds(10, 561, 291, 35);
		Icon iconLamMoiNV = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoiNV.setIcon(iconLamMoiNV);
		pNhapThongTin.add(btnLamMoiNV);

		
		/**
		 * Khung sắp xếp chức các mục sắp xếp theo tăng dần, giảm dần, mã NV, tên NV, chức vụ
		 * JPanel pSapXep
		 */
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(171, 192, 238));
		pSapXep.setBounds(332, 50, 928, 46);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);

		/**
		 * Chọn kiểu sắp xếp tăng dần hoặc giảm dần
		 * JComboBox cboSapXep 
		 */
		cboSapXep = new JComboBox<Object>(new Object[] {"Tăng dần", "Giảm dần"});
		cboSapXep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBackground(Color.WHITE);
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBounds(32, 12, 121, 28);
		pSapXep.add(cboSapXep);

		/**
		 * Nhấn tích chọn nút tất cả để hiện toàn bộ danh sách thông tin lên bảng nhân viên
		 * JCheckBox chkTatCa
		 */
		chkTatCa = new JCheckBox("Tất cả");
		chkTatCa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chkTatCa.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkTatCa.setBackground(new Color(171, 192, 238));
		chkTatCa.setBounds(200, 13, 113, 25);
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

		
		/**
		 * Nhấn chọn sắp xếp kí tự từ trái sang phải theo mã, tên nhân viên tăng hoặc giảm dần
		 * Sắp xếp chức vụ tăng dần: phục vụ, thu ngân, quản lý và giảm dần ngược lại
		 * JRadioButton rdoTheoMaNV, rdoTheoTenNV, rboTheoChucVuNV
		 */
		rdoTheoMaNV = new JRadioButton("Theo mã nhân viên");
		rdoTheoMaNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaNV.setBounds(310, 13, 159, 25);
		rdoTheoMaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoMaNV);

		rdoTheoTenNV = new JRadioButton("Theo tên nhân viên");
		rdoTheoTenNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoTenNV.setBounds(512, 13, 161, 25);
		rdoTheoTenNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoTenNV);

		rdoTheoChucVuNV = new JRadioButton("Theo chức vụ nhân viên");
		rdoTheoChucVuNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoChucVuNV.setBounds(710, 13, 195, 25);
		rdoTheoChucVuNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoChucVuNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoChucVuNV);

		/**
		 * Nhóm các radiobutton
		 * ButtonGroup bg
		 */
		bg=new ButtonGroup();
		bg.add(btnThemNV); bg.add(btnSuaNV); bg.add(btnHuy); bg.add(btnLamMoiNV);
		bg.add(rdoTheoMaNV); bg.add(rdoTheoTenNV); bg.add(rdoTheoChucVuNV);

		/**
		 * Bảng chứa các thông tin nhân viên
		 * JScrollPane scrollPaneNV
		 * String col[]: tên các cột
		 * JTable tblNV: nội dung của bảng
		 * JTableHeader tbHeader: định dạng khung các tên cột
		 */
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
		tblNV.getColumnModel().getColumn(5).setPreferredWidth(300); //diachi
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

		/**
		 * Hình nền của giao diện
		 */
		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);


		/**
		 * Các sự kiện của giao diện quản lý nhân viên
		 */
		txtTim.addFocusListener(this);
		
		cboCaLamViec.addActionListener(this);
		
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

	/**
	 * Xóa hết dữ liệu trong bảng danh sách thông tin nhân viên
	 * @param defaultTableModel
	 */
	private void removeDanhSachNV(DefaultTableModel defaultTableModel) {
		//		DefaultTableModel dtm = (DefaultTableModel) tableNV.getModel();
		//		dtm.getDataVector().removeAllElements();

		while(tblNV.getRowCount() > 0){
			modelNV.removeRow(0);
		}
	}

	/**
	 * Xóa trắng textfield và textarea, đặt lại mặc định các combobox và các button
	 */
	private void xoaTrang() {
		txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, ca làm việc.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtHoTen.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCccd.setText("");

		cboChucVu.setSelectedItem("Quản lý");
		cboGioiTinh.setSelectedItem("Nam");
		cboCaLamViec.setSelectedItem("1");
		lblSubGioTheoCa.setText("08:00 AM - 13:00 PM");
		//dateChooserNgaySinh.setDate(new Date(0));
		lblNVDaNghiViec.setText("");

		chkTatCa.setSelected(false);
		rdoTheoMaNV.setSelected(false);
		rdoTheoTenNV.setSelected(false);
		rdoTheoChucVuNV.setSelected(false);
	}

	/**
	 * Chọn và chú thích giờ theo ca làm việc
	 */
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

	/**
	 * Hiện danh sách thông tin nhân viên đang làm việc
	 * @param nv
	 */
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

	
	/**
	 * Hiện thông tin 1 nhân viên
	 * @param nv
	 */
	private void loadNV(NhanVien nv) {
		TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(nv.getMaNhanVien());
		modelNV.setRowCount(0);
		modelNV.addRow(new Object[] {
				nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(), 
				dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(), 
				dfLuong.format(Math.round(nv.getLuong())), nv.getCaLamViec(), nv.getTrangThaiLamViec(), tk.getMatKhau()	
		});
	}

	
	/**
	 * Hiện thông tin nhân viên đã nghỉ việc
	 * @param nvNghi
	 */
	private void loadNVDaNghiViec(NhanVien nvNghi) {
		TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(nvNghi.getMaNhanVien());
		modelNV.setRowCount(0);
		modelNV.addRow(new Object[] {
				nvNghi.getMaNhanVien(), nvNghi.getTenNhanVien(), nvNghi.getChucVu(), nvNghi.getGioiTinh(), 
				dfNgaySinh.format(nvNghi.getNgaySinh()), nvNghi.getDiaChi(), nvNghi.getSdt(), nvNghi.getCccd(), 
				dfLuong.format(Math.round(nvNghi.getLuong())), nvNghi.getCaLamViec(), nvNghi.getTrangThaiLamViec(), tk.getMatKhau()	
		});
	}
	
	
	/**
	 * Hiện danh sách thông tin NV theo mã và sđt nhân viên
	 * @param lstNV
	 */
	private void loadDanhSachMaVaSdtNV(ArrayList<NhanVien> lstNV)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstMa = daoNhanVien.getMaVaSDTNV(txtTim.getText().toLowerCase().trim());
		for(NhanVien infoNV : lstMa) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}
	}

	
	/**
	 * Hiện danh sách thông tin NV theo tên nhân viên
	 * @param lstNV
	 */
	private void loadDanhSachTenNV(ArrayList<NhanVien> lstNV)  {
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

	
	/**
	 * Hiện danh sách thông tin NV theo ca làm việc
	 * @param lstNV
	 */
	private void loadDanhSachCaNV(ArrayList<NhanVien> lstNV)  {
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

	/**
	 * Sự kiện tìm kiếm thông tin nhân viên
	 */
	private void findNV() {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		ArrayList<NhanVien> lstNV = null;
		
		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		String regexTenNV= "^[ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$";
		String regexSDT  = "^(0[0-9]{9})$";
		String regexCa   = "^[1-3]{1}$";
		
			if(regex.regexTimNV(txtTim)) {
					if(input.matches(regexMaNV)) {
						lstNV = daoNhanVien.getMaVaSDTNV(input);
						loadDanhSachMaVaSdtNV(lstNV);
					}
					else if(input.matches(regexTenNV)) {
						lstNV = daoNhanVien.getTenNV(input);
						loadDanhSachTenNV(lstNV);
					}
					else if(input.matches(regexSDT)) {
						lstNV = daoNhanVien.getMaVaSDTNV(input);
						loadDanhSachMaVaSdtNV(lstNV);
					}
					else if(input.matches(regexCa)) {
						lstNV = daoNhanVien.getCaNV(input);
						loadDanhSachCaNV(lstNV);
					}
					if(lstNV.size()==0)
						JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tìm kiếm phù hợp!", "Thông báo", JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					txtTim.selectAll();
		}
	}

	/**
	 * Tạo tài khoản nhân viên mới, thêm thông tin nhân viên vào cơ sở dữ liệu và hiện lên bảng danh sách
	 */
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

	/**
	 * Hủy tài khoản nhân viên, chuyển trạng thái đang làm việc thành đã nghỉ việc
	 * @return
	 */
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

	/**
	 * Sửa, cập nhật thông tin nhân viên
	 */
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
//						loadDanhSachNV(nv);
//						loadNV(nv);
						TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(maNV);
						removeDanhSachNV(modelNV);
						modelNV.setRowCount(0);
						modelNV.addRow(new Object[] {
								maNV, nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(), 
								dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(), 
								dfLuong.format(Math.round(200000)), nv.getCaLamViec(), "Đang làm việc", tk.getMatKhau()	
						});
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

	/**
	 * Sắp xếp theo mã NV tăng dần
	 * @param nv
	 */
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

	/**
	 * Sắp xếp theo mã NV giảm dần
	 * @param nv
	 */
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

	/**
	 * Sắp xếp theo tên NV tăng dần
	 * @param nv
	 */
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

	/**
	 * Sắp xếp theo tên NV giảm dần
	 * @param nv
	 */
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

	/**
	 * Sắp xếp chức vụ tăng dần: phục vụ, thu ngân, quản lý
	 * @param nv
	 */
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

	/**
	 * Sắp xếp chức vụ giảm dần: quản lý, thu ngân, phục vụ
	 * @param nv
	 */
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

	/**
	 *Code sự kiện
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		//sub giờ làm việc theo ca
		if(o.equals(cboCaLamViec)) {
			subGioTheoCa();
		}

		//tìm NV
		if(o.equals(btnTim)) {
			if(txtTim.getText().equals("") || txtTim.getText().equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, ca làm việc.")) {
				chkTatCa.setSelected(false);
				removeDanhSachNV(modelNV);
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				txtTim.requestFocus();
			}else {
				findNV();
				xoaTrang();
			}
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

	/**
	 * Chọn 1 dòng thông tin nhân viên trong bảng danh sách thông tin NV
	 */
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

	/**
	 *Sự kiện của click chuột
	 */
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

	/**
	 *Sự kiện placeholder của txtTim
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(txtTim.getText().equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, ca làm việc.")) {
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
			txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt, ca làm việc.");
		}
	}
}
