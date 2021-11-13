package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class DonDatPhong implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maDDP;
	private Date ngayLap;
	private String trangThaiDDP;
	private Date ngayDen;
	private Time gioDen;
	
	
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private Phong phong;
	public String getMaDDP() {
		return maDDP;
	}
	public void setMaDDP(String maDDP) {
		this.maDDP = maDDP;
	}
	public Date getNgayLap() {
		return ngayLap;
	}
	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}
	public String getTrangThaiDDP() {
		return trangThaiDDP;
	}
	public void setTrangThaiDDP(String trangThaiDDP) {
		this.trangThaiDDP = trangThaiDDP;
	}
	public Date getNgayDen() {
		return ngayDen;
	}
	public void setNgayDen(Date ngayDen) {
		this.ngayDen = ngayDen;
	}
	public Time getGioDen() {
		return gioDen;
	}
	public void setGioDen(Time gioDen) {
		this.gioDen = gioDen;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public DonDatPhong(String maDDP, Date ngayLap, String trangThaiDDP, Date ngayDen, Time gioDen, KhachHang khachHang,
			NhanVien nhanVien, Phong phong) {
		super();
		this.maDDP = maDDP;
		this.ngayLap = ngayLap;
		this.trangThaiDDP = trangThaiDDP;
		this.ngayDen = ngayDen;
		this.gioDen = gioDen;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.phong = phong;
	}
	public DonDatPhong(String maDDP) {
		super();
		this.maDDP = maDDP;
	}
	public DonDatPhong() {
		super();
	}
	@Override
	public String toString() {
		return "DonDatPhong [maDDP=" + maDDP + ", ngayLap=" + ngayLap + ", trangThaiDDP=" + trangThaiDDP + ", ngayDen="
				+ ngayDen + ", gioDen=" + gioDen + ", khachHang=" + khachHang + ", nhanVien=" + nhanVien + ", phong="
				+ phong + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maDDP == null) ? 0 : maDDP.hashCode());
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
		DonDatPhong other = (DonDatPhong) obj;
		if (maDDP == null) {
			if (other.maDDP != null)
				return false;
		} else if (!maDDP.equals(other.maDDP))
			return false;
		return true;
	}
	
	
	
	

}
