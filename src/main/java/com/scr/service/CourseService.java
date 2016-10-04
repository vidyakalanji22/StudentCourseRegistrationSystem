package com.scr.service;

import java.sql.Time;
import java.util.List;

import com.scr.exception.DataNotFoundException;
import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public interface CourseService {

	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */
	public List<CourseVO> listAllCourse();

	/**
	 * This method fetches courses between a date range
	 */
	public List<CourseVO> getDateRangeCourseList(String startDate, String endDate);

	/**
	 * This method fetches courses based on time range
	 */
	public List<CourseVO> getTimeRangeCourseList(Time startTime, Time endTime);

	/**
	 * This method fetches courses between amount range
	 */
	public List<CourseVO> getAmountRangeCourseList(int startAmount, int endAmount);

	/**
	 * This method fetches courses based on days of week
	 */
	public List<CourseVO> getDayRangeCourseList(String[] days);

	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return List<CoursesVO>
	 */
	public CourseVO getCourseDetails(int courseId);


	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws DataNotFoundException
	 */
	public CourseVO addCourse(CourseVO courseVo);


	/**
	 * Updates the Course information
	 *
	 */
	public String updateCourse(int courseId, CourseVO courseVO);


	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String disableCourse(int courseId);

	/**
	 * Gets the Course Curriculum
	 * @param courseId
	 * @return List<CurriculumVO>
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseId);

	/**
	 * This method fetches books related to a course
	 */
	public List<BookVO> getCourseBooks(int courseId);

	/**
	 * This method fetches schedule for a course
	 */
	public List<ScheduleVO> getCourseSchedule(int courseId);
}
