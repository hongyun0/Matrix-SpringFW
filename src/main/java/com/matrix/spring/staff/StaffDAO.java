package com.matrix.spring.staff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.matrix.spring.FormatCheck;

@Repository
public class StaffDAO {
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private FormatCheck formatCheck;

	/** 배정할 파트 목록 보기: 업무배정, 업무재배정 */
	public List<String> getWorkParts(String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("getWorkParts 실패 nullBranchSeq");
		}
		return staffMapper.getWorkParts(branchSeq);
	}

	/** 배정할 직원 목록 보기: 업무배정, 업무재배정 / 재직 중인 직원 목록 보기 */
	public List<Map<String, String>> getWorkingStaffs(String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("getWorkingStaffs 실패 nullBranchSeq");
		}
		return staffMapper.getWorkingStaffs(branchSeq);
	}

	/** 직원 인증요청 목록 보기 */
	public List<Map<String, String>> getPreStaffs(String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("getPreStaffs 실패 nullBranchSeq");
		}
		return staffMapper.getPreStaffs(branchSeq);
	}

	/** 직원 인증요청 수락 - 인증일은 시스템날짜 */
	public void setJoinDate(String staffId, String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("setJoinDate 실패 nullBranchSeq");
		}
		staffMapper.setJoinDate(staffId, branchSeq);
	}

	/** 직원 인증요청 거부 */
	public void removeStaff(String staffId, String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("removeStaff 실패 nullBranchSeq");
		}
		staffMapper.removeStaff(staffId, branchSeq);
	}

	/** 퇴사 직원 등록 - 퇴사일은 시스템날짜 */
	public void setLeaveDate(String staffId, String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("setLeaveDate 실패 nullBranchSeq");
		}
		staffMapper.setLeaveDate(staffId, branchSeq);
	}

	/** 퇴사한 직원 목록 보기 */
	public List<Map<String, String>> getLeftStaffs(String branchSeq) {
		if (!isBranchSeq(branchSeq)) {
			throw new RuntimeException("getLeftStaffs 실패 nullBranchSeq");
		}
		return staffMapper.getLeftStaffs(branchSeq);
	}

	/**
	 * 직원 상세 정보 보기, 직원정보변경 시 기존정보 출력(유지)용. 해당 지점의 가장 최근 내역 1건 출력(null있으면 null이 최근) -
	 */
	public Map<String, String> getStaffDetail(String staffId, String branchSeq) {
		Map<String, String> result = null;
		result = staffMapper.getStaffDetail(staffId, branchSeq);
		if (result == null) {
			throw new RuntimeException("getStaffDetail 결과:null");
		}
		return result;
	}

	/** 직원 등록(승인요청) */
	public void addStaff(StaffDTO staffDTO) {
		if (!formatCheck.isInputLength(staffDTO.getAccountNum(), 0, 20)
				|| !formatCheck.isInputLength(staffDTO.getResumeFile(), 0, 40)
				|| !formatCheck.isInputLength(staffDTO.getHealthFile(), 0, 40)
				|| !formatCheck.isInputLength(staffDTO.getBankFile(), 0, 40)) {
			throw new RuntimeException("입력값 길이 제한 초과");
		}
		
		Map<String, String> staff = getStaffDetail(staffDTO.getStaffId(), staffDTO.getBranchSeq());
		if ((staff.get("JOIN_DATE") == null) && (staff.get("LEAVE_DATE") == null)) {
			throw new RuntimeException("addStaff 실패:이미 승인요청함");
		}
		if (staff.get("JOIN_DATE") != null) {
			throw new RuntimeException("addStaff 실패:이미 재직중인 직원");
		}
		if (!isBranchSeq(staffDTO.getBranchSeq())) {
			throw new RuntimeException("addStaff 실패:없는 지점 코드");
		}
		if (!formatCheck.isFileFormat(staffDTO.getResumeFile()) || !formatCheck.isFileFormat(staffDTO.getHealthFile())
				|| !formatCheck.isFileFormat(staffDTO.getBankFile())) {
			throw new RuntimeException("addStaff insert 실패:파일형식 오류");
		}
		if (!formatCheck.isNumberFormat(staffDTO.getAccountNum())) {
			throw new NumberFormatException("addStaff 실패:계좌번호 형식 오류");
		}
		staffMapper.addStaff(staffDTO);
	}

	/** 직원 회원정보 변경 - 모든 정보 */
	public void setStaffInfo(StaffDTO staffDTO) {
		if (!formatCheck.isFileFormat(staffDTO.getResumeFile()) || !formatCheck.isFileFormat(staffDTO.getHealthFile())
				|| !formatCheck.isFileFormat(staffDTO.getBankFile())) {
			throw new RuntimeException("setStaffInfo 실패:파일형식 오류");
		}
		if (!formatCheck.isNumberFormat(staffDTO.getAccountNum())) {
			throw new NumberFormatException("setStaffInfo 실패:계좌번호 형식 오류");
		}
		staffMapper.setStaffInfo(staffDTO);
	}

	/** 직원목록 - 직원 소속파트 배정/변경 */
	public void setWorkPart(String workPart, String staffId, String branchSeq) {
		staffMapper.setWorkPart(workPart, staffId, branchSeq);
	}

	/** 있는 지점인지 검사 */
	public boolean isBranchSeq(String branchSeq) {
		return staffMapper.isBranchSeq(branchSeq) != null;
	}

}
