package com.revature.data.impl;

import java.io.File;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;

public class DAOImpl implements DAO{

	private SessionFactory sessionFactory;
	private Session session;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.session = this.sessionFactory.openSession();
	}
	
	public DAOImpl(){
		super();
	}
	
	public DAOImpl(SessionFactory _factory){
		this();
		setSessionFactory(_factory);
	}
	
	@Override
	public String uploadFile(String folderPath, String fileName, File file) {
		System.out.println(com.fasterxml.jackson.databind.ObjectMapper.class.getProtectionDomain().getCodeSource().getLocation());
		String bucketName = "jjf28-bucket";
		AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJVEAKBOMV6NISKKA", "bYRtQq1LepU6C9UeRpPceddfl0pXvykV");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		//AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("jjf28"));
		try {
			String keyName = "IMS-Storage/" + fileName;
			PutObjectResult result = s3client.putObject(new PutObjectRequest(
	                 bucketName, keyName, file));
			
		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
        	System.out.println("Error Message: " + ace.getMessage());
        }
		return "NotYetRelevant";
	}
	
	// Push
	
	/*
	 * insertRecord will make a record if one does not exist
	 * 
	 */
	
	public void insertRecord(Object _obj){
		
		Object mergedObj = session.merge(_obj);
		session.saveOrUpdate(mergedObj);
	}


	// Pull
	public User getUsers(String _username){
		
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("username", _username));
		User user = (User)criteria.uniqueResult();
		return user;
	}
	
	public List<User> getUsers(){
	
		Criteria criteria = session.createCriteria(User.class);
		List<User> users = (List<User>)criteria.list();
		return users;
	}
	public List<Blog> getBlogs(){
	
		Criteria criteria = session.createCriteria(Blog.class);
		List<Blog> blogs = (List<Blog>)criteria.list();
		return blogs;
	}
	public List<Tags> getTags(){
		
		Criteria criteria = session.createCriteria(Tags.class);
		List<Tags> tags = (List<Tags>)criteria.list();
		return tags;
	}
	public List<UserRoles> getRoles(){
	
		Criteria criteria = session.createCriteria(UserRoles.class);
		List<UserRoles> roles = (List<UserRoles>)criteria.list();
		return roles;
	}
	public List<Evidence> getEvidence(){
		
		Criteria criteria = session.createCriteria(Evidence.class);
		List<Evidence> evidence = (List<Evidence>)criteria.list();
		return evidence;
	}
}
