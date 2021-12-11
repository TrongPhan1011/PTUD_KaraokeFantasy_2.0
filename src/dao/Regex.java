package dao;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class Regex {
	public boolean regexDiaChi(JTextField txtDiaChi) {
		String input = txtDiaChi.getText().trim();
		String regex = "^([ A-Za-z0-9,\\/.a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ!\nMẫu địa chỉ:56a Cầu Xéo, Tân quí, Tân Phú",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtDiaChi.requestFocus();
			txtDiaChi.selectAll();
			return false;
		} else
			return true;
	}

	public boolean regexTen(JTextField txtTen2) {
		String input = txtTen2.getText().trim();
		String regex = "^([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Họ tên không hợp lệ!\nMẫu họ tên: Nguyễn Văn A", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtTen2.requestFocus();
			txtTen2.selectAll();
			return false;
		} else
			return true;
	}

	public boolean regexSDT(JTextField txtSDT) {
		String input = txtSDT.getText().trim();
		String regex = "^(0[0-9]{9}$)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "SĐT không hợp lệ!\nSĐT gồm 10 chữ số và bắt đầu bằng số 0",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtSDT.requestFocus();
			txtSDT.selectAll();
			return false;
		} else
			return true;
	}

	public boolean regexCCCD(JTextField txtCCCD) {
		String input = txtCCCD.getText().trim();
		String regex = "^([0-9]{12}$)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "CCCD không hợp lệ!\nCCCD gồm 12 chữ số", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtCCCD.requestFocus();
			txtCCCD.selectAll();
			return false;
		} else
			return true;
	}

	public boolean regexSoLuong(JTextField txtSoluong) {
		String input = txtSoluong.getText().trim();
		String regex = "^[1-9]+[0-9]*$";
		if (!input.matches(regex)) {
			JOptionPane.showMessageDialog(null,
					"Số lượng không được để trống, không được nhập chữ và phải lớn hơn 0\nVí dụ: 10", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtSoluong.requestFocus();
			txtSoluong.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexGiaP(JTextField txtGiaP) {
		String input = txtGiaP.getText();
		String regex = "^[1-9]+[0-9]*$";
		if (!input.matches(regex)) {
			JOptionPane.showMessageDialog(null,
					"Giá phòng không được để trống, không được nhập chữ và phải lớn hơn 0\nVí dụ: 1000000", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtGiaP.requestFocus();
			txtGiaP.selectAll();
			return false;
		}
		return true;
	}
	public boolean regexGiaMH(JTextField txtGiaMH) {
		String input = txtGiaMH.getText();
		String regex = "^[1-9]+[0-9]*$";
		if(!input.matches(regex))
		{	JOptionPane.showMessageDialog(null, "Giá mặt hàng không được để trống, không được nhập chữ và phải lớn hơn 0\nVí dụ: 10", "Thông báo", JOptionPane.ERROR_MESSAGE);
			txtGiaMH.requestFocus();
			txtGiaMH.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimKiemMaPhong(JTextField txtTim) {
		String input = txtTim.getText();
		String regex = "^((P|p)[0-9]{3})$";
		if (!input.matches(regex)) {
			
			  JOptionPane.showMessageDialog(null,"Thông tin tìm kiếm không hợp lệ\nThông tin có thể tìm kiếm:\n - Mã Phòng. Ví dụ: P003\n", "Thông báo", JOptionPane.ERROR_MESSAGE);
			 
			txtTim.requestFocus();
			txtTim.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimKiemMaNV(JTextField txtTim) {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		String regexMaNV = "^NV[0-9]{3}$";
		if (!input.matches(regexMaNV)) {
			JOptionPane.showMessageDialog(null, "Mã nhân viên tìm kiếm không hợp lệ\nMã nhân viên. Ví dụ: NV001\n");
			txtTim.requestFocus();
			txtTim.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimNV(JTextField txtTim) {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		String regexMaNV = "((NV|nv)[0-9]{3})|";
		String regexTenNV = "([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+)|";
		String regexSDT = "(0[0-9]{9})|";
		String regexCa = "([1-3]{1})";
		String regexNV = "^(" + regexMaNV + regexTenNV + regexSDT + regexCa + ")$";
		
		if (!input.matches(regexNV)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã nhân viên. Ví dụ: NV001 hoặc nv001"
							+ "\n - Tên nhân viên. Ví dụ: Trần Thanh Thiện hoặc Thanh Thiện"
							+ "\n - SĐT gồm 10 chữ số và bắt đầu bằng số 0" + "\n - Tìm theo ca: 1, 2, 3",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtTim.requestFocus();
			txtTim.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimKiemMaKH(JTextField txtTK) {
		String input = txtTK.getText().trim();
		input = input.toUpperCase();
		String regexMaKH = "^(KH[0-9]{3})$";
		if (!input.matches(regexMaKH)) {
			JOptionPane.showMessageDialog(null, "Mã khách hàng không hợp lệ!\n - Ví dụ mã khách hàng: KH001");
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimKiemLoaiKH(JTextField txtTK) {
		String in = txtTK.getText().toLowerCase().trim();

		String regexThuong = "^[khách hàng thường]*+$";
		Pattern patternThuong = Pattern.compile(regexThuong);
		Matcher matcherThuong = patternThuong.matcher(in);

		String regexMember = "^[thành viên thường]*+$";
		Pattern patternMember = Pattern.compile(regexMember);
		Matcher matcherMember = patternMember.matcher(in);

		String regexVip = "^[thành viên vip]*+$";
		Pattern patternVip = Pattern.compile(regexVip);
		Matcher matcherVip = patternVip.matcher(in);

		String regexStop = "^[không còn là khách hàng]*+$";
		Pattern patternStop = Pattern.compile(regexStop);
		Matcher matcherStop = patternStop.matcher(in);

		if (!matcherThuong.find() && !matcherMember.find() && !matcherVip.find() && !matcherStop.find()) {
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		} else
			return true;
	}
	public boolean regexTenMH(JTextField txtTen2) {
		String input = txtTen2.getText().trim();
		String regex = "^([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find()) {
			JOptionPane.showMessageDialog(null, "Tên không hợp lệ!\nMẫu tên: Bia Heniken, trái cây,...", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtTen2.requestFocus();
			txtTen2.selectAll();
			return false;
		} else
			return true;
		}
	public boolean regexTimMH(JTextField txtTen2) {
		String input = txtTen2.getText();
		String regex = "^([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]*(\\s?))+$";
		if (input.matches(regex)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Tên mặt hàng. Ví dụ: Bia Heiniken,..."
							+ "\n - Loại mặt hàng. Ví dụ: Nước uống, đồ ăn.",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtTen2.requestFocus();
			txtTen2.selectAll();
			return false;
		} else
			return true;

	}

	@SuppressWarnings("deprecation")
	public boolean regexNVTren18(JDateChooser dateChooser) {
		LocalDate dNow = LocalDate.now();
		int nam = dNow.getYear();
		int day = dateChooser.getDate().getDay();
		int monnth = dateChooser.getDate().getMonth();
		int year = dateChooser.getDate().getYear();
		if (day <= 0 || day > 31 || monnth <= 0 || monnth > 12 || year <= 0 || year > (nam - 18)) {
			JOptionPane.showMessageDialog(null, "Nhân viên phải trên 18 tuổi!", "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean regexTimDSHD(JTextField txtDSHD) {
		String input = txtDSHD.getText().toString().trim();
		input = input.toUpperCase();
		String regexMaNV = "^((NV|nv)[0-9]{3})|([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+)$";
		if (!input.matches(regexMaNV)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã nhân viên Ví dụ: NV001\n - Tên Khách hàng Ví dụ Hợp hoặc Nguyễn Thị Bích Hợp...");
			txtDSHD.requestFocus();
			txtDSHD.selectAll();
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean regexMatKhau(JPasswordField pwMK) {
		String input = pwMK.getText().toString().trim();
		String regexMaNV = "^[A-Z][a-zA-Z0-9 ]{5,}$";
		if (!input.matches(regexMaNV)) {
			JOptionPane.showMessageDialog(null,
					"Mật khẩu không hợp lệ\nMật khẩu hợp lệ:\n - Kí tự đầu tiên là chữ viết hoa.\n - Có ít nhất 6 kí tự, chỉ chứa chữ, số, và khoảng trắng.\nVí dụ: T123abc hoặc DangNhap123");
			pwMK.requestFocus();
			pwMK.selectAll();
			return false;
		}
		return true;
	}


	public boolean regexTimKiemLoaiMatHang(JTextField ten) {
		String in = ten.getText().toLowerCase().trim();

		String regexDA = "^[đồ ăn]*+$";
		Pattern patternDA = Pattern.compile(regexDA);
		Matcher matcherDA = patternDA.matcher(in);

		String regexDU = "^[đồ uống]*+$";
		Pattern patternDU = Pattern.compile(regexDU);
		Matcher matcherDU = patternDU.matcher(in);

		String regexKhac = "^[khác]*+$";
		Pattern patternKhac = Pattern.compile(regexKhac);
		Matcher matcherKhac = patternKhac.matcher(in);

		String regexStop = "^[ngừng kinh doanh]*+$";
		Pattern patternStop = Pattern.compile(regexStop);
		Matcher matcherStop = patternStop.matcher(in);
		
		if (!matcherDA.find() && !matcherDU.find() && !matcherKhac.find() && !matcherStop.find()) {
			ten.requestFocus();
			ten.selectAll();
			return false;
		} else
			return true;
	}

	public boolean regexTimKiemGioiTinh(JTextField txtTim) {
		String input = txtTim.getText();
		String regexNam = "^[nam]$";
		String regexNu = "^[nữ]$";
		Pattern patternNam = Pattern.compile(regexNam);
		Matcher matcherNam = patternNam.matcher(input);
		if (!matcherNam.find()) {
			JOptionPane.showMessageDialog(null, "Giới tính không hợp lệ!\nTìm giới tính: nam hoặc nữ", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtTim.requestFocus();
			txtTim.selectAll();
			return false;
		}

		Pattern patternNu = Pattern.compile(regexNu);
		Matcher matcherNu = patternNu.matcher(input);
		if (!matcherNu.find()) {
			JOptionPane.showMessageDialog(null, "Giới tính không hợp lệ!\nTìm giới tính: nam hoặc nữ", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
			txtTim.requestFocus();
			txtTim.selectAll();
			return false;
		}
		return true;
	}
	
	public boolean regexTimKH(JTextField txtTK) {
		String input = txtTK.getText().trim();
		input = input.toUpperCase();
		String regexMaKH = "((KH|kh)[0-9]{3})|";
		String regexTenKH = "([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+)|";
		String regexSDT = "(0[0-9]{9})";
		String regexKH = "^(" + regexMaKH + regexTenKH + regexSDT+ ")$";
		if (!input.matches(regexKH)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã khách hàng. Ví dụ: KH001 hoặc kh001"
							+ "\n - Tên khách hàng. Ví dụ: Trần Thanh Thiện..."
							+ "\n - SĐT gồm 10 chữ số và bắt đầu bằng số 0",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		}
		return true;
	}
	
	public boolean regexTimPhong(JTextField txtTK) {
		String input = txtTK.getText().trim();
		input = input.toUpperCase();
		String regexMaP = "((P|p)[0-9]{3})|";
		String regexTen = "([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+)|";
		String regexPhong = "^(" + regexMaP + regexTen +  ")$";
		if (!input.matches(regexPhong)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:\n - Mã phòng. Ví dụ: P001 hoặc p001"
							+ "\n - Loại phòng. Ví dụ: Phòng thường, trung, VIP, ngừng hoạt động."
							+ "\n - Tình trạng. Ví dụ: Trống, đã đặt, đang sử dụng.",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		}
		return true;
	}

	public boolean regexTimKiemLoaiPhong(JTextField txtTK) {
		String in = txtTK.getText().toLowerCase().trim();

		String regexThuong = "^[phòng thường]*+$";
		Pattern patternThuong = Pattern.compile(regexThuong);
		Matcher matcherThuong = patternThuong.matcher(in);

		String regexMember = "^[phòng trung]*+$";
		Pattern patternMember = Pattern.compile(regexMember);
		Matcher matcherMember = patternMember.matcher(in);

		String regexVip = "^[phòng vip]*+$";
		Pattern patternVip = Pattern.compile(regexVip);
		Matcher matcherVip = patternVip.matcher(in);

		String regexStop = "^[ngừng hoạt động]*+$";
		Pattern patternStop = Pattern.compile(regexStop);
		Matcher matcherStop = patternStop.matcher(in);

		if (!matcherThuong.find() && !matcherMember.find() && !matcherVip.find() && !matcherStop.find()) {
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		} else
			return true;
	}
	
	public boolean regexTimKiemTinhTrang(JTextField txtTK) {
		String in = txtTK.getText().toLowerCase().trim();

		String regexTrong = "^[trống]*+$";
		Pattern patternTrong = Pattern.compile(regexTrong);
		Matcher matcherTrong = patternTrong.matcher(in);

		String regexDaDat = "^[đã đặt]*+$";
		Pattern patternDaDat = Pattern.compile(regexDaDat);
		Matcher matcherDaDat = patternDaDat.matcher(in);

		String regexUsing = "^[đang hoạt động]*+$";
		Pattern patternUsing = Pattern.compile(regexUsing);
		Matcher matcherUsing = patternUsing.matcher(in);


		if (!matcherTrong.find() && !matcherDaDat.find() && !matcherUsing.find() ) {
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		} else
			return true;
	}
	
	public boolean regexTimDDPTheoKH(JTextField txtTK) {
		String input = txtTK.getText().trim();
		input = input.toUpperCase();
		String regexTenKH = "([ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+)|";
		String regexSDT = "(0[0-9]{9})";
		String regexKH = "^(" + regexTenKH + regexSDT+ ")$";
		if (!input.matches(regexKH)) {
			JOptionPane.showMessageDialog(null,
					"Thông tin tìm kiếm không hợp lệ!\nThông tin có thể tìm kiếm:"
							+ "\n - Tên khách hàng. Ví dụ: Trần Thanh Tâm hoặc Thanh Tâm"
							+ "\n - SĐT gồm 10 chữ số và bắt đầu bằng số 0",
					"Thông báo", JOptionPane.ERROR_MESSAGE);
			txtTK.requestFocus();
			txtTK.selectAll();
			return false;
		}
		return true;
	}
}
