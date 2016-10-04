package com.scr.resources.beans;

import javax.ws.rs.QueryParam;

public class CourseFilterBean {

	@QueryParam("startDate") 
	private String startDate;
	@QueryParam("endDate") private String endDate;
	@QueryParam("startAmount") 
	private int startAmount;
	@QueryParam("endAmount")
	private int endAmount;
	@QueryParam("days") 
	private String[] days;
	@QueryParam("start")
	private int start;
	@QueryParam("size")
	private int size;
	
	public CourseFilterBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @param startAmount
	 * @param endAmount
	 * @param days
	 */
	public CourseFilterBean(String startDate, String endDate, int startAmount, int endAmount, String[] days) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startAmount = startAmount;
		this.endAmount = endAmount;
		this.days = days;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startAmount
	 */
	public int getStartAmount() {
		return startAmount;
	}

	/**
	 * @param startAmount the startAmount to set
	 */
	public void setStartAmount(int startAmount) {
		this.startAmount = startAmount;
	}

	/**
	 * @return the endAmount
	 */
	public int getEndAmount() {
		return endAmount;
	}

	/**
	 * @param endAmount the endAmount to set
	 */
	public void setEndAmount(int endAmount) {
		this.endAmount = endAmount;
	}

	/**
	 * @return the days
	 */
	public String[] getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(String[] days) {
		this.days = days;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
