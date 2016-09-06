package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="PP_PROPERTIES")
public class ApplicationProperties {
	
	@Id
	@Column(name="PROPERTY_COMPANY")
	private String company;
	
	@Column(name="PROPERTY_APP", unique=true, nullable=false)
	private String app;
	
	@Column(name="PROPERTY_S3", unique=true, nullable=false)
	private String s3;
	
	@Column(name="PROPERTY_SERVER", unique=true, nullable=false)
	private String server;
	
	@Column(name="PROPERTY_JENKINS", unique=true, nullable=false)
	private String jenkins;
	
	@Column(name="PROPERTY_SONARQUBE", unique=true, nullable=false)
	private String sonarqube;
	
	@Column(name="PROPERTY_K", unique=true, nullable=false)
	private String k;
	
	@Column(name="PROPERTY_V", unique=true, nullable=false)
	private String v;
	
	@Column(name="PROPERTY_FAPP", unique=true, nullable=false)
	private String fapp;
	
	@Column(name="PROPERTY_LINKTOKEN", unique=true, nullable=false)
	private String linkToken;
	
	@Column(name="PROPERTY_S3BUCKET", unique=true, nullable=false)
	private String bucketURL;
	
	/**
	 *  Constructors
	 */
	public ApplicationProperties() {
		super();
	}
	public ApplicationProperties(String company, String app, String s3, String server, String jenkins, String sonarqube,
			String k, String v, String fapp, String linkToken, String bucketURL) {
		super();
		this.company = company;
		this.app = app;
		this.s3 = s3;
		this.server = server;
		this.jenkins = jenkins;
		this.sonarqube = sonarqube;
		this.k = k;
		this.v = v;
		this.fapp = fapp;
		this.linkToken = linkToken;
		this.bucketURL = bucketURL;
	}
	
	/**
	 * 	Getters & Setters
	 */
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getJenkins() {
		return jenkins;
	}
	public void setJenkins(String jenkins) {
		this.jenkins = jenkins;
	}
	public String getSonarqube() {
		return sonarqube;
	}
	public void setSonarqube(String sonarqube) {
		this.sonarqube = sonarqube;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getFapp() {
		return fapp;
	}
	public void setFapp(String fapp) {
		this.fapp = fapp;
	}
	public String getLinkToken() {
		return linkToken;
	}
	public void setLinkToken(String linkToken) {
		this.linkToken = linkToken;
	}
	public String getBucketURL() {
		return bucketURL;
	}
	public void setBucketURL(String bucketURL) {
		this.bucketURL = bucketURL;
	}
}
