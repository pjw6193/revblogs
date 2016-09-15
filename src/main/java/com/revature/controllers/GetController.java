package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.revature.beans.Blog;
import com.revature.beans.User;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;
import com.revature.service.impl.LoginEncoder;

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
			
			if(curUser.isActive()){
				
				LoginEncoder.user.setUserId(curUser.getUserId());
				LoginEncoder.user.setEmail(curUser.getEmail());
				LoginEncoder.user.setPassword(curUser.getPassword());
				LoginEncoder.user.setFirstName(curUser.getFirstName());
				LoginEncoder.user.setLastName(curUser.getLastName());
				LoginEncoder.user.setProfilePicture(curUser.getProfilePicture());
				LoginEncoder.user.setJobTitle(curUser.getJobTitle());
				LoginEncoder.user.setLinkedInURL(curUser.getLinkedInURL());
				LoginEncoder.user.setDescription(curUser.getDescription());
				LoginEncoder.user.setActive(curUser.isActive());
				LoginEncoder.user.setNewUser(curUser.isNewUser());
				
				value = "Success";
			
			} else {
				
				value = "Inactive";
			}
		}
		
		return value; 
	}
	
	@RequestMapping(value="/manage-S3", method=RequestMethod.GET)
	public String manageS3(HttpServletRequest req, HttpServletResponse resp){
		String[] str = businessDelegate.getList();
		List<String> pages = new ArrayList<>();
		List<String> test = new ArrayList<>();
		List<String> resources = new ArrayList<>();
		List<String> evidence = new ArrayList<>();
		List<String> profiles = new ArrayList<>();
		for(String st:str){
			if(!st.contains("content/")||st.contains("/resources/"))
				resources.add(st);
			else if(st.contains("/evidence/"))
				evidence.add(st);
			else if(st.contains("/pages/"))
				pages.add(st);
			else if(!st.contains("test/")&&st.contains("/profiles/"))
				profiles.add(st);
			else
				test.add(st);
		}
		req.setAttribute("blog", new Blog());
		req.setAttribute("list", pages);
		req.setAttribute("prlist", profiles);
		req.setAttribute("elist", evidence);
		req.setAttribute("tlist", test);
		req.setAttribute("rlist", resources);
		return "management";
	}
	
	@RequestMapping(value="/manageusers", method=RequestMethod.GET)
	public String manageUsers(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		req.setAttribute("roleList", businessDelegate.requestRoles());
		return "manageusers";
	}
}
