package com.matrix.spring.task.daily;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyService implements IDailyService {
	@Autowired
	DailyDAO dailyDAO;

	@Override
	public List<String> getAssignedParts(String assignDate, String branchSeq) throws ParseException {
		// TODO Auto-generated method stub
		List<String> parts = dailyDAO.getAssignedParts(assignDate, branchSeq);
		boolean personal = true;
		List<Map<String, String>> list = dailyDAO.getDailyTasksForPerson(assignDate, branchSeq);
		if(list == null){
			personal = false;
		} else if(list.isEmpty()){
			personal = false;
		}
		if (personal) {
			parts.add("personal");
		}
		return parts;
	}

	@Override
	public List<Map<String, String>> getDailyTasks(String assignDate, String assignType, String branchSeq) throws ParseException {
		List<Map<String, String>> tasks = null;
		if(assignType.equals("개인")){
			tasks = dailyDAO.getDailyTasksForPerson(assignDate, branchSeq);
		} else {
			tasks = dailyDAO.getDailyTasksForParts(assignDate, assignType, branchSeq);
		}
		return tasks;
	}

	@Override
	public void setDailyTask(String newDailyTask, String oldDailyTask, String assignDate, String assignDetail,
			String newImportance, String branchSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDailyTask(String dailyTask, String assignDate, String assignType, String assignDetail,
			String branchSeq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, String>> getRecommendedTasks(String date, String branchSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDailyTask(String dailyTask, String assignDate, String branchSeq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addDailyTask(DailyVO dailyVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFinisher(String userId, String staffName, String assignDate, String branchSeq, String dailyTask) {
		// TODO Auto-generated method stub
		
	}

	
}