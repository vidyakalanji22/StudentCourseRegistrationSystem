/**
 * 
 */
package com.scr.dao.impl;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.StudentsDAO;
import com.scr.exception.ConstrainViolationException;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.GenericException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.util.CommonUtils;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.CourseVO;
import com.scr.vo.ScheduleVO;
import com.scr.vo.StudentVO;


public class StudentDAOImpl implements StudentsDAO{
	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method adds student details to STUDENT table
	 * 
	 * @param studentVO
	 *            - studentVO contains student details which needs to be
	 *            persisted
	 * @return statusMessage specifying success or failure
	 * @throws Exception 
	 */
	@Override
	public StudentVO createStudent(StudentVO studentVO) throws ResourceAlreadyExistsException, ResourceNotCreatedException, GenericException{
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		int index = 0;
		int studentId = 0;
		ResultSet rs = null;
		try {
			connection = DBConnectionManager.getConnection(); 
			connection.setAutoCommit(false);
			boolean studentExists = checkStudentExists(connection, studentVO.getEmailId());

			//Check if student already exists. If student exists throw exception saying student already exists
			if (studentExists){
				throw new ResourceAlreadyExistsException("Student with emailId: " + studentVO.getEmailId() + " already exists");
			}
			//Prepare insert statement to insert student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.student"), PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(++index, studentVO.getFirstName());
			preparedStatement.setString(++index, studentVO.getLastName());
			preparedStatement.setString(++index, studentVO.getEmailId());
			preparedStatement.setString(++index, studentVO.getPassword());
			preparedStatement.setString(++index, Constants.USER_STUDENT);
			preparedStatement.setTimestamp(++index, CommonUtils.convertUtilDateToSqlDate(new java.util.Date()));
		
			int result = preparedStatement.executeUpdate(); 	//execute the insert statement
			connection.commit();
			if(result>0){
				rs = preparedStatement.getGeneratedKeys(); //Get the created studentId
				while(rs.next()){
					studentId= rs.getInt(1);
					System.out.println("Created Student with studentId :" +studentId);
					studentVO.setStudentId(studentId);
				}
			}else{
				throw new ResourceNotCreatedException("Could not create Stundent in the system");
			}
			
		}catch (ResourceAlreadyExistsException raeExp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw raeExp;
		}catch (ResourceNotCreatedException rncExp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw rncExp;
		}catch (Exception exp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			try {
				if(connection!=null)
					connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, rs);
		}
		return studentVO;
	}



