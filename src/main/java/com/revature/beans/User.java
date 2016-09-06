package com.revature.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;

@Entity
@Table(name="PP_USER")
public class User {
	
	/*
	 *  User Attributes
	 */
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="userSequence")
	@SequenceGenerator(name="userSequence",sequenceName="USER_SEQUENCE",initialValue=1,allocationSize=1)
	private int userId;
		
	@Column(name="USER_EMAIL", unique=true, nullable=false)
	private String email;
	
	@Column(name="USER_PASSWORD", nullable=false)
	private String password;
		
	@Column(name="USER_FIRST", nullable=false)
	@Field
	private String firstName;
		
	@Column(name="USER_LAST", nullable=false)
	@Field
	private String lastName;
		
	@Column(name="USER_PICTURE")
	private String profilePicture;
		
	@Column(name="USER_TITLE", nullable=false)
	private String jobTitle;
		
	@Column(name="USER_LINKEDIN", unique=true)
	private String linkedInURL;
		
	@Column(name="USER_DESCRIPTION", nullable=false)
	private String description;
	
	@Column(name="USER_ACTIVE", nullable=false)
	private boolean active;
	
	@Column(name="USER_NEW", nullable=false)
	private boolean newUser;
	
	/*
	 *  Relationship Mapping
	 */
	@ManyToOne
	@JoinColumn(name="USER_ROLE", nullable=false)
	private UserRoles userRole;
		
	@OneToMany
	@JoinColumn(name="USER_BLOGS")
	private Set<Blog> blogs;
		
	/*
	 * 	Constructors
	 */
	public User() {
		super();
		
		// User always starts active && new
		this.active = true;
		this.newUser = true;
	}
	
	// Missing Password, Role, and Picture
	public User(String email, String firstName, String lastName, String jobTitle, String linkedInURL, String description){
		this();
		this.email = email.toLowerCase();
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.linkedInURL = linkedInURL;
		this.description = description;
	}
	
	// All Attributes
	public User(String email, String password, String firstName, String lastName, String jobTitle, 
				String linkedInURL, String description, UserRoles userRole) {
		this();
		this.email = email.toLowerCase();
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.linkedInURL = linkedInURL;
		this.description = description;
		this.userRole = userRole;
	}		
		
	/*
	 * 	Getters & Setters
	 */
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getLinkedInURL() {
		return linkedInURL;
	}
	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserRoles getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Set<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}
	public boolean isNewUser() {
		return newUser;
	}
	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
	public String getFullname(){
		return this.lastName + ", " + this.firstName;
	}
}
