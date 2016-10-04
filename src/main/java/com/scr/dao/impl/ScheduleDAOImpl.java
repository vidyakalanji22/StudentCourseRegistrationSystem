package com.scr.dao.impl;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.ScheduleDAO;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.GenericException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.ScheduleVO;

public class ScheduleDAOImpl implements ScheduleDAO {
	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method adds schedule details to Schedule Table
	 * 
	 * @param scheduleVo
	 *            -scheduleVo contains schedule details which needs
	 *            to be persisted
	 * @return statusMessage specifying success or failure 
	 */
	@Override
	public ScheduleVO addSchedule(ScheduleVO scheduleVo) throws ResourceAlreadyExistsException,ResourceNotCreatedException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		int index = 0;
		String statusMessage = null;
		ResultSet rs= null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			boolean scheduleExists = checkScheduleExists(connection , scheduleVo);

			//Check if student already exists. If schedule exists throw exception saying student already exists
			if(scheduleExists)
				throw new ResourceAlreadyExistsException("Schedule already exist");

			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.schedule"),PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setDate(++index,new java.sql.Date(scheduleVo.getStartDate().getTime()));
			preparedStatement.setDate(++index,new java.sql.Date(scheduleVo.getEndDate().getTime()));
			preparedStatement.setTime(++index,scheduleVo.getStartTime());
			preparedStatement.setTime(++index,scheduleVo.getEndTime());
			System.out.println("Set time done-------------");

			Array daysArray = connection.createArrayOf("text", scheduleVo.getDaysOfWeek());
			preparedStatement.setArray(++index, daysArray);

