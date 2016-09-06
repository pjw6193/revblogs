package com.revature.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.revature.beans.ApplicationProperties;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.impl.Crypt;

public class Population {

	private BusinessDelegate delegate;
	private Logging logger;
	private Session session;
	
	public void setDelegate(BusinessDelegate delegate) {
		this.delegate = delegate;
	}
	public void setLogger(Logging logger) {
		this.logger = logger;
	}

	//-----------------------------------
	// Complete Population (Besides Evidence)
	public void populateDatabase(){
		
		populateProperties();
		populateRoles();
		populateTags();
		populateUsers();
		populateBlogs();
	}
	
	//-----------------------------------
	// Roles
	public void populateRoles(){
		
		/**
		 *  List the roles.
		 *  Iterate through the list.
		 *  Insantiate a new Role for each role description.
		 *  Save that role in the database.
		 */
		
		String[] roles = new String[]{"ADMIN", "CONTRIBUTOR"};
		
		//Assigning null indicates it is ready for GC, which can
		//cause error in this situation, please avoid this practice
		//Assign either new constructor or leave as is below.
		UserRoles role;
		
		for(int i = 0; i < 2; i++){
			
			role = new UserRoles(roles[i]);
		
			delegate.putRecord(role);
		}
		
		logger.log("-- Done Populating Role Table --");
	}
	
	//-----------------------------------
	// Tags
	public void populateTags(){
		
		/**
		 *  List the tags.
		 *  Iterate through the tags.
		 *  Insantiate a new Tag for each tag description.
		 *  Save that tag to the GLOBAL list.
		 *  Save that tag in the database.
		 */
		
		String[] tags = new String[]{"Java", "SQL", "Apian", "JSP", "Servlet", "Hibernate", "Spring", "REST", "SOAP"};
		
		//Assigning null indicates it is ready for GC, which can
		//cause error in this situation, please avoid this practice
		//Assign either new constructor or leave as is below.
		Tags tag;
		
		for(int i = 0; i < tags.length; i++){
			
			tag = new Tags(tags[i]);
					
			delegate.putRecord(tag);
		}
		
		logger.log("-- Done Populating Tags Table --");
	}
	
	//-----------------------------------
	// Blogs
	public void populateBlogs(){
		
		/**
		 *  List the blog data.
		 *  Iterate through the blog data.
		 *  Insantiate a new Blog for each corresponding data element.
		 *  Save that blog in the database.
		 */
		
		session = delegate.requestSession();
		
		List<Tags> tagList = new ArrayList<>();
		
		Criteria criteria = session.createCriteria(Tags.class);
		tagList = (List<Tags>)criteria.list();
	
		
		String[] blogTitle = new String[]{
			
			// 1
			"Making Web Pages Flexible",
			
			// 2
			"The differences between SOAP and REST Web Services",
			
			// 3
			"Ditching SQL with HIbernate!"	
		};
		
		String[] blogSubtitle = new String[]{
				
			// 1
			"JSP, JSTL, and Servlets",
			
			// 2
			"Hint: Loads of fun here.",
			
			// 3
			"Criteria to the Rescue"	
		};
		
		String[] blogContent = new String[]{
			
			// 1
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus, est non sodales blandit, "
			+ "dolor odio blandit lorem, eget euismod eros dolor sit amet tortor. Aenean malesuada tempor accumsan. "
			+ "In hac habitasse platea dictumst. Duis commodo metus nulla, et pharetra ipsum venenatis a. Mauris "
			+ "cursus fringilla fermentum. Suspendisse vitae ante nulla. Donec auctor quam ex, at tincidunt lacus "
			+ "volutpat ac.",
			
			// 2
			// 
			"Etiam quis velit fermentum, scelerisque velit eget, dapibus metus. Ut eu diam gravida ligula mollis "
			+ "feugiat sed pellentesque ligula. Nullam commodo, leo vel accumsan cursus, elit quam commodo diam, "
			+ "ullamcorper auctor ante dolor in nibh. Cras mattis nunc at felis accumsan pellentesque. Nulla "
			+ "tincidunt dapibus tortor, mattis fringilla turpis. Nam nulla enim, posuere at mauris et, varius "
			+ "sollicitudin tellus. Quisque cursus vel tellus non fringilla.",
			
			// 3
			"Donec facilisis urna sit amet quam varius, eu porttitor tortor imperdiet. Duis justo felis, elementum"
			+ "eu ante vitae, dignissim tincidunt lectus. Pellentesque odio tellus, blandit a metus sit amet, "
			+ "dignissim sollicitudin tortor. In hac habitasse platea dictumst. Aenean dui risus, auctor non luctus "
			+ "vel, condimentum a turpis. Aenean eu massa malesuada, suscipit purus quis, tristique sapien."
			+ "Aliquam leo nisl, feugiat in porta et, laoreet non purus."
		};
		
		// Tag Population Nonsense
		int[][] tagIndexes = new int[][]{
			
			/*
			 * First  [] = Blog #
			 * Second [] = Tags of that Blog
			 * 
			 * Tags: Java, SQL, Apian, JSP, Servlet, Hibernate, Spring, REST, SOAP
			 * 	 Java      (1)
			 *   SQL       (2)
			 *   Apian     (3)
			 *   JSP 	   (4)
			 *   Servlet   (5)
			 *   Hibernate (6)
			 *   Spring    (7)
			 *   REST      (8)
			 *   SOAP      (9)
			 */
	
			// 1
			{1, 0, 0, 4, 5, 0, 0, 0, 0},
			
			// 2
			{1, 0, 0, 0, 0, 0, 0, 8, 9},
			
			// 3
			{1, 2, 0, 0, 0, 6, 0, 0, 0}
		};
		
		List<User> users = delegate.requestUsers();
		
		//Assigning null indicates it is ready for GC, which can
		//cause error in this situation, please avoid this practice
		//Assign either new constructor or leave as is below.
		Blog blog;
		
		for(int i = 0; i < blogTitle.length; i++){
			
			Set<Tags> tagsSet = new HashSet<>();
			
			// Attach the set of Product Categories that correspond to THIS particular product.
			for(int j = 0; j < tagIndexes.length; j++){
				
				if(tagIndexes[i][j] != 0){
					
					tagsSet.add(tagList.get(tagIndexes[i][j] - 1));
				}
			}
			
			blog = new Blog(blogTitle[i], blogSubtitle[i], blogContent[i], users.get(i), tagsSet);
			delegate.putRecord(blog);
		}

		logger.log("-- Done Populating Blogs Table --");
	}
	
