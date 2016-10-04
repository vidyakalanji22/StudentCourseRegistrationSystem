package com.scr.vo;

import java.util.List;

public class CourseVO {
	private int courseId;
	private String courseName;
	private int courseAmount;
	private String courseDesc;
	private List<ScheduleVO> scheduleList;
	private List<BookVO> booksList;
	private List<CurriculumVO> curriculumList;
	
	public CourseVO(){
		
	}

	/**
	 * @param courseId
	 * @param courseName
	 */
	public CourseVO(int courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}

	/**
	 * @param courseId
	 * @param courseName
	 * @param courseAmount
	 * @param courseDesc
	 */
	public CourseVO(int courseId, String courseName, int courseAmount, String courseDesc) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseAmount = courseAmount;
		this.courseDesc = courseDesc;
	}

	/**
	 * @param courseId
	 * @param courseName
	 * @param courseAmount
	 * @param courseDesc
	 * @param scheduleList
	 * @param booksList
	 * @param curriculumList
	 */
	public CourseVO(int courseId, String courseName, int courseAmount, String courseDesc,
			List<ScheduleVO> scheduleList, List<BookVO> booksList, List<CurriculumVO> curriculumList) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseAmount = courseAmount;
		this.courseDesc = courseDesc;
		this.scheduleList = scheduleList;
		this.booksList = booksList;
		this.curriculumList = curriculumList;
	}

	/**
	 * @return the courseId
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the courseAmount
	 */
	public int getCourseAmount() {
		return courseAmount;
	}

	/**
	 * @param courseAmount the courseAmount to set
	 */
	public void setCourseAmount(int courseAmount) {
		this.courseAmount = courseAmount;
	}

	/**
	 * @return the courseDesc
	 */
	public String getCourseDesc() {
		return courseDesc;
	}

	/**
	 * @param courseDesc the courseDesc to set
	 */
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}

	/**
	 * @return the scheduleList
	 */
	public List<ScheduleVO> getScheduleList() {
		return scheduleList;
	}

	/**
	 * @param scheduleList the scheduleList to set
	 */
	public void setScheduleList(List<ScheduleVO> scheduleList) {
		this.scheduleList = scheduleList;
	}

	/**
	 * @return the booksList
	 */
	public List<BookVO> getBooksList() {
		return booksList;
	}

	/**
	 * @param booksList the booksList to set
	 */
	public void setBooksList(List<BookVO> booksList) {
		this.booksList = booksList;
	}

	/**
	 * @return the curriculumList
	 */
	public List<CurriculumVO> getCurriculumList() {
		return curriculumList;
	}

	/**
	 * @param curriculumList the curriculumList to set
	 */
	public void setCurriculumList(List<CurriculumVO> curriculumList) {
		this.curriculumList = curriculumList;
	}


}
