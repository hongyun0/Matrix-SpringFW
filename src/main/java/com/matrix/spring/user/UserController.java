package com.matrix.spring.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		String result = "loginError";
		if (userService.login(userId, pw)) {
			result = "loginOK";
			request.getSession().setAttribute("userId", userId);
		}
		return result;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:login";
	}

	@RequestMapping(value = "isCertified", method = RequestMethod.GET)
	public String certify(HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("userId");
		String result = "certify";

		Map<String, String> user = userService.getCertifiedInfo(userId);
		if (user != null) {
			String type = user.get("type");
			HttpSession session = request.getSession();
			if (type.equals("admin")) {
				result = "admin/task/daily";
				session.setAttribute("branchSeq", user.get("BRANCH_SEQ"));
				session.setAttribute("adminSeq", user.get("ADMIN_SEQ"));
			} else if (type.equals("staff")) {
				result = "staff/task/daily";
				session.setAttribute("branchSeq", user.get("BRANCH_SEQ"));
				session.setAttribute("staffName", user.get("NAME"));
			}
			session.setAttribute("type", type);
		}

		return "redirect:" + result;
	}

}
