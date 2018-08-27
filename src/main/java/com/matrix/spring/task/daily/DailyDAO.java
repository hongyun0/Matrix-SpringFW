package com.matrix.spring.task.daily;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.matrix.spring.FormatCheck;
import com.matrix.spring.staff.StaffDAO;
import com.matrix.spring.task.manual.ManualDAO;

@Repository
public class DailyDAO {
	@Autowired
	private DailyMapper dailyMapper;
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
		List<String> list = dailyMapper.getAssignedParts(assignDate, branchSeq);
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
		List<Map<String, String>> list = dailyMapper.getDailyTasksForParts(assignDate, assignDetail, branchSeq);
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
		list = dailyMapper.getDailyTasksForPerson(assignDate, branchSeq);
		return list;
	}

	/** 업무 중복확인 */
	public boolean isDailyTask(String dailyTask, String assignDate, String branchSeq) {
		boolean result = false;
		if (dailyMapper.isDailyTask(assignDate, dailyTask, branchSeq) != null) {
			result = true;
		}
		return result;
	}

	/**
	 * 업무 배정
	 * 
	 * @throws ParseException
	 */
	@Transactional
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
		if (dailyMapper.isAdminSeq(dailyDTO.getAdminSeq()) == null) {
			throw new RuntimeException("없는 관리자 코드입니다.");
		}
		if (dailyDTO.getAssignType().equals("파트")) {
			Collection<String> workParts = staffDAO.getWorkParts(dailyDTO.getBranchSeq()); // 지점에 해당하는 파트 종류 호출
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
			Collection<Map<String, String>> workingStaffs = staffDAO.getWorkingStaffs(dailyDTO.getBranchSeq());
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

		dailyMapper.addDailyTask(dailyDTO);
	}

	/** 특정 날짜에 배정된 업무 전체 출력 */
	public List<String> getDailyTasks(String assignDate, String branchSeq) {
		List<String> list = dailyMapper.getDailyTasks(assignDate, branchSeq);
		return list;
	}

	/**
	 * 업무 수정
	 * 
	 * @throws ParseException
	 */
	@Transactional
	public void setDailyTask(String newDailyTask, String oldDailyTask, String assignDate, String assignDetail,
			String newImportance, String branchSeq) throws ParseException {
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
		String str = dailyMapper.getAssignDetail(oldDailyTask, assignDate, branchSeq);
		if (!str.equals(assignDetail)) {
			throw new RuntimeException("바꾸려는 업무의 배정대상이 일치하지 않습니다.");
		}

		String newManualTaskSeq = manualDAO.getManualTaskSeq(newDailyTask);
		dailyMapper.setDailyTask(newDailyTask, newManualTaskSeq, newImportance, assignDate, assignDetail, oldDailyTask,
				branchSeq);
	}

	/** 업무 한가지 검색 */
	public Map<String, String> getDailyTask(DailyDTO dailyDTO) {
		Map<String, String> result = null;
		result = dailyMapper.getDailyTask(dailyDTO);
		if (result == null) {
			throw new RuntimeException("선택된 업무가 존재하지 않습니다.");
		}
		return result;
	}

	/** 업무 재배정 */
	@Transactional
	public void setDailyAssign(String newAssignType, String newAssignDetail, String assignDate, String oldAssignType,
			String oldAssignDetail, String dailyTask, String branchSeq) {
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

		dailyMapper.setDailyAssign(newAssignType, newAssignDetail, assignDate, oldAssignType, oldAssignDetail,
				dailyTask, branchSeq);
	}

	/**
	 * 업무 삭제
	 * 
	 * @throws ParseException
	 */
	@Transactional
	public void removeDailyTask(DailyDTO dailyDTO) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		today.setTime(0);
		Date date = df.parse(dailyDTO.getAssignDate());
		if (today.after(date)) {
			throw new RuntimeException("과거의 업무를 삭제 할 수 없습니다.");
		}
		if (getDailyTask(dailyDTO) == null) {
			throw new RuntimeException("해당 날짜에 존재하지 않는 업무");
		}
		dailyMapper.removeDailyTask(dailyDTO);
	}

	/** 직원: 업무 완료 선택 */
	public void setFinisher(String finisherId, String staffName, String assignDate, String branchSeq,
			String dailyTask) {
		dailyMapper.setFinisher(finisherId, staffName, assignDate, dailyTask, branchSeq);
	}

	/** 미완료 업무 FINISHER 등록 : 매일 자정 마다 batch 실행 */
	public void setUnfinished() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String date = df.format(new Date());
		dailyMapper.setUnfinished(date);
	}
}
