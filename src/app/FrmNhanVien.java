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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
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

public class FrmNhanVien extends JFrame implements ActionListener, MouseListener, FocusListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnTim, btnThemNV, btnSuaNV, btnHuy, btnLamMoiNV;
	private Panel pMain;
	@SuppressWarnings("unused")
	private String sHeaderTenNV, sHeaderMaNV;
	@SuppressWarnings("unused")
	private Date dNgayHienTai;
	private LocalDate now;
	private Date dNow;
	private int nam, thang, ngay;
	private JLabel lblNVDaNghiViec, lblSubGioTheoCa;
	private JTextField txtTim, txtHoTen, txtSDT, txtCccd, txtDiaChi;
	private JComboBox<Object> cboChucVu, cboGioiTinh, cboCaLamViec, cboSapXep;
	private JRadioButton rdoTheoMaNV, rdoTheoTenNV, rdoTheoChucVuNV;
	private ButtonGroup bg;
	private JTable tblNV;
	private DefaultTableModel modelNV;
	private SimpleDateFormat dfNgaySinh;
	private DecimalFormat dfLuong;
	private JDateChooser chooserNgaySinh;

	private DAONhanVien daoNhanVien; 
	private DAOPhatSinhMa daoPhatSinhMa;
	private DAOTaiKhoan daoTaiKhoan;
	private Regex regex;

	private NhanVien nv;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	private FixButton btnExcels;


	/**
	 * @return pMain
	 */
	public Panel getPanel() {
		return pMain;
	}

	/**
	 * K??? th???a t??n v?? m?? nh??n vi??n, ng??y hi???n t???i c???a FrmQuanLy
	 * @param sHeaderTenNV
	 * @param sHeaderMaNV
	 * @param dNgayHienTai
	 */
	@SuppressWarnings("deprecation")
	public  FrmNhanVien(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		/**
		 * K???t n???i database
		 */
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/**
		 * Khai b??o c??c DAO
		 */
		daoNhanVien=new DAONhanVien();
		daoPhatSinhMa=new DAOPhatSinhMa();
		daoTaiKhoan=new DAOTaiKhoan();
		regex=new Regex();

		/**
		 * ?????nh d???ng ng??y, l????ng trong b???ng
		 */
		dfNgaySinh=new SimpleDateFormat("dd/MM/yyyy");
		dfLuong=new DecimalFormat("##,###,###");

		/**
		 * Khai b??o entity NhanVien
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
		 * Nh???p th??ng tin nh??n vi??n m???i
		 * Panel pNhapThongTin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 312, 607);
		pNhapThongTin.setLayout(null);
		pNhapThongTin.setToolTipText("C??c th??ng tin nh??n vi??n c???n nh???p");
		pMain.add(pNhapThongTin);

		lblNhapThongTin = new JLabel("Nh???p th??ng tin nh??n vi??n");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 292, 29);
		pNhapThongTin.add(lblNhapThongTin);

		JLabel lblHoTen = new JLabel("H??? v?? t??n:");
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
		txtHoTen.setText("??inh Quang Tu???n");

		JLabel lblSDT = new JLabel("S??T:");
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

		JLabel lblDiaChi = new JLabel("?????a ch???:");
		lblDiaChi.setBounds(10, 139, 72, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(110, 136, 191, 28);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("118 Ho??ng V??n Th???, Q.Ph?? Nhu???n, Tp.HCM");

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

		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		lblGioiTinh.setBounds(11, 249, 88, 23);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioiTinh);
		cboGioiTinh = new JComboBox<Object>(new Object[] {"Nam", "N???"});
		cboGioiTinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGioiTinh.setBounds(111, 247, 191, 25);
		cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGioiTinh.setBackground(Color.white);
		cboGioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboGioiTinh.setToolTipText("Ch???n gi???i t??nh");
		pNhapThongTin.add(cboGioiTinh);

		now = LocalDate.now();
		ngay = now.getDayOfMonth(); 
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		dNow = new Date(nam, thang, ngay);

		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
		lblNgaySinh.setBounds(10, 214, 90, 23);
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblNgaySinh);
		chooserNgaySinh = new JDateChooser();
		chooserNgaySinh.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chooserNgaySinh.setBounds(110, 213, 191, 25);
		chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		chooserNgaySinh.setDate(dNgayHienTai);
		chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgaySinh.getCalendarButton().setToolTipText("Ch???n ng??y sinh");
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 17, Color.white);
		chooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgaySinh);

		JLabel lblChucVu = new JLabel("Ch???c v???:");
		lblChucVu.setBounds(10, 286, 98, 19);
		lblChucVu.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblChucVu);
		cboChucVu = new JComboBox<Object>(new Object[] {"Qu???n l??", "Ph???c v???", "Thu ng??n"});
		cboChucVu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboChucVu.setBounds(110, 283, 191, 25);
		cboChucVu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboChucVu.setBackground(Color.white);
		cboChucVu.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboChucVu.setToolTipText("Ch???n ch???c v??? nh??n vi??n");
		pNhapThongTin.add(cboChucVu);

		JLabel lblCaLamViec = new JLabel("Ca l??m vi???c:");
		lblCaLamViec.setBounds(10, 323, 90, 20);
		lblCaLamViec.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblCaLamViec);
		cboCaLamViec = new JComboBox<Object>(new Object[] {"1", "2", "3"});
		cboCaLamViec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboCaLamViec.setBounds(110, 319, 56, 25);
		cboCaLamViec.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboCaLamViec.setBackground(Color.white);
		cboCaLamViec.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboCaLamViec.setToolTipText("Ch???n ca l??m vi???c");
		pNhapThongTin.add(cboCaLamViec);

		lblSubGioTheoCa = new JLabel("08:00 AM - 13:00 PM");
		lblSubGioTheoCa.setBounds(167, 323, 145, 20);
		lblSubGioTheoCa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		pNhapThongTin.add(lblSubGioTheoCa);

		lblNVDaNghiViec = new JLabel();
		lblNVDaNghiViec.setHorizontalAlignment(SwingConstants.CENTER);
		lblNVDaNghiViec.setForeground(Color.RED);
		lblNVDaNghiViec.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblNVDaNghiViec.setBounds(10, 355, 291, 28);
		pNhapThongTin.add(lblNVDaNghiViec);

		/**
		 * T??m ki???m nh??n vi??n: m?? nh??n vi??n, t??n nh??n vi??n, s??t, ch???c v???, ca l??m vi???c
		 * Label lblTim
		 * JTextField txtTim
		 * S??? ki???n placeholder t??m NV: FocusListener
		 * N??t t??m NV
		 * JButton btnTim
		 * Icon iconTim
		 */
		JLabel lblTim = new JLabel("T??m ki???m:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(332, 11, 90, 35);
		pMain.add(lblTim);
		//
		txtTim = new JTextField();
		txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t, ca l??m vi???c.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(414, 11, 540, 33);
		txtTim.setToolTipText("T??m ki???m th??ng tin nh??n vi??n");
		pMain.add(txtTim);
		//
		btnTim = new FixButton("T??m");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(964, 12, 127, 33);
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.white);
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);
		
		/**
		 * N??t xu???t file Excel
		 * JButton btnExcels
		 * Icon iconExcel
		 */
		btnExcels = new FixButton("Xu???t Excels");
		btnExcels.setForeground(Color.WHITE);
		btnExcels.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcels.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnExcels.setBackground(new Color(16, 124, 65));
		btnExcels.setBounds(1101, 12, 159, 33);
		Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);
		btnExcels.setIcon(iconExcel);
		pMain.add(btnExcels);

		/**
		 * Th??m 1 nh??n vi??n v??o danh s??ch b???ng NV
		 * N??t th??m NV
		 * JButton btnThemNV
		 * Icon iconThemNV
		 */
		btnThemNV = new FixButton("Th??m");
		btnThemNV.setForeground(Color.black);
		btnThemNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemNV.setBackground(new Color(57, 210, 247));
		btnThemNV.setBounds(10, 423, 291, 35);
		Icon iconThemNV = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemNV.setIcon(iconThemNV);
		pNhapThongTin.add(btnThemNV);

		/**
		 * S???a th??ng tin nh??n vi??n
		 * N??t s???a th??ng tin NV
		 * JButton btnSuaNV
		 * Icon iconSuaNV
		 */
		btnSuaNV = new FixButton("S???a");
		btnSuaNV.setForeground(Color.black);
		btnSuaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaNV.setBackground(new Color(133, 217, 191));
		btnSuaNV.setBounds(10, 469, 291, 35);
		Icon iconSuaNV = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaNV.setIcon(iconSuaNV);
		pNhapThongTin.add(btnSuaNV);

		/**
		 * H???y nh??n vi??n ???? ngh??? vi???c
		 * N??t h???y NV
		 * JButton btnH???yNV
		 * Icon iconThemNV
		 */
		btnHuy = new FixButton("H???y");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHuy.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnHuy.setBackground(new Color(0xE91940));
		btnHuy.setBounds(10, 515, 291, 35);
		Icon iconHuyNV = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		btnHuy.setIcon(iconHuyNV);
		pNhapThongTin.add(btnHuy);

		/**
		 * L??m m???i: xo??? tr???ng c??c text, x??a t???t c??? n???i dung trong b???ng, ????t m???c ?????nh c??c combobox, b??? ch???n checkbox v?? c??c radiobutton
		 * N??t l??m m???i
		 * JButton btnH???yNV
		 * Icon iconThemNV
		 */
		btnLamMoiNV = new FixButton("L??m m???i");
		btnLamMoiNV.setForeground(Color.WHITE);
		btnLamMoiNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiNV.setBackground(new Color(114, 23, 153));
		btnLamMoiNV.setBounds(10, 561, 291, 35);
		Icon iconLamMoiNV = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoiNV.setIcon(iconLamMoiNV);
		pNhapThongTin.add(btnLamMoiNV);


		/**
		 * Khung s???p x???p ch???c c??c m???c s???p x???p theo t??ng d???n, gi???m d???n, m?? NV, t??n NV, ch???c v???
		 * JPanel pSapXep
		 */
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "S???p x???p", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(171, 192, 238));
		pSapXep.setBounds(332, 50, 928, 46);
		pSapXep.setToolTipText("S???p x???p th??ng tin nh??n vi??n");
		pMain.add(pSapXep);
		pSapXep.setLayout(null);

		/**
		 * Ch???n ki???u s???p x???p t??ng d???n ho???c gi???m d???n
		 * JComboBox cboSapXep 
		 */
		cboSapXep = new JComboBox<Object>(new Object[] {"T??ng d???n", "Gi???m d???n"});
		cboSapXep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBackground(Color.WHITE);
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBounds(32, 12, 121, 28);
		cboSapXep.setToolTipText("S???p x???p th??ng tin nh??n vi??n t??ng d???n ho???c gi???m d???n theo c??c ti??u ch??");
		pSapXep.add(cboSapXep);

		/**
		 * Nh???n ch???n s???p x???p k?? t??? t??? tr??i sang ph???i theo m??, t??n nh??n vi??n t??ng ho???c gi???m d???n
		 * S???p x???p ch???c v??? t??ng d???n: ph???c v???, thu ng??n, qu???n l?? v?? gi???m d???n ng?????c l???i
		 * JRadioButton rdoTheoMaNV, rdoTheoTenNV, rboTheoChucVuNV
		 */
		rdoTheoMaNV = new JRadioButton("Theo m?? nh??n vi??n");
		rdoTheoMaNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaNV.setBounds(235, 15, 159, 25);
		rdoTheoMaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoMaNV);

		rdoTheoTenNV = new JRadioButton("Theo t??n nh??n vi??n");
		rdoTheoTenNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoTenNV.setBounds(472, 15, 161, 25);
		rdoTheoTenNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoTenNV);

		rdoTheoChucVuNV = new JRadioButton("Theo ch???c v??? nh??n vi??n");
		rdoTheoChucVuNV.setToolTipText("Ch???c v??? t??ng d???n: ph???c v???, thu ng??n, qu???n l?? v?? ng?????c l???i");
		rdoTheoChucVuNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoChucVuNV.setBounds(710, 15, 195, 25);
		rdoTheoChucVuNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoChucVuNV.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoChucVuNV);

		/**
		 * Nh??m c??c radiobutton
		 * ButtonGroup bg
		 */
		bg=new ButtonGroup();
		bg.add(btnThemNV); bg.add(btnSuaNV); bg.add(btnHuy); bg.add(btnLamMoiNV);
		bg.add(rdoTheoMaNV); bg.add(rdoTheoTenNV); bg.add(rdoTheoChucVuNV);

		/**
		 * B???ng ch???a c??c th??ng tin nh??n vi??n
		 * JScrollPane scrollPaneNV
		 * String col[]: t??n c??c c???t
		 * JTable tblNV: n???i dung c???a b???ng
		 * JTableHeader tbHeader: ?????nh d???ng khung c??c t??n c???t
		 */
		JScrollPane scrollPaneNV = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneNV.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneNV.setBackground(new Color(164, 44, 167));
		scrollPaneNV.setBounds(332, 102, 928, 516);
		scrollPaneNV.getHorizontalScrollBar();
		scrollPaneNV.setToolTipText("Danh s??ch th??ng tin nh??n vi??n");
		pMain.add(scrollPaneNV);

		String col[] = {"M?? NV", "H??? v?? t??n nh??n vi??n", "Ch???c v???", "Gi???i t??nh", "Ng??y sinh", "?????a ch???", "S??T", "CCCD", "L????ng", "Ca l??m vi???c", "Tr???ng th??i l??m vi???c", "M???t kh???u"};
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
		tblNV.setToolTipText("Ch???n th??ng tin nh??n vi??n ????? th???c hi???n c??c ch???c n??ng");

		JTableHeader tbHeader = tblNV.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		tbHeader.setToolTipText("Danh s??ch th??ng tin nh??n vi??n");

		tblNV.getColumnModel().getColumn(0).setPreferredWidth(60); 
		tblNV.getColumnModel().getColumn(1).setPreferredWidth(155);
		tblNV.getColumnModel().getColumn(2).setPreferredWidth(80); 
		tblNV.getColumnModel().getColumn(3).setPreferredWidth(75); 
		tblNV.getColumnModel().getColumn(4).setPreferredWidth(80); 
		tblNV.getColumnModel().getColumn(5).setPreferredWidth(350); 
		tblNV.getColumnModel().getColumn(6).setPreferredWidth(90); 
		tblNV.getColumnModel().getColumn(7).setPreferredWidth(100); 
		tblNV.getColumnModel().getColumn(8).setPreferredWidth(80); 
		tblNV.getColumnModel().getColumn(9).setPreferredWidth(90); 
		tblNV.getColumnModel().getColumn(10).setPreferredWidth(145);
		tblNV.getColumnModel().getColumn(11).setPreferredWidth(120);

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
		 * H??nh n???n c???a giao di???n
		 */
		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);


		/**
		 * C??c s??? ki???n c???a giao di???n qu???n l?? nh??n vi??n
		 */
		loadDanhSachNV(nv);
		
		txtTim.addFocusListener(this);

		cboCaLamViec.addActionListener(this);

		btnTim.addActionListener(this); 
		btnThemNV.addActionListener(this);
		btnSuaNV.addActionListener(this);
		btnHuy.addActionListener(this);
		btnLamMoiNV.addActionListener(this);
		btnExcels.addActionListener(this);

		cboSapXep.addActionListener(this);		

		rdoTheoMaNV.addActionListener(this);
		rdoTheoTenNV.addActionListener(this);
		rdoTheoChucVuNV.addActionListener(this);

		tblNV.addMouseListener(this);
		
		txtTim.addKeyListener(this);
		btnTim.addKeyListener(this);
		btnLamMoiNV.addKeyListener(this);
	}

	/**
	 * X??a h???t d??? li???u trong b???ng danh s??ch th??ng tin nh??n vi??n
	 * @param defaultTableModel tr??? v??? modelNV
	 */
	private void removeDanhSachNV(DefaultTableModel defaultTableModel) {
		while(tblNV.getRowCount() > 0){
			modelNV.removeRow(0);
		}
	}

	/**
	 * X??a tr???ng textfield v?? textarea, ?????t l???i m???c ?????nh c??c combobox v?? c??c button
	 */
	private void resetAll() {
		txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t, ca l??m vi???c.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);

		txtHoTen.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCccd.setText("");

		cboChucVu.setSelectedItem("Qu???n l??");
		cboGioiTinh.setSelectedItem("Nam");
		cboCaLamViec.setSelectedItem("1");
		lblSubGioTheoCa.setText("08:00 AM - 13:00 PM");
		chooserNgaySinh.setDate(dNow);
		lblNVDaNghiViec.setText("");

		rdoTheoMaNV.setSelected(false);
		rdoTheoTenNV.setSelected(false);
		rdoTheoChucVuNV.setSelected(false);
		
		txtHoTen.requestFocus();
	}

	/**
	 * Ch???n v?? ch?? th??ch gi??? theo ca l??m vi???c
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
	 * Hi???n danh s??ch th??ng tin nh??n vi??n ??ang l??m vi???c t??? m?? NV g???m: 
	 * m?? NV, t??n NV, ch???c v???, gi???i t??nh, ng??y sinh, ?????a ch???, s??t, cccd, l????ng, ca l??m vi???c, tr???ng th??i l??m vi???c, m???t kh???u
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
	 * Hi???n danh s??ch th??ng tin NV theo m?? v?? s??t nh??n vi??n
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
	 * Hi???n danh s??ch th??ng tin NV theo t??n nh??n vi??n
	 * @param lstNV
	 */
	private void loadDanhSachTenNV(ArrayList<NhanVien> lstNV)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstName = daoNhanVien.getTenNV(txtTim.getText().trim());
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
	 * Hi???n danh s??ch th??ng tin NV theo ca l??m vi???c
	 * @param lstNV
	 */
	private void loadDanhSachCaNV(ArrayList<NhanVien> lstNV)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstCa = daoNhanVien.getCaNV(txtTim.getText().trim());
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
	 * S??? ki???n t??m ki???m th??ng tin nh??n vi??n
	 */
	private void findNV() {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		ArrayList<NhanVien> lstNV = null;

		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		String regexTenNV= "^[ A-Za-za-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????]+$";
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
			if(lstNV.size()==0) {
				JOptionPane.showMessageDialog(this, "Kh??ng t??m th???y th??ng tin t??m ki???m ph?? h???p!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
				txtTim.requestFocus();
				txtTim.selectAll();
			}
		}
	}

	/**
	 * T???o t??i kho???n nh??n vi??n m???i, th??m th??ng tin nh??n vi??n v??o c?? s??? d??? li???u v?? hi???n l??n b???ng danh s??ch
	 */
	@SuppressWarnings("deprecation")
	private void addNV() {
		String phatSinhMaNV = daoPhatSinhMa.getMaNV();
		String hoTen = txtHoTen.getText().trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String chucVu = cboChucVu.getSelectedItem().toString();
		String cccd = txtCccd.getText().trim();
		String gioiTinh = cboGioiTinh.getSelectedItem().toString();

		java.util.Date date = chooserNgaySinh.getDate();
		Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
		int age = nam - ngaySinh.getYear();

		int caLamViec = Integer.parseInt((String) cboCaLamViec.getSelectedItem());

		TaiKhoan tk=new TaiKhoan(phatSinhMaNV);
		String matKhau = phatSinhMaNV.concat(sdt);

		if(hoTen.equals("") || sdt.equals("") || diaChi.equals("") || cccd.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin ?????y ?????!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
			txtHoTen.requestFocus();
		}else {
			if(regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi) && regex.regexCCCD(txtCccd)) {
				if(daoNhanVien.checkSdtNV(sdt)) {
					if(daoNhanVien.checkCccdNV(cccd)) {
						if(age>=18 && ngaySinh.getDate()>0 && ngaySinh.getDate()<=31 && ngaySinh.getMonth()>0 && ngaySinh.getMonth()<=12 && ngaySinh.getYear()>0 && ngaySinh.getYear()<nam) { 
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
							nv.setNgaySinh(ngaySinh);
							nv.setDiaChi(diaChi);
							nv.setSdt(sdt);
							nv.setCccd(cccd);
							if(cboChucVu.getSelectedItem()=="Qu???n l??")
								nv.setLuong(10000000);
							if(cboChucVu.getSelectedItem()=="Thu ng??n")
								nv.setLuong(6000000);
							if(cboChucVu.getSelectedItem()=="Ph???c v???")
								nv.setLuong(5000000);
							nv.setCaLamViec(caLamViec);
							nv.setTrangThaiLamViec("??ang l??m vi???c");
							try {
								new DAONhanVien().themNV(nv);
							}catch (SQLException e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(this, "Th??m nh??n vi??n th???t b???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
							}

							//them vao table  
							resetAll();
							modelNV.addRow(new Object[] {
									phatSinhMaNV, hoTen, chucVu, gioiTinh, 
									dfNgaySinh.format(chooserNgaySinh.getDate()), diaChi, sdt, cccd,
									dfLuong.format(Math.round(nv.getLuong())), caLamViec, "??ang l??m vi???c", matKhau
							});
							String mkTK = "\nM???t kh???u: "+matKhau;
							JOptionPane.showMessageDialog(this, "Th??m th??nh c??ng!\nM?? t??i kho???n: "+phatSinhMaNV +mkTK, "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
						} else
							JOptionPane.showMessageDialog(this, "Nh??n vi??n l??m vi???c ph???i tr??n 18 tu???i!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
					} else
						JOptionPane.showMessageDialog(this, "C??n c?????c c??ng d??n ???? ????ng k??", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
				} else 
					JOptionPane.showMessageDialog(this, "S??? ??i???n tho???i ???? ????ng k??", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * H???y t??i kho???n nh??n vi??n, chuy???n tr???ng th??i ??ang l??m vi???c th??nh ???? ngh??? vi???c
	 * @return
	 */
	private boolean cancelNV() {
		int row = tblNV.getSelectedRow();
		if(row>=0) {
			int cancel = JOptionPane.showConfirmDialog(null, "B???n mu???n h???y t??i kho???n nh??n vi??n n??y?", "Th??ng b??o", JOptionPane.YES_NO_OPTION);
			if(cancel == JOptionPane.YES_OPTION) {
				NhanVien nv=new NhanVien();
				String maNV = tblNV.getValueAt(row, 0).toString();
				if(!maNV.equals(sHeaderMaNV)) {
					try {
						modelNV.removeRow(row);
						removeDanhSachNV(modelNV);
						new DAONhanVien().huyNV(maNV);
						loadDanhSachNV(nv);
						JOptionPane.showMessageDialog(null, "???? h???y t??i kho???n!");
					}catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "H???y t??i kho???n th???t b???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
					}
				}else
					JOptionPane.showMessageDialog(this, "T??i kho???n nh??n vi??n n??y kh??ng ???????c h???y v?? ??ang ????ng nh???p h??? th???ng!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin t??i kho???n nh??n vi??n c???n h???y!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

	/**
	 * S???a, c???p nh???t th??ng tin nh??n vi??n
	 */
	@SuppressWarnings("deprecation")
	private void updateNV() {
		int row = tblNV.getSelectedRow();
		if(row>=0) {
			int update = JOptionPane.showConfirmDialog(this, "B???n mu???n s???a th??ng tin nh??n vi??n n??y?", "Th??ng b??o", JOptionPane.YES_NO_OPTION);
			if(update == JOptionPane.YES_OPTION) {
				NhanVien nv=new NhanVien();
				String maNV = (String) tblNV.getValueAt(row, 0);
				java.util.Date date = chooserNgaySinh.getDate();
				Date ngaySinh=new Date(date.getYear(), date.getMonth(), date.getDate());
				int caLamViec = Integer.parseInt((String) cboCaLamViec.getSelectedItem());
				try {
					if(regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi) && regex.regexCCCD(txtCccd)) {
						nv.setTenNhanVien(txtHoTen.getText());
						nv.setChucVu((String) cboChucVu.getSelectedItem());
						nv.setGioiTinh((String) cboGioiTinh.getSelectedItem());
						nv.setNgaySinh(ngaySinh);
						nv.setDiaChi(txtDiaChi.getText());
						nv.setSdt(txtSDT.getText());
						nv.setCccd(txtCccd.getText());
						if(cboChucVu.getSelectedItem()=="Qu???n l??")
							nv.setLuong(10000000);
						if(cboChucVu.getSelectedItem()=="Thu ng??n")
							nv.setLuong(6000000);
						if(cboChucVu.getSelectedItem()=="Ph???c v???")
							nv.setLuong(5000000);
						nv.setCaLamViec(caLamViec);

						new DAONhanVien().capNhatNV(nv, maNV);
						TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(maNV);

						removeDanhSachNV(modelNV);
						modelNV.setRowCount(0);
						modelNV.addRow(new Object[] {
								maNV, nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(), 
								dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(), 
								dfLuong.format(Math.round(nv.getLuong())), nv.getCaLamViec(), "??ang l??m vi???c", tk.getMatKhau()	
						});
						JOptionPane.showMessageDialog(this, "Th??ng tin nh??n vi??n ???? ???????c s???a!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
					}
				}catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ch???nh s???a th??ng tin th???t b???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin nh??n vi??n c???n s???a!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * S???p x???p theo m?? NV t??ng d???n
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
	 * S???p x???p theo m?? NV gi???m d???n
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
	 * S???p x???p theo t??n NV t??ng d???n
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
	 * S???p x???p theo t??n NV gi???m d???n
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
	 * S???p x???p ch???c v??? t??ng d???n: ph???c v???, thu ng??n, qu???n l??
	 * @param nv
	 */
	private void sortChucVuTangDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstPV = daoNhanVien.sortCV("Ph???c v???");
		for(NhanVien infoNV : lstPV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstTN = daoNhanVien.sortCV("Thu ng??n");
		for(NhanVien infoNV : lstTN) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstQL = daoNhanVien.sortCV("Qu???n l??");
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
	 * S???p x???p ch???c v??? gi???m d???n: qu???n l??, thu ng??n, ph???c v???
	 * @param nv
	 */
	private void sortChucVuGiamDan(NhanVien nv)  {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstQL = daoNhanVien.sortCV("Qu???n l??");
		for(NhanVien infoNV : lstQL) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstTN = daoNhanVien.sortCV("Thu ng??n");
		for(NhanVien infoNV : lstTN) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] {
					infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(), infoNV.getGioiTinh(), 
					dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(), infoNV.getCccd(), 
					dfLuong.format(Math.round(infoNV.getLuong())), infoNV.getCaLamViec(), infoNV.getTrangThaiLamViec(), tk.getMatKhau()
			});
		}

		ArrayList<NhanVien> lstPV = daoNhanVien.sortCV("Ph???c v???");
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
	 * Xu???t file excel danh s??ch th??ng tin nh??n vi??n
	 * @throws IOException
	 */
	private void xuatExcel() throws IOException {
		XuatExcels xuat = new XuatExcels();
		FileDialog fileDialog  = new FileDialog(this, "Xu???t th??ng tin nh??n vi??n ra Excels", FileDialog.SAVE);
		fileDialog.setFile("Danh s??ch th??ng tin nh??n vi??n");
		fileDialog .setVisible(true);
		String name = fileDialog.getFile();
		String fileName = fileDialog.getDirectory() + name;

		if (name == null) 
			return;
		
		if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) 
			fileName += ".xlsx";
		
		xuat.xuatTable(tblNV, "DANH S??CH TH??NG TIN NH??N VI??N", fileName);
	}
	
	/**
	 *Code s??? ki???n
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		//sub gi??? l??m vi???c theo ca
		if(o.equals(cboCaLamViec)) {
			subGioTheoCa();
		}

		//t??m NV
		if(o.equals(btnTim)) {
			if(txtTim.getText().equals("") || txtTim.getText().equals("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t, ca l??m vi???c.")) {
				removeDanhSachNV(modelNV);
				JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin t??m ki???m!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
				txtTim.requestFocus();
			}else {
				findNV();
				txtTim.selectAll();
			}
		}

		//th??m NV
		if(o.equals(btnThemNV)) {
			addNV();
		}

		//s???a NV
		if(o.equals(btnSuaNV)) {
			updateNV();
		}

		//h???y t??i kho???n NV
		if(o.equals(btnHuy)) {
			cancelNV();
		}

		//l??m m???i
		if(o.equals(btnLamMoiNV)) {
			resetAll();
			bg.clearSelection();
			removeDanhSachNV(modelNV);
			loadDanhSachNV(nv);
		}
		
		//xu???t excel
		if(o.equals(btnExcels)) {
			try {
				xuatExcel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//x??a ch???n radbutton khi ch???n combobox
		if(o.equals(cboSapXep)) {
			if(cboSapXep.getSelectedItem()=="T??ng d???n")
				bg.clearSelection();
			if(cboSapXep.getSelectedItem()=="Gi???m d???n") 
				bg.clearSelection();
		}

		//sapxep t??ng
		if(cboSapXep.getSelectedItem()=="T??ng d???n") {
			if(o.equals(rdoTheoMaNV))	
				sortMaNVTangDan(nv);

			if(o.equals(rdoTheoTenNV)) 
				sortTenNVTangDan(nv);		//sort ten dau cua nv theo a-z

			if(o.equals(rdoTheoChucVuNV)) 
				sortChucVuTangDan(nv);		//sx chucvu tang dan: PV, TN, QL
		}

		//sapxep gi???m
		if(cboSapXep.getSelectedItem()=="Gi???m d???n") {
			if(o.equals(rdoTheoMaNV))
				sortMaNVGiamDan(nv);

			if(o.equals(rdoTheoTenNV)) 
				sortTenNVGiamDan(nv);		//sort ten dau cua nv theo z-a

			if(o.equals(rdoTheoChucVuNV)) 
				sortChucVuGiamDan(nv);		//sx chucvu giam dan: QL, TN, PV
		}
	}

	/**
	 *S??? ki???n c???a click chu???t
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		choose1NV();
	}
	
	/**
	 * Ch???n 1 d??ng th??ng tin nh??n vi??n trong b???ng danh s??ch th??ng tin NV
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
				if(trangThai.equals("???? ngh??? vi???c")) 
					lblNVDaNghiViec.setText("???? NGH??? VI???C.");
				if(trangThai.equals("??ang l??m vi???c")) 
					lblNVDaNghiViec.setText("");
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

	/**
	 *S??? ki???n placeholder c???a txtTim
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(txtTim.getText().equals("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t, ca l??m vi???c.")) {
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
			txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t, ca l??m vi???c.");
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
		
		if(o.equals(txtTim) && key==KeyEvent.VK_ENTER)
			btnTim.doClick();
		
		else if(o.equals(txtTim) && key == KeyEvent.VK_F5)
			btnLamMoiNV.doClick();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