	/**
	 * This method checks if student already exists or not
	 * 
	 * @param connection
	 *            - connection to create prepared statement
	 * @param emailId
	 *            - to check the if student with emailid already exists
	 * @return true if student exists else false
	 */
	private boolean checkStudentExists(Connection connection, int studentId) {
		boolean studentExists = false;
		int count = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.student"));  //prepare query to check if student exists
			preparedStatement.setInt(1, studentId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			
			if (count > 0) {
				studentExists = true;
			}else{
				studentExists = false;
				System.out.println("Student with emailId "+studentId +" doesnot exist");
			}
		} catch (Exception exp) {
			System.out.println("Error occured while checking if student exists or not " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentExists;
	}
	
	private boolean checkStudentExists(Connection connection, String email) {
		boolean studentExists = false;
		int count = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.student.email")); //prepare query to check if student exists
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			System.out.println("Count : "+count);
			if (count > 0) {
				studentExists = true;
			}else{
				studentExists = false;
				System.out.println("Student with emailId "+email+" doesnot exist");
			}
		} catch (Exception exp) {
			System.out.println("Error occured while checking if student exists or not " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentExists;
	}


	/**
	 * This method updates student 
	 * @param studentVO Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */
	@Override
	public String updateStudent(StudentVO studentVO) throws DataNotFoundException, GenericException{
		int index = 0;
		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = DBConnectionManager.getConnection(); 	//get connection to DB
			connection.setAutoCommit(false);

			//Prepare insert statement to insert student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.student"));

			//set Student values
			preparedStatement.setString(++index, studentVO.getFirstName());
			preparedStatement.setString(++index, studentVO.getLastName());
			preparedStatement.setString(++index, studentVO.getEmailId());
			preparedStatement.setString(++index, studentVO.getPassword());
			preparedStatement.setString(++index, studentVO.getUserFlag());
			preparedStatement.setTimestamp(++index, CommonUtils.convertUtilDateToSqlDate(new java.util.Date()));
			preparedStatement.setInt(++index, studentVO.getStudentId());
			System.out.println("Updating student using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			int result = preparedStatement.executeUpdate();

			if(result>0){
				statusMessage = Constants.SUCCESS;
			}else{
				statusMessage = Constants.FAILURE;
				throw new DataNotFoundException("Cannot update student as student doesnot exist");
			}
			connection.commit();
		}catch (DataNotFoundException gExp) {
			statusMessage = Constants.FAILURE;
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw gExp;
		}catch (Exception exp) {
			statusMessage = Constants.FAILURE;
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			try {
				if(connection!=null)
					connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * This method deletes student's record from STUDENTS table
	 * @param studentVO
	 * @return status message stating success or failure
	 */
	@Override
	public String deleteStudent(int studentId) throws DataNotFoundException, GenericException, ConstrainViolationException{
		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = DBConnectionManager.getConnection();//get connection to DB
			connection.setAutoCommit(false);
			if(!checkStudentExists(connection, studentId)){
				throw new DataNotFoundException("Cannot delete student as the student does not exist");
			}
			//Prepare DDL statement to delete student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.student"));

			//set emailid
			preparedStatement.setInt(1, studentId);
			System.out.println("Deleting student using DDL statement "+preparedStatement.toString());

			//execute the delete statement
			int result = preparedStatement.executeUpdate();
			connection.commit();
			if(result>0){
				statusMessage = Constants.SUCCESS;
			}else{
				statusMessage = "Unable to delete student";
			}
		}catch (DataNotFoundException dnfExp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw dnfExp;
		}catch (Exception exp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			statusMessage = Constants.FAILURE;
			if(exp.getMessage().contains("violates foreign key constraint")){
				return "Cannot delete student "+studentId +" due to constraint violation";
			}
		}finally {
			try {
				if(connection!=null)
					connection.setAutoCommit(true);
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * This method returns all the students details
	 * @return list of students
	 * @throws Exception 
	 */
	@Override
	public List<StudentVO> getStudents() throws GenericException {
		List<StudentVO> studentsList = new ArrayList<StudentVO>();
		StudentVO studentVO = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = DBConnectionManager.getConnection(); //get connection to DB

			//Prepare DDL statement to delete student details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.students"));
			System.out.println("Lising student details using query "+preparedStatement.toString());

			//execute the select query 
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD), resultSet.getString(Constants.USER_FLAG), resultSet.getTimestamp(Constants.LAST_MODIFIED));
				studentsList.add(studentVO);
			}
		} catch (Exception e) {
			System.out.println("An Exception occurred in getStudents() of StudentDAOImpl() " +e.getMessage());
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return studentsList;
	}

	/**
	 * This method a student's details
	 * @param email - student's email
	 * @return studentVO object 
	 */
	@Override
	public StudentVO getStudentDetails(int studentId) throws DataNotFoundException, GenericException{
		StudentVO studentVO =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.student.details"));
			preparedStatement.setInt(1, studentId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {    
				System.out.println("Student with emailID "+studentId +" doesnot exists "); 
				throw new DataNotFoundException(Constants.NO_STUDENT_MESSAGE);
			} 

			while (resultSet.next()) {
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD), resultSet.getString(Constants.USER_FLAG), resultSet.getTimestamp(Constants.LAST_MODIFIED));
				System.out.println("Student Details "+studentVO.toString());
			}

		}catch (DataNotFoundException dfnExp) {
			System.out.println("DataNotFoundException occurred in getStudentDetails() : " + dfnExp);
			throw dfnExp;
		}  catch (Exception exp) {
			System.out.println("Exception occurred in getStudentDetails() : " + exp);
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentVO;
	}


	/**
	 * This method lists course details which are enrolled by a student
	 * @param email students' email id whose enrolled courses should be listed
	 * @return studentVO contains student details and course details
	 */
	@Override
	public StudentVO getStudentEnrolledCourses(int studentId) throws DataNotFoundException, GenericException{
		StudentVO studentVO = null;
		Connection connection = null;
		List<CourseVO> courseList = null;
		try {
			studentVO = getStudentDetails(studentId);
		
			courseList = getCourseSchedule(studentId);
			if(courseList.isEmpty()){
				throw new DataNotFoundException(Constants.NO_STUDENT_ENROLLMENT);
			}else{
				studentVO.setCourseList(courseList);
			}

		} catch (DataNotFoundException dnfExp) {
			System.out.println("Exception occurred in getStudentEnrolledCourses()" + dnfExp.getMessage());
			throw dnfExp;
		} catch (Exception exp) {
			System.out.println("Exception occurred in getStudentEnrolledCourses.." + exp.getMessage());
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			DBConnectionManager.close(connection, null, null);
		}
		return studentVO;
	}

	/**
	 * This method fetches course schedule details
	 * @param connection
	 * @param emailId
	 * @return
	 */
	private List<CourseVO> getCourseSchedule( int studentId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CourseVO> courseList = null;
		CourseVO courseVO = null;
		int courseId = 0;
		String courseName = null;
		List<ScheduleVO> scheduleList = null;
		ScheduleVO scheduleVO = null;
		Date startDate;
		Date endDate;
		Time startTime;
		Time endTime;
		Connection connection=null;

		try{
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.student.enrolled.courses"));
			preparedStatement.setInt(1, studentId);
			resultSet = preparedStatement.executeQuery();
			courseList = new ArrayList<CourseVO>();
			while (resultSet.next()) {
				courseName = resultSet.getString(Constants.COURSE_NAME);
				startDate = resultSet.getDate(Constants.START_DATE);
				endDate = resultSet.getDate(Constants.END_DATE);
				startTime = resultSet.getTime(Constants.START_TIME);
				endTime = resultSet.getTime(Constants.END_TIME);
				Array days = resultSet.getArray(Constants.DAYS_OF_WEEK);
				String[] daysOfWeek=null;
				if(days!=null){
				 daysOfWeek = (String[])days.getArray();
				}
				scheduleVO = new ScheduleVO(resultSet.getInt(Constants.SCHEDULE_ID), startDate, endDate, startTime, endTime,daysOfWeek);
				if(courseId == 0){
					scheduleList = new ArrayList<ScheduleVO>();
					courseId = resultSet.getInt(1);
					courseVO = new CourseVO(courseId, courseName);
					scheduleList.add(scheduleVO);
				}else if(resultSet.getInt(1) != courseId){
					//If course ID changes, add the schedule list of earlier course to courseVO
					//Add course VO to courseList
					courseVO.setScheduleList(scheduleList);
					courseList.add(courseVO);
					scheduleList = new ArrayList<ScheduleVO>();

					courseId = resultSet.getInt(Constants.COURSE_ID);
					courseVO = new CourseVO(courseId, courseName);
					scheduleList.add(scheduleVO);
				}else{
					scheduleList.add(scheduleVO);
				}
			}
			if(courseVO!=null){
				if(scheduleList!=null && !scheduleList.isEmpty())
				courseVO.setScheduleList(scheduleList);
				courseList.add(courseVO);
			}

		} catch (Exception exp) {
			DBConnectionManager.close(null, preparedStatement, resultSet); //correct this
		}
		return courseList;
	}


	/**
	 * This method checks if student has already enrolled for a course or not.
	 * If not student will be enrolled for a course
	 * 
	 * @param emailId
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	@Override
	public String enrollCourse(int studentId, int courseId, int scheduleId) throws ResourceAlreadyExistsException, DataNotFoundException, GenericException{
		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			if(checkStudentExists(connection, studentId)){
				boolean alreadyEnrolled = checkCourseEnrolled(connection, studentId, courseId,scheduleId);
				if (alreadyEnrolled){
					throw new ResourceAlreadyExistsException("Student with emailId: " + studentId + " has already enrolled for this course !!! ");
				}else{
					preparedStatement = connection.prepareStatement(dbQueries.getProperty("student.enroll.course"));
					preparedStatement.setInt(1, studentId);
					preparedStatement.setInt(2, courseId);
					preparedStatement.setInt(3, scheduleId);

					int result = preparedStatement.executeUpdate();
					if(result>0){
						statusMessage = Constants.SUCCESS;
					}else{
						statusMessage = Constants.UNABLE_COURSE_ENROLLMENT;
					}
					connection.commit();
				}
			}else{
					throw new DataNotFoundException(Constants.NO_STUDENT_MESSAGE);
			}

		}catch (ResourceAlreadyExistsException raeExp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw raeExp;
		}catch (DataNotFoundException dnfExp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw dnfExp;
		}catch (Exception exp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException sqlExp) {
				System.out.println(sqlExp);
			}
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		}finally {
			try {
				if(connection!=null)
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}

		System.out.println("statusMessage  " + statusMessage);
		return statusMessage;
	}

	/**
	 * This method checks if this course is already enrolled by student or not
	 * 
	 * @param connection - connection object to start the transaction
	 * @param email - email id of student
	 * @param courseId -course id which to student will enroll
	 * @return true if student already enrolled else false 
	 */
	private boolean checkCourseEnrolled(Connection connection, int studentId, int courseId, int scheduleId) {
		int count=0;
		boolean alreadyEnrolled = false;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.course.enrolled"));
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.setInt(3, scheduleId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count > 0) {
				alreadyEnrolled = true;
			}
		} catch (Exception exp) {
			System.out.println("Error while checking course is already enrolled or not " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return alreadyEnrolled;
	}


	/**
	 * This method drops course to which student enrolled earlier
	 * 
	 * @param email - email id of student
	 * @param courseId -course id which has to be dropped
	 * @param scheduleId - schedule id of course which has to be dropped
	 * @return status message stating success or failure
	 */
	@Override
	public String dropCourse(int studentId, int courseId, int scheduleId) throws GenericException{
		String statusMessage = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			if(checkCourseEnrolled(connection, studentId, courseId,scheduleId)){

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("student.drop.course"));
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.setInt(3, scheduleId);

			int result = preparedStatement.executeUpdate();
			connection.commit();
			if(result>0){
				statusMessage = Constants.SUCCESS;
				System.out.println("Course with course id "+courseId +" and schedule id "+scheduleId+"dropped");
			}
			else{
				System.out.println(Constants.UNABLE_DROP_COURSE);
				statusMessage=Constants.UNABLE_DROP_COURSE;
			}
			}else{
				statusMessage = Constants.STUDENT_NOT_ENROLLED;
				System.out.println(Constants.STUDENT_NOT_ENROLLED);
			}
		}catch (Exception exp) {
			try {
				if(connection!=null)
					connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			try {
				if(connection!=null)
					connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}


	/**
	 * This method validates the user with email and password
	 * @param email - user's email
	 * @param password - user's password
	 * @return returns StudentVO object if student exists else returns null
	 */
	@Override
	public StudentVO login(String email, String password) throws DataNotFoundException, GenericException{
		StudentVO studentVO =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("login"));
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst() ) {    
				throw new DataNotFoundException(Constants.NO_STUDENT_MESSAGE);
			} 
			while (resultSet.next()) {
				studentVO = new StudentVO(resultSet.getInt(Constants.STUDENT_ID), resultSet.getString(Constants.FIRST_NAME), resultSet.getString(Constants.LAST_NAME), resultSet.getString(Constants.EMAIL), resultSet.getString(Constants.PASSWORD),resultSet.getString(Constants.USER_FLAG));
				System.out.println("Student Details "+studentVO.toString());
			}

		} catch (DataNotFoundException dnfExp) {
			throw dnfExp;
		} catch (Exception exp) {
			System.out.println("Error : " + exp);
			throw new GenericException(Constants.GENERIC_ERROR_MESSAGE);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return studentVO;
	}

}
