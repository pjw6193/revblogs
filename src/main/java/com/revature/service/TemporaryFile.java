package com.revature.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class TemporaryFile {

	private File temporaryFile;
	private File temporaryDirectory;
	private String temporaryDirectoryPath;
	
	/**
	 * Makes a temporary file on the application servers instance store
	 * out of the given MultipartFile
	 * @param multipartFile the MultipartFile to be made into a temporary File
	 * @return the temporaryFile if created, null otherwise
	 */
	public static TemporaryFile make(MultipartFile multipartFile) {
		
		TemporaryFile temporaryFileContainer = null;
		try
		{
			temporaryFileContainer = new TemporaryFile();
			if ( temporaryFileContainer.createTemporaryFile(multipartFile) )
				return temporaryFileContainer;
		}
		catch ( Throwable t ) {}
		
		if ( temporaryFileContainer != null )
			temporaryFileContainer.destroy();
		
		return null;
	}
	
	public File getTemporaryFile() {
		if ( temporaryFile != null && temporaryFile.exists() )
			return temporaryFile;
		
		return null;
	}
	
	public void destroy() {
		try { removeTemporaryFile();	  } catch ( Throwable t ) {}
		try { removeTemporaryDirectory(); } catch ( Throwable t ) {}
	}
	
	
	protected TemporaryFile() {
		
		temporaryFile = null;
		temporaryDirectory = null;
		temporaryDirectoryPath = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
	
	protected boolean createTemporaryFile(MultipartFile multipartFile) throws IOException {
		
		if ( createTemporaryDirectory() ) {
			temporaryFile = new File(temporaryDirectoryPath + "tmp");
			FileOutputStream fos = new FileOutputStream(temporaryFile);
			fos.write(multipartFile.getBytes());
		    fos.close();
		    return true;
		}
		return false;
	}
	
	protected boolean removeTemporaryFile() {
		return temporaryFile == null ||
			   !temporaryFile.exists() ||
			   temporaryFile.delete();
	}
	
	protected boolean createTemporaryDirectory() {
		
		temporaryDirectoryPath = System.getProperty("java.io.tmpdir")
				+ "tmp" + System.nanoTime();
		temporaryDirectory = new File(temporaryDirectoryPath);
		return temporaryDirectory.exists() ||
			   temporaryDirectory.mkdir();
	}
	
	protected boolean removeTemporaryDirectory() {
		
		return temporaryDirectory == null ||
			   !temporaryDirectory.exists() ||
			   temporaryDirectory.delete();
	}
}
