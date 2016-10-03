package com.revature.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogPostCollectionDTO {
	
	int page = 0;
	
	@JsonProperty("total_pages")
	int totalPages = 0;
	
	@JsonProperty("per_page")
	int perPage = 0;
	
	@JsonProperty("total_posts")
	long totalPosts = 0;
	
	String prev = null;
	String next = null;
	List<BlogPostDTO> posts = new ArrayList<>();
	
	List<String> searchFills = new ArrayList<>();
	
	public List<String> getSearchFills() {
		return searchFills;
	}
	public void setSearchFills(List<String> searchFills) {
		this.searchFills = searchFills;
	}
	AuthorDTO author = null;
	String category = null;
	
	public BlogPostCollectionDTO() {
		super();
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalPosts() {
		return totalPosts;
	}
	public void setTotalPosts(long totalPosts) {
		this.totalPosts = totalPosts;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public List<BlogPostDTO> getPosts() {
		return posts;
	}
	public void setPosts(List<BlogPostDTO> posts) {
		this.posts = posts;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public AuthorDTO getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
