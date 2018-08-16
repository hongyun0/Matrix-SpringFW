package com.matrix.spring.task.daily;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
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
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
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
	public String assignTask(DailyDTO dailyVO, @SessionAttribute String adminSeq, @SessionAttribute String userId) {
		try {
			dailyVO.setAdminSeq(adminSeq);
			dailyService.addDailyTask(dailyVO);
			log.info("INFO [{}] assign '{}' to [{}].", userId, dailyVO.getDailyTask(), dailyVO.getAssignDetail());
		} catch (Exception e) {
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
		}
		return "succeed";
	}
	
	@RequestMapping(value = "/admin/task/daily/exist", method = RequestMethod.GET)
	public String isDailyTask(@RequestParam String dailyTask, @RequestParam String assignDate,
			@SessionAttribute String branchSeq, Model model) {
		try {
			boolean result = dailyService.isDailyTask(dailyTask, assignDate, branchSeq);
			model.addAttribute("result", String.valueOf(result));
		} catch (Exception e) {
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
		}
		return "isExist";
	}
}
