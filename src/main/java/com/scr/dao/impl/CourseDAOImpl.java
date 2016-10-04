package com.scr.dao.impl;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.CourseDAO;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

public class CourseDAOImpl implements CourseDAO {

	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * Lists all courses which are enabled
	 * @return List<CourseInfoVO> 
	 */
	public List<CourseVO> listAllCourse(){

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CourseVO> courseInfoList =null;
		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.list.all"));
			rs = pstmt.executeQuery();

			courseInfoList = new ArrayList<CourseVO>();
			while(rs.next()){
				courseInfoList.add(new CourseVO(rs.getInt(Constants.COURSE_ID), rs.getString(Constants.COURSE_NAME), 
						rs.getInt(Constants.COURSE_AMOUNT), rs.getString(Constants.COURSE_DESC)));
			}
			if(courseInfoList.isEmpty()){
				throw new DataNotFoundException("No Courses found");
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in listAllCourse() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in listAllCourse() " + exp.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, rs);
		}
		return courseInfoList;
	}

	/**
	 * Gets details of the particular course.
	 * @param courseId
	 * @return List<CoursesVO>
	 */
	public CourseVO getCourseDetails(int courseId){

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CourseVO courseInfo= null;
		try {
			conn = DBConnectionManager.getConnection();

			courseInfo =  getCourseInfo(conn, courseId);
			if(courseInfo!=null){
				List<ScheduleVO> courseSchedule = getCourseSchedule(conn, courseId);  
				List<CurriculumVO> courseCurriculum = getCourseCurriculum(conn, courseId); 
				List<BookVO> courseBooks = getCourseBooks(conn, courseId);  

				courseInfo.setBooksList(courseBooks);
				courseInfo.setCurriculumList(courseCurriculum);
				courseInfo.setScheduleList(courseSchedule);
			}
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseDetails() " + sqlExp.getMessage());
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseDetails() "+e.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, rs);
		}
		return courseInfo;
	}

	/**
	 * Gets the Course Information
	 * @param conn
	 * @param courseId
	 * @return CourseInfoVO
	 */
	private CourseVO getCourseInfo(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CourseVO courseInfo = null;

		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.info"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				courseInfo = new CourseVO(rs.getInt(Constants.COURSE_ID), rs.getString(Constants.COURSE_NAME), rs.getInt(Constants.COURSE_AMOUNT), rs.getString(Constants.COURSE_DESC));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseInfo() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseInfo() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseInfo;
	}

