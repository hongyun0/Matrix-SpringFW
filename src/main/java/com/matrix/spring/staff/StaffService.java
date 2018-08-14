package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService implements IStaffService {
	@Autowired
	StaffDAO staffDAO;

	@Override
	public List<String> getWorkParts(String branchSeq) {
		return staffDAO.getWorkParts(branchSeq);
	}

	@Override
	public List<Map<String, String>> getWorkingStaffs(String branchSeq) {
		return staffDAO.getWorkingStaffs(branchSeq);
	}
	
	
}
