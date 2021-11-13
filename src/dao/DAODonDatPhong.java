package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.LoaiKH;
import entity.LoaiMatHang;
import entity.LoaiPhong;
import entity.MatHang;
import entity.NhanVien;
import entity.Phong;

public class DAODonDatPhong {
	
	public ArrayList<DonDatPhong> getAllDonDatPhong(){
		ArrayList<DonDatPhong> lsDDP = new ArrayList<DonDatPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from DonDatPhong";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				DonDatPhong ddp = new DonDatPhong();
				ddp.setMaDDP(rs.getNString(1));
				ddp.setNgayLap(rs.getDate(2));
				ddp.setKhachHang(new KhachHang(rs.getNString(3)));
				ddp.setNhanVien(new NhanVien(rs.getNString(4)));
					lsDDP.add(ddp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsDDP;
	}
	
	public ArrayList<DonDatPhong> getDanhSachDDPKhongHuy() {
		ArrayList<DonDatPhong> lsDDP = new ArrayList<DonDatPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from DonDatPhong where TrangThaiDDP != N'Hủy'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				DonDatPhong ddp = new DonDatPhong();
				ddp.setMaDDP(rs.getString(1));
				ddp.setPhong(new Phong(rs.getString(2)));
				ddp.setKhachHang(new KhachHang(rs.getString(3)));
				ddp.setNhanVien(new NhanVien(rs.getString(4)));
				ddp.setNgayLap(rs.getDate(5));
				ddp.setGioDen(rs.getTime(6));
				ddp.setNgayDen(rs.getDate(7));
				ddp.setTrangThaiDDP(rs.getString(8));
				lsDDP.add(ddp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lsDDP;
	} 
	
	public DonDatPhong getDDPTheoMaPhong(String ma){
		DonDatPhong ddp = new DonDatPhong();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT DonDatPhong.*\r\n"
				+ "FROM  DonDatPhong\r\n"
				+ "where maPhong = N'"+ma+"' and TrangThaiDDP = N'Đã xác nhận'"
				+ "";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				ddp.setMaDDP(rs.getNString(1));
				ddp.setPhong(new Phong(rs.getNString(2)));
				ddp.setKhachHang(new KhachHang(rs.getNString(3)));
				ddp.setNhanVien(new NhanVien(rs.getNString(4)));
				ddp.setNgayLap(rs.getDate(5));
				ddp.setGioDen(rs.getTime(6));
				ddp.setNgayDen(rs.getDate(7));
				ddp.setTrangThaiDDP(rs.getNString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ddp;
	}
	
	//them ddp
	public boolean themDDP(DonDatPhong ddp) throws SQLException {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into DonDatPhong values (?,?,?,?,?,?,?,?)");
			ps.setString(1, ddp.getMaDDP());
			ps.setString(2, ddp.getPhong().getMaPhong());
			ps.setString(3, ddp.getKhachHang().getMaKhangHang());
			ps.setString(4, ddp.getNhanVien().getMaNhanVien());
			ps.setDate(5, ddp.getNgayLap());
			ps.setTime(6, ddp.getGioDen());
			ps.setDate(7, ddp.getNgayDen());
			ps.setString(8, ddp.getTrangThaiDDP());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
//	public DonDatPhong getDDPTheoTenKH(String ) {
//		DonDatPhong ddp=new DonDatPhong();
//		ConnectDB.getinstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement ps = con.prepareStatement("")
//	}
	
}
