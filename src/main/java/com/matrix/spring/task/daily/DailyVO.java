package com.matrix.spring.task.daily;

import lombok.Data;

@Data
public class DailyVO {
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
	public DailyVO(String dailyTask, String assignDate, String importance, String assignType, String assignDetail, String adminSeq) {
		this(dailyTask, assignDate, assignType, assignDetail);
		setImportance(importance);
		setAdminSeq(adminSeq);
	}
	/**업무 삭제*/
	public DailyVO(String dailyTask, String assignDate, String assignType, String assignDetail) {
		setDailyTask(dailyTask);
		setAssignDate(assignDate);
		setAssignType(assignType);
		setAssignDetail(assignDetail);
	}
}
