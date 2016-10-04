package com.scr.resources.beans;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

import com.scr.vo.BookVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;
import com.scr.vo.StudentVO;

@XmlRootElement
public class RestCourseVO {
	
	private int courseId;
	@NotNull
	private @QueryParam("courseName") String courseName;
	@Min(0)
	private @QueryParam("courseAmount") int courseAmount;
	private @QueryParam("courseDesc") String courseDesc;
	private List<ScheduleVO> scheduleList;
	private List<BookVO> booksList;
	private List<CurriculumVO> curriculumList;
	private List<Link> links = new ArrayList<Link>();

	public RestCourseVO() {
		// TODO Auto-generated constructor stub
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
	public RestCourseVO(int courseId, String courseName, int courseAmount, String courseDesc,
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
	
	@Override
	public String toString(){
		return "courseId: " + courseId + "courseDesc: "+courseName +" "+courseDesc +" courseAmount: "+courseAmount;
	}
	
	@Override
	public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof StudentVO))
            return false;
        if (obj == this)
            return true;
        return this.toString().equals(obj.toString());
        
    }


}
