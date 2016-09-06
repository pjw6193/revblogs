package com.revature.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.jsoup.Jsoup;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.beans.Blog;
import com.revature.beans.Tags;

public class BlogPostDTO {
	
	int id = 0;
	String title = null;
	String subtitle = null;
	String content = null;
	String thumbnail = null;
	ArrayList<String> tags = new ArrayList<>();
	AuthorDTO author = new AuthorDTO();
	
	@JsonProperty("post_date")
	Date postDate = new Date();
	
	public BlogPostDTO() {
		super();
	}
	public BlogPostDTO(Blog post) {
		super();
		this.id = post.getBlogId();
		this.title = post.getBlogTitle();
		this.subtitle = post.getBlogSubtitle();
		this.content = post.getBlogContent();
		this.content = Jsoup.parse(content).text();
		Set<Tags> t = post.getTags();
		for (Tags tag: t) {
			this.tags.add(tag.getDescription());
		}
		if (post.getAuthor() != null)
			this.author = new AuthorDTO(post.getAuthor());
		else
			this.author = new AuthorDTO();
		this.postDate = post.getPublishDate();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public AuthorDTO getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}
