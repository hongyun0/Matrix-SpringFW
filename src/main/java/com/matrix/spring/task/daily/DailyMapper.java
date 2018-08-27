package com.matrix.spring.task.daily;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DailyMapper {
	void addDailyTask(DailyDTO dailyDTO);

	List<String> getAssignedParts(@Param("assignDate") String assignDate, @Param("branchSeq") String branchSeq);

	List<Map<String, String>> getDailyTasksForParts(@Param("assignDate") String assignDate,
			@Param("assignDetail") String assignDetail, @Param("branchSeq") String branchSeq);

	List<Map<String, String>> getDailyTasksForPerson(@Param("assignDate") String assignDate,
			@Param("branchSeq") String branchSeq);

	List<String> getDailyTasks(@Param("assignDate") String assignDate, @Param("branchSeq") String branchSeq);

	String isDailyTask(@Param("assignDate") String assignDate, @Param("dailyTask") String dailyTask,
			@Param("branchSeq") String branchSeq);

	Map<String, String> getDailyTask(DailyDTO dailyDTO);

	String getBranchSeq(String adminSeq);

	String isAdminSeq(String adminSeq);

	String getAdminSeqByAssignDateDailyTask(@Param("assignDate") String assignDate,
			@Param("dailyTask") String dailyTask, @Param("branchSeq") String branchSeq);

	String getAssignDetail(@Param("dailyTask") String dailyTask, @Param("assignDate") String assignDate,
			@Param("branchSeq") String branchSeq);

	void setDailyTask(@Param("newDailyTask") String newDailyTask,
			@Param("newManualTaskSeq") String newManualTaskSeq, @Param("newImportance") String newImportance,
			@Param("assignDate") String assignDate, @Param("assignDetail") String assignDetail,
			@Param("oldDailyTask") String oldDailyTask, @Param("branchSeq") String branchSeq);

	void setDailyAssign(@Param("newAssignType") String newAssignType, @Param("newAssignDetail") String newAssignDetail,
			@Param("assignDate") String assignDate, @Param("oldAssignType") String oldAssignType,
			@Param("oldAssignDetail") String oldAssignDetail, @Param("dailyTask") String dailyTask,
			@Param("branchSeq") String branchSeq);
	
	void setFinisher(@Param("finisherId") String finisherId, @Param("staffName") String staffName,
			@Param("assignDate") String assignDate, @Param("dailyTask") String dailyTask,
			@Param("branchSeq") String branchSeq);
	
	void setUnfinished(String date);
	
	void removeDailyTask(DailyDTO dailyDTO);
}
