package com.revature.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.revature.beans.Blog;
import com.revature.beans.User;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;
import com.revature.service.Population;

@Controller
public class BaseController {
	
	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private Logging logging;
	private BusinessDelegate businessDelegate;
	private Population population;
	private static Logger log = Logger.getRootLogger();
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Population getPopulation() {
		return population;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}

	/*
	 *  Default Controller
	 *  
	 */
	
	//------------------------------------------------
	// Redirections (&& Population)
	
	// Default Page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		
		return "index";
	}
	
	// Login Page
	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
	public String login(){
		
		return "loginPage";
	}
	
	@RequestMapping(value="/password", method=RequestMethod.GET)
	public String password(HttpServletRequest req){
		req.setAttribute("updatePassword", new UserDTO());
		return "password";
	}
	
	// Profile Page
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(HttpServletRequest req){
		
		User myUser = (User) req.getSession().getAttribute("user");
		
		
		if(myUser.isNewUser()){
			
	
			return "password";
			
		} else {
			
			req.setAttribute("updateUser", new User());
			
			return "profile";
		}
	}
	
	// Create Blog Page
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req){
		
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}
	
	// Upload Example Page
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public String uploadExamplePage() {
		
		return "upload-example";
	}
	
	// Add Picture Page
	@RequestMapping(value="/add-picture", method=RequestMethod.GET)
	public String addPicture(){
		return "add-picture";
	}
	
	// Navbar Page
	@RequestMapping(value="/navbar-partial", method=RequestMethod.GET)
	public String getNavbar() {
		return "navbar";
	}
	
	// Database Population (Empty = Database Populated) - No Redirection
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	public void populate(){
			
	}
	
	//------------------------------------------------
	// SPRING SECURITY
	
	// Admin Page
	@RequestMapping(value="/admin**")
	public ModelAndView viewAdmin(HttpServletRequest request, HttpServletRequest response, Principal principal){
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
	}
	
	// Contributor Page
	@RequestMapping(value="/contributor**")
	public ModelAndView viewContributor(HttpServletRequest request, HttpServletRequest response, Principal principal){
		
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
	}
	
	@RequestMapping(value="/go-admin")
	public ModelAndView getViewAdmin(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
		
	}
	
	@RequestMapping(value="/go-contributor")
	public ModelAndView getViewContributor(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/home");
		model.addObject("title", "Logged in as " + user.getJobTitle());
		model.addObject("message", "Welcome " + user.getFirstName());
		
		return model;
		
	}
	
	@RequestMapping(value="edit.do")
	public String getEditBlog(HttpServletRequest req) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		req.setAttribute("blog", blog);
		return "edit-blog";
	}
}