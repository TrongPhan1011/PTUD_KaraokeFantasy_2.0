package entity;

import java.io.Serializable;

public class Phong implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maPhong;
	private String tinhTrangPhong;
	private double giaPhong;
	
	private LoaiPhong loaiPhong;

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getTinhTrangPhong() {
		return tinhTrangPhong;
	}

	public void setTinhTrangPhong(String tinhTrangPhong) {
		this.tinhTrangPhong = tinhTrangPhong;
	}

	public double getGiaPhong() {
		return giaPhong;
	}

	public void setGiaPhong(double giaPhong) {
		this.giaPhong = giaPhong;
	}

	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	public Phong(String maPhong, String tinhTrangPhong, double giaPhong, LoaiPhong loaiPhong) {
		super();
		this.maPhong = maPhong;
		this.tinhTrangPhong = tinhTrangPhong;
		this.giaPhong = giaPhong;
		this.loaiPhong = loaiPhong;
	}
	public Phong() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}

	@Override
	public String toString() {
		return "Phong [maPhong=" + maPhong + ", tinhTrangPhong=" + tinhTrangPhong + ", giaPhong=" + giaPhong
				+ ", loaiPhong=" + loaiPhong + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maPhong == null) ? 0 : maPhong.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		if (maPhong == null) {
			if (other.maPhong != null)
				return false;
		} else if (!maPhong.equals(other.maPhong))
			return false;
		return true;
	}
	
	
	

}
