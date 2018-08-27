package com.matrix.spring.task.manual;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ManualMapper {
	List<Map<String, String>> getPeriodicManualTasks();

	int isRecommendedTask(Map<String, String> input);

	List<Map<String, String>> searchManualTasks(String inputText);

	List<String> getSpaceTypes();

	List<String> getTaskTypesBySpaceType(String spaceType);

	List<String> getTaskTypes();

	List<String> getSpaceTypesByTaskType(String taskType);

	List<Map<String, String>> getTasks(@Param("spaceType") String spaceType, @Param("taskType") String taskType);

	String getManualTaskSeq(String searchTask);
}
