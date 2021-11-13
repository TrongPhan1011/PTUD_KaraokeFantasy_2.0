package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.table.TableColumn;

import connection.ConnectDB;
import entity.*;
import dao.*;

public class DAONhanVien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DAOPhatSinhMa daoPhatSinhMa;
	
	//Load tat ca sd NV
	public ArrayList<NhanVien> getAllDanhSachNV() {
		ArrayList<NhanVien> lstNV=new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from [dbo].[NhanVien]");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				lstNV.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstNV;
	}

	//Load ds NV dang lam viec
	public ArrayList<NhanVien> getDanhSachNV() {
		ArrayList<NhanVien> lstNV=new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from [dbo].[NhanVien] where trangThaiLamViec = N'Đang làm việc'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				lstNV.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstNV;
	}
	
	//Load 1 NV dang lam viec theo manv, sdt
	public NhanVien getMaVaSDTNV(String info) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sqlMa = "select * from [dbo].[NhanVien] where maNhanVien = '"+info+"'";
		String sqlSDT = "select * from [dbo].[NhanVien] where sdt = '"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rsMa = stm.executeQuery(sqlMa);
			while(rsMa.next()) {
				nv.setMaNhanVien(rsMa.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsMa.getString(2)));
				nv.setTenNhanVien(rsMa.getString(3));
				nv.setChucVu(rsMa.getString(4));
				nv.setGioiTinh(rsMa.getString(5));
				nv.setNgaySinh(rsMa.getDate(6));
				nv.setDiaChi(rsMa.getString(7));
				nv.setSdt(rsMa.getString(8));
				nv.setCccd(rsMa.getString(9));
				nv.setLuong(rsMa.getDouble(10));
				nv.setCaLamViec(rsMa.getInt(11));
				nv.setTrangThaiLamViec(rsMa.getString(12));
			}
			
			ResultSet rsSDT = stm.executeQuery(sqlSDT);
			while(rsSDT.next()) {
				nv.setMaNhanVien(rsSDT.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsSDT.getString(2)));
				nv.setTenNhanVien(rsSDT.getString(3));
				nv.setChucVu(rsSDT.getString(4));
				nv.setGioiTinh(rsSDT.getString(5));
				nv.setNgaySinh(rsSDT.getDate(6));
				nv.setDiaChi(rsSDT.getString(7));
				nv.setSdt(rsSDT.getString(8));
				nv.setCccd(rsSDT.getString(9));
				nv.setLuong(rsSDT.getDouble(10));
				nv.setCaLamViec(rsSDT.getInt(11));
				nv.setTrangThaiLamViec(rsSDT.getString(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}
	
	
	public NhanVien getNVTheoMa(String ma) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where maNhanVien = '"+ma+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}
	
	//Load 1 NV dang lam viec theo tenNV
	public ArrayList<NhanVien> getTenNV(String info) { 
		ArrayList<NhanVien> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where tenNhanVien like N'"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		
	//Load 1 NV dang lam viec theo chucvu
	public ArrayList<NhanVien> getChucVuNV(String info) { 
		ArrayList<NhanVien> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where chucVu like N'"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		
	//Load 1 NV dang lam viec theo ca
	public ArrayList<NhanVien> getCaNV(String info) { 
		ArrayList<NhanVien> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where caLamViec = '"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
				
	//Load 1 NV da nghi viec theo manv
	public NhanVien getNVDaNghiViec(String ma) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
//		String sql = "select * from NhanVien where maNhanVien = '"+ma+"'";
		String sql = "select * from [dbo].[NhanVien] where maNhanVien = '"+ma+"' and trangThaiLamViec = N'Đã nghỉ việc'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}
	
	public NhanVien getNVTheoTK(String maTK) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from NhanVien where maTK = '"+maTK+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getNString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}
	
	//them NV
	public boolean themNV(NhanVien nv) throws SQLException {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "insert into NhanVien values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nv.getMaNhanVien());
			ps.setString(2, nv.getTaiKhoan().getMaTK());
			ps.setString(3, nv.getTenNhanVien());
			ps.setString(4, nv.getChucVu());
			ps.setString(5, nv.getGioiTinh());
			ps.setDate(6, nv.getNgaySinh());
			ps.setString(7, nv.getDiaChi());
			ps.setString(8, nv.getSdt());
			ps.setString(9, nv.getCccd());
			ps.setDouble(10, nv.getLuong());
			ps.setInt(11, nv.getCaLamViec());
			ps.setString(12, nv.getTrangThaiLamViec());
			
			return ps.executeUpdate() > 0;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
	//huyNV, chuyen trangThaiLamViec
	public boolean huyNV(String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "update NhanVien set trangThaiLamViec = N'Đã nghỉ việc' where maNhanVien = '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}
	
	//capnhat-sua NV
	public boolean capNhatNV(NhanVien nv, String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "update NhanVien set tenNhanVien = ?, chucVu = ?, gioiTinh = ?, ngaySinh = ?, diaChi = ?, sdt = ?, cccd = ?, caLamViec = ? where maNhanVien = '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nv.getTenNhanVien());
			ps.setString(2, nv.getChucVu());
			ps.setString(3, nv.getGioiTinh());
			ps.setDate(4, nv.getNgaySinh());
			ps.setString(5, nv.getDiaChi());
			ps.setString(6, nv.getSdt());
			ps.setString(7, nv.getCccd());
			ps.setInt(8, nv.getCaLamViec());
			
			return ps.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		con.close();
		return false;
		
	}
	
	//sapxep maNV cua nv dang lam viec
		public ArrayList<NhanVien> sortMaNV(String kieuSapXep) {
			ArrayList<NhanVien> lstNV=new ArrayList<>();
			ConnectDB.getinstance();
			Connection con = ConnectDB.getConnection();
			try {
				PreparedStatement ps = con.prepareStatement("select * from NhanVien where trangThaiLamViec = N'Đang làm việc' "
														  + "order by maNhanVien "+kieuSapXep+"");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					NhanVien nv=new NhanVien();
					nv.setMaNhanVien(rs.getString(1));
					nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
					nv.setTenNhanVien(rs.getString(3));
					nv.setChucVu(rs.getString(4));
					nv.setGioiTinh(rs.getString(5));
					nv.setNgaySinh(rs.getDate(6));
					nv.setDiaChi(rs.getString(7));
					nv.setSdt(rs.getString(8));
					nv.setCccd(rs.getString(9));
					nv.setLuong(rs.getDouble(10));
					nv.setCaLamViec(rs.getInt(11));
					nv.setTrangThaiLamViec(rs.getString(12));
					lstNV.add(nv);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lstNV;
		}
	
	//sapxep chucvu cua nv dang lam viec
	public ArrayList<NhanVien> sortCV(String tenChucVu) {
		ArrayList<NhanVien> lstNV=new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from NhanVien where chucVu = N'"+tenChucVu+"' and trangThaiLamViec = N'Đang làm việc'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				nv.setCaLamViec(rs.getInt(11));
				nv.setTrangThaiLamViec(rs.getString(12));
				lstNV.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstNV;
	}
				
}
