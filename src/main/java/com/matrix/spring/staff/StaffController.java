package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class StaffController {
	@Autowired
	StaffService staffService;

	@RequestMapping(value = "/admin/employee/workparts", method = RequestMethod.GET)
	public String getWorkParts(@SessionAttribute String branchSeq, Model model) {
		List<String> parts = staffService.getWorkParts(branchSeq);
		model.addAttribute("workParts", parts);
		return "getWorkParts";
	}

	@RequestMapping(value = "/admin/employee/assignable", method = RequestMethod.GET)
	public String getAssignableStaffs(@SessionAttribute String branchSeq, Model model) {
		List<Map<String, String>> staffs = staffService.getWorkingStaffs(branchSeq);
		model.addAttribute("staffs", staffs);
		return "getAssignableStaffs";
	}
}
