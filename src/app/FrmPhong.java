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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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


public class FrmPhong extends JPanel implements ActionListener, MouseListener, ItemListener  {

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
	private JComboBox<String> cboLoaiP;
	private JComboBox<String> cboTinhTrangP;
	private JComboBox<String> cboSapXep;
	private JRadioButton rdoTheoMaP;
	private JRadioButton rdoTheoLoaiP;
	private JRadioButton rdoTheoGiaP;
	private JCheckBox chkAll = new JCheckBox("Tất cả");
	private JTable tblPhong;
	private DefaultTableModel modelPhong;
	private DAOPhong daoPhong;
	private DAOLoaiPhong daoLoaiP;
	private DAOPhatSinhMa daoMa;
	private DecimalFormat dfGiaP=new DecimalFormat("###,###");
	private Regex regex;

	private ArrayList<LoaiPhong> loaiP;
	private Phong p;
	
	
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
		
		JLabel lblQuanLyKH = new JLabel("Quản lý phòng");
		lblQuanLyKH.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblQuanLyKH.setBounds(31, 11, 251, 33);
		pMain.add(lblQuanLyKH);

		// lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(310, 13, 90, 35);
		pMain.add(lblTim);

		// txtTK
		txtTK = new JTextField();
		txtTK.setText("Tìm theo mã phòng, loại phòng, tình trạng phòng");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		txtTK.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTK.setBounds(400, 12, 526, 33);
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
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(942, 11, 98, 33);
		Image imgTim = Toolkit.getDefaultToolkit().getImage("data\\img\\iconKinhLup.png");
		Image resizeImgTim = imgTim.getScaledInstance(20, 20, 0);
		btnTim.setIcon(new ImageIcon(resizeImgTim));
		pMain.add(btnTim);
		
		//imgNhac1
				JLabel lblNhac1=new JLabel("");
				lblNhac1.setBounds(25, 105, 120, 135);
				Image imgNhac1 = Toolkit.getDefaultToolkit().getImage("data\\img\\IconNhac1.png");
				Image resizeNhac1 = imgNhac1.getScaledInstance(lblNhac1.getWidth(), lblNhac1.getHeight(), 0);
				lblNhac1.setIcon(new ImageIcon(resizeNhac1));
				pMain.add(lblNhac1);
				
		//imgNhac2
				JLabel lblNhac2=new JLabel("");
				lblNhac2.setBounds(1100, 105, 105, 159);
				Image imgNhac2 = Toolkit.getDefaultToolkit().getImage("data\\img\\IconNhac2.png");
				Image resizeNhac2 = imgNhac2.getScaledInstance(lblNhac2.getWidth(), lblNhac2.getHeight(), 0);
				lblNhac2.setIcon(new ImageIcon(resizeNhac2));
				pMain.add(lblNhac2);

				JLabel lblTenPhong = new JLabel("Tên phòng: ");
				lblTenPhong.setFont(new Font("SansSerif", Font.PLAIN, 15));
				lblTenPhong.setBounds(217, 59, 102, 26);
				pMain.add(lblTenPhong);
				
				txtTenP = new JTextField();
				txtTenP.setBackground(new Color(255, 255, 255));
				txtTenP.setFont(new Font("SansSerif", Font.PLAIN, 14));
				txtTenP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				txtTenP.setBounds(329, 58, 310, 30);
				pMain.add(txtTenP);
				txtTenP.setEditable(isDisplayable());
				txtTenP.setColumns(30);
				
				//-----
				JLabel lblGiaP = new JLabel("Giá phòng:");
				lblGiaP.setFont(new Font("SansSerif", Font.PLAIN, 15));
				lblGiaP.setBounds(217, 99, 130, 26);
				pMain.add(lblGiaP);
				
				txtGiaPhong = new JTextField();
				txtGiaPhong.setBackground(new Color(255, 255, 255));
				txtGiaPhong.setFont(new Font("SansSerif", Font.PLAIN, 14));
				txtGiaPhong.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				txtGiaPhong.setBounds(329, 98, 310, 30);
				pMain.add(txtGiaPhong);
				txtGiaPhong.setColumns(20);
//				//------
				JLabel lblLoaiP = new JLabel("Loại phòng:");
				lblLoaiP.setFont(new Font("SansSerif", Font.PLAIN, 15));
				lblLoaiP.setBounds(654, 59, 84, 26);
				pMain.add(lblLoaiP);
				
				cboLoaiP = new JComboBox<String>();
				cboLoaiP.setBackground(new Color(255, 255, 255));
				cboLoaiP.setFont(new Font("SansSerif", Font.PLAIN, 14));
				cboLoaiP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				cboLoaiP.setBounds(766, 58, 310, 30);
				pMain.add(cboLoaiP);
				//txtSoLuong.setColumns(20);
				
				JLabel lblSubLMH = new JLabel("Tình trạng phòng:");
				lblSubLMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
				lblSubLMH.setBounds(654, 99, 102, 26);
				pMain.add(lblSubLMH);
				
