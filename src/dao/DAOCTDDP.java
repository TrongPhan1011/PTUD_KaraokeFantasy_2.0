package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.CTDDP;
import entity.DonDatPhong;
import entity.MatHang;

public class DAOCTDDP {
	
	public boolean themCTDDP(CTDDP ctddp) {
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("insert into CTDDP values (?,?,?)");
			stmt.setString(1, ctddp.getDonDatPhong().getMaDDP());
			stmt.setString(2, ctddp.getMatHang().getMaMatHang());
			stmt.setInt(3, ctddp.getSoLuongMH());
			
			
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
	
	public boolean xoaCTDDP(String maDDP,String maMH) {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("delete CTDDP where maDDP =? and maMH = ?");
			stmt.setString(1, maDDP);
			stmt.setString(2, maMH);
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
	
	public boolean suaSoluongMH(String maDDP,String maMH,int soLuong) {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("update CTDDP set soLuong = "+soLuong+" where maDDP =? and maMH = ?");
			stmt.setString(1, maDDP);
			stmt.setString(2, maMH);
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
	
	public	CTDDP getCTDDPTheoMa(String maDPP,String maMH) {
		CTDDP ctddp = new CTDDP();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * FROM  CTDDP where maDDP = '"+maDPP+"' and maMH = '"+maMH+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				

				ctddp.setDonDatPhong(new DonDatPhong(maDPP));
				ctddp.setMatHang(new MatHang(maMH));
				ctddp.setSoLuongMH(rs.getInt(3));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ctddp;
		
	}
	
	public ArrayList<CTDDP> getCTDDPTheoMaDDP(String ma) {
		ArrayList< CTDDP> lsCTDDP = new ArrayList<CTDDP>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT CTDDP.*\r\n"
				+ "FROM  CTDDP where maDDP = '"+ma+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				CTDDP ctddp = new CTDDP();

				ctddp.setDonDatPhong(new DonDatPhong(ma));
				ctddp.setMatHang(new MatHang(rs.getNString(2)));
				ctddp.setSoLuongMH(rs.getInt(3));
				
				lsCTDDP.add(ctddp);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsCTDDP;
		
	}
	
	
}
