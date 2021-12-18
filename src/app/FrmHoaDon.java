package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entity.HoaDon;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmHoaDon extends JFrame  implements ActionListener{

	
	private static final long serialVersionUID = -6027396469314105075L;
	private JPanel pMain;
	private FixButton btnIn;
	private JTextPane txtHD;
	private HoaDon hd;
	private JTable tbCTHD;
	private String giamGia;
	private String tongTien;
	private String thanhTien;
	private SimpleDateFormat sfTime;
	private SimpleDateFormat sfdate;
	/**
	 * Create the frame.
	 */
	public FrmHoaDon(HoaDon hd,JTable tbCTHD,String giamGia, String tongTien,String thanhTien )  {
		this.giamGia = giamGia;
		this.hd = hd;
		this.tbCTHD = tbCTHD;
		this.tongTien = tongTien;
		this.thanhTien = thanhTien;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 492, 664);
		setLocationRelativeTo(null);
		setTitle("Hóa đơn tạm");
		ImageIcon logoApp = (ImageIcon) IconFontSwing.buildIcon(FontAwesome.FOURSQUARE, 50, new Color(164, 44,167));
		setIconImage(logoApp.getImage());
		pMain = new JPanel();
		pMain.setBackground(new Color(255, 240, 245));
		pMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pMain);
		pMain.setLayout(null);
		
		txtHD = new JTextPane();
		txtHD.setBackground(Color.WHITE);
		txtHD.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtHD.setEditable(false);
		txtHD.setBounds(10, 11, 460, 560);
		pMain.add(txtHD);
		
		btnIn = new FixButton("In hóa đơn");
		btnIn.setForeground(Color.WHITE);
		btnIn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnIn.setBackground(new Color(114, 23, 153));
		btnIn.setBounds(117, 585, 261, 33);
		
		Icon iconInHD = IconFontSwing.buildIcon(FontAwesome.PRINT, 20, new Color(255, 255 ,255));
		btnIn.setIcon(iconInHD);
		pMain.add(btnIn);
		
		sfdate = new SimpleDateFormat("dd/MM/yyyy");
		sfTime = new SimpleDateFormat("HH:mm");
		loadHD();
		
		btnIn.addActionListener(this);
		
	
	}
	
	public void loadHD() {
		if(giamGia.equalsIgnoreCase(""))
			giamGia = "0";
		
		txtHD.setText("\t\t   KARAOKE FANTASY\n");
		txtHD.setText(txtHD.getText()+"      12 Nguyễn Văn Bảo, P.4, Gò Vấp, TP.Hồ Chí Minh\n");
		txtHD.setText(txtHD.getText() + "\t\t   SĐT: 0363435011\n");
		txtHD.setText(txtHD.getText()+ "\t---------------------------------------\n");
		txtHD.setText(txtHD.getText()+ "\t\t      HÓA ĐƠN\n\n");
		txtHD.setText(txtHD.getText()+ "  Mã hóa đơn: "+hd.getMaHoaDon()+"\t"+"        Ngày lập: "+sfdate.format(hd.getNgayLap())+"\n");
		txtHD.setText(txtHD.getText()+ "  Phòng: "+hd.getPhong().getMaPhong()+"\t\t        "+"Giờ vào: \t"+sfTime.format(hd.getGioVao())+"\n");
		txtHD.setText(txtHD.getText()+ "  Mã khách hàng: "+hd.getKhachHang().getMaKhangHang()+"\t        "+"Giờ ra:  \t"+sfTime.format(hd.getGioRa())+"\n");
		txtHD.setText(txtHD.getText()+ "\t---------------------------------------\n");
		txtHD.setText(txtHD.getText()+ "  STT  Tên dịch vụ\t\tSL\t   Đơn giá\n");
		int row = tbCTHD.getRowCount();
		DefaultTableModel model = (DefaultTableModel) tbCTHD.getModel();
		
		for(int i = 0; i<row;i++) {
			int stt = i+1;
			if(model.getValueAt(i, 0).toString().length() >=12)
				txtHD.setText(txtHD.getText()+ "   "+stt+"   "+model.getValueAt(i, 0).toString()+"\t\t"+model.getValueAt(i, 2)+"\t   "+model.getValueAt(i, 3)+"\n");
			else txtHD.setText(txtHD.getText()+ "   "+stt+"   "+model.getValueAt(i, 0).toString()+"\t\t\t"+model.getValueAt(i, 2)+"\t   "+model.getValueAt(i, 3)+"\n");
		}
		txtHD.setText(txtHD.getText()+ "\t---------------------------------------\n");
		txtHD.setText(txtHD.getText()+ "\t\t\tGiảm giá:   "+giamGia+"\n");
		txtHD.setText(txtHD.getText()+ "\t\t\tTiền thuê:        "+tongTien+"\n");
		txtHD.setText(txtHD.getText()+ "\t\t\tThành tiền:       "+thanhTien+"\n");
		txtHD.setText(txtHD.getText()+ "\t---------------------------------------\n");
		txtHD.setText(txtHD.getText()+ "\t\t   CẢM ƠN QUÝ KHÁCH\n");
		txtHD.setText(txtHD.getText()+ "\t\t      HẸN GẶP LẠI");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnIn)) {
			try {
				txtHD.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
	}


}
