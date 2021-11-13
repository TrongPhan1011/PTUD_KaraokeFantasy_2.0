package entity;

import java.io.Serializable;

public class LoaiMatHang implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maLoaiMatHang;
	private String tenLoaiMatHang;
	
	public String getMaLoaiMatHang() {
		return maLoaiMatHang;
	}
	public void setMaLoaiMatHang(String maLoaiMatHang) {
		this.maLoaiMatHang = maLoaiMatHang;
	}
	public String getTenLoaiMatHang() {
		return tenLoaiMatHang;
	}
	public void setTenLoaiMatHang(String tenLoaiMatHang) {
		this.tenLoaiMatHang = tenLoaiMatHang;
	}
	
	public LoaiMatHang(String maLoaiMatHang, String tenLoaiMatHang) {
		super();
		this.maLoaiMatHang = maLoaiMatHang;
		this.tenLoaiMatHang = tenLoaiMatHang;
	}
	public LoaiMatHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoaiMatHang(String maLoaiMatHang) {
		super();
		this.maLoaiMatHang = maLoaiMatHang;
	}
	
	@Override
	public String toString() {
		return "LoaiMatHang [maLoaiMatHang=" + maLoaiMatHang + ", tenLoaiMatHang=" + tenLoaiMatHang + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maLoaiMatHang == null) ? 0 : maLoaiMatHang.hashCode());
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
		LoaiMatHang other = (LoaiMatHang) obj;
		if (maLoaiMatHang == null) {
			if (other.maLoaiMatHang != null)
				return false;
		} else if (!maLoaiMatHang.equals(other.maLoaiMatHang))
			return false;
		return true;
	}
	
	
	

}
