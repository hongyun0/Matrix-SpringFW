package com.matrix.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.matrix.spring.task.daily.DailyDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	/* 예외처리 하기
	@Pointcut(value = "bean(*Controller)")
	private void controllerPointcut() {}
	
	@AfterThrowing(pointcut = "controllerPointcut()", throwing = "exception")
	public void exceptionWarn(JoinPoint joinPoint, Exception exception) {
		log.warn("WARN! {} : '{}' [{}]", joinPoint.getSignature(), exception.getMessage(), exception.getStackTrace()[0]);
	}
	
	@Around(value = "controllerPointcut()")
	public void exceptionWarn(ProceedingJoinPoint joinPoint) {
		try {
			joinPoint.proceed();
		} catch (Throwable exception) {
			log.warn("WARN! {} : '{}' [{}]", joinPoint.getSignature(), exception.getMessage(), exception.getStackTrace()[0]);
		}
	}*/
	
	@Pointcut(value = "execution(* com.matrix.spring.user.UserController.login(*, *, *))")
	private void loginPointcut() {}
	
	@AfterReturning(pointcut = "loginPointcut() && args(userId, *, *)")
	public void loginInfo(JoinPoint joinPoint, String userId) {
		log.info("INFO [{}] login.", userId);
	}
	
	@Pointcut(value = "execution(* com.matrix.spring.task.daily.DailyController.assignTask(*, *, *))")
	private void assignPointcut() {}
	
	@AfterReturning(pointcut = "assignPointcut() && args(dailyDTO, *, userId)")
	public void assignInfo(JoinPoint joinPoint, DailyDTO dailyDTO, String userId) {
		log.info("INFO [{}] assign '{}' to [{}].", userId, dailyDTO.getDailyTask(), dailyDTO.getAssignDetail());
	}
	
	@Pointcut(value = "execution(* com.matrix.spring.task.daily.DailyController.removeTask(*, *, *))")
	private void removeTaskPointcut() {}
	
	@AfterReturning(pointcut = "removeTaskPointcut() && args(dailyDTO, *, userId)")
	public void removeTaskInfo(JoinPoint joinPoint, DailyDTO dailyDTO, String userId) {
		log.info("INFO [{}] remove '{}' from [{}].", userId, dailyDTO.getDailyTask(), dailyDTO.getAssignDetail());
	}
	
}
