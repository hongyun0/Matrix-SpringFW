package com.matrix.spring.task.manual;

import java.util.List;
import java.util.Map;

public interface IManualService {
	List<String> getSpaceTypes();
	List<String> getTaskTypesBySpaceType(String spaceType);
	List<Map<String, String>> getManualTasks(String spaceType, String taskType);
	List<Map<String, String>> getRecommendedTasks(String date, String branchSeq);
	List<Map<String, String>> searchManualTasks(String inputText);
}
