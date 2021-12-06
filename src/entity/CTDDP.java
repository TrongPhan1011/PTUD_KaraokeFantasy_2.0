package entity;

import java.io.Serializable;

public class CTDDP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DonDatPhong donDatPhong;
	private int soLuongMH;
	private MatHang matHang;
	public DonDatPhong getDonDatPhong() {
		return donDatPhong;
	}
	public void setDonDatPhong(DonDatPhong donDatPhong) {
		this.donDatPhong = donDatPhong;
	}
	public int getSoLuongMH() {
		return soLuongMH;
	}
	public void setSoLuongMH(int soLuongMH) {
		this.soLuongMH = soLuongMH;
	}
	public MatHang getMatHang() {
		return matHang;
	}
	public void setMatHang(MatHang matHang) {
		this.matHang = matHang;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public CTDDP(DonDatPhong donDatPhong, int soLuongMH, MatHang matHang) {
		super();
		this.donDatPhong = donDatPhong;
		this.soLuongMH = soLuongMH;
		this.matHang = matHang;
	}
	public CTDDP() {
		super();
	}
	public CTDDP(DonDatPhong donDatPhong, MatHang matHang) {
		super();
		this.donDatPhong = donDatPhong;
		this.matHang = matHang;
	}
	@Override
	public String toString() {
		return "CTDDP [donDatPhong=" + donDatPhong + ", soLuongMH=" + soLuongMH + ", matHang=" + matHang + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donDatPhong == null) ? 0 : donDatPhong.hashCode());
		result = prime * result + ((matHang == null) ? 0 : matHang.hashCode());
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
		CTDDP other = (CTDDP) obj;
		if (donDatPhong == null) {
			if (other.donDatPhong != null)
				return false;
		} else if (!donDatPhong.equals(other.donDatPhong))
			return false;
		if (matHang == null) {
			if (other.matHang != null)
				return false;
		} else if (!matHang.equals(other.matHang))
			return false;
		return true;
	}
	
	
	
	

}