				cboTinhTrangP = new JComboBox<String>();
				cboTinhTrangP.setFont(new Font("SansSerif", Font.PLAIN, 15));
				cboTinhTrangP.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
				cboTinhTrangP.setBackground(Color.WHITE);
				cboTinhTrangP.setBounds(766, 97, 310, 30);
				String cbbTinhTrang[] = { "Đã đặt", "Đang hoạt động", "Trống" };
				for (int i = 0; i < cbbTinhTrang.length; i++) {
					cboTinhTrangP.addItem(cbbTinhTrang[i]);
				}
				pMain.add(cboTinhTrangP);
		/////Buttons
				btnThemP = new FixButton("Thêm");
				btnThemP.setForeground(Color.WHITE);
				btnThemP.setFont(new Font("SansSerif", Font.BOLD, 14));
				btnThemP.setBackground(new Color(114, 43, 153));
				btnThemP.setBounds(374, 150, 118, 35);
				Image imgThemKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconGrayThem.png");
				Image resizeImgThemKH = imgThemKH.getScaledInstance(25, 25, 0);
				btnThemP.setIcon(new ImageIcon(resizeImgThemKH));
				pMain.add(btnThemP);
				
				btnSuaP = new FixButton("Sửa");
				btnSuaP.setForeground(Color.WHITE);
				btnSuaP.setFont(new Font("SansSerif", Font.BOLD, 14));
				btnSuaP.setBackground(new Color(114, 43, 153));
				btnSuaP.setBounds(537, 150, 118, 35);
				Image imgSuaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconTool.png");
				Image resizeImgSuaKH = imgSuaKH.getScaledInstance(25, 25, 0);
				btnSuaP.setIcon(new ImageIcon(resizeImgSuaKH));
				pMain.add(btnSuaP);
				
				btnXoaP = new FixButton("Xóa");
				btnXoaP.setForeground(Color.WHITE);
				btnXoaP.setFont(new Font("SansSerif", Font.BOLD, 14));
				btnXoaP.setBackground(new Color(114, 43, 153));
				btnXoaP.setBounds(702, 150, 125, 35);
				Image imgXoaKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconRemove.png");
				Image resizeImgXoaKH = imgXoaKH.getScaledInstance(25, 25, 0);
				btnXoaP.setIcon(new ImageIcon(resizeImgXoaKH));
				pMain.add(btnXoaP);
				
				btnReset = new FixButton("Làm mới");
				btnReset.setForeground(Color.WHITE);
				btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
				btnReset.setBackground(new Color(114, 43, 153));
				btnReset.setBounds(868, 150, 144, 35);
				Image imgLamMoiKH = Toolkit.getDefaultToolkit().getImage("data\\img\\iconReset.png");
				Image resizeImgLamMoiKH = imgLamMoiKH.getScaledInstance(25, 25, 0);
				btnReset.setIcon(new ImageIcon(resizeImgLamMoiKH));
				pMain.add(btnReset);
		//SapXep
				JPanel pSapXep = new JPanel();
				pSapXep.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "Sắp xếp", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
				pSapXep.setBackground(new Color(207, 195, 237));
				pSapXep.setBounds(217, 195, 859, 47);
				pMain.add(pSapXep);
				pSapXep.setLayout(null);
				
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
				
				
				
				rdoTheoMaP = new JRadioButton("Theo mã phòng");
				rdoTheoMaP.setBounds(312, 15, 170, 27);
				rdoTheoMaP.setSelected(true);
				rdoTheoMaP.setFont(new Font("SansSerif", Font.BOLD, 14));
				rdoTheoMaP.setBackground(new Color(207, 195, 237));
				pSapXep.add(rdoTheoMaP);
				
				rdoTheoLoaiP = new JRadioButton("Theo loại phòng");
				rdoTheoLoaiP.setBounds(518, 15, 170, 27);
				rdoTheoLoaiP.setFont(new Font("SansSerif", Font.BOLD, 14));
				rdoTheoLoaiP.setBackground(new Color(207, 195, 237));
				pSapXep.add(rdoTheoLoaiP);
				
				rdoTheoGiaP = new JRadioButton("Theo giá phòng ");
				rdoTheoGiaP.setBounds(718, 15, 135, 27);
				rdoTheoGiaP.setFont(new Font("SansSerif", Font.BOLD, 14));
				rdoTheoGiaP.setBackground(new Color(207, 195, 237));
				pSapXep.add(rdoTheoGiaP);
				

