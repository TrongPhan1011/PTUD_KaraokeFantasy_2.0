package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.CTHD;
import entity.HoaDon;
import entity.MatHang;

public class DAOCTHD {
	
	public boolean themCTHD(CTHD cthd) {
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("insert into CTHD values (?,?,?)");
			stmt.setString(1, cthd.getHoaDon().getMaHoaDon());
			stmt.setString(2, cthd.getMatHang().getMaMatHang());
			stmt.setInt(3, cthd.getSoLuong());
			
			
			n = stmt.executeUpdate();
			} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
		return n>0;
	}
	
	public	CTHD getCTHDTheoMa(String maHD,String maMH) {
		CTHD cthd = new CTHD();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * FROM  CTHD where maHD = '"+maHD+"' and maMH = '"+maMH+"' order by maMH";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
	
				cthd.setHoaDon(new HoaDon(maHD));
				cthd.setMatHang(new MatHang(maMH));
				cthd.setSoLuong(rs.getInt(3));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cthd;
	
	}
	
	public	ArrayList<CTHD> getCTHDTheoMaHD(String maHD) {
		ArrayList<CTHD> lsCTHD = new ArrayList<CTHD>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * FROM  CTHD where maHD = '"+maHD+"' order by maMH" ;
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				CTHD cthd = new CTHD();
	
				cthd.setHoaDon(new HoaDon(rs.getString(1)));
				cthd.setMatHang(new MatHang(rs.getString(2)));
				cthd.setSoLuong(rs.getInt(3));
				
				lsCTHD.add(cthd);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsCTHD;
	
}
	
	

}
