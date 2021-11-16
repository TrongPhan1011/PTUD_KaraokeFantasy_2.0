package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOCTHD;
import dao.DAOHoaDon;
import dao.DAOMatHang;
import dao.DAOPhong;
import entity.CTHD;
import entity.HoaDon;
import entity.MatHang;
import entity.Phong;



public class FrmThongKe extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV;
	private String sHeaderTenNV;
	private Panel pMain;
	private Date dNgayHienTai;
	private JRadioButton rdoTKNg;
	private JRadioButton rdoTKT;
	private JRadioButton rdoTKNam;
	private JButton btnTK;
	private JButton btnTongDoanhThu;
	private JButton btnSoMH;
	private JButton btnSoKH;
	private JButton btnTGHD;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private SpringLayout springLayout;
	private FixButton btnLamMoi;
	private JDateChooser dateChooserThongKeTheoNgay;
	private JComboBox<String> cbbThang;
	private JComboBox<String> cbbNamTh;
	private JComboBox<String> cbbNam;
	private DAOCTHD daoCTHD;
	private DAOMatHang daoMatHang;
	private DAOHoaDon daoHoaDon;
	private DAOPhong daoPhong;
	private DecimalFormat df;

	public Panel getFrmThongKe() {
		return this.pMain;
	}
	public FrmThongKe(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {
		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		//connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//khai báo DAO
		daoCTHD = new DAOCTHD();
		daoMatHang = new DAOMatHang();
		daoHoaDon = new DAOHoaDon();
		daoPhong = new DAOPhong();

		setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		add(pMain);
		pMain.setLayout(null);
		/////////////////////////////////////------------------------------------------
		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue();
		nam = now.getYear();

		dNow = new Date(nam,thang,ngay);


		JLabel lblHeaderDate = new JLabel("   Thời Gian:");
		lblHeaderDate.setForeground(Color.BLACK);
		lblHeaderDate.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblHeaderDate.setBounds(712, 11, 112, 41);
		pMain.add(lblHeaderDate);

		JLabel lblNgayHienTai = new JLabel(ngay+" / "+thang+" / "+nam);
		lblNgayHienTai.setForeground(Color.BLACK);
		lblNgayHienTai.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNgayHienTai.setBounds(834, 11, 151, 41);
		pMain.add(lblNgayHienTai);
		/////////////////////////////////////------------------------------------------
		JPanel pThongKe = new JPanel();
		pThongKe.setBackground(new Color(238,239,243,90));
		pThongKe.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pThongKe.setBounds(10, 11, 328, 607);
		pThongKe.setBackground(Color.WHITE);
		pMain.add(pThongKe);
		pThongKe.setLayout(null);

		//
		JPanel pBieuDo = new JPanel();
		pBieuDo.setBackground(new Color(238,239,243,90));
		pBieuDo.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pBieuDo.setBounds(348, 173, 905, 411);
		pBieuDo.setBackground(Color.WHITE);
		pMain.add(pBieuDo);
		pBieuDo.setLayout(null);
		//

		rdoTKNg = new JRadioButton("Thống kê theo ngày");
		rdoTKT = new JRadioButton("Thống kê theo tháng");
		rdoTKNam = new JRadioButton("Thống kê theo năm");
		rdoTKNam.setBackground(Color.white);
		rdoTKNg.setBackground(Color.white);
		rdoTKT.setBackground(Color.white);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoTKNam);
		bg.add(rdoTKNg);
		bg.add(rdoTKT);
		bg.clearSelection();

		pThongKe.add(rdoTKNg);
		pThongKe.add(rdoTKNam);
		pThongKe.add(rdoTKT);

		rdoTKNg.setBounds(10, 77, 260, 30);
		rdoTKNg.setFont(new Font("SansSerif", Font.PLAIN, 20));
		rdoTKT.setBounds(10, 179, 260, 29);
		rdoTKT.setFont(new Font("SansSerif", Font.PLAIN, 20));
		rdoTKNam.setBounds(10, 318, 260, 45);
		rdoTKNam.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel lblChonNgay = new JLabel("Chọn ngày: ");
		lblChonNgay.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNgay.setBounds(35, 120, 100, 36);
		pThongKe.add(lblChonNgay);

		//		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		//		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		//		lblNgaySinh.setBounds(859, 65, 90, 18);
		//		pMain.add(lblNgaySinh);

		SqlDateModel modelNgaySinh=new SqlDateModel();
		modelNgaySinh.setSelected(true);
		//modelNgaySinh.setDate(2000, 0, 1); //month= 0+1 = 1
		Properties p=new Properties();
		p.put("text.day", "Day");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl panel=new JDatePanelImpl(modelNgaySinh, p);
		JDatePickerImpl datePicker=new JDatePickerImpl(panel, new AbstractFormatter() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object stringToValue(String text) throws ParseException {
				//				text=new String("Chọn ngày");
				//				return text;
				return "";
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				if(value != null) {
					Calendar cal = (Calendar) value;
					SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
					String strDate = format.format(cal.getTime());
					return strDate;
				}
				return "";
			}

		});
		dateChooserThongKeTheoNgay = new JDateChooser();
		dateChooserThongKeTheoNgay.setDateFormatString("dd/MM/yyyy");
		dateChooserThongKeTheoNgay.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserThongKeTheoNgay.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserThongKeTheoNgay.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserThongKeTheoNgay.getCalendarButton().setBackground(new Color(102, 0, 153));

		dateChooserThongKeTheoNgay.setBounds(130, 121, 180, 38);
		pThongKe.add(dateChooserThongKeTheoNgay);


		//		ftfNgaySinh.setBounds(964, 62, 100, 25);
		//		ftfNgaySinh.setEditable(false);
		//		pMain.add(ftfNgaySinh);
		//		
		JButton btnLich=new JButton();
		btnLich.setBackground(Color.WHITE);
		btnLich.setBorder(new LineBorder(new Color(255, 255, 255), 5, true));
		btnLich.setIcon(new ImageIcon("data/img/lich1.png"));
		btnLich.setBounds(1072, 62, 26, 25);
		////////////////////////////////////////////////
		////////////////////////////////////////////////
		JLabel lblChonThang = new JLabel("Chọn tháng: ");
		lblChonThang.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonThang.setBounds(35, 215, 100, 38);
		pThongKe.add(lblChonThang);

		JLabel lblChonNamTh = new JLabel("Chọn năm: ");
		lblChonNamTh.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNamTh.setBounds(35, 273, 100, 36);
		pThongKe.add(lblChonNamTh);

		cbbThang = new JComboBox<String>();
		cbbThang.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbbThang.setBackground(Color.white);
		cbbThang.setBounds(130, 215, 180, 38);
		for(int i = 1; i <= 12;i++) {
			cbbThang.addItem(""+i);
		}
		pThongKe.add(cbbThang);

		cbbNamTh = new JComboBox<String>();
		cbbNamTh.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbbNamTh.setBackground(Color.white);
		cbbNamTh.setBounds(130, 273, 180, 38);
		for(int i = 2021; i > 2014; i--) {
			cbbNamTh.addItem(""+i);
		}
		pThongKe.add(cbbNamTh);

		JLabel lblChonNam = new JLabel("Chọn năm: ");
		lblChonNam.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNam.setBounds(35, 376, 100, 35);
		pThongKe.add(lblChonNam);

		cbbNam = new JComboBox<String>();
		cbbNam.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbbNam.setBackground(Color.white);
		cbbNam.setBounds(130, 376, 180, 38);
		for(int i = 2021; i > 2014; i--) {
			cbbNam.addItem(""+i);
		}
		pThongKe.add(cbbNam);

		btnTK = new FixButton("Thống kê");
		//		btnTK.setFont(new Font("SansSerif", Font.ITALIC, 25));
		btnTK.setForeground(Color.WHITE);
		btnTK.setFont(new Font("SansSerif", Font.BOLD, 20));
		//		btnTK.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTK.setBackground(new Color(114, 23, 153));
		btnTK.setBounds(10, 513, 300, 36);
		Image imgLamMoiKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconThongKe.png");
		Image resizeImgLamMoiKH = imgLamMoiKH.getScaledInstance(25, 25, 0);
		btnTK.setIcon(new ImageIcon(resizeImgLamMoiKH));
		pThongKe.add(btnTK);

		btnLamMoi = new FixButton("Làm mới");
		//		btnTK.setFont(new Font("SansSerif", Font.ITALIC, 25));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("SansSerif", Font.BOLD, 20));
		//		btnTK.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoi.setBackground(new Color(114, 23, 153));
		btnLamMoi.setBounds(10, 560, 300, 36);

		pThongKe.add(btnLamMoi);

		JLabel lblChonMuc = new JLabel("Chọn mục thống kê");
		lblChonMuc.setHorizontalAlignment(SwingConstants.CENTER);
		lblChonMuc.setForeground(Color.BLACK);
		lblChonMuc.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblChonMuc.setBounds(10, 11, 320, 41);
		pThongKe.add(lblChonMuc);

		/////////////////////////////////
		JPanel pTongDoanhThu = new JPanel();
		pTongDoanhThu.setBackground(new Color(238,239,243,90));
		pTongDoanhThu.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTongDoanhThu.setBounds(348, 62, 246, 100);
		pTongDoanhThu.setBackground(Color.WHITE);
		pMain.add(pTongDoanhThu);
		pTongDoanhThu.setLayout(null);

		JLabel lblTDT= new JLabel("  Tổng doanh thu ");
		lblTDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblTDT.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTDT.setForeground(new Color(148, 0, 211));
		lblTDT.setBounds(10, 72, 226, 17);
		pTongDoanhThu.add(lblTDT);

		btnTongDoanhThu = new FixButton("10,000,000đ");
		btnTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTongDoanhThu.setForeground(Color.BLACK);
		btnTongDoanhThu.setBackground(Color.WHITE);
		btnTongDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTongDoanhThu.setBounds(10, 9, 226, 52);
		pTongDoanhThu.add(btnTongDoanhThu);

		JPanel pSoKhachHang = new JPanel();
		pSoKhachHang.setBackground(new Color(238,239,243,90));
		pSoKhachHang.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pSoKhachHang.setBounds(604, 62, 184, 100);
		pSoKhachHang.setBackground(Color.WHITE);
		pMain.add(pSoKhachHang);
		pSoKhachHang.setLayout(null);

		JLabel lblSoKH= new JLabel("   Số khách hàng ");
		lblSoKH.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoKH.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblSoKH.setForeground(new Color(153, 50, 204));
		lblSoKH.setBounds(10, 72, 164, 17);
		pSoKhachHang.add(lblSoKH);

		btnSoKH = new FixButton("250");
		btnSoKH.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnSoKH.setForeground(Color.BLACK);
		btnSoKH.setBackground(Color.WHITE);
		btnSoKH.setBounds(10, 10, 164, 51);
		pSoKhachHang.add(btnSoKH);

		JPanel pSoMatHang = new JPanel();
		pSoMatHang.setBackground(new Color(238,239,243,90));
		pSoMatHang.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pSoMatHang.setBounds(798, 62, 188, 100);
		pSoMatHang.setBackground(Color.WHITE);
		pMain.add(pSoMatHang);
		pSoMatHang.setLayout(null);

		JLabel lblSoMH= new JLabel("   Số mặt hàng ");
		lblSoMH.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoMH.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblSoMH.setForeground(new Color(153, 50, 204));
		lblSoMH.setBounds(10, 72, 168, 17);
		pSoMatHang.add(lblSoMH);

		btnSoMH = new FixButton("300");
		btnSoMH.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnSoMH.setForeground(Color.BLACK);
		btnSoMH.setBackground(Color.WHITE);
		btnSoMH.setBounds(10, 10, 168, 51);
		pSoMatHang.add(btnSoMH);

		JPanel pTgPhongSD = new JPanel();
		pTgPhongSD.setBackground(new Color(238,239,243,90));
		pTgPhongSD.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTgPhongSD.setBounds(996, 62, 257, 100);
		pTgPhongSD.setBackground(Color.WHITE);
		pMain.add(pTgPhongSD);
		pTgPhongSD.setLayout(null);

		JLabel lblTGSD= new JLabel("Thời gian hoạt động phòng ");
		lblTGSD.setHorizontalAlignment(SwingConstants.CENTER);
		lblTGSD.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTGSD.setForeground(new Color(153, 50, 204));
		lblTGSD.setBounds(10, 72, 237, 17);
		pTgPhongSD.add(lblTGSD);

		btnTGHD = new FixButton("500 Giờ");
		btnTGHD.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTGHD.setForeground(Color.BLACK);
		btnTGHD.setBackground(Color.WHITE);
		btnTGHD.setBounds(10, 10, 237, 51);
		pTgPhongSD.add(btnTGHD);

		JLabel lblTenBieuDo = new JLabel("Biểu đồ tổng doanh thu");
		lblTenBieuDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTenBieuDo.setForeground(new Color(148, 0, 211));
		lblTenBieuDo.setFont(new Font("SansSerif", Font.ITALIC, 20));
		lblTenBieuDo.setBounds(348, 584, 905, 34);
		pMain.add(lblTenBieuDo);


		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, -23, 1281, 652);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		///Jchart



		///ActionListener
		btnTK.addActionListener(this);
		btnSoKH.addActionListener(this);
		rdoTKNg.addActionListener(this);
		rdoTKNam.addActionListener(this);
		rdoTKT.addActionListener(this);

		df = new DecimalFormat("###,### VNĐ");
	}
	//số tiền thuê
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
	//tong tien 1 hoa don
	public double tongTienCTHD(double tongTienThue,HoaDon hd) {
		double tong = tongTienThue;

		ArrayList<CTHD> lsCTHD = daoCTHD.getCTHDTheoMaHD(hd.getMaHoaDon());
		for(CTHD ct : lsCTHD) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ct.getMatHang().getMaMatHang());
			tong += mh.getGiaMatHang() * ct.getSoLuong();
		}

		return tong;
	}


	//load thông tin thống kê doanh

	//load thong ke doanh thu
	public void loadThongKeDoanhThu() {
		int ngayTK = dateChooserThongKeTheoNgay.getDate().getDate();
		int thangTK = dateChooserThongKeTheoNgay.getDate().getMonth();
		int namTK = dateChooserThongKeTheoNgay.getDate().getYear();
		ArrayList<HoaDon>  lstHD = daoHoaDon.getHDtheoNgay(new Date(namTK, thangTK, ngayTK));
		if(rdoTKNg.isSelected()) {
			double doanhThu = 0;
			for(HoaDon hd : lstHD) {
				String phuThu = hd.getPhuThu();
				Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
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



				double thanhTien = tongTienCTHD(tongTienThue, hd);


				thanhTien = thanhTien - hd.getGiamGia();
				doanhThu+= thanhTien;
			}



			btnTongDoanhThu.setText(df.format(doanhThu));


		}
	}

	//load thong tin thong ke khach hang
	public void loadThongKeKhachHang() {
		int ngayTK = dateChooserThongKeTheoNgay.getDate().getDate();
		int thangTK = dateChooserThongKeTheoNgay.getDate().getMonth();
		int namTK = dateChooserThongKeTheoNgay.getDate().getYear();
		if(rdoTKNg.isSelected()) {
			int dem = daoHoaDon.demSoKHTrongNgay(new Date(namTK, thangTK, ngayTK));
			btnSoKH.setText(dem+"");
		}
		if(rdoTKT.isSelected()) {
			
		}
	}

	//load thống kê số mặt hàng
	public void loadThongKeSoMatHang() {
		int ngayTK = dateChooserThongKeTheoNgay.getDate().getDate();
		int thangTK = dateChooserThongKeTheoNgay.getDate().getMonth();
		int namTK = dateChooserThongKeTheoNgay.getDate().getYear();
		if(rdoTKNg.isSelected()) {
			int dem = daoHoaDon.demSoMHTrongNgay(new Date(namTK, thangTK, ngayTK));
			btnSoMH.setText(dem+"");
		}
	}

	//load thống kê số giờ đã sử dụng cho các phòng
	public void loadThongKeSoGio() {
		int ngayTK = dateChooserThongKeTheoNgay.getDate().getDate();
		int thangTK = dateChooserThongKeTheoNgay.getDate().getMonth();
		int namTK = dateChooserThongKeTheoNgay.getDate().getYear();

		ArrayList<HoaDon>  lstHD = daoHoaDon.getHDtheoNgay(new Date(namTK, thangTK, ngayTK));
		if(rdoTKNg.isSelected()) {
			int thoiGianThongKe = 0;
			for(HoaDon hd: lstHD) {
				int gioVao = hd.getGioVao().getHours(),
						phutVao = hd.getGioVao().getMinutes();
				int gioRa = hd.getGioRa().getHours(),
						phutRa = hd.getGioRa().getMinutes();

				int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
				thoiGianThongKe += tongThoiGian;
			}
			btnTGHD.setText(thoiGianThongKe+"");
		}
	}
	
	/*
	 * private static JFreeChart createChart() { JFreeChart chart =
	 * ChartFactory.createBarChart(false, "", arg2, arg3, arg4, arg5, arg6, arg7);
	 * return chart; }
	 */

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(rdoTKNg)) {
			dateChooserThongKeTheoNgay.setEnabled(true);
			cbbNam.setEnabled(false);
			cbbNamTh.setEnabled(false);
			cbbThang.setEnabled(false);;
		}
		if(o.equals(rdoTKT)) {
			cbbThang.setEnabled(true);
			cbbNamTh.setEnabled(true);
			dateChooserThongKeTheoNgay.setEnabled(false);
			cbbNam.setEnabled(false);
		}
		if(o.equals(rdoTKNam)) {
			cbbNam.setEnabled(true);
			cbbThang.setEnabled(false);
			cbbNamTh.setEnabled(false);
			dateChooserThongKeTheoNgay.setEnabled(false);
		}
		if(o.equals(btnTK))
		{
			loadThongKeDoanhThu();
			loadThongKeKhachHang();
			loadThongKeSoMatHang();
			loadThongKeSoGio();
		}
	}

}


