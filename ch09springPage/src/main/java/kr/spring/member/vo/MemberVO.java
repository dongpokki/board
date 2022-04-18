package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
	private int mem_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String id;
	private int auth;
	@NotEmpty
	private String name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String passwd;
	@NotEmpty
	private String phone;
	@Email
	@NotEmpty
	private String email;
	@Size(min=5,max=5)
	@NotEmpty
	private String zipcode;
	@NotEmpty
	private String address1;
	@NotEmpty
	private String address2;
	private MultipartFile upload;
	private byte[] photo;
	private String photo_name;
	private Date reg_date;
	private Date modify_date;
	
	//비밀번호 변경시 현재 비밀번호를 저장하는 용도로 사용
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String now_passwd;
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPasswd) {
		
		if(auth > 1 && passwd.equals(userPasswd)){
			return true;
		}
		
		return false;
	}
	
	//업로드 된 이미지 blob 처리
	public void setUpload(MultipartFile upload) throws IOException{
		this.upload = upload;
		//MultipartFile -> byte[]
		setPhoto(upload.getBytes()); // getBytes() : 업로드된 파일의 byte 값을 알아내는 메서드 - byte[] 형으로 반환
		//파일이름
		setPhoto_name(upload.getOriginalFilename()); // getOriginalFilename() : 업로드된 파일의 파일명을 알아내는 메서드 
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public MultipartFile getUpload() {
		return upload;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhoto_name() {
		return photo_name;
	}

	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public String getNow_passwd() {
		return now_passwd;
	}

	public void setNow_passwd(String now_passwd) {
		this.now_passwd = now_passwd;
	}

	//toString 메서드에서 표기되는 프로퍼티 중 byte[] 타입의 데이터(photo) 제외
	@Override
	public String toString() {
		return "MemberVO [mem_num=" + mem_num + ", id=" + id + ", auth=" + auth + ", name=" + name + ", passwd="
				+ passwd + ", phone=" + phone + ", email=" + email + ", zipcode=" + zipcode + ", address1=" + address1
				+ ", address2=" + address2 + ", upload=" + upload + ", photo_name=" + photo_name + ", reg_date="
				+ reg_date + ", modify_date=" + modify_date + ", now_passwd=" + now_passwd + "]";
	}

}