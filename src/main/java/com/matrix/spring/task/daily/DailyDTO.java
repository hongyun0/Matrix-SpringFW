package com.matrix.spring.task.daily;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyDTO {
	private String dailyTaskSeq;
	private String dailyTask;
	private String assignDate;
	private String importance;
	private String assignType;
	private String assignDetail;
	private String manualTaskSeq;
	private String finisherId;
	private String finisherName;
	private String adminSeq;
	private String branchSeq;

	/**업무 배정*/
	public DailyDTO(String dailyTask, String assignDate, String importance, String assignType, String assignDetail) {
		setDailyTask(dailyTask);
		setAssignDate(assignDate);
		setImportance(importance);
		setAssignType(assignType);
		setAssignDetail(assignDetail);
	}
	public DailyDTO(String dailyTask, String assignDate, String importance, String assignType, String assignDetail, String adminSeq) {
		setDailyTask(dailyTask);
		setAssignDate(assignDate);
		setImportance(importance);
		setAssignType(assignType);
		setAssignDetail(assignDetail);
		setAdminSeq(adminSeq);
	}
	
	/**업무 삭제*/
	public DailyDTO(String dailyTask, String assignDate, String assignType, String assignDetail) {
		setDailyTask(dailyTask);
		setAssignDate(assignDate);
		setAssignType(assignType);
		setAssignDetail(assignDetail);
	}
}
