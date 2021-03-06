package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import org.springframework.web.servlet.ModelAndView;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.Logging;
import com.revature.service.Population;
import com.revature.service.impl.Mailer;

@Controller
public class PostController {

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private BusinessDelegate businessDelegate;
	private Population population;
	private static final String SUCCESS = "passwordSuccess";
	private static final String UPDATE = "userUpdate";
	private static final String LIST = "userList";
	private static final String PROFILE = "updateUserProfile";
	private static final String REDIRECTPROFILE = "redirect:/profile";
	private static final String MANAGE = "redirect:/manageusers";
	private static final String EDIT = "editingBlogInDatabase";
	private static final String REDIRECTP = "redirect:/password";
	private static final String TAGS = "newTags";

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	
	/*
	 *  Methods that effect the database
	 *  
	 */
	
	// Update a User
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
							 HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName(REDIRECTPROFILE);
		if(bindingResult.hasErrors()){
			
			return model;
		}
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		
		String userUpdate = "update";
		req.getSession().setAttribute("user", loggedIn);
		req.getSession().setAttribute(SUCCESS, null);
		businessDelegate.updateRecord(loggedIn);
		req.getSession().setAttribute(UPDATE, userUpdate);
		req.setAttribute("updateUser", new User());
		return model;
	}
	
	// Admin update a User
	//DO NOT CHANGE USER TO USERDTO! IT BREAKS EEEEVVVVEEEERRRYYYTIME!!!!!!!!1!!
	@RequestMapping(value="updateUserProfile.do", method=RequestMethod.POST)
	public ModelAndView updateUserProfile(@ModelAttribute(PROFILE) @Valid User updateUserProfile, 
							 BindingResult bindingResult, HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName(MANAGE);
		System.err.println("Manage GRABBED!");
		if(bindingResult.hasErrors()){
			System.err.println(bindingResult.toString());
			System.err.println("I dun fucked up, Captain.");
			req.setAttribute(LIST, businessDelegate.requestUsers());
			req.setAttribute(PROFILE, new User());
			return model;
		}
		
		User updateUser = businessDelegate.requestUser(updateUserProfile.getUserId());
		System.err.println("Calling BD");
		System.err.println(model.toString());
		// Set attributes
		updateUser.setEmail(updateUserProfile.getEmail());
		updateUser.setFirstName(updateUserProfile.getFirstName());
		updateUser.setLastName(updateUserProfile.getLastName());
		updateUser.setJobTitle(updateUserProfile.getJobTitle());
		updateUser.setLinkedInURL(updateUserProfile.getLinkedInURL());
		updateUser.setDescription(updateUserProfile.getDescription());		
		
		businessDelegate.updateRecord(updateUser);
		req.setAttribute(LIST, businessDelegate.requestUsers());
		req.setAttribute(PROFILE, new User());
		return model;
	}
	
	//Create a new User
	@RequestMapping(value="createAccount.do", method=RequestMethod.POST)
	public ModelAndView createAccount(HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
	
		// User Supplied
		String email = req.getParameter("email").toLowerCase();
		UserRoles userRole = businessDelegate.requestRoles(2);
		
		// Check if email exists
		if(businessDelegate.requestUsers(email) == null){
			
			// Generate a Temporary Password
			String password = businessDelegate.getRandom(6);
			String firstName = " ";
			String lastName = " ";
			String jobTitle = " ";
			String linkedInURL = null;
			String description = null;
			
			User newUser = new User(email, 
									businessDelegate.maskElement(password), 
									firstName, 
									lastName, 
									jobTitle,
									linkedInURL, 
									description, 
									userRole);
			
			// Save in Database
			businessDelegate.putRecord(newUser);
			
			newUser = businessDelegate.requestUsers(email);
				
			String user = "" + newUser.getUserId();
			
			String profilePicture = businessDelegate.uploadProfileItem(user, user);
			
			newUser.setProfilePicture(profilePicture);
			
			businessDelegate.updateRecord(newUser);
				
			// Send Email to Account
			Mailer.sendMail(email, password);
		}
		
		model.setViewName(MANAGE);
		
		return model;
	}
	
	// Admin Reset Profile Picture
	@RequestMapping(value="resetProfile.do", method=RequestMethod.GET)
	public ModelAndView resetProfilePicture(@RequestParam(value="resetProfile") int userId, HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		model.setViewName(MANAGE);
		
		User resetUserPic = businessDelegate.requestUser(userId);

		String user = "" + resetUserPic.getUserId();
		
		String profilePicture = businessDelegate.uploadProfileItem(user, user);
		
		resetUserPic.setProfilePicture(profilePicture);
		
		businessDelegate.updateRecord(resetUserPic);

		req.setAttribute(LIST, businessDelegate.requestUsers());
		req.setAttribute(PROFILE, new User());
		return model;		
	}
	
	// Admin Deactivate User
	@RequestMapping(value="deactivateUser.do", method=RequestMethod.GET)
	public String deactivateUser(@RequestParam(value="deactivate") int userId, HttpServletRequest req){
				
		User deactivateUser = businessDelegate.requestUser(userId);
		deactivateUser.setActive(false);
		businessDelegate.updateRecord(deactivateUser);	
		
		req.setAttribute(LIST, businessDelegate.requestUsers());
		req.setAttribute(PROFILE, new User());
		return MANAGE;
	}
	
	// Admin Activate User
	@RequestMapping(value="activateUser.do", method=RequestMethod.GET)
	public String activateUser(@RequestParam(value="activate") int userId, HttpServletRequest req){
				
		User activateUser = businessDelegate.requestUser(userId);
		activateUser.setActive(true);
		businessDelegate.updateRecord(activateUser);	
		
		req.setAttribute(LIST, businessDelegate.requestUsers());
		req.setAttribute(PROFILE, new User());
		return MANAGE;
	}
	
	
	// Admin Can Reset Password
	@RequestMapping(value="resetUserPassword.do", method=RequestMethod.GET)
	public ModelAndView resetUserPassword(@RequestParam(value="resetPass") int userId, HttpServletRequest req){
		ModelAndView model = new ModelAndView();

		model.setViewName(MANAGE);
		
		User resetUserPassword = businessDelegate.requestUser(userId);
		String email = resetUserPassword.getEmail();
		
		// Generate a Temporary Password

		String password = businessDelegate.getRandom(6);

		resetUserPassword.setPassword(businessDelegate.maskElement(password));
		resetUserPassword.setNewUser(true);
		
		// Save in Database
		businessDelegate.updateRecord(resetUserPassword);
		
		// Send Email to Account
		Mailer.sendMail(email, password, resetUserPassword.getFullname());
		
		req.setAttribute(LIST, businessDelegate.requestUsers());
		req.setAttribute(PROFILE, new User());
		return model;
	}
		
	// Update Password Page
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("updatePassword") @Valid UserDTO passwordDTO, BindingResult bindingResult,
							   HttpServletRequest req, HttpServletResponse resp){
		
		req.getSession().setAttribute("passwordFailure1", null);
		req.getSession().setAttribute("passwordFailure2", null);
		req.getSession().setAttribute("passwordFailure3", null);
		
		ModelAndView model = new ModelAndView();
		model.setViewName(REDIRECTPROFILE);
		
		String success = "success";
		String failure = "failure";
		
		if(bindingResult.hasErrors()){
			
			req.getSession().setAttribute("passwordFailure3", failure);
			
			model.setViewName(REDIRECTP);
			return model;
		}
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		String prevPass = passwordDTO.getOldPassword();
		String password = passwordDTO.getNewPassword();
		
		// if old password matches current password
		if(businessDelegate.validate(prevPass, loggedIn.getPassword())){
				
			// if old password does not match new password
			if(!(prevPass.equals(password))){
			
				loggedIn.setPassword(businessDelegate.maskElement(password));
				
				if(loggedIn.isNewUser()){
					loggedIn.setNewUser(false);
				}
				
				req.getSession().setAttribute("user", loggedIn);
				req.getSession().setAttribute("passwordSuccess", success);
				req.getSession().setAttribute("userUpdate", null);
				businessDelegate.updateRecord(loggedIn);
			}
			
			else {
				
				req.getSession().setAttribute("passwordFailure2", failure);
				model.setViewName(REDIRECTP);
			}
			
		} else {
			
			req.getSession().setAttribute("passwordFailure1", failure);
			model.setViewName(REDIRECTP);
		}
		
		return model;
	}
	
	// Updates a Users Profile Picture
	@RequestMapping(value="uploadProfilePicture", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			produces=MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, 
			HttpServletRequest req, HttpServletResponse resp)
	{
		User loggedIn = (User) req.getSession().getAttribute("user");
		String url = businessDelegate.uploadProfileItem(""+loggedIn.getUserId(),""+loggedIn.getUserId(), profilePicture);		
		loggedIn.setProfilePicture(url);
		businessDelegate.updateRecord(loggedIn);
		req.getSession().setAttribute(SUCCESS, null);
		req.getSession().setAttribute(UPDATE, null);
		return "Success";
	}
	
	//Upload Item remotely
	@RequestMapping(value="/upload-remote", method=RequestMethod.POST)
	public ModelAndView uploadRemote(@RequestParam("path") String path, 
			@RequestParam("file") MultipartFile file,
			HttpServletRequest req, 
			HttpServletResponse resp){
		businessDelegate.uploadRemote(path, file);
		//Resets lists
		this.setLists(req);
		//Return to management page 
		ModelAndView model = new ModelAndView();
		model.setViewName("/management");
		return model;
	}
	
	// Uploads a Blog Picture
	@RequestMapping(value="/upload-picture", method=RequestMethod.POST)
	public void uploadPictureHandler(@RequestParam("file") MultipartFile file,
			HttpServletRequest req,
			HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><h3>Copy</h3><body><textarea id=\"picLink\" autofocus " +
					"rows=\"5\" cols=\"35\" readonly>" + url +
					"</textarea></body><script>window.onload=function(){" +
					"document.getElementById(\"picLink\").select();};</script></html>");
		} catch (IOException e) {
			Logging.error(e);
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
			Logging.error(e);
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
			Logging.error(e);
		}
	}
	
	public Map<Integer, String> getReferences(HttpServletRequest req) {
		
		int highestReferenceNum = -1;
		TreeMap<Integer, String> references = new TreeMap<>();
		for ( Enumeration<String> params = req.getParameterNames();
				params.hasMoreElements(); )
		{
			String paramName = params.nextElement();
			String paramValue = req.getParameter(paramName);
			if ( paramName.startsWith("reference") && paramValue.length() > 0 ) {
				int numberStart = paramName.lastIndexOf('e')+1;
				String referenceNumStr = paramName.substring(numberStart);
				try {
					int referenceNum = Integer.parseInt(referenceNumStr);
					references.put(referenceNum, paramValue);
					highestReferenceNum = Math.max(highestReferenceNum, referenceNum);
				} catch ( NumberFormatException e ) {
					Logging.error(e);
				}
			}
		}
		
		// Remove trailing empty references
		for ( int i=highestReferenceNum; i>=0; i-- ) {
			String ref = references.get(i);
			if ( ref != null && ref.length() <= 0 ) {
				references.remove(i);
			} else {
				break;
			}
		}
		
		return references;
	}
	
	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
			
		/*
		 * Check to see if the current blog's title already exists. 
		 * If exists, redirect to current page, if new, go to preview blog page.
		 */
		Boolean editingBlogInDatabase = (Boolean)req.getSession().getAttribute(EDIT);
		User author;
		
		if(editingBlogInDatabase == null) {
			editingBlogInDatabase = false;
		}
		if(!editingBlogInDatabase) {
			List<Blog> myBlogs = businessDelegate.requestBlogs();
			for(Blog curBlog : myBlogs){
				if(curBlog.getBlogTitle().equalsIgnoreCase(blog.getBlogTitle())){
					return "create-blog";
				}
			}
			author = (User) req.getSession().getAttribute("user");
		} else {
			author = (User) req.getSession().getAttribute("blogToEditAuthor");
		}
		blog.setAuthor(author);
		//blog.setReferences(getReferences(req))
		
		/*
		 * Blog Bean will be generated with proper tags and fields
		 */
		if(blog.getBlogTagsString().isEmpty()){
			blog.setTags(new HashSet<Tags>());
		}
		else{
			String tmp = blog.getBlogTagsString();
			List<String> myList = Arrays.asList(tmp.split(","));
			Set<Tags> tmpTags = new HashSet<>();
			Set<Tags> newTags = new HashSet<>();
			List<Tags> dbTags = businessDelegate.requestTags();
			/*
			 * loop through List of tag descriptions the user types in
			 */
			for(String a : myList){
				boolean check = false;
				String tagDesc = a.toLowerCase().trim();
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
					newTags.add(myTag);
					tmpTags.add(myTag);
					
				}
			}
			blog.setTags(tmpTags);
			req.getSession().setAttribute(TAGS, newTags);
		}
		req.getSession().setAttribute("blog", blog);
		return "preview-blog";
	}
	
	@RequestMapping(value="publish.do", method=RequestMethod.POST)
	public String publishBlog(HttpServletRequest req, HttpServletResponse resp) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		HtmlWriter htmlWriter;
		String url = "";
		Set<Tags> newTags = (Set<Tags>)req.getSession().getAttribute(TAGS);
		if(newTags != null) {
			for(Tags newTag : newTags){
				businessDelegate.putRecord(newTag);
			}
			req.getSession().setAttribute(TAGS, null);
		}
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile blogTempFile = htmlWriter.render();
			String fileName = blogTempFile.getTemporaryFile().getName();
			url = "http://blogs.pjw6193.tech/content/pages/" + fileName;
			blog.setLocationURL(url);
			Boolean editingBlogInDatabase = (Boolean)req.getSession().getAttribute(EDIT);
			if(editingBlogInDatabase != null && editingBlogInDatabase) {
				int id = (int) req.getSession().getAttribute("blogToEditId");
				blog.setBlogId(id);
				businessDelegate.updateRecord(blog);
			} else {
				businessDelegate.putRecord(blog);
			}
			businessDelegate.uploadPage(blogTempFile.getTemporaryFile());
			blogTempFile.destroy();
			req.getSession().setAttribute("blog", null);
			req.getSession().setAttribute(EDIT, false);
			req.getSession().setAttribute("blogToEditId", 0);
		} catch (FileNotFoundException e) { 
			Logging.error(e);
		} catch (IOException e1) {
			Logging.error(e1);
		}
		return "redirect: " + url;
	}
	
	@RequestMapping(value="/deleteFile", method=RequestMethod.GET)
	public ModelAndView delete(Blog blog, HttpServletRequest req, HttpServletResponse resp){
		//Delete specified file
		//Note: blog is not necessarily a blog page,
		//	but used as a generic object with a string field for deletion
		businessDelegate.delete(blog.getBlogTitle());
		//Used to refresh the lists
		this.setLists(req);
		//Return to management page 
		ModelAndView model = new ModelAndView();
		model.setViewName("/management");
		return model;
	}
	
	public void setLists(HttpServletRequest req){
		String[] str = businessDelegate.getList();
		List<String> pages = new ArrayList<>();
		List<String> test = new ArrayList<>();
		List<String> resources = new ArrayList<>();
		List<String> evidence = new ArrayList<>();
		List<String> profiles = new ArrayList<>();
		//Distribute items to corresponding lists
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
		//Set the lists as session objects
		req.setAttribute("blog", new Blog());
		req.setAttribute("list", pages);
		req.setAttribute("prlist", profiles);
		req.setAttribute("elist", evidence);
		req.setAttribute("tlist", test);
		req.setAttribute("rlist", resources);
	}
	
	@RequestMapping(value="delete.do", method=RequestMethod.POST)
	public String deleteUserBlog(HttpServletRequest req, HttpServletResponse resp) {
		String blogLink = req.getParameter("blog-link");
		String cutBlogLink = blogLink.replace("http://blogs.pjw6193.tech/", "");
		businessDelegate.delete(cutBlogLink);
		return "user-blogs";
	}
}
