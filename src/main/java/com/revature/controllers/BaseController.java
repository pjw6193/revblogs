package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.service.BusinessDelegate;

@Controller
public class BaseController {

	private BusinessDelegate businessDelegate;

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value="/fileUpload.TEMP", method=RequestMethod.GET)
	public ModelAndView tempFileUploadPage() {
		ModelAndView mv = new ModelAndView("file-upload");
		return mv;
	}
	
	@RequestMapping(value="/uploadFile.do", method=RequestMethod.POST)
	public ModelAndView uploadFileHandler(
			//@RequestParam("upc") String upc,
			@RequestParam("file") MultipartFile file
			/*HttpServletRequest req*/)
	{
		businessDelegate.uploadFile("Test/", "testFile", file);
		return tempFileUploadPage();
	}
	
	
}
