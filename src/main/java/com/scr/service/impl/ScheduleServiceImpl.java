package com.scr.service.impl;

import java.util.List;

import com.scr.dao.ScheduleDAO;
import com.scr.dao.impl.ScheduleDAOImpl;
import com.scr.vo.ScheduleVO;
import com.scr.util.Constants;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService{
	private ScheduleDAO scheduleDAO = new ScheduleDAOImpl();
	
	public ScheduleServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public List<ScheduleVO> getAllSchedule(){
		
		List<ScheduleVO> scheduleVOList = null;
		try {
			scheduleVOList = scheduleDAO.getAllSchedule();
			if(scheduleVOList == null || scheduleVOList.isEmpty()){
				throw new Exception("No schedules to display");
			}
		} catch (Exception e) {
			throw new DataNotFoundException(e.getMessage());
		}
		
		return scheduleVOList;
		
	}
	
	/*public List<ScheduleVO> getCourseSchedule(int courseId){
		
		List<ScheduleVO> scheduleVOList = null;
		try {
			scheduleVOList = scheduleDAO.getCourseSchedule(courseId);
			if(scheduleVOList == null || scheduleVOList.isEmpty()){
				throw new Exception("No schedules to display");
			}
		} catch (Exception e) {
			throw new DataNotFoundException(e.getMessage());
		}
		
		return scheduleVOList;
		
	}*/
	
	public ScheduleVO addSchedule(ScheduleVO scheduleVo) {
	
			try {
				scheduleVo = scheduleDAO.addSchedule(scheduleVo);
			} catch (ResourceNotCreatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(scheduleVo!=null && scheduleVo.getScheduleId()>0){
				return scheduleVo;
			}else{
				throw new ResourceNotCreatedException("Cannot add Schedule");
			}

	}
	
	/*public String addCourseSchedule(int courseId, List<ScheduleVO> scheduleVOs){
		String statusMessage = "";
		try {
			statusMessage = scheduleDAO.addCourseSchedule(courseId, scheduleVOs);
			if(!statusMessage.equalsIgnoreCase(Constants.SUCCESS)){
				throw new Exception(statusMessage);
			}
		} catch (Exception e) {
			throw new DataNotFoundException(e.getMessage());
		}
		return statusMessage;
	}*/
	
	public String updateSchedule(ScheduleVO scheduleVO) {
		String statusMessage = "";
		try {
			statusMessage = scheduleDAO.updateSchedule(scheduleVO);
			if(!statusMessage.equalsIgnoreCase(Constants.SUCCESS)){
				throw new Exception(statusMessage);
			}
		} catch (Exception e) {
			throw new DataNotFoundException(e.getMessage());
		}
		return statusMessage;
	}
	
	public String deleteSchedule(int scheduleId) {
		String statusMessage = "";
		try {
			statusMessage = scheduleDAO.deleteSchedule(scheduleId);
			if(!statusMessage.equalsIgnoreCase(Constants.SUCCESS)){
				throw new Exception(statusMessage);
			}
		} catch (Exception e) {
			throw new DataNotFoundException(e.getMessage());
		}
		return statusMessage;
	}	

}
