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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.DateUtil;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOCTHD;
import dao.DAOHoaDon;
import dao.DAOKhachHang;
import dao.DAOLoaiKH;
import dao.DAOMatHang;
import dao.DAOPhong;
import entity.CTHD;
import entity.HoaDon;
import entity.KhachHang;
import entity.MatHang;
import entity.NhanVien;
import entity.Phong;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;



public class FrmThongKe extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV;
	private String sHeaderTenNV;
	private Panel pMain;
	private Date dNgayHienTai;
	private JButton btnTK;
	private JButton btnTongDoanhThu;
	private JButton btnTGHD;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private SpringLayout springLayout;
	private FixButton btnLamMoi;
	private JDateChooser dateChooserThongKeNgayBatDau;
	private JDateChooser dateChooserThongKeNgayKetThuc;
	private DAOCTHD daoCTHD;
	private DAOMatHang daoMatHang;
	private DAOHoaDon daoHoaDon;
	private DAOPhong daoPhong;
	private DAOKhachHang daoKH;
	private JButton btnExcels;
	private DecimalFormat df;
	private SimpleDateFormat sf;
	private JPanel pBieuDo;

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
		daoKH = new DAOKhachHang();

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


		JLabel lblHeaderDate = new JLabel("   Thời gian thống kê:");
		lblHeaderDate.setForeground(Color.BLACK);
		lblHeaderDate.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblHeaderDate.setBounds(334, 0, 221, 41);
		pMain.add(lblHeaderDate);

		JLabel lblNgayHienTai = new JLabel(ngay+" / "+thang+" / "+nam);
		lblNgayHienTai.setForeground(Color.BLACK);
		lblNgayHienTai.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblNgayHienTai.setBounds(521, -2, 151, 41);
		pMain.add(lblNgayHienTai);
		/////////////////////////////////////------------------------------------------
		JPanel pThongKe = new JPanel();
		pThongKe.setBackground(new Color(238,239,243,90));
		pThongKe.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pThongKe.setBounds(10, 36, 328, 200);
		pThongKe.setBackground(Color.WHITE);
		pMain.add(pThongKe);
		pThongKe.setLayout(null);

		// Bieu do
		pBieuDo = new JPanel();
		pBieuDo.setBackground(new Color(238,239,243,90));
		pBieuDo.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pBieuDo.setBounds(10, 244, 1243, 374);
		pBieuDo.setBackground(Color.WHITE);
		pMain.add(pBieuDo);
		pBieuDo.setLayout(null);
		

		ButtonGroup bg = new ButtonGroup();
		bg.clearSelection();

		JLabel lblChonNgay = new JLabel("Ngày bắt đầu:");
		lblChonNgay.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNgay.setBounds(25, 15, 100, 36);
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
			 *  thêm table ở đâu v
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
		dateChooserThongKeNgayBatDau = new JDateChooser();
		dateChooserThongKeNgayBatDau.setDateFormatString("dd/MM/yyyy");
		//dateChooserThongKeTheoNgay.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserThongKeNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserThongKeNgayBatDau.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserThongKeNgayBatDau.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayBatDau.setIcon((ImageIcon) iconCalendar);

		dateChooserThongKeNgayBatDau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayBatDau.setBounds(130, 15, 180, 38);
		pThongKe.add(dateChooserThongKeNgayBatDau);


		//		ftfNgaySinh.setBounds(964, 62, 100, 25);
		//		ftfNgaySinh.setEditable(false);
		//		pMain.add(ftfNgaySinh);
		//		


		btnTK = new FixButton("Thống kê");

		btnTK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//		btnTK.setFont(new Font("SansSerif", Font.ITALIC, 25));
		btnTK.setForeground(Color.WHITE);
		btnTK.setFont(new Font("SansSerif", Font.BOLD, 20));
		//		btnTK.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTK.setBackground(new Color(114, 23, 153));
		btnTK.setBounds(10, 112, 300, 36);
