package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.LoaiMatHang;

public class DAOLoaiMH {

	public ArrayList<LoaiMatHang> getAllLoaiMatHang() {
		ArrayList<LoaiMatHang> lsLoaiMH = new ArrayList<LoaiMatHang>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiMatHang";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maLoaiMH = rs.getString(1);
				String tenLoaiMH = rs.getString(2);
				LoaiMatHang loaiMH = new LoaiMatHang(maLoaiMH, tenLoaiMH);
				lsLoaiMH.add(loaiMH);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lsLoaiMH;
	}

	public LoaiMatHang getLoaiMHTheoMaLoai(String ma) {
		LoaiMatHang loaiMH = new LoaiMatHang();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from LoaiMatHang where maLoaiMH = '"+ma+"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				loaiMH.setMaLoaiMatHang(rs.getNString(1));
				loaiMH.setTenLoaiMatHang(rs.getNString(2));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loaiMH;
	}


	public String getMaLoaiMHTheoTen(String ten) {
		String maLoai ="";
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select maLoaiMH from LoaiMatHang where tenLoaiMH = N'"+ten +"'";

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
