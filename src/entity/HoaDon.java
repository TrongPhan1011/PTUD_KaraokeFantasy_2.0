package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class HoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maHoaDon;
	private Date ngayLap;
	private Time gioVao;
	private Time gioRa;
	private String phuThu;
	private String trangThaiHD;
	private double giamGia;
	
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private Phong phong;
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public Date getNgayLap() {
		return ngayLap;
	}
	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}
	public Time getGioVao() {
		return gioVao;
	}
	public void setGioVao(Time gioVao) {
		this.gioVao = gioVao;
	}
	public Time getGioRa() {
		return gioRa;
	}
	public void setGioRa(Time gioRa) {
		this.gioRa = gioRa;
	}
	public String getPhuThu() {
		return phuThu;
	}
	public void setPhuThu(String phuThu) {
		this.phuThu = phuThu;
	}
	public String getTrangThaiHD() {
		return trangThaiHD;
	}
	public void setTrangThaiHD(String trangThaiHD) {
		this.trangThaiHD = trangThaiHD;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	
	public double getGiamGia() {
		return giamGia;
	}
	public void setGiamGia(double giamGia) {
		this.giamGia = giamGia;
	}
	public HoaDon(String maHoaDon, Date ngayLap, Time gioVao, Time gioRa, String phuThu, String trangThaiHD,double giamGia,
			NhanVien nhanVien, KhachHang khachHang, Phong phong) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLap = ngayLap;
		this.gioVao = gioVao;
		this.gioRa = gioRa;
		this.phuThu = phuThu;
		this.trangThaiHD = trangThaiHD;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;
		this.giamGia = giamGia;
	}
	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}
	public HoaDon() {
		super();
	}
	
	//tinh thanh tien
	public double tinhThanhTien() {
		double thanhTien = 0;
		return thanhTien;
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLap=" + ngayLap + ", gioVao=" + gioVao + ", gioRa=" + gioRa
				+ ", phuThu=" + phuThu + ", trangThaiHD=" + trangThaiHD + ", nhanVien=" + nhanVien + ", khachHang="
				+ khachHang + ", phong=" + phong + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maHoaDon == null) ? 0 : maHoaDon.hashCode());
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
		HoaDon other = (HoaDon) obj;
		if (maHoaDon == null) {
			if (other.maHoaDon != null)
				return false;
		} else if (!maHoaDon.equals(other.maHoaDon))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
