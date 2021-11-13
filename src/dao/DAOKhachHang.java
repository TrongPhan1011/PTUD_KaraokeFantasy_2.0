package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTextField;

import connection.ConnectDB;
import entity.KhachHang;
import entity.LoaiKH;
import entity.TaiKhoan;


public class DAOKhachHang {
	public KhachHang getKHTheoMa(String ma) { 
		KhachHang kh = new KhachHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from KhachHang where maKhachHang = '"+ma+"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));


			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return kh;
	}

	public KhachHang getKHTheoMa(JTextField tam) { 
		KhachHang kh = new KhachHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from KhachHang where maKhachHang = '"+tam+"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));


			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return kh;
	}

	//get ten KH
	public ArrayList<KhachHang> getTenKH(String info) { 
		ArrayList<KhachHang> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from KhachHang where tenKH like N'"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));
				list.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<KhachHang> getDanhSachKH() {


		ArrayList<KhachHang> lsKH = new ArrayList<KhachHang>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select *from KhachHang where not maLoaiKH = N'LKH004'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				KhachHang kh = new KhachHang();

				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));

				lsKH.add(kh);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsKH;
	}

	public boolean themDanhSachKH(KhachHang kh) {

		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try { 
			stmt = con.prepareStatement("insert into KhachHang values (?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, kh.getMaKhangHang());
			stmt.setString(2, kh.getLoaiKH().getMaLoaiKH());
			stmt.setString(3, kh.getTenKH());
			stmt.setString(4, kh.getSdt());
			stmt.setString(5, kh.getCccd());
			stmt.setString(6, kh.getDiaChi());
			stmt.setDate(7, kh.getNgaySinh());
			stmt.setString(8, kh.getGioiTinh());
			stmt.setInt(9, kh.getDiemTichLuy());
			stmt.setDate(10, kh.getNgayDangKy());

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

	public boolean suaThongTinKhachHang(KhachHang kh) {

		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n=0;
		try { 
			stmt = con.prepareStatement("update KhachHang set maLoaiKH=?, tenKH=?, sdt=?, cccd=?, diaChi=?, ngaySinh=?, gioiTinh=?, diemTichLuy=?, ngayDangKy=? where maKhachHang=? ");
			stmt.setString(1, kh.getLoaiKH().getMaLoaiKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setString(3, kh.getSdt());
			stmt.setString(4, kh.getCccd());
			stmt.setString(5, kh.getDiaChi());
			stmt.setDate(6, kh.getNgaySinh());
			stmt.setString(7, kh.getGioiTinh());
			stmt.setInt(8, kh.getDiemTichLuy());
			stmt.setDate(9, kh.getNgayDangKy());
			stmt.setString(10, kh.getMaKhangHang());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		return n>0;
	}

	public boolean huyKH(String ma) throws SQLException {
		KhachHang kh = new KhachHang();

		Connection con= ConnectDB.getConnection();
		String sql = "update KhachHang set maLoaiKH = N'LKH004' where maKhachHang = '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
	// này nó viết sai r, để t viết lại
	public KhachHang getKH(String info) { 
		KhachHang kh = new KhachHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sqlMa = "select * from KhachHang where maKhachHang = '"+info+"'";
		String sqlSDT = "select * from KhachHang where sdt = '"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sqlMa);
			while(rs.next()) {
				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));
			}

			ResultSet rsSDT = stm.executeQuery(sqlSDT);
			while(rsSDT.next()) {
				kh.setMaKhangHang(rsSDT.getString(1));
				kh.setLoaiKH(new LoaiKH(rsSDT.getString(2)));
				kh.setTenKH(rsSDT.getString(3));
				kh.setSdt(rsSDT.getString(4));
				kh.setCccd(rsSDT.getString(5));
				kh.setDiaChi(rsSDT.getString(6));
				kh.setNgaySinh(rsSDT.getDate(7));
				kh.setGioiTinh(rsSDT.getString(8));
				kh.setDiemTichLuy(rsSDT.getInt(9));
				kh.setNgayDangKy(rsSDT.getDate(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kh;
	}
	
	public KhachHang getKHTheoSDT(String sdt) { 
		KhachHang kh = new KhachHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sqlSDT = "select * from KhachHang where sdt = '"+sdt+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rsSDT = stm.executeQuery(sqlSDT);
			while(rsSDT.next()) {
				kh.setMaKhangHang(rsSDT.getString(1));
				kh.setLoaiKH(new LoaiKH(rsSDT.getString(2)));
				kh.setTenKH(rsSDT.getString(3));
				kh.setSdt(rsSDT.getString(4));
				kh.setCccd(rsSDT.getString(5));
				kh.setDiaChi(rsSDT.getString(6));
				kh.setNgaySinh(rsSDT.getDate(7));
				kh.setGioiTinh(rsSDT.getString(8));
				kh.setDiemTichLuy(rsSDT.getInt(9));
				kh.setNgayDangKy(rsSDT.getDate(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kh;
	}
	

	public ArrayList<KhachHang> sortByMa() {


		ArrayList<KhachHang> lsKH = new ArrayList<KhachHang>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select *from KhachHang where not maLoaiKH = N'LKH004' order by maKhachHang desc";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				KhachHang kh = new KhachHang();

				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));

				lsKH.add(kh);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsKH;
	}
	public ArrayList<KhachHang> getKHTheoLoai(String loaiKH) {
		ArrayList<KhachHang> lsKH=new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from KhachHang where maLoaiKH = N'"+loaiKH+"' ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				KhachHang kh = new KhachHang();

				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));

				lsKH.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lsKH;
	}

	//so khớp sdt nhập trong ddp, kq=true thì load từ sql, kq=false thì tạo mới KH
	public boolean matchedSdtKH(String sdt) {
		KhachHang kh=new KhachHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from KhachHang where sdt = '"+sdt+"'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				kh.setMaKhangHang(rs.getString(1));
				kh.setLoaiKH(new LoaiKH(rs.getString(2)));
				kh.setTenKH(rs.getString(3));
				kh.setSdt(rs.getString(4));
				kh.setCccd(rs.getString(5));
				kh.setDiaChi(rs.getString(6));
				kh.setNgaySinh(rs.getDate(7));
				kh.setGioiTinh(rs.getString(8));
				kh.setDiemTichLuy(rs.getInt(9));
				kh.setNgayDangKy(rs.getDate(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean themKHTheoDDP(KhachHang kh) throws SQLException {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into KhachHang (maKhachHang, maLoaiKH, tenKH, sdt, diaChi) values (?,?,?,?,?)");
			ps.setString(1, kh.getMaKhangHang());
			ps.setString(2, kh.getLoaiKH().getMaLoaiKH());
			ps.setString(3, kh.getTenKH());
			ps.setString(4, kh.getSdt());
			ps.setString(5, kh.getDiaChi());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
	//lay doituong loaiKH thông tin maLoai tu tenLoai
	public LoaiKH getMaLoaiKHFromTen(String tenLoaiKH) {
		LoaiKH lKH =new LoaiKH();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select maLoaiKH from LoaiKH where tenLoaiKH = N'"+tenLoaiKH+"'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
				lKH.setMaLoaiKH(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lKH;
	}
	
}
