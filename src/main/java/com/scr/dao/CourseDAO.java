package com.scr.dao;

import java.sql.Time;
import java.util.List;

import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public interface CourseDAO {

	List<CourseVO> listAllCourse();

	CourseVO getCourseDetails(int courseId);
	
	String disableCourse(int courseId);
	
	CourseVO addCourse(CourseVO courseVo);
	
	String updateCourse(int courseId, CourseVO courseVo);
	
	String updateCourseSchedule(int courseId,  List<ScheduleVO> courseSchList);
	
	String updateCourseCurriculum(int courseId, List<CurriculumVO> curriculumList);
	
	String updateCourseBook(int courseId, List<BookVO> booksList);
	
	public List<CourseVO> getDateRangeCourseList(String startDate,  String endDate);
	
	public List<CourseVO> getDayRangeCourseList(String[] days);
	
	public List<CourseVO> getTimeRangeCourseList(Time startTime, Time endTime);
	
	public List<CourseVO> getAmountRangeCourseList(int startAmount, int endAmount);
	/**
	 * Gets the Course Curriculum
	 * @param courseId
	 * @return List<String>
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseId);
	
	/**
	 * Gets the Course Books
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	public List<BookVO> getCourseBooks( int courseId);
	
	/**
	 * This method fetches course schedule
	 * @param courseId
	 * @return List<ScheduleVO>
	 */
	public List<ScheduleVO> getCourseSchedule(int courseId);

}