	//-----------------------------------
	// Users
	public void populateUsers(){
		
		session = delegate.requestSession();
			
		String[] email = new String[]{
				
				"pickles@yahoo.com",
				"dots@yahoo.com",
				"admin@test.com"
		};
		
		String[] password = new String[]{
				
				"password1",
				"password1",
				"password1"
		};
		
		String[] firstName = new String[]{
				
				"Dan",
				"John",
				"Tally"
		};
		String[] lastName = new String[]{
				
				"Pickles",
				"Doe",
				"Marx"
		};
		String[] jobTitle = new String[]{
				
				"Java Developer",
				"Java Engineer",
				"Hibernate Wizard"
		};
		String[] linkedInURL = new String[]{
				
				"Fake@linkedin.com",
				"Other@linkedin.com",
				"Almost@linkedin.com"
		};
		String[] description = new String[]{
				
				"I am a software developer who focuses on applications that revolve around pickles.",
				"I am a Java developer with a lot of experience in web services.",
				"I excel at Hibernate and hate SQL."
		};
 		boolean[] isAdmin = new boolean[]{true,false,true};
		
		Criteria criteria1 = session.createCriteria(UserRoles.class).add(Restrictions.eq("role", "ADMIN"));
		Criteria criteria2 = session.createCriteria(UserRoles.class).add(Restrictions.eq("role", "CONTRIBUTOR"));
		
		UserRoles admin = (UserRoles) criteria1.uniqueResult();
		UserRoles contributor = (UserRoles) criteria2.uniqueResult();
		
		UserRoles myRole;
		
		for(int i = 0; i < email.length; i++){
			
			// Find Role
			if(isAdmin[i]){
				
				myRole = admin;
				
			} else {
				
				myRole = contributor;
			}
			
			User user = new User(email[i], password[i], firstName[i], lastName[i], jobTitle[i], linkedInURL[i], 
								 description[i], myRole);
			
			// Encrypt Password
			user.setPassword(Crypt.encrypt(user.getPassword(), user.getEmail(), user.getFullname()));
			
			delegate.putRecord(user);
		}
		
		logger.log("-- Done Populating Users Table --");
	}
	
