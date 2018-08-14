package com.matrix.spring.task.manual;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManualService implements IManualService {
	@Autowired
	ManualDAO manualDAO;
	
	@Override
	public List<String> getSpaceTypes() {
		return manualDAO.getSpaceTypes();
	}

	@Override
	public List<String> getTaskTypesBySpaceType(String spaceType) {
		return manualDAO.getTaskTypesBySpaceType(spaceType);
	}

	@Override
	public List<Map<String, String>> getManualTasks(String spaceType, String taskType) {
		return manualDAO.getManualTasks(spaceType, taskType);
	}

	@Override
	public List<Map<String, String>> getRecommendedTasks(String date, String branchSeq) {
		return manualDAO.getRecommendedTasks(date, branchSeq);
	}

	@Override
	public List<Map<String, String>> searchManualTasks(String inputText) {
		return manualDAO.searchManualTasks(inputText);
	}
	
}
