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
import entity.NhanVien;
import entity.Phong;

public class DAODonDatPhong {

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
	
	public boolean capNhatDDP(DonDatPhong ddp, String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "update DonDatPhong set ngayLap = ?, gioDen = ?, ngayDen = ?, TrangThaiDDP = ? where maDDP like '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, ddp.getNgayLap());
			ps.setTime(2, ddp.getGioDen());
			ps.setDate(3, ddp.getNgayDen());
			ps.setString(4, ddp.getTrangThaiDDP());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	public boolean capNhatTrangThaiDDP(String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "update DonDatPhong set TrangThaiDDP = N'Đã trả phòng' where maDDP = '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}

	public ArrayList<DonDatPhong> getAllDanhSachDDP() {
		ArrayList<DonDatPhong> lsDDP = new ArrayList<DonDatPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from DonDatPhong");
			ResultSet rs = ps.executeQuery();
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
	
	public ArrayList<DonDatPhong> getDanhSachDDPChoXacNhanVaDaXacNhan() {
		ArrayList<DonDatPhong> lsDDP = new ArrayList<DonDatPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from DonDatPhong where TrangThaiDDP != N'Hủy' and TrangThaiDDP != N'Đã trả phòng'";
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

	public ArrayList<DonDatPhong> getDDPTheoMaKH(String ma){
		ArrayList<DonDatPhong> lstDDP = new ArrayList<DonDatPhong>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from DonDatPhong where maKH = N'"+ma+"' and TrangThaiDDP = N'Chờ xác nhận'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DonDatPhong ddp=new DonDatPhong();
				ddp.setMaDDP(rs.getNString(1));
				ddp.setPhong(new Phong(rs.getNString(2)));
				ddp.setKhachHang(new KhachHang(rs.getNString(3)));
				ddp.setNhanVien(new NhanVien(rs.getNString(4)));
				ddp.setNgayLap(rs.getDate(5));
				ddp.setGioDen(rs.getTime(6));
				ddp.setNgayDen(rs.getDate(7));
				ddp.setTrangThaiDDP(rs.getNString(8));
				lstDDP.add(ddp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstDDP;
	}

	public ArrayList<DonDatPhong> sortMaDDP(String kieuSX) {
		ArrayList<DonDatPhong> lstDDP = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from DonDatPhong where TrangThaiDDP != N'Hủy' order by maDDP "+kieuSX+"");
			ResultSet rs = ps.executeQuery();
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
				lstDDP.add(ddp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstDDP;
	}
	
	public ArrayList<DonDatPhong> sortDDPTheoLoaiPhong(String kieuSX) {
		ArrayList<DonDatPhong> lstDDP = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT DonDatPhong.* FROM DonDatPhong INNER JOIN Phong ON DonDatPhong.maPhong = Phong.maPhong "
														+ "where maLoaiPhong != 'LP004' order by maLoaiPhong "+kieuSX+"");
			ResultSet rs = ps.executeQuery();
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
				lstDDP.add(ddp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstDDP;
	}

}
