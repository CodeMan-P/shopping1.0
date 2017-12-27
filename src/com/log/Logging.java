package com.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface Logging {
	Logger log = null;
	public void declareJoinPointExpression();
	
	public void beforeMethod(JoinPoint jp);
	public void returnMethod(JoinPoint jp,Object result);
	public void afterMethod(JoinPoint jp);
	public void afterThrowing(JoinPoint jp,Exception ex);
	public Object aroundMethod(ProceedingJoinPoint pjp);
}
