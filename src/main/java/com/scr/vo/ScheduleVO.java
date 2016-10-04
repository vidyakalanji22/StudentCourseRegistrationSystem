package com.scr.vo;

import java.sql.Date;
import java.sql.Time;


public class ScheduleVO {
	private int scheduleId;
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String[] daysOfWeek;

	/**
	 * 
	 */
	public ScheduleVO() {
		super();
	}

	/**
	 * @param scheduleId
	 */
	public ScheduleVO(int scheduleId) {
		super();
		this.scheduleId = scheduleId;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 */
	public ScheduleVO(Date startDate, Date endDate, Time startTime, Time endTime) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ScheduleVO(Date startDate, Date endDate, Time startTime, Time endTime, String[] daysOfWeek) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.daysOfWeek = daysOfWeek;
	}
	/**
	 * @param scheduleId
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param daysOfWeek
	 */
	public ScheduleVO(int scheduleId, Date startDate, Date endDate, Time startTime, Time endTime,
			String[] daysOfWeek) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.daysOfWeek = daysOfWeek;
	}

	public boolean isDayExists(String day) {
		Boolean isDayExists = false;
		for (String dayOfWeek : daysOfWeek) {
			if (dayOfWeek.equalsIgnoreCase(day))
				isDayExists = true;
		}
		return isDayExists;
	}


	/**
	 * @param scheduleId
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 */
	public ScheduleVO(int scheduleId, Date startDate, Date endDate, Time startTime, Time endTime) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
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

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the startTime
	 */
	public Time getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Time getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the daysOfWeek
	 */
	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}

	/**
	 * @param daysOfWeek the daysOfWeek to set
	 */
	public void setDaysOfWeek(String[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" Course starts from : "+startDate.toString());
		stringBuffer.append(" to : "+endDate.toString());
		stringBuffer.append(" at : "+startTime.toString());
		stringBuffer.append(" to : "+endTime.toString());
		stringBuffer.append(" on : "+daysOfWeek(daysOfWeek));
		stringBuffer.append(" scheduleId "+scheduleId);
		return stringBuffer.toString();
	}

	public String daysOfWeek(String[] daysOfWeek){
		StringBuffer buf = new StringBuffer();
		if(null!=daysOfWeek){
			for(String day: daysOfWeek){
				buf.append(day);
			}
		}
		return buf.toString();
	}

}
