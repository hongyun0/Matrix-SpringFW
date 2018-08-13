package com.matrix.spring.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	@Autowired
	UserDAO userDAO;
	
	@Override
	public boolean login(String userId, String pw) {
		return userDAO.login(userId, pw);
	}

	@Override
	public void addUser(UserDTO user) {
		userDAO.addUser(user);
	}

	@Override
	public Map<String, String> getCertifiedInfo(String userId) {
		return userDAO.getCertifiedInfo(userId);
	}

	@Override
	public boolean isUserId(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserPhone(String phoneNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUserPhoneById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserIdByPhoneNum(String phoneNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSlideInfo(String userId, String type) {
		Map<String, String> result = null;
		if(type.equals("admin")){
			result = userDAO.getAdminSlideInfo(userId);
		} else if(type.equals("staff")){
			result = userDAO.getStaffSlideInfo(userId);
		}
		return result;
	}
}
