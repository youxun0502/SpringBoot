package com.liu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liu.model.Member;
import com.liu.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainFunction {

	@Autowired
	private MemberService mService;

	@GetMapping("main/goBackToHome")
	public String goBackTOHome() {
		return "liu/main";
	}
	
	@GetMapping("main/loginPage")
	public String loginPage(){
		return "liu/memberLogin";
	}

	@PostMapping("main/memberLogin")
	public String memberLogin(@RequestParam("email") String email, @RequestParam("memberPwd") String memberPwd,
			Model m, HttpSession session) {
		Member member = mService.isMember(email, memberPwd);
		if(member == null) {
			return "liu/memberLoginError";
		}else if(member.getLevelId() == 100){
			session.setAttribute("managerBeans", member);
			session.setAttribute("character", "manager");
			return "liu/main";
		}else {
			session.setAttribute("memberBeans", member);
			session.setAttribute("character", "member");
			return "liu/home";
		}
		
	}
}
