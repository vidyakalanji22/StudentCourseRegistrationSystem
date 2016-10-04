package com.scr.resources.beans;
import java.util.List;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestCurriculumVO {
	
	@NotNull
	private int curriculumId;
	
	@NotNull(message="Topic Name can not be null")
    @Size(min=1)
	private @QueryParam("topicName") String topicName;
	private List<Link> links = new ArrayList<Link>();

	public RestCurriculumVO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param curriculumId
	 * @param topicName
	 */
	public RestCurriculumVO(int curriculumId, String topicName) {
		super();
		this.curriculumId = curriculumId;
		this.topicName = topicName;
	}
	
	
	/**
	 * @return the curriculumId
	 */
	public int getCurriculumId() {
		return curriculumId;
	}
	/**
	 * @param curriculumId the curriculumId to set
	 */
	public void setCurriculumId(int curriculumId) {
		this.curriculumId = curriculumId;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
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
