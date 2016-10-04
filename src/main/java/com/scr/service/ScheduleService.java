package com.scr.service;

import java.util.List;

import com.scr.vo.ScheduleVO;

public interface ScheduleService {
    
	/**
	 * This method returns the list of all the Schedules.
	 * @return List<ScheduleVO>
	 */
	List<ScheduleVO> getAllSchedule();
	
	/**
	 * This method adds a new schedule record.
	 * @return ScheduleVO
	 */
    ScheduleVO addSchedule(ScheduleVO scheduleVo) ;

    /**
	 * This method updates the existing schedule.
	 * @return String
	 */
	String updateSchedule(ScheduleVO scheduleVO);
    
	/**
	 * This method deletes the existing schedule.
	 * @return String
	 */
	String deleteSchedule(int scheduleId);

}