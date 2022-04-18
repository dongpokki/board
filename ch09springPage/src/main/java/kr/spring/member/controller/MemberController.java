package kr.spring.member.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	//회원 등록 폼 호출
	@GetMapping("/member/registerUser.do")
	public String form() {
		
		//타일스 설정
		return "memberRegister";
	}
	
	//회원 등록 폼 호출
	@PostMapping("/member/registerUser.do")
	public String submit(@Valid MemberVO memberVO, BindingResult result) {
		
		logger.info("<<회원가입>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원가입
		memberService.insertMember(memberVO);
		
		return "redirect:/main/main.do";
	}
	
	//로그인 폼
	@GetMapping("/member/login.do")
	public String formLogin() {
		
		
		return "memberLogin";
	}
	
	//로그인 폼에서 전송된 데이터 처리
	@PostMapping("/member/login.do")
	public String submitLogin(@Valid MemberVO memberVO, BindingResult result,HttpSession session) {
		
		logger.info("<<회원 로그인>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		//id와 passwd 필드만 체크
		if(result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formLogin();
		}
		
		//로그인 체크 (id,비밀번호 일치 여부 체크)
		try {			
			MemberVO member = memberService.selectCheckMember(memberVO.getId());
			
			boolean check = false;
			
			if(member != null) {
				//비밀번호 일치 여부 체크
				check = member.isCheckedPassword(memberVO.getPasswd());
			}
			
			if(check) {
				//인증 성공, 로그인 처리
				session.setAttribute("user_num", member.getMem_num());
				session.setAttribute("user_id", member.getId());
				session.setAttribute("user_auth", member.getAuth());
				session.setAttribute("user_photo", member.getPhoto());
				
				return "redirect:/main/main.do";
			}
			
			//인증 실패
			throw new AuthCheckException();
			
		}catch(AuthCheckException e) {
			//폼에서 보여질 메세지 생성
			result.reject("invalidIdOrPassword");
			//인증 실패로 로그인 폼을 호출
			return formLogin();
		}
	}
	
	//회원 로그아웃
	@RequestMapping("/member/logout.do")
	public String processLogout(HttpSession session) {
		//로그아웃
		session.invalidate();
		
		return "redirect:/main/main.do";
	}
	
	//마이페이지
	@RequestMapping("/member/myPage.do")
	public String process(HttpSession session, Model model) {
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		MemberVO member = memberService.selectMember(user_num);
		
		logger.info("<<회원 상세정보>> : " + member);
		
		model.addAttribute("member", member);
		
		return "memberView";
	}
	
	//회원정보 수정 폼
	@GetMapping("/member/update.do")
	public String formUpdate(HttpSession session,Model model) {
		
		Integer user_num =(Integer)session.getAttribute("user_num");
		
		MemberVO memberVO = memberService.selectMember(user_num);
		
		model.addAttribute("memberVO", memberVO);

		return "memberModify";
	}
	
	//수정폼에서 전송된 데이터 처리
	@PostMapping("/member/update.do")
	public String submitUpdate(@Valid MemberVO memberVO,BindingResult result,HttpSession session) {
		
		logger.info("<<회원 정보 수정>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return "memberModify";
		}
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		memberVO.setMem_num(user_num); // 회원수정 입력폼에서 num 값이 안넘어왔기 때문에 세션으로 받은 num값을 memberVO 객체에 세팅
		
		//회원정보 수정
		memberService.updateMember(memberVO);
		
		return "redirect:/member/myPage.do";
	}
	
	//비밀번호 변경 폼
	@GetMapping("/member/changePassword.do")
	public String formChangePassword() {
		return "memberChangePassword";
	}
	
	//비밀번호폼에서 전송된 데이터 처리
	@PostMapping("/member/changePassword.do")
	public String submitChangePassword(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
		logger.info("<<비밀번호 변경 처리>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasFieldErrors("now_passwd") || result.hasFieldErrors("passwd")) {
			return formChangePassword();
		}
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		memberVO.setMem_num(user_num);
		
		MemberVO db_member = memberService.selectMember(memberVO.getMem_num());
		
		//   DB에 저장된 현재 비밀번호                            사용자가 입력한 현재 비밀번호
		if(!db_member.getPasswd().equals(memberVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPassword");
			return formChangePassword();
		}
		
		//비밀번호 수정
		memberService.updatePassword(memberVO);
		
		return "redirect:/member/myPage.do";
	}
	
	//회원 탈퇴 폼
	@GetMapping("/member/delete.do")
	public String formDelete() {
		return "memberDelete";
	}
	
	//회원 탈퇴폼에서 전송된 데이터 처리
	@PostMapping("/member/delete.do")
	public String submitDelete(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
		logger.info("<<회원탈퇴 처리>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formDelete();
		}
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		memberVO.setMem_num(user_num);
		
		//아이디와 비밀번호 일치 여부 확인
		try {
			//세션에 저장된 회원번호를 이용해서 DB에 저장된 회원 정보를 MemberVO에 담아서 반환
			MemberVO db_member = memberService.selectMember(memberVO.getMem_num());
			
			boolean check = false;
			
			if(db_member!=null && memberVO.getId().equals(db_member.getId())) {
				//비밀번호 일치 여부 체크
				check = db_member.isCheckedPassword(memberVO.getPasswd());
			}
			
			if(check) {
				//인증 성공, 회원 정보 삭제
				memberService.deleteMember(memberVO.getMem_num());
				//로그아웃 (세션 삭제)
				session.invalidate();
				
				return "redirect:/main/main.do";
			}
			
			//인증 실패
			throw new AuthCheckException();
			
		}catch(AuthCheckException e) { // 아이디와 비밀번호가 일치하지 않는다면
			result.reject("invalidIdOrPassword");
			return formDelete();
		}
	}
	
	//이미지 출력 (BLOB로 받은걸 바이트 배열(photo)정보와 파일명(photo_name)을 함께 imageView.java 파일에서 가공한다.
	//가공작업(스트림을 발생시켜 img 태그를 통해 이미지로 노출될 수 있도록 하는 작업) 수행하여 화면에 노출
	@RequestMapping("/member/photoView.do")
	public ModelAndView viewImage(HttpSession session) {
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		MemberVO memberVO = memberService.selectMember(user_num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile", memberVO.getPhoto());
		mav.addObject("filename",memberVO.getPhoto_name());
		
		return mav;
	}
	
}