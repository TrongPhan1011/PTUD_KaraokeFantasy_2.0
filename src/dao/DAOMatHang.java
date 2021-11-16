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
	/**
	 * Thêm một mặt hàng vào bảng mặt hàng trong SQL server
	 * @param mh
	 * @return
	 */
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
	/**
	 * Cập nhật toàn bộ thông tin mặt hàng trên SQL server với các giá trị theo thứ tự
	 * @param mh
	 * @return n>0
	 */
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
	/**
	 * Lấy danh sách các mặt hàng từ bảng mặt hàng
	 * @return ArrayList<MatHang> dsMH 
	 */
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
	/**
	 * Lấy danh sách cách mặt hàng theo mã mặt hàng trong bảng hàng từ SQL Server
	 * @param ma
	 * @return MatHang  mh
	 */
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
	/**
	 * Lấy mặt hàng theo tên mặt hàng trong bảng mặt hàng từ SQL Server
	 * @param ma
	 * @return
	 */
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
	/**
	 * Lấy mặt hàng theo mã loại mặt hàng trong bảng mặt hàng từ SQL Server
	 * @param Maloai
	 * @return ArrayList<MatHang> lstMatHang
	 */
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
	/**
	 * Lấy mặt hàng theo tên mặt hàng trong bảng mặt hàng từ SQL Server
	 * @param Ten
	 * @return ArrayList<MatHang> lstMatHang
	 */
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
	/**
	 * Lấy mặt hàng theo tên mặt hàng và loại mặt hàng từ bảng mặt hàng và loại mặt hàng trong SQL Server
	 * @param tenMH
	 * @param tenLoai
	 * @return MatHang mh
	 */
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
	/**
	 * Sắp xếp giá mặt hàng theo kiểu tăng dần/ giảm dần thông qua chuỗi truyền vào trong bảng mặt hàng từ SQL Server
	 * @param ksx
	 * @return ArrayList<MatHang> lstMatHang
	 */
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
	/**
	 * Sắp xếp mã loại mặt hàng theo kiểu sắp xếp truyền vào từ bảng loại mặt hàng trong SQL Server
	 * @param ksx
	 * @return ArrayList<MatHang> lstMH
	 */
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
	/**
	 * Lấy tên mặt hàng với tên cần tìm được truyền vào trong bảng mặt hàng từ SQL Server
	 * @param tenMH
	 * @return ArrayList<MatHang> lstMH
	 */
	public ArrayList<MatHang> getTenMH(String tenMH) {
		ArrayList<MatHang> lstMH = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from MatHang	where tenMH like N'%"+tenMH+"%'";
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
	/**
	 * Lấy toàn bộ thông tin bảng loại mặt hàng theo mã loại mặt hàng được truyền vào 
	 * @param tenLMH
	 * @return ArrayList<MatHang> lstMH
	 */
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
