package com.log;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class DaoLogging implements Logging{

	 Logger log = Logger.getLogger(DaoLogging.class.getName());
	@Pointcut("execution(* com.dao.*.*(..)) && !execution(* com.dao.QrcheckDao.getQrcheck(String))")
	@Override
	public void declareJoinPointExpression() {}

	@Before("declareJoinPointExpression()")
	@Override
	public void beforeMethod(JoinPoint jp) {
		String mName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("Method <"+mName+"> begin with ---> "+args+"....");
	}

	@AfterReturning(value="declareJoinPointExpression()",returning="result")
	@Override
	public void returnMethod(JoinPoint jp, Object result) {
		String mName = jp.getSignature().getName();
		log.info("Method <"+mName+"> end with --->"+result+"....");
	}

	@After("declareJoinPointExpression()")
	@Override
	public void afterMethod(JoinPoint jp) {
		String mName = jp.getSignature().getName();
		log.info("Method <"+mName+"> end!");
	}
	@AfterThrowing(value="declareJoinPointExpression()",throwing="ex")
	@Override
	public void afterThrowing(JoinPoint jp, Exception ex) {
		String mName = jp.getSignature().getName();
		log.warn("Method <"+mName+"> end with exception: "+ex.getLocalizedMessage()+"....");		
	}
	//@Around("declareJoinPointExpression()")
	@Override
	public Object aroundMethod(ProceedingJoinPoint pjp) {
		// TODO Auto-generated method stub
		return null;
	}

}
