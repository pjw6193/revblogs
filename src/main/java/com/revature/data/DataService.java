package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.impl.PaginatedResultList;

@Service
public interface DataService {
	
	public Session grabSession();
	
	// Push
	public void makeRecord(Object obj);
	public void makeRecord(Object[] obj);
	public void changeRecord(Object obj);
	public void changeRecord(Object[] obj);
	public void deleteRecord(Object obj);
	
	// Pull 1
	public User grabUsers(String email);
	public UserRoles grabRoles(int roleId);
	public User grabUser(int id);
	public Tags grabTag(int id);
	public UserRoles grabRoles(String role);
	public Blog grabBlog(int id);
	
	// Pull All
	public List<User> grabUsers();
	public List<Blog> grabBlogs();
	public List<Tags> grabTags();
	public List<UserRoles> grabRoles();
	public List<Evidence> grabEvidence();
	
	// Pagination
	public PaginatedResultList<Blog> grabBlogs(int start, int max);
	public PaginatedResultList<Blog> grabBlogs(User author, int start, int max);
	public PaginatedResultList<Blog> grabBlogs(Tags category, int start, int max);
	public PaginatedResultList<Blog> grabBlogs(String search, int start, int max);
}
