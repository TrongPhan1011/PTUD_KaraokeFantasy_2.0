package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.*;
import entity.*;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FrmDonDatPhong extends JPanel implements ActionListener, MouseListener, ItemListener, FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV, sHeaderTenNV;
	private Panel pMain;
	private Date dNgayHienTai;
	private JLabel lblTim, lblTenKH, lblLoaiKH, lblNgayDen, lblSDT, lblGioDen, lblTinhTrangDDP, lblDiaChi, lblChonPhong, lblBackGround;
	private JTextField txtTim, txtTenKH, txtSDT, txtGioDen, txtDiaChi;
	private JComboBox<Object> cboLoaiKH, cboTrangThaiDDP, cboSapXep;
	private JComboBox<String> cboGio, cboPhut;
	private JTable tblPhong, tblDDP;
	private DefaultTableModel modelPhong, modelDDP;
	private JButton btnTim, btnThemDDP, btnSuaDDP, btnHuyDDP, btnLamMoiDDP;
	private JCheckBox chkTatCa;
	private JRadioButton rdoTheoMaPhong, rdoTheoLoaiPhong;
	private ButtonGroup bg;
	private SimpleDateFormat dfNgay=new SimpleDateFormat("dd/MM/yyyy"), dfHienGio=new SimpleDateFormat("HH:mm a");
	//	private DateTimeFormatter dftxtGioPhut= DateTimeFormatter.ofPattern("HH:mm");
	private DecimalFormat dfGiaPhong=new DecimalFormat("###,###"), dftxtGio=new DecimalFormat("HH:mm");
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
	


	public Panel getFrmDDP() {
		return this.pMain;
	}
	public FrmDonDatPhong(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

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
		daoPhong=new DAOPhong();
		daoLoaiPhong=new DAOLoaiPhong();
		daoDonDatPhong=new DAODonDatPhong();
		daoKhachHang=new DAOKhachHang();
		daoLoaiKH=new DAOLoaiKH();
		daoNhanVien=new DAONhanVien();
		daoPhatSinhMa=new DAOPhatSinhMa();
		regex=new Regex();

		//Entity
		Phong p=new Phong();
		DonDatPhong ddp=new DonDatPhong();
		KhachHang kh=new KhachHang();

		//frameDDP
		setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1273, 629);
		add(pMain);
		pMain.setLayout(null);
		
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 333, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);
		
				//lblNgayDen
		lblNgayDen = new JLabel("Ngày đến:");
		lblNgayDen.setBounds(10, 267, 74, 19);
		lblNgayDen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblNgayDen);
		
		//lblSDT
		lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(10, 160, 46, 28);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblSDT);
		
		lblNhapThongTin = new JLabel("Nhập thông tin đơn đặt phòng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 292, 29);
		pNhapThongTin.add(lblNhapThongTin);
		
		//txtTenKH
		txtTenKH = new JTextField();
		txtTenKH.setBounds(145, 110, 175, 39);
		txtTenKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtTenKH.setColumns(10);
		txtTenKH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		pNhapThongTin.add(txtTenKH);
		
		
		//test data nhanh
		//		txtTenKH.setText("Đoàn Phạm Bích Hợp");
		//		txtSDT.setText("0708985897");
		//		txtDiaChi.setText("1358 Quang Trung, P.14, Q.Gò Vấp");
		txtTenKH.setText("Đinh Quang Tuấn");
		
		//lblTenKH
		lblTenKH = new JLabel("Tên khách hàng:");
		lblTenKH.setBounds(10, 116, 133, 19);
		lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblTenKH);
		
		//lblLoaiKH
		lblLoaiKH = new JLabel("Loại khách hàng:");
		lblLoaiKH.setBounds(10, 69, 133, 19);
		lblLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblLoaiKH);
		
		//cbbLoaiKH
		cboLoaiKH = new JComboBox<Object>(new Object[] {"Thường", "Thành viên", "VIP"});
		cboLoaiKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboLoaiKH.setBounds(145, 64, 175, 35);
		cboLoaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboLoaiKH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboLoaiKH.setBackground(Color.white);
		pNhapThongTin.add(cboLoaiKH);
		
		//chooserNgayDen
		chooserNgayDen = new JDateChooser();
		chooserNgayDen.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chooserNgayDen.setBounds(144, 263, 176, 38);
		chooserNgayDen.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgayDen.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgayDen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgayDen.setDateFormatString("dd/MM/yyyy");
		chooserNgayDen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		chooserNgayDen.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgayDen);
		
		//txtSDT
		txtSDT = new JTextField();
		txtSDT.setBounds(144, 160, 176, 41);
		txtSDT.setColumns(10);
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtSDT.setText("0944302210");
		pNhapThongTin.add(txtSDT);
		
		//				txtGioDen = new JTextField("00:00");
			//				txtGioDen.setColumns(10);
																						//				txtGioDen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		//				txtGioDen.setForeground(Colors.LightGray);
		//				txtGioDen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		//				txtGioDen.setBounds(762, 59, 74, 28);
		//				txtGioDen.addFocusListener(new FocusAdapter() {
		//					@Override
		//					public void focusGained(FocusEvent e) {
		//						if(txtGioDen.getText().equals("00:00")) {
		//							txtGioDen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		//							txtGioDen.setForeground(Color.BLACK);
		//							txtGioDen.setText("");
		//						}
		//					}
		//					@Override
		//					public void focusLost(FocusEvent e) {
		//						if(txtGioDen.getText().equals("")) {
		//							txtGioDen.setFont(new Font("SansSerif", Font.PLAIN, 15));
		//							txtGioDen.setForeground(Colors.LightGray);
		//							txtGioDen.setText("00:00");
		//						}
		//					}
		//				});
		//				try {
		//					dfGio.parse(txtGioDen.getText());
		//				} catch (ParseException e1) {
		//					e1.printStackTrace();
		//				}
		//				pMain.add(txtGioDen);
		
		//lblDiaChi
		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(10, 216, 61, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);
		
		//txtDiaChi
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(145, 212, 175, 40);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtDiaChi.setText("118 Hoàng Văn Thụ, P9, Q.Phú Nhuận");
		pNhapThongTin.add(txtDiaChi);
		
		//lblGioDen
		lblGioDen = new JLabel("Giờ đến:");
		lblGioDen.setBounds(10, 316, 74, 19);
		lblGioDen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioDen);
		
		//txtGioDen
		cboGio=new JComboBox<String>();
		cboGio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGio.setBounds(145, 312, 72, 37);
		cboGio.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGio.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboGio.setBackground(Color.white);
		pNhapThongTin.add(cboGio);
		////
		cboPhut = new JComboBox<String>();
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
		lblHaiCham.setBounds(220, 309, 21, 39);
		lblHaiCham.setFont(new Font("SansSerif", Font.PLAIN, 25));
		pNhapThongTin.add(lblHaiCham);
		
		//lblTinhTrangDDP
		lblTinhTrangDDP = new JLabel("Trạng thái ĐĐP:");
		lblTinhTrangDDP.setBounds(10, 364, 133, 19);
		lblTinhTrangDDP.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblTinhTrangDDP);
		
		//cbbTinhTrangDDP
		cboTrangThaiDDP = new JComboBox<Object>(new Object[]{"Đã xác nhận", "Chờ xác nhận", "Hủy", "Đã trả phòng"});
		cboTrangThaiDDP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboTrangThaiDDP.setBounds(145, 360, 175, 39);
		cboTrangThaiDDP.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboTrangThaiDDP.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		cboTrangThaiDDP.setBackground(Color.white);
		pNhapThongTin.add(cboTrangThaiDDP);
		
		//lblTim
		lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(550, 11, 80, 35);
		pMain.add(lblTim);

		//txtTim
		txtTim = new JTextField();
		txtTim.setText("Tìm đơn đặt phòng, khách hàng theo tên, sđt khách hàng.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(632, 12, 514, 33);
		pMain.add(txtTim);

		//btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1156, 11, 104, 33);
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, Color.white);
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);

		//lblChonPhong
		lblChonPhong = new JLabel("Chọn phòng:");
		lblChonPhong.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblChonPhong.setBounds(431, 70, 136, 29);
		pMain.add(lblChonPhong);

		//bangthongtinPhong
		JScrollPane scrollPaneChonPhong = new JScrollPane(tblPhong, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneChonPhong.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneChonPhong.setBackground(new Color(164, 44, 167));
		scrollPaneChonPhong.setBounds(353, 106, 273, 512);
		scrollPaneChonPhong.getHorizontalScrollBar();
		pMain.add(scrollPaneChonPhong);

		String colPhong[] = {"Mã phòng", "Loại phòng", "Giá phòng", "Tình trạng phòng"};
		modelPhong=new DefaultTableModel(colPhong, 0);

		tblPhong=new JTable(modelPhong);
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
		
		//btnthem,sua,xoa,lammoiDDP
		btnThemDDP = new FixButton("Thêm");
		btnThemDDP.setForeground(Color.black);
		btnThemDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemDDP.setBackground(new Color(57, 210, 247));
		btnThemDDP.setBounds(10, 469, 310, 35);
		Icon iconThemDDP = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemDDP.setIcon(iconThemDDP);
		pNhapThongTin.add(btnThemDDP);

		btnSuaDDP = new FixButton("Sửa");
		btnSuaDDP.setForeground(Color.black);
		btnSuaDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaDDP.setBackground(new Color(133, 217, 191));
		btnSuaDDP.setBounds(10, 515, 310, 35);
		Icon iconSuaDDP = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaDDP.setIcon(iconSuaDDP);
		pNhapThongTin.add(btnSuaDDP);

		btnLamMoiDDP = new FixButton("Làm mới");
		btnLamMoiDDP.setForeground(Color.white);
		btnLamMoiDDP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiDDP.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiDDP.setBackground(new Color(114, 23, 153));
		btnLamMoiDDP.setBounds(10, 561, 310, 35);
		Icon iconLamMoiDDP = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoiDDP.setIcon(iconLamMoiDDP);
		pNhapThongTin.add(btnLamMoiDDP);

		//sapxep
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp đơn đặt phòng", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(178, 192, 237));
		pSapXep.setBounds(632, 48, 628, 50);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);

		cboSapXep = new JComboBox<Object>(new Object[]{"Tăng dần", "Giảm dần"});
		cboSapXep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboSapXep.setBounds(34, 14, 123, 28);
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBackground(Color.WHITE);
		pSapXep.add(cboSapXep);

		chkTatCa = new JCheckBox("Tất cả");
		chkTatCa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chkTatCa.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkTatCa.setBackground(new Color(178, 192, 237));
		chkTatCa.setBounds(193, 16, 95, 27);
		chkTatCa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1)
					loadDanhSachDDP(ddp);
				else
					removeDanhSachDDP(modelDDP);
			}
		});
		pSapXep.add(chkTatCa);

		rdoTheoMaPhong = new JRadioButton("Theo mã phòng");
		rdoTheoMaPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaPhong.setBounds(305, 16, 133, 27);
		rdoTheoMaPhong.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaPhong.setBackground(new Color(178, 192, 237));
		pSapXep.add(rdoTheoMaPhong);

		rdoTheoLoaiPhong = new JRadioButton("Theo loại phòng");
		rdoTheoLoaiPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoLoaiPhong.setBounds(468, 16, 147, 27);
		rdoTheoLoaiPhong.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiPhong.setBackground(new Color(178, 192, 237));
		pSapXep.add(rdoTheoLoaiPhong);

		bg=new ButtonGroup();
		bg.add(rdoTheoMaPhong); bg.add(rdoTheoLoaiPhong);

		//bangthongtinDDP
		JScrollPane scrollPaneDDP = new JScrollPane();
		scrollPaneDDP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneDDP.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneDDP.setBackground(new Color(164, 44, 167));
		scrollPaneDDP.setBounds(632, 106, 628, 512);
		scrollPaneDDP.getHorizontalScrollBar();
		pMain.add(scrollPaneDDP);

		String colDDP[] = {"Mã DDP", "Mã phòng", "Tên KH", "SĐT", "Ngày đến", "Giờ đến" , "Tên NV lập", "Ngày lập", "Trạng thái DDP"};
		modelDDP=new DefaultTableModel(colDDP, 0);

		tblDDP=new JTable(modelDDP);
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

		
		JTableHeader tbHeaderDDP = tblDDP.getTableHeader();
		tbHeaderDDP.setBackground(new Color(164, 44, 167));
		tbHeaderDDP.setForeground(Color.white);
		tbHeaderDDP.setFont(new Font("SansSerif", Font.BOLD, 14));

		tblDDP.getColumnModel().getColumn(0).setPreferredWidth(80);//maDDP
		tblDDP.getColumnModel().getColumn(1).setPreferredWidth(100);//maPhong
		tblDDP.getColumnModel().getColumn(2).setPreferredWidth(180);//tenKH
		tblDDP.getColumnModel().getColumn(3).setPreferredWidth(100);//sdt
		tblDDP.getColumnModel().getColumn(4).setPreferredWidth(100);//ngayDen
		tblDDP.getColumnModel().getColumn(5).setPreferredWidth(80);//gioDen
		tblDDP.getColumnModel().getColumn(6).setPreferredWidth(180);//tenNVLap
		tblDDP.getColumnModel().getColumn(7).setPreferredWidth(90);//ngayLap
		tblDDP.getColumnModel().getColumn(8).setPreferredWidth(130);//trangThaiDDP

		DefaultTableCellRenderer rightRenderer2=new DefaultTableCellRenderer();
		rightRenderer2.setHorizontalAlignment(JLabel.RIGHT);
		tblDDP.getColumnModel().getColumn(3).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(4).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(5).setCellRenderer(rightRenderer2);
		tblDDP.getColumnModel().getColumn(7).setCellRenderer(rightRenderer2);

		//		tableDDP.setOpaque(false);
		scrollPaneDDP.setViewportView(tblDDP);
		
		//background
		lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		
		
		txtTim.addFocusListener(this);
		
		cboGio.addItemListener(this);
		
		loadDSPhongTrongVaDaDat(p);

		btnTim.addActionListener(this);
		btnThemDDP.addActionListener(this);
		btnSuaDDP.addActionListener(this);
		btnLamMoiDDP.addActionListener(this);

		cboSapXep.addActionListener(this);
		chkTatCa.addActionListener(this);
		rdoTheoMaPhong.addActionListener(this);
		rdoTheoLoaiPhong.addActionListener(this);

		tblPhong.addMouseListener(this);
	}

	//xoa het data trong tblPhong
	private void removeDanhSachPhong(DefaultTableModel defaultTableModel) {
		while(tblPhong.getRowCount() > 0)
			modelPhong.removeRow(0);
	}

	//xoa het data trong tblDDP
	private void removeDanhSachDDP(DefaultTableModel defaultTableModel) {
		while(tblDDP.getRowCount() > 0)
			modelDDP.removeRow(0);
	}

	//xoa trang txt
	private void xoaTrang() {
		txtTim.setText("Tìm đơn đặt phòng, khách hàng theo tên, sđt khách hàng.");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);

		txtTenKH.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		
		chooserNgayDen.setDate(dNow);
		cboGio.setSelectedIndex(0);
		cboPhut.setSelectedIndex(0);
		
		chkTatCa.setSelected(false);
		rdoTheoMaPhong.setSelected(false);
		rdoTheoLoaiPhong.setSelected(false);
	}

	//load phong trong va da dat
	private void loadDSPhongTrongVaDaDat(Phong p) {
		removeDanhSachPhong(modelPhong);
		ArrayList<Phong> lstP = daoPhong.getPhongTrongVaDaDat();
		for(Phong infoP : lstP) {
			LoaiPhong lp = daoLoaiPhong.getLoaiPhongTheoMa(infoP.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {
					infoP.getMaPhong(), lp.getTenLoaiPhong(), dfGiaPhong.format(infoP.getGiaPhong()), infoP.getTinhTrangPhong()
			});
		}
	}

	//load ds ddp
	private void loadDanhSachDDP(DonDatPhong ddp) {
		removeDanhSachDDP(modelDDP);
		ArrayList<DonDatPhong> lstDDP = daoDonDatPhong.getDanhSachDDPKhongHuy();
		for(DonDatPhong infoDDP : lstDDP) {
			KhachHang kh = daoKhachHang.getKHTheoMa(infoDDP.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getMaVaSdtNVChoDDP(infoDDP.getNhanVien().getMaNhanVien());
			modelDDP.addRow(new Object[] {
					infoDDP.getMaDDP(), infoDDP.getPhong().getMaPhong(), kh.getTenKH(), kh.getSdt(),
					dfNgay.format(infoDDP.getNgayDen()), dfHienGio.format(infoDDP.getGioDen()), nv.getTenNhanVien(), dfNgay.format(infoDDP.getNgayLap()), infoDDP.getTrangThaiDDP()
			});
		}
		//		for(int i=0; i<=tblPhong.getRowCount(); i++) {
		//			String trangThaiP = tblPhong.getValueAt(i, 4).toString();
		//			if(trangThaiP.equals("Đã đặt"))
		//				tblPhong.setBackground(Color.yellow);
		//		}
	}

	private void findKH() {
		String input = txtTim.getText().trim();
//		KhachHang kh = daoKhachHang.getKHTheoSDT(sdt);
		KhachHang kh = daoKhachHang.getKHTheoSDT(input);
		
	}
	
	private void addDDP() {
		//		try {
		String phatSinhMaDDP = daoPhatSinhMa.getMaDDP();
		String hoTen = txtTenKH.getText();
		String loaiKH = cboLoaiKH.getSelectedItem().toString();
		String sdt = txtSDT.getText();
		String diaChi = txtDiaChi.getText();
		String trangThaiDDP = cboTrangThaiDDP.getSelectedItem().toString();
		
		java.util.Date date = chooserNgayDen.getDate();
		Date ngayDen = new Date(date.getYear(), date.getMonth(), date.getDate());

		Date ngayLap = dNgayHienTai;
		
		int gio = Integer.parseInt(cboGio.getSelectedItem().toString());
		int phut = Integer.parseInt(cboPhut.getSelectedItem().toString());
		Time gioDen = new Time(gio, phut, 0);

		int chonPhong = tblPhong.getSelectedRow(); //chọn phòng lấy info từ maPhong
		String maPhongChon = tblPhong.getValueAt(chonPhong, 0).toString();
		Phong p = daoPhong.getPhongTheoMa(maPhongChon);
		String tinhTrangPhong = tblPhong.getValueAt(chonPhong, 3).toString();
		
		
		KhachHang kh = daoKhachHang.getKHTheoSDT(sdt);	//lấy maKH, tenKH từ sđt
		//info new KH
		String phatSinhMaKH = daoPhatSinhMa.getMaKH();
//		LoaiKH lKH = daoKhachHang.getMaLoaiKHFromTen(loaiKH);
//		String maLoaiKH = lKH.getMaLoaiKH();
		String maLoaiKH = daoLoaiKH.getMaLoaiKHTheoTen(loaiKH);
//		String loaiKHThuong = cboLoaiKH.setSelectedIndex(1);
		
		String maNV = sHeaderMaNV; //lấy info NV từ maNV
		NhanVien nv= daoNhanVien.getMaVaSdtNVChoDDP(maNV);
		
		
		if(regex.regexTen(txtTenKH) && regex.regexSDT(txtSDT)) {
				if(cboGio.getSelectedItem().toString()=="" || cboPhut.getSelectedItem().toString()=="")
					JOptionPane.showMessageDialog(null, "Vui lòng chọn giờ đến!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//				if(tinhTrangPhong.equals("Đã đặt"))
//					JOptionPane.showMessageDialog(null, "Phòng này đã đặt, vui lòng chọn phòng trống khác!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				else {
					//so khớp sdt nhập trong ddp
					if(!daoKhachHang.checkSdtKH(sdt)) { //kq=false thì load từ sql
						//them vao data
						DonDatPhong ddp=new DonDatPhong();
						ddp.setMaDDP(phatSinhMaDDP);
						ddp.setNgayLap(ngayLap);
						ddp.setGioDen(gioDen);
						ddp.setNgayDen(ngayDen);
						ddp.setTrangThaiDDP(trangThaiDDP);
						ddp.setKhachHang(kh);
						ddp.setNhanVien(nv);
						ddp.setPhong(p);
						try {
							daoDonDatPhong.themDDP(ddp);
							daoPhong.capnhatTrangThaiPhong(maPhongChon, "Đã đặt");
						}catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(this, "Thêm đơn đặt phòng thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
						}

						xoaTrang();
						removeDanhSachPhong(modelPhong);
						loadDSPhongTrongVaDaDat(p);
						modelDDP.addRow(new Object[] {
								phatSinhMaDDP, maPhongChon, kh.getTenKH(), sdt, 
								dfNgay.format(ngayDen), dfHienGio.format(gioDen), nv.getTenNhanVien(), dfNgay.format(ngayLap), trangThaiDDP
						});
						JOptionPane.showMessageDialog(this, "Thêm đơn đặt phòng thành công!", "Thông báo", JOptionPane.OK_OPTION);
					}
					if(daoKhachHang.checkSdtKH(sdt)) {	//ke=true thì tạo mới KH
						KhachHang newKH = new KhachHang(phatSinhMaKH, new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen("Khách hàng thường")), hoTen, sdt, diaChi);
						daoKhachHang.themDanhSachKH(newKH);
						
						DonDatPhong ddp=new DonDatPhong();
						ddp.setMaDDP(phatSinhMaDDP);
						ddp.setNgayLap(ngayLap);
						ddp.setGioDen(gioDen);
						ddp.setNgayDen(ngayDen);
						ddp.setTrangThaiDDP(trangThaiDDP);
						ddp.setKhachHang(newKH);
						ddp.setNhanVien(nv);
						ddp.setPhong(p);
						try {
							daoDonDatPhong.themDDP(ddp);
							daoPhong.capnhatTrangThaiPhong(maPhongChon, "Đã đặt");
						}catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(this, "Thêm đơn đặt phòng thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
						}

						xoaTrang();
						removeDanhSachPhong(modelPhong);
						loadDSPhongTrongVaDaDat(p);
						modelDDP.addRow(new Object[] {
								phatSinhMaDDP, maPhongChon, hoTen, sdt, 
								dfNgay.format(ngayDen), dfHienGio.format(gioDen), nv.getTenNhanVien(), dfNgay.format(ngayLap), trangThaiDDP
						});
						JOptionPane.showMessageDialog(this, "Thêm đơn đặt phòng thành công!", "Thông báo", JOptionPane.OK_OPTION);
					}
				}
			}

		//		}catch (Exception e) {
		//			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
		//		}
	}
	
	private void updateDDP() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o.equals(btnTim)) {
			findKH();
		}

		//thêm ddp
		if(o.equals(btnThemDDP)) {
			addDDP();
		}
		
		//sửa ddp
		if(o.equals(btnSuaDDP)) {
			updateDDP();
		}
		
		//reset
		if(o.equals(btnLamMoiDDP)) {
			xoaTrang();
			removeDanhSachDDP(modelDDP);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getItem();
		
	}
	
	
	@Override
	public void focusGained(FocusEvent e) {
		if(txtTim.getText().equals("Tìm đơn đặt phòng, khách hàng theo tên, sđt khách hàng.")) {
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
			txtTim.setText("Tìm đơn đặt phòng, khách hàng theo tên, sđt khách hàng.");
		}
	}
}
