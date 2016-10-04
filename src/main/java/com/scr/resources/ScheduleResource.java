package com.scr.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.resources.beans.RestScheduleVO;

public interface ScheduleResource {
	
	/**
	 * This method returns the list of all the schedules.
	 * @return List<ScheduleVO>
	 */
    Response getAllSchedule(UriInfo uriInfo);
    
    /**
	 * Adds a new schedule record
	 * @param start_date
	 * @param end_date
	 * @param start_time
	 * @param end_time
	 * @param days_of_week
	 * @return Object
	 */
    Response addSchedule(RestScheduleVO restScheduleVO, UriInfo uriInfo)
			throws ResourceNotCreatedException, ResourceAlreadyExistsException;
    
    /**
	 * Updates the schedule details
	 * @param schedule_id
	 * @param start_date
	 * @param end_date
	 * @param start_time
	 * @param end_time
	 * @param days_of_week
	 * @return String
	 */
	Response updateSchedule(int scheduleId, RestScheduleVO restScheduleVO);
    
	/**
	 * Deletes the schedule
	 * @param schedule_id
	 * @param start_date
	 * @param end_date
	 * @param start_time
	 * @param end_time
	 * @param days_of_week
	 * @return String
	 */
	Response deleteSchedule(int scheduleId);

}