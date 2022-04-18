package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;

@Controller
public class MemberAjaxController {
	private static final Logger logger = LoggerFactory.getLogger(MemberAjaxController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/member/confirmId.do")
	@ResponseBody //json 문자열 만드는 jackson 라이브러리 사용하는 어노테이션 : 반환타입이 키와 값의 형태(Map 등등..)이라면, json 문자열로 변환후 반환해준다. 
	public Map<String,String> process(@RequestParam String id){
		
		logger.info("<<id>> : " + id);
		
		Map<String,String> map = new HashMap<String,String>();
		
		MemberVO member = memberService.selectCheckMember(id); 
		
		if(member != null) {
			//아이디 중복
			map.put("result","idDuplicated");
		}else {
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", id)) {
				//패턴 불일치
				map.put("result", "notMatchPattern");
			}else {
				//패턴도 일치하고 아이디 미중복
				map.put("result", "idNotFound");
			}
		}
		
		return map;
	}
	
	
	@RequestMapping("/member/updateMyPhoto.do")
	@ResponseBody
	public Map<String,String> processProfile(MemberVO memberVO,HttpSession session){
		
		Map<String,String> map = new HashMap<String,String>();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되어있나 확인 (ajax 처리는 페이지 내에서 처리하는 작업이므로 interseptor 사용 X)
		if(user_num == null) { // 로그인 X
			map.put("result","logout");
		}else {
			memberVO.setMem_num(user_num);
			memberService.updateProfile(memberVO);
			
			//이미지 업로드한 후 세션에 저장된 user_photo 값 변경
			session.setAttribute("user_photo", memberVO.getPhoto());
			
			map.put("result", "success");
		}
		
		return map;
	}
	
}