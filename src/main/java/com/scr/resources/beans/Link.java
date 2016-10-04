package com.scr.resources.beans;

public class Link {
	private String link;
	private String rel;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	/**
	 * @param link
	 * @param rel
	 */
	public Link(String link, String rel) {
		super();
		this.link = link;
		this.rel = rel;
	}
	
	
}