package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
import dao.DAOPhatSinhMa;
import dao.DAOPhong;
import dao.Regex;
import entity.CTDDP;
import entity.CTHD;
import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiKH;
import entity.LoaiMatHang;
import entity.LoaiPhong;
import entity.MatHang;
import entity.NhanVien;
import entity.Phong;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmThanhToan extends JPanel implements ActionListener, MouseListener,ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV;
	private String sHeaderTenNV;
	private Date dNgayHienTai;
	private JFrame frm;
	private Panel pMain;
	
	private JTextField txtTim;
	private JTextField txtSoLuong;
	
	private DefaultTableModel modelMatHang;
	private JTable tbMatHang;
	private JButton btnDSHD,btnTim;
	private DAOLoaiMH daoLoaiMH;
	private JComboBox<String> cbbTenMH;
	private JComboBox<String> cbbLoaiMH;
	private DAOMatHang daoMatHang;
	private JButton btnThemMH;
	private JButton btnXoaMH;
	private JButton btnLamMoiMH;
	private JButton btnThanhToan;
	private JButton btnLamMoiHD;
	private DAOCTDDP daoCTDDP;
	private JPanel pPhong;
	private DAOLoaiPhong daoLoaiPhong;
	private JLabel lblMaPhong;
	private DAODonDatPhong daoDDP;
	private DAOPhong daoPhong;
	private JLabel lblMaKH;
	private DAOKhachHang daoKhachHang;
	private JLabel lblTenKH;
	private JLabel lblGioVao;
	private JLabel lblPhutVao;
	private JLabel lblThanhTien;
	private JLabel lblPhuThu;
	private JLabel lblThoiGian;
	private JLabel lblGiaPhong;
	private JComboBox<String> cbbPhutRa;
	private JComboBox<String> cbbGioRa;
	private JComboBox<String> cbbPhuThu;
	private JLabel lblNhanVienLap;
	private DAOHoaDon daoHD;
	private DAOPhatSinhMa daoMa;
	private DAONhanVien daoNhanVien;
	private JRadioButton rdbtnGiamSL;
	private DecimalFormat df;
	private DecimalFormat dfTable;
	private Regex regex;
	private JLabel lblThanhToanLoaiKH;
	private JLabel lblGiamGia;
	private JButton btnInHoaDon;
	private DAOCTHD daoCTHD;
	private double giamGia = 0;
	private JPanel pLine2;
	private JLabel lblThongTinHD;
	
	public Panel getFrmQLBH() {
		return this.pMain;
	}
	
	public FrmThanhToan(JFrame frm,String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai)  {
		
		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;
		this.frm = frm;
		
//connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//khai bao dao
		daoLoaiMH = new DAOLoaiMH();
		daoCTHD = new DAOCTHD();
		daoMatHang = new DAOMatHang();
		daoCTDDP = new DAOCTDDP();
		daoLoaiPhong = new DAOLoaiPhong();
		daoDDP = new DAODonDatPhong();
		daoPhong = new DAOPhong();
		daoKhachHang =  new DAOKhachHang();
		daoHD = new DAOHoaDon();
		daoNhanVien = new DAONhanVien();
		daoMa = new DAOPhatSinhMa();
		regex = new Regex();
		
		
		

		IconFontSwing.register(FontAwesome.getIconFont());
		
		setLayout(null);
		
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		add(pMain);
		pMain.setLayout(null);
		
		JLabel lblSubTimKiem = new JLabel("Tìm kiếm:");
		lblSubTimKiem.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSubTimKiem.setBounds(438, 10, 83, 35);
		pMain.add(lblSubTimKiem);
		
		txtTim = new JTextField();
		txtTim.setToolTipText("Tìm kiếm mã phòng đang hát");
		txtTim.setBounds(520, 11, 357, 33);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 14));
		txtTim.setText("Tìm phòng đang thuê theo mã phòng.");
		txtTim.setForeground(Color.lightGray);

		pMain.add(txtTim);
		txtTim.setColumns(10);
		

		
		
		btnTim = new FixButton("Tìm"); 
		btnTim.setBackground(new Color(114, 23 ,153));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBounds(887, 12, 98, 33);
		
		Icon iconTim = IconFontSwing.buildIcon(FontAwesome.SEARCH, 20, new Color(255, 255, 255));
		btnTim.setIcon(iconTim);
		
		pMain.add(btnTim);
		
		
		
		
		btnDSHD = new FixButton("Xem danh sách hóa đơn");
		
		btnTim.setBackground(new Color(114, 23 ,153));
		btnDSHD.setForeground(Color.WHITE);
		btnDSHD.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnDSHD.setBackground(new Color(114, 23, 153));
		btnDSHD.setBounds(1025, 12, 232, 33);
		

		Icon iconDSHD = IconFontSwing.buildIcon(FontAwesome.LIST_ALT, 20, new Color(57, 210, 247));
		btnDSHD.setIcon(iconDSHD);
		
		pMain.add(btnDSHD);
	
		
		JLabel lblHeaderPhong = new JLabel("Chọn phòng đang hát");
		lblHeaderPhong.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeaderPhong.setFont(new Font("SansSerif", Font.BOLD, 18));
	
		lblHeaderPhong.setBounds(10, 16, 208, 26);
		pMain.add(lblHeaderPhong);
		
		pPhong = new JPanel();
		pPhong.setToolTipText("Danh sách phòng đang hát");
		
		pPhong.setBackground(Color.white);		//new Color(164, 44, 167,20)
		pPhong.setLayout(new GridLayout(6, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane(pPhong);
		//JScrollPane scrollPane = new JScrollPane(); //pPhong
		scrollPane.setViewportView(pPhong);
		scrollPane.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		
		
		scrollPane.setBounds(10, 53, 216, 565);
		pMain.add(scrollPane);
		
		JPanel pThongTinDDP = new JPanel();
		pThongTinDDP.setToolTipText("Thông tin đơn đặt phòng");
		pThongTinDDP.setBackground(new Color(171, 192, 238));
		pThongTinDDP.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Thông tin chung", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pThongTinDDP.setBounds(519, 53, 738, 64);
		pMain.add(pThongTinDDP);
		pThongTinDDP.setLayout(null);
		
		JLabel lblSubPhong = new JLabel("Phòng : ");
		lblSubPhong.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubPhong.setBounds(10, 9, 56, 26);
		pThongTinDDP.add(lblSubPhong);
		
		lblMaPhong = new JLabel("");
		lblMaPhong.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblMaPhong.setBounds(62, 9, 98, 26);
		pThongTinDDP.add(lblMaPhong);
		
		JLabel lblSubTenKH = new JLabel("Khách hàng: ");
		lblSubTenKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubTenKH.setBounds(10, 33, 90, 26);
		pThongTinDDP.add(lblSubTenKH);
		
		lblMaKH = new JLabel("");
		lblMaKH.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblMaKH.setBounds(96, 33, 71, 26);
		pThongTinDDP.add(lblMaKH);
		
		lblTenKH = new JLabel("");
		lblTenKH.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblTenKH.setBounds(170, 33, 252, 26);
		pThongTinDDP.add(lblTenKH);
		
		
		JLabel lblSubGioVao = new JLabel("Giờ vào: ");
		lblSubGioVao.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubGioVao.setBounds(543, 33, 61, 26);
		pThongTinDDP.add(lblSubGioVao);
		
		lblGioVao = new JLabel("");
		lblGioVao.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblGioVao.setBounds(603, 33, 40, 26);
		pThongTinDDP.add(lblGioVao);
		
		lblPhutVao = new JLabel("");
		lblPhutVao.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblPhutVao.setBounds(653, 33, 56, 26);
		pThongTinDDP.add(lblPhutVao);
		
		JLabel blbSubAfterGioRa1 = new JLabel(":");
		blbSubAfterGioRa1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		blbSubAfterGioRa1.setBounds(644, 33, 26, 26);
		pThongTinDDP.add(blbSubAfterGioRa1);
		
	
		
		JPanel pDichVu = new JPanel();
		pDichVu.setToolTipText("Thông tin các dịch vụ và hóa đơn");
		pDichVu.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Dịch vụ ", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		pDichVu.setBackground(Color.WHITE);
		pDichVu.setBounds(228, 53, 281, 565);
		pMain.add(pDichVu);
		pDichVu.setLayout(null);
		
		JLabel lblSubLMH = new JLabel("Loại mặt hàng: ");
		lblSubLMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubLMH.setBounds(10, 27, 102, 26);
		pDichVu.add(lblSubLMH);
		
		cbbLoaiMH = new JComboBox<String>();
		cbbLoaiMH.setToolTipText("Chọn loại mặt hàng");
		cbbLoaiMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbLoaiMH.setBackground(Color.WHITE);
		cbbLoaiMH.setBounds(112, 25, 159, 30);
		cbbLoaiMH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		pDichVu.add(cbbLoaiMH);
		
		JLabel lblSubTenMH = new JLabel("Tên mặt hàng: ");
		lblSubTenMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubTenMH.setBounds(10, 66, 102, 26);
		pDichVu.add(lblSubTenMH);
		
		cbbTenMH = new JComboBox<String>();
		cbbTenMH.setToolTipText("Chọn tên mặt hàng");
		cbbTenMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbTenMH.setBackground(Color.WHITE);
		cbbTenMH.setBounds(112, 64, 159, 30);
		cbbTenMH.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		pDichVu.add(cbbTenMH);
		
		JLabel lblSoluongMH = new JLabel("Số lượng:");
		lblSoluongMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSoluongMH.setBounds(10, 104, 84, 26);
		pDichVu.add(lblSoluongMH);
		
		txtSoLuong = new JTextField();
		txtSoLuong.setBackground(new Color(255, 255, 255));
		txtSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtSoLuong.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));;
		txtSoLuong.setBounds(112, 103, 159, 30);
		pDichVu.add(txtSoLuong);
		txtSoLuong.setColumns(10);
		
		rdbtnGiamSL = new JRadioButton("Giảm số lượng");
		rdbtnGiamSL.setBackground(Color.WHITE);
		rdbtnGiamSL.setFont(new Font("SansSerif", Font.PLAIN, 15));
		rdbtnGiamSL.setBounds(112, 140, 159, 35);
		pDichVu.add(rdbtnGiamSL);
		
		btnThemMH = new FixButton("Thêm mặt hàng");
		btnThemMH.setForeground(Color.black);
		btnThemMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		btnThemMH.setBackground(new Color(57, 210, 247));
		btnThemMH.setBounds(10, 192, 261, 33);
		
		Icon iconThemMH = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, new Color(255, 255 ,255));
		btnThemMH.setIcon(iconThemMH);
		
		pDichVu.add(btnThemMH);
		
		btnXoaMH = new FixButton("Xóa mặt hàng");
		btnXoaMH.setForeground(Color.WHITE);
		btnXoaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		btnXoaMH.setBackground(new Color(0xE91940));
		btnXoaMH.setBounds(10, 236, 261, 33);
		
		Icon iconXoaMH = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, new Color(255, 255 ,255));
		btnXoaMH.setIcon(iconXoaMH);
		pDichVu.add(btnXoaMH);
		
		btnLamMoiMH = new FixButton("Làm mới");
		btnLamMoiMH.setForeground(Color.WHITE);
		btnLamMoiMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiMH.setBackground(new Color(114, 23, 153));
		btnLamMoiMH.setBounds(10, 280, 261, 33);
		
		Icon iconLamMoiMH = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, new Color(255, 255 ,255));
		btnLamMoiMH.setIcon(iconLamMoiMH);
		
		pDichVu.add(btnLamMoiMH);
		
		String col [] = {"Tên mặt hàng", "Tên loại", "Số lượng", "Đơn giá","Tổng tiền"};
		modelMatHang = new DefaultTableModel(col,0);
		
		tbMatHang = new JTable(modelMatHang);
		tbMatHang.setShowHorizontalLines(true);
		tbMatHang.setShowGrid(true);
		tbMatHang.setBackground(Color.WHITE);
		tbMatHang.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		JTableHeader tbHeader = tbMatHang.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		tbMatHang.setSelectionBackground(new Color(164, 44, 167,30));
		tbMatHang.setSelectionForeground(new Color(114, 23, 153));
		tbMatHang.setRowHeight(30);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		tbMatHang.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tbMatHang.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbMatHang.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		
		JScrollPane spMatHang = new JScrollPane(tbMatHang);
		spMatHang.setToolTipText("Danh sách các mặt hàng đã được đặt");
		spMatHang.setBounds(520, 120, 736, 398);
		spMatHang.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		spMatHang.setBackground(new Color(164, 44, 167));
		pMain.add(spMatHang);
		
		JPanel pLine = new JPanel();
		pLine.setBackground(Color.BLACK);
		pLine.setBounds(736, 582, 276, 2);
		pMain.add(pLine);
		
		JLabel lblSubNhanVien = new JLabel("Nhân viên lập:");
		lblSubNhanVien.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSubNhanVien.setBounds(10, 390, 102, 26);
		pDichVu.add(lblSubNhanVien);
		
		lblNhanVienLap = new JLabel(sHeaderMaNV);
		lblNhanVienLap.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblNhanVienLap.setBounds(112, 390, 159, 26);
		pDichVu.add(lblNhanVienLap);
		
		Icon iconThanhToan = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 25, new Color(255, 255 ,255));
		
		
		Icon iconLamMoiTT = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, new Color(255, 255 ,255));
		
		JLabel lblSubPhuThu = new JLabel("Phụ thu: ");
		lblSubPhuThu.setBounds(10, 427, 61, 26);
		pDichVu.add(lblSubPhuThu);
		lblSubPhuThu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		
		cbbPhuThu = new JComboBox<String>();
		cbbPhuThu.setToolTipText("Chọn loại phụ thu");
		cbbPhuThu.setBounds(112, 424, 159, 29);
		pDichVu.add(cbbPhuThu);
		cbbPhuThu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbPhuThu.setBackground(Color.WHITE);
		cbbPhuThu.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		
		JLabel lblSubGioRa = new JLabel("Giờ ra: ");
		lblSubGioRa.setBounds(11, 464, 61, 26);
		pDichVu.add(lblSubGioRa);
		lblSubGioRa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		
		 cbbGioRa = new JComboBox<String>();
		 cbbGioRa.setToolTipText("Chọn giờ ra");
		cbbGioRa.setBounds(112, 463, 69, 29);
		pDichVu.add(cbbGioRa);
		cbbGioRa.setBackground(Color.WHITE);
		cbbGioRa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbGioRa.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		
		JLabel blbSubAfterGioRa = new JLabel(":");
		blbSubAfterGioRa.setHorizontalAlignment(SwingConstants.CENTER);
		blbSubAfterGioRa.setBounds(181, 464, 20, 26);
		pDichVu.add(blbSubAfterGioRa);
		blbSubAfterGioRa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		
		cbbPhutRa = new JComboBox<String>();
		cbbPhutRa.setToolTipText("Chọn giờ ra");
		cbbPhutRa.setBounds(202, 463, 69, 29);
		pDichVu.add(cbbPhutRa);
		cbbPhutRa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbbPhutRa.setBackground(Color.WHITE);
		cbbPhutRa.setBorder(new LineBorder(new Color(114, 23 ,153), 1, true));
		

		btnInHoaDon = new FixButton("Xuất hóa đơn tạm");
		btnInHoaDon.setForeground(Color.WHITE);
		btnInHoaDon.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnInHoaDon.setBackground(new Color(114, 23, 153));
		btnInHoaDon.setBounds(10, 521, 261, 33);
		
		Icon iconInHD = IconFontSwing.buildIcon(FontAwesome.PRINT, 20, new Color(255, 255 ,255));
		btnInHoaDon.setIcon(iconInHD);
		pDichVu.add(btnInHoaDon);
		
		pLine2 = new JPanel();
		pLine2.setBackground(Color.BLACK);
		pLine2.setBounds(2, 338, 276, 2);
		pDichVu.add(pLine2);
		
		lblThongTinHD = new JLabel("Thông tin hóa đơn");
		lblThongTinHD.setHorizontalAlignment(SwingConstants.CENTER);
		lblThongTinHD.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblThongTinHD.setBounds(10, 351, 261, 35);
		pDichVu.add(lblThongTinHD);

		
		
		
		
		JPanel pThanhToan = new JPanel();
		pThanhToan.setToolTipText("Thông tin giá thanh toán");
		pThanhToan.setBackground(new Color(236,196,236));
		pThanhToan.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Thanh toán", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pThanhToan.setBounds(520, 519, 737, 99);
		pMain.add(pThanhToan);
		pThanhToan.setLayout(null);
		
		JLabel lblpSubPhuThu = new JLabel("Phụ thu: ");
		lblpSubPhuThu.setBounds(10, 11, 90, 26);
		pThanhToan.add(lblpSubPhuThu);
		lblpSubPhuThu.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		lblPhuThu = new JLabel("");
		lblPhuThu.setBounds(77, 11, 169, 26);
		pThanhToan.add(lblPhuThu);
		lblPhuThu.setForeground(Color.RED);
		lblPhuThu.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		
		JLabel lblSubGiaPhong = new JLabel("Giá phòng: ");
		lblSubGiaPhong.setBounds(244, 11, 77, 26);
		pThanhToan.add(lblSubGiaPhong);
		lblSubGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		lblGiaPhong = new JLabel("");
		lblGiaPhong.setBounds(333, 11, 109, 26);
		pThanhToan.add(lblGiaPhong);
		lblGiaPhong.setForeground(Color.RED);
		lblGiaPhong.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		
	
		
		lblThanhToanLoaiKH = new JLabel("");
		lblThanhToanLoaiKH.setBounds(10, 33, 90, 26);
		pThanhToan.add(lblThanhToanLoaiKH);
		lblThanhToanLoaiKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		lblGiamGia = new JLabel("");
		lblGiamGia.setBounds(96, 33, 149, 26);
		pThanhToan.add(lblGiamGia);
		lblGiamGia.setForeground(Color.RED);
		lblGiamGia.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		
		JLabel lblSubThoiGian = new JLabel("Thời gian: ");
		lblSubThoiGian.setBounds(244, 33, 77, 26);
		pThanhToan.add(lblSubThoiGian);
		lblSubThoiGian.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		lblThoiGian = new JLabel("");
		lblThoiGian.setBounds(326, 33, 176, 26);
		pThanhToan.add(lblThoiGian);
		lblThoiGian.setForeground(Color.RED);
		lblThoiGian.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		
		JLabel lblSubThanhTien = new JLabel("Thành tiền: ");
		lblSubThanhTien.setBounds(226, 68, 90, 26);
		pThanhToan.add(lblSubThanhTien);
		lblSubThanhTien.setFont(new Font("SansSerif", Font.PLAIN, 17));
		
		lblThanhTien = new JLabel("");
		lblThanhTien.setBounds(312, 67, 187, 26);
		pThanhToan.add(lblThanhTien);
		lblThanhTien.setForeground(Color.RED);
		lblThanhTien.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 20));
		
		btnLamMoiHD = new FixButton("Làm mới");
		btnLamMoiHD.setBounds(541, 55, 186, 33);
		pThanhToan.add(btnLamMoiHD);
		btnLamMoiHD.setForeground(Color.WHITE);
		btnLamMoiHD.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiHD.setBackground(new Color(114, 23, 153));
		btnLamMoiHD.setIcon(iconLamMoiTT);
		
		
		
		btnThanhToan = new FixButton("Thanh toán");
		btnThanhToan.setBounds(541, 14, 186, 35);
		pThanhToan.add(btnThanhToan);
		btnThanhToan.setForeground(Color.black);
		btnThanhToan.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnThanhToan.setBackground(new Color(57, 210, 247));
		btnThanhToan.setIcon(iconThanhToan);
		btnThanhToan.addActionListener(this);
		
		
		btnLamMoiHD.addActionListener(this);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackground.setBounds(0, 0, 1281, 629);
		Image imgBackground = Toolkit.getDefaultToolkit ().getImage ("data\\img\\background.png");
		Image resizeBG = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBG));	
		pMain.add(lblBackground);
		
