package com.revature.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DataService;
import com.revature.service.BusinessDelegate;
import com.revature.service.ServiceLocator;
import com.revature.service.TemporaryFile;

public class BusinessDelegateImpl implements BusinessDelegate{

	private DataService dataService;
	private ServiceLocator serviceLocator;
	
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	
	public String uploadFile(String folderPath, String fileName, MultipartFile file) {
		String result = null;
		TemporaryFile temporaryFile = TemporaryFile.make(file);
		if ( temporaryFile != null ) {
			System.out.println("Successfully created temporary file!");
			File compatibleFile = temporaryFile.getTemporaryFile();
			result = dataService.uploadFile(folderPath, fileName, compatibleFile);
			temporaryFile.destroy();
		} else {
			System.out.println("FAILED TO CREATE TEMPORARY FILE :(");
		}
		
		/// TEMP ///
		System.out.println("File is now available at: " + result);
		////////////
		
		return result;
	}
	
	// Push
	public void putRecord(Object _obj){
		dataService.makeRecord(_obj);
	}
	
	// Pull
	public User requestUsers(String _username){
		return dataService.grabUsers(_username);
	}
	
	public List<User> requestUsers(){
		return dataService.grabUsers();
	}
	public List<Blog> requestBlogs(){
		return dataService.grabBlogs();
	}
	public List<Tags> requestTags(){
		return dataService.grabTags();
	}
	public List<UserRoles> requestRoles(){
		return dataService.grabRoles();
	}
	public List<Evidence> requestEvidence(){
		return dataService.grabEvidence();
	}
}
