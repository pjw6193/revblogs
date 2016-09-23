package com.revature.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DataService;
import com.revature.data.impl.PaginatedResultList;
import com.revature.data.impl.PropertyType;
import com.revature.dto.AuthorDTO;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.dto.BlogPostDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Crypt;
import com.revature.service.JetS3;

@Service
public class BusinessDelegateImpl implements BusinessDelegate{
	
	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */

	private Crypt crypt;
	private DataService dataService;
	private JetS3 jetS3;
	private static final String ILLEGAL = "page and perPage must be positive integers";

	public void setCrypt(Crypt crypt) {
		this.crypt = crypt;
	}
	public Crypt getCrypt() {
		return crypt;
	}
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public DataService getDataService() {
		return dataService;
	}
	public void setJetS3(JetS3 jetS3) {
		this.jetS3 = jetS3;
		jetS3.syncBusinessDelegate(this);
	}
	public JetS3 getJetS3() {
		return jetS3;
	}
	
	/*
	 *  Methods
	 * 
	 */
	
	public Session requestSession() {
		return dataService.grabSession();
	}
	
	/**
	 * Attempts to upload a resource (such as a CSS or JS file) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file) {
		return jetS3.uploadResource(fileName, file);

	}
	
	/**
	 * Attempts to upload a front-end page (html) to the S3 server
	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(File file) {
		return jetS3.uploadPage(file);

	}
	
	/**
	 * Attempts to upload an 'evidence' (picture, code, attachment) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadEvidence(String fileName, MultipartFile file) {
		return jetS3.uploadEvidence(fileName, file);

	}
	
	/**
	 * Attempts to upload a profile picture to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param loginName the name (or email) the user logs in with
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadProfileItem(String loginName, String fileName, MultipartFile file) {
		return jetS3.uploadProfileItem(loginName, fileName, file);
	}
	
	public String uploadProfileItem(String folderPath, String fileName){
		return jetS3.uploadProfileItem(folderPath, fileName);
	}
	
	public String uploadProfileItem(String loginName, String fileName, File file){
		return jetS3.uploadProfileItem(loginName, fileName, file);
	}
	
	public String uploadProfileItem(String loginName, File file){
		return jetS3.uploadProfileItem(loginName, file);
	}
	
	public String uploadInitial(File file){
		return jetS3.uploadInitial(file);
	}
	
	public String[] getList(){
		return jetS3.list();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean delete(String filename){
		if(filename.contains("pages")){
			List<Blog> blogs = dataService.grabBlogs();
			for(Blog blog:blogs){
				if(blog.getLocationURL().endsWith(filename)){
					dataService.deleteRecord(blog);
					return jetS3.delete(filename);
				}
			}
		}
		else if(filename.contains("evidence")){
			return jetS3.delete(filename);
		}
		return false;
	}
	
	/*
	 *  Database Altering Methods
	 */
	
	public void putRecord(Object obj){
		dataService.makeRecord(obj);
	}
	public void putRecord(Object[] obj){
		dataService.makeRecord(obj);
	}
	public void updateRecord(Object obj){
		dataService.changeRecord(obj);
	}
	public void updateRecord(Object[] obj){
		dataService.changeRecord(obj);
	}
	
	/*
	 *  Database Query Methods
	 */
	
	public User requestUsers(String username){
		return dataService.grabUsers(username);
	}
	public UserRoles requestRoles(int roleId) {
		return dataService.grabRoles(roleId);
	}
	public UserRoles requestRoles(String role) {
		return dataService.grabRoles(role);
	}
	public String requestProperty(PropertyType type){
		return crypt.getProperty(type);
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
	public User requestUser(int id) {
		return dataService.grabUser(id);
	}
	public Tags requestTag(int id) {
		return dataService.grabTag(id);
	}
	
	/*
	 * 
	 *  Encryption
	 */
	public String maskElement(String target){
		
		return crypt.encrypt(target);
	}
	public boolean validate(String input, String hashed) {
		
		return crypt.validate(input, hashed);	
	}
	public String getRandom(int length){
		
		return crypt.getRandom(length);
	}
	public void setProperty(String[] props){
		
		crypt.setProperty(props);
	}
	
	//-------------------------------------------------------------------------------------------------
	// Pagination
	
	public BlogPostCollectionDTO requestBlogPosts(int page, int perPage){
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException(ILLEGAL);
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		
		Set<String> searchFill = new HashSet<>();
		
		for(int i = 0; i < postList.size(); i++)
		{
			searchFill.add(postList.get(i).getTitle().toUpperCase());
			searchFill.add(postList.get(i).getSubtitle().toUpperCase());
			searchFill.add(postList.get(i).getAuthor().getName().toUpperCase());
			for (int j = 0; j < postList.get(i).getTags().size(); j++)
			{
				searchFill.add(postList.get(i).getTags().get(j).toUpperCase());
			}
		}
		
		searchFill.toArray();
		List<String> sortedResults = new ArrayList<String>(searchFill);
		Collections.sort(sortedResults);
		
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		postCollection.setSearchFills(sortedResults);
		
		return postCollection;
	}
	
	public BlogPostCollectionDTO requestBlogPosts(User author, int page, int perPage){
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException(ILLEGAL);
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(author, start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		postCollection.setAuthor(new AuthorDTO(author));
		
		return postCollection;
	}

	public BlogPostCollectionDTO requestBlogPosts(Tags category, int page, int perPage){
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException(ILLEGAL);
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(category, start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		postCollection.setCategory(category.getDescription());
		
		return postCollection;
	}
	
	public BlogPostCollectionDTO searchBlogPosts(String query, int page, int perPage){
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException(ILLEGAL);
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			BlogPostDTO blog = new BlogPostDTO(p);
			
			if(blog.getTitle().contains(query) || blog.getContent().contains(query) || blog.getAuthor().getName().contains(query) || blog.getSubtitle().contains(query))
			{
				postList.add(blog);
			}
			
			else
			{
				for (int i = 0; i < blog.getTags().size(); i++)
				{
					if(blog.getTags().get(i).contains(query))
					{
						postList.add(blog);
					}
				}
			}
				
			//postList.add(new BlogPostDTO(p));
		}
		//long totalItems = results.getTotalItems();
		long totalItems = postList.size();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		
		return postCollection;
	}
	
	public Blog requestBlog(int id) {
		return dataService.grabBlog(id);
	}
}
