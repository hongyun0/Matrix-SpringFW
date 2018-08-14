package com.matrix.spring.staff;

import lombok.Data;

@Data
public class StaffDTO {
	/**member data*/
	private String staffId;
	private String staffName;
	private String branchSeq;
	private String workPart;
	private String bankName;
	private String accountNum;
	private String resumeFile;
	private String healthFile;
	private String bankFile;
	
	/**직원등록요청, 직원회원정보변경용*/
	public StaffDTO(String staffId, String branchSeq, String bankName, String accountNum, String resumeFile, String healthFile, String bankFile) {
		setStaffId(staffId);
		setBranchSeq(branchSeq);
		setBankName(bankName);
		setAccountNum(accountNum);
		setResumeFile(resumeFile);
		setHealthFile(healthFile);
		setBankFile(bankFile);
	}
	
	/**직원목록 조회용*/
	public StaffDTO(String staffId, String branchSeq, String workPart) {
		setStaffId(staffId);
		setBranchSeq(branchSeq);
		setWorkPart(workPart);
	}

}
