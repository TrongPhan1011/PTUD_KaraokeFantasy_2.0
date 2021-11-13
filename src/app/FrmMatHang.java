package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;

import connection.ConnectDB;
import dao.DAOLoaiMH;
import dao.DAOMatHang;
import dao.DAOPhatSinhMa;
import dao.Regex;
import entity.LoaiMatHang;
import entity.MatHang;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;


public class FrmMatHang extends JPanel implements ActionListener, MouseListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel pMain;
	private JTextField txtSoLuong;
	private JTextField txtTenMH;
	private JTextField txtDonGia;
	private JTable tblMH;
	private DefaultTableModel modelMatHang;
	private FixButton btnTim;
	private JTextField txtTim;
	private FixButton btnThemKH;
	private FixButton btnSuaMH;
	private FixButton btnXoaMH;
	private FixButton btnReset;
	private DAOMatHang daoMH;
	private DAOLoaiMH daoLMH;
	private JComboBox<String> cboLoaiMH;
	private DAOPhatSinhMa daoPhatSinhMa;
	private JComboBox<Object> cboSapXep;
	private ArrayList<LoaiMatHang> loaiMH;
	private JCheckBox chkTatCa;
	private JRadioButton rdoTheoGiaMH;
	private JRadioButton rdoTheoLoaiMH;
	private JRadioButton rdoTheoTenMH;
	private DecimalFormat dfK;
	private DecimalFormat dfVND;
	private MatHang mh;
	private Regex regex;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	
	public Panel getFrmMatHang() {
		return this.pMain;
	}
	public FrmMatHang(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {
		
		setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		add(pMain);
		pMain.setLayout(null);
		
		regex = new Regex();
		daoMH = new DAOMatHang();
		daoLMH = new DAOLoaiMH();
		daoPhatSinhMa = new DAOPhatSinhMa();
		try {
			ConnectDB.getinstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 333, 607);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);
		//////////		
				JLabel lblTenMH = new JLabel("Tên mặt hàng: ");
				lblTenMH.setBounds(10, 83, 102, 36);
				pNhapThongTin.add(lblTenMH);
				lblTenMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
				
				txtTenMH = new JTextField();
				txtTenMH.setBounds(122, 82, 201, 37);
				pNhapThongTin.add(txtTenMH);
				txtTenMH.setBackground(new Color(255, 255, 255));
				txtTenMH.setFont(new Font("SansSerif", Font.PLAIN, 14));
				txtTenMH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				txtTenMH.setColumns(30);
				
				//-----
				JLabel lblDonGia = new JLabel("Giá bán:");
				lblDonGia.setBounds(10, 143, 102, 36);
				pNhapThongTin.add(lblDonGia);
				lblDonGia.setFont(new Font("SansSerif", Font.PLAIN, 15));
				
				txtDonGia = new JTextField();
				txtDonGia.setBounds(122, 143, 201, 37);
				pNhapThongTin.add(txtDonGia);
				txtDonGia.setBackground(new Color(255, 255, 255));
				txtDonGia.setFont(new Font("SansSerif", Font.PLAIN, 14));
				txtDonGia.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				txtDonGia.setColumns(20);
				
				lblNhapThongTin = new JLabel("Nhập thông tin mặt hàng");
				lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
				lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
				lblNhapThongTin.setBounds(10, 11, 292, 29);
				pNhapThongTin.add(lblNhapThongTin);
				//		//------
						JLabel lblSoluongMH = new JLabel("Số lượng:");
						lblSoluongMH.setBounds(10, 202, 84, 36);
						pNhapThongTin.add(lblSoluongMH);
						lblSoluongMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
						
						txtSoLuong = new JTextField();
						txtSoLuong.setBounds(122, 201, 201, 37);
						pNhapThongTin.add(txtSoLuong);
						txtSoLuong.setBackground(new Color(255, 255, 255));
						txtSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
						txtSoLuong.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
						txtSoLuong.setColumns(20);
						
						JLabel lblSubLMH = new JLabel("Loại mặt hàng: ");
						lblSubLMH.setBounds(10, 260, 102, 35);
						pNhapThongTin.add(lblSubLMH);
						lblSubLMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
						
						cboLoaiMH = new JComboBox<String>();
						cboLoaiMH.setBounds(122, 258, 201, 37);
						pNhapThongTin.add(cboLoaiMH);
						cboLoaiMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
						cboLoaiMH.setBackground(Color.WHITE);
		
//Tim Kiem
		//txtTim 
		txtTim = new JTextField();
		txtTim.setText("Tìm mặt hàng theo tên mặt hàng, loại mặt hàng");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23 ,153), 2, true));
		txtTim.setBounds(589, 11, 539, 33);
		txtTim.addFocusListener(new FocusAdapter() {	
			//place holder
			@Override
			public void focusGained(FocusEvent e) {
				if(txtTim.getText().equals("Tìm mặt hàng theo tên mặt hàng, loại mặt hàng")) {
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
					txtTim.setText("Tìm mặt hàng theo tên mặt hàng, loại mặt hàng");
				}
			}
		});
		pMain.add(txtTim);
		//lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(511, 11, 90, 35);
		pMain.add(lblTim);
				
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(1138, 11, 121, 33);
		Image imgTim = Toolkit.getDefaultToolkit().getImage("data\\img\\iconKinhLup.png");
		Image resizeImgTim = imgTim.getScaledInstance(20, 20, 0);
		btnTim.setIcon(new ImageIcon(resizeImgTim));
		pMain.add(btnTim);
		

