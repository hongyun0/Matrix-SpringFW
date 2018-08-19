package com.matrix.spring.task.daily;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
	public String getAssignedParts(@RequestParam String date, @SessionAttribute String branchSeq, Model model)
			throws ParseException {
		List<String> parts;
		parts = dailyService.getAssignedParts(date, branchSeq);
		boolean personal = false;
		if (parts.contains("personal")) {
			parts.remove("personal");
			personal = true;
		}
		model.addAttribute("parts", parts);
		model.addAttribute("personal", personal);
		return "getAssignedParts";
	}

	@RequestMapping(value = "/admin/task/daily/{assignType}", method = RequestMethod.GET)
	public String getDailyTasks(@RequestParam String assignType, @RequestParam String assignDate,
			@SessionAttribute String branchSeq, Model model) throws ParseException {
		String page = "error";

		List<Map<String, String>> tasks;
		tasks = dailyService.getDailyTasks(assignDate, assignType, branchSeq);
		model.addAttribute("tasks", tasks);
		if (assignType.equals("개인")) {
			page = "getDailyTasksForPerson";
		} else {
			page = "getDailyTasksForParts";
		}
		return page;
	}

	@RequestMapping(value = "/admin/task/daily/assign", method = RequestMethod.GET)
	public String assignTask() {
		return "assignTaskAdmin";
	}

	@RequestMapping(value = "/admin/task/daily/assign/next", method = RequestMethod.GET)
	public String assignTaskNext() {
		return "assignTaskNextAdmin";
	}

	@RequestMapping(value = "/admin/task/daily/assign", method = RequestMethod.POST)
	public String assignTask(DailyDTO dailyDTO, @SessionAttribute String adminSeq, @SessionAttribute String userId)
			throws ParseException {
		dailyDTO.setAdminSeq(adminSeq);
		dailyService.addDailyTask(dailyDTO);
		return "succeed";
	}

	@RequestMapping(value = "/admin/task/daily/exist", method = RequestMethod.GET)
	public String isDailyTask(@RequestParam String dailyTask, @RequestParam String assignDate,
			@SessionAttribute String branchSeq, Model model) {
		boolean result = dailyService.isDailyTask(dailyTask, assignDate, branchSeq);
		model.addAttribute("result", String.valueOf(result));
		return "isExist";
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void setUnfinished() {
		dailyService.setUnfinished();
	}
	
}
