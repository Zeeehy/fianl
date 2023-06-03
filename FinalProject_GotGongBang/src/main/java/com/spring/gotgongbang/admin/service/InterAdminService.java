package com.spring.gotgongbang.admin.service;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.spring.gotgongbang.admin.model.AdminDAO;
import com.spring.gotgongbang.admin.model.AdminVO;

public interface InterAdminService {
	
	// ==== ==================================== 김나윤 시작 ======================================================= // 
//	boolean loginCheck(AdminVO avo, HttpSession session) throws Exception;    //관리자 로그인을 체크하는 메소드
	
	ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request, Map<String, String> paraMap);
	
	// ==== ==================================== 김나윤 끝 ======================================================= // 

}
