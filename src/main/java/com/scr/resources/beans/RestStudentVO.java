package com.scr.resources.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.core.Link;

import com.scr.vo.CourseVO;
import com.scr.vo.StudentVO;

public class RestStudentVO {
	
private int studentId;
	
	//@NotNull(message="First Name can not be empty")
	//@Pattern(regexp = "[a-zA-Z]+", message = "Only aplphabets are allowed in First Name")
	private String firstName ;
	
	//@NotNull(message="Last Name can not be empty")
	//@Pattern(regexp = "[a-zA-Z]+", message = "Only aplphebets are allowed in Last Name")
	private String lastName ;
	
	@NotNull(message="Email can not be empty")
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,}+", message = "Invalid email format. Note: Upper case alphabets are not allowed")
	private String emailId ;
	
	//@NotNull(message="Password can not be empty")
	//@Pattern(regexp = "[a-zA-Z0-9.@_*$]{4,20}+", message = "Invalid Password pattern. Note: Password length should be between 5-20")
	private String password ;
	
	private String userFlag;
	private List<CourseVO> courseList;
	private List<Link> links = new ArrayList<Link>();
	
	public RestStudentVO(){
		
	}
	
	public RestStudentVO(int studentID, String firstName, String lastName, String emailId, String password, String userFlag) {
		super();
		this.studentId = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.userFlag = userFlag;
		
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @param userFlag
	 * @param courseList
	 */
	public RestStudentVO(int studentId, String firstName, String lastName, String emailId, String password, String userFlag,
			List<CourseVO> courseList) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.userFlag = userFlag;
		this.courseList = courseList;
	}
	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}
	
	/**
	 * @return the courseList
	 */
	public List<CourseVO> getCourseList() {
		return courseList;
	}
	/**
	 * @param courseList the courseList to set
	 */
	public void setCourseList(List<CourseVO> courseList) {
		this.courseList = courseList;
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
		return "Student Is: " + studentId + "Student Name: "+firstName +" "+lastName +" email: "+emailId;
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
