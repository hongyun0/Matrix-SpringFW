package com.matrix.spring.task.manual;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManualDAO {
	@Autowired
	private SqlSession sqlSession;

	/** 추천업무 출력 : 업무배정, 업무수정 */
	public List<Map<String, String>> getRecommendedTasks(String date) {
		List<Map<String, String>> tasks = sqlSession.selectList("manualMapper.getPeriodicManualTasks");
		for (Map<String, String> task : tasks) {
			Map<String, String> input = new HashMap<>();
			input.putAll(task);
			input.put("date", date);
			if ((int)sqlSession.selectOne("manualMapper.getRecommendedTasks", input) > 0) {
				tasks.remove(task);
			}
		}
		return tasks;
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
		List<Map<String, String>> list = null;
		list = sqlSession.selectList("manualMapper.searchManualTasks", inputText);
		return list;
	}

	/** (대분류)공간분류 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<String> getSpaceTypes() {
		List<String> list = null;
		list = sqlSession.selectList("manualMapper.getSpaceTypes");
		return list;
	}

	/** (중분류)선택한 공간에 속한 업무분류 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<String> getTaskTypesBySpaceType(String spaceType) {
		if (spaceType == null) {
			return null;
		}
		List<String> list = null;
		list = sqlSession.selectList("manualMapper.getTaskTypesBySpaceType", spaceType);
		return list;
	}

	/** (대분류)업무분류 보기: 매뉴얼 보기 */
	public List<String> getTaskTypes() {
		List<String> list = null;
		list = sqlSession.selectList("manualMapper.getTaskTypes");
		return list;
	}

	/** (중분류)선택한 업무에 속한 공간분류 보기: 매뉴얼 보기 */
	public List<String> getSpaceTypesByTaskType(String taskType) {
		if (taskType == null) {
			return null;
		}
		List<String> list = null;
		list = sqlSession.selectList("manualMapper.getSpaceTypesByTaskType", taskType);
		return list;
	}

	/** (공통 소분류)선택한 공간/업무분류에 속한 업무 목록 보기: 업무배정, 업무수정, 매뉴얼 보기 */
	public List<Map<String, String>> getManualTasks(String spaceType, String taskType) {
		List<Map<String, String>> list = null;
		Map<String, String> input = new HashMap<>();
		input.put("spaceType", spaceType);
		input.put("taskType", taskType);
		list = sqlSession.selectList("manualMapper.getTasks", input);
		return list;
	}

	/** i: 업무명, o: ManualTaskSeq 단 값이 -1인경우는 해당 없다. */
	public String getManualTaskSeq(String searchTask) {
		String manualTaskSeq = null;
		manualTaskSeq = sqlSession.selectOne("manualMapper.getManualTaskSeq", searchTask);
		return manualTaskSeq;
	}

}
