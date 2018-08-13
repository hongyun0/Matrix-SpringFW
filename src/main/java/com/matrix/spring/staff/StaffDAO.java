package com.matrix.spring.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.matrix.spring.FormatCheck;

@Repository
public class StaffDAO {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private FormatCheck formatCheck;

	/** 배정할 파트 목록 보기: 업무배정, 업무재배정 */
	public List<String> getWorkParts(String branchSeq) {
		List<String> list = sqlSession.selectList("staffMapper.getWorkParts", branchSeq);
		return list;
	}

	/** 배정할 직원 목록 보기: 업무배정, 업무재배정 / 재직 중인 직원 목록 보기 */
	public List<Map<String, String>> getWorkingStaffs(String branchSeq) {
		List<Map<String, String>> list = sqlSession.selectList("staffMapper.getWorkingStaffs", branchSeq);
		return list;
	}

	/** 직원 인증요청 목록 보기 */
	public List<Map<String, String>> getPreStaffs(String branchSeq) {
		List<Map<String, String>> list = new ArrayList<>();
		list = sqlSession.selectList("staffMapper.getPreStaffs", branchSeq);
		return list;
	}

	/** 직원 인증요청 수락 - 인증일은 시스템날짜 */
	public void setJoinDate(String staffId, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		input.put("staffId", staffId);
		input.put("branchSeq", branchSeq);
		sqlSession.update("staffMapper.setJoinDate", input);
	}

	/** 직원 인증요청 거부 */
	public void removeStaff(String staffId, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		input.put("staffId", staffId);
		input.put("branchSeq", branchSeq);
		sqlSession.delete("staffMapper.removeStaff", input);
	}

	/** 퇴사 직원 등록 - 퇴사일은 시스템날짜 */
	public void setLeaveDate(String staffId, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		input.put("staffId", staffId);
		input.put("branchSeq", branchSeq);
		sqlSession.update("staffMapper.setLeaveDate", input);
	}

	/** 퇴사한 직원 목록 보기 */
	public List<Map<String, String>> getLeftStaffs(String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("getLeftStaffs 실패 nullBranchSeq");
		}
		List<Map<String, String>> list = sqlSession.selectList("staffMapper.getLeftStaffs", branchSeq);
		return list;
	}

	/**
	 * 직원 상세 정보 보기, 직원정보변경 시 기존정보 출력(유지)용. 해당 지점의 가장 최근 내역 1건 출력(null있으면 null이 최근) -
	 */
	public Map<String, String> getStaffDetail(String staffId, String branchSeq) {
		Map<String, String> input = new HashMap<>();
		input.put("staffId", staffId);
		input.put("branchSeq", branchSeq);
		Map<String, String> result = null;
		result = sqlSession.selectOne("staffMapper.getStaffDetail", input);
		if (result == null) {
			throw new RuntimeException("getStaffDetail 결과:null");
		}
		return result;
	}

	/** 직원 등록(승인요청) */
	public void addStaff(StaffVO vo) {
		Map<String, String> staff = getStaffDetail(vo.getStaffId(), vo.getBranchSeq());
		if (!formatCheck.isInputLength(vo.getAccountNum(), 0, 20)
				|| !formatCheck.isInputLength(vo.getResumeFile(), 0, 40)
				|| !formatCheck.isInputLength(vo.getHealthFile(), 0, 40)
				|| !formatCheck.isInputLength(vo.getBankFile(), 0, 40)) {
			throw new RuntimeException("입력값 길이 제한 초과");
		}
		if ((staff.get("JOIN_DATE") == null) && (staff.get("LEAVE_DATE") == null)) {
			throw new RuntimeException("addStaff 실패:이미 승인요청함");
		}
		if (staff.get("JOIN_DATE") != null) {
			throw new RuntimeException("addStaff 실패:이미 재직중인 직원");
		}
		if (!isBranchSeq(vo.getBranchSeq())) {
			throw new RuntimeException("addStaff 실패:없는 지점 코드");
		}
		if (!formatCheck.isFileFormat(vo.getResumeFile()) || !formatCheck.isFileFormat(vo.getHealthFile())
				|| !formatCheck.isFileFormat(vo.getBankFile())) {
			throw new RuntimeException("addStaff insert 실패:파일형식 오류");
		}
		if (!formatCheck.isNumberFormat(vo.getAccountNum())) {
			throw new NumberFormatException("addStaff 실패:계좌번호 형식 오류");
		}
		sqlSession.insert("staffMapper.addStaff", vo);
	}

	/** 직원 회원정보 변경 - 모든 정보 */
	public void setStaffInfo(StaffVO vo) {
		if (!formatCheck.isFileFormat(vo.getResumeFile()) || !formatCheck.isFileFormat(vo.getHealthFile())
				|| !formatCheck.isFileFormat(vo.getBankFile())) {
			throw new RuntimeException("setStaffInfo 실패:파일형식 오류");
		}
		if (!formatCheck.isNumberFormat(vo.getAccountNum())) {
			throw new NumberFormatException("setStaffInfo 실패:계좌번호 형식 오류");
		}
		sqlSession.update("staffMapper.setStaffInfo", vo);
	}

	/** 직원목록 - 직원 소속파트 배정/변경 */
	public void setWorkPart(StaffVO vo) {
		sqlSession.update("staffMapper.setWorkPart", vo);
	}

	/** 특정 직원의 입사/퇴사 날짜 존재여부 조회 */
	public boolean isStaffDate(String staffId, String branchSeq, String joinDate, String leaveDate) {
		Map<String, String> input = new HashMap<>();
		input.put("staffId", staffId);
		input.put("branchSeq", branchSeq);
		input.put("joinDate", joinDate);
		input.put("leaveDate", leaveDate);
		boolean result = false;
		if (sqlSession.selectOne("staffMapper.isStaffDate", input) != null) {
			result = true;
		}
		return result;
	}

	/** 있는 지점인지 검사 */
	public boolean isBranchSeq(String branchSeq) {
		return sqlSession.selectOne("staffMapper.isBranchSeq", branchSeq) != null;
	}

}
