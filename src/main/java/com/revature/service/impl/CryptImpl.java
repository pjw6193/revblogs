package com.revature.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.controllers.GetController;
import com.revature.service.BusinessDelegate;

@Service
public class CryptImpl implements PasswordEncoder{

	/*
	 *  Attributes && Getters/Setters
	 */
	
	public static final User user = new User();
	
	private BusinessDelegate businessDelegate;
	private GetController getController;
	
	public BusinessDelegate getBusinessDelegate() {		
		return businessDelegate;
	}
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	public GetController getController() {
		return getController;
	}
	public void setGetController(GetController getController) {
		this.getController = getController;
	}
	
	/*
	 * Password Encoder implementation for Custom Encryption
	 * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
	 */
	
	public String encrypt(CharSequence passwordInput){
		return Crypt.encrypt((String)passwordInput, user.getEmail(), user.getFullname());
	}
	
	/*
	 * 
	 * 	Encode comes from: the Spring Password Encoder, but doesn't allow me to use Getters/Setters for some stupid reason.
	 * 					   It requires an implementation, but it's not required to be called anywhere. DO NOT REMOVE! I was 
	 * 					   able to bypass it by implementing the same exact lines in a custom method and calling that instead.
	 * 
	 * 					   Oh Spring! Why do you hurt me so?
	 */
	
	public String encode(CharSequence passwordInput) {
		
		//String temp = Crypt.encrypt((String)passwordInput, user.getEmail(), user.getFullname())
		//return temp
		
		return "";
	}
	public boolean matches(CharSequence passwordInput, String encodedPassword) {
		return encodedPassword.equals(encrypt(passwordInput));
	}
}
