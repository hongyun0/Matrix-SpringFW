package com.matrix.spring.staff;

public class StaffVO {
	/**member data*/
	private String staffId;
	private String staffName;
	private int branchSeq;
	private String workPart;
	private String bankName;
	private String accountNum;
	private String resumeFile;
	private String healthFile;
	private String bankFile;
	
	/**직원등록요청, 직원회원정보변경용*/
	public StaffVO(String staffId, int branchSeq, String bankName, String accountNum, String resumeFile, String healthFile, String bankFile) {
		setStaffId(staffId);
		setBranchSeq(branchSeq);
		setBankName(bankName);
		setAccountNum(accountNum);
		setResumeFile(resumeFile);
		setHealthFile(healthFile);
		setBankFile(bankFile);
	}
	
	/**직원목록 조회용*/
	public StaffVO(String staffId, int branchSeq, String workPart) {
		setStaffId(staffId);
		setBranchSeq(branchSeq);
		setWorkPart(workPart);
	}

	/**get(), set()*/
	public String getWorkPart() {
		return workPart;
	}

	public void setWorkPart(String workPart) {
		this.workPart = workPart;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public int getBranchSeq() {
		return branchSeq;
	}

	public void setBranchSeq(int branchSeq) {
		this.branchSeq = branchSeq;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getResumeFile() {
		return resumeFile;
	}

	public void setResumeFile(String resumeFile) {
		this.resumeFile = resumeFile;
	}

	public String getHealthFile() {
		return healthFile;
	}

	public void setHealthFile(String healthFile) {
		this.healthFile = healthFile;
	}

	public String getBankFile() {
		return bankFile;
	}

	public void setBankFile(String bankFile) {
		this.bankFile = bankFile;
	}

	/**toString()*/
	@Override
	public String toString() {
		return "StaffVO [workPart=" + workPart + ", staffName=" + staffName + ", staffId=" + staffId + ", branchSeq="
				+ branchSeq + ", bankName=" + bankName + ", accountNum=" + accountNum + ", resumeFile=" + resumeFile
				+ ", healthFile=" + healthFile + ", bankFile=" + bankFile + "]";
	}

	/**hashCode() & equals()*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StaffVO other = (StaffVO) obj;
		if (staffId == null) {
			if (other.staffId != null)
				return false;
		} else if (!staffId.equals(other.staffId))
			return false;
		return true;
	}
	
}