//		Image imgLamMoiKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconThongKe.png");
//		Image resizeImgLamMoiKH = imgLamMoiKH.getScaledInstance(25, 25, 0);
//		btnTK.setIcon(new ImageIcon(resizeImgLamMoiKH));
		Icon iconChart = IconFontSwing.buildIcon(FontAwesome.BAR_CHART, 20, Color.white);
		btnTK.setIcon(iconChart);
		pThongKe.add(btnTK);

		btnLamMoi = new FixButton("Làm mới");

		btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//		btnTK.setFont(new Font("SansSerif", Font.ITALIC, 25));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("SansSerif", Font.BOLD, 20));
		//		btnTK.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoi.setBackground(new Color(114, 23, 153));
		btnLamMoi.setBounds(10, 153, 300, 36);
		Icon iconLamMoi = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnLamMoi.setIcon(iconLamMoi);
		pThongKe.add(btnLamMoi);
		Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);
		
		dateChooserThongKeNgayKetThuc = new JDateChooser();
		dateChooserThongKeNgayKetThuc.getCalendarButton().setPreferredSize(new Dimension(30, 24));

		dateChooserThongKeNgayKetThuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayKetThuc.getCalendarButton().setBackground(new Color(102, 0, 153));
		dateChooserThongKeNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		Icon iconCalendar1 = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayKetThuc.setIcon((ImageIcon) iconCalendar1);
		dateChooserThongKeNgayKetThuc.setDateFormatString("dd/MM/yyyy");
		dateChooserThongKeNgayKetThuc.setBounds(130, 60, 180, 38);
		pThongKe.add(dateChooserThongKeNgayKetThuc);
		
		JLabel lblNgyKtThc = new JLabel("Ngày kết thúc:");
		lblNgyKtThc.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNgyKtThc.setBounds(25, 62, 100, 36);
		pThongKe.add(lblNgyKtThc);

		/////////////////////////////////
		JPanel pTongDoanhThu = new JPanel();
		pTongDoanhThu.setBackground(new Color(238,239,243,90));
		pTongDoanhThu.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTongDoanhThu.setBounds(348, 36, 440, 200);
		pTongDoanhThu.setBackground(Color.WHITE);
		pMain.add(pTongDoanhThu);
		pTongDoanhThu.setLayout(null);

		JLabel lblTDT= new JLabel("  Tổng doanh thu ");
		lblTDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblTDT.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTDT.setForeground(new Color(148, 0, 211));
		lblTDT.setBounds(107, 172, 226, 17);
		pTongDoanhThu.add(lblTDT);

		btnTongDoanhThu = new FixButton("");

		btnTongDoanhThu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTongDoanhThu.setForeground(Color.BLACK);
		btnTongDoanhThu.setBackground(Color.WHITE);
		btnTongDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTongDoanhThu.setBounds(107, 109, 226, 52);
		pTongDoanhThu.add(btnTongDoanhThu);
		
		JLabel lblDollar = new JLabel("$");
		lblDollar.setFont(new Font("SansSerif", Font.BOLD, 60));
		lblDollar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDollar.setForeground(Color.ORANGE);
		lblDollar.setBounds(107, 11, 226, 87);
		pTongDoanhThu.add(lblDollar);
		Icon iconSoKH = IconFontSwing.buildIcon(FontAwesome.USERS, 60, new Color(0, 172, 234));
		Icon iconSoMH = IconFontSwing.buildIcon(FontAwesome.GLASS, 60, Colors.LightGray);

		JPanel pTgPhongSD = new JPanel();
		pTgPhongSD.setBackground(new Color(238,239,243,90));
		pTgPhongSD.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTgPhongSD.setBounds(798, 36, 455, 200);
		pTgPhongSD.setBackground(Color.WHITE);
		pMain.add(pTgPhongSD);
		pTgPhongSD.setLayout(null);

		JLabel lblTGSD= new JLabel("Thời gian hoạt động phòng ");
		lblTGSD.setHorizontalAlignment(SwingConstants.CENTER);
		lblTGSD.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTGSD.setForeground(new Color(153, 50, 204));
		lblTGSD.setBounds(109, 172, 237, 17);
		pTgPhongSD.add(lblTGSD);

		btnTGHD = new FixButton("");

		btnTGHD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTGHD.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTGHD.setForeground(Color.BLACK);
		btnTGHD.setBackground(Color.WHITE);
		btnTGHD.setBounds(109, 109, 237, 51);
		pTgPhongSD.add(btnTGHD);
		
		JLabel lblIconTGHoatDong = new JLabel();
		lblIconTGHoatDong.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconTGHoatDong.setBounds(109, 11, 237, 87);
		Icon iconTGHoatDong = IconFontSwing.buildIcon(FontAwesome.MUSIC, 60, Color.red);
		lblIconTGHoatDong.setIcon(iconTGHoatDong);
		pTgPhongSD.add(lblIconTGHoatDong);


		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, -23, 1281, 652);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		
		btnExcels = new FixButton("Xuất Excels");
		btnExcels.setBounds(1128, 10, 125, 23);
		pMain.add(btnExcels);
		btnExcels.setForeground(Color.WHITE);
		btnExcels.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnExcels.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnExcels.setBackground(new Color(16, 124, 65));
		btnExcels.setIcon(iconExcel);
		///Jchart
		
	
		


		///ActionListener
		btnTK.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTongDoanhThu.addActionListener(this);
		btnTGHD.addActionListener(this);

		df = new DecimalFormat("###,### VNĐ");
		sf = new SimpleDateFormat("dd/MM/yyy");
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
	public double tongDoanhThu(ArrayList<HoaDon> lstHD) {
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
		return doanhThu;
	}


	//load thông tin thống kê doanh

	//load thong ke doanh thu
	public void loadThongKeDoanhThu() {
		
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		
		@SuppressWarnings("deprecation")
		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			
			ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
			double doanhThu = tongDoanhThu(lstHD);
			btnTongDoanhThu.setText(df.format(doanhThu));
			
		}
		else JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
	}

	public void xuatDoanhThu(ArrayList<HoaDon> lsHD,String path) throws IOException {
		Workbook workbook = null;
		 
        if (path.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("DSDT"); 
 
        int rowIndex = 1;
         
        Row title = sheet.createRow(rowIndex);
        
        Cell cellTitle = title.createCell(0,CellType.STRING);
        cellTitle.setCellValue("DANH SÁCH DOANH THU");
        rowIndex++;
        Row headerRow = sheet.createRow(rowIndex);
        Cell cMaNgay = headerRow.createCell(0,CellType.STRING);
        cMaNgay.setCellValue("Ngày");
        
        Cell cMaDT = headerRow.createCell(1,CellType.STRING);
        cMaDT.setCellValue("Tổng tiền");

        
        rowIndex++; 
   
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		@SuppressWarnings("deprecation")
		Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
		
		for(int i = 0;i<=noDay;i++) {
			ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
			double tongtien =0;
			for(HoaDon hd : ls) {
				if(hd != null) {
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
					tongtien+= thanhTien;
				}	
			}
			Row row = sheet.createRow(rowIndex);
	        Cell cRowNgayLap = row.createCell(0,CellType.STRING);
	        cRowNgayLap.setCellValue(sf.format(ngayden));
	        Cell cRowDT = row.createCell(1, CellType.STRING);
	        cRowDT.setCellValue(df.format(tongtien));
			rowIndex++;
	        
	        Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
			ngayden= ngayMoi;

		}	
        
        File f = new File(path);
         try {
        	
        		 FileOutputStream out = new FileOutputStream(f);
        		 workbook.write(out);
				
        		 out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Lưu không thành công!\n Tên file đã tồn tại");
		}
	}
	
	public void xuatSoGio(ArrayList<HoaDon> lsHD,String path) throws IOException {
		Workbook workbook = null;
		 
        if (path.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("DSGio"); 
 
        int rowIndex = 1;
         
        Row title = sheet.createRow(rowIndex);
        
        Cell cellTitle = title.createCell(0,CellType.STRING);
        cellTitle.setCellValue("DANH SÁCH SỐ GIỜ HOẠT ĐỘNG");
        rowIndex++;
        Row headerRow = sheet.createRow(rowIndex);
        Cell cMaPhong = headerRow.createCell(0,CellType.STRING);
        cMaPhong.setCellValue("Tên Phòng");
        
        Cell cMaGio = headerRow.createCell(1,CellType.STRING);
        cMaGio.setCellValue("Số giờ");

        
        rowIndex++; 
   
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		@SuppressWarnings("deprecation")
		Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
		for(int i = 0;i<=noDay;i++) {
			ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
			int count =0;
			String tenP = "";
			for(HoaDon hd :ls) {
				if(hd!=null) {
					 tenP = hd.getPhong().getMaPhong().toString();
					//Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
					int gioVao = hd.getGioVao().getHours(),
							phutVao = hd.getGioVao().getMinutes();
					int gioRa = hd.getGioRa().getHours(),
							phutRa = hd.getGioRa().getMinutes();

					int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
					count += tongThoiGian;
				}
				}	
			Row row = sheet.createRow(rowIndex);
	        Cell cRowMaP = row.createCell(0,CellType.STRING);
	        cRowMaP.setCellValue(tenP);
	        Cell cRowSoGio = row.createCell(1, CellType.STRING);
	        cRowSoGio.setCellValue(count);
			rowIndex++;
			
			Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
			ngayden= ngayMoi;
		}	
        
        File f = new File(path);
         try {
        	
        		 FileOutputStream out = new FileOutputStream(f);
        		 workbook.write(out);
				
        		 out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Lưu không thành công!\n Tên file đã tồn tại");
		}
	}


	//load thống kê số giờ đã sử dụng cho các phòng
	public void loadThongKeSoGio() {
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		
		@SuppressWarnings("deprecation")
		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
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
	public void resetAll() {
		dateChooserThongKeNgayBatDau.setDate(dNow);
		dateChooserThongKeNgayKetThuc.setDate(dNow);
		btnTongDoanhThu.setText("");
		btnTGHD.setText("");
	}

	public void addChart() throws RemoteException {
		if(!btnTongDoanhThu.getText().equalsIgnoreCase("")) {
			pBieuDo.removeAll();
			pBieuDo.revalidate();
			pBieuDo.repaint();
			ChartPanel chartPanel = new ChartPanel(createChart());
			chartPanel.setLocation(10, 11);
			chartPanel.setSize(1223, 352);
	        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
	        pBieuDo.add(chartPanel);
	 	}else JOptionPane.showMessageDialog(this, "Vui lòng thống kê trước khi xem biểu đồ");
	}
	
	private JFreeChart createChart() throws RemoteException {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ thống kê doanh thu",
                "Ngày", "Tổng tiền",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        
        CategoryAxis domainAxis = plot.getDomainAxis();
		
		  domainAxis.setCategoryLabelPositions(
				  CategoryLabelPositions.UP_45);
		//  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0) );
        return barChart;
	}
	 
	 public CategoryDataset createDataset() throws RemoteException {
		 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		 
			java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
			java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
			@SuppressWarnings("deprecation")
			Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
			@SuppressWarnings("deprecation")
			Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
			long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
			for(int i = 0;i<=noDay;i++) {
				ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
				double tongtien =0;
				for(HoaDon hd : ls) {
					if(hd != null) {
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
						tongtien+= thanhTien;
					}	
				}
				
				dataset.addValue(tongtien, "Số tiền (VNĐ)", sf.format(ngayden));
				Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
				ngayden= ngayMoi;
			}	

	        return dataset;
	}
	 
	
	 
	
	 public void addChartGio() throws RemoteException {
			if(!btnTGHD.getText().equalsIgnoreCase("")) {
				pBieuDo.removeAll();
				pBieuDo.revalidate();
				pBieuDo.repaint();
				ChartPanel chartPanel = new ChartPanel(createChartGio());
				chartPanel.setLocation(10, 20);
				chartPanel.setSize(1200, 250);
		        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		        pBieuDo.add(chartPanel);
		 	}else JOptionPane.showMessageDialog(this, "Vui lòng thống kê trước khi xem biểu đồ");
		}
		
		private JFreeChart createChartGio() throws RemoteException {
			  JFreeChart chart = ChartFactory.createPieChart(
		                "Biểu đồ thống kê giờ hoạt động mỗi phòng",  createDatasetGio(), true, true, true);

	        return chart;
		}
		 
		 public PieDataset  createDatasetGio() throws RemoteException {
			 final DefaultPieDataset  dataset = new DefaultPieDataset ();
			
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
				@SuppressWarnings("deprecation")
				Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				@SuppressWarnings("deprecation")
				Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
				for(int i = 0;i<=noDay;i++) {
					ArrayList<HoaDon> ls = daoHoaDon.getHDtheoNgay(ngayden);
					int count =0;
					String tenP = "";
					for(HoaDon hd :ls) {
						if(hd!=null) {
							 tenP = hd.getPhong().getMaPhong().toString();
							//Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
							int gioVao = hd.getGioVao().getHours(),
									phutVao = hd.getGioVao().getMinutes();
							int gioRa = hd.getGioRa().getHours(),
									phutRa = hd.getGioRa().getMinutes();

							int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
							count += tongThoiGian;
						}
						}	
					dataset.setValue(tenP, count);
					Date ngayMoi = new Date(ngayden.getYear(), ngayden.getMonth(), ngayden.getDate()+1);
					ngayden= ngayMoi;
				}	

		        return dataset;
		}
		 
			public  void xuatExcels() throws IOException {
				
				
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
				
				@SuppressWarnings("deprecation")
				Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				@SuppressWarnings("deprecation")
				Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				ArrayList<HoaDon> lsHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
				FileDialog fileDialog  = new FileDialog(this,"Xuất hóa đơn ra Excels",FileDialog.SAVE);
				fileDialog.setFile("*.xlsx");
				fileDialog .setVisible(true);
				String name = fileDialog.getFile();
				String fileName = fileDialog.getDirectory() + name;

				if (name == null) {
					return;
				}
				
				if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) {
					fileName += ".xlsx";
				}
				xuatDoanhThu(lsHD, fileName);
				
					
					
			}
public  void xuatExcelsSoGio() throws IOException {
				
				
				java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
				java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
				
				@SuppressWarnings("deprecation")
				Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
				@SuppressWarnings("deprecation")
				Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
				ArrayList<HoaDon> lsHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
				FileDialog fileDialog  = new FileDialog(this,"Xuất hóa đơn ra Excels",FileDialog.SAVE);
				fileDialog.setFile("*.xlsx");
				fileDialog .setVisible(true);
				String name = fileDialog.getFile();
				String fileName = fileDialog.getDirectory() + name;

				if (name == null) {
					return;
				}
				
				if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) {
					fileName += ".xlsx";
				}
				xuatSoGio(lsHD, fileName);
					
					
			}
		 

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();

		if(o.equals(btnTK))
		{
			java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
			java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
			@SuppressWarnings("deprecation")
			Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
			@SuppressWarnings("deprecation")
			Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
			long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
			if(noDay < 32 ) {
				loadThongKeDoanhThu();
				loadThongKeSoGio();
			}
			JOptionPane.showMessageDialog(null, "Chỉ được chọn trong khoảng thời gian 1 tháng");

		}
		if(o.equals(btnLamMoi))
			resetAll();
		if(o.equals(btnTongDoanhThu))
		{
			try {
				addChart();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				xuatExcels();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(o.equals(btnTGHD)) {
			try {
				addChartGio();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				xuatExcelsSoGio();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}


	}
}


