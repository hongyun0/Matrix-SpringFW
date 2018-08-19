package com.matrix.spring.user;

import com.matrix.spring.FormatCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private FormatCheck formatCheck;

	/** 로그인 + 현재 비밀번호 일치여부 검사 + 비밀번호 재확인 검사(같은 쿼리문) */
	public boolean login(String userId, String pw) {
		boolean result = false;
		Map<String, String> input = new HashMap<>();
		input.put("userId", userId);
		input.put("pw", pw);
		if (!formatCheck.isInputLength(userId, 6, 16) || !formatCheck.isInputLength(pw, 6, 16)) {
			throw new RuntimeException("login 실패 inputLength");
		}
		if (sqlSession.selectOne("userMapper.login", input) != null) {
			result = true;
		}
		return result;
	}

	/** 회원가입 */
	public void addUser(UserDTO userDTO) {
		if (!formatCheck.isFileFormat(userDTO.getProfilePhoto())) {
			throw new RuntimeException("addUser 실패 profilePhotoFormat 오류");
		}
		if (!formatCheck.isDomainFormat(userDTO.getEmailDomain())) {
			throw new RuntimeException("addUser 실패 emailDomainFormat 오류");
		}
		if (!formatCheck.isNumberFormat(userDTO.getPhoneNum()) || !formatCheck.isNumberFormat(userDTO.getBirthYear())
				|| !formatCheck.isNumberFormat(userDTO.getBirthMonth()) || !formatCheck.isNumberFormat(userDTO.getBirthDay())) {
			throw new RuntimeException("addUser 실패 휴대폰번호/생년월일 numberFormat 오류");
		}
		if (!formatCheck.isInputLength(userDTO.getUserId(), 6, 16) || !formatCheck.isInputLength(userDTO.getPw(), 6, 16)
				|| !formatCheck.isInputLength(userDTO.getPhoneNum(), 10, 11)
				|| !formatCheck.isInputLength(userDTO.getEmailAccount() + "@" + userDTO.getEmailDomain(), 0, 40)
				|| !formatCheck.isInputLength(userDTO.getName(), 2, 4) || !formatCheck.isInputLength(userDTO.getBirthYear(), 4, 4)
				|| !formatCheck.isInputLength(userDTO.getBirthMonth(), 2, 2) || !formatCheck.isInputLength(userDTO.getBirthDay(), 2, 2)) {
			throw new RuntimeException("addUser 실패 inputLength 오류");
		}
		if (isUserId(userDTO.getUserId())) {
			throw new RuntimeException("addUser 실패 existingUserId");
		}
		if (!userDTO.getGender().equals("M") && !userDTO.getGender().equals("F")) {
			throw new RuntimeException("addUser 실패 wrongGenderFormat");
		}

		sqlSession.insert("userMapper.addUser", userDTO);
	}

	/** 휴대폰 번호 중복 검사 */
	public boolean isUserPhoneNum(String phoneNum) {
		boolean result = false;
		if (!formatCheck.isNumberFormat(phoneNum)) {
			throw new RuntimeException("isUserPhoneNum 실패 휴대폰번호/생년월일 numberFormat 오류");
		}
		if (!formatCheck.isInputLength(phoneNum, 10, 11)) {
			throw new RuntimeException("isUserPhoneNum 실패 휴대폰번호 inputLength");
		}
		if (sqlSession.selectOne("userMapper.isUserPhoneNum", phoneNum) != null) {
			result = true;
		}
		return result;
	}

	/** 아이디 중복 검사 + 아이디 유무 검사 */
	public boolean isUserId(String userId) {
		boolean result = false;
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("isUserId 실패 userId inputLength 오류");
		}
		if (sqlSession.selectOne("userMapper.isUserId", userId) != null) {
			result = true;
		}
		return result;
	}

	/** 비밀번호 재설정 */
	public void resetPw(String newPw, String userId) {
		Map<String, String> input = new HashMap<>();
		input.put("newPw", newPw);
		input.put("userId", userId);
		if (!formatCheck.isInputLength(userId, 6, 16) || !formatCheck.isInputLength(newPw, 6, 16)) {
			throw new RuntimeException("resetPw 실패 inputLength 오류");
		}
		if (!isUserId(userId)) {
			throw new RuntimeException("resetPw 실패 null Id");
		}

		sqlSession.update("userMapper.setPw", input);
	}

	/** 휴대폰 번호에 해당하는 아이디 보기 */
	public String getUserIdByPhoneNum(String phoneNum) {
		String result = null;
		if (!formatCheck.isNumberFormat(phoneNum) || !formatCheck.isInputLength(phoneNum, 10, 11)) {
			throw new RuntimeException("getUserIdByPhoneNum 실패 phoneNum 형식/길이 오류");
		}
		result = sqlSession.selectOne("userMapper.getUserIdByPhoneNum", phoneNum);
		if (result == null) {
			throw new RuntimeException("getUserIdByPhoneNum 실패 null userId");
		}
		return result;
	}

	/** 아이디에 해당하는 휴대폰 번호 보기 */
	public String getUserPhoneNum(String userId) {
		String result = null;
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("getUserPhoneNum 실패 inputLength 오류");
		}
		if (!isUserId(userId)) {
			throw new RuntimeException("getUserPhoneNum 실패 null userId");
		}
		result = sqlSession.selectOne("userMapper.getUserPhoneNum", userId);
		if (result == null) {
			throw new RuntimeException("getUserPhoneNum 실패 null phoneNum");
		}
		return result;
	}

	/** 기본 회원정보 변경 */
	public void setUserInfo(UserDTO userDTO) {
		if (!formatCheck.isFileFormat(userDTO.getProfilePhoto())) {
			throw new RuntimeException("setUserInfo 실패 profilePhotoFormat");
		}
		if (!formatCheck.isDomainFormat(userDTO.getEmailDomain())) {
			throw new RuntimeException("setUserInfo 실패 emailDomainFormat");
		}
		if (!formatCheck.isNumberFormat(userDTO.getPhoneNum()) || !formatCheck.isNumberFormat(userDTO.getBirthYear())
				|| !formatCheck.isNumberFormat(userDTO.getBirthMonth()) || !formatCheck.isNumberFormat(userDTO.getBirthDay())) {
			throw new RuntimeException("setUserInfo 실패 휴대폰번호/생년월일 numberFormat");
		}
		if (!formatCheck.isInputLength(userDTO.getUserId(), 6, 16) || !formatCheck.isInputLength(userDTO.getPhoneNum(), 10, 11)
				|| !formatCheck.isInputLength(userDTO.getEmailAccount() + "@" + userDTO.getEmailDomain(), 0, 40)
				|| !formatCheck.isInputLength(userDTO.getBirthYear(), 4, 4) || !formatCheck.isInputLength(userDTO.getBirthMonth(), 2, 2)
				|| !formatCheck.isInputLength(userDTO.getBirthDay(), 2, 2)) {
			throw new RuntimeException("setUserInfo 실패 inputLength 오류");
		}
		if (!isUserId(userDTO.getUserId())) {
			throw new RuntimeException("setUserInfo 실패 null userId");
		}

		sqlSession.update("userMapper.setUserInfo", userDTO);
	}

	/** 현재 이름, 생년월일, 주소, 휴대폰번호, 프로필사진 보기 */
	public Map<String, String> getUserInfo(String userId) {
		Map<String, String> map = null;
		if (!isUserId(userId)) {
			throw new RuntimeException("getUserInfo 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("getUserInfo 실패 inputLength 오류");
		}
		map = sqlSession.selectOne("userMapper.getUserInfo", userId);
		return map;
	}

	/** 프로필 사진 첨부 */
	public void setProfilePhoto(String profilePhoto, String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("newProfilePhoto", profilePhoto);
		map.put("userId", userId);
		if (!isUserId(userId)) {
			throw new RuntimeException("setProfilePhoto 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16) || !formatCheck.isInputLength(profilePhoto, 0, 40)) {
			throw new RuntimeException("setProfilePhoto 실패 inputLength 오류");
		}
		if (!formatCheck.isFileFormat(profilePhoto)) {
			throw new RuntimeException("setProfilePhoto 실패 profilePhotoFormat 오류");
		}

		sqlSession.update("userMapper.setProfilePhoto", map);
	}// 차후에 슬라이드메뉴에서 프로필사진 변경 기능 추가할 경우 사용

	/** 비밀번호 변경 */
	@Transactional
	public void setPw(String newPw, String userId, String pw) {
		Map<String, String> input = new HashMap<>();
		input.put("newPw", newPw);
		input.put("userId", userId);
		input.put("pw", pw);
		if (!isUserId(userId)) {
			throw new RuntimeException("setPw 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16) || !formatCheck.isInputLength(newPw, 6, 16) 
				|| !formatCheck.isInputLength(pw, 6, 16)) {
			throw new RuntimeException("setPw 실패 inputLength 오류");
		}
		if (newPw == pw) {
			throw new RuntimeException("setPw 오류 newPw와 pw가 같음");
		}
		if (sqlSession.selectOne("userMapper.login", input) == null) {
			throw new RuntimeException("setPw 실패 기존 pw 불일치");
		}

		sqlSession.update("userMapper.setPw", input);
	}

	/** 프로필사진, 속한 지점, 회원인증유형, 이름 보기-슬라이드용 */
	// 주의) staffs, branches와 겹치는 것 있음
	public Map<String, String> getAdminSlideInfo(String userId) {
		Map<String, String> result = null;
		if (!isUserId(userId)) {
			throw new RuntimeException("getAdminSlideInfo 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("getAdminSlideInfo 실패 idInputLength 오류");
		}

		result = sqlSession.selectOne("userMapper.getAdminSlideInfo", userId);
		return result;
	}

	public Map<String, String> getStaffSlideInfo(String userId) {
		Map<String, String> result = null;
		if (!isUserId(userId)) {
			throw new RuntimeException("getStaffSlideInfo 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("getStaffSlideInfo 실패 idInputLength 오류");
		}

		result = sqlSession.selectOne("userMapper.getStaffSlideInfo", userId);
		return result;
	}

	/** 회원 탈퇴 */
	public void removeUser(String userId, String pw) {
		Map<String, String> input = new HashMap<>();
		input.put("userId", userId);
		input.put("pw", pw);
		if (!isUserId(userId)) {
			throw new RuntimeException("removeUser 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16) || !formatCheck.isInputLength(pw, 6, 16)) {
			throw new RuntimeException("removeUser 실패 inputLength 오류");
		}

		sqlSession.update("userMapper.removeUser", input);
	}

	/** 회원 전체 아이디 목록 보기-테스트보조용 */
	public List<String> getUsers() {
		List<String> list = sqlSession.selectList("userMapper.getUsers");
		return list;
	}

	/** 관리자 or 직원 or 미인증자 여부 확인 */
	@Transactional
	public Map<String, String> getCertifiedInfo(String userId) {
		Map<String, String> result = new HashMap<>();
		if (!isUserId(userId)) {
			throw new RuntimeException("getCertifiedInfo 실패 nullUserId");
		}
		if (!formatCheck.isInputLength(userId, 6, 16)) {
			throw new RuntimeException("getCertifiedInfo 실패 inputLength 오류");
		}
		result = sqlSession.selectOne("userMapper.isCertifiedAdmin", userId);
		if (result == null) {
			result = sqlSession.selectOne("userMapper.isCertifiedStaff", userId);
			if (result != null) {
				result.put("type", "staff");
			} else {
				return null; // null일 경우, 미인증 회원으로 인식.
			}
		} else {
			result.put("type", "admin");
		}
		return result;
	}

}
