package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



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
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class XuatExcels {
	
	
	private DAOHoaDon daoHD;
	private DAOCTHD daoCTHD;
	private DAOLoaiMH daoLoaiMH;
	private DAOMatHang daoMatHang;
	private DAOCTDDP daoCTDDP;
	private DAOLoaiPhong daoLoaiPhong;
	private DAODonDatPhong daoDDP;
	private DAOPhong daoPhong;
	private DAOKhachHang daoKhachHang;
	private DAONhanVien daoNhanVien;
	private SimpleDateFormat sf;
	private SimpleDateFormat sdf;
	
	public XuatExcels() {
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
			sf = new SimpleDateFormat("dd/MM/yyyy");
			sdf = new SimpleDateFormat("HH:mm");
	}

	public void xuatHoaDon(ArrayList<HoaDon> lsHD,String path) throws IOException {
		Workbook workbook = null;
		 
        if (path.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet("DSHD"); 
 
        int rowIndex = 1;
         
        Row title = sheet.createRow(rowIndex);
        
        Cell cellTitle = title.createCell(0,CellType.STRING);
        cellTitle.setCellValue("DANH SÁCH HÓA ĐƠN");
        rowIndex++;
        Row headerRow = sheet.createRow(rowIndex);
        Cell cMaHD = headerRow.createCell(0,CellType.STRING);
        cMaHD.setCellValue("Mã hóa đơn");
        
        Cell cMaKH = headerRow.createCell(1,CellType.STRING);
        cMaKH.setCellValue("Mã khách hàng");
        
        Cell cTenKH = headerRow.createCell(2,CellType.STRING);
        cTenKH.setCellValue("Tên khách hàng");
        Cell cMaNV = headerRow.createCell(3,CellType.STRING);
        cMaNV.setCellValue("Mã nhân viên");
        Cell cTenNV = headerRow.createCell(4,CellType.STRING);
        cTenNV.setCellValue("Tên nhân viên");
        Cell cNgayLap = headerRow.createCell(5,CellType.STRING);
        cNgayLap.setCellValue("Ngày lập");
        Cell cPhong = headerRow.createCell(6,CellType.STRING);
        cPhong.setCellValue("Phòng");
        Cell cGiovao = headerRow.createCell(7,CellType.STRING);
        cGiovao.setCellValue("Giờ vào");
        Cell cGiora = headerRow.createCell(8,CellType.STRING);
        cGiora.setCellValue("Giờ ra");
        Cell cPhuThu = headerRow.createCell(9,CellType.STRING);
        cPhuThu.setCellValue("Phụ thu");
        Cell cGiamGia = headerRow.createCell(10,CellType.STRING);
        cGiamGia.setCellValue("Giảm giá");
        
        rowIndex++; 
        for(HoaDon hd : lsHD) {
        	KhachHang kh = daoKhachHang.getKHTheoMa(hd.getKhachHang().getMaKhangHang());
			NhanVien nv = daoNhanVien.getNVTheoMa(hd.getNhanVien().getMaNhanVien());
			Row row = sheet.createRow(rowIndex);
			
			Cell cRowMaHD = row.createCell(0,CellType.STRING);
	        cRowMaHD.setCellValue(hd.getMaHoaDon());
	        
	        Cell cRowMaKH = row.createCell(1,CellType.STRING);
	        cRowMaKH.setCellValue(kh.getMaKhangHang());
	        
	        Cell cRowTenKH = row.createCell(2,CellType.STRING);
	        cRowTenKH.setCellValue(kh.getTenKH());
	        
	        Cell cRowMaNV = row.createCell(3,CellType.STRING);
	        cRowMaNV.setCellValue(nv.getMaNhanVien());
	        
	        Cell cRowTenNV = row.createCell(4,CellType.STRING);
	        cRowTenNV.setCellValue(nv.getTenNhanVien());
	        
	        Cell cRowNgayLap = row.createCell(5,CellType.STRING);
	        cRowNgayLap.setCellValue(sf.format(hd.getNgayLap()));
	        
	        Cell cRowPhong = row.createCell(6,CellType.STRING);
	        cRowPhong.setCellValue(hd.getPhong().getMaPhong());
	        
	        Cell cRowGiovao = row.createCell(7,CellType.STRING);
	        cRowGiovao.setCellValue(sdf.format(hd.getGioVao()));
	        
	        Cell cRowGiora = row.createCell(8,CellType.STRING);
	        cRowGiora.setCellValue(sdf.format(hd.getGioRa()));
	        
	        Cell cRowPhuThu = row.createCell(9,CellType.STRING);
	        cRowPhuThu.setCellValue(hd.getPhuThu());
	        
	        Cell cRowGiamGia = row.createCell(10,CellType.NUMERIC);
	        cRowGiamGia.setCellValue(hd.getGiamGia());
			
	        rowIndex++;
			
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
	
	public void xuatTable(JTable tb,String tieude,String path) throws IOException {
		Workbook workbook = null;
		 
        if (path.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = workbook.createSheet(); 
 
        int rowIndex = 1;
         
        Row title = sheet.createRow(rowIndex);
        Cell cellTitle = title.createCell(0,CellType.STRING);
        cellTitle.setCellValue(tieude);
        rowIndex++;
        int rowCount = tb.getRowCount();
        int colCount = tb.getColumnCount();
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        
        // add header to excels
        Row headerRow = sheet.createRow(rowIndex);
        for(int i = 0; i < colCount;i++) {
        	 Cell cH = headerRow.createCell(i,CellType.STRING);
             cH.setCellValue(tb.getColumnName(i));
        }
        //Xuat row
        rowIndex++;
        for(int i = 0; i < rowCount;i++) {
        	Row row = sheet.createRow(rowIndex);
        	for(int j = 0; j < colCount;j++) {
	        	Cell c = row.createCell(j,CellType.STRING);
	            c.setCellValue(model.getValueAt(i, j).toString());
        	}
            rowIndex++;
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
	
	
	
}