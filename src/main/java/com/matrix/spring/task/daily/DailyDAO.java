package com.matrix.spring.task.daily;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.matrix.spring.FormatCheck;
import com.matrix.spring.staff.StaffDAO;
import com.matrix.spring.task.manual.ManualDAO;

@Repository
public class DailyDAO {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private ManualDAO manualDAO;
	@Autowired
	private StaffDAO staffDAO;
	@Autowired
	private FormatCheck formatCheck;

	/**
	 * 선택한 날짜에 해당하는 배정대상 목록 보기
	 * 
	 * @throws ParseException
	 */
	public List<String> getAssignedParts(String assignDate, String branchSeq) throws ParseException {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, +1);
		Date tomorrow = cal.getTime();
		if (new SimpleDateFormat("yyyy/MM/dd").parse(assignDate).after(tomorrow)) {
			throw new RuntimeException("내일 이후는 조회 불가");
		}
		Map<String, String> input = new HashMap<>();
		input.put("assignDate",	assignDate);
		input.put("branchSeq", branchSeq);
		List<String> list = sqlSession.selectList("dailyMapper.getAssignedParts", input);
		return list;
	}

	/**
	 * 파트별 - 선택한 날짜, 배정대상에 속한 업무 목록 보기
	 * 
	 * @throws ParseException
	 */
	public List<Map<String, String>> getDailyTasksForParts(String assignDate, String assignDetail, String branchSeq)
			throws ParseException {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, +1);
		if (new SimpleDateFormat("yyyy/MM/dd").parse(assignDate).after(cal.getTime())) {
			throw new RuntimeException("내일 이후 날짜는 조회 불가");
		}
		Map<String, String> input = new HashMap<>();
		input.put("assignDate", assignDate);
		input.put("assignDetail", assignDetail);
		input.put("branchSeq", branchSeq);
		List<Map<String, String>> list = sqlSession.selectList("dailyMapper.getDailyTasksForParts", input);
		return list;
	}

	/**
	 * 개인별 - 선택한 날짜, 배정대상에 속한 업무 목록 보기
	 * 
	 * @throws ParseException
	 */
	public List<Map<String, String>> getDailyTasksForPerson(String assignDate, String branchSeq) throws ParseException {
		List<Map<String, String>> list = null;
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, +1);
		if (new SimpleDateFormat("yyyy/MM/dd").parse(assignDate).after(cal.getTime())) {
			throw new RuntimeException("내일 이후 날짜는 조회 불가");
		}
		Map<String, String> input = new HashMap<>();
		input.put("assignDate",	assignDate);
		input.put("branchSeq", branchSeq);
		list = sqlSession.selectList("dailyMapper.getDailyTasksForPerson", input);
		return list;
	}

	/** 업무 중복확인 */
	public boolean isDailyTask(String dailyTask, String assignDate, String branchSeq) {
		boolean result = false;
		Map<String, String> input = new HashMap<>();
		input.put("dailyTask", dailyTask);
		input.put("assignDate", assignDate);
		input.put("branchSeq", branchSeq);
		if (sqlSession.selectOne("dailyMapper.isDailyTask", input) != null) {
			result = true;
		}
		return result;
	}

	/** 업무 배정 
	 * @throws ParseException */
	public void addDailyTask(DailyDTO dailyDTO) throws ParseException {
		dailyDTO.setManualTaskSeq(manualDAO.getManualTaskSeq(dailyDTO.getDailyTask()));
		if (dailyDTO.getDailyTask() == null) {
			throw new RuntimeException("입력된 업무가 없습니다.");
		}
		if (isDailyTask(dailyDTO.getDailyTask(), dailyDTO.getAssignDate(), dailyDTO.getBranchSeq()) == true) {
			throw new RuntimeException("이미 배정된 업무입니다.");
		}
		if (!dailyDTO.getAssignType().equals("개인") && !dailyDTO.getAssignType().equals("파트")) {
			throw new RuntimeException("배정유형이 올바르지 않습니다.");
		}
		if (!dailyDTO.getImportance().equals("0") && !dailyDTO.getImportance().equals("1")) {
			throw new RuntimeException("중요도 설정이 올바르지 않습니다.");
		}
		if (formatCheck.getByteSize(dailyDTO.getDailyTask()) > 60) {
			throw new RuntimeException("최대 한글 20자, 영어60자 입력 가능합니다.");
		}
		if (sqlSession.selectOne("dailyMapper.isAdminSeq", dailyDTO.getAdminSeq()) == null) {
			throw new RuntimeException("없는 관리자 코드입니다.");
		}
		String branchSeq = sqlSession.selectOne("dailyMapper.getBranchSeq", dailyDTO.getAdminSeq());
		if (dailyDTO.getAssignType().equals("파트")) {
			Collection<String> workParts = staffDAO.getWorkParts(branchSeq); // 지점에 해당하는 파트 종류 호출
			boolean flag = false;
			for (String tmp : workParts) {
				if (tmp != null && tmp.equals(dailyDTO.getAssignDetail())) {
					flag = true;
				}
			}
			if (!flag) {
				throw new RuntimeException("없는 파트입니다.");
			}
		} else if (dailyDTO.getAssignType().equals("개인")) {
			Collection<Map<String, String>> workingStaffs = staffDAO.getWorkingStaffs(branchSeq); // 지점에 해당하는 재직중인 직원 호출
			boolean flag = false;
			for (Map<String, String> map : workingStaffs) {
				if (map.get("STAFF_ID").equals(dailyDTO.getAssignDetail())) {
					flag = true;
				}
			}
			if (!flag) {
				throw new RuntimeException("없는 직원입니다.");
			}
		} else {
			throw new RuntimeException("없는 배정타입입니다.");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		today.setTime(0);
		Date assignDate = df.parse(dailyDTO.getAssignDate());
		if (today.after(assignDate)) {
			throw new RuntimeException("과거에 업무배정을 할 수 없습니다.");
		}

		if (dailyDTO.getManualTaskSeq() == null) {
			sqlSession.insert("dailyMapper.addDailyTaskByInput", dailyDTO);
		} else {
			sqlSession.insert("dailyMapper.addDailyTaskByManual", dailyDTO);
		}
	}

	/** 특정 날짜에 배정된 업무 전체 출력 */
	public List<String> getDailyTasks(String assignDate, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		input.put("assignDate", assignDate);
		input.put("branchSeq", branchSeq);
		List<String> list = sqlSession.selectList("dailyMapper.getDailyTasks", input);
		return list;
	}

	/**
	 * 업무 수정
	 * 
	 * @throws ParseException
	 */
	public void setDailyTask(String newDailyTask, String oldDailyTask, String assignDate, String assignDetail,
			String newImportance, String branchSeq) throws ParseException {
		Map<String, String> input = new HashMap<>();
		// 과거 업무 수정
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		today.setTime(0);
		Date date = df.parse(assignDate);
		if (today.after(date)) {
			throw new RuntimeException("과거의 업무를 수정 할 수 없습니다.");
		}

		if (!isDailyTask(oldDailyTask, assignDate, branchSeq)) {
			throw new RuntimeException("해당날짜에 없는 업무입니다.");
		}

		if (formatCheck.getByteSize(newDailyTask) > 60) {
			throw new RuntimeException("최대 한글 20자, 영어60자 입력 가능합니다.");
		}
		// 날짜에 해당하는 업무명의 assign_detail 비교
		// SQL 추가 : 업무명, 배정날짜 입력 -> getAssignDetail
		Map<String, String> map = new HashMap<>();
		map.put("oldDailyTask", oldDailyTask);
		map.put("assignDate", assignDate);
		String str = sqlSession.selectOne("dailyMapper.getAssignDetail", map);
		if (!str.equals(assignDetail)) {
			throw new RuntimeException("바꾸려는 업무의 배정대상이 일치하지 않습니다.");
		}

		input.put("newDailyTask", newDailyTask);
		input.put("assignDetail", assignDetail);
		input.put("assignDate", assignDate);
		input.put("oldDailyTask", oldDailyTask);
		input.put("newImportance", newImportance);
		input.put("branchSeq", branchSeq);
		String newManualTaskSeq = manualDAO.getManualTaskSeq(newDailyTask);
		if (newManualTaskSeq == null) {
			sqlSession.update("dailyMapper.setDailyTaskByInput", input);
		} else {
			input.put("newManualTaskSeq", newManualTaskSeq);
			sqlSession.update("dailyMapper.setDailyTaskByManual", input);
		}
	}

	/** 업무 한가지 검색 */
	public Map<String, String> getDailyTask(String dailyTask, String assignDate, String assignDetail, String branchSeq) {
		Map<String, String> result = null;
		Map<String, String> input = new HashMap<>();
		input.put("dailyTask", dailyTask);
		input.put("assignDate", assignDate);
		input.put("assignDetail", assignDetail);
		input.put("branchSeq", branchSeq);
		result = sqlSession.selectOne("dailyMapper.getDailyTask", input);
		if (result == null) {
			throw new RuntimeException("선택된 업무가 존재하지 않습니다.");
		}
		return result;
	}

	/** 업무 재배정 */
	public void setDailyAssign(String newAssignType, String newAssignDetail, String assignDate, String oldAssignType,
			String oldAssignDetail, String dailyTask, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		if (newAssignType == null || newAssignDetail == null) {
			throw new RuntimeException("배정대상이 없습니다.");
		}

		// 해당 지점의 assginDetail 찾아서 newAssignDetail 비교
		if (newAssignType == "파트") {
			boolean flag = false;
			Collection<String> workParts = staffDAO.getWorkParts(branchSeq); // 지점에 해당하는 파트 종류 호출
			for (String tmp : workParts) {
				if (tmp.equals(newAssignDetail)) {
					flag = true;
				}
			}
			if (!flag) {
				throw new RuntimeException("없는 파트입니다.");
			}
		} else if (newAssignType == "개인") {
			boolean flag = false;
			Collection<Map<String, String>> workingStaffs = staffDAO.getWorkingStaffs(branchSeq);
			for (Map<String, String> m : workingStaffs) {
				if (m.get("STAFF_ID").equals(newAssignDetail)) {
					flag = true;
				}
			}
			if (!flag) {
				throw new RuntimeException("없는 직원입니다.");
			}
		} else {
			throw new RuntimeException("없는 배정타입입니다.");
		}

		input.put("newAssignType", newAssignType);
		input.put("newAssignDetail", newAssignDetail);
		input.put("assignDate", assignDate);
		input.put("oldAssignType", oldAssignType);
		input.put("oldAssignDetail", oldAssignDetail);
		input.put("dailyTask", dailyTask);
		input.put("branchSeq", branchSeq);
		sqlSession.update("dailyMapper.setDailyAssign", input);
	}

	/** 업무 삭제 
	 * @throws ParseException */
	public void removeDailyTask(DailyDTO dailyDTO) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		today.setTime(0);
		Date date = df.parse(dailyDTO.getAssignDate());
		if (today.after(date)) {
			throw new RuntimeException("과거의 업무를 삭제 할 수 없습니다.");
		}
		if (getDailyTask(dailyDTO.getDailyTask(), dailyDTO.getAssignDate(), dailyDTO.getAssignDetail(), dailyDTO.getBranchSeq()) == null) {
			throw new RuntimeException("해당 날짜에 존재하지 않는 업무");
		}
		sqlSession.delete("dailyMapper.removeDailyTask", dailyDTO);
	}

	/** 직원: 업무 완료 선택 */
	public void setFinisher(String finisherId, String staffName, String assignDate, String branchSeq, String dailyTask) {
		Map<String, String> input = new HashMap<>();
		input.put("finisherId", finisherId);
		input.put("staffName", staffName);
		input.put("assignDate", assignDate);
		input.put("branchSeq", branchSeq);
		input.put("dailyTask", dailyTask);
		sqlSession.update("dailyMapper.setFinisher", input);
	}

}
