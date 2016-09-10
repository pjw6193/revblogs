package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
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
	
	/*
	 *  Methods that effect the database
	 *  
	 */
	
	// Populate Database (GET used for simplicity. No params are passed)
	@RequestMapping(value="populate.do", method=RequestMethod.GET)
	public String buildDatabase(){
	
		return null;
	}
	
	// Update a User
	@RequestMapping(value="updateUser.do", method=RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute("updateUser") @Valid User updateUser, BindingResult bindingResult,
							 HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/profile");
		if(bindingResult.hasErrors()){
			return model;
		}
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		//password needed to be decrypted first
		loggedIn.setPassword(businessDelegate.revealElement(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
		//end decryption
		
		loggedIn.setEmail(updateUser.getEmail());
		loggedIn.setFirstName(updateUser.getFirstName());
		loggedIn.setLastName(updateUser.getLastName());
		loggedIn.setJobTitle(updateUser.getJobTitle());
		loggedIn.setLinkedInURL(updateUser.getLinkedInURL());
		loggedIn.setDescription(updateUser.getDescription());
		
		//re-encrypt password
		loggedIn.setPassword(businessDelegate.maskElement(loggedIn.getPassword(), loggedIn.getEmail(), loggedIn.getFullname()));
		//end re-encryption
		
		String userUpdate = "update";
		req.getSession().setAttribute("user", loggedIn);
		req.getSession().setAttribute("passwordSuccess", null);
		businessDelegate.updateRecord(loggedIn);
		req.getSession().setAttribute("userUpdate", userUpdate);
		req.setAttribute("updateUser", new User());
		return model;
	}
	
	// Admin update a User
	@RequestMapping(value="updateUserProfile.do", method=RequestMethod.POST)
	public ModelAndView updateUserProfile(@ModelAttribute("updateUserProfile") @Valid UserDTO updateUserProfile, 
							 BindingResult bindingResult, HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/manageusers");
		if(bindingResult.hasErrors()){
			return model;
		}
		
		User updateUser = businessDelegate.requestUser(updateUserProfile.getUserId());


				
		//password needed to be decrypted first
		updateUser.setPassword(businessDelegate.revealElement(updateUser.getPassword(), updateUser.getEmail(),
				updateUser.getFullname()));
		//end decryption
		
		// Set attributes
		updateUser.setEmail(updateUserProfile.getEmail());
		updateUser.setFirstName(updateUserProfile.getFirstName());
		updateUser.setLastName(updateUserProfile.getLastName());
		updateUser.setJobTitle(updateUserProfile.getJobTitle());
		updateUser.setLinkedInURL(updateUserProfile.getLinkedInURL());
		updateUser.setDescription(updateUserProfile.getDescription());		
		
		//re-encrypt password
		updateUser.setPassword(businessDelegate.maskElement(updateUser.getPassword(), updateUser.getEmail(), 
				updateUser.getFullname()));
		//end re-encryption
		
		businessDelegate.updateRecord(updateUser);
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		return model;
	}
	
	//Create a new User
	@RequestMapping(value="createAccount.do", method=RequestMethod.POST)
	public ModelAndView createAccount(HttpServletRequest req, HttpServletResponse resp){
		ModelAndView model = new ModelAndView();
		
		// User Supplied
		String email = req.getParameter("email");
		String role = businessDelegate.requestRoles(2).getRole();
		
		// Check if email exists
		if(businessDelegate.requestUsers(email) == null){
			
			// Generate a Temporary Password
			String password = businessDelegate.getRandom(6);
			String firstName = " ";
			String lastName = " ";
			
			String jobTitle = " ";
			String linkedInURL = null;
			String description = " ";
			
			// Role Obj from Database
			UserRoles userRole = businessDelegate.requestRoles(role);
			User newUser = new User(email, 
									businessDelegate.maskElement(password, email, lastName+", "+firstName), 
									firstName, 
									lastName, 
									jobTitle,
									linkedInURL, 
									description, 
									userRole);

			// Save in Database
			businessDelegate.putRecord(newUser);
			
			// Send Email to Account
			Mailer.sendMail(email, password, firstName, lastName);
			
			//Get default picture
			URL fileURL = PostController.class.getClassLoader().getResource("default.png");
			File file;
			try {
				file = new File(fileURL.toURI());
				
				Logging.info("File length: " + file.length());
				
				User getNewUser = businessDelegate.requestUsers(email);
				
				String user = "" + getNewUser.getUserId();
				
				String profilePicture = businessDelegate.uploadProfileItem(user, user, file);
				
				getNewUser.setProfilePicture(profilePicture);
				
				businessDelegate.updateRecord(getNewUser);
				
			} catch (URISyntaxException e) {
				
				Logging.error(e);
			}
			
			model.setViewName("redirect:/manageusers");
			
			return model;
			
		}
		
		model.setViewName("redirect:/manageusers");
		
		return model;
	}
	
	// Admin Reset Profile Picture
	@RequestMapping(value="resetProfile.do", method=RequestMethod.POST)
	public ModelAndView resetProfilePicture(@RequestParam(value="resetProfile") int userId, HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/manageusers");
		
		User resetUserPic = businessDelegate.requestUser(userId);
		
		//Get default picture
		URL fileURL = PostController.class.getClassLoader().getResource("default.png");
		File file;
		try {
			file = new File(fileURL.toURI());
			
			Logging.info("File length: " + file.length());
			
			String user = "" + resetUserPic.getUserId();
			
			String profilePicture = businessDelegate.uploadProfileItem(user, user, file);
			
			resetUserPic.setProfilePicture(profilePicture);
			
			businessDelegate.updateRecord(resetUserPic);
			
		} catch (URISyntaxException e) {
			
			Logging.error(e);
		}
		
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		return model;		
	}
	
	// Admin Deactivate User
	@RequestMapping(value="deactivateUser.do", method=RequestMethod.POST)
	public String deactivateUser(@RequestParam(value="deactivate") int userId, HttpServletRequest req){
				
		User deactivateUser = businessDelegate.requestUser(userId);
		deactivateUser.setActive(false);
		businessDelegate.updateRecord(deactivateUser);	
		
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		return "redirect:/manageusers";
	}
	
	// Admin Activate User
	@RequestMapping(value="activateUser.do", method=RequestMethod.POST)
	public String activateUser(@RequestParam(value="activate") int userId, HttpServletRequest req){
				
		User activateUser = businessDelegate.requestUser(userId);
		activateUser.setActive(true);
		businessDelegate.updateRecord(activateUser);	
		
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		return "redirect:/manageusers";
	}
	
	
	// Admin Can Reset Password
	@RequestMapping(value="resetUserPassword.do", method=RequestMethod.POST)
	public ModelAndView resetUserPassword(@RequestParam(value="resetPass") int userId, HttpServletRequest req){
		ModelAndView model = new ModelAndView();

		model.setViewName("redirect:/manageusers");
		
		User resetUserPassword = businessDelegate.requestUser(userId);
		String email = resetUserPassword.getEmail();
		String role = resetUserPassword.getUserRole().getRole();
		
		// Generate a Temporary Password

		String password = businessDelegate.getRandom(6);

		resetUserPassword.setPassword(password);
		
		// Save in Database
		businessDelegate.updateRecord(resetUserPassword);
		
		// Send Email to Account
		Mailer.sendMail(email, password);
		
		req.setAttribute("userList", businessDelegate.requestUsers());
		req.setAttribute("updateUserProfile", new UserDTO());
		return model;
	}
		
	// Update Password Page
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute("updatePassword") @Valid UserDTO passwordDTO, BindingResult bindingResult,
							   HttpServletRequest req, HttpServletResponse resp){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/profile");
		if(bindingResult.hasErrors()){
			model.setViewName("redirect:/password");
			return model;
		}
		
		String password = passwordDTO.getNewPassword();
		
		User loggedIn = (User) req.getSession().getAttribute("user");
		
		loggedIn.setPassword(businessDelegate.maskElement(password, loggedIn.getEmail(), loggedIn.getFullname()));
		
		if(loggedIn.isNewUser()){
			loggedIn.setNewUser(false);
		}
		
		String success = "success";
		
		req.getSession().setAttribute("user", loggedIn);
		req.getSession().setAttribute("passwordSuccess", success);
		req.getSession().setAttribute("userUpdate", null);
		businessDelegate.updateRecord(loggedIn);
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
		req.getSession().setAttribute("passwordSuccess", null);
		req.getSession().setAttribute("userUpdate", null);
		return "Success";
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
		TreeMap<Integer, String> references = new TreeMap<Integer, String>();
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
		Boolean editingBlogInDatabase = (Boolean)req.getSession().getAttribute("editingBlogInDatabase");
		User author = null;
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
		blog.setReferences(getReferences(req));
		
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
			Set<Tags> newTags = new HashSet<Tags>();
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
			req.getSession().setAttribute("newTags", newTags);
		}
		req.getSession().setAttribute("blog", blog);
		return "preview-blog";
	}
	
	@RequestMapping(value="publish.do", method=RequestMethod.POST)
	public String publishBlog(HttpServletRequest req, HttpServletResponse resp) {
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		HtmlWriter htmlWriter;
		String url = "";
		Set<Tags> newTags = (Set<Tags>)req.getSession().getAttribute("newTags");
		for(Tags newTag : newTags){
			businessDelegate.putRecord(newTag);
		}
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile blogTempFile = htmlWriter.render();
			String fileName = blogTempFile.getTemporaryFile().getName();
			url = "http://blogs.pjw6193.tech/content/pages/" + fileName;
			blog.setLocationURL(url);
			Boolean editingBlogInDatabase = (Boolean)req.getSession().getAttribute("editingBlogInDatabase");
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
			req.getSession().setAttribute("editingBlogInDatabase", false);
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
		businessDelegate.delete(blog.getBlogTitle());
		String[] str = businessDelegate.getList();
		req.setAttribute("blog", new Blog());
		req.setAttribute("list", str);
		ModelAndView model = new ModelAndView();
		model.setViewName("/management");
		return model;
	}
	
	@RequestMapping(value="delete.do", method=RequestMethod.POST)
	public String deleteUserBlog(HttpServletRequest req, HttpServletResponse resp) {
		String blogLink = req.getParameter("blog-link");
		String cutBlogLink = blogLink.replace("http://blogs.pjw6193.tech/", "");
		businessDelegate.delete(cutBlogLink);
		return "user-blogs";
	}
}