//		Định dạng tiền: 
		dfTable = new DecimalFormat("###,###");
		df = new DecimalFormat("###,### VNĐ");
		
//		Load cbb gio phut ra 
		for(int i=0 ; i <24;i++ ) {
			cbbGioRa.addItem(""+i);
		}
		
		for(int i =0; i<60; i++) {
			cbbPhutRa.addItem(""+i);
		}
	
		String sPhuThu [] = {"Không","Buổi tối","Ngày lễ","Cuối tuần"};
		for(int i =0; i< sPhuThu.length;i++) {
			cbbPhuThu.addItem(sPhuThu[i]);
		}
		

	
		
//		Load tên loại mặt hàng
		ArrayList<LoaiMatHang> lsLoaiMH = daoLoaiMH.getAllLoaiMatHang();
		for(LoaiMatHang lmh : lsLoaiMH) {
			cbbLoaiMH.addItem(lmh.getTenLoaiMatHang());
		}
		
//		 Load ten mat hang mac dinh :
		String tenMH = (String) cbbLoaiMH.getSelectedItem();
		String maLoaiMatHang = daoLoaiMH.getMaLoaiMHTheoTen(tenMH);
		ArrayList<MatHang> lsMH = daoMatHang.getMatHangTheoMaLoai(maLoaiMatHang);
		cbbTenMH.removeAllItems();
		for(MatHang mh : lsMH) {
			cbbTenMH.addItem(mh.getTenMatHang());
		}
		
