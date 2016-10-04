package com.scr.resources.beans;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class RestScheduleVO {
	
	private int scheduleId;
	

	/*@NotNull(message="Start Date can not be null")
	@Pattern(regexp = "[^(19|20)dd[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$]+", message = "Start Date should be in YYYY-MM-DD format")*/
	private Date startDate;
	
	/*@NotNull(message="End Date can not be null")
	@Pattern(regexp = "[^(19|20)dd[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$]+", message = "End Date should be in YYYY-MM-DD format")*/
	private Date endDate;
	
	/*@NotNull(message="Start Time can not be null")
	@Pattern(regexp = "[^(?:(?:([01]?d|2[0-3]):)?([0-5]?d):)?([0-5]?d)$]+", message = "Start Time should be in HH:MM:SS format")*/
	private Time startTime;
	
	/*@NotNull(message="End Time can not be null")
	@Pattern(regexp = "[^(?:(?:([01]?d|2[0-3]):)?([0-5]?d):)?([0-5]?d)$]+", message = "End Time should be in HH:MM:SS format")*/
	private Time endTime;
	
	
	private String[] daysOfWeek;
	private List<Link> links = new ArrayList<Link>();
	
	public RestScheduleVO(){
	
	}
	/**
	 * @param scheduleId
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param daysOfWeek
	 */
	public RestScheduleVO(int scheduleId, Date startDate, Date endDate, Time startTime, Time endTime,
			String[] daysOfWeek) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.daysOfWeek = daysOfWeek;
	}
	
	/**
	 * @return the scheduleId
	 */
	public int getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}


	
	public Date getStartDate() {
		return startDate; 
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;	
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}


	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
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
