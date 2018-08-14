package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

public interface IStaffService {
	List<String> getWorkParts(String branchSeq);
	List<Map<String, String>> getWorkingStaffs(String branchSeq);
}
