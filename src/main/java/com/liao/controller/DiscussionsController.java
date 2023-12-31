package com.liao.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liao.model.Discussions;
import com.liao.service.DiscussionsService;

@Controller
public class DiscussionsController {

	@Autowired
	private DiscussionsService dService;
	
	@GetMapping("/discussions")
	public String goHome() {
		return "liao/DiscussionsMain";
	}
	
	@GetMapping("/discussions/insertpage")
	public String insertpage() {
		return "liao/DiscussionInsert";
	}
	
	@PostMapping("/discussions/insertDiscussion")
	public String insertDiscussion(@RequestParam("memberId") Integer memberId,
									@RequestParam("eventId") Integer eventId,
									@RequestParam("userName") String userName,
									@RequestParam("gameId") Integer gameId,
									@RequestParam("gameName") String gameName,
									@RequestParam("title") String title,
									@RequestParam("dcontent") String dcontent,
									@RequestParam("lastReplyTime") String lastReplyTime,
									@RequestParam("d_views") Integer d_views,
									@RequestParam("dcreated_at") String dcreated_at,
									@RequestParam("dlikes") Integer dlikes, Model model) {
		Discussions discussions = new Discussions();
		discussions.setMemberId(memberId);
		discussions.setEventId(eventId);
		discussions.setUserName(userName);
		discussions.setGameId(gameId);
		discussions.setGameName(gameName);
		discussions.setTitle(title);
		discussions.setDcontent(dcontent);
		discussions.setLastReplyTime(lastReplyTime);
		discussions.setD_views(d_views);
		discussions.setDcreated_at(dcreated_at);
		discussions.setDlikes(dlikes);
		
		dService.insert(discussions);
		
		return "redirect:/discussions/getAllDiscussions";
	}
	
	@GetMapping("/discussions/getAllDiscussions")
	  public String getAllDiscussions(Model model) throws SQLException {
	          List<Discussions> discussions = dService.findAll();
	          model.addAttribute("discussions", discussions);
	          return "liao/GetAllDiscussion";
	  }
	
//	@GetMapping("/GetDiscussion")
//	public String selectById(@RequestParam("articleId") int articleId, Model model) {
//	    
//	    	Discussions discussion = dService.findById(articleId);
//	        model.addAttribute("discussion", discussion);
//
//	        return "liao/updateDiscussionData";   
//
// 
//	}
	
	@GetMapping("/discussions/update")
	public String updatePage(@RequestParam Integer articleId,Model model) {
		Discussions discussions = dService.findById(articleId);
		model.addAttribute("discussions", discussions);
		return "liao/updateDiscussionData";
	}
	
	@Transactional
	@PutMapping("/discussions/update")
	public String updatePost(@ModelAttribute(name="discussions") Discussions discussions) {
		dService.updateDiscussionsById(discussions.getArticleId(), discussions.getMemberId(),
				discussions.getEventId(),
				discussions.getUserName(), 
				discussions.getGameId(),
				discussions.getGameName(), 
				discussions.getTitle(),
				discussions.getDcontent(),
				discussions.getLastReplyTime(),
				discussions.getD_views(),
				discussions.getDcreated_at(),
				discussions.getDlikes());
		return "redirect:/discussions/getAllDiscussions";
	}
	
	
	@DeleteMapping("/discussions/delete")
	public String deleteDiscussion(@RequestParam("articleId") Integer articleId) {
		dService.deleteById(articleId);
		return "redirect:/discussions/getAllDiscussions";
	}
	
	@GetMapping("/discussions/findByUserName")
	public String findByUserName(@RequestParam("userName") String userName, Model model) {
		List<Discussions> discussions = dService.findDiscussionsByUserName(userName);
		model.addAttribute("discussions", discussions);
//	    model.addAttribute("userName", userName);
		
//		return dService.findDiscussionsByUserName(userName);
//		for (Discussions discussions2 : discussions) {
//			System.out.println(discussions2.getUserName());
//		}
		return "liao/GetDiscussionSelectUserName";
		
	}
	
	

	
	
}
