package com.revature.service.impl;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.revature.service.BusinessDelegate;
import com.revature.service.JetS3;

public class JetS3Impl implements JetS3{

	private BusinessDelegate businessDelegate;
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	@Override
	public String uploadResource(String fileName, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadPage(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadEvidence(String fileName, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean uploadText(String filename, String filedata) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String filename) {
		// TODO Auto-generated method stub
		return false;
	}
}

//	
//	private static AWSCredentials credentials;
//	private static S3Service s3;
//	private static Logger log = Logger.getRootLogger();
//	private static final String BUCKET = "blogs.pjw6193.tech";
//	private BusinessDelegate businessDelegate;
//	private String credBucket;
//	
//	public JetS3Impl() {
//		super();
//	}
//	public JetS3Impl(BusinessDelegate businessDelegate) {
//		super();
//		this.businessDelegate = businessDelegate;
//	}
//	
//	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
//		this.businessDelegate = businessDelegate;
//		JetS3Impl.syncBusinessDelegate(this.businessDelegate);
//		credBucket = this.businessDelegate.requestProperty(PropertyType.S3BUCKET);
//	}
//
//	public static synchronized void syncBusinessDelegate(BusinessDelegate businessDelegate){
//		  
//	
//	   	credentials = new AWSCredentials(businessDelegate.requestProperty(PropertyType.K),businessDelegate.requestProperty(PropertyType.V));
//	   	s3 = new RestS3Service(credentials);
//	   	
//	}
//
//	/**
//	 * Attempts to upload a resource (such as a CSS or JS file) to the S3 server
//	 * @param fileName the destination name of the file, a valid extension should be included
//	 * @param file a file that is to be uploaded to the S3 server
//	 * @return the URL where the file was uploaded if successful, null otherwise
//	 */
//	public String uploadResource(String fileName, MultipartFile file) {
//		return uploadFile("content/resources/" + System.nanoTime() + "/", fileName, file);
//	}
//	
//	/**
//	 * Attempts to upload a front-end page (html) to the S3 server
//	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
//	 * @return the URL where the file was uploaded if successful, null otherwise
//	 */
//	public String uploadPage(File file) {
//		return uploadFile("content/pages/", file);
//	}
//	
//	/**
//	 * Attempts to upload an 'evidence' (picture, code, attachment) to the S3 server
//	 * @param fileName the destination name of the file, a valid extension should be included
//	 * @param file a file that is to be uploaded to the S3 server
//	 * @return the URL where the file was uploaded if successful, null otherwise
//	 */
//	public String uploadEvidence(String fileName, MultipartFile file) {
//		return uploadFile("content/evidence/", fileName, file);
//	}
//	
//	/**
//	 * Attempts to upload a file to the S3 server
//	 * @param folderPath the path to the folder this file will be stored at starting at the S3 root
//	 * @param fileName the destination name of the file, a valid extension should be included
//	 * @param file a File that is to be uploaded to the database
//	 * @return the URL where the file was uploaded if successful, null otherwise
//	 */
//	protected String uploadFile(String folderPath, String fileName, MultipartFile file) {
//		try {
//			
//			S3Bucket bucket = s3.getBucket(BUCKET);
//			S3Object s3Obj = new S3Object(folderPath + fileName);
//			s3Obj.setContentType(file.getContentType());
//			AccessControlList acl = new AccessControlList();
//			acl.setOwner(bucket.getOwner());
//			acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
//			s3Obj.setDataInputStream(file.getInputStream());
//			s3Obj.setContentLength(file.getSize());
//			s3Obj.setAcl(acl);
//			s3.putObject(bucket, s3Obj);
//
//			return 
//				"http://" + BUCKET+ "/" + folderPath + fileName;
//			
//		} catch (Exception e) {
//			
//			log.info(e);
//		}
//		return null; // Resource could not be uploaded
//	}
//	
//	/**
//	 * Attempts to upload a file to the S3 server
//	 * @param folderPath the path to the folder this file will be stored at starting at the S3 root
//	 * @param fileName the destination name of the file, a valid extension should be included
//	 * @param file a File that is to be uploaded to the database
//	 * @return the URL where the file was uploaded if successful, null otherwise
//	 */
//	protected String uploadFile(String folderPath, File file) {
//		
//		try {
//			S3Bucket bucket = s3.getBucket(BUCKET);
//			S3Object s3Obj = new S3Object(folderPath + file.getName());
//			AccessControlList acl = new AccessControlList();
//			acl.setOwner(bucket.getOwner());
//			acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
//			FileInputStream fis = new FileInputStream(file);
//			s3Obj.setDataInputStream(fis);
//			s3Obj.setContentLength(file.length());
//			s3Obj.setAcl(acl);
//			s3Obj.setContentType("text/html");
//			s3.putObject(bucket, s3Obj);
//			
//			return 
//					"http://" + BUCKET+ "/" + folderPath + file.getName();
//			
//		} catch (Exception e) {
//			log.info(e);
//		}
//		return null; // Resource could not be uploaded
//	}
//	
//	public boolean uploadFile(MultipartFile mFile)
//	{
//		try{
//			S3Bucket bucket = s3.getBucket(BUCKET);
//			S3Object file = new S3Object("test/"+mFile.getOriginalFilename());
//			file.setContentType(mFile.getContentType());
//			AccessControlList acl = new AccessControlList();
//			acl.setOwner(bucket.getOwner());
//			acl.grantPermission(GroupGrantee.AUTHENTICATED_USERS, Permission.PERMISSION_READ);
//			file.setDataInputStream(mFile.getInputStream());
//			file.setContentLength(mFile.getSize());
//			file.setAcl(acl);
//			s3.putObject(bucket, file);
//			}catch(Exception e)
//			{
//				log.info(e);
//				return false;
//			}	
//			return true;
//	}
//	public boolean uploadText(String filename,String filedata)
//	{
//		try{
//		S3Bucket bucket = s3.getBucket(BUCKET);
//		S3Object file = new S3Object(filename, filedata);
//		AccessControlList acl = new AccessControlList();
//		acl.setOwner(bucket.getOwner());
//		acl.grantPermission(GroupGrantee.AUTHENTICATED_USERS, Permission.PERMISSION_READ);
//		file.setAcl(acl);
//		s3.putObject(bucket, file);
//		}catch(Exception e)
//		{
//			log.info(e);
//			return false;
//		}	
//		return true;
//	}
//	public boolean delete(String filename)
//	{
//		try{
//			S3Bucket bucket = s3.getBucket(BUCKET);
//			s3.deleteObject(bucket, filename);
//		}catch(Exception e)
//		{
//			log.info(e);
//			return false;
//		}	
//		return true;
//	}
//}
