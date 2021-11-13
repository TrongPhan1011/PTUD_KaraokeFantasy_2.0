package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOCTDDP;
import dao.DAOCTHD;
import dao.DAODonDatPhong;
import dao.DAOHoaDon;
import dao.DAOKhachHang;
import dao.DAOLoaiMH;
import dao.DAOLoaiPhong;
import dao.DAOMatHang;
import dao.DAONhanVien;
import dao.DAOPhong;
import dao.Regex;
import entity.CTDDP;
import entity.CTHD;
import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiMatHang;
import entity.MatHang;
import entity.NhanVien;
import entity.Phong;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmDanhSachHoaDon extends JFrame implements ActionListener,MouseListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnQuayLai;
	private JFrame frm;
	private JTable tblHoaDon;
	private JTable tblCTHD;
	private JButton btnTim;
	private JButton btnLamMoi;
	private JDateChooser chooserNgayBatDau;
	private JButton btnXem;
	private JDateChooser chooserNgayKetThuc;
	private DefaultTableModel modelHoaDon;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private DAOHoaDon daoHD;
	private DAOLoaiMH daoLoaiMH;
	private DAOMatHang daoMatHang;
	private DAOCTDDP daoCTDDP;
	private DAOLoaiPhong daoLoaiPhong;
	private DAODonDatPhong daoDDP;
	private DAOPhong daoPhong;
	private DAOKhachHang daoKhachHang;
	private DAONhanVien daoNhanVien;
	private SimpleDateFormat sf;

	private JLabel lblTrangThai;
	private JLabel lblGiaPhong;
	private JLabel lblMaPhong;
	private JLabel lblMaKH;
	private JLabel lblTenKH;
	private JLabel lblGioVao;
	private JLabel lblPhutVao;
	private JLabel lblGioRa;
	private JLabel lblPhutRa;
	private DAOCTHD daoCTHD;
	private DefaultTableModel modelCTHD;
	private DecimalFormat dfTable;
	private DecimalFormat df;
	private JTextField txtTim;
	private JLabel lblThanhTien;
	private JLabel lblPhuThu;
	private JLabel lblThoiGian;
	private JLabel lblGiamGia;
	private JLabel lblThanhToanLoaiKH;




	public FrmDanhSachHoaDon(JFrame frm) {
		this.frm = frm;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Danh sách hóa đơn");
		setSize(1081, 706);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		daoHD = new DAOHoaDon();
		daoCTHD = new DAOCTHD();
		daoLoaiMH = new DAOLoaiMH();
		daoMatHang = new DAOMatHang();
		daoCTDDP = new DAOCTDDP();
		daoLoaiPhong = new DAOLoaiPhong();
		daoDDP = new DAODonDatPhong();
		daoPhong = new DAOPhong();
		daoKhachHang =  new DAOKhachHang();
		daoHD = new DAOHoaDon();
		daoNhanVien = new DAONhanVien();
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 1106, 682);
		pMain.setBackground(Color.WHITE);
		getContentPane().add(pMain);
		pMain.setLayout(null);
		
		btnQuayLai = new FixButton("Quay lại");
		btnQuayLai.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnQuayLai.setBackground(new Color(114, 23 ,153));
		btnQuayLai.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnQuayLai.setForeground(Color.WHITE);
		btnQuayLai.setBounds(10, 59, 115, 35);
		
		Icon iconThoat = IconFontSwing.buildIcon(FontAwesome.ARROW_CIRCLE_LEFT, 20, new Color(255, 255 ,255));
		btnQuayLai.setIcon(iconThoat);
		
		pMain.add(btnQuayLai);
		
		JLabel lblDanhSachHD = new JLabel("Danh sách hóa đơn");
		lblDanhSachHD.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblDanhSachHD.setBounds(10, 11, 225, 29);
		pMain.add(lblDanhSachHD);
		btnQuayLai.addActionListener(this);
		
		JLabel lblTimKiem = new JLabel("Tìm Kiếm:");
		lblTimKiem.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTimKiem.setBounds(306, 11, 90, 35);
		pMain.add(lblTimKiem);
		
		
		
		txtTim = new JTextField();
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 14));
		txtTim.setText("Nhập tên khách hàng, mã, tên nhân viên.");
		txtTim.setForeground(Color.lightGray);
		txtTim.setBounds(394, 11, 281, 36);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		
		pMain.add(txtTim);
		
		
		btnTim = new FixButton("Tìm");
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBounds(685, 11, 98, 34);
		btnTim.setBackground(new Color(114, 23 ,153));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setForeground(Color.WHITE);
		
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 18, new Color(255, 255, 255));
		btnTim.setIcon(iconTim);
		pMain.add(btnTim);
		
		JPanel pSapXep = new JPanel();
		pSapXep.setBackground(new Color(220,210,239));
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23, 153), 1, true), "Chọn thời gian", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBounds(237, 53, 747, 56);
		pMain.add(pSapXep);
		
		pSapXep.setLayout(null);
		
		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		
		dNow = new Date(nam,thang,ngay);
		
		chooserNgayBatDau = new JDateChooser();
		chooserNgayBatDau.setDateFormatString("dd/MM/yyyy");
		chooserNgayBatDau.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		chooserNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgayBatDau.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgayBatDau.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgayBatDau.setBounds(93, 15, 191, 28);
		
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 18, new Color(255, 255 ,255));
		
		chooserNgayBatDau.setIcon((ImageIcon) iconCalendar);
		chooserNgayBatDau.setDate(dNow);
		pSapXep.add(chooserNgayBatDau);
		
		ButtonGroup bg = new ButtonGroup();
		
		JLabel lblNgayBatDau = new JLabel("Từ ngày:");
		lblNgayBatDau.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNgayBatDau.setBounds(23, 10, 70, 35);
		pSapXep.add(lblNgayBatDau);
		
		JLabel lblNgayKetThuc = new JLabel("Đến ngày:");
		lblNgayKetThuc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNgayKetThuc.setBounds(319, 15, 108, 28);
		pSapXep.add(lblNgayKetThuc);
		
		chooserNgayKetThuc = new JDateChooser();
		chooserNgayKetThuc.setDateFormatString("dd/MM/yyyy");
		chooserNgayKetThuc.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		chooserNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgayKetThuc.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgayKetThuc.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgayKetThuc.setBounds(398, 15, 191, 28);
		chooserNgayKetThuc.setDate(dNow);
		
		chooserNgayKetThuc.setIcon((ImageIcon) iconCalendar);
		
		pSapXep.add(chooserNgayKetThuc);
	
		
		btnXem = new FixButton("Xem");
		btnXem.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXem.setBounds(623, 13, 98, 30);
		btnXem.setBackground(new Color(114, 23 ,153));
		btnXem.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXem.setForeground(Color.WHITE);
		
		Icon iconXem = IconFontSwing.buildIcon(FontAwesome.LIST, 18, new Color(255, 255, 255));
		btnXem.setIcon(iconXem);
		pSapXep.add(btnXem);
		
	
		
		
		
		JScrollPane scrollPaneListKH = new JScrollPane();
		
		scrollPaneListKH.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneListKH.setBackground(new Color(164, 44, 167));
		scrollPaneListKH.setBounds(10, 116, 1047, 191);
		pMain.add(scrollPaneListKH);
		
		String col []= {"Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Tên nhân viên", "Ngày lập","Phụ thu"};
		modelHoaDon = new DefaultTableModel(col, 0);		
		tblHoaDon = new JTable(modelHoaDon);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		tblHoaDon.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		tblHoaDon.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tblHoaDon.setShowHorizontalLines(true);
		tblHoaDon.setRowHeight(30);
		tblHoaDon.setSelectionBackground(new Color(164, 44, 167,30));
		tblHoaDon.setSelectionForeground(new Color(114, 23, 153));
		//tableDanhSachKH.setOpaque(false);
		tblHoaDon.setShowGrid(true);
		
		JTableHeader tbHeader = tblHoaDon.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		scrollPaneListKH.setViewportView(tblHoaDon);
		
		JLabel lblSubPhong = new JLabel("Phòng : ");
		lblSubPhong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubPhong.setBounds(142, 314, 56, 26);
		pMain.add(lblSubPhong);
		
		lblMaPhong = new JLabel("");
		lblMaPhong.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblMaPhong.setBounds(199, 314, 84, 26);
		pMain.add(lblMaPhong);
		
		
		JLabel lblSubGioVao = new JLabel("Giờ vào: ");
		lblSubGioVao.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubGioVao.setBounds(293, 314, 61, 26);
		pMain.add(lblSubGioVao);
		
		lblGioVao = new JLabel("");
		lblGioVao.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblGioVao.setBounds(353, 313, 39, 26);
		pMain.add(lblGioVao);
		
		lblPhutVao = new JLabel("");
		lblPhutVao.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblPhutVao.setBounds(400, 314, 39, 26);
		pMain.add(lblPhutVao);
		
		JLabel blbSubAfterGioRa1 = new JLabel(":");
		blbSubAfterGioRa1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		blbSubAfterGioRa1.setBounds(387, 319, 12, 14);
		pMain.add(blbSubAfterGioRa1);
		
		JLabel lblSubGioRa = new JLabel("Giờ ra: ");
		lblSubGioRa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubGioRa.setBounds(502, 313, 61, 26);
		pMain.add(lblSubGioRa);
		
		 lblGioRa = new JLabel("");
		lblGioRa.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblGioRa.setBounds(562, 312, 39, 26);
		pMain.add(lblGioRa);
		
		JLabel blbSubAfterGioRa2 = new JLabel(":");
		blbSubAfterGioRa2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		blbSubAfterGioRa2.setBounds(596, 318, 12, 14);
		pMain.add(blbSubAfterGioRa2);
		
		 lblPhutRa = new JLabel("");
		lblPhutRa.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblPhutRa.setBounds(609, 313, 39, 26);
		pMain.add(lblPhutRa);
		
		
		JLabel lblTrangThaiHoaDon = new JLabel("Trạng thái hóa đơn :");
		lblTrangThaiHoaDon.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblTrangThaiHoaDon.setBounds(685, 314, 146, 22);
		pMain.add(lblTrangThaiHoaDon);
		
		lblTrangThai = new JLabel("");
		lblTrangThai.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblTrangThai.setBounds(826, 315, 115, 21);
		pMain.add(lblTrangThai);
		
		JScrollPane scrollPaneListMH = new JScrollPane();
		scrollPaneListMH.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneListMH.setBackground(new Color(164, 44, 167));
		scrollPaneListMH.setBounds(10, 344, 1047, 249);
		pMain.add(scrollPaneListMH);
		
		String colMH [] = {"Mã mặt hàng", "Tên mặt hàng", "Loại mặt hàng", "Số lượng", "Đơn giá", "Tổng tiền"};
		modelCTHD = new DefaultTableModel(colMH, 0);	
		
		tblCTHD = new JTable(modelCTHD);
		
		tblCTHD.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tblCTHD.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tblCTHD.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		tblCTHD.setShowHorizontalLines(true);
		tblCTHD.setShowGrid(true);
		tblCTHD.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tblCTHD.setSelectionBackground(new Color(164, 44, 167,30));
		tblCTHD.setSelectionForeground(new Color(114, 23, 153));
		tblCTHD.setRowHeight(30);
		
		
		JTableHeader tbHeaderMH = tblCTHD.getTableHeader();
		tbHeaderMH.setBackground(new Color(164, 44, 167));
		tbHeaderMH.setForeground(Color.white);
		tbHeaderMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		scrollPaneListMH.setViewportView(tblCTHD);
		
		lblThanhToanLoaiKH = new JLabel("");
		lblThanhToanLoaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblThanhToanLoaiKH.setBounds(39, 595, 98, 26);
		pMain.add(lblThanhToanLoaiKH);
		
		lblGiamGia = new JLabel("");
		lblGiamGia.setForeground(Color.RED);
		lblGiamGia.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblGiamGia.setBounds(145, 595, 100, 26);
		pMain.add(lblGiamGia);
		
		JLabel lblSubGiaPhong = new JLabel("Giá phòng: ");
		lblSubGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubGiaPhong.setBounds(559, 595, 77, 26);
		
		pMain.add(lblSubGiaPhong);
		
		lblGiaPhong = new JLabel("");
		lblGiaPhong.setForeground(Color.RED);
		lblGiaPhong.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblGiaPhong.setBounds(653, 595, 103, 26);
		pMain.add(lblGiaPhong);
		
		lblThoiGian = new JLabel("");
		lblThoiGian.setForeground(Color.RED);
		lblThoiGian.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblThoiGian.setBounds(854, 595, 183, 26);
		pMain.add(lblThoiGian);
		
		JLabel lblSubThoiGian = new JLabel("Thời gian: ");
		lblSubThoiGian.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubThoiGian.setBounds(782, 595, 77, 26);
		pMain.add(lblSubThoiGian);
		
		JLabel lblpSubPhuThu = new JLabel("Phụ thu: ");
		lblpSubPhuThu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblpSubPhuThu.setBounds(293, 595, 61, 26);
		pMain.add(lblpSubPhuThu);
		
		lblPhuThu = new JLabel("");
		lblPhuThu.setForeground(Color.RED);
		lblPhuThu.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblPhuThu.setBounds(357, 595, 169, 26);
		pMain.add(lblPhuThu);
		
		JLabel lblSubThanhTien = new JLabel("Thành tiền: ");
		lblSubThanhTien.setFont(new Font("SansSerif", Font.PLAIN, 17));
		lblSubThanhTien.setBounds(782, 640, 90, 26);
		pMain.add(lblSubThanhTien);
		
		lblThanhTien = new JLabel("");
		lblThanhTien.setForeground(Color.RED);
		lblThanhTien.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20));
		lblThanhTien.setBounds(882, 640, 175, 26);
		pMain.add(lblThanhTien);
		
		JPanel pLine = new JPanel();
		pLine.setBackground(Color.BLACK);
		pLine.setBounds(763, 626, 290, 3);
		pMain.add(pLine);

		btnLamMoi = new FixButton("Làm mới");
		btnLamMoi.setBounds(793, 11, 98, 35);
		pMain.add(btnLamMoi);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoi.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoi.setBackground(new Color(114, 23, 153));
		
		Icon iconLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, new Color(255, 255 ,255));
		btnLamMoi.setIcon(iconLamMoi);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackground.setBounds(0, 0, 1292, 670);
		Image imgBackground = Toolkit.getDefaultToolkit ().getImage ("data\\img\\background.png");
		Image resizeBG = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBG));
		
		pMain.add(lblBackground);
		
		
		lblThanhToanLoaiKH.setText("Giảm giá: ");
		
		
		btnLamMoi.addActionListener(this);
		btnXem.addActionListener(this);
		btnTim.addActionListener(this);
		
		tblHoaDon.addMouseListener(this);
		txtTim.addMouseListener(this);
		
		
		
		sf = new SimpleDateFormat("dd/MM/yyyy");
		dfTable = new DecimalFormat("###,###");
		df = new DecimalFormat("###,### VNĐ");
		
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e)
//			{
//				frm.setVisible(true);
//			}
//		});
	}
	
	
	/**
	 * Xóa toàn bộ các danh sách trong bảng hóa đơn
	 */
	public void clearTable() {
		while (tblHoaDon.getRowCount() > 0) {
			modelHoaDon.removeRow(0);
		}
	}
	public void clearTableCTHD() {
		while (tblCTHD.getRowCount() > 0) {
			modelCTHD.removeRow(0);
		}
	}
	
	public void resetAll() {
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 14));
		txtTim.setText("Nhập tên khách hàng, mã, tên nhân viên.");
		txtTim.setForeground(Color.lightGray);
		lblMaPhong.setText("");
		lblGioVao.setText("");
		lblPhutVao.setText("");
		lblGioRa.setText("");
		lblPhutRa.setText("");
		lblTrangThai.setText("");
		
		
		lblGiamGia.setText("");
		lblPhuThu.setText("");
		lblGiaPhong.setText("");
		lblThoiGian.setText("");
		lblThanhTien.setText("");
		
		
		chooserNgayBatDau.setDate(dNow);
		chooserNgayKetThuc.setDate(dNow);
		clearTable();
		clearTableCTHD();
	}
	
	public void loadTableHoaDon(ArrayList<HoaDon> lsHD) {
		clearTable();
		clearTableCTHD();
		for(HoaDon hd : lsHD) {
			KhachHang kh = daoKhachHang.getKHTheoMa(hd.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getNVTheoMa(hd.getNhanVien().getMaNhanVien());
			modelHoaDon.addRow(new Object[] {
					hd.getMaHoaDon(),kh.getMaKhangHang(),kh.getTenKH(),nv.getMaNhanVien(),nv.getTenNhanVien(),sf.format(hd.getNgayLap()),hd.getPhuThu()
			});
			
		}
		
	}
	
	/**
	 * Tính tổng tiền chi tiết hóa đơn của một hóa đơn
	 * @param tongTienThue
	 * @return tổng tiền trong CTHD
	 */
	public double tongTienCTHD(double tongTienThue,HoaDon hd) {
		double tong = tongTienThue;
	
		ArrayList<CTHD> lsCTHD = daoCTHD.getCTHDTheoMaHD(hd.getMaHoaDon());
		for(CTHD ct : lsCTHD) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ct.getMatHang().getMaMatHang());
			tong += mh.getGiaMatHang() * ct.getSoLuong();
		}
		
		return tong;
	}
	
	public void loadHoaDon() {
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 14));
		txtTim.setText("Nhập tên khách hàng, mã, tên nhân viên.");
		txtTim.setForeground(Color.lightGray);
		
		java.util.Date utilngayBD = chooserNgayBatDau.getDate();
		java.util.Date utilngayKT = chooserNgayKetThuc.getDate();
		
		@SuppressWarnings("deprecation")
		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			
			ArrayList<HoaDon> lsHD = daoHD.getHDTheoNgay(ngayBatDau, ngayKetThuc);
			loadTableHoaDon(lsHD);
			
		}
		else JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
		
		
	}
	@SuppressWarnings("deprecation")
	public double tinhTienThue(double giaPhong, HoaDon hd) {
		int gioVao = hd.getGioVao().getHours(),
				phutVao = hd.getGioVao().getMinutes();
			int gioRa = hd.getGioRa().getHours(),
				phutRa = hd.getGioRa().getMinutes();
			
			int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
			double tongTienThuePhong = 0;
			if(tongThoiGian > 0) {
				if(tongThoiGian <= 60) {
					tongTienThuePhong = giaPhong;
					return tongTienThuePhong;
				}
				else {
					tongTienThuePhong = (tongThoiGian * giaPhong)/60;
					return tongTienThuePhong;
				}
			}
			
			 return -1;
	}
	
	public void loadThanhTien(HoaDon hd) {
		int row = tblHoaDon.getSelectedRow();
		String phuThu = modelHoaDon.getValueAt(row, 6).toString();
		Phong p = daoPhong.getPhongTheoMa(lblMaPhong.getText());
		double giaPhong =p.getGiaPhong();
		
		double giaPhuThu = 0;
		if(phuThu.equalsIgnoreCase("Buổi tối")) {
			giaPhuThu = 10000;
			
		}
		
		if(phuThu.equalsIgnoreCase("Ngày lễ")) {
			giaPhuThu = 30000;
			
		}
		if(phuThu.equalsIgnoreCase("Cuối tuần")) {
			giaPhuThu = 20000;
		}
		giaPhong = giaPhuThu + giaPhong;
		
		double tongTienThue = tinhTienThue(giaPhong, hd);
		
		int tongGioThue = (int) ((tongTienThue)/giaPhong);
		int tongPhutThue = (int) (((tongTienThue*60)/giaPhong) % 60);
		
		lblPhuThu.setText(phuThu+": "+ df.format(giaPhuThu));
		lblGiaPhong.setText(df.format(giaPhong));
		lblThoiGian.setText(tongGioThue+"h : "+tongPhutThue +"'  "+ df.format(tongTienThue));
		
		double thanhTien = tongTienCTHD(tongTienThue, hd);
		
			lblGiamGia.setText("- "+df.format(hd.getGiamGia()));
			thanhTien = thanhTien - hd.getGiamGia();
		
		
		lblThanhTien.setText(df.format(thanhTien));
		
	}
	
	
	//tìm kiếm
	public void loadTimKiem() {
		Regex regexDao = new Regex();
		String thongTin = txtTim.getText().trim();
		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		String regexTenKH = "^[ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$";
		ArrayList<HoaDon> lsHD = null;
		if(regexDao.regexTimDSHD(txtTim)) {
			if(thongTin.matches(regexMaNV)) {
				lsHD = daoHD.getHDTheoMaNV(thongTin);
				
			}
			else if(thongTin.matches(regexTenKH)) {
				lsHD = daoHD.getHDTheoTenKH(thongTin);
			}
			if(lsHD.size() == 0)
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tìm kiếm phù hợp!");
			loadTableHoaDon(lsHD);
			
		}
	
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnQuayLai)) {
			setVisible(false);
			frm.setVisible(true);
		}
		if(o.equals(btnLamMoi)) {
			resetAll();
		}
		if(o.equals(btnXem)) {
			loadHoaDon();
		}
		if(o.equals(btnTim)) {
			loadTimKiem();
		}
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void loadThongTin(HoaDon hd) {
		lblMaPhong.setText(hd.getPhong().getMaPhong());
		lblGioVao.setText(hd.getGioVao().getHours()+"h");
		lblPhutVao.setText(hd.getGioVao().getMinutes()+"'");
		lblGioRa.setText(hd.getGioRa().getHours()+"h");
		lblPhutRa.setText(hd.getGioRa().getMinutes()+"'");
		lblTrangThai.setText(hd.getTrangThaiHD());
	}
	
	
	
	public void loadTableCTHD(){
		clearTableCTHD();
		int row = tblHoaDon.getSelectedRow();
		String maHD = modelHoaDon.getValueAt(row, 0).toString();
		HoaDon hd = daoHD.getHDTheoMa(maHD);
		loadThongTin(hd);
		
		ArrayList<CTHD> lsCTHD = daoCTHD.getCTHDTheoMaHD(hd.getMaHoaDon());
		
		
		for(CTHD ct : lsCTHD) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ct.getMatHang().getMaMatHang());
			LoaiMatHang loaiMH =daoLoaiMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			double tongTien = mh.getGiaMatHang()*ct.getSoLuong();
			modelCTHD.addRow(new Object[] {
					mh.getMaMatHang(),mh.getTenMatHang(),loaiMH.getTenLoaiMatHang(),ct.getSoLuong(),dfTable.format(mh.getGiaMatHang()),dfTable.format(tongTien)
			});
		}
		loadThanhTien(hd);
		
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(txtTim)) {
			/*
			 *thay đổi lại font của txt tìm kiếm
			 */
			
			txtTim.setFont(new Font("SansSerif", Font.PLAIN, 14));
			
			txtTim.setText("");
			txtTim.setForeground(Color.black);
			
		}
		if(o.equals(tblHoaDon)) {
			
			loadTableCTHD();
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
}