/////Buttons
		btnThemKH = new FixButton("Thêm");
		btnThemKH.setForeground(Color.WHITE);
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemKH.setBackground(new Color(114, 43, 153));
		btnThemKH.setBounds(10, 385, 313, 42);
		Image imgThemKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconGrayThem.png");
		Image resizeImgThemKH = imgThemKH.getScaledInstance(25, 25, 0);
		btnThemKH.setIcon(new ImageIcon(resizeImgThemKH));
		pNhapThongTin.add(btnThemKH);
		
		btnSuaMH = new FixButton("Sửa");
		btnSuaMH.setForeground(Color.WHITE);
		btnSuaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaMH.setBackground(new Color(114, 43, 153));
		btnSuaMH.setBounds(10, 443, 313, 42);
		Image imgSuaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconTool.png");
		Image resizeImgSuaKH = imgSuaKH.getScaledInstance(25, 25, 0);
		btnSuaMH.setIcon(new ImageIcon(resizeImgSuaKH));
		pNhapThongTin.add(btnSuaMH);
		
		btnXoaMH = new FixButton("Xóa");
		btnXoaMH.setForeground(Color.WHITE);
		btnXoaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaMH.setBackground(new Color(114, 43, 153));
		btnXoaMH.setBounds(10, 499, 313, 42);
		Image imgXoaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconRemove.png");
		Image resizeImgXoaKH = imgXoaKH.getScaledInstance(25, 25, 0);
		btnXoaMH.setIcon(new ImageIcon(resizeImgXoaKH));
		pNhapThongTin.add(btnXoaMH);
		
		btnReset = new FixButton("Làm mới");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBackground(new Color(114, 43, 153));
		btnReset.setBounds(10, 552, 313, 44);
		Image imgLamMoiKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconReset.png");
		Image resizeImgLamMoiKH = imgLamMoiKH.getScaledInstance(25, 25, 0);
		btnReset.setIcon(new ImageIcon(resizeImgLamMoiKH));
		pNhapThongTin.add(btnReset);
