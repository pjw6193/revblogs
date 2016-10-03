package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;

//This is actually a REST controller.  Don't let the AJAX confuse you.

@Controller
@RequestMapping("/api")
public class AjaxController {
	private BusinessDelegate businessDelegate;
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.GET)
	@ResponseBody
	public BlogPostCollectionDTO getPosts (
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="per_page", required=false, defaultValue="10") int perPage,
			@RequestParam(value="author", required=false, defaultValue="0") int authorId,
			@RequestParam(value="category", required=false, defaultValue="0") int tagId,
			@RequestParam(value="q", required=false) String searchQuery,
			HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		try {
			User author = null;
			Tags category = null;
			if(authorId > 0) {
				author = businessDelegate.requestUser(authorId);
			}
			
			if(tagId > 0) {
				category = businessDelegate.requestTag(tagId);
			}
			
			if (author != null) {
				return businessDelegate.requestBlogPosts(author, page, perPage);
			}
			else if (category != null) {
				return businessDelegate.requestBlogPosts(category, page, perPage);
			}
			else if (searchQuery != null) {
				return businessDelegate.searchBlogPosts(searchQuery, page, perPage);
			}
			else {
				return businessDelegate.requestBlogPosts(page, perPage);
			}
		} catch (IllegalArgumentException e) {
			Logging.error(e);
			return null;
		}
	}
	
	@RequestMapping(value="/posts", method=RequestMethod.OPTIONS)
	@ResponseBody
	public void getPostsPreflight(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");
	}
}
