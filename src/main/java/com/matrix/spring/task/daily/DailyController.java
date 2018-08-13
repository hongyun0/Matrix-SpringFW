package com.matrix.spring.task.daily;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class DailyController {
	@Autowired
	DailyService dailyService;
	
	@RequestMapping(value = "/admin/task/daily", method = RequestMethod.GET)
	public String dailyTaskAdmin() {
		return "dailyTaskAdmin";
	}
	
	@RequestMapping(value = "/admin/task/daily/parts", method = RequestMethod.GET)
	public String getAssignedParts(@RequestParam String date, @SessionAttribute String branchSeq, Model model) {
		try {
			List<String> parts;
			parts = dailyService.getAssignedParts(date, branchSeq);
			boolean personal = false;
			if (parts.contains("personal")) {
				parts.remove("personal");
				personal = true;
			}
			model.addAttribute("parts", parts);
			model.addAttribute("personal", personal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "getAssignedParts";
	}
	
	@RequestMapping(value = "/admin/task/daily/{assignType}", method = RequestMethod.GET)
	public String getDailyTasks(@RequestParam String assignType, @RequestParam String assignDate, @SessionAttribute String branchSeq, Model model) {
		String page = "error";
		try {
			List<Map<String, String>> tasks;
			tasks = dailyService.getDailyTasks(assignDate, assignType, branchSeq);
			model.addAttribute("tasks", tasks);
			if(assignType.equals("개인")){
				page = "getDailyTasksForPerson";
			} else {
				page = "getDailyTasksForParts";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping(value = "/admin/task/daily/assign", method = RequestMethod.PUT)
	public String assignTask(@RequestParam DailyVO dailyVO) {
		dailyService.addDailyTask(dailyVO);
		return "";
	}
}