//SapXep
		JPanel pSapXep = new JPanel();
		pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSapXep.setBackground(new Color(207, 195, 237));
		pSapXep.setBounds(350, 49, 909, 47);
		pMain.add(pSapXep);
		pSapXep.setLayout(null);
		
		cboSapXep = new JComboBox<Object>(new Object[]{"Tăng dần", "Giảm dần"});
		cboSapXep.setBounds(51, 12, 135, 28);
		cboSapXep.setFont(new Font("SansSerif", Font.BOLD, 15));
		cboSapXep.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboSapXep.setBackground(Color.WHITE);
		pSapXep.add(cboSapXep);
		
		rdoTheoTenMH = new JRadioButton("Theo tên mặt hàng");
		rdoTheoTenMH.setBounds(357, 13, 170, 27);
		rdoTheoTenMH.setSelected(true);
		rdoTheoTenMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoTenMH.setBackground(new Color(207, 195, 237));
		pSapXep.add(rdoTheoTenMH);
		
		rdoTheoLoaiMH = new JRadioButton("Theo loại mặt hàng");
		rdoTheoLoaiMH.setBounds(560, 13, 170, 27);
		rdoTheoLoaiMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoLoaiMH.setBackground(new Color(207, 195, 237));
		pSapXep.add(rdoTheoLoaiMH);
		
		rdoTheoGiaMH = new JRadioButton("Theo giá ");
		rdoTheoGiaMH.setBounds(768, 13, 135, 27);
		rdoTheoGiaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		rdoTheoGiaMH.setBackground(new Color(207, 195, 237));
		pSapXep.add(rdoTheoGiaMH);
		
		chkTatCa = new JCheckBox("Tất cả ");
		chkTatCa.setFont(new Font("SansSerif", Font.BOLD, 14));
		chkTatCa.setBackground(new Color(207, 195, 237));
		chkTatCa.setBounds(233, 13, 106, 27);
		pSapXep.add(chkTatCa);
		
		ButtonGroup bgRdo=new ButtonGroup();
		bgRdo.add(rdoTheoTenMH);
		bgRdo.add(rdoTheoLoaiMH);
		bgRdo.add(rdoTheoGiaMH);
		bgRdo.add(chkTatCa);
		bgRdo.clearSelection();
