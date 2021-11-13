package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.LoaiPhong;

public class DAOLoaiPhong {
	public ArrayList<LoaiPhong> getAllLoaiPhong() {
		ArrayList<LoaiPhong> dsLoaiPhong = new ArrayList<LoaiPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiPhong where not maLoaiPhong = N'LP004'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maLoaiP = rs.getString(1);
				String tenLoaiP = rs.getString(2);
				LoaiPhong loaiP = new LoaiPhong(maLoaiP, tenLoaiP);
				dsLoaiPhong.add(loaiP);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsLoaiPhong;
	}
	
	public LoaiPhong getLoaiPhongTheoMa(String ma) {
		LoaiPhong lp = new LoaiPhong();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiPhong where maLoaiPhong = N'"+ma+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				 lp.setMaLoaiPhong(rs.getNString(1));
				 lp.setTenLoaiPhong(rs.getNString(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lp;
	}
	
	public LoaiPhong getLoaiPhongTheoTenLoai(String ten) {
		LoaiPhong lp = new LoaiPhong();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiPhong where tenLoaiPhong = N'"+ten +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				 lp.setMaLoaiPhong(rs.getNString(1));
				 lp.setTenLoaiPhong(rs.getNString(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lp;
		
	}
	
	public String getMaLoaiPTheoTen(String tenP) {
		String maLoai ="";
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maLoaiPhong from LoaiPhong where tenLoaiPhong = N'"+tenP +"'";
		
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
