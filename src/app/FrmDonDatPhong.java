package app;

import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import dao.DAODonDatPhong;
import dao.DAOKhachHang;
import dao.DAOLoaiKH;
import dao.DAOLoaiPhong;
import dao.DAONhanVien;
import dao.DAOPhatSinhMa;
import dao.DAOPhong;
import dao.Regex;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.LoaiKH;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmDonDatPhong extends JPanel implements ActionListener, FocusListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String sHeaderMaNV, sHeaderTenNV;
	private Panel pMain;
	private Date dNgayHienTai;
	private JLabel lblTim, lblTenKH, lblLoaiKH, lblNgayDen, lblSDT, lblGioDen, lblTinhTrangDDP, lblDiaChi, lblChonPhong, lblBackGround;
	private JTextField txtTim, txtTenKH, txtSDT, txtDiaChi;
	private JComboBox<Object> cboTrangThaiDDP, cboSapXep;
	private JComboBox<String> cboLoaiKH, cboGio, cboPhut;
	private JTable tblPhong, tblDDP;
	private DefaultTableModel modelPhong, modelDDP;
	private JButton btnTim, btnThemDDP, btnSuaDDP, btnLamMoiDDP;
	private JRadioButton rdoTheoMaPhong, rdoTheoLoaiPhong;
	private ButtonGroup bg;
	private SimpleDateFormat dfNgay, dfHienGio;
	private DecimalFormat dfGiaPhong, dftxtGio, dftxtPhut;
	private java.util.Date timeNow1, timeNow2;
	private long nowHours, nowMinutes;
	@SuppressWarnings("unused")
	private Date dNow;
	private LocalDate now;
	private int ngay, thang, nam;
	private JDateChooser chooserNgayDen;

	private DAOPhong daoPhong;
	private DAOLoaiPhong daoLoaiPhong;
	private DAODonDatPhong daoDonDatPhong;
	private DAOKhachHang daoKhachHang;
	private DAOLoaiKH daoLoaiKH;
	private DAONhanVien daoNhanVien;
	private DAOPhatSinhMa daoPhatSinhMa;
	private Regex regex;

	private DonDatPhong ddp;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;


	/**
	 * @return pMain
	 */
	public Panel getFrmDDP() {
		return this.pMain;
	}
	
	/**
	 * K??? th???a t??n v?? m?? NV, ng??y hi???n t???i c???a FrmQuanLy
	 * @param sHeaderTenNV
	 * @param sHeaderMaNV
	 * @param dNgayHienTai
	 */
	@SuppressWarnings("deprecation")
	public FrmDonDatPhong(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

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
		daoPhong=new DAOPhong();
		daoLoaiPhong=new DAOLoaiPhong();
		daoDonDatPhong=new DAODonDatPhong();
		daoKhachHang=new DAOKhachHang();
		daoLoaiKH=new DAOLoaiKH();
		daoNhanVien=new DAONhanVien();
		daoPhatSinhMa=new DAOPhatSinhMa();
		regex=new Regex();

		/**
		 * Khai b??o c??c entity
		 */
		Phong p=new Phong();
		ddp=new DonDatPhong();

		/**
		 * ?????nh d???ng gi??, ng??y, gi???, ph??t trong b???ng
		 */
		dfNgay=new SimpleDateFormat("dd/MM/yyyy");
		dfHienGio=new SimpleDateFormat("HH:mm a");
		dfGiaPhong=new DecimalFormat("###,###");
		dftxtGio=new DecimalFormat("##");
		dftxtPhut=new DecimalFormat("##");

		/**
		 * Frame DonDatPhong
		 */
		setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1273, 629);
		add(pMain);
		pMain.setLayout(null);

		/**
		 * Nh???p th??ng tin ????n ?????t ph??ng m???i
		 * Panel pNhapThongTin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setToolTipText("C??c th??ng tin ????P c???n nh???p");
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 333, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);

		lblNgayDen = new JLabel("Ng??y ?????n:");
		lblNgayDen.setBounds(10, 267, 74, 19);
		lblNgayDen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblNgayDen);

		lblSDT = new JLabel("S??T:");
		lblSDT.setBounds(10, 160, 46, 28);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblSDT);

		lblNhapThongTin = new JLabel("Nh???p th??ng tin ????n ?????t ph??ng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 292, 29);
		pNhapThongTin.add(lblNhapThongTin);

		txtTenKH = new JTextField();
		txtTenKH.setBounds(145, 110, 175, 39);
		txtTenKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTenKH.setColumns(10);
		txtTenKH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		pNhapThongTin.add(txtTenKH);


		//test data nhanh
		txtTenKH.setText("??inh Quang Tu???n");

		lblTenKH = new JLabel("T??n kh??ch h??ng:");
		lblTenKH.setBounds(10, 116, 133, 19);
		lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblTenKH);

		lblLoaiKH = new JLabel("Lo???i kh??ch h??ng:");
		lblLoaiKH.setBounds(10, 69, 133, 19);
		lblLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblLoaiKH);

		cboLoaiKH = new JComboBox<String>();
		cboLoaiKH.setToolTipText("Ch???n lo???i kh??ch h??ng");
		cboLoaiKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboLoaiKH.setBounds(145, 64, 175, 35);
		cboLoaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboLoaiKH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboLoaiKH.setBackground(Color.white);
		pNhapThongTin.add(cboLoaiKH);
		ArrayList<LoaiKH> lsLoaiKH = daoLoaiKH.getAllLoaiKH();
		for(LoaiKH lkh : lsLoaiKH) 
			cboLoaiKH.addItem(lkh.getTenLoaiKH());

		chooserNgayDen = new JDateChooser();
		chooserNgayDen.getCalendarButton().setToolTipText("Ch???n ng??y ?????n");
		chooserNgayDen.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chooserNgayDen.setBounds(144, 263, 176, 38);
		chooserNgayDen.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgayDen.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgayDen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgayDen.setDateFormatString("dd/MM/yyyy");
		chooserNgayDen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 18, Color.white);
		chooserNgayDen.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgayDen);

		txtSDT = new JTextField();
		txtSDT.setBounds(144, 160, 176, 41);
		txtSDT.setColumns(10);
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtSDT.setText("0903142210");
		pNhapThongTin.add(txtSDT);

		lblDiaChi = new JLabel("?????a ch???:");
		lblDiaChi.setBounds(10, 216, 61, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(145, 212, 175, 40);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtDiaChi.setText("118 Ho??ng V??n Th???, P9, Q.Ph?? Nhu???n");
		pNhapThongTin.add(txtDiaChi);

		lblGioDen = new JLabel("Gi??? ?????n:");
		lblGioDen.setBounds(10, 316, 74, 19);
		lblGioDen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioDen);

		cboGio=new JComboBox<String>();
		cboGio.setToolTipText("Ch???n s??? gi??? ?????n");
		cboGio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGio.setBounds(145, 312, 72, 37);
		cboGio.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGio.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboGio.setBackground(Color.white);
		pNhapThongTin.add(cboGio);
		////
		cboPhut = new JComboBox<String>();
		cboPhut.setToolTipText("Ch???n s??? ph??t ?????n");
		cboPhut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboPhut.setBounds(246, 312, 74, 37);
		cboPhut.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboPhut.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboPhut.setBackground(Color.white);
		pNhapThongTin.add(cboPhut);
		for(int i=0; i<24; i++)
			cboGio.addItem(""+i);
		for(int i=0; i<60; i++)
			cboPhut.addItem(""+i);

		JLabel lblHaiCham = new JLabel(" :");
		lblHaiCham.setHorizontalAlignment(SwingConstants.CENTER);
		lblHaiCham.setBounds(218, 309, 21, 39);
		lblHaiCham.setFont(new Font("SansSerif", Font.PLAIN, 25));
		pNhapThongTin.add(lblHaiCham);

		lblTinhTrangDDP = new JLabel("Tr???ng th??i ????P:");
		lblTinhTrangDDP.setBounds(10, 364, 133, 19);
		lblTinhTrangDDP.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblTinhTrangDDP);

		cboTrangThaiDDP = new JComboBox<Object>(new Object[] {"Ch??? x??c nh???n", "???? x??c nh???n", "H???y", "???? tr??? ph??ng"});
		cboTrangThaiDDP.setToolTipText("Ch???n tr???ng th??i ????P");
		cboTrangThaiDDP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboTrangThaiDDP.setBounds(145, 360, 175, 39);
		cboTrangThaiDDP.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboTrangThaiDDP.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboTrangThaiDDP.setBackground(Color.white);
		pNhapThongTin.add(cboTrangThaiDDP);

		/**
		 * T??m ki???m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng.
		 * T??m kh??ch h??ng theo s??t.
		 * Label lblTim
		 * JTextField txtTim
		 * S??? ki???n placeholder t??m DDP: FocusListener
		 * N??t t??m DDP v?? KH
		 * Icon iconTim
		 */
		lblTim = new JLabel("T??m ki???m:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(550, 11, 80, 35);
		pMain.add(lblTim);
		//
		txtTim = new JTextField();
		txtTim.setToolTipText("T??m ki???m th??ng tin ????P v?? kh??ch h??ng");
		txtTim.setText("T??m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng, t??m kh??ch h??ng theo s??t.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(632, 12, 514, 33);
		pMain.add(txtTim);
		//
		btnTim = new FixButton("T??m");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1156, 11, 104, 33);
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.white);
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);


		/**
		 * B???ng ch???a c??c th??ng tin ph??ng
		 * JScrollPane scrollPaneChonPhong
		 * String col[]: t??n c??c c???t
		 * JTable tblPhong: n???i dung c???a b???ng
		 * JTableHeader tbHeaderPhong: ?????nh d???ng khung c??c t??n c???t
		 */
		lblChonPhong = new JLabel("Ch???n ph??ng:");
		lblChonPhong.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblChonPhong.setBounds(431, 70, 136, 29);
		pMain.add(lblChonPhong);

		JScrollPane scrollPaneChonPhong = new JScrollPane(tblPhong, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneChonPhong.setToolTipText("Danh s??ch th??ng tin ph??ng");
		scrollPaneChonPhong.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneChonPhong.setBackground(new Color(164, 44, 167));
		scrollPaneChonPhong.setBounds(353, 106, 273, 512);
		scrollPaneChonPhong.getHorizontalScrollBar();
		pMain.add(scrollPaneChonPhong);

		String colPhong[] = {"M?? ph??ng", "Lo???i ph??ng", "Gi?? ph??ng", "T??nh tr???ng ph??ng"};
		modelPhong=new DefaultTableModel(colPhong, 0);

		tblPhong=new JTable(modelPhong);
		tblPhong.setToolTipText("Ch???n th??ng tin ph??ng mu???n ?????t");
		tblPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblPhong.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblPhong.setShowHorizontalLines(true);
		tblPhong.setShowGrid(true);
		tblPhong.setBackground(Color.white);
		tblPhong.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblPhong.setSelectionBackground(new Color(164, 44, 167, 30));
		tblPhong.setSelectionForeground(new Color(114, 23, 153));
		tblPhong.setRowHeight(30);
		tblPhong.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JTableHeader tbHeaderPhong = tblPhong.getTableHeader();
		tbHeaderPhong.setToolTipText("Ch???n th??ng tin ph??ng mu???n ?????t");
		tbHeaderPhong.setBackground(new Color(164, 44, 167));
		tbHeaderPhong.setForeground(Color.white);
		tbHeaderPhong.setFont(new Font("SansSerif", Font.BOLD, 14));

		tblPhong.getColumnModel().getColumn(0).setPreferredWidth(100);//maphong
		tblPhong.getColumnModel().getColumn(1).setPreferredWidth(120);//loaiphong
		tblPhong.getColumnModel().getColumn(2).setPreferredWidth(100);//giaphong
		tblPhong.getColumnModel().getColumn(3).setPreferredWidth(150);//tinhtrangphong

		DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tblPhong.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

		scrollPaneChonPhong.setViewportView(tblPhong);

		//ngay thang nam lap DDP
		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		dNow = new Date(nam, thang, ngay);
		pNhapThongTin.setLayout(null);

		/**
		 * Th??m 1 DDP v??o danh s??ch b???ng DDP
		 * N??t th??m DDP
		 * JButton btnThemDDP
		 * Icon iconThemDDP
		 */
		btnThemDDP = new FixButton("Th??m");
		btnThemDDP.setForeground(Color.black);
		btnThemDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemDDP.setBackground(new Color(57, 210, 247));
		btnThemDDP.setBounds(10, 469, 310, 35);
		Icon iconThemDDP = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemDDP.setIcon(iconThemDDP);
		pNhapThongTin.add(btnThemDDP);

		/**
		 * S???a th??ng tin DDP
		 * N??t s???a DDP
		 * JButton btnSuaDDP
		 * Icon iconSuaDDP
		 */
		btnSuaDDP = new FixButton("S???a");
		btnSuaDDP.setForeground(Color.black);
		btnSuaDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaDDP.setBackground(new Color(133, 217, 191));
		btnSuaDDP.setBounds(10, 515, 310, 35);
		Icon iconSuaDDP = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaDDP.setIcon(iconSuaDDP);
		pNhapThongTin.add(btnSuaDDP);

		/**
		 * L??m m???i: x??a tr???ng c??c text, x??a t???t c??? n???i dung trong b???ng DDP, ?????t m???c ?????nh c??c combobox, b??? ch???n checkbox v?? c??c radiobutton
		 * N??t l??m m???i
		 * JButton btnLamMoiDDP
		 * Icon iconLamMoiDDP
		 */
		btnLamMoiDDP = new FixButton("L??m m???i");
		btnLamMoiDDP.setForeground(Color.white);
		btnLamMoiDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiDDP.setBackground(new Color(114, 23, 153));
		btnLamMoiDDP.setBounds(10, 561, 310, 35);
		Icon iconLamMoiDDP = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoiDDP.setIcon(iconLamMoiDDP);
		pNhapThongTin.add(btnLamMoiDDP);

		/**
		 * Khung s???p x???p ch???a c??c m???c s???p x???p theo t??ng d???n, gi???m d???n, theo m?? ph??ng, theo lo???i ph??ng
		 * JPanel pSapXep
		 */
		JPanel pSapXep = new JPanel();
		pSapXep.setToolTipText("S???p x???p th??ng tin ????P");
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "S???p x???p ????n ?????t ph??ng", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(178, 192, 237));
		pSapXep.setBounds(632, 48, 628, 50);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);

		/**
		 * Ch???n ki???u s???p x???p t??ng d???n ho???c gi???m d???n
		 * JComboBox cboSapXep
		 */
		cboSapXep = new JComboBox<Object>(new Object[]{"T??ng d???n", "Gi???m d???n"});
		cboSapXep.setToolTipText("S???p x???p th??ng tin ????P t??ng d???n ho???c gi???m d???n theo c??c ti??u ch??");
		cboSapXep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboSapXep.setBounds(34, 14, 123, 28);
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBackground(Color.WHITE);
		pSapXep.add(cboSapXep);

		/**
		 * Nh???n ch???n s???p x???p k?? t??? t??? tr??i sang ph???i theo m?? v?? lo???i ph??ng t??ng ho???c gi???m d???n
		 * S???p x???p lo???i ph??ng t??ng d???n: ph??ng th?????ng, trung, VIP v?? gi???m d???n ng?????c l???i
		 * JRadioButton rdoTheoMaPhong, rdoTheoLoaiPhong
		 */
		rdoTheoMaPhong = new JRadioButton("Theo m?? ph??ng");
		rdoTheoMaPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaPhong.setBounds(257, 16, 133, 27);
		rdoTheoMaPhong.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaPhong.setBackground(new Color(178, 192, 237));
		pSapXep.add(rdoTheoMaPhong);

		rdoTheoLoaiPhong = new JRadioButton("Theo lo???i ph??ng");
		rdoTheoLoaiPhong.setToolTipText("Lo???i ph??ng t??ng d???n: ph??ng th?????ng, trung, VIP v?? ng?????c l???i");
		rdoTheoLoaiPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoLoaiPhong.setBounds(468, 16, 147, 27);
		rdoTheoLoaiPhong.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiPhong.setBackground(new Color(178, 192, 237));
		pSapXep.add(rdoTheoLoaiPhong);

		/**
		 * Nh??m c??c radiobutton
		 * ButtonGroup bg
		 */
		bg=new ButtonGroup();
		bg.add(rdoTheoMaPhong); bg.add(rdoTheoLoaiPhong);


		/**
		 * B???ng ch???a c??c th??ng tin ????n ?????t ph??ng
		 * JScrollPane scrollPaneDDP
		 * String col[]: t??n c??c c???t
		 * JTable tblDDP: n???i dung c???a b???ng
		 * JTableHeader tbHeaderDDP: ?????nh d???ng khung c??c t??n c???t
		 */
		JScrollPane scrollPaneDDP = new JScrollPane();
		scrollPaneDDP.setToolTipText("Danh s??ch th??ng tin ????P");
		scrollPaneDDP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneDDP.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneDDP.setBackground(new Color(164, 44, 167));
		scrollPaneDDP.setBounds(632, 106, 628, 512);
		scrollPaneDDP.getHorizontalScrollBar();
		pMain.add(scrollPaneDDP);

		String colDDP[] = {"M?? DDP", "M?? ph??ng", "T??n KH", "S??T", "Ng??y ?????n", "Gi??? ?????n" , "T??n NV l???p", "Ng??y l???p", "Tr???ng th??i DDP"};
		modelDDP=new DefaultTableModel(colDDP, 0);

		tblDDP=new JTable(modelDDP);
		tblDDP.setToolTipText("Ch???n th??ng tin ????P ????? th???c hi???n c??c ch???c n??ng");
		tblDDP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblDDP.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblDDP.setShowHorizontalLines(true);
		tblDDP.setShowGrid(true);
		tblDDP.setBackground(Color.white);
		tblDDP.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblDDP.setSelectionBackground(new Color(164, 44, 167, 30));
		tblDDP.setSelectionForeground(new Color(114, 23, 153));
		tblDDP.setRowHeight(30);
		tblDDP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblDDP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choose1DDP();
			}
		});


		JTableHeader tbHeaderDDP = tblDDP.getTableHeader();
		tbHeaderDDP.setToolTipText("Danh s??ch th??ng tin ????P");
		tbHeaderDDP.setBackground(new Color(164, 44, 167));
		tbHeaderDDP.setForeground(Color.white);
		tbHeaderDDP.setFont(new Font("SansSerif", Font.BOLD, 14));

		tblDDP.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblDDP.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblDDP.getColumnModel().getColumn(2).setPreferredWidth(180);
		tblDDP.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblDDP.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblDDP.getColumnModel().getColumn(5).setPreferredWidth(80);
		tblDDP.getColumnModel().getColumn(6).setPreferredWidth(180);
		tblDDP.getColumnModel().getColumn(7).setPreferredWidth(90);
		tblDDP.getColumnModel().getColumn(8).setPreferredWidth(130);

		DefaultTableCellRenderer rightRenderer2=new DefaultTableCellRenderer();
		rightRenderer2.setHorizontalAlignment(JLabel.RIGHT);
		tblDDP.getColumnModel().getColumn(3).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(4).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(5).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(7).setCellRenderer(rightRenderer2);

		scrollPaneDDP.setViewportView(tblDDP);

		/**
		 * H??nh n???n c???a giao di???n
		 */
		lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);


		/**
		 * Load ng??y, gi???, ph??t hi???n t???i
		 */
		chooserNgayDen.setDate(dNgayHienTai);

		timeNow1 = new java.util.Date();
		nowHours = timeNow1.getHours();
		nowMinutes = timeNow1.getMinutes();
		cboGio.setSelectedItem(dftxtGio.format(nowHours));
		cboPhut.setSelectedItem(dftxtPhut.format(nowMinutes));

		/**
		 * C??c s??? ki???n c???a giao di???n qu???n l?? ????n ?????t ph??ng
		 */
		loadDanhSachDDP(ddp);

		txtTim.addFocusListener(this);

		loadDSPhongTrongVaDaDat(p);

		btnTim.addActionListener(this);
		btnThemDDP.addActionListener(this);
		btnSuaDDP.addActionListener(this);
		btnLamMoiDDP.addActionListener(this);

		cboSapXep.addActionListener(this);
		rdoTheoMaPhong.addActionListener(this);
		rdoTheoLoaiPhong.addActionListener(this);

		txtTim.addKeyListener(this);
		btnTim.addKeyListener(this);
	}


	/**
	 * X??a h???t d??? li???u trong b???ng danh s??ch th??ng tin ch???n ph??ng
	 * @param defaultTableModel tr??? v??? modelPhong
	 */
	private void removeDanhSachPhong(DefaultTableModel defaultTableModel) {
		while(tblPhong.getRowCount() > 0)
			modelPhong.removeRow(0);
	}

	/**
	 * @param defaultTableModel tr??? v??? modelDDP
	 */
	private void removeDanhSachDDP(DefaultTableModel defaultTableModel) {
		while(tblDDP.getRowCount() > 0)
			modelDDP.removeRow(0);
	}

	/**
	 * X??a tr???ng textfield v?? textarea, ?????t l???i m???c ?????nh c??c combobox v?? c??c button
	 */
	@SuppressWarnings("deprecation")
	private void resetAll() {
		txtTim.setText("T??m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng, t??m kh??ch h??ng theo s??t.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		
		cboLoaiKH.setEnabled(true);
		txtTenKH.setText("");
		txtTenKH.setEditable(true);
		txtSDT.setText("");
		txtSDT.setEditable(true);
		txtDiaChi.setText("");
		txtDiaChi.setEditable(true);

		chooserNgayDen.setDate(dNgayHienTai);

		timeNow2 = new java.util.Date();
		nowHours = timeNow2.getHours();
		nowMinutes = timeNow2.getMinutes();
		cboGio.setSelectedItem(dftxtGio.format(nowHours));
		cboPhut.setSelectedItem(dftxtPhut.format(nowMinutes));
		
		cboTrangThaiDDP.setSelectedItem("Ch??? x??c nh???n");

		bg.clearSelection();

		removeDanhSachPhong(modelPhong);
		loadDSPhongTrongVaDaDat(new Phong());

		removeDanhSachDDP(modelDDP);
		loadDanhSachDDP(ddp);
		
		txtTenKH.requestFocus();
	}

	/**
	 * Hi???n th??? danh s??ch th??ng tin ph??ng tr???ng v?? ???? ?????t g???m:
	 * m?? ph??ng, lo???i ph??ng, gi?? ph??ng, t??nh tr???ng ph??ng
	 * @param p
	 */
	private void loadDSPhongTrongVaDaDat(Phong p) {
		removeDanhSachPhong(modelPhong);
		ArrayList<Phong> lstP = daoPhong.getPhongTrongVaDaDat();
		for(Phong infoP : lstP) {
			LoaiPhong lp = daoLoaiPhong.getLoaiPhongTheoMa(infoP.getLoaiPhong().getMaLoaiPhong());
			String gia = dfGiaPhong.format(infoP.getGiaPhong());
			modelPhong.addRow(new Object[] {
					infoP.getMaPhong(), lp.getTenLoaiPhong(), gia, infoP.getTinhTrangPhong()
			});
		}
		changeColorRow(tblPhong);
	}

	/**
	 * Hi???n th??? danh s??ch th??ng tin ????n ?????t ph??ng, kh??ng hi???n th??? ????n ???? h???y, th??ng tin g???m:
	 * m?? ????P, m?? ph??ng, t??n KH, s??t, ng??y ?????n, gi??? ?????n, t??n NV l???p, ng??y l???p, tr???ng th??i ????P
	 * @param ddp
	 */
	private void loadDanhSachDDP(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDanhSachDDPChoXacNhanVaDaXacNhan();
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
		
	}
	
	/**
	 * Thay ?????i m??u c???a d??ng trong b???ng ch???n ph??ng c?? tr???ng th??i ph??ng "???? ?????t"
	 * @param tb
	 */
	public void changeColorRow(JTable tb) {
		tb.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 5206972278640725451L;

			@Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		        String status = (String)table.getModel().getValueAt(row, 3);
		        if ("???? ?????t".equals(status)) {
		            setBackground(Color.yellow);
		            setForeground(Color.red);
		        } else {
		            setBackground(table.getBackground());
		            setForeground(table.getForeground());
		        }       
		        return this;
		    }   
		});
	}

	/**
	 * Hi???n danh s??ch th??ng tin ????P theo s??t c???a KH
	 * @param list
	 */
	private void loadDDPTheoSdtKH(ArrayList<DonDatPhong> list) {
		removeDanhSachDDP(modelDDP);
		KhachHang khachHang = daoKhachHang.getKHTheoSDT(txtTim.getText().trim());
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDDPTheoMaKH(khachHang.getMaKhangHang());
		for(DonDatPhong infoDDP : lstDDP) {
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), khachHang.getTenKH(), khachHang.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 * Hi???n danh s??ch th??ng tin ????P theo h??? t??n c???a KH
	 * @param list
	 */
	private void loadDDPTheoTenKH(ArrayList<DonDatPhong> list) {
		removeDanhSachDDP(modelDDP);
		KhachHang khachHang = daoKhachHang.getKHTheoTen(txtTim.getText().trim());
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDDPTheoMaKH(khachHang.getMaKhangHang());
		for(DonDatPhong infoDDP : lstDDP) {
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), khachHang.getTenKH(), khachHang.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 * Ch???n 1 d??ng th??ng tin ????n ?????t ph??ng trong b???ng danh s??ch th??ng tin ????P
	 */
	@SuppressWarnings("deprecation")
	private void choose1DDP() {
		int selectedRow = tblDDP.getSelectedRow();
		if((selectedRow>=0 && tblPhong.getSelectedRow()==-1) || (selectedRow>=0 && tblPhong.getSelectedRow()!=-1)) {
			cboLoaiKH.setEnabled(false);
			txtTenKH.setEditable(false);
			txtTenKH.setForeground(Color.gray);
			txtSDT.setEditable(false);
			txtSDT.setForeground(Color.gray);
			txtDiaChi.setEditable(false);
			txtDiaChi.setForeground(Color.gray);
			
			String maDDP = tblDDP.getValueAt(selectedRow, 0).toString();
			String sdt = tblDDP.getValueAt(selectedRow, 3).toString();

			ArrayList<KhachHang> lstKH = daoKhachHang.getMaVaSDTKH(sdt);
			for(KhachHang khachHang : lstKH) {
				if(sdt.equals(khachHang.getSdt())) {
					LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khachHang.getLoaiKH().getMaLoaiKH());

					cboLoaiKH.setSelectedItem(loaiKH.getTenLoaiKH());
					txtTenKH.setText(khachHang.getTenKH());
					txtSDT.setText(khachHang.getSdt());
					txtDiaChi.setText(khachHang.getDiaChi());
				}
			}

			ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getAllDanhSachDDP();
			for(DonDatPhong ddp : lstDDP) {
				if(maDDP.equals(ddp.getMaDDP())) {
					chooserNgayDen.setDate(ddp.getNgayDen());
					cboGio.setSelectedItem(dftxtGio.format(ddp.getGioDen().getHours()));
					cboPhut.setSelectedItem(dftxtPhut.format(ddp.getGioDen().getMinutes()));
					cboTrangThaiDDP.setSelectedItem(ddp.getTrangThaiDDP()+"");
				}
			}
		}
	}

	/**
	 * S??? ki???n t??m ki???m th??ng tin ????P theo h??? t??n v?? s??t c???a KH
	 * S??? ki???n t??m ki???m th??ng tin KH theo s??t c???a KH
	 */
	@SuppressWarnings("deprecation")
	private void findKHVaDDP() {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		String regexTenKH= "^[ A-Za-za-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????]+$";
		String regexSDT  = "^(0[0-9]{9})$";

		ArrayList<KhachHang> lstKH1 = null;
		ArrayList<KhachHang> lstKH2 = null;

		if(regex.regexTimDDPTheoKH(txtTim)) {
			if(input.matches(regexSDT)) {
				lstKH1 = daoKhachHang.getMaVaSDTKH(input);
				for(KhachHang khachHang : lstKH1) {
					ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDDPTheoMaKH(khachHang.getMaKhangHang());
					if(input.equals(khachHang.getSdt())) {
						KhachHang kh = daoKhachHang.getKHTheoSDT(input);
						LoaiKH lkh = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());

						cboLoaiKH.setSelectedItem(lkh.getTenLoaiKH());
						txtTenKH.setText(khachHang.getTenKH());
						txtSDT.setText(khachHang.getSdt());
						txtDiaChi.setText(khachHang.getDiaChi());

						loadDDPTheoSdtKH(lstDDP);
					}
				}
				if(lstKH1.size()==0) {
					JOptionPane.showMessageDialog(this, "Kh??ng t??m th???y th??ng tin t??m ki???m ph?? h???p!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					txtTim.selectAll();
				}
			}
			else if(input.matches(regexTenKH)) {
				lstKH2 = daoKhachHang.getTenKH(input);
				for(KhachHang khachHang : lstKH2) {
					ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDDPTheoMaKH(khachHang.getMaKhangHang());
					if(daoKhachHang.getTenKH(input) != null) {
						KhachHang kh = daoKhachHang.getKHTheoTen(input);
						LoaiKH lkh = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());

						cboLoaiKH.setSelectedItem(lkh.getTenLoaiKH());
						txtTenKH.setText(khachHang.getTenKH());
						txtSDT.setText(khachHang.getSdt());
						txtDiaChi.setText(khachHang.getDiaChi());

						loadDDPTheoTenKH(lstDDP);
					}
				}
				if(lstKH2.size()==0) {
					JOptionPane.showMessageDialog(this, "Kh??ng t??m th???y th??ng tin t??m ki???m ph?? h???p!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
					txtTim.requestFocus();
					txtTim.selectAll();
				}
			}
		}
		
		chooserNgayDen.setDate(dNgayHienTai);

		timeNow2 = new java.util.Date();
		nowHours = timeNow2.getHours();
		nowMinutes = timeNow2.getMinutes();
		cboGio.setSelectedItem(dftxtGio.format(nowHours));
		cboPhut.setSelectedItem(dftxtPhut.format(nowMinutes));
		
		cboTrangThaiDDP.setSelectedItem("Ch??? x??c nh???n");
	}

	/**
	 * Ki???m tra c??c th??ng tin kh??ch h??ng tr?????c khi ?????t ????n
	 * T??m ki???m KH theo s??t, n???u th??ng tin KH ???? c?? trong d??? li???u th?? hi???n th??ng tin KH l??n v?? ?????t ????n
	 * N???u KH m???i, t??m kh??ng c?? th??ng tin th?? th??m 1 KH m???i v?? d??? li???u v?? ???????c ?????t ????n
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private void checkInfoKH() {
		String hoTen = txtTenKH.getText();
		String sdt = txtSDT.getText();
		String diaChi = txtDiaChi.getText();
		if(!hoTen.equals("") && !sdt.equals("") && !diaChi.equals("")) {
			String phatSinhMaDDP = daoPhatSinhMa.getMaDDP();
			String loaiKH = cboLoaiKH.getSelectedItem().toString();
			String trangThaiDDP = cboTrangThaiDDP.getSelectedItem().toString();

			java.util.Date date = chooserNgayDen.getDate();
			Date ngayDen = new Date(date.getYear(), date.getMonth(), date.getDate());

			Date ngayLap = dNgayHienTai;

			int gio = Integer.parseInt(cboGio.getSelectedItem().toString());
			int phut = Integer.parseInt(cboPhut.getSelectedItem().toString());
			Time gioDen = new Time(gio, phut, 0);

			int chonPhong = tblPhong.getSelectedRow(); //ch???n ph??ng l???y info t??? maPhong

			KhachHang kh = daoKhachHang.getKHTheoSDT(sdt);	//l???y maKH, tenKH t??? s??t
			//info new KH
			String phatSinhMaKH = daoPhatSinhMa.getMaKH();
			String maLoaiKH = daoLoaiKH.getMaLoaiKHTheoTen(loaiKH);

			String maNV = sHeaderMaNV; //l???y info NV t??? maNV
			NhanVien nv= daoNhanVien.getMaVaSdtNVChoDDP(maNV);

			if(chonPhong>=0) {
				String maPhongChon = tblPhong.getValueAt(chonPhong, 0).toString();
				Phong p = daoPhong.getPhongTheoMa(maPhongChon);
				String tinhTrangPhong = tblPhong.getValueAt(chonPhong, 3).toString();
				if(regex.regexTen(txtTenKH) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi)) {
					if(tinhTrangPhong.equals("???? ?????t")) 
						JOptionPane.showMessageDialog(null, "Ph??ng n??y ???? ???????c ?????t, vui l??ng ch???n ph??ng kh??c!", "Th??ng b??o", JOptionPane.OK_OPTION);
					else {
						if(daoKhachHang.checkSdtKH(sdt)) { //kq=true th?? l???y th??ng tin KH c??
							//them vao data
							DonDatPhong ddp=new DonDatPhong(phatSinhMaDDP, ngayLap, trangThaiDDP, ngayDen, gioDen, kh, nv, p);
							try {
								if(trangThaiDDP.equals("Ch??? x??c nh???n")) {
									daoDonDatPhong.themDDP(ddp);
									daoPhong.capnhatTrangThaiPhong(maPhongChon, "???? ?????t");
									resetAll();
									JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th??nh c??ng!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
								}
								if(trangThaiDDP.equals("???? x??c nh???n")) {
									daoDonDatPhong.themDDP(ddp);
									daoPhong.capnhatTrangThaiPhong(maPhongChon, "??ang ho???t ?????ng");
									resetAll();
									JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th??nh c??ng!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
								}
								if(trangThaiDDP.equals("H???y") || trangThaiDDP.equals("???? tr??? ph??ng"))
									JOptionPane.showMessageDialog(this, "Kh??ng ???????c th??m ????n ?????t ph??ng c?? tr???ng th??i ????n l?? h???y ho???c ???? tr??? ph??ng!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
							}catch (SQLException e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th???t b???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
							}

						}

						if(!daoKhachHang.checkSdtKH(sdt)) {	//kq=false th?? th??m KH m???i
							try {
								if(trangThaiDDP.equals("Ch??? x??c nh???n")) {
									KhachHang newKH = new KhachHang(phatSinhMaKH, new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen("Kh??ch h??ng th?????ng")), hoTen, sdt, diaChi);
									daoKhachHang.themDanhSachKH(newKH);
									
									DonDatPhong ddp=new DonDatPhong(phatSinhMaDDP, ngayLap, trangThaiDDP, ngayDen, gioDen, newKH, nv, p);
									daoDonDatPhong.themDDP(ddp);
									daoPhong.capnhatTrangThaiPhong(maPhongChon, "???? ?????t");
									
									resetAll();
									JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th??nh c??ng!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
								}
								if(trangThaiDDP.equals("???? x??c nh???n")) {
									KhachHang newKH = new KhachHang(phatSinhMaKH, new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen("Kh??ch h??ng th?????ng")), hoTen, sdt, diaChi);
									daoKhachHang.themDanhSachKH(newKH);
									
									DonDatPhong ddp=new DonDatPhong(phatSinhMaDDP, ngayLap, trangThaiDDP, ngayDen, gioDen, newKH, nv, p);
									daoDonDatPhong.themDDP(ddp);
									daoPhong.capnhatTrangThaiPhong(maPhongChon, "??ang ho???t ?????ng");
									
									resetAll();
									JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th??nh c??ng!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
								}
								if(trangThaiDDP.equals("H???y") || trangThaiDDP.equals("???? tr??? ph??ng"))
									JOptionPane.showMessageDialog(this, "Kh??ng ???????c th??m ????n ?????t ph??ng c?? tr???ng th??i ????n l?? h???y ho???c ???? tr??? ph??ng!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
							}catch (SQLException e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(this, "Th??m ????n ?????t ph??ng th???t b???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
							}
						}
						
						removeDanhSachPhong(modelPhong);
						loadDSPhongTrongVaDaDat(p);
					}
				}
			}else
				JOptionPane.showMessageDialog(null, "Vui l??ng ch???n ph??ng mu???n ?????t!", "Th??ng b??o", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin ?????y ?????!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Th??m 1 ????P m???i v??o d??? li???u v?? hi???n l??n b???ng danh s??ch
	 */
	@SuppressWarnings("deprecation")
	private void addDDP() {
		if(chooserNgayDen.getDate()!=null) {
			java.util.Date date = chooserNgayDen.getDate();
			Date ngayDen = new Date(date.getYear(), date.getMonth(), date.getDate());

			int gio = Integer.parseInt(cboGio.getSelectedItem().toString());
			int phut = Integer.parseInt(cboPhut.getSelectedItem().toString());

			java.util.Date dateNow = new java.util.Date();
			int hourNow = dateNow.getHours();
			int minNow = dateNow.getMinutes();

			if(ngayDen.equals(dNgayHienTai)) {
				if(gio>hourNow)
					checkInfoKH();
				if(gio==hourNow && phut>=minNow) 
					checkInfoKH();
				if(gio<hourNow || (gio==hourNow && phut<minNow))
					JOptionPane.showMessageDialog(this, "Gi??? ?????n ph???i ???????c ?????t sau ho???c ngay gi??? hi???n t???i! \nGi??? hi???n t???i l??: "+dfHienGio.format(dateNow), "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(this, "Ng??y ?????n ph???i ?????t trong ng??y h??m nay! \nNg??y h??m nay l??: " +dfNgay.format(dNgayHienTai), "Th??ng b??o", JOptionPane.WARNING_MESSAGE);

			removeDanhSachDDP(modelDDP);
			loadDanhSachDDP(ddp);
		}else 
			JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin ?????y ?????!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * S???a, c???p nh???t th??ng tin ????P, trong giao di???n qu???n l?? ????P kh??ng ???????c s???a th??ng tin KH
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private void updateDDP() throws SQLException { //th??ng tin KH trong ddp ko ??c s???a
		int row = tblDDP.getSelectedRow();
		if(row>=0) {
			int update = JOptionPane.showConfirmDialog(this, "B???n mu???n s???a th??ng tin ????n ?????t ph??ng n??y?", "Th??ng b??o", JOptionPane.YES_NO_OPTION);
			if(update == JOptionPane.YES_OPTION) {
				String maDDP = tblDDP.getValueAt(row, 0).toString();
				String maPhong = tblDDP.getValueAt(row, 1).toString();

				String sdt = tblDDP.getValueAt(row, 3).toString();
				KhachHang kh = daoKhachHang.getKHTheoSDT(sdt);

				String maNV = sHeaderMaNV; //l???y info NV t??? maNV
				NhanVien nv= daoNhanVien.getMaVaSdtNVChoDDP(maNV);

				java.util.Date date = chooserNgayDen.getDate();
				Date ngayDen = new Date(date.getYear(), date.getMonth(), date.getDate());
				Date ngayLap = dNgayHienTai;

				int gio = Integer.parseInt(cboGio.getSelectedItem().toString());
				int phut = Integer.parseInt(cboPhut.getSelectedItem().toString());
				Time gioDen = new Time(gio, phut, 0);

				java.util.Date dateNow = new java.util.Date();
				int hourNow = dateNow.getHours();
				int minNow = dateNow.getMinutes();

				String trangThaiDDP = cboTrangThaiDDP.getSelectedItem().toString();

				ArrayList<KhachHang> lstKH = daoKhachHang.getMaVaSDTKH(sdt);
				for(KhachHang khachHang : lstKH) {
					if(sdt.equals(khachHang.getSdt())) {
						LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(khachHang.getLoaiKH().getMaLoaiKH());

						cboLoaiKH.setSelectedItem(loaiKH.getTenLoaiKH());
						txtTenKH.setText(khachHang.getTenKH());
						txtSDT.setText(khachHang.getSdt());
						txtDiaChi.setText(khachHang.getDiaChi());
					}
				}
				
				DonDatPhong ddp=new DonDatPhong();
				ddp.setNgayLap(ngayLap);
				ddp.setGioDen(gioDen);
				ddp.setNgayDen(ngayDen);
				ddp.setTrangThaiDDP(trangThaiDDP);

				daoDonDatPhong.capNhatDDP(ddp, maDDP);
				removeDanhSachPhong(modelDDP);
				if(trangThaiDDP.equals("Ch??? x??c nh???n"))
					daoPhong.capnhatTrangThaiPhong(maPhong, "???? ?????t");
				if(trangThaiDDP.equals("???? x??c nh???n"))
					daoPhong.capnhatTrangThaiPhong(maPhong, "??ang ho???t ?????ng");
				if(trangThaiDDP.equals("H???y") || trangThaiDDP.equals("???? tr??? ph??ng"))
					daoPhong.capnhatTrangThaiPhong(maPhong, "Tr???ng");
				loadDSPhongTrongVaDaDat(new Phong());

				resetAll();
				removeDanhSachDDP(modelDDP);
				modelDDP.setRowCount(0);
				modelDDP.addRow(new Object[] {
						maDDP, maPhong, kh.getTenKH(), kh.getSdt(),
						dfNgay.format(ngayDen), dfHienGio.format(gioDen), nv.getTenNhanVien(), dfNgay.format(ngayLap), trangThaiDDP
				});
				JOptionPane.showMessageDialog(this, "S???a th??ng tin ????n ?????t ph??ng th??nh c??ng!", "Th??ng b??o", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
			JOptionPane.showMessageDialog(this, "Vui l??ng ch???n th??ng tin ????n ?????t ph??ng c???n s???a!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * S???p x???p theo m?? ????P t??ng d???n
	 * @param ddp
	 */
	private void sortMaDDPTangDan(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.sortMaDDP("ASC");
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 * S???p x???p theo m?? ????P gi???m d???n
	 * @param ddp
	 */
	private void sortMaDDPGiamDan(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.sortMaDDP("DESC");
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 * S???p x???p lo???i ph??ng t??ng d???n: ph??ng th?????ng, trung, VIP
	 * @param ddp
	 */
	private void sortDDPTheoLoaiPhongTangDan(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.sortDDPTheoLoaiPhong("ASC");
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 * S???p x???p lo???i ph??ng gi???m d???n: ph??ng VIP, trung, th?????ng
	 * @param ddp
	 */
	private void sortDDPTheoLoaiPhongGiamDan(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.sortDDPTheoLoaiPhong("DESC");
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
	}

	/**
	 *Code s??? ki???n
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if(o.equals(btnTim)) {
			if(txtTim.getText().equals("") || txtTim.getText().equals("T??m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng, t??m kh??ch h??ng theo s??t.")) {
				removeDanhSachDDP(modelDDP);
				JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin t??m ki???m!", "Th??ng b??o", JOptionPane.WARNING_MESSAGE);
				txtTim.requestFocus();
			}else 
				findKHVaDDP();
		}

		//th??m ddp
		if(o.equals(btnThemDDP)) {
			addDDP();
		}

		//s???a ddp
		if(o.equals(btnSuaDDP)) {
			try {
				updateDDP();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		//l??m m???i
		if(o.equals(btnLamMoiDDP)) {
			resetAll();
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
			if(o.equals(rdoTheoMaPhong))
				sortMaDDPTangDan(ddp);
			if(o.equals(rdoTheoLoaiPhong))
				sortDDPTheoLoaiPhongTangDan(ddp);
		}

		//sapxep gi???m
		if(cboSapXep.getSelectedItem()=="Gi???m d???n") {
			if(o.equals(rdoTheoMaPhong))
				sortMaDDPGiamDan(ddp);
			if(o.equals(rdoTheoLoaiPhong))
				sortDDPTheoLoaiPhongGiamDan(ddp);
		}
	}

	/**
	 *S??? ki???n placeholder c???a txtTim
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if(txtTim.getText().equals("T??m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng, t??m kh??ch h??ng theo s??t.")) {
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
			txtTim.setText("T??m ????n ?????t ph??ng theo h??? t??n v?? s??t kh??ch h??ng, t??m kh??ch h??ng theo s??t.");
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
			btnLamMoiDDP.doClick();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
