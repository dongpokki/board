package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	// 사용자 
	//public int selectMem_num();
	public void insertMember(MemberVO member);
	//public void insertMember_detail(MemberVO member);
	public MemberVO selectCheckMember(String id);
	public MemberVO selectMember(Integer mem_num);
	public void updateMember(MemberVO member);
	public void updatePassword(MemberVO member);
	public void deleteMember(Integer mem_num);
	//public void deleteMember_detail(Integer mem_num);
	public void updateProfile(MemberVO member);

	// 관리자
	public List<MemberVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void updateByAdmin(MemberVO member);
	public void updateDetailByAdmin(MemberVO member);
}