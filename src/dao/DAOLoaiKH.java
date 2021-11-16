package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.LoaiKH;

public class DAOLoaiKH {

	/**
	 * Lấy ra thông tin loại khách hàng trừ LKH004 (không còn là khách hàng)
	 * @return các thông tin từ bảng loại khách hàng
	 */
	public ArrayList<LoaiKH> getAllLoaiKH() {
		ArrayList<LoaiKH> lsLoaiKH = new ArrayList<LoaiKH>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiKH  where not maLoaiKH=N'LKH004'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maLoaiKH = rs.getString(1);
				String tenLoaiKH = rs.getString(2);
				LoaiKH loaiKH = new LoaiKH(maLoaiKH, tenLoaiKH);
				lsLoaiKH.add(loaiKH);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsLoaiKH;
	}
	
	/**
	 * Lấy ra loại khách hàng 
	 * @param mã khách hàng được nhập vào
	 * @return thông tin của mã loại khách hàng đã được nhập
	 */
	public LoaiKH getLoaiKHTheoMaLoai(String ma) {
		LoaiKH loaiKH = new LoaiKH();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiKH where maLoaiKH = '"+ma+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				loaiKH.setMaLoaiKH(rs.getNString(1));
				loaiKH.setTenLoaiKH(rs.getNString(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return loaiKH;
	}
	/**
	 * 
	 * @param ten loại khách hàng được nhập
	 * @return mã loại khách hàng
	 */
	
	public String getMaLoaiKHTheoTen(String ten) {
		String maLoai ="";
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maLoaiKH from LoaiKH where tenLoaiKH = N'"+ten +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				maLoai = rs.getString(1);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return maLoai;
		
	}
	
}
