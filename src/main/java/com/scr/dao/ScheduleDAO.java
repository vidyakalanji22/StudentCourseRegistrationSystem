/**
 * 
 */
package com.scr.dao;
import java.util.List;

import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.vo.ScheduleVO;



public interface ScheduleDAO {

	/**
	 * This method adds schedule details to Schedule Table
	 * 
	 * @param scheduleVo
	 *            -scheduleVo contains schedule details which needs
	 *            to be persisted
	 * @return statusMessage specifying success or failure 
	 * @throws Exception 
	 */
	public ScheduleVO addSchedule(ScheduleVO scheduleVo) throws ResourceAlreadyExistsException,ResourceNotCreatedException;


	/**
	 * This method gives the list of all the schedules available 
	 *                                          in schedule table.
	 *                                          
	 * @return List of schedule	
	 * @throws Exception 
	 */
	public List<ScheduleVO> getAllSchedule() throws Exception;

	/**
	 * This method delete the schedule from schedule table
	 * 
	 * @param connection
	 *               - connection to create prepared statement
	 * @return
	 *           -Message to confirm if the schedule is deleted or not   
	 * @throws Exception 
	 */
	public String deleteSchedule (int scheduleId) throws Exception; 


	/**
	 * This method updates schedule
	 * @param scheduleVo Object contains student details that has to be updated
	 * @return status message stating success or failure
	 * @throws Exception 
	 */
	public String updateSchedule(ScheduleVO scheduleVo) throws Exception ;

	/**
	 * Gets the course schedule
	 * @param connection
	 * @param courseId
	 * @return List<ScheduleVO>
	 * @throws Exception 
	 */
	public List<ScheduleVO> getCourseSchedule(int courseId) throws Exception;
	
	/**
	 * Adds the schedule for a course
	 * @param connection
	 * @param courseId
	 * @param courseSchList
	 * @return boolean
	 * @throws Exception
	 */

	public String addCourseSchedule(int courseId, List<ScheduleVO> scheduleVOs) throws Exception;


	

}
