package com.matrix.spring.task.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ManualDAO {
	@Autowired
	private ManualMapper manualMapper;

	/** 추천업무 출력 : 업무배정, 업무수정 */
	@Transactional
	public List<Map<String, String>> getRecommendedTasks(String date, String branchSeq) {
		List<Map<String, String>> tasks = manualMapper.getPeriodicManualTasks();
		List<Map<String, String>> output = new ArrayList<>();
		for (Map<String, String> task : tasks) {
			Map<String, String> input = new HashMap<>();
			input.putAll(task);
			input.put("date", date);
			input.put("branchSeq", branchSeq);
			if (manualMapper.isRecommendedTask(input) == 0) {
				output.add(task);
			}
		}
		return output;
	}

	/** 매뉴얼 업무 검색 (자동완성) */
	public List<Map<String, String>> searchManualTasks(String inputText) {
		if (inputText == null) {
			return null;
		}
		String[] inputs = inputText.split(" ");
		inputText = "%";
		for (String i : inputs) {
			inputText += i + "%";
		}
		return manualMapper.searchManualTasks(inputText);
	}

	/** (대분류)공간분류 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<String> getSpaceTypes() {
		return manualMapper.getSpaceTypes();
	}

	/** (중분류)선택한 공간에 속한 업무분류 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<String> getTaskTypesBySpaceType(String spaceType) {
		if (spaceType == null) {
			return null;
		}
		return manualMapper.getTaskTypesBySpaceType(spaceType);
	}

	/** (대분류)업무분류 보기: 매뉴얼 보기 */
	public List<String> getTaskTypes() {
		return manualMapper.getTaskTypes();
	}

	/** (중분류)선택한 업무에 속한 공간분류 보기: 매뉴얼 보기 */
	public List<String> getSpaceTypesByTaskType(String taskType) {
		if (taskType == null) {
			return null;
		}
		return manualMapper.getSpaceTypesByTaskType(taskType);
	}

	/** (공통 소분류)선택한 공간/업무분류에 속한 업무 목록 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<Map<String, String>> getManualTasks(String spaceType, String taskType) {
		return manualMapper.getTasks(spaceType, taskType);
	}

	/** i: 업무명, o: ManualTaskSeq */
	public String getManualTaskSeq(String searchTask) {
		return manualMapper.getManualTaskSeq(searchTask);
	}

}
