package com.matrix.spring.task.manual;

import java.util.ArrayList;
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
public class ManualController {
	@Autowired
	IManualService manualService;

	@RequestMapping(value = "/task/manual/spacetypes", method = RequestMethod.GET)
	public String getSpaceTypes(Model model) {
		List<String> types = manualService.getSpaceTypes();
		model.addAttribute("types", types);
		model.addAttribute("mode", "spaceType");
		return "getAccordion";
	}

	@RequestMapping(value = "/task/manual/{spaceType}/tasktypes", method = RequestMethod.GET)
	public String getTaskTypesBySpaceType(@RequestParam String spaceType, Model model) {
		List<String> types = manualService.getTaskTypesBySpaceType(spaceType);
		model.addAttribute("types", types);
		model.addAttribute("mode", "taskType");
		return "getSubAccordion";
	}

	@RequestMapping(value = "/task/manual/type/tasks", method = RequestMethod.GET)
	public String getManualTasks(@RequestParam String spaceType, @RequestParam String taskType, Model model) {
		List<Map<String, String>> tasks = manualService.getManualTasks(spaceType, taskType);
		model.addAttribute("tasks", tasks);
		return "getManualTasks";
	}

	@RequestMapping(value = "/task/manual/recommend/tasks", method = RequestMethod.GET)
	public String getRecommendedTasks(@RequestParam String date, @SessionAttribute String branchSeq, Model model) {
		List<Map<String, String>> tasks = manualService.getRecommendedTasks(date, branchSeq);
		List<String> spaces = new ArrayList<>();
		for (Map<String, String> task : tasks) {
			String space = task.get("SPACE_TYPE");
			if (!spaces.contains(space)) {
				spaces.add(space);
			}
		}
		model.addAttribute("tasks", tasks);
		model.addAttribute("spaces", spaces);
		return "getRecommendedTasks";
	}

	@RequestMapping(value = "/task/manual/search/tasks", method = RequestMethod.GET)
	public String searchManualTasks(String inputText, Model model) {
		List<Map<String, String>> tasks = manualService.searchManualTasks(inputText);
		model.addAttribute("tasks", tasks);
		return "getSearchedTasks";
	}
}
