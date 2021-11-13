package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.LoaiMatHang;
import entity.MatHang;

public class DAOMatHang {
	public ArrayList<MatHang> getDSMatHang() {
		ArrayList<MatHang> dsMH = new ArrayList<MatHang>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang";
		
		try {
			java.sql.Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				MatHang mh = new MatHang();
				mh.setMaMatHang(rs.getString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getString(2)));
				mh.setTenMatHang(rs.getString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				dsMH.add(mh);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return dsMH;
	}
	
	public MatHang getMHTheoMaMH(String ma) {
		
		MatHang mh = new MatHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang where maMH = '"+ ma +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				mh.setMaMatHang(rs.getNString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getNString(2)));
				mh.setTenMatHang(rs.getNString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mh;
	}
	public MatHang getMHTheoTenMH(String ma) {	
		MatHang mh = new MatHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang where tenMH = '"+ ma +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				mh.setMaMatHang(rs.getNString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getNString(2)));
				mh.setTenMatHang(rs.getNString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mh;
	}
	public ArrayList<MatHang> getMatHangTheoMaLoai(String Maloai) {
		ArrayList< MatHang> lsMatHang = new ArrayList<MatHang>();
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang where MaLoaiMH = '"+Maloai +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				MatHang mh = new MatHang();
				
				mh.setMaMatHang(rs.getNString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getNString(2)));
				mh.setTenMatHang(rs.getNString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				
				lsMatHang.add(mh);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsMatHang;
		
	}
	public ArrayList<MatHang> getMatHangTheoTenMatHang(String Ten) {
		ArrayList< MatHang> lsMatHang = new ArrayList<MatHang>();
		
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang where tenMH like N '"+Ten +"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				MatHang mh = new MatHang();
				
				mh.setMaMatHang(rs.getNString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getNString(2)));
				mh.setTenMatHang(rs.getNString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				
				lsMatHang.add(mh);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lsMatHang;
		
	}
	public MatHang getMHTheoTenMHVaLoaiMH(String tenMH, String tenLoai) {
		MatHang mh = new MatHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT MatHang.*\r\n"
				+ "FROM  LoaiMatHang INNER JOIN\r\n"
				+ "         MatHang ON LoaiMatHang.maLoaiMH = MatHang.maLoaiMH\r\n"
				+ "Where tenMH = N'"+tenMH+"' and tenLoaiMH = N'"+tenLoai+"'";
		
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				mh.setMaMatHang(rs.getNString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getNString(2)));
				mh.setTenMatHang(rs.getNString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mh;
		
	}
	public boolean ThemMH(MatHang mh) {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "insert into MatHang values (?,?,?,?,?)";
		PreparedStatement stm = null;
		int n = 0;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, mh.getMaMatHang());
			stm.setString(2, mh.getLoaiMatHang().getMaLoaiMatHang());
			stm.setString(3, mh.getTenMatHang());
			stm.setInt(4, mh.getSoLuongMatHang());
			stm.setDouble(5, mh.getGiaMatHang());
			n= stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public boolean XoaMH(String maMH) {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "delete from MatHang where maMH = ?";
		PreparedStatement stm = null;
		int n = 0;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, maMH);
			n = stm.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}
	public boolean updateMH(MatHang mh) {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "update MatHang set tenMH = ?, maLoaiMH = ?, soLuongMH = ?, giaMH = ? where maMH = ?";
		PreparedStatement stm = null;
		int n = 0;
		try {
			stm = con.prepareStatement(sql);
			stm.setString(1, mh.getTenMatHang());
			stm.setString(2, mh.getLoaiMatHang().getMaLoaiMatHang());
			stm.setInt(3, mh.getSoLuongMatHang());
			stm.setDouble(4, mh.getGiaMatHang());
			stm.setString(5, mh.getMaMatHang());
			n= stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public ArrayList<MatHang> sortGia(String ksx) {
		ArrayList<MatHang> lstMH = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang	order by giaMH "+ksx+"";
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				MatHang mh = new MatHang();
				mh.setMaMatHang(rs.getString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getString(2)));
				mh.setTenMatHang(rs.getString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				lstMH.add(mh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstMH;
	}
	public ArrayList<MatHang> sortLMH(String ksx) {
		ArrayList<MatHang> lstMH = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang	order by maLoaiMH "+ksx+"";
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				MatHang mh = new MatHang();
				mh.setMaMatHang(rs.getString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getString(2)));
				mh.setTenMatHang(rs.getString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				lstMH.add(mh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstMH;
	}
	public ArrayList<MatHang> getTenMH(String tenMH) {
		ArrayList<MatHang> lstMH = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang	where tenMH like N'"+tenMH+"'";
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				MatHang mh = new MatHang();
				mh.setMaMatHang(rs.getString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getString(2)));
				mh.setTenMatHang(rs.getString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				lstMH.add(mh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstMH;
	}
	public ArrayList<MatHang> getLMH(String tenLMH) {
		ArrayList<MatHang> lstMH = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang	where maLoaiMH like N'"+tenLMH+"'";
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				MatHang mh = new MatHang();
				mh.setMaMatHang(rs.getString(1));
				mh.setLoaiMatHang(new LoaiMatHang(rs.getString(2)));
				mh.setTenMatHang(rs.getString(3));
				mh.setSoLuongMatHang(rs.getInt(4));
				mh.setGiaMatHang(rs.getDouble(5));
				lstMH.add(mh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstMH;
	}
}
