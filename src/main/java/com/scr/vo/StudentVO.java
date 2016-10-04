/**
 * 
 */
package com.scr.vo;

import java.util.Date;
import java.util.List;

public class StudentVO {
	
	private int studentId;
	private String firstName ;
	private String lastName ;
	private String emailId ;
	private String password ;
	private String userFlag;
	private List<CourseVO> courseList;
	private Date lastModified;
	
	
	/**
	 * 
	 */
	public StudentVO() {
		super();
	}
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @param userFlag
	 */
	public StudentVO(String firstName, String lastName, String emailId, String password, String userFlag) {
		super();
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
	public StudentVO(String firstName, String lastName, String emailId, String password, String userFlag,
			List<CourseVO> courseList) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.userFlag = userFlag;
		this.courseList = courseList;
	}
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 */
	public StudentVO(int studentID, String firstName, String lastName, String emailId, String password, String userFlag) {
		super();
		this.studentId = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.userFlag = userFlag;
	}
	
	/**
	 * @param studentId
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @param userFlag
	 * @param courseList
	 * @param lastModified
	 */
	public StudentVO(int studentId, String firstName, String lastName, String emailId, String userFlag,
			List<CourseVO> courseList, Date lastModified) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.userFlag = userFlag;
		this.courseList = courseList;
		this.lastModified = lastModified;
	}
	
	/**
	 * @param studentId
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param userFlag
	 * @param lastModified
	 */
	public StudentVO(int studentId, String firstName, String lastName, String emailId, String userFlag,
			Date lastModified) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.userFlag = userFlag;
		this.lastModified = lastModified;
	}
	/**
	 * @param studentId
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @param userFlag
	 * @param lastModified
	 */
	public StudentVO(int studentId, String firstName, String lastName, String emailId, String password, String userFlag,
			Date lastModified) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.userFlag = userFlag;
		this.lastModified = lastModified;
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
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the user_flag
	 */
	public String getUserFlag() {
		return userFlag;
	}
	/**
	 * @param user_flag the user_flag to set
	 */
	public void setUserFlag(String user_flag) {
		this.userFlag = user_flag;
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
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String toString(){
		return "Student Is: " + studentId + "Student Name: "+firstName +" "+lastName +" email: "+emailId;
	}
	
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