			System.out.println("Inserting schedule using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			int result = preparedStatement.executeUpdate();
			connection.commit();
			if(result>0){
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()){
					scheduleVo.setScheduleId(rs.getInt(Constants.SCHEDULE_ID));
				}
				statusMessage = Constants.SUCCESS;
			}
			else{
				statusMessage = Constants.FAILURE;
				throw new ResourceNotCreatedException("Schedule Cannot be created");
			}

		} catch (Exception exp) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}

		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, rs);
		}
		return scheduleVo;
	}

	/**
	 * This method gives the list of all the schedules available 
	 *                                          in schedule table.
	 *                                          
	 * @return List of schedule	
	 */

	@Override
	public List<ScheduleVO> getAllSchedule() throws Exception{
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List<ScheduleVO> scheduleList =null;
		scheduleList = new ArrayList<ScheduleVO>();

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("list.schedule"));

			//execute the select query 
			resultSet = preparedStatement.executeQuery();


			while(resultSet.next()){
				String[] daysOfWeek = (String[])resultSet.getArray(Constants.DAYS_OF_WEEK).getArray();
				scheduleList.add(new ScheduleVO(resultSet.getInt(Constants.SCHEDULE_ID),resultSet.getDate(Constants.START_DATE), resultSet.getDate(Constants.END_DATE), 
						resultSet.getTime(Constants.START_TIME), resultSet.getTime(Constants.END_TIME),daysOfWeek));
			}
		}catch (Exception e) {
			System.out.println("AN Exception occurred in getAllSchedule() of ScheduleDAOImpl() " +e.getMessage());
			throw new Exception("An unexpected Error occurred");
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		System.out.println("scheduleList == " + scheduleList);
		return scheduleList;
	}

	/**
	 * This method checks if schedule already exists or not
	 * 
	 * @param connection
	 *            - connection to create prepared statement
	 *
	 * @return true if schedule exists else false
	 */

	private boolean checkScheduleExists(Connection connection, ScheduleVO scheduleVo) {
		Boolean scheduleExists = false;
		int count =0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//prepare query to check if schedule exists
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.schedule"));

			preparedStatement.setDate(1,new java.sql.Date(scheduleVo.getStartDate().getTime()));
			preparedStatement.setDate(2,new java.sql.Date(scheduleVo.getEndDate().getTime()));
			preparedStatement.setTime(3,scheduleVo.getStartTime());
			preparedStatement.setTime(4,scheduleVo.getEndTime());
			Array daysArray = connection.createArrayOf("text", scheduleVo.getDaysOfWeek());
			preparedStatement.setArray(5, daysArray);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			System.out.println("count = " + count);
			if (count > 0) {
				scheduleExists = true;
			}
		}catch (Exception exp) {
			System.out.println("Error occured while checking if schedule exists or not" + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("scheduleExists===== > " + scheduleExists);
		return scheduleExists;
	}

	/**
	 * This method checks if the course corresponding to the scheduleID exists
	 * 
	 * @param connection
	 *              -- connection to create prepared statement
	 * @ return
	 *           -true if the course exists for the scheduleID else False           
	 */
	private boolean checkScheduleOfCourseExists (Connection connection, int scheduleId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int scheduleID = 0;
		int count = 0;
		Boolean scheduleOfCourseExists = false;
		try {
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.schedule.course"));
			preparedStatement.setInt(1, scheduleID);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(1);  
			}
			System.out.println("count = " + count);
			if (count > 0) {
				scheduleOfCourseExists = true;
			}
		}catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		System.out.println("scheduleOfCourseExists ===== > " + scheduleOfCourseExists);
		return scheduleOfCourseExists;
	}


	/**
	 * This method delete the schedule from schedule table
	 * 
	 * @param connection
	 *               - connection to create prepared statement
	 * @return
	 *           -Delete the schedule    
	 */

	@Override
	public String deleteSchedule (int scheduleId) throws Exception {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		String statusMessage = null;

		try {
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			//Checks if the course for the scheduleID exists
			if (checkScheduleOfCourseExists (connection, scheduleId)) {
				throw new RuntimeException("Cannot delete schedule as the schedule does not exist");
			}
			//get connection to DB
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("drop.schedule"));
			preparedStatement.setInt(1, scheduleId);
			int result = preparedStatement.executeUpdate();
			if(result>0)
				statusMessage = Constants.SUCCESS;
			else
				statusMessage = Constants.FAILURE;
		}/*catch (Exception exp) {
				statusMessage = Constants.FAILURE;
				System.out.println("Error : " + exp);
				try {
					connection.rollback();
				} catch (SQLException e1) {
					System.out.println(statusMessage);
				}
			} */ catch (Exception exp) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					System.out.println(statusMessage);
				}

				if(exp instanceof RuntimeException){
					statusMessage = exp.getMessage();
				}else if(exp instanceof SQLException || exp instanceof Exception){
					throw new Exception("Unexpected error occurred");
				}
			}finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DBConnectionManager.close(connection, preparedStatement, resultSet);
			}  
		return statusMessage;
	}

	/**
	 * This method updates schedule
	 * @param scheduleVo Object contains student details that has to be updated
	 * @return status message stating success or failure
	 */

	@Override
	public String updateSchedule(ScheduleVO scheduleVo) throws DataNotFoundException, GenericException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String statusMessage = null;

		try {
			//get connection to DB
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);

			//Prepare insert statement to insert schedule details using preparedStatement
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.schedule"));

			int index=0;
			//set Schedule values
			preparedStatement.setDate(++index, scheduleVo.getStartDate());
			preparedStatement.setDate(++index, scheduleVo.getEndDate());
			preparedStatement.setTime(++index, scheduleVo.getStartTime());
			preparedStatement.setTime(++index, scheduleVo.getEndTime());
			Array daysArray = connection.createArrayOf("text", scheduleVo.getDaysOfWeek());
			preparedStatement.setArray(++index, daysArray);
			preparedStatement.setInt(++index, scheduleVo.getScheduleId());
			System.out.println("Updating Schedule using DDL statement "+preparedStatement.toString());

			//execute the insert statement
			int result = preparedStatement.executeUpdate();

			if(result>0)
				statusMessage = Constants.SUCCESS;
			else{
				connection.rollback();
				statusMessage = Constants.FAILURE;
				throw new DataNotFoundException("Cannot update schedule as schedule doesnot exist");
			}
			connection.commit();

		} catch (DataNotFoundException gExp) {
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
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		System.out.println("statusMessage =====> " + statusMessage);
		return statusMessage;

	}

	/**
	 * Gets the course schedule
	 * @param connection
	 * @param courseId
	 * @return List<ScheduleVO>
	 */

	@Override
	public List<ScheduleVO> getCourseSchedule(int courseId) throws Exception {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ScheduleVO> courseSchedule = null;
		Connection connection = null;
		courseSchedule =new ArrayList<ScheduleVO>();

		try{
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.schedule"));
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){
				String[] daysOfWeek = (String[])resultSet.getArray(Constants.DAYS_OF_WEEK).getArray();
				ScheduleVO scheduleVO = new ScheduleVO(resultSet.getDate(Constants.START_DATE), resultSet.getDate(Constants.END_DATE), resultSet.getTime(Constants.START_TIME), resultSet.getTime(Constants.END_TIME),daysOfWeek);
				courseSchedule.add(scheduleVO);
				System.out.println("Course Schedule for courseID: "+courseId+" "+scheduleVO.toString());
			}

		}catch(Exception exp) {
			System.out.println("Error : " + exp);
			throw new Exception("An unexpected error occurred");

		}finally{
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		return courseSchedule;
	}

	/**
	 * Adds the schedule for a course
	 * @param connection
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */

	@Override
	public String addCourseSchedule(int courseId, List<ScheduleVO> scheduleVOs) throws Exception {
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		Connection connection = null;
		try{
			connection = DBConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.schedule"));
			for(ScheduleVO scheduleVO : scheduleVOs){
				preparedStatement.setInt(1, courseId);
				preparedStatement.setInt(2, scheduleVO.getScheduleId());
				preparedStatement.addBatch();
			}
			int count[] = preparedStatement.executeBatch();
			if(count.length > 0)
				statusMessage = Constants.SUCCESS;
			else{
				statusMessage = Constants.FAILURE;

			}
			connection.commit();
		} /*catch(SQLException sqlExp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("SQLException occurred in insertCourseSchedule() " + sqlExp.getMessage());
		}catch(Exception exp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("Exception occurred in insertCourseSchedule() "+exp.getMessage());
		}*/ catch (Exception exp) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}

			if(exp instanceof RuntimeException){
				statusMessage = exp.getMessage();
			}else if(exp instanceof SQLException || exp instanceof Exception){
				throw new Exception("Unexpected error occurred");
			}
		} finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}


}
