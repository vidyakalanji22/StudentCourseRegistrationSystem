/**
 * 
 */
package com.scr.resources.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.scr.dao.ScheduleDAO;
import com.scr.dao.impl.ScheduleDAOImpl;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.resources.ScheduleResource;
import com.scr.resources.beans.RestScheduleVO;
import com.scr.service.ScheduleService;
import com.scr.service.impl.ScheduleServiceImpl;
import com.scr.vo.ScheduleVO;


@Path("/schedules")
public class ScheduleResourceImpl implements ScheduleResource {
	
	ScheduleDAO scheduleDAOObject = new ScheduleDAOImpl();

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSchedule(@Context UriInfo uriInfo){
	
		ScheduleService scheduleServiceImpl = new ScheduleServiceImpl();
	    List<ScheduleVO> scheduleList = scheduleServiceImpl.getAllSchedule();
		
		List<RestScheduleVO> scheduleBeanList =new ArrayList<RestScheduleVO>();
		for (ScheduleVO scheduleVo : scheduleList) {
			RestScheduleVO restScheduleVO = new RestScheduleVO(scheduleVo.getScheduleId(),scheduleVo.getStartDate(),scheduleVo.getEndDate(),scheduleVo.getStartTime(),scheduleVo.getEndTime(),scheduleVo.getDaysOfWeek());
			//restScheduleVO.addLink(getUriForSelf(uriInfo, restScheduleVO), "self");
			scheduleBeanList.add(restScheduleVO);
		}
		GenericEntity<List<RestScheduleVO>> entity = new GenericEntity<List<RestScheduleVO>>(scheduleBeanList) {};
		return Response.status(Status.OK).entity(entity).build();
	}
	
	
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSchedule(RestScheduleVO restScheduleVO, @Context UriInfo uriInfo) throws ResourceNotCreatedException, ResourceAlreadyExistsException {
		ScheduleService scheduleServiceImpl = new ScheduleServiceImpl();
			ScheduleVO scheduleVo = new ScheduleVO(restScheduleVO.getStartDate(), restScheduleVO.getEndDate(), restScheduleVO.getStartTime(), 
    			restScheduleVO.getEndTime(), restScheduleVO.getDaysOfWeek());
			scheduleVo = scheduleServiceImpl.addSchedule(scheduleVo);
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(scheduleVo.getScheduleId())).build();
			return Response.created(uri)
					.entity(scheduleVo)
					.build();
	}
    
    @Override
	@PUT
	@Path("/{scheduleId}")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response updateSchedule (@PathParam("scheduleId")int scheduleId, RestScheduleVO restScheduleVO ){
    	ScheduleService scheduleServiceImpl = new ScheduleServiceImpl();
    	String statusMessage = "";
    	ScheduleVO scheduleVo = new ScheduleVO(scheduleId,restScheduleVO.getStartDate(), restScheduleVO.getEndDate(), restScheduleVO.getStartTime(), 
    			restScheduleVO.getEndTime(), restScheduleVO.getDaysOfWeek());
    		statusMessage = scheduleServiceImpl.updateSchedule(scheduleVo);
    		GenericEntity<String> entity = new GenericEntity<String>(statusMessage) {};
			return Response.status(Status.OK).entity(entity).build();
    }
    
    
    @Override
	@DELETE
	@Path("/{scheduleId}")
    public Response deleteSchedule(@PathParam("scheduleId") int scheduleId){
    	ScheduleService scheduleServiceImpl = new ScheduleServiceImpl();
    	String statusMessage = "";
    		statusMessage = scheduleServiceImpl.deleteSchedule(scheduleId);
			return Response.status(Status.OK).entity(statusMessage).build();
    }
    
	@OPTIONS
	public String getSupportedOperations(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}
    
//    private String getUriForSelf(UriInfo uriInfo, RestScheduleVO restScheduleVO) {
//		String uri = uriInfo.getBaseUriBuilder()
//				.path(ScheduleResourceImpl.class)
//				.path(Long.toString(restScheduleVO.getScheduleId()))
//				.build()
//				.toString();
//		return uri;
//	}
    
    

}