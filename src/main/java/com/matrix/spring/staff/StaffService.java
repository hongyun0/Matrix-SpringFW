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

	@Override
	public void addStaff(StaffDTO staffDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, String>> getPreStaffs(String branchSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getLeftStaffs(String branchSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getStaffDetail(String staffId, String branchSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJoinDate(String staffId, String branchSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLeaveDate(String staffId, String branchSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkPart(String workPart, String staffId, String branchSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStaffInfo(StaffDTO staffDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStaff(String staffId, String branchSeq) {
		// TODO Auto-generated method stub
		
	}
	
	
}
