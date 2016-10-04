package com.scr.service.impl;

import java.sql.Time;
import java.util.List;

import com.scr.dao.CourseDAO;
import com.scr.dao.impl.CourseDAOImpl;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.service.CourseService;
import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public class CourseServiceImpl implements CourseService{
	private CourseDAO courseDAO = new CourseDAOImpl();

	public CourseServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */
	public List<CourseVO> listAllCourse(){
		List<CourseVO> courseVOs = courseDAO.listAllCourse();
		if(courseVOs==null){
			throw new DataNotFoundException("No Courses Found");
		}else if(courseVOs!=null && courseVOs.isEmpty()){
			throw new DataNotFoundException("No Courses Found");
		}
		return courseVOs;
	}

	/**
	 * This method fetches courses between a date range
	 */
	public List<CourseVO> getDateRangeCourseList(String startDate, String endDate){
		List<CourseVO> courseVOs = courseDAO.getDateRangeCourseList(startDate, endDate);
		if(courseVOs==null || (courseVOs!=null && courseVOs.isEmpty())){
			throw new DataNotFoundException("No Courses Found");
		}
		return courseVOs;
	}

	/**
	 * This method fetches courses based on time range
	 */
	public List<CourseVO> getTimeRangeCourseList(Time startTime, Time endTime){
		List<CourseVO> courseVOs = courseDAO.getTimeRangeCourseList(startTime, endTime);
		if(courseVOs==null){
			throw new DataNotFoundException("No Courses Found");
		}else if(courseVOs!=null && courseVOs.isEmpty()){
			throw new DataNotFoundException("No Courses Found");
		}
		return courseVOs;
	}

	/**
	 * This method fetches courses between amount range
	 */
	public List<CourseVO> getAmountRangeCourseList(int startAmount, int endAmount){
		List<CourseVO> courseVOs = courseDAO.getAmountRangeCourseList(startAmount, endAmount);
		if(courseVOs==null){
			throw new DataNotFoundException("No Courses Found");
		}else if(courseVOs!=null && courseVOs.isEmpty()){
			throw new DataNotFoundException("No Courses Found");
		}
		return courseVOs;
	}

	/**
	 * This method fetches courses based on days of week
	 */
	public List<CourseVO> getDayRangeCourseList(String[] days){
		List<CourseVO> courseVOs = courseDAO.getDayRangeCourseList(days);
		if(courseVOs==null){
			throw new DataNotFoundException("No Courses Found");
		}else if(courseVOs!=null && courseVOs.isEmpty()){
			throw new DataNotFoundException("No Courses Found");
		}
		return courseVOs;
	}

	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return List<CoursesVO>
	 */
	public CourseVO getCourseDetails(int courseId){
		CourseVO courseVO = null;
		courseVO = courseDAO.getCourseDetails(courseId);
		if(courseVO == null){
			throw new DataNotFoundException("No Courses Found with Id "+courseId);
		}
		return courseVO;
	}

	/**
	 * Gets the Course Curriculum
	 * @param courseId
	 * @return List<CurriculumVO>
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseId){
		List<CurriculumVO> curriculumVOs = courseDAO.getCourseCurriculum(courseId);
		if(curriculumVOs==null ||(curriculumVOs!=null && curriculumVOs.isEmpty())){
			throw new DataNotFoundException("Course curriculum not found");
		}
		return curriculumVOs;
	}
	
	/**
	 * This method fetches books related to a course
	 */
	public List<BookVO> getCourseBooks(int courseId){
		List<BookVO> bookVOs = courseDAO.getCourseBooks(courseId);
		if(bookVOs==null ||(bookVOs!=null && bookVOs.isEmpty())){
			throw new DataNotFoundException("Course books not found");
		}
		return bookVOs;
	}
	
	/**
	 * This method fetches schedule for a course
	 */
	public List<ScheduleVO> getCourseSchedule(int courseId){
		List<ScheduleVO> scheduleVOs = courseDAO.getCourseSchedule(courseId);
		if(scheduleVOs==null ||(scheduleVOs!=null && scheduleVOs.isEmpty())){
			throw new DataNotFoundException("Course schedule not found");
		}
		return scheduleVOs;
	}

	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws DataNotFoundException
	 */
	public CourseVO addCourse(CourseVO courseVo){
		courseVo = courseDAO.addCourse(courseVo);
		if(courseVo!=null && courseVo.getCourseId()>0){
			return courseVo;
		}else{
			throw new ResourceNotCreatedException("Cannot add Course");
		}

	}

	/**
	 * Updates the Course information
	 *
	 */
	public String updateCourse(int courseId, CourseVO courseVO) {
		String result = null;
		result = courseDAO.updateCourse(courseId, courseVO);
		if(courseVO.getBooksList()!=null && courseVO.getBooksList().size()>0){
			result = courseDAO.updateCourseBook(courseId, courseVO.getBooksList());
		}
		if(courseVO.getScheduleList()!=null && courseVO.getScheduleList().size()>0){
			result = courseDAO.updateCourseSchedule(courseId, courseVO.getScheduleList());
		}
		if(courseVO.getCurriculumList()!=null && courseVO.getCurriculumList().size()>0){
			result = courseDAO.updateCourseCurriculum(courseId, courseVO.getCurriculumList());
		}
		return result;
	}

	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String disableCourse(int courseId){
		String result = courseDAO.disableCourse(courseId);
		return result;
	}

}
