package com.chen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chen.model.Competition;
import com.chen.model.CompetitionRegistration;
import com.chen.model.CompetitionRepository;
import com.chen.service.CompetitionRegistrationService;

@Controller
public class CompetitionRegistrationController {

	@Autowired
	private CompetitionRegistrationService crService;
	
	@Autowired
	private CompetitionRepository cRepo;
	
	//跳轉新增頁面
	@GetMapping("/3/competition/registration")
	public String signupPage(Model m) {
		List<Competition> competitions = cRepo.findAll();
		m.addAttribute("competitions", competitions);
		return "chen/competitionRegistration";
	}
	
	//跳轉單筆頁面
	@GetMapping("/competition/registration/update")
	public String processUpdate(@RequestParam("signupId")Integer id, Model m) {
		CompetitionRegistration registration = crService.findById(id);
		m.addAttribute("registration", registration);
		
		List<Competition> competitions = cRepo.findAll();
		m.addAttribute("competitions", competitions);
		return "chen/updateCompetitionRegistration";
	}
	
	//查詢全部
	@GetMapping("/competition/registration/data")
	public String findALL(Model m) {
		List<CompetitionRegistration> registrations = crService.findAll();
		m.addAttribute("registrations", registrations);
		return "chen/competitionRegistrationData";
	}
	
	//透過姓名搜尋
	@PostMapping("/competition/registration/data")
	public String findDataByName(@RequestParam("realName")String realName,Model m) {
		List<CompetitionRegistration> registrations = crService.findByName(realName);
		m.addAttribute("registrations", registrations);
		return "chen/competitionRegistrationData";
	}
	
	//新增資料
	@PostMapping("/3/competition/registration/insert")
	public String inserData(@RequestParam("competitionId")Integer id,@RequestParam("gameNickname")String gameNickname,@RequestParam(value = "teamName", required = false)String teamName,
							@RequestParam("realName")String realName,@RequestParam("email")String email,@RequestParam("phone")String phone,
							@RequestParam("address")String address) {
		CompetitionRegistration cr = new CompetitionRegistration();
		cr.setCompetitionId(id);
		cr.setGameNickname(gameNickname);
		cr.setTeamName(teamName);
		cr.setRealName(realName);
		cr.setEmail(email);
		cr.setPhone(phone);
		cr.setAddress(address);
		crService.insert(cr);
		return "redirect:/competition/registration";
	}
	
	//修改資料
	@PutMapping("/competition/registration/update")
	public String updatePost(@RequestParam("signupId")Integer signupId,@RequestParam("competitionId")Integer competitionId,@RequestParam("gameNickname")String gameNickname,
							@RequestParam(value = "teamName", required = false)String teamName,@RequestParam("realName")String realName,@RequestParam("email")String email,
							@RequestParam("phone")String phone,@RequestParam("address")String address) {
		crService.updateRegistrationById(signupId, competitionId, gameNickname, teamName, realName, email, phone, address);
		return "redirect:/competition/registration/data";
	}
	
	//刪除資料
	@DeleteMapping("/competition/registration/delete")
	public String deletePost(@RequestParam("signupId")Integer id) {
		crService.deleteById(id);
		return "redirect:/competition/registration/data";
	}
}
