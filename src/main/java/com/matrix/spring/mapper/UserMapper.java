package com.matrix.spring.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.matrix.spring.user.UserDTO;

@Component
public interface UserMapper {
	void addUser(UserDTO userDTO);
	String login(@Param("userId") String userId, @Param("pw") String pw);
	String isUserPhoneNum(String phoneNum);
	String isUserId(String userId);
	String getUserPhoneNum(String userId);
	String getUserIdByPhoneNum(String phoneNum);
	Map<String, String> getUserInfo(String userId);
	Map<String, String> getAdminSlideInfo(String userId);
	Map<String, String> getStaffSlideInfo(String userId);
	List<String> getUsers();
	Map<String, String> isCertifiedAdmin(String userId);
	Map<String, String> isCertifiedStaff(String userId);
	void setPw(@Param("userId") String userId, @Param("newPw") String newPw);
	void setUserInfo(UserDTO userDTO);
	void setprofilePhoto(@Param("newProfilePhoto") String newProfilePhoto, @Param("userId") String userId);
	void removeUser(@Param("userId") String userId, @Param("pw") String pw);
	
}
