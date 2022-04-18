package kr.spring.board.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
	private int board_num; // 게시판 번호
	@NotEmpty
	private String title; // 제목
	@NotEmpty
	private String content; // 내용
	private int hit; // 조회수
	private Date reg_date; // 등록일
	private Date modify_date; // 수정일
	private MultipartFile upload;
	private byte[] uploadfile; // 파일
	private String filename; // 파일명
	private String ip; //ip 주소
	private int mem_num; // 회원번호
	private String id; // 아이디(spmember랑 조인해서 구하기)
	
	// 업로드 파일 처리
	public void setUpload(MultipartFile upload) throws IOException{
		this.upload = upload;
		//MultipartFile -> byte[] 반환 (myBtis 제공)
		setUploadfile(upload.getBytes());
		//파일명 구하기
		setFilename(upload.getOriginalFilename());
	}

	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
	public MultipartFile getUpload() {
		return upload;
	}
	public byte[] getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(byte[] uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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

	@Override
	public String toString() {
		return "BoardVO [board_num=" + board_num + ", title=" + title + ", content=" + content + ", hit=" + hit
				+ ", reg_date=" + reg_date + ", modify_date=" + modify_date + ", upload=" + upload + ", filename="
				+ filename + ", ip=" + ip + ", mem_num=" + mem_num + ", id=" + id + "]";
	}	
	
	
}