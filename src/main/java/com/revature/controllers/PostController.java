package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.JetS3;
import com.revature.service.Logging;
import com.revature.service.impl.Crypt;
import com.revature.service.impl.JetS3Impl;

@Controller
public class PostController {

	/**
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
	
	
	/**
	 *  Model Attributes
	 *  
	 */
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String instantializeNewUser(HttpServletRequest req){
		if(req.getSession().getAttribute("updateUser") == null){
			req.setAttribute("updateUser", new User());
			req.getSession().getAttribute("user");
		}
		return "profile";
	}
	
	/**
	 *  Methods that effect the database
	 *  
	 */
	
	
	// Update a User
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
			HttpServletRequest req, HttpServletResponse resp){

		if(bindingResult.hasErrors()){
			return "profile";
		}
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		
		businessDelegate.updateRecord(loggedIn);
		
		return "profile";
	}
	
	// Update a Users Password
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public String updatePassword(@RequestParam("newPassword") String password,
			HttpServletRequest req, HttpServletResponse resp){
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		User user = businessDelegate.requestUsers(loggedIn.getEmail());
		user.setPassword(Crypt.encrypt(password, user.getEmail(), user.getFullname()));
		
		if(user.isNewUser() == true){
			user.setNewUser(false);
		}
		businessDelegate.updateRecord(user);
		req.getSession().setAttribute("user", user);
		return "profile";
		
	}
	
	// Updates a Users Profile Picture
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(profilePicture.getOriginalFilename(), profilePicture);
		User loggedIn = (User) req.getSession().getAttribute("user");
		loggedIn.setProfilePicture(url);
		businessDelegate.updateRecord(loggedIn);
		return loggedIn.getProfilePicture();
	}
	
	// Uploads a Blog Picture
	@RequestMapping(value="/upload-picture", method=RequestMethod.POST)
	public void uploadPictureHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><h3>Copy Link</h3><br><body></html>" + url);
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
	@RequestMapping(value="/upload-resource", method=RequestMethod.POST)
	public void uploadResourceHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
	@RequestMapping(value="/upload-page", method=RequestMethod.POST)
	public void uploadPageHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		TemporaryFile tempFile = TemporaryFile.make(file);
		File convertedFile = tempFile.getTemporaryFile();
		String url = businessDelegate.uploadPage(convertedFile);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><a href=\"" + url + "\">" + url + "</a></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		/*
		 * Blog Bean will be generated with proper tags and fields
		 */
		
		if(blog.getBlogTagsString().isEmpty()){
			blog.setTags(new HashSet<Tags>());
		}
		else{
			String tmp = blog.getBlogTagsString();
			List<String> myList = Arrays.asList(tmp.split(","));
			Set<Tags> tmpTags = new HashSet<Tags>();
			List<Tags> dbTags = businessDelegate.requestTags();
			/*
			 * loop through List of tag descriptions the user types in
			 */
			for(String a : myList){
				boolean check = false;
				String tagDesc = a.toLowerCase().replaceAll("\\s+","");
				/*
				 * loop through database Tags to check with user input tags
				 * if theres a match, put instance of database Tag into User bean, if not, create new Tag bean
				 */
				for(Tags b : dbTags){
					if(b.getDescription().equals(tagDesc)){
						tmpTags.add(b);
						check = true;
					}
				}
				if(!check){
					Tags myTag = new Tags(tagDesc);
					businessDelegate.putRecord(myTag);
					tmpTags.add(myTag);
					
				}
			}
			blog.setTags(tmpTags);
		}
		User author = businessDelegate.requestUsers("dpickles");
//		User author = (User) req.getSession().getAttribute("user");
		author.getFirstName();
		blog.setAuthor(author);
		blog.setPublishDate(new Date());
		blog.setBlogContent("empty");
		req.getSession().setAttribute("blog", blog);
		return "preview-blog";
	}
	
	@RequestMapping(value="publish.do", method=RequestMethod.POST)
	public String publishBlog(HttpServletRequest req, HttpServletResponse resp) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		HtmlWriter htmlWriter;
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile blogTempFile = htmlWriter.render();
			System.out.println(blogTempFile.getTemporaryFile().getName());
			String fileName = blogTempFile.getTemporaryFile().getName();
			req.setAttribute("fileName", fileName);
			JetS3 jetS3 = new JetS3Impl();
			businessDelegate.putRecord(blog);
			jetS3.uploadPage(blogTempFile.getTemporaryFile());
			blogTempFile.destroy();
		} catch (FileNotFoundException e) { 
			logging.info(e);
		} catch (IOException e1) {
			logging.info(e1);
		}
		return "success";
	}
}
