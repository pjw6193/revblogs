package com.revature.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/*
 * Logger for debugging
 */

@Aspect
public class Logging {
	
	private static Logger log = Logger.getRootLogger(); 

	@Around("pointcutHook()")
	public Object intercept(ProceedingJoinPoint pjp){
		
		Object result = null;
		
		try {
			
			//BEFORE EXECUTION
			log.debug("[AOP LOGGER]: Enter Method (" + pjp.getSignature()+ ") Execution" );
			
			//Execute the intercepted method
			result = pjp.proceed(); 
			
			//AFTER EXECUTION
			log.debug("[AOP LOGGER]: Exit Method (" + pjp.getSignature()+ ") Execution" );
			
			if(result != null){
				
				log.info("[AOP LOGGER]: (" + pjp.getSignature() + ") returned: " + result);
			}
			
		} catch (Throwable e) {
			
			//Send to the logger an error message that the method failed
			log.error("[AOP LOGGER]:" + e);
		}
		
		return result;
	}
	
	public void log(String message){
		
		log.info("[AOP LOGGER]:" + message);
	}
	
	@Pointcut("within(com.revature.*)")
	private void pointcutHook() {}
}
