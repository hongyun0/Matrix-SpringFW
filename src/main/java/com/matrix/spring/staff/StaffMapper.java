package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StaffMapper {
	void addStaff(StaffDTO staffDTO);

	List<String> getWorkParts(String branchSeq);

	List<Map<String, String>> getWorkingStaffs(String branchSeq);

	List<Map<String, String>> getPreStaffs(String branchSeq);

	List<Map<String, String>> getLeftStaffs(String branchSeq);

	Map<String, String> getStaffDetail(@Param("staffId") String staffId, @Param("branchSeq") String branchSeq);

	String isBranchSeq(String branchSeq);

	void setStaffInfo(StaffDTO staffDTO);

	void setWorkPart(@Param("workPart") String workPart, @Param("staffId") String staffId,
			@Param("branchSeq") String branchSeq);

	void setJoinDate(@Param("staffId") String staffId, @Param("branchSeq") String branchSeq);
	
	void setLeaveDate(@Param("staffId") String staffId, @Param("branchSeq") String branchSeq);
	
	void removeStaff(@Param("staffId") String staffId, @Param("branchSeq") String branchSeq);
}