	/**
	 * Gets the course schedule
	 * @param conn
	 * @param courseId
	 * @return List<ScheduleVO>
	 */
	private List<ScheduleVO> getCourseSchedule(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> courseSchedule = null;

		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.schedule"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseSchedule =new ArrayList<ScheduleVO>();
			while(rs.next()){
				String[] daysOfWeek = (String[])rs.getArray(Constants.DAYS_OF_WEEK).getArray();
				courseSchedule.add(new ScheduleVO(rs.getInt(Constants.SCHEDULE_ID), rs.getDate(Constants.START_DATE), rs.getDate(Constants.END_DATE), rs.getTime(Constants.START_TIME), rs.getTime(Constants.END_TIME),daysOfWeek));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseSchedule() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseSchedule;
	}
	
	public List<ScheduleVO> getCourseSchedule(int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScheduleVO> courseSchedule = null;
		Connection conn = null;

		try{
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.schedule"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseSchedule =new ArrayList<ScheduleVO>();
			while(rs.next()){
				String[] daysOfWeek = (String[])rs.getArray(Constants.DAYS_OF_WEEK).getArray();
				courseSchedule.add(new ScheduleVO(rs.getInt(Constants.SCHEDULE_ID), rs.getDate(Constants.START_DATE), rs.getDate(Constants.END_DATE), rs.getTime(Constants.START_TIME), rs.getTime(Constants.END_TIME),daysOfWeek));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseSchedule() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseSchedule;
	}

	/**
	 * Gets the Course Curriculum
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	private List<CurriculumVO> getCourseCurriculum(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CurriculumVO> courseCurriculum = null;

		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.curriculum"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseCurriculum =new ArrayList<CurriculumVO>();
			while(rs.next()){
				courseCurriculum.add(new CurriculumVO(rs.getInt(Constants.CURRICULUM_ID), rs.getString(Constants.TOPIC_NAME)));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseCurriculum() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseCurriculum() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseCurriculum;
	}
	
	/**
	 * Gets the Course Curriculum
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CurriculumVO> courseCurriculum = null;
		Connection conn = null;
		
		try{
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.curriculum"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseCurriculum =new ArrayList<CurriculumVO>();
			while(rs.next()){
				courseCurriculum.add(new CurriculumVO(rs.getInt(Constants.CURRICULUM_ID), rs.getString(Constants.TOPIC_NAME)));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseCurriculum() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseCurriculum() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseCurriculum;
	}

	/**
	 * Gets the Course Books
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	private List<BookVO> getCourseBooks(Connection conn, int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BookVO> courseBooks = null;

		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.books"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseBooks =new ArrayList<BookVO>();
			while(rs.next()){
				courseBooks.add(new BookVO(rs.getInt(Constants.BOOK_ID), rs.getString(Constants.BOOK_NAME)));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseBooks() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseBooks() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseBooks;
	}
	
	/**
	 * Gets the Course Books
	 * @param conn
	 * @param courseId
	 * @return List<String>
	 */
	public List<BookVO> getCourseBooks( int courseId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BookVO> courseBooks = null;
		Connection conn = null;

		try{
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.get.books"));
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			courseBooks =new ArrayList<BookVO>();
			while(rs.next()){
				courseBooks.add(new BookVO(rs.getInt(Constants.BOOK_ID), rs.getString(Constants.BOOK_NAME)));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseBooks() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseBooks() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseBooks;
	}

	/**
	 * Disables the course
	 * @param courseId
	 * @return String 
	 */
	public String disableCourse(int courseId){

		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sbuffStatus = new StringBuffer();

		try {
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.disable"));
			pstmt.setInt(1,courseId);

			int status = pstmt.executeUpdate();
			//Returns 1 if any rows updated, returns 0 if no rows updated
			if(status>0)
				sbuffStatus.append("successfully disabled the course : ").append(courseId);
			else if(status==0)
				sbuffStatus.append("No course with courseId : ").append(courseId).append(" available to disable");

		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in disableCourse() " + sqlExp.getMessage());
		}catch (Exception e) {
			System.out.println("Exception occurred in getCourseInfo() "+e.getMessage());
			sbuffStatus.append("An error occurred while disabling the course");
		}finally{
			DBConnectionManager.close(conn, pstmt, null);
		}

		return sbuffStatus.toString();
	}


	/**
	 * Adds a course and all the corresponding information like schedule, curriculum, books.
	 * @param courseVo
	 * @return String
	 * @throws SQLException
	 */
	public CourseVO addCourse(CourseVO courseVo) throws ResourceAlreadyExistsException, ResourceNotCreatedException{

		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage ="";
		try {
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);

			int courseId = insertCourseInfo(conn, courseVo);
			System.out.println("Course ID : " + courseId);
			boolean insertCourseScheduleStatus = insertCourseSchedule(conn, courseId, courseVo.getScheduleList());
			boolean insertCourseCurriculumStatus = insertCourseCurriculum(conn, courseId, courseVo.getCurriculumList());
			boolean insertCourseBookStatus = insertCourseBook(conn, courseId, courseVo.getBooksList());

			if(insertCourseScheduleStatus && insertCourseCurriculumStatus && insertCourseBookStatus){
				conn.commit();
				courseVo.setCourseId(courseId);
				statusMessage= "Successfully created course : "+courseVo.getCourseName();
			}else{
				conn.rollback();
				courseVo.setCourseId(0);
				statusMessage = "Unable to create the course due to some issues";
				throw new ResourceNotCreatedException(statusMessage);
			}

		} catch(SQLException sqlExp){
			System.out.println("SQLException occurred in addCourse() " + sqlExp.getMessage());
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			statusMessage = "An error occurred while creating the course";
			System.out.println("Exception occurred in addCourse() "+e.getMessage());
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		System.out.println(statusMessage);
		return courseVo;
	}

	/**
	 * Insert the Course information
	 * @param conn
	 * @param couseInfo
	 * @return int
	 * @throws Exception
	 */
	private int insertCourseInfo(Connection conn, CourseVO couseInfo) throws ResourceAlreadyExistsException, Exception {

		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int courseId=0;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.info"),PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, couseInfo.getCourseName());
			pstmt.setInt(2, couseInfo.getCourseAmount());
			pstmt.setString(3, couseInfo.getCourseDesc());
			pstmt.setString(4, "Y"); 
			int status = pstmt.executeUpdate();
			if(status>0){
				rs = pstmt.getGeneratedKeys();
				if (rs.next()){
					courseId = rs.getInt(Constants.COURSE_ID);
				}
			}else{
				throw new ResourceAlreadyExistsException("Course already exceptions");
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseInfo() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseInfo() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, rs);
		}
		return courseId;
	}

	/**
	 * Adds the schedule for a course
	 * @param conn
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */

	private boolean insertCourseSchedule(Connection conn, int courseId, List<ScheduleVO> courseSchList) throws Exception {
		PreparedStatement pstmt = null;
		boolean success = false;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.schedule"));
			for(ScheduleVO scheduleVo : courseSchList){
				System.out.println("courseId : "+courseId);
				pstmt.setInt(1, courseId);
				pstmt.setInt(2,scheduleVo.getScheduleId());
				pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseSchedule() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}

	/**
	 * Inserts curriculum for a course
	 * @param conn
	 * @param courseId
	 * @param curriculumList
	 * @return boolean 
	 * @throws Exception
	 */
	private boolean insertCourseCurriculum(Connection conn, int courseId, List<CurriculumVO> curriculumList) throws Exception {
		PreparedStatement pstmt = null;
		boolean success = false;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.curriculum"));
			for(CurriculumVO curriculumVo : curriculumList){
				pstmt.setInt(1, courseId);
				pstmt.setInt(2, curriculumVo.getCurriculumId());
				pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseCurriculum() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseCurriculum() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}

	/**
	 * Inserts books for a course
	 * @param conn
	 * @param courseId
	 * @param booksList
	 * @return boolean
	 * @throws Exception
	 */
	private boolean insertCourseBook(Connection conn, int courseId, List<BookVO> booksList) throws Exception {
		PreparedStatement pstmt = null;
		boolean success = false;
		try{
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.insert.book"));
			for(BookVO bookVo : booksList){
				pstmt.setInt(1, courseId);
				pstmt.setInt(2, bookVo.getBookID());
				pstmt.addBatch();
			}
			int count[] = pstmt.executeBatch();
			if(count.length > 0)
				success =true;
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in insertCourseBook() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in insertCourseBook() "+exp.getMessage());
			throw exp;
		}finally{
			DBConnectionManager.close(null, pstmt, null);
		}
		return success;
	}

	/**
	 * Updates the Course information
	 *
	 */

	public String updateCourse(int courseId, CourseVO courseVo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.update.course_info"));
			pstmt.setString(1, courseVo.getCourseName());
			pstmt.setInt(2, courseVo.getCourseAmount());
			pstmt.setString(3, courseVo.getCourseDesc());
			pstmt.setInt(4, courseId);

			int count = pstmt.executeUpdate();
			if(count > 0)
				statusMessage ="Successfully updated the course details";

		}catch(Exception exp){
			statusMessage = "An error occured while updating the details";
			System.out.println("Exception occurred in updateCourseInfo() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the course schedule
	 */
	public String updateCourseSchedule(int courseId, List<ScheduleVO> courseSchList) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.schedule"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();

			if(status>0){
				boolean insertSchstatus = insertCourseSchedule(conn, courseId, courseSchList);
				if(insertSchstatus){
					conn.commit();
					statusMessage ="Successfully updated the schedule";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseSchedule() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the course curriculum
	 */
	public String updateCourseCurriculum(int courseId, List<CurriculumVO> curriculumList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.curriculum"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();
			if(status>0){
				boolean insertCurrStatus = insertCourseCurriculum(conn, courseId, curriculumList);
				if(insertCurrStatus){
					conn.commit();
					statusMessage ="Successfully updated the Curriculum";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseCurriculum() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * Updates the course book
	 */
	public String updateCourseBook(int courseId, List<BookVO> booksList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String statusMessage = "";
		try{
			conn = DBConnectionManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(dbQueries.getProperty("course.delete.book"));
			pstmt.setInt(1, courseId);
			int status = pstmt.executeUpdate();
			if(pstmt!=null)
				pstmt.close();

			if(status>0 || (status == 0 && !booksList.isEmpty())){
				boolean insertBookStatus = insertCourseBook(conn, courseId, booksList);
				if(insertBookStatus){
					conn.commit();
					statusMessage ="Successfully updated the books";
				}
			}
		}catch(Exception exp){
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Exception occurred in updateCourseBook() "+exp.getMessage());
			statusMessage = "An error occured while updating the details";
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(conn, pstmt, null);
		}
		return statusMessage;
	}

	/**
	 * 
	 */
	public List<CourseVO> getDateRangeCourseList(String startDate, String endDate){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CourseVO> coursesList =null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.list.date.range"));
			preparedStatement.setString(1,startDate);
			preparedStatement.setString(2, endDate);
			resultSet = preparedStatement.executeQuery();

			coursesList = prepareCourseList(resultSet);

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getDateRangeCourseList() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getDateRangeCourseList() " + exp.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return coursesList;
	}

	public List<CourseVO> getTimeRangeCourseList(Time startTime, Time endTime){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CourseVO> coursesList =null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.list.time.range"));
			preparedStatement.setTime(1, startTime);
			preparedStatement.setTime(2, endTime);
			resultSet = preparedStatement.executeQuery();

			coursesList = prepareCourseList(resultSet);

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getDateRangeCourseList() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getDateRangeCourseList() " + exp.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return coursesList;
	}

	public List<CourseVO> getAmountRangeCourseList(int startAmount, int endAmount){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CourseVO> coursesList =new ArrayList<>();
		CourseVO courseVO = null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.list.amount.range"));
			preparedStatement.setInt(1, startAmount);
			preparedStatement.setInt(2, endAmount);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				courseVO = new CourseVO(resultSet.getInt(Constants.COURSE_ID), resultSet.getString(Constants.COURSE_NAME), 
						resultSet.getInt(Constants.COURSE_AMOUNT), resultSet.getString(Constants.COURSE_DESC));
				coursesList.add(courseVO);
			}
		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getDateRangeCourseList() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getDateRangeCourseList() " + exp.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return coursesList;
	}

	public List<CourseVO> getDayRangeCourseList(String[] days){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CourseVO> coursesList =null;
		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.list.day.range"));
			Array daysArray = connection.createArrayOf("text", days);
			preparedStatement.setArray(1, daysArray);
			resultSet = preparedStatement.executeQuery();
			coursesList = prepareCourseList(resultSet);

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getDateRangeCourseList() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getDateRangeCourseList() " + exp.getMessage());
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return coursesList;
	}

	private List<CourseVO> prepareCourseList(ResultSet resultSet) throws Exception{
		List<CourseVO> coursesList = new ArrayList<CourseVO>();
		ScheduleVO scheduleVO=null;
		List<ScheduleVO> scheduleList = null;
		CourseVO courseVO = null;
		int courseId=0;
		while(resultSet.next()){
			Array days = resultSet.getArray(Constants.DAYS_OF_WEEK);
			String[] daysOfWeek=null;
			if(days!=null){
				daysOfWeek = (String[])days.getArray();
			}
			scheduleVO = new ScheduleVO(resultSet.getInt(Constants.SCHEDULE_ID), resultSet.getDate(Constants.START_DATE), resultSet.getDate(Constants.END_DATE), resultSet.getTime(Constants.START_TIME), resultSet.getTime(Constants.END_TIME), daysOfWeek);
			if(courseId==0){
				scheduleList = new ArrayList<ScheduleVO>();
				courseId = resultSet.getInt(Constants.COURSE_ID);
				courseVO = new CourseVO(courseId, resultSet.getString(Constants.COURSE_NAME), 
						resultSet.getInt(Constants.COURSE_AMOUNT), resultSet.getString(Constants.COURSE_DESC));
				scheduleList.add(scheduleVO);
			}else if(courseId != resultSet.getInt(Constants.COURSE_ID)){
				//If course ID changes, add the schedule list of earlier course to courseVO
				//Add course VO to courseList
				courseVO.setScheduleList(scheduleList);
				coursesList.add(courseVO);
				scheduleList = new ArrayList<ScheduleVO>();

				courseId = resultSet.getInt(Constants.COURSE_ID);
				courseVO = new CourseVO(resultSet.getInt(Constants.COURSE_ID), resultSet.getString(Constants.COURSE_NAME), 
						resultSet.getInt(Constants.COURSE_AMOUNT), resultSet.getString(Constants.COURSE_DESC));
				scheduleList.add(scheduleVO);
			}else{
				scheduleList.add(scheduleVO);
			}

		}
		if(courseVO!=null){
			if(scheduleList!=null && !scheduleList.isEmpty())
				courseVO.setScheduleList(scheduleList);
			coursesList.add(courseVO);
		}
		return coursesList;
	}

}
