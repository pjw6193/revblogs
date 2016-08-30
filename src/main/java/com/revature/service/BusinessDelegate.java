package com.revature.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

public interface BusinessDelegate {
	
	/**
	 * Attempts to upload a file to the S3 server
	 * @param folderPath the path to the folder this file will be stored at starting at the S3 root
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a MultipartFile that is to be uploaded to the database
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadFile(String folderPath, String fileName, MultipartFile file);
	
	// Push
	public void putRecord(Object _obj);
	
	// Pull
	public User requestUsers(String _username);
	
	public List<User> requestUsers();
	public List<Blog> requestBlogs();
	public List<Tags> requestTags();
	public List<UserRoles> requestRoles();
	public List<Evidence> requestEvidence();
}
