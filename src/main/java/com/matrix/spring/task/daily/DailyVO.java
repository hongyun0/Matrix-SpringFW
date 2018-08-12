package com.matrix.spring.task.daily;

public class DailyVO {
	private int dailyTaskSeq;
	private String dailyTask;
	private String assignDate;
	private int importance;
	private String assignType;
	private String assignDetail;
	private int manualTaskSeq;
	private String finisherId;
	private String finisherName;
	private int adminSeq;

	/**업무 배정*/
	public DailyVO(String dailyTask, String assignDate, int importance, String assignType, String assignDetail, int adminSeq) {
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
	public int getDailyTaskSeq() {
		return dailyTaskSeq;
	}
	public String getDailyTask() {
		return dailyTask;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public int getImportance() {
		return importance;
	}
	public String getAssignType() {
		return assignType;
	}
	public String getAssignDetail() {
		return assignDetail;
	}
	public int getManualTaskSeq() {
		return manualTaskSeq;
	}
	public String getFinisherId() {
		return finisherId;
	}
	public String getFinisherName() {
		return finisherName;
	}
	public int getAdminSeq() {
		return adminSeq;
	}
	private void setDailyTaskSeq(int dailyTaskSeq) {
		this.dailyTaskSeq = dailyTaskSeq;
	}
	private void setDailyTask(String dailyTask) {
		this.dailyTask = dailyTask;
	}
	private void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	private void setImportance(int importance) {
		this.importance = importance;
	}
	private void setAssignType(String assignType) {
		this.assignType = assignType;
	}
	private void setAssignDetail(String assignDetail) {
		this.assignDetail = assignDetail;
	}
	public void setManualTaskSeq(int manualTaskSeq) {
		this.manualTaskSeq = manualTaskSeq;
	}
	public void setFinisherId(String finisherId) {
		this.finisherId = finisherId;
	}
	public void setFinisherName(String finisherName) {
		this.finisherName = finisherName;
	}
	private void setAdminSeq(int adminSeq) {
		this.adminSeq = adminSeq;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dailyTaskSeq;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyVO other = (DailyVO) obj;
		if (dailyTaskSeq != other.dailyTaskSeq)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DailyVO [dailyTaskSeq=" + dailyTaskSeq + ", dailyTask=" + dailyTask + ", assignDate=" + assignDate
				+ ", importance=" + importance + ", assignType=" + assignType + ", assignDetail=" + assignDetail
				+ ", manualTaskSeq=" + manualTaskSeq + ", finisherId=" + finisherId + ", finisherName=" + finisherName
				+ ", adminSeq=" + adminSeq + "]";
	}
	
}
