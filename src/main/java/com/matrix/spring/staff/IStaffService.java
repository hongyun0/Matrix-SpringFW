package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

public interface IStaffService {
	void addStaff(StaffDTO staffDTO);
	List<String> getWorkParts(String branchSeq);
	List<Map<String, String>> getWorkingStaffs(String branchSeq);
	List<Map<String, String>> getPreStaffs(String branchSeq);
	List<Map<String, String>> getLeftStaffs(String branchSeq);
	Map<String, String> getStaffDetail(String staffId, String branchSeq);
	void setJoinDate(String staffId, String branchSeq);
	void setLeaveDate(String staffId, String branchSeq);
	void setWorkPart(String workPart, String staffId, String branchSeq);
	void setStaffInfo(StaffDTO staffDTO);
	void removeStaff(String staffId, String branchSeq);
}