//Table
		String mh [] = {"Mã MH","Tên mặt hàng", "Loại MH", "Số lượng", "Giá bán"};
		modelMatHang = new DefaultTableModel(mh,0);
		
		tblMH = new JTable(modelMatHang);
		tblMH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblMH.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblMH.setShowHorizontalLines(true);
		tblMH.setShowGrid(true);
		tblMH.setBackground(Color.WHITE);
		tblMH.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblMH.setSelectionBackground(new Color(164, 44, 167, 30));
		tblMH.setSelectionForeground(new Color(114, 23, 153));
		tblMH.setRowHeight(30);
		tblMH.setSelectionBackground(new Color(164, 44, 167,30));
		
		JTableHeader tbHeader = tblMH.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		JScrollPane spMatHang = new JScrollPane(tblMH, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spMatHang.setBounds(353, 104, 906, 514);
		spMatHang.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		spMatHang.setBackground(new Color(164, 44, 167));
		spMatHang.getHorizontalScrollBar();
		pMain.add(spMatHang);
		
		tblMH.getColumnModel().getColumn(0).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(1).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(2).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(3).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(4).setPreferredWidth(230);
		
		DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		tblMH.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tblMH.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		spMatHang.setViewportView(tblMH);
///Background
		
		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		
		
/// Load loai MH
		loaiMH = daoLMH.getAllLoaiMatHang();
		for(LoaiMatHang lmh : loaiMH) {
			cboLoaiMH.addItem(lmh.getTenLoaiMatHang());
		}
////Action, Mouse
		btnThemKH.addActionListener(this);
		btnXoaMH.addActionListener(this);
		btnTim.addActionListener(this);
		btnSuaMH.addActionListener(this);
		btnReset.addActionListener(this);
		
		tblMH.addMouseListener(this);
		
		rdoTheoGiaMH.addActionListener(this);
		rdoTheoLoaiMH.addActionListener(this);
		rdoTheoTenMH.addActionListener(this);
		
		chkTatCa.addActionListener(this);
		
		rdoTheoGiaMH.addActionListener(this);
		rdoTheoLoaiMH.addActionListener(this);
		rdoTheoTenMH.addActionListener(this);
		
		cboSapXep.addActionListener(this);
//Dinh dang thap phan, VND
		dfK = new DecimalFormat("###,###");
		dfVND = new DecimalFormat("###,### VND");
	}
	/**
	 * Lấy dữ liệu từ SQL nạp vào bảng
	 */
	public void loadTableMH() {
		ArrayList<MatHang> lsMH = daoMH.getDSMatHang();
		for(MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(), mh.getSoLuongMatHang(), mh.getGiaMatHang() } );
		}
	}
	////dfK.format(Math.round(mh.getSoLuongMatHang())),dfVND.format(Math.round(mh.getGiaMatHang()))
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnReset)) {
			LamMoi();
		}else if(o.equals(btnThemKH)) {
			ThemMH();
		}else if(o.equals(btnXoaMH)) {
			XoaMH();
		}else if (o.equals(btnSuaMH)) {
			SuaMH();
		}else if(o.equals(chkTatCa)) {
			loadTableMH();
		}else if(o.equals(btnTim)) {
			timMH();
		}else if (cboSapXep.getSelectedItem() == "Tăng dần") {
			if(o.equals(rdoTheoGiaMH)) {
				sortGiaTangDan(mh);
			}else if (o.equals(rdoTheoLoaiMH)) {
				clearTable();
				sortLMHTangDan(mh);
			}else if (o.equals(rdoTheoTenMH)) {
				sortTenMHTangDan(mh); 
			} 
		}else if (cboSapXep.getSelectedItem() == "Giảm dần") {
			if(o.equals(rdoTheoGiaMH)) {
				sortGiaGiamDan(mh);
			}else if (o.equals(rdoTheoLoaiMH)) {
				sortLMHGiamDan(mh);
			}else if (o.equals(rdoTheoTenMH)) {
				sortTenMHGiamDan(mh);
			} 
		}
	}
	/**
	 * Xóa mặt hàng khỏi table và SQL
	 */
	public void XoaMH() {
		if (tblMH.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn mặt hàng cần xóa");
		}else {
			int r = tblMH.getSelectedRow();
			if(r > 0) {
			int del = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa? ", "Thông báo", JOptionPane.YES_NO_OPTION);
			String maMH = modelMatHang.getValueAt(r, 0).toString();
			if(del == JOptionPane.YES_OPTION) {
				if(daoMH.XoaMH(maMH))
				modelMatHang.removeRow(r);
				}
			}
		}
	}
	/**
	 * Thêm mặt hàng vào table và SQL
	 */
	public void ThemMH() {
		String maMH = daoPhatSinhMa.getMaMH();
		String tenMH = txtTenMH.getText();
		String loaiMH = cboLoaiMH.getSelectedItem().toString();
		String maLMH = daoLMH.getMaLoaiMHTheoTen(loaiMH);
		int soluong = Integer.parseInt(txtSoLuong.getText());
		double dongia = Double.parseDouble(txtDonGia.getText());
		MatHang mh = new MatHang(maMH, tenMH, soluong, dongia, new LoaiMatHang(maLMH));
		try {
			daoMH.ThemMH(mh);
		}catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Thêm mặt hàng thất bại!");
		}
		LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
		modelMatHang.addRow(new Object[] {mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(), mh.getSoLuongMatHang(), mh.getGiaMatHang() } );
		JOptionPane.showMessageDialog(this, "Thêm mặt hàng thành công!");
	}
	/**
	 * Sửa thông tin mặt hàng
	 */
	public void SuaMH() {
		int row = tblMH.getSelectedRow();
		try {
			String maMH = (String) tblMH.getValueAt(row, 0);
			String tenMH = txtTenMH.getText();
			String loaiMH = cboLoaiMH.getSelectedItem().toString();
			String maLMH = daoLMH.getMaLoaiMHTheoTen(loaiMH);
			int soluong = Integer.parseInt(txtSoLuong.getText());
			double dongia = Double.parseDouble(txtDonGia.getText());
			MatHang mh = new MatHang(maMH, tenMH, soluong, dongia, new LoaiMatHang(maLMH));  
			daoMH.updateMH(mh);
			clearTable();
			loadTableMH();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Mã mặt hàng không tồn tại.");
		}	
		
		JOptionPane.showMessageDialog(this, "Cập  nhật mặt hàng thành công! ");
	}
	/**
	 * Xóa bảng
	 */
	private void clearTable() {
		while(tblMH.getRowCount() > 0) {
			modelMatHang.removeRow(0);
		}
	}
	/**
	 * Làm mới dữ liệu nhập
	 */
	private void LamMoi() {
		txtTenMH.setText("");
			txtDonGia.setText("");
			txtSoLuong.setText("");
			txtTenMH.requestFocus();
	}
	private void sortTenMHTangDan(MatHang mh) {
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.getDSMatHang();
		Collections.sort(lstMH, new Comparator<MatHang>() {

			@Override
			public int compare(MatHang o1, MatHang o2) {
				return o1.getTenMatHang().compareTo(o2.getTenMatHang());
			}
		});
		for (MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	private void sortTenMHGiamDan(MatHang mh) {
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.getDSMatHang();
		Collections.sort(lstMH, new Comparator<MatHang>() {

			@Override
			public int compare(MatHang o1, MatHang o2) {
				return o2.getTenMatHang().compareTo(o1.getTenMatHang());
			}
		});
		for (MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	private void sortLMHGiamDan(MatHang mh){
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.sortLMH("DESC");
		for(MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	
	private void sortLMHTangDan(MatHang mh){
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.sortLMH("ASC");
		for(MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	/**
	 *	
	 * @param mh
	 */
	private void sortGiaTangDan(MatHang mh){
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.sortGia("ASC");
		for(MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	private void sortGiaGiamDan(MatHang mh){
		clearTable();
		ArrayList<MatHang> lstMH = daoMH.sortGia("DESC");
		for(MatHang infoMH : lstMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(infoMH.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {infoMH.getMaMatHang(), infoMH.getTenMatHang(), lMH.getTenLoaiMatHang(), infoMH.getSoLuongMatHang(), infoMH.getGiaMatHang() } );
		}
	}
	private void timMH() {
		String info = txtTim.getText().toLowerCase().trim();
		ArrayList<MatHang> mh1 = daoMH.getTenMH(info);
		ArrayList<MatHang> mh2 = daoMH.getLMH(info);
		if(!info.equals("") && !info.equals("Tìm mặt hàng theo tên mặt hàng, loại mặt hàng")) {
			if(regex.regexTenMH(txtTim)) {
				try {
					loadTenMH(mh1);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy tên mặt hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}if(regex.regexTimKiemLoaiMatHang(txtTim)) {
				try {
					loadLoaiMH(mh2);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Không tìm thấy loại mặt hàng!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}
		}else
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	}
	
	public void loadTenMH(ArrayList<MatHang> mh1) {
		clearTable();
		ArrayList<MatHang> lsMH = daoMH.getTenMH(txtTim.getText());
		for(MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(), mh.getSoLuongMatHang(), mh.getGiaMatHang() } );
		}
	}
	
	public void loadLoaiMH(ArrayList<MatHang> mh1) {
		clearTable();
		String maLoai = daoLMH.getMaLoaiMHTheoTen(txtTim.getText());
		ArrayList<MatHang> lsMH = daoMH.getLMH(maLoai);
		for(MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] {mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(), mh.getSoLuongMatHang(), mh.getGiaMatHang() } );
		}
	}
	
	/**
	 * Sự kiện click chuột
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if(o.equals(tblMH)) {
			int row = tblMH.getSelectedRow();	
			txtTenMH.setText(modelMatHang.getValueAt(row, 1).toString());
			txtSoLuong.setText(modelMatHang.getValueAt(row, 3).toString());
			txtDonGia.setText(modelMatHang.getValueAt(row, 4).toString());
			cboLoaiMH.setSelectedItem(modelMatHang.getValueAt(row, 2).toString());
		
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
