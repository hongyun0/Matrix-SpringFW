package com.matrix.spring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.matrix.spring.user.UserController;
import com.matrix.spring.user.UserDAO;
import com.matrix.spring.user.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserTest {
	@Autowired
	UserDAO userDAO;
	@Autowired
	UserController userController;
	
	@Test
	public void loginWithCorrectInfo() {
		String userId = "yunyoung";
		String pw = "yunyoung1234";
		assertTrue(userDAO.login(userId, pw));
	}
	
	//@Test
	public void addUserWithNoProfilePhoto() {
		String userId = "testtest";
		String pw = "test1234";
		String phoneNum = "01020203030";
		String name = "테스트";
		String birthYear = "1990";
		String birthMonth = "04";
		String birthDay = "20";
		String gender = "M";
		String emailAccount = "test";
		String emailDomain = "naver.com";
		String addressCity = "서울시";
		String addressGu = "서울구";
		String addressDong = "서울동";
		
		UserDTO vo = new UserDTO(userId, pw, phoneNum, name, birthYear, birthMonth, birthDay,
				gender, emailAccount, emailDomain, addressCity, addressGu, addressDong);
		List<String> list = userDAO.getUsers();
		System.out.println(list.size());
		userDAO.addUser(vo);
		System.out.println(userDAO.getUsers().size());
		assertFalse(list.size() == userDAO.getUsers().size());
	}
}
