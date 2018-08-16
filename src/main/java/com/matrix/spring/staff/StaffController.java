package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StaffController {
	@Autowired
	StaffService staffService;
	
	@RequestMapping(value = "/admin/employee/workparts", method = RequestMethod.GET)
	public String getWorkParts(@SessionAttribute String branchSeq, Model model) {
		try {
			List<String> parts = staffService.getWorkParts(branchSeq);
			model.addAttribute("workParts", parts);
		} catch (Exception e) {
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
		}
		return "getWorkParts";
	}
	
	@RequestMapping(value = "/admin/employee/assignable", method = RequestMethod.GET)
	public String getAssignableStaffs(@SessionAttribute String branchSeq, Model model) {
		try {
			List<Map<String, String>> staffs = staffService.getWorkingStaffs(branchSeq);
			model.addAttribute("staffs", staffs);
		} catch (Exception e) {
			log.warn("WARN! {} : '{}' [{}]", e.getClass().getName(), e.getMessage(), e.getStackTrace()[0]);
		}
		return "getAssignableStaffs";
	}
}