	//-----------------------------------
	// Properties
	public void populateProperties(){
		
		/**
		 *  Insantiate 1 entry with all the encrypted properties.
		 *  Save that entry in the database.
		 */
		
		String[] props = new String[]{
			
			/*
			 * Company
			 * App
			 * S3
			 * Server
			 * Jenkins
			 * Sonarqube
			 * K
			 * V
			 * Facebook Auth
			 * LinkedIn Token
			 * S3 Bucket
			 * 
			 */
		
			/*
			 * Properties removed to keep credentials secure once pushed to GitHub.
			 * Additionally, properties already instantiated in database. This field
			 * was kept so that populating different properties would be as easy as
			 * plugging them in here and re-running this method (you may need to drop old
			 * properties to avoid unique contraint though).
			 * 
			 */
		};
		
		for(int i = 0; i < props.length; i++){
			
			switch(i){
			
				/*
				 * Crypt.encrypt was developed to encrypt passwords. Which is why the fields are labeled as:
				 * password, username, and email. However, these declarations are perfectly valid! The first
				 * parameter is the String that you want encrypted (your juicy data). The second parameter is
				 * a keyword; I utilized a custom Vigenere Cipher to shift the keys in the first parameter with
				 * the corresponding values in the keyword. Data wrapping is programmed too, meaning that you 
				 * can have a keyword that is shorter than the value you want encrypted, it will automatically
				 * wrap back to the beginning of the word when it hits the end. Finally, the third parameter 
				 * takes the length of the argument and runs the cipher to encrypt the first parameter the number
				 * of times equal to the length. 
				 */
			
				case 0: 
					props[i] = Crypt.encrypt(	props[i], 
												"CZmTgoznKnJocTkGuFFURvZjUDuVvBhoETorfnzPOfqymleBbOOHfqPCSSty", 
												"pneumonoultramicroscopicsilicovolcanoconiosis"); 
					break;
				case 1: 
					props[i] = Crypt.encrypt(	props[i], 
												"GSXWzGGiiDBvlYxTNddabeUOsSPLHoYnibqBEAtRrSDnZPrACvUjBMGxcoBZ", 
												"Pseudopseudohypoparathyroidism"); 
					break;
				case 2: 
					props[i] = Crypt.encrypt(	props[i], 
												"cCpQZBETFySMWXeMTQDQomszbDhIgTCWNfjzrBQjwyzcMIrNeFGZggWpzSdQ", 
												"Floccinaucinihilipilification"); 
					break;
				case 3: 
					props[i] = Crypt.encrypt(	props[i], 
												"UjVheJqfrHXEuciEaIEibjRYjaxGEJFPrLcZNuugxZQmpHdeoBJRVLFeEDfc", 
												"Antidisestablishmentarianism"); 
					break;
				case 4: 
					props[i] = Crypt.encrypt(	props[i], 
												"BhXCFkEevSCHlJMCJyvqhyOiNnKDaoxwcdWrNGxUZySIJspidexHSROVXDAh", 
												"supercalifragilisticexpialidocious"); 
					break;
				case 5: 
					props[i] = Crypt.encrypt(	props[i], 
												"RnhHIlwovrapdVzySrOIfmMZPOPOEACAsVScsBIflnsIphgireiIRKkmINdr", 
												"Incomprehensibilities"); 
					break;
				case 6: 
					props[i] = Crypt.encrypt(	props[i], 
												"momGKfMimvxYGNKmZCzdXNSBGpvQngTbtvxETwjePoZWyirhkyAWMhkFzxQI", 
												"honorificabilitudinitatibus"); 
					break;
				case 7: 
					props[i] = Crypt.encrypt(	props[i], 
												"TuJgzrAAFblqmFUfDvRyNHOtKQjVpxESLwrXecnGMSrSEJyhfkgPGvTccbPJ", 
												"sesquipedalianism"); 
					break;
				case 8: 
					props[i] = Crypt.encrypt(	props[i], 
												"JplYSkoJXvxUEIaEZtLMzYugcPINpzArbIoGHjwHwFzdoUtfNfMOetPvvsHn", 
												"METHIONYLTHREONYLTHREONYGLUTAMINYLARGINY"); 
					break;
				case 9: 
					props[i] = Crypt.encrypt(	props[i], 
												"iqGJkjoSepUYggqxsZCdxXzCSyjxADhQtsiMPhyNRMxJbGowMrGmlIQETFzC", 
												"Aequeosalinocalcalinoceraceoaluminosocupreovitriolic"); 
					break;
				case 10: 
					props[i] = Crypt.encrypt(	props[i], 
												"boosNkoVgLkjnWJUMEeHAGbUmwWhVlBOPZKZjUduUXunxwbsZmnNxKdAWePg ", 
												"peobuefdvxjbtoajefspkfuccfngbf"); 
					break;
				default:
					
					break;
			}
			
			
		}

		ApplicationProperties propObj = new ApplicationProperties(	props[0], 
																	props[1], 
																	props[2],
																	props[3],
																	props[4], 
																	props[5], 
																	props[6], 
																	props[7],
																	props[8],
																	props[9],
																	props[10]);
			
		delegate.putRecord(propObj);
		
		logger.log("-- Done Populating Properties Table --");
	}
}
