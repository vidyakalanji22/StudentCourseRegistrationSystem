package com.scr.resources.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.Link;

public class RestBookVO {

	@NotNull
	private int bookID;
	@NotNull(message="Book Name can not be null")
	@Size(min=1)
	private String bookName;
	private List<Link> links = new ArrayList<Link>();
	
	public RestBookVO() {
		// TODO Auto-generated constructor stub
	}

	public RestBookVO(String bookName) {
		super();
		this.bookName = bookName;
	}
	
	public RestBookVO(int bookID, String bookName) {
		this.bookID = bookID;
		this.bookName = bookName;
	}
	
	public RestBookVO(int bookID) {
		super();
		this.bookID = bookID;
	}
	/**
	 * @return the bookID
	 */
	public int getBookID() {
		return bookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(String url, String rel) {
		links.add(Link.fromUri(url)
				.rel(rel).build());
	}
	
}