//		Load Phong dang hoat dong
		loadPhong();

		
//		action 
		cbbLoaiMH.addItemListener(this);
		cbbTenMH.addItemListener(this);
		cbbGioRa.addItemListener(this);
		cbbPhutRa.addItemListener(this);
		cbbPhuThu.addItemListener(this);
		
		tbMatHang.addMouseListener(this);
		txtTim.addMouseListener(this);
		
		btnDSHD.addActionListener(this);
		btnThemMH.addActionListener(this);
		btnLamMoiMH.addActionListener(this);
		btnXoaMH.addActionListener(this);
		rdbtnGiamSL.addActionListener(this);
		btnTim.addActionListener(this);
		btnInHoaDon.addActionListener(this);
		
		
		
	}

	
	/**
	 * Tải lên thông tin của phòng đã được đặt, đang có trạng thái đã đặt, có đơn đặt phòng có trạng thái
	 * đã xác nhận.
	 * thông tin phòng tải lên sẽ kèm theo giao diện của từng nút, có chức năng như đang chứa thông tin đơn đặt phòng.
	 *
	 */
	public void loadPhong() {

	
		ArrayList<Phong> lsPhong = daoPhong.getPhongDangHoatDong(dNgayHienTai);
		for(Phong p : lsPhong) {
			JPanel pn = new JPanel();
			
			LoaiPhong lp = daoLoaiPhong.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			JButton btnPhong = new JButton(p.getMaPhong());
			pn.add(btnPhong);
			
			btnPhong.setBackground(new Color(57, 210, 247));
			btnPhong.setPreferredSize(new Dimension(70,70));
			btnPhong.setBorder(new LineBorder(Color.white,10));
			
			JLabel lblTenPhong = new JLabel(lp.getTenLoaiPhong()+ " "+ p.getMaPhong());
			lblTenPhong.setFont(new Font("SansSerif", Font.BOLD, 15));
			pn.setBackground(new Color(248, 238, 248));
			btnPhong.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object o = e.getSource();
					if(o.equals(btnPhong)) {
						resetAll();
						loadInfo(p);
					}
					
				}
			});
		
			pn.add(lblTenPhong);
			
			pPhong.add(pn);
		
		}
	}
	
	/**
	 * Tải thông tin phòng khi có mã phòng lên giao diện ở các label, đồng thời tải các danh sách mặt hàng đơn đặt phòng lên bảng
	 * @param Phong
	 */
	@SuppressWarnings("deprecation")
	public void loadInfo(Phong p) {
		lblMaPhong.setText(p.getMaPhong());
		DonDatPhong ddp = daoDDP.getDDPTheoMaPhong(p.getMaPhong());
		
		KhachHang kh = daoKhachHang.getKHTheoMa(ddp.getKhachHang().getMaKhangHang());
		Time gioDen = ddp.getGioDen();
		lblMaKH.setText(kh.getMaKhangHang());
		lblTenKH.setText(" - "+kh.getTenKH());
		lblGioVao.setText(""+gioDen.getHours());
		lblPhutVao.setText(""+gioDen.getMinutes());
		loadTable(ddp);
	}
	
	
	
	/**
	 * Tải thông tin mặt hàng của đơn đặt phòng lên bảng
	 * @param DonDatPhong
	 */
	public void loadTable(DonDatPhong ddp) {
		clearTable();
		ArrayList<CTDDP> lsCTDDP = daoCTDDP.getCTDDPTheoMaDDP(ddp.getMaDDP());
		for(CTDDP ctddp : lsCTDDP) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ctddp.getMatHang().getMaMatHang());
			LoaiMatHang loaiMH = daoLoaiMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			double tongTien = mh.getGiaMatHang() * ctddp.getSoLuongMH();
			modelMatHang.addRow(new Object[] {
					mh.getTenMatHang(),loaiMH.getTenLoaiMatHang(),ctddp.getSoLuongMH(),dfTable.format(Math.round( mh.getGiaMatHang())),dfTable.format(Math.round(tongTien))
			});
		}
	}
	
	/**
	 * Xóa toàn bộ các danh sách trong bảng
	 */
	public void clearTable() {
		while (tbMatHang.getRowCount() > 0) {
			modelMatHang.removeRow(0);
		}
	}
	
	
	/**
	 * Tính tiền thuê phòng = thời gian hát * giá phòng
	 * Thời gian được chuyển sang phút trước khi tính tiền phòng
	 * Trả về tiền thuê phòng
	 * @param giaPhong
	 * @return double 
	 */
	public double tinhTienThue(double giaPhong) {
		int gioVao = Integer.parseInt(lblGioVao.getText()),
				phutVao = Integer.parseInt(lblPhutVao.getText());
			int gioRa = Integer.parseInt(cbbGioRa.getSelectedItem().toString()),
				phutRa = Integer.parseInt(cbbPhutRa.getSelectedItem().toString());
			
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
	
	/**
	 * Tính tổng tiền chi tiết hóa đơn của một hóa đơn.
	 *  Trả về tổng tiền trong CTHD
	 * @param tongTienThue
	 * @return tổng tiền trong CTHD
	 */
	public double tongTienCTHD(double tongTienThue) {
		double tong = tongTienThue;
		DonDatPhong ddp = daoDDP.getDDPTheoMaPhong(lblMaPhong.getText());
		ArrayList<CTDDP> lsCTDDP = daoCTDDP.getCTDDPTheoMaDDP(ddp.getMaDDP());
		for(CTDDP ctddp : lsCTDDP) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ctddp.getMatHang().getMaMatHang());
			tong += mh.getGiaMatHang() * ctddp.getSoLuongMH();
		}
		
		return tong;
	}

	/**
	 * Tải lên giao diện thông tin thành tiền, bao gồm giá phòng, phụ thu, tính giờ, tính toàn bộ thành tiền...
	 * 
	 */
	public void loadThanhTien() {
		if(!lblMaPhong.getText().toString().equalsIgnoreCase("")) {
			Phong p = daoPhong.getPhongTheoMa(lblMaPhong.getText());
			double giaPhong =p.getGiaPhong();
			String phuThu = cbbPhuThu.getSelectedItem().toString();
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

			double tongTienThue = tinhTienThue(giaPhong);
			
			//Nếu tổng tiền thuê > 0 thì tính thành tiền theo toognr tiền thuê
			if(tongTienThue > 0) {
				int tongGioThue = (int) ((tongTienThue)/giaPhong);
				int tongPhutThue = (int) (((tongTienThue*60)/giaPhong) % 60);
				
				lblPhuThu.setText(phuThu+": "+ df.format(giaPhuThu));
				lblGiaPhong.setText(df.format(giaPhong));
				lblThoiGian.setText(tongGioThue+"h : "+tongPhutThue +"'  "+ df.format(tongTienThue));
				
				double thanhTien = tongTienCTHD(tongTienThue);
				
				KhachHang kh = daoKhachHang.getKHTheoMa(lblMaKH.getText());
				
				if(kh.getLoaiKH().getMaLoaiKH().equalsIgnoreCase("LKH002")) {
					lblThanhToanLoaiKH.setText("Thành viên: ");
					giamGia  = thanhTien* 0.03;
					lblGiamGia.setText("- "+df.format(giamGia));
					thanhTien = thanhTien - thanhTien*0.03;
				}
				else if(kh.getLoaiKH().getMaLoaiKH().equalsIgnoreCase("LKH003")) {
					lblThanhToanLoaiKH.setText("VIP: ");
					giamGia  = thanhTien* 0.1;
					lblGiamGia.setText("- "+df.format(giamGia));
					thanhTien = thanhTien - thanhTien*0.1;
				}
				
				lblThanhTien.setText(df.format(thanhTien));
			}
			else 
				JOptionPane.showMessageDialog(this, "Thời gian ra không hợp lệ!\nThời gian ra phải lớn hơn thời gian vào");
		}
		else JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để thay đổi thời gian và phụ thu phù hợp!");
	}
	
