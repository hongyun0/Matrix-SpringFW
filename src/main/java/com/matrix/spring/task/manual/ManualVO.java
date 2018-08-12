package com.matrix.spring.task.manual;

public class ManualVO {
	/**member data*/
	private int manualTaskSeq;
	private String manualTask;
	private String repeatType;
	private String repeatDetail;
	private String spaceType;
	private String taskType;
	
	/**constructor: 반복 기준 없는 업무*/
	public ManualVO(int manualTaskSeq, String manualTask, String spaceType, String taskType) {
		setManualTaskSeq(manualTaskSeq);
		setManualTask(manualTask);
		setSpaceType(spaceType);
		setTaskType(taskType);
	}

	/**constructor: 반복 기준 있는 업무*/
	public ManualVO(int manualTasksSeq, String manualTask, String repeatType, String repeatDetail, String spaceType, String taskType) {
		this(manualTasksSeq, manualTask, spaceType, taskType);
		setRepeatType(repeatType);
		setRepeatDetail(repeatDetail);
	}

	
	/**get(), set()*/
	public int getManualTaskSeq() {
		return manualTaskSeq;
	}

	public String getManualTask() {
		return manualTask;
	}

	public String getRepeatType() {
		return repeatType;
	}

	public String getRepeatDetail() {
		return repeatDetail;
	}

	public String getSpaceType() {
		return spaceType;
	}

	public String getTaskType() {
		return taskType;
	}

	private void setManualTaskSeq(int manualTaskSeq) {
		this.manualTaskSeq = manualTaskSeq;
	}

	private void setManualTask(String manualTask) {
		this.manualTask = manualTask;
	}

	private void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

	private void setRepeatDetail(String repeatDetail) {
		this.repeatDetail = repeatDetail;
	}

	private void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}

	private void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**toString()*/
	@Override
	public String toString() {
		return "ManualVO [manualTaskSeq=" + manualTaskSeq + ", manualTask=" + manualTask + ", repeatType=" + repeatType
				+ ", repeatDetail=" + repeatDetail + ", spaceType=" + spaceType + ", taskType=" + taskType + "]";
	}

	
	/**equals()*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + manualTaskSeq;
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
		ManualVO other = (ManualVO) obj;
		if (manualTaskSeq != other.manualTaskSeq)
			return false;
		return true;
	}
	
	
	
}
