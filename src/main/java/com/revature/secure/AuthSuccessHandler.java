package com.revature.secure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author Eric
 *	AUTHENTICATE THE USER'S ROLE, POINT TO CORRECT PAGE FOR LOGIN
 */

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		String targetUrl;
		
		if("[ADMIN]".equalsIgnoreCase(role)){
			targetUrl = "/admin";
		}
		else if("[CONTRIBUTOR]".equalsIgnoreCase(role)){
			targetUrl= "/contributor";
		}
		else{
			targetUrl="/";
		}
		
		return targetUrl;
		
	}
}
