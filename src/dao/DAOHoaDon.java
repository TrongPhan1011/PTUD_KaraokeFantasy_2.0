package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.CTDDP;
import entity.DonDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.MatHang;
import entity.NhanVien;
import entity.Phong;

public class DAOHoaDon {
public boolean themHoaDon(HoaDon hd) {
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try {
			stmt = con.prepareStatement("insert into HoaDon values (?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, hd.getMaHoaDon());
			stmt.setString(2, hd.getPhong().getMaPhong());
			stmt.setString(3, hd.getKhachHang().getMaKhangHang());
			stmt.setString(4, hd.getNhanVien().getMaNhanVien());
			stmt.setDate(5, hd.getNgayLap());
			stmt.setTime(6, hd.getGioVao());
			stmt.setTime(7, hd.getGioRa());
			stmt.setString(8, hd.getPhuThu());
			stmt.setString(9, hd.getTrangThaiHD());
			stmt.setDouble(10, hd.getGiamGia());
			
			
			
			
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

	public	ArrayList<HoaDon> getHDTheoNgay(Date ngayBatDau,Date ngayKetThuc) {
		
		ArrayList<HoaDon> lsHD = new ArrayList<HoaDon>();
		String ngayBD = (ngayBatDau.getYear()+1900) +"/"+ (ngayBatDau.getMonth()+1) +"/"+ngayBatDau.getDate();
		String ngayKT = (ngayKetThuc.getYear()+1900) +"/"+ (ngayKetThuc.getMonth()+1) +"/"+ngayKetThuc.getDate();
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * FROM [KaraokeFantasy].[dbo].[HoaDon] where ngayLap between '"+ngayBD+"' and '"+ngayKT+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				HoaDon  hd = new HoaDon();
				hd.setMaHoaDon(rs.getString(1));
				hd.setPhong(new Phong(rs.getString(2)));
				hd.setKhachHang(new KhachHang(rs.getString(3)));
				hd.setNhanVien(new NhanVien(rs.getString(4)));
				hd.setNgayLap(rs.getDate(5));
				hd.setGioVao(rs.getTime(6));
				hd.setGioRa(rs.getTime(7));
				hd.setPhuThu(rs.getNString(8));
				hd.setTrangThaiHD(rs.getNString(9));
				hd.setGiamGia(rs.getDouble(10));
				
				lsHD.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsHD;
		
	}
	
public	HoaDon getHDTheoMa(String ma) {
	
		HoaDon  hd = new HoaDon();
	
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * FROM [KaraokeFantasy].[dbo].[HoaDon] where maHD ='"+ma+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				hd.setMaHoaDon(rs.getString(1));
				hd.setPhong(new Phong(rs.getString(2)));
				hd.setKhachHang(new KhachHang(rs.getString(3)));
				hd.setNhanVien(new NhanVien(rs.getString(4)));
				hd.setNgayLap(rs.getDate(5));
				hd.setGioVao(rs.getTime(6));
				hd.setGioRa(rs.getTime(7));
				hd.setPhuThu(rs.getNString(8));
				hd.setTrangThaiHD(rs.getNString(9));
				hd.setGiamGia(rs.getDouble(10));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return hd;
		
	}

public	ArrayList<HoaDon> getHDTheoTenKH(String tenKH) {
	
	ArrayList<HoaDon> lsHD = new ArrayList<HoaDon>();
	ConnectDB.getinstance();
	Connection con = ConnectDB.getConnection();
	String sql = "SELECT HoaDon.* FROM  HoaDon INNER JOIN KhachHang ON HoaDon.maKH = KhachHang.maKhachHang where KhachHang.tenKH like N'%"+tenKH+"%'";
	
	try {
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()) {
			HoaDon  hd = new HoaDon();
			hd.setMaHoaDon(rs.getString(1));
			hd.setPhong(new Phong(rs.getString(2)));
			hd.setKhachHang(new KhachHang(rs.getString(3)));
			hd.setNhanVien(new NhanVien(rs.getString(4)));
			hd.setNgayLap(rs.getDate(5));
			hd.setGioVao(rs.getTime(6));
			hd.setGioRa(rs.getTime(7));
			hd.setPhuThu(rs.getNString(8));
			hd.setTrangThaiHD(rs.getNString(9));
			hd.setGiamGia(rs.getDouble(10));
			
			lsHD.add(hd);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return lsHD;
	
	}

public	ArrayList<HoaDon> getHDTheoMaNV(String maNV) {
	
	ArrayList<HoaDon> lsHD = new ArrayList<HoaDon>();
	ConnectDB.getinstance();
	Connection con = ConnectDB.getConnection();
	String sql = "SELECT * FROM [KaraokeFantasy].[dbo].[HoaDon] where maNhanVien = '"+maNV+"'";
	
	try {
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while(rs.next()) {
			HoaDon  hd = new HoaDon();
			hd.setMaHoaDon(rs.getString(1));
			hd.setPhong(new Phong(rs.getString(2)));
			hd.setKhachHang(new KhachHang(rs.getString(3)));
			hd.setNhanVien(new NhanVien(rs.getString(4)));
			hd.setNgayLap(rs.getDate(5));
			hd.setGioVao(rs.getTime(6));
			hd.setGioRa(rs.getTime(7));
			hd.setPhuThu(rs.getNString(8));
			hd.setTrangThaiHD(rs.getNString(9));
			hd.setGiamGia(rs.getDouble(10));
			
			lsHD.add(hd);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return lsHD;
	
	}



}
