package entity;

import java.io.Serializable;
import java.sql.Date;

public class KhachHang implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maKhangHang;
	private String tenKH;
	private String diaChi;
	private String sdt;
	private String cccd;
	private Date ngaySinh;
	private String gioiTinh;
	private int diemTichLuy;
	private Date ngayDangKy;
	private LoaiKH loaiKH;
	
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaKhangHang() {
		return maKhangHang;
	}
	public void setMaKhangHang(String maKhangHang) {
		this.maKhangHang = maKhangHang;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public int getDiemTichLuy() {
		return diemTichLuy;
	}
	public void setDiemTichLuy(int diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}
	public Date getNgayDangKy() {
		return ngayDangKy;
	}
	public void setNgayDangKy(Date ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}
	public LoaiKH getLoaiKH() {
		return loaiKH;
	}
	public void setLoaiKH(LoaiKH loaiKH) {
		this.loaiKH = loaiKH;
	}
	
	public KhachHang(String maKhangHang, String tenKH, String diaChi, String sdt, String cccd, Date ngaySinh,
			String gioiTinh, int diemTichLuy, Date ngayDangKy, LoaiKH loaiKH) {
		super();
		this.maKhangHang = maKhangHang;
		this.tenKH = tenKH;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.cccd = cccd;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.diemTichLuy = diemTichLuy;
		this.ngayDangKy = ngayDangKy;
		this.loaiKH = loaiKH;
	}
	
	public KhachHang() {
		super();
	}
	
	public KhachHang(String maKhangHang) {
		super();
		this.maKhangHang = maKhangHang;
	}
	
	
	public KhachHang(String maKH, String tenKH2, String diaChi2, String sdt2, String cccd2, Date ngaySinh2,
			String gioiTinh2, int diemTichLuy2, Date ngayDangKy2, KhachHang tenLoaiKH) {
	}
	
	public KhachHang(String maKhangHang, LoaiKH loaiKH, String tenKH, String sdt, String diaChi) {
		super();
		this.maKhangHang = maKhangHang;
		this.loaiKH = loaiKH;
		this.tenKH = tenKH;
		this.sdt = sdt;
		this.diaChi = diaChi;
	}
	
	@Override
	public String toString() {
		return "KhachHang [maKhangHang=" + maKhangHang + ", tenKH=" + tenKH + ", diaChi=" + diaChi + ", sdt=" + sdt
				+ ", cccd=" + cccd + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh + ", diemTichLuy="
				+ diemTichLuy + ", ngayDangKy=" + ngayDangKy + ", loaiKH=" + loaiKH + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maKhangHang == null) ? 0 : maKhangHang.hashCode());
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
		KhachHang other = (KhachHang) obj;
		if (maKhangHang == null) {
			if (other.maKhangHang != null)
				return false;
		} else if (!maKhangHang.equals(other.maKhangHang))
			return false;
		return true;
	}

	
	
}
