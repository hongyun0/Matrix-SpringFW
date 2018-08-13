package com.matrix.spring;

import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.matrix.spring.task.daily.DailyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/resources/application-config.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DailyTest {
	@Autowired
	DailyDAO dailyDAO;

	@Test
	public void test() throws ParseException {
		String assignDate = "2018/08/14";
		String branchSeq = "1";
		dailyDAO.getAssignedParts(assignDate, branchSeq);
		
	}

}
