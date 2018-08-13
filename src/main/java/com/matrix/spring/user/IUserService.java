package com.matrix.spring.user;

import java.util.Map;

public interface IUserService {
	boolean login(String userId, String pw);
	void addUser(UserDTO user);
	Map<String, String> getCertifiedInfo(String userId);
	boolean isUserId(String userId);
	boolean isUserPhone(String phoneNum);
	String getUserPhoneById(String userId);
	String getUserIdByPhoneNum(String phoneNum);
	Map<String, String> getSlideInfo(String userId, String type);
}
