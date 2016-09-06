package com.revature.data.impl;

import java.util.List;

public class PaginatedResultList<T> {
	
	private List<T> items;
	private long totalItems;
	public PaginatedResultList() {
		super();
	}
	public PaginatedResultList(List<T> items, int totalItems) {
		super();
		this.items = items;
		this.totalItems = totalItems;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
}
