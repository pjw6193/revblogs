package com.revature.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface JetS3 {
	
	/**
	 * Attempts to upload a resource (such as a CSS or JS file) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file);
	
	/**
	 * Attempts to upload a front-end page (html) to the S3 server
	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(File file);
	
	/**
	 * Attempts to upload an 'evidence' (picture, code, attachment) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadEvidence(String fileName, MultipartFile file);
	
	/**
	 * Attempts to upload a profile picture to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param loginName the name (or email) the user logs in with
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadProfileItem(String loginName, String fileName, MultipartFile file);
	
	public String uploadProfileItem(String folderPath, String fileName, File file);
	
	public String uploadProfileItem(String folderPath, String fileName);
	
	public String uploadProfileItem(String loginName, File file);
	
	/**
	 * Used to upload the files at initialization
	 * @param file the actual file that is to be saved
	 * @return
	 */
	public String uploadInitial(File file);
	/**
	 * Used as a base for uploading data to S3, should not be used 
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param filedata the string to be stored in the
	 */
	public boolean uploadText(String filename,String filedata);
	
	/**
	 * Used to delete any file from the bucket as long
	 * as the name matches in the S3
	 * @param filename the files name that is designated to be deleted
	 */
	public boolean delete(String filename);
	
	/**
	 * Used to grab a list of items from the s3 bucket
	 */
	public String[] list();
	

	public void syncBusinessDelegate(BusinessDelegate businessDelegate);

}
