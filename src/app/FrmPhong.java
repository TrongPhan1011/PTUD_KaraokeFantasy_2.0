package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

import connection.ConnectDB;
import dao.DAOLoaiMH;
import dao.DAOLoaiPhong;
import dao.DAOMatHang;
import dao.DAOPhatSinhMa;
import dao.DAOPhong;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiKH;
import entity.LoaiMatHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;


public class FrmPhong extends JFrame implements ActionListener, MouseListener, ItemListener,KeyListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sHeaderMaNV;
	private String sHeaderTenNV;
	private Panel pMain;
	private Date dNgayHienTai;
	private JTextField txtTK;
	private JTextField txtTenP;
	private JTextField txtGiaPhong;
	private JButton btnTim;
	private JButton btnThemP;
	private JButton btnXoaP;
	private JButton btnSuaP;
	private JButton btnReset;
	private JButton btnExcels;
	private JComboBox<String> cboLoaiP;
	private JComboBox<String> cboTinhTrangP;
	private JComboBox<String> cboSapXep;
	private JRadioButton rdoTheoMaP;
	private JRadioButton rdoTheoLoaiP;
	private JRadioButton rdoTheoGiaP;
	private JTable tblPhong;
	private DefaultTableModel modelPhong;
	private DAOPhong daoPhong;
	private DAOLoaiPhong daoLoaiP;
	private DAOPhatSinhMa daoMa;
	private DecimalFormat dfGiaP=new DecimalFormat("###,###");
	private Regex regex;
	private ArrayList<LoaiPhong> loaiP;
	private Phong p;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	private ButtonGroup bgRdo;

	public Panel getFrmPhong() {
		return this.pMain;
	}
	public FrmPhong(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		//Khai bao dao
		daoPhong = new DAOPhong();
		daoLoaiP = new DAOLoaiPhong();
		daoMa = new DAOPhatSinhMa();
		regex = new Regex();
		//giao dien
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

		
		JLabel lblLoaiP = new JLabel("Loại phòng:");
		lblLoaiP.setBounds(10, 76, 84, 26);
		pNhapThongTin.add(lblLoaiP);
		lblLoaiP.setFont(new Font("SansSerif", Font.PLAIN, 15));
		
		// Loai phong
		cboLoaiP = new JComboBox<String>();
		cboLoaiP.setBounds(132, 76, 191, 35);
		pNhapThongTin.add(cboLoaiP);
		cboLoaiP.setBackground(new Color(255, 255, 255));
		cboLoaiP.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cboLoaiP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));

		// tinh trang phong
		JLabel lblTinhTrangP = new JLabel("Tình trạng phòng:");
		lblTinhTrangP.setBounds(10, 144, 125, 26);
		pNhapThongTin.add(lblTinhTrangP);
		lblTinhTrangP.setFont(new Font("SansSerif", Font.PLAIN, 15));

		cboTinhTrangP = new JComboBox<String>();
		cboTinhTrangP.setBounds(132, 142, 191, 35);
		pNhapThongTin.add(cboTinhTrangP);
		cboTinhTrangP.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboTinhTrangP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboTinhTrangP.setBackground(Color.WHITE);
		String cbbTinhTrang[] = { "Trống", "Đang hoạt động", "Đã đặt" };
		for (int i = 0; i < cbbTinhTrang.length; i++) {
			cboTinhTrangP.addItem(cbbTinhTrang[i]);
		}
		cboTinhTrangP.setEnabled(false);
		
		//nhap gia phong
		txtGiaPhong = new JTextField();
		txtGiaPhong.setBounds(132, 277, 191, 35);
		pNhapThongTin.add(txtGiaPhong);
		txtGiaPhong.setBackground(new Color(255, 255, 255));
		txtGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtGiaPhong.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtGiaPhong.setColumns(20);
		
		JLabel lblGiaP = new JLabel("Giá phòng:");
		lblGiaP.setBounds(10, 278, 111, 26);
		pNhapThongTin.add(lblGiaP);
		lblGiaP.setFont(new Font("SansSerif", Font.PLAIN, 15));
		
		//ten Phong
		txtTenP = new JTextField();
		txtTenP.setBounds(132, 211, 191, 35);
		pNhapThongTin.add(txtTenP);
		txtTenP.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtTenP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtTenP.setEnabled(false);
		txtTenP.setColumns(30);
		
		JLabel lblTenPhong = new JLabel("Tên phòng: ");
		lblTenPhong.setBounds(10, 212, 102, 26);
		pNhapThongTin.add(lblTenPhong);
		lblTenPhong.setFont(new Font("SansSerif", Font.PLAIN, 15));


		// lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(350, 11, 90, 35);
		pMain.add(lblTim);

		// txtTK
		txtTK = new JTextField();
		txtTK.setText("Tìm theo mã phòng, loại phòng, tình trạng phòng");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		txtTK.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTK.setBounds(425, 11, 529, 33);
		txtTK.addFocusListener(new FocusAdapter() { // place holder
			@Override
			public void focusGained(FocusEvent e) {
				if (txtTK.getText().equals("Tìm theo mã phòng, loại phòng, tình trạng phòng")) {
					txtTK.setText("");
					txtTK.setFont(new Font("SansSerif", Font.PLAIN, 15));
					txtTK.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtTK.getText().equals("")) {
					txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
					txtTK.setText("Tìm theo mã phòng, loại phòng, tình trạng phòng");
					txtTK.setForeground(Colors.LightGray);
				}
			}
		});
		pMain.add(txtTK);

		// btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);

		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(964, 12, 127, 33);
		
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
		

		// nút thêm
		btnThemP = new FixButton("Thêm");
		btnThemP.setForeground(Color.WHITE);

		btnThemP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemP.setBackground(new Color(57, 210, 247));
		btnThemP.setBounds(10, 391, 313, 43);
		Icon iconThemP = IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 20, Color.white);
		btnThemP.setIcon(iconThemP);
		pNhapThongTin.add(btnThemP);
		
		//nút sửa
		btnSuaP = new FixButton("Sửa");
		btnSuaP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSuaP.setForeground(Color.WHITE);
		btnSuaP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaP.setBackground(new Color(133, 217, 191));
		btnSuaP.setBounds(10, 445, 313, 43);
		Icon iconSuaP = IconFontSwing.buildIcon(FontAwesome.WRENCH, 20, Color.white);
		btnSuaP.setIcon(iconSuaP);
		pNhapThongTin.add(btnSuaP);
		
		//nút xóa set loại phòng về ngưng hoạt động
		btnXoaP = new FixButton("Xóa");
		btnXoaP.setForeground(Color.WHITE);
		btnXoaP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoaP.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaP.setBackground(new Color(0xE91940));
		btnXoaP.setBounds(10, 499, 313, 43);
		Icon iconHuyP = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		btnXoaP.setIcon(iconHuyP);
		pNhapThongTin.add(btnXoaP);
		
		//nút làm mới
		btnReset = new FixButton("Làm mới");
		btnReset.setForeground(Color.WHITE);
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBackground(new Color(114, 23, 153));
		btnReset.setBounds(10, 553, 313, 43);
		Icon iconReset = IconFontSwing.buildIcon(FontAwesome.REFRESH, 20, Color.white);
		btnReset.setIcon(iconReset);
		pNhapThongTin.add(btnReset);
		//SapXep
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(171, 192, 238));
		pSapXep.setBounds(353, 51, 904, 47);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);
		
		//Sắp xếp tăng dần hoặc giảm dần
		cboSapXep = new JComboBox<String>();
		cboSapXep.setBounds(51, 12, 115, 28);
		cboSapXep.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBackground(Color.WHITE);
		String cbSort[] = { "Tăng dần", "Giảm dần" };
		for (int i = 0; i < cbSort.length; i++) {
			cboSapXep.addItem(cbSort[i]);
		}
		pSapXep.add(cboSapXep);

		//Group rdo giúp sắp xếp phòng
		rdoTheoMaP = new JRadioButton("Theo mã phòng");
		rdoTheoMaP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoMaP.setBounds(312, 15, 170, 27);
		rdoTheoMaP.setSelected(true);
		rdoTheoMaP.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoMaP.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoMaP);

		rdoTheoLoaiP = new JRadioButton("Theo loại phòng");
		rdoTheoLoaiP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoLoaiP.setBounds(518, 15, 170, 27);
		rdoTheoLoaiP.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiP.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoLoaiP);

		rdoTheoGiaP = new JRadioButton("Theo giá phòng ");
		rdoTheoGiaP.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rdoTheoGiaP.setBounds(718, 15, 135, 27);
		rdoTheoGiaP.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoGiaP.setBackground(new Color(171, 192, 238));
		pSapXep.add(rdoTheoGiaP);






		bgRdo=new ButtonGroup();
		bgRdo.add(rdoTheoMaP);
		bgRdo.add(rdoTheoLoaiP);
		bgRdo.add(rdoTheoGiaP);
		bgRdo.clearSelection();
		
		//tạo bảng
		//tên các cột trong bảng
		String phong [] = {"Mã phòng","Loại phòng", "Giá phòng", "Tình trạng phòng"};
		modelPhong = new DefaultTableModel(phong,0);

		tblPhong = new JTable(modelPhong);
		tblPhong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblPhong.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblPhong.setShowHorizontalLines(true);
		tblPhong.setShowGrid(true);
		tblPhong.setBackground(Color.WHITE);
		tblPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tblPhong.setSelectionBackground(new Color(164, 44, 167, 30));
		tblPhong.setSelectionForeground(new Color(114, 23, 153));
		tblPhong.setRowHeight(30);
		
		JTableHeader tbHeader = tblPhong.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		//thanh cuốn lên xuống
		JScrollPane spPhong = new JScrollPane(tblPhong, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPhong.setBounds(353, 104, 904, 514);
		spPhong.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		spPhong.setBackground(new Color(164, 44, 167));
		spPhong.getHorizontalScrollBar();
		pMain.add(spPhong);
		
		//chỉnh độ dài từng cột
		tblPhong.getColumnModel().getColumn(0).setPreferredWidth(240);
		tblPhong.getColumnModel().getColumn(1).setPreferredWidth(240);
		tblPhong.getColumnModel().getColumn(2).setPreferredWidth(240);
		tblPhong.getColumnModel().getColumn(3).setPreferredWidth(240);
		
		//Chữ canh lề trái, số canh lề phải
		DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		tblPhong.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		tblPhong.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblPhong.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblPhong.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
		
		///Background
		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		// load loai Phong
		loaiP = daoLoaiP.getAllLoaiPhong();
		for(LoaiPhong lp : loaiP) {
			cboLoaiP.addItem(lp.getTenLoaiPhong());
		}

		loadDanhSachPhong();
		//add actions
		tblPhong.addMouseListener(this);
		btnReset.addActionListener(this);
		btnThemP.addActionListener(this);
		btnSuaP.addActionListener(this);
		btnXoaP.addActionListener(this);
		btnTim.addActionListener(this);
		btnExcels.addActionListener(this);
		rdoTheoGiaP.addActionListener(this);
		rdoTheoLoaiP.addActionListener(this);
		rdoTheoMaP.addActionListener(this);
		cboSapXep.addActionListener(this);
		
		txtGiaPhong.addKeyListener(this);
		txtTK.addKeyListener(this);
		
		
	}
	// end giao dien

	//Lam moi danh sach
	public void clearTable() {
		while (tblPhong.getRowCount() > 0) {
			modelPhong.removeRow(0);
		}
	}

	//Load danh sach cac phong
	public void loadDanhSachPhong() {
		clearTable();
		ArrayList<Phong> lsP = daoPhong.getDanhSachPhong();
		for (Phong p : lsP) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
		}
	}
	
	/**
	 * load danh sách phòng
	 * @param lstP: danh sách phòng
	 */
	private void loadDanhSachPhong(ArrayList<Phong> lstP) {
		clearTable();
		for (Phong p : lstP) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
		}
	}

	/**
	 * load danh sách 1 phòng
	 * @param Phong p: thôgn tin phòng được chọn
	 */
	public void loadPhongDuocChon(Phong p) {
		LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
		modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
	}
	
	/**
	 * load danh sách phòng theo loại
	 * @param p2: danh sách các phong đã được lấy ra theo mã loại phòng
	 */
	private void loadDanhSachPhongTheoLoai(ArrayList<Phong> p2) {
		clearTable();
		String maLoai = daoLoaiP.getMaLoaiPTheoTen(txtTK.getText());

		ArrayList<Phong> lstName = daoPhong.getPhongTheoLoai(maLoai);
		for (Phong p : lstName) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
		}
	}
	
	/**
	 * load danh sách phòng theo mã
	 * @param lstP: danh sách các phòng đã được lấy ra theo mã phòng
	 */
	public void loadDanhSachPhongTheoMa(ArrayList<Phong> lstP) {
		clearTable();
		ArrayList<Phong> lsP = daoPhong.getPhongTheoMaP(txtTK.getText());
		for (Phong p : lsP) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
		}
	}
	
	/**
	 * load danh sách các phòng theo tình trạng
	 * @param lstP: danh sách các phòng đã đươc lấy ra theo tình trạng
	 */
	public void loadDanhSachPhongTheoTinhTrang(ArrayList<Phong> lstP) {
		clearTable();
		ArrayList<Phong> lsP = daoPhong.getPhongTheoTinhTrang(txtTK.getText());
		for (Phong p : lsP) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
		}
	}

	//Them phong
	/**
	 * Thêm Phòng vào trong danh sách
	 * kiểm tra giá phòng phải >0
	 * Xuất ra danh sách phòng
	 */
	public void themPhong() {
		if(regex.regexGiaP(txtGiaPhong)) {
			float giaP = Float.parseFloat(txtGiaPhong.getText());
			String maP = daoMa.getMaP();
			String tinhTrang = cboTinhTrangP.getSelectedItem().toString();
			LoaiPhong loaiP = new LoaiPhong(daoLoaiP.getMaLoaiPTheoTen(cboLoaiP.getSelectedItem().toString()));

			Phong p = new Phong(maP, tinhTrang, giaP, loaiP);

			daoPhong.themPhong(p);
			clearTable();
			loadPhongDuocChon(p);
			JOptionPane.showMessageDialog(this, "Thêm phòng thành công");
		}
	}

	//Sua thong tin phong
	public void suaThongTin() {
		int row = tblPhong.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin phòng này không?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				JTextField txtTam = new JTextField();
				String maP = modelPhong.getValueAt(row, 0).toString();
				double gia = Math.round(daoPhong.getPhongTheoMa(maP).getGiaPhong());
				txtTam.setText(String.valueOf(Math.round(gia)));
				if (regex.regexGiaP(txtTam)) {
					try {
						LoaiPhong loaiP = new LoaiPhong(daoLoaiP.getMaLoaiPTheoTen(cboLoaiP.getSelectedItem().toString()));
						double giaP = Double.parseDouble(txtGiaPhong.getText().toString());
						String tinhTrang = cboTinhTrangP.getSelectedItem().toString();
						Phong p = new Phong(maP, tinhTrang, giaP, loaiP);
						clearTable();
						daoPhong.suaThongTinPhong(p);
						loadPhongDuocChon(p);
						JOptionPane.showMessageDialog(this, "Thông tin phòng đã được sửa!", "Thông báo",
								JOptionPane.OK_OPTION);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Quên chỉnh sửa giá phòng!!", "Thông báo",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin phòng sửa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	//xoa phong 
	private boolean xoaPhong() {
		int row = tblPhong.getSelectedRow();
		if (row >= 0) {
			int cancel = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa phòng này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (cancel == JOptionPane.YES_OPTION && cboTinhTrangP.getSelectedIndex()==0) {
				String maP = tblPhong.getValueAt(row, 0).toString();
				try {
					modelPhong.removeRow(row);
					clearTable();
					daoPhong.huyP(maP);
					JOptionPane.showMessageDialog(null, "Đã xóa phòng!", "Thông báo", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "xóa phòng thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn lại và kiểm tra tinh trạng phòng");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thông tin phòng cần hủy!", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	//Tìm kiếm phòng
	private void findPhong() {
		ArrayList<Phong> lstP = null;
		String regexMaPhong = "((P|p)[0-9]{3})";
		if (!txtTK.getText().equals("") && !txtTK.getText().equals("Tìm theo mã phòng, loại phòng, tình trạng phòng")) {
			if(regex.regexTimPhong(txtTK)) {
				if (txtTK.getText().trim().matches(regexMaPhong)) {
					lstP = daoPhong.getPhongTheoMaP(txtTK.getText().trim());
					loadDanhSachPhongTheoMa(lstP);
				}
			if(regex.regexTimKiemLoaiPhong(txtTK)) {
					lstP = daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen(txtTK.getText()).trim());
					loadDanhSachPhongTheoLoai(lstP);
			}
			if(regex.regexTimKiemTinhTrang(txtTK)) {
					lstP = daoPhong.getPhongTheoTinhTrang(txtTK.getText());
					loadDanhSachPhongTheoTinhTrang(lstP);
			}
			if(lstP.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tìm kiếm phù hợp!");
				loadDanhSachPhong(lstP);
			}
			}
		}else {
			clearTable();
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}		
	

	//Làm mới
	public void resetAll() {
		txtGiaPhong.setText("");
		txtTenP.setText("");
		txtTK.setText("");
		cboLoaiP.setSelectedIndex(0);
		cboSapXep.setSelectedIndex(0);
		cboTinhTrangP.setSelectedIndex(0);
	}

	//Sap xep theo loai Phong tang dan
	public void sortLoaiPhongTangDan(Phong p) {
		clearTable();
		ArrayList<Phong> lsThuong= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng thường"));
		for (Phong lsp: lsThuong) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}

		ArrayList<Phong> lsTrung= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng trung"));
		for (Phong lsp: lsTrung) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}


		ArrayList<Phong> lsVip= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng VIP"));
		for (Phong lsp: lsVip) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}
	}

	//sap xep gia phong tang dan
	private void sortGiaPhongTangDan(Phong p)  {
		clearTable();
		ArrayList<Phong> lsPhong = daoPhong.sortTheoGiaPhong("");
		for (Phong lsp: lsPhong) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}
	}

	//sap xep ma Phong giam dan
	private void sortMaPhongGiamDan(Phong p) {
		clearTable();
		ArrayList<Phong> lsPhong = daoPhong.sortTheoMaPhong();
		for (Phong lsp: lsPhong) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}
	}

	//sap xep loai Phong giam dan
	public void sortLoaiPhongGiamDan(Phong p) {
		clearTable();
		ArrayList<Phong> lsVip= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng VIP"));
		for (Phong lsp: lsVip) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}

		ArrayList<Phong> lsTrung= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng trung"));
		for (Phong lsp: lsTrung) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}




		ArrayList<Phong> lsThuong= daoPhong.getPhongTheoLoai(daoLoaiP.getMaLoaiPTheoTen("Phòng thường"));
		for (Phong lsp: lsThuong) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}
	}

	//sap xep gia phog giam dan

	private void sortGiaPhongGiamDan(Phong p)  {
		clearTable();
		ArrayList<Phong> lsPhong = daoPhong.sortTheoGiaPhong("desc");
		for (Phong lsp: lsPhong) {
			LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(lsp.getLoaiPhong().getMaLoaiPhong());
			modelPhong.addRow(new Object[] {lsp.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(lsp.getGiaPhong()), lsp.getTinhTrangPhong() });
		}
	}
	private void xuatExcel() throws IOException {
		XuatExcels xuat = new XuatExcels();
		FileDialog fileDialog  = new FileDialog(this, "Xuất thông tin phòngng ra Excels", FileDialog.SAVE);
		fileDialog.setFile("Danh sách thông tin phòng");
		fileDialog .setVisible(true);
		String name = fileDialog.getFile();
		String fileName = fileDialog.getDirectory() + name;

		if (name == null) 
			return;
		
		if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xls")) 
			fileName += ".xlsx";
		
		xuat.xuatTable(tblPhong, "DANH SÁCH THÔNG TIN PHÒNG", fileName);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}
	@Override

	//Hien thi thong tin phong khi chon vao bang
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblPhong)) {
			int row = tblPhong.getSelectedRow();
			txtTenP.setText(modelPhong.getValueAt(row, 0).toString());
			cboLoaiP.setSelectedItem(modelPhong.getValueAt(row, 1));
			txtGiaPhong.setText(modelPhong.getValueAt(row, 2).toString());
			cboTinhTrangP.setSelectedItem(modelPhong.getValueAt(row, 3).toString());
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
		if(o.equals(btnReset)) {
			resetAll();
		}
		if(o.equals(btnThemP)) {
			themPhong();
		}
		if(o.equals(btnSuaP)) {
			suaThongTin();
		}
		if(o.equals(btnXoaP)) {
			xoaPhong();
		}
		if(cboSapXep.getSelectedItem()=="Tăng dần") {
			if(o.equals(rdoTheoMaP)) {
				loadDanhSachPhong();
			}
			if(o.equals(rdoTheoLoaiP)) {
				sortLoaiPhongTangDan(p);
			}
			if(o.equals(rdoTheoGiaP)) {
				sortGiaPhongTangDan(p);
			}
		}
		if(cboSapXep.getSelectedItem()=="Giảm dần") {
			if(o.equals(rdoTheoMaP)) {
				sortMaPhongGiamDan(p);
			}
			if(o.equals(rdoTheoLoaiP)) {
				sortLoaiPhongGiamDan(p);
			}
			if(o.equals(rdoTheoGiaP)) {
				sortGiaPhongGiamDan(p);
			}
		}
		if(o.equals(btnTim)) {
			findPhong();
		}
		if(o.equals(cboSapXep)) {
			bgRdo.clearSelection();
			clearTable();
		}
		if(o.equals(btnExcels)) {
			try {
				xuatExcel();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		if(o.equals(txtGiaPhong)&& key == KeyEvent.VK_ENTER ) {
			btnThemP.doClick();
		}
		else if(o.equals(txtTK)&& key == KeyEvent.VK_ENTER ) {
			btnTim.doClick();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
