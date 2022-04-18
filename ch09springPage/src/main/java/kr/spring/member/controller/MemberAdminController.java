package kr.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;

@Controller
public class MemberAdminController {
	private static final Logger logger = LoggerFactory.getLogger(MemberAdminController.class);
	
	@Autowired
	private MemberService MemberService;
	
	//회원 관리 목록
	@RequestMapping("/member/admin_list.do")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue = "1") int currentPage, 
								@RequestParam(value="keyfield",defaultValue = "") String keyfield, 
								@RequestParam(value="keyword",defaultValue = "") String keyword) {

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("keyfield",keyfield);
		map.put("keyword",keyword);
		
		//총 글의 갯수 또는 검색된 글의 갯수
		int count = MemberService.selectRowCount(map);
		
		logger.info("<<회원관리>> : " + count);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,20,10,"admin_list.do");
		
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<MemberVO> list = null;
		if(count>0) {
			list = MemberService.selectList(map);
			// 반복문을통해 list.add(MemberVO) 해줄 필요 없나?
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin_memberList");
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml",page.getPagingHtml());
		
		return mav;
	}
	
	//회원 정보 수정 폼
	@GetMapping("/member/admin_update.do")
	public String form(@RequestParam int mem_num,Model model) {
		
		MemberVO memberVO = MemberService.selectMember(mem_num);
		
		model.addAttribute("memberVO", memberVO);
		
		return "admin_memberModify";
	}
	
	//회원 정보 수정 폼으로부터 받은 데이터 값을 통한 회원 수정
	@PostMapping("/member/admin_update.do")
	public String submit(@Valid MemberVO memberVO,BindingResult result) {
		
		logger.info("<<[관리자]회원 정보 수정>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return "admin_memberModify";
		}
		
		//회원정보 수정
		MemberService.updateByAdmin(memberVO);
		
		return "redirect:/member/admin_list.do";
	}
	
	
	
}