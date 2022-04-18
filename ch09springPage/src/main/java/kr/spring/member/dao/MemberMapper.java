package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;

public interface MemberMapper {
	// 사용자 
	@Select("select spmember_seq.nextval from dual")
	public int selectMem_num();
	@Insert("insert into spmember (mem_num,id) values(#{mem_num},#{id})")
	public void insertMember(MemberVO member);
	@Insert("insert into spmember_detail (mem_num,name,passwd,phone,email,zipcode,address1,address2) values(#{mem_num},#{name},#{passwd},#{phone},#{email},#{zipcode},#{address1},#{address2})")
	public void insertMember_detail(MemberVO member);
	@Select("select m.mem_num, m.id, m.auth, d.passwd, d.photo from spmember m left outer join spmember_detail d on m.mem_num=d.mem_num where m.id=#{id}")
	public MemberVO selectCheckMember(String id);
	@Select("select * from spmember m join spmember_detail d on m.mem_num=d.mem_num where m.mem_num=#{mem_num}")
	public MemberVO selectMember(Integer mem_num);
	@Update("update spmember_detail set name=#{name},phone=#{phone},email=#{email},zipcode=#{zipcode},address1=#{address1},address2=#{address2},modify_date=sysdate where mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	@Update("update spmember_detail set passwd=#{passwd} where mem_num=#{mem_num}")
	public void updatePassword(MemberVO member);
	@Update("update spmember set auth=0 where mem_num=#{mem_num}")
	public void deleteMember(Integer mem_num);
	@Delete("delete from spmember_detail where mem_num=#{mem_num}")
	public void deleteMember_detail(Integer mem_num);
	@Update("update spmember_detail set photo=#{photo},photo_name=#{photo_name} where mem_num=#{mem_num}") //photo : blob 타입의 바이트 배열
	public void updateProfile(MemberVO member);
	
	// 관리자
	public List<MemberVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	@Update("update spmember set auth=#{auth} where mem_num=#{mem_num}")
	public void updateByAdmin(MemberVO member);
	@Update("update spmember_detail set name=#{name},phone=#{phone},email=#{email},zipcode=#{zipcode},address1=#{address1},address2=#{address2} where mem_num=#{mem_num}")
	public void updateDetailByAdmin(MemberVO member);
}