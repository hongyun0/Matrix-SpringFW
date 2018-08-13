package com.matrix.spring.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class UserController {
	@Autowired
	IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String userId, @RequestParam String pw, HttpServletRequest request) {
		String page = "loginError";
		if (userService.login(userId, pw)) {
			page = "loginOK";
			request.getSession().setAttribute("userId", userId);
		}
		return page;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:login";
	}

	@RequestMapping(value = "/isCertified", method = RequestMethod.GET)
	public String certify(@SessionAttribute String userId, HttpServletRequest request) {
		String page = "certify";

		Map<String, String> user = userService.getCertifiedInfo(userId);
		if (user != null) {
			String type = user.get("type");
			HttpSession session = request.getSession();
			if (type.equals("admin")) {
				page = "admin/task/daily";
				session.setAttribute("branchSeq", user.get("BRANCH_SEQ"));
				session.setAttribute("adminSeq", user.get("ADMIN_SEQ"));
			} else if (type.equals("staff")) {
				page = "staff/task/daily";
				session.setAttribute("branchSeq", user.get("BRANCH_SEQ"));
				session.setAttribute("staffName", user.get("NAME"));
			}
			session.setAttribute("type", type);
		}

		return "redirect:" + page;
	}
	
	@RequestMapping(value = "/slideinfo", method = RequestMethod.GET)
	public String getSlideInfo(@SessionAttribute String userId, @SessionAttribute String type, Model model) {
		Map<String, String> info = userService.getSlideInfo(userId, type);
		model.addAttribute("info", info);
		return "getSlideInfo";
	}

}
