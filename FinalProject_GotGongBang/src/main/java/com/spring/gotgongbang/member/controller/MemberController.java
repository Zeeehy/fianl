package com.spring.gotgongbang.member.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.gotgongbang.HomeController;
import com.spring.gotgongbang.common.MyUtil;
import com.spring.gotgongbang.common.Sha256;
import com.spring.gotgongbang.craft.model.PartnerVO;
import com.spring.gotgongbang.member.model.MemberVO;
import com.spring.gotgongbang.member.service.InterMemberService;
import com.spring.gotgongbang.member.service.MailSendService;

@Component
@Controller
public class MemberController {
		
	@Autowired
	private MyUtil myUtil;
	
		// 김나윤 시작
		// ===========================================================================
		// 김나윤 끝
		// ===========================================================================

		// 김진솔 시작
		// ===========================================================================
		// 김진솔 끝
		// ===========================================================================

		// 박준엽 시작
		// ===========================================================================

		@RequestMapping(value="/proposal_list.got")
		public ModelAndView requiredLogin_proposalList(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userId = loginuser.getUser_id_pk(); 
			int totalCount = 0;
			int sizePerPage = 5;
			int currentShowPageNo = 0;
			int totalPage = 0;
			
			int startRno = 0;
			int endRno = 0;
			
			String str_currentShowPageNo = request.getParameter("currentShowPageNo");
			totalCount = service.getTotalCountProposalListByUserId(userId);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			if(str_currentShowPageNo == null) {
				currentShowPageNo = 1;
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
						currentShowPageNo = 1;
					}
				}
				catch(NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
		    
			startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
			endRno = startRno + sizePerPage -1;
			
			HashMap<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("startRno", String.valueOf(startRno));
			paraMap.put("endRno", String.valueOf(endRno));
			paraMap.put("userId", userId);
			
			String url = "order_list.got";
			String pageBar = myUtil.makePageBar(currentShowPageNo, 10, totalPage, url);
			List<HashMap<String, String>> proposalList = service.getProposalListByUserId(paraMap);
			
			mav.addObject("pageBar", pageBar);
			mav.addObject("proposalList", proposalList);
			mav.setViewName("member/proposal_list.tiles1");
			return mav;
		}
		
		@RequestMapping(value="/edit_user_info.got")
		public ModelAndView requiredLogin_editUserInfo(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			  HttpSession session = request.getSession();
			  MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			  String userId = loginuser.getUser_id_pk();
		      
		      MemberVO mvo = new MemberVO();
		      mvo = service.getUserInfoByUserId(userId);
		      mav.addObject("mvo", mvo);
		      mav.setViewName("member/editUserInfo.tiles1");
		      return mav;
		}
		
		@RequestMapping(value="/edit_user_info_end.got")
		public ModelAndView editUserInfoEnd(ModelAndView mav, HttpServletRequest request, MemberVO mvo) {
			int n = 0;
		    n = service.updateMemberInfoByMVO(mvo);

		    String message = "";
		    String loc = "";
		      
		    if (n == 1) {
		       message = "정상적으로 변경되었습니다.";
		       loc = request.getContextPath()+"/index.got";
		    }
		      
		    else {
		       message = "오류가 발생했습니다";
		       loc ="javascript:history.back();";
		    }

		    request.setAttribute("message", message);
	     	request.setAttribute("loc", loc);
		    mav.setViewName("msg");
			
			
			return mav;
		}
		
		@ResponseBody
		@RequestMapping(value="/update_user_pwd.got")
		public String updateUserPwd(HttpServletRequest request) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userId = loginuser.getUser_id_pk();
			String editPw = request.getParameter("editPw");
			editPw = Sha256.encrypt(editPw); 
		    MemberVO mvo = new MemberVO();
		    mvo = service.getUserInfoByUserId(userId);
		    int n = 0;

