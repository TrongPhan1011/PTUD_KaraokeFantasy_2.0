package entity;

import java.io.Serializable;

public class LoaiKH implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maLoaiKH;
	private String tenLoaiKH;
	
	public String getMaLoaiKH() {
		return maLoaiKH;
	}
	public void setMaLoaiKH(String maLoaiKH) {
		this.maLoaiKH = maLoaiKH;
	}
	public String getTenLoaiKH() {
		return tenLoaiKH;
	}
	public void setTenLoaiKH(String tenLoaiKH) {
		this.tenLoaiKH = tenLoaiKH;
	}
	
	public LoaiKH(String maLoaiKH, String tenLoaiKH) {
		super();
		this.maLoaiKH = maLoaiKH;
		this.tenLoaiKH = tenLoaiKH;
	}
	public LoaiKH() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoaiKH(String maLoaiKH) {
		super();
		this.maLoaiKH = maLoaiKH;
	}
	
	@Override
	public String toString() {
		return "LoaiKH [maLoaiKH=" + maLoaiKH + ", tenLoaiKH=" + tenLoaiKH + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maLoaiKH == null) ? 0 : maLoaiKH.hashCode());
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
		LoaiKH other = (LoaiKH) obj;
		if (maLoaiKH == null) {
			if (other.maLoaiKH != null)
				return false;
		} else if (!maLoaiKH.equals(other.maLoaiKH))
			return false;
		return true;
	}
	
	


	

}
