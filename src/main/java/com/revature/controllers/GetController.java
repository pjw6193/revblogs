package com.revature.controllers;

import java.util.List;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;
import com.revature.service.impl.CryptImpl;

@Controller
@SessionAttributes("roleDropDown")
public class GetController {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private BusinessDelegate businessDelegate;
	private Logging logging;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}
	
	/*
	 *  Model Attributes
	 *  
	 */
	
	@ModelAttribute("roleDropDown")
	public List<UserRoles> instantiateRoleDrop(){
		return businessDelegate.requestRoles();
	}
	
	/*
	 *  Methods that pull from the database
	 *  
	 */
	
	@RequestMapping(value="/makeClientAccount", method=RequestMethod.GET)
	public String newClient(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("roleDropDown", businessDelegate.requestRoles());
		return "makeClientAccount";
	}
	
	@RequestMapping(value="/bindUser", method=RequestMethod.GET)
	@ResponseBody
	public String bindUser (@RequestParam(value="u") String email, HttpServletRequest request) {
		
		String value = null;
		
		User curUser = businessDelegate.requestUsers(email.toLowerCase());

		if(curUser != null){
			
			CryptImpl.user.setUserId(curUser.getUserId());
			CryptImpl.user.setEmail(curUser.getEmail());
			CryptImpl.user.setPassword(curUser.getPassword());
			CryptImpl.user.setFirstName(curUser.getFirstName());
			CryptImpl.user.setLastName(curUser.getLastName());
			CryptImpl.user.setProfilePicture(curUser.getProfilePicture());
			CryptImpl.user.setJobTitle(curUser.getJobTitle());
			CryptImpl.user.setLinkedInURL(curUser.getLinkedInURL());
			CryptImpl.user.setDescription(curUser.getDescription());
			CryptImpl.user.setActive(curUser.isActive());
			CryptImpl.user.setNewUser(curUser.isNewUser());
			
			value = "Success";
		}
		
		return value; 
	}
}