		    if(editPw.equals(mvo.getPwd())) {
		    	n = 2;
		    }
			else {
				mvo.setPwd(editPw);
				n = service.updateMemberPwd(mvo);
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n);
			return jsonObj.toString();
		}
		
		@ResponseBody
		@RequestMapping(value="/check_insert_pwd.got")
		public String checkInsertPwd(HttpServletRequest request) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userId = loginuser.getUser_id_pk();
			String insertPwd = request.getParameter("insertPwd");
			
		    MemberVO mvo = new MemberVO();
		    mvo = service.getUserInfoByUserId(userId);
		    int n = 0;

		    if(insertPwd.equals(mvo.getPwd())) {
		    	n = 1;
		    }
		    
		    JSONObject jsonObj = new JSONObject();
		    jsonObj.put("n", n);
		    return jsonObj.toString();
		}
		
		@RequestMapping(value="/order_list.got")
		public ModelAndView requiredLogin_getOrderListById(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userId = loginuser.getUser_id_pk();

			String str_currentShowPageNo = request.getParameter("currentShowPageNo");
			int totalCount = 0;
			int sizePerPage = 5;
			int currentShowPageNo = 0;
			int totalPage = 0;
			
			int startRno = 0;
			int endRno = 0;
			
			totalCount = service.getTotalCountForOrderListByUserId(userId);
			
			totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
			if(str_currentShowPageNo == null) {
				currentShowPageNo = 1;
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
						currentShowPageNo = 1;
					}
				}
				catch(NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}
			
			startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
			endRno = startRno + sizePerPage -1;
			
			HashMap<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("startRno", String.valueOf(startRno));
			paraMap.put("endRno", String.valueOf(endRno));
			paraMap.put("userId", userId);
			
			String url = "order_list.got";
			String pageBar = myUtil.makePageBar(currentShowPageNo, 10, totalPage, url);
			
			List<HashMap<String, String>> orderList = service.getOrderListByUserId(paraMap);
			
			mav.addObject("currentShowPageNo", currentShowPageNo);
			mav.addObject("pageBar", pageBar);
			mav.addObject("orderList", orderList);
			mav.setViewName("member/orderList.tiles1");
			return mav;

		}
		
		
		// 박준엽 끝
		// ===========================================================================

		// 오준혁 시작
		// ===========================================================================
		// 오준혁 끝
		// ===========================================================================

		// 이지현 시작
		// ===========================================================================
		// 이지현 끝
		// ===========================================================================

		// 홍용훈 시작
		// ===========================================================================
	
		private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
		
	
		@Autowired
		private MailSendService mailService;
		@Autowired
		private InterMemberService service;
	
		// === 로그인 폼 페이지 요청 === //
		@RequestMapping(value="/login.got", method= {RequestMethod.GET})
		public ModelAndView login(ModelAndView mav) {
			
			mav.setViewName("member/login.tiles1");
			return mav;
	
		}
		
		
		@RequestMapping(value="/end_login.got")
		public ModelAndView end_login(ModelAndView mav, HttpServletRequest request) {
			
			String userid = request.getParameter("userid");
		    String pwd = request.getParameter("pwd");

		    Map<String, String> paraMap = new HashMap<String, String>();
		    paraMap.put("userid", userid);
		    paraMap.put("pwd", Sha256.encrypt(pwd));

		    // service의 loginEnd() 메서드 호출하여 로그인 처리
		    mav = service.loginEnd(mav, request, paraMap);
	    
		    return mav;
		}
		
		
		@RequestMapping(value="/register_member_first.got")
		public ModelAndView register_member_first(ModelAndView mav) {
			
			mav.setViewName("member/register_member_first.tiles1");
			return mav;
	
		}
		
		// 회원가입
		@RequestMapping(value="/register_member.got")
		public ModelAndView register_member(ModelAndView mav) {
			
			mav.setViewName("member/register_member.tiles1");
			return mav;
	
		}
		
		
		// 이메일 중복 확인 AJAX 요청 처리
		@ResponseBody
		@GetMapping("/check_email.got")	    
	    public boolean checkEmail(@RequestParam("email") String email) {
	        return service.isEmailDuplicate(email);
	    }
	    
	    // 아이디 중복 확인 AJAX 요청 처리
	    @ResponseBody
	    @GetMapping("/check_id.got")
	    public boolean checkId(@RequestParam("id") String id) {
	        return service.isIdDuplicate(id);
	    }
		

		
		// 이메일 인증 ( 회원가입 )
		@ResponseBody
	    @RequestMapping(value="/member/email_check.got")
		public String email_check(String email) {
			
			System.out.println("이메일 인증 요청이 들어옴!");
			System.out.println("이메일 인증 이메일 : " + email);
			
			return mailService.joinEmail(email);
			
		}
		
		// 회원가입 get
		@RequestMapping(value="/register.got", method=RequestMethod.GET)
		public void register() {
			
		}
		
		// 회원가입 post
		@RequestMapping(value="/register.got", method=RequestMethod.POST)
		public String register(MemberVO membervo) {
			
			System.out.println("들어옴");
			service.encryptPassword(membervo);
			
			service.insertMember(membervo);
			
			return "redirect:/end_register_member.got";
		}
		
		
		@RequestMapping(value="/end_register_member.got")
		public ModelAndView end_register_member(ModelAndView mav) {
			
			mav.setViewName("member/end_register_member.tiles1");
			return mav;
		}
		
		// 아이디 찾기
		@RequestMapping(value="/find_id.got")
		public ModelAndView find_id(ModelAndView mav) {
			
			mav.setViewName("member/find_id.tiles1");
			return mav;
		}
		
		
		// 이메일 인증 ( 아이디 찾기 )
		@ResponseBody
	    @RequestMapping(value="/member/find_id_email_check.got")
		public String find_id_email_check(String name, String email) {
			
			System.out.println("이메일 인증 요청이 들어옴!");
			System.out.println("이메일 인증 이메일 : " + email);
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("name", name);
			paraMap.put("email", email);
			
			//List<MemberVO> membervo = service.compareNameEmail(name, email);
			
			
			String memberId = service.compareNameEmailMember(paraMap);
			System.out.println(memberId);
			String partnerId = service.compareNameEmailpartner(paraMap);
			System.out.println(partnerId);
			
			String emailCode = mailService.joinEmail(email);
			
			// JSON 형태로 결과를 반환
		    JSONObject jsonObj = new JSONObject();
		    jsonObj.put("memberId", memberId);
		    jsonObj.put("partnerId", partnerId);
		    jsonObj.put("emailCode", emailCode);

		    return jsonObj.toString();
			
			
		}
		
		
		// 아이디 찾기 end
		@RequestMapping(value="/find_id_end.got")
		public ModelAndView find_id_end(ModelAndView mav) {
		
			mav.setViewName("member/find_id_end.tiles1");
		
			return mav;
		}
		
		
		@RequestMapping(value="/find_pwd.got")
		public ModelAndView find_pwd(ModelAndView mav) {
			
			mav.setViewName("member/find_pwd.tiles1");
			return mav;
		}
		
		// 로그아웃 처리
		@RequestMapping(value="/logout.got")
		public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {
			
			// 로그아웃시 메인
			HttpSession session = request.getSession();
			session.invalidate();
			
			String message = "로그아웃 되었습니다.";
			String loc = request.getContextPath()+"/index.got";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
			
			return mav;
		}
		
		
		

		
		// 홍용훈 끝
		// ===========================================================================
}