// 	XỬ LÝ MẶT HÀNG : mã đơn đặt phòng, maMH, số lượng
	/**
	 * Tìm mặt hàng trong bảng, nếu tìm thấy thì sửa số lượng mặt hàng ở bảng theo CTHD
	 * @param ctddp
	 * @param matHang
	 * @return boolean
	 */
	public boolean kiemTraMatHangTrongBang(CTDDP ctddp, MatHang mh) {
		if(timRow() != -1) {
			daoCTDDP.suaSoluongMH(ctddp.getDonDatPhong().getMaDDP(), ctddp.getMatHang().getMaMatHang(), getSoLuongMH());
			modelMatHang.setValueAt(getSoLuongMH(),timRow(), 2);
			double giaMoi = mh.getGiaMatHang()* getSoLuongMH();
			modelMatHang.setValueAt(dfTable.format(giaMoi), timRow(), 4);
			
			
			return false;
		}
		return true;
		
	}
	
	/**
	 * Tìm số lượng của 1 mặt hàng. Nếu mặt hàng có trong bảng mặt hàng thì trả về số lượng mặt hàng trong bảng đó
	 * @return số lượng mặt hàng
	 */
	public int getSoLuongMH() {
		int soLuong = 0;
		if(timRow() != -1) {
			soLuong = Integer.parseInt(modelMatHang.getValueAt(timRow(), 2).toString()) + Integer.parseInt(txtSoLuong.getText());
			return soLuong;
		}
		else return Integer.parseInt(txtSoLuong.getText()); 
	}
	
	/**
	 * Tìm 1 hàng trong bảng mặt hàng, nếu tìm thấy sẽ trả về vị trí trong bảng của mặt hàng đó.
	 * @return vị trí hàng
	 */
	public int timRow() {		
		
		for(int i =0; i< tbMatHang.getRowCount(); i++) {
			if(modelMatHang.getValueAt(i, 0).toString().equalsIgnoreCase(cbbTenMH.getSelectedItem().toString())&& modelMatHang.getValueAt(i, 1).toString().equalsIgnoreCase(cbbLoaiMH.getSelectedItem().toString()))
				return i;
		}
		return -1;
	}

	/**
	 * Tính toán giảm số lượng một mặt hàng trong bảng
	 * @return Soluong
	 */
	public int giamSL() {
		int soLuong = 0;
		soLuong = Integer.parseInt(modelMatHang.getValueAt(timRow(), 2).toString()) - Integer.parseInt(txtSoLuong.getText());
		return soLuong;
		
	}
	/**
	 * Tìm mặt hàng trong bảng và thực hiện giảm số lượng
	 * Nếu số lượng nhỏ hơn hoặc bằng 0 thì thông báo lỗi và không thực hiện giảm số lượng
	 * @param ctddp
	 */
	public void kiemTraGiamSL(CTDDP ctddp) {
		
		
		if(timRow() != -1) {
			int row = giamSL();
			if(row > 0) {
				daoCTDDP.suaSoluongMH(ctddp.getDonDatPhong().getMaDDP(), ctddp.getMatHang().getMaMatHang(), row);
				modelMatHang.setValueAt(giamSL(),timRow(), 2);
				double giaMoi = ctddp.getMatHang().getGiaMatHang()* ctddp.getSoLuongMH();
				modelMatHang.setValueAt(dfTable.format(giaMoi), timRow(), 4);
			}
			else {
				JOptionPane.showMessageDialog(this, "Số lượng cần giảm đã lớn hơn số lượng hiện có trong đơn đặt phòng!\nVui lòng nhập lại số lượng");
				txtSoLuong.selectAll();
				txtSoLuong.requestFocus();
			}
		}
		else JOptionPane.showMessageDialog(this, "Mặt hàng cần giảm số lượng không đúng!\nVui lòng chọn lại");
	}
	
	
	/**
	 * Thêm một mặt hàng vào bảng.
	 * Nếu thông tin hợp lệ sẽ tạo đối tượng chi tiết hóa đơn và kiểm tra nút giảm số lượng có được chọn không
	 * Nếu được chọn sẽ thực hiện gọi phương thức giảm số lượng.
	 * Nếu không được chọn sẽ kiểm tra trong bảng có mặt hàng chưa, nếu có thì cập nhật số lượng, không thì sẽ được thêm mới
	 */
	
	public void themMHVaoCTDDP() {
			if(lblMaPhong.getText() != "") {
				if(regex.regexSoLuong(txtSoLuong)) {
					DonDatPhong ddp = daoDDP.getDDPTheoMaPhong(lblMaPhong.getText());
					
					String tenMH = cbbTenMH.getSelectedItem().toString();
					String loaiMH = cbbLoaiMH.getSelectedItem().toString();
					MatHang mh = daoMatHang.getMHTheoTenMHVaLoaiMH(tenMH, loaiMH);
					int soLuongMH = Integer.parseInt(txtSoLuong.getText());
					int soLuongTon = mh.getSoLuongMatHang();
						CTDDP ctddp = new CTDDP(ddp, soLuongMH, mh);
						if(rdbtnGiamSL.isSelected()) {
							kiemTraGiamSL(ctddp);
							mh.setSoLuongMatHang(soLuongTon+soLuongMH);
							daoMatHang.updateMH(mh);
							
						}
						else if(soLuongMH <= soLuongTon) {
								if(kiemTraMatHangTrongBang(ctddp,mh)) {
									daoCTDDP.themCTDDP(ctddp);
									double tongTien = mh.getGiaMatHang() * soLuongMH;
									modelMatHang.addRow(new Object[] {
										tenMH,loaiMH,ctddp.getSoLuongMH(),dfTable.format(Math.round( mh.getGiaMatHang())),dfTable.format(Math.round(tongTien))
									});
								}
								
								mh.setSoLuongMatHang(soLuongTon-soLuongMH);
								daoMatHang.updateMH(mh);
						}
						else JOptionPane.showMessageDialog(this, "Số lượng mặt hàng tồn không đủ cung cấp!\nVui lòng nhập lại số lượng.");

				}
			}
			else JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng sau đó nhập số lượng trước khi thêm mặt hàng!");
		}

	/**
	 * Xóa 1 mặt hàng hóa đơn sau đó các mục sẽ được làm mới. 
	 * Phòng phải được chọn.
	 * Trước khi xóa phải chọn một mặt hàng cần xóa
	 */
	public void xoaCTDDP() {
		if(!lblMaPhong.getText().equalsIgnoreCase("")) {
				if(timRow() != -1) {
				DonDatPhong ddp = daoDDP.getDDPTheoMaPhong(lblMaPhong.getText());
				
				String tenMH = cbbTenMH.getSelectedItem().toString();
				String loaiMH = cbbLoaiMH.getSelectedItem().toString();
				MatHang mh = daoMatHang.getMHTheoTenMHVaLoaiMH(tenMH, loaiMH);
				daoCTDDP.xoaCTDDP(ddp.getMaDDP(),mh.getMaMatHang());
				
				int soLuongMH = Integer.parseInt(modelMatHang.getValueAt(timRow(), 2).toString());
				int soLuongTon = mh.getSoLuongMatHang();
				
				mh.setSoLuongMatHang(soLuongTon+soLuongMH);
				daoMatHang.updateMH(mh);
				loadTable(ddp);
				resetDichVu();
			}
			else JOptionPane.showMessageDialog(this, "Vui lòng chọn mặt hàng cần xóa");
		}
		else JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng và chọn mặt hàng cần xóa");
		
	}

	/**
	 * Cập nhật lại thông tin điểm tích lũy và loại khách hàng. 
	 * Nếu điểm tích lũy lớn hơn 20 thì cập nhật lại thông tin loại khách hàng.
	 * @param kh
	 * @return KhachHang
	 */
	public KhachHang capNhatKHThanhToan(KhachHang kh) {
		if(!kh.getCccd().equalsIgnoreCase("")) {
			int diemTichLuy = kh.getDiemTichLuy() + 1;
			if(diemTichLuy >= 20) {
				kh.setLoaiKH(new LoaiKH("LKH003"));
				JOptionPane.showMessageDialog(this,"Khách hàng đã được nâng lên thành khách hàng VIP");
			}
			kh.setDiemTichLuy(diemTichLuy);

			
		}
		return kh;
	}
	

	/**
	 * Thêm một măt hàng mới vào bảng mặt hàng.
	 * Thông tin mặt hàng sẽ được lấy từ các combobox và jtextfield tương ứng
	 * @param HoaDon
	 */
	public void themCTHD(HoaDon hd) {
		int row = tbMatHang.getRowCount();
		for(int i =0; i< row; i++) {
			String tenMH = modelMatHang.getValueAt(i, 0).toString();
			String loaiMH = modelMatHang.getValueAt(i, 1).toString();
			int soLuong = Integer.parseInt(modelMatHang.getValueAt(i, 2).toString());
			MatHang	mh = daoMatHang.getMHTheoTenMHVaLoaiMH(tenMH, loaiMH);
			daoCTHD.themCTHD(new CTHD(soLuong, mh, hd));
		}
		
	}
	
	/**
	 * Giúp thêm một hóa đơn mới.
	 * Sẽ kiểm tra thông tin đầy đủ trước khi thực hiện lưu thông tin lên database.
	 * Sau khi thêm hóa đơn thành công sẽ cập nhật lại trạng thái phòng, điểm tích lũy nếu là thành viên
	 */
	public void themHD() {
		int optThanhToan = JOptionPane.showConfirmDialog(this, "Bạn có chắn chắn muốn thanh toán không?", "Thông báo", JOptionPane.YES_NO_OPTION );
		
		if(optThanhToan == JOptionPane.YES_OPTION) {
			if(lblMaPhong.getText().toString() !="") {
				if(!lblThanhTien.getText().equalsIgnoreCase("")) {
					String maHD = daoMa.getMaHD();
					Phong p = daoPhong.getPhongTheoMa(lblMaPhong.getText());
					KhachHang kh = daoKhachHang.getKHTheoMa(lblMaKH.getText());
					NhanVien nv = daoNhanVien.getNVTheoMa(sHeaderMaNV);
					Date ngayLap = dNgayHienTai;
		
					int gioVao = Integer.parseInt(lblGioVao.getText()),
						phutVao = Integer.parseInt(lblPhutVao.getText());
					int gioRa = Integer.parseInt(cbbGioRa.getSelectedItem().toString()),
							phutRa = Integer.parseInt(cbbPhutRa.getSelectedItem().toString());
					String phuThu = cbbPhuThu.getSelectedItem().toString();
					String trangThaiHD = "Đã thanh toán";
					double giamGiaThanhToan = this.giamGia;
						
					
					@SuppressWarnings("deprecation")
					HoaDon hd = new HoaDon(maHD, ngayLap, new Time(gioVao, phutVao, 0), new Time(gioRa, phutRa, 0), phuThu, trangThaiHD,giamGiaThanhToan, nv, kh, p);
					daoHD.themHoaDon(hd);
					themCTHD(hd);
					
					daoKhachHang.suaThongTinKhachHang(capNhatKHThanhToan(kh));
					daoPhong.capnhatTrangThaiPhong(p.getMaPhong(), "Trống");
					resetAll();
				}
				else JOptionPane.showMessageDialog(this, "Vui lòng chọn thời gian hợp lệ trước khi thanh toán!");
			}
			else JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng trước khi thanh toán!");
		}
		
	}
	

	
	/**
	 * giúp tìm kiếm thông tin phòng theo mã phòng đang hoạt động để thanh toán
	 */
	public void timKiem() {
		if(regex.regexTimKiemMaPhong(txtTim)) {
			Phong p1 = daoPhong.getPhongDangHoatDongTheoMaP(txtTim.getText().toString());
			if(p1!=null)
				loadInfo(p1);
			else 
				JOptionPane.showMessageDialog(this, "Không tìm thấy phòng đang hoạt động nào như yêu cầu!");
		}
	}
	
 
	/**
	 * giúp làm mới lại các mục của dịch vụ về mặc định
	 */
	public void resetDichVu() {
		cbbLoaiMH.setSelectedIndex(0);
		cbbTenMH.setSelectedIndex(0);
		txtSoLuong.setText("");
		rdbtnGiamSL.setSelected(false);
		
	}
	
	
	/**
	 * ResetAll giúp làm mới lại toàn bộ giao diện của thanh toán ngoại
	 */
	public void resetAll() {
		resetDichVu();
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 14));
		txtTim.setText("Tìm phòng đang thuê theo mã phòng.");
		txtTim.setForeground(Color.lightGray);

		pPhong.removeAll();
		pPhong.revalidate();
		pPhong.repaint();
		loadPhong();
		lblMaPhong.setText("");
		lblMaKH.setText("");
		lblTenKH.setText("");
		lblGioVao.setText("");
		lblPhutVao.setText("");
		clearTable();
		lblGiaPhong.setText("");
		lblPhuThu.setText("");
		lblThoiGian.setText("");
		lblThanhTien.setText("");
		cbbGioRa.setSelectedIndex(0);
		cbbPhutRa.setSelectedIndex(0);
		cbbPhuThu.setSelectedIndex(0);
		
		lblGiamGia.setText("");
		lblThanhToanLoaiKH.setText("");
		
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnDSHD)) {
			FrmDanhSachHoaDon frmDanhSachHoaDon = new FrmDanhSachHoaDon(frm);
			frmDanhSachHoaDon.setVisible(true);
			frm.setVisible(false);
		}
		if(o.equals(btnLamMoiMH)) {
			resetDichVu();
			
			
		}
		if(o.equals(btnLamMoiHD)) {
			resetAll();
		}
		if(rdbtnGiamSL.isSelected()) {
			btnThemMH.setText("Giảm mặt hàng");
			Icon minus = IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 25, new Color(255, 255 ,255));
			btnThemMH.setBackground(new Color(133, 217, 191));
			btnThemMH.setIcon(minus);
		} else {
			btnThemMH.setText("Thêm mặt hàng");
			Icon iconThanhToan = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 25, new Color(255, 255 ,255));
			btnThemMH.setBackground(new Color(57, 210, 247));
			btnThemMH.setIcon(iconThanhToan);
		}
		
		if(o.equals(btnThemMH)) {
			themMHVaoCTDDP();
		}
		if(o.equals(btnXoaMH)) {
			xoaCTDDP();
		}
		if(o.equals(btnThanhToan)) {
			themHD();
		}
		if(o.equals(btnTim)) {
			timKiem();
		
		}
		if(o.equals(btnInHoaDon)) {
			JOptionPane.showMessageDialog(this, "Chức năng đang hoàn thiện.\nVui lòng sử dụng chức năng sau khi được cập nhật.");
		}
		
		
	}

	
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getItem();
		if(o == cbbLoaiMH.getSelectedItem()) {
			/*
			 * Nếu cbb loại mặt hàng hàng thay đổi, thì cbb tên mặt hàng sẽ được hiển thị danh sách lên 
			 */
			String tenMH = (String) cbbLoaiMH.getSelectedItem();
			String maLoaiMatHang = daoLoaiMH.getMaLoaiMHTheoTen(tenMH);
			ArrayList<MatHang> lsMH = daoMatHang.getMatHangTheoMaLoai(maLoaiMatHang);
			cbbTenMH.removeAllItems();
			for(MatHang mh : lsMH) {
				cbbTenMH.addItem(mh.getTenMatHang());
			}
		}
		if(o == cbbGioRa.getSelectedItem()|| o== cbbPhutRa.getSelectedItem() || o == cbbPhuThu.getSelectedItem()) {
			/*
			 * Nếu giờ ra != 0 hoặc phút ra  != 0 thì sẽ load thành tiền
			 */
			if(!cbbGioRa.getSelectedItem().toString().equalsIgnoreCase("0")||!cbbPhutRa.getSelectedItem().toString().equalsIgnoreCase("0"))
				loadThanhTien();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Object o = e.getSource();
		
		if(o.equals(tbMatHang)) {
			
		int row = tbMatHang.getSelectedRow();
		cbbTenMH.setSelectedItem(modelMatHang.getValueAt(row,0).toString());
		cbbLoaiMH.setSelectedItem(modelMatHang.getValueAt(row,1).toString());
		txtSoLuong.setText(modelMatHang.getValueAt(row,2).toString());
		}
		if(o.equals(txtTim)) {
			/*
			 *thay đổi lại font của txt tìm kiếm
			 */
			txtTim.setFont(new Font("SansSerif", Font.PLAIN, 14));
			txtTim.setText("");
			txtTim.setForeground(Color.black);
			
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
