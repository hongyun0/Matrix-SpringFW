package com.matrix.spring.task.daily;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IDailyService {
	List<String> getAssignedParts(String assignDate, String branchSeq) throws ParseException;
	List<Map<String, String>> getDailyTasks(String assignDate, String assignType, String branchSeq) throws ParseException;
	boolean isDailyTask(String dailyTask, String assignDate, String branchSeq);
	void addDailyTask(DailyDTO dailyDTO) throws ParseException;
	void setUnfinished();
/*	void setDailyTask(String newDailyTask, String oldDailyTask, String assignDate, String assignDetail, String newImportance, String branchSeq);
	void removeDailyTask(String dailyTask, String assignDate, String assignType, String assignDetail, String branchSeq);
	void setFinisher(String userId, String staffName, String assignDate, String branchSeq, String dailyTask); */
	}
