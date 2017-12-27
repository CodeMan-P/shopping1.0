package com.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class ServeletLogging implements Logging{
	Logger log = Logger.getLogger(ServeletLogging.class.getName());
	@Pointcut("execution(* com.servelet.*.*(..))")
	@Override
	public void declareJoinPointExpression() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeMethod(JoinPoint jp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnMethod(JoinPoint jp, Object result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterMethod(JoinPoint jp) {
		// TODO Auto-generated method stub
		
	}

	@AfterThrowing(value="declareJoinPointExpression()",throwing="ex")
	@Override
	public void afterThrowing(JoinPoint jp, Exception ex) {
		String mName = jp.getSignature().getName();
		log.warn("Method <"+mName+"> end with exception: "+ex.getLocalizedMessage()+"....");		
	}

	@Override
	public Object aroundMethod(ProceedingJoinPoint pjp) {
		// TODO Auto-generated method stub
		return null;
	}

}
