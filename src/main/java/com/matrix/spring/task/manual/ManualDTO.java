package com.matrix.spring.task.manual;

import lombok.Data;

@Data
public class ManualDTO {
	/**member data*/
	private int manualTaskSeq;
	private String manualTask;
	private String repeatType;
	private String repeatDetail;
	private String spaceType;
	private String taskType;
	
	/**constructor: 반복 기준 없는 업무*/
	public ManualDTO(int manualTaskSeq, String manualTask, String spaceType, String taskType) {
		setManualTaskSeq(manualTaskSeq);
		setManualTask(manualTask);
		setSpaceType(spaceType);
		setTaskType(taskType);
	}

	/**constructor: 반복 기준 있는 업무*/
	public ManualDTO(int manualTasksSeq, String manualTask, String repeatType, String repeatDetail, String spaceType, String taskType) {
		this(manualTasksSeq, manualTask, spaceType, taskType);
		setRepeatType(repeatType);
		setRepeatDetail(repeatDetail);
	}

}