				chkAll.setFont(new Font("SansSerif", Font.BOLD, 14));
				chkAll.setBackground(new Color(207, 195, 237));
				chkAll.setBounds(201, 15, 135, 27);
				pSapXep.add(chkAll);
				chkAll.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange()==1) {
							loadDanhSachPhong();
						}
						else
							clearTable();
					}
				});
				
				
				
				ButtonGroup bgRdo=new ButtonGroup();
				bgRdo.add(rdoTheoMaP);
				bgRdo.add(rdoTheoLoaiP);
				bgRdo.add(rdoTheoGiaP);
				bgRdo.clearSelection();
				
				String phong [] = {"Mã phòng","Tên phòng", "Giá phòng", "Tình trạng phòng"};
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
				
				JScrollPane spPhong = new JScrollPane(tblPhong, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				spPhong.setBounds(37, 249, 1194, 346);
				spPhong.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
				spPhong.setBackground(new Color(164, 44, 167));
				spPhong.getHorizontalScrollBar();
				pMain.add(spPhong);
				
				tblPhong.getColumnModel().getColumn(0).setPreferredWidth(240);
				tblPhong.getColumnModel().getColumn(1).setPreferredWidth(240);
				tblPhong.getColumnModel().getColumn(2).setPreferredWidth(240);
				tblPhong.getColumnModel().getColumn(3).setPreferredWidth(240);
				
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
				lblBackGround.setBounds(0, 0, 1281, 606);
				Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
				Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
				lblBackGround.setIcon(new ImageIcon(resizeBG));
				pMain.add(lblBackGround);
		// load loai Phong
				loaiP = daoLoaiP.getAllLoaiPhong();
				for(LoaiPhong lp : loaiP) {
					cboLoaiP.addItem(lp.getTenLoaiPhong());
				}

		//
				tblPhong.addMouseListener(this);
				btnReset.addActionListener(this);
				btnThemP.addActionListener(this);
				btnSuaP.addActionListener(this);
				btnXoaP.addActionListener(this);
				rdoTheoGiaP.addActionListener(this);
				rdoTheoLoaiP.addActionListener(this);
				rdoTheoMaP.addActionListener(this);
				btnTim.addActionListener(this);
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
	
	//load thong tin phong sau khi them
	public void loadPhongDuocChon(Phong p) {
		LoaiPhong loaiP = daoLoaiP.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiPhong());
		modelPhong.addRow(new Object[] {p.getMaPhong(), loaiP.getTenLoaiPhong(), dfGiaP.format(p.getGiaPhong()), p.getTinhTrangPhong() });
	}
	
	
	//Them phong
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
						//	System.out.println(giaP);
							daoPhong.suaThongTinPhong(p);
							loadPhongDuocChon(p);
							JOptionPane.showMessageDialog(this, "Thông tin phòng đã được sửa!", "Thông báo",
									JOptionPane.OK_OPTION);
						} catch (Exception e) {
							//e.printStackTrace();
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
			if (cancel == JOptionPane.YES_OPTION) {
				String maP = tblPhong.getValueAt(row, 0).toString();
				try {
					modelPhong.removeRow(row);
					clearTable();
					daoPhong.huyP(maP);
					loadDanhSachPhong();
					JOptionPane.showMessageDialog(null, "Đã xóa phòng!", "Thông báo", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "xóa phòng thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thông tin phòng cần hủy!", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	//Tìm kiếm phòng
	private void findPhong() {
		Phong p = daoPhong.getThongTinPhong(txtTK.getText().toLowerCase().trim());
		
		if (!txtTK.getText().equals("") && !txtTK.getText().equals("Tìm theo mã phòng, loại phòng, tình trạng phòng")) {
			String messTenPhong = "\n - Ví dụ: P001";
			String messLoaiPhong = "\n - Tìm theo loại phòng: thường, trung, VIP";
			String messTinhTrang = "\n - Tình trạng: đã đặt, trống, đang sử dụng";

			if (regex.regexTimKiemMaPhong(txtTK)) {
				try {
					clearTable();
					loadPhongDuocChon(p);
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Không tìm thấy tên phòng cần tìm!", "Thông báo", JOptionPane.OK_OPTION);
				}
			}  
			 else
				JOptionPane.showMessageDialog(null,
						"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã khách hàng. Ví dụ: KH001"
								+ messTenPhong + messLoaiPhong + messTinhTrang,
						"Thông báo", JOptionPane.ERROR_MESSAGE);
		} else {
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
			//resetAll();
			suaThongTin();
		}
		if(o.equals(btnXoaP)) {
			xoaPhong();
		}
		if(cboSapXep.getSelectedItem()=="Tăng dần") {
			if(o.equals(rdoTheoMaP))
				loadDanhSachPhong();
			if(o.equals(rdoTheoLoaiP))
				sortLoaiPhongTangDan(p);
			if(o.equals(rdoTheoGiaP))
				sortGiaPhongTangDan(p);
		}
		if(cboSapXep.getSelectedItem()=="Giảm dần") {
			if(o.equals(rdoTheoMaP))
				sortMaPhongGiamDan(p);
			if(o.equals(rdoTheoLoaiP))
				sortLoaiPhongGiamDan(p);
			if(o.equals(rdoTheoGiaP))
				sortGiaPhongGiamDan(p);
		}
		if(o.equals(btnTim)) {
			findPhong();
		}
	}
}
