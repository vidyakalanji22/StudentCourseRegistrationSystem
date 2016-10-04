/**
 * 
 */
package com.scr.resources.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BeanParam;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.scr.etag.EntityTag;
import com.scr.permissions.PermissionsTag;
import com.scr.resources.CourseResource;
import com.scr.resources.beans.CourseFilterBean;
import com.scr.resources.beans.RestBookVO;
import com.scr.resources.beans.RestCourseVO;
import com.scr.resources.beans.RestCurriculumVO;
import com.scr.resources.beans.RestScheduleVO;
import com.scr.service.CourseService;
import com.scr.service.impl.CourseServiceImpl;
import com.scr.util.Constants;
import com.scr.vo.BookVO;
import com.scr.vo.CourseVO;
import com.scr.vo.CurriculumVO;
import com.scr.vo.ScheduleVO;

@Path("/courses")
public class CourseResourceImpl implements CourseResource {
	CourseService courseService = new CourseServiceImpl();

	/**
	 * This method gets courses based on the query
	 * @param filterBean
	 * @param uriInfo
	 * @return Response
	 */
	@Override
	@GET
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getCourses(@BeanParam CourseFilterBean filterBean, @Context UriInfo uriInfo){
		List<CourseVO> courseVOs = null;

		if(filterBean.getStartDate()!=null && filterBean.getEndDate()!=null){
			courseVOs = courseService.getDateRangeCourseList(filterBean.getStartDate(), filterBean.getEndDate());
		}else if(filterBean.getStartAmount()>0 && filterBean.getEndAmount()>0){
			courseVOs = courseService.getAmountRangeCourseList(filterBean.getStartAmount(), filterBean.getEndAmount());
		}else if(filterBean.getDays()!=null && filterBean.getDays().length>0){
			courseVOs = courseService.getDayRangeCourseList(filterBean.getDays());
		}else if(filterBean.getStart()>0 && filterBean.getSize()>0){
			int start = filterBean.getStart()-1;
			courseVOs = courseService.listAllCourse().subList(start, filterBean.getSize());
		}else{
			courseVOs = courseService.listAllCourse();
		}

		List<RestCourseVO> restCourseVOs = new ArrayList<RestCourseVO>();

		for (CourseVO courseVO : courseVOs) {
			RestCourseVO restCourseVO = new RestCourseVO(courseVO.getCourseId(), courseVO.getCourseName(), courseVO.getCourseAmount(), courseVO.getCourseDesc(), courseVO.getScheduleList(), courseVO.getBooksList(), courseVO.getCurriculumList());
			restCourseVO.addLink(getUriForSelf(uriInfo, restCourseVO), "self");
			restCourseVOs.add(restCourseVO);
		}
		GenericEntity<List<RestCourseVO>> entity = new GenericEntity<List<RestCourseVO>>(restCourseVOs) {};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * This method gets course details
	 * @param courseId
	 * @param uriInfo
	 * @return Response
	 */
	@Override
	@GET
	@Path("/{courseId}")
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	@EntityTag
	public Response getCourseDetails(@PathParam("courseId") int courseId, @Context UriInfo uriInfo){
		RestCourseVO restCourseVO= null;
		CourseVO courseVO = courseService.getCourseDetails(courseId);

		if(courseVO!=null){
			restCourseVO = new RestCourseVO(courseVO.getCourseId(), courseVO.getCourseName(), courseVO.getCourseAmount(), courseVO.getCourseDesc(), courseVO.getScheduleList(), courseVO.getBooksList(), courseVO.getCurriculumList());
			restCourseVO.addLink(getUriForSelf(uriInfo, restCourseVO), "self");
			restCourseVO.addLink(getUriForBooks(uriInfo, restCourseVO), "books");
			restCourseVO.addLink(getUriForSchedules(uriInfo, restCourseVO), "schedules");
			restCourseVO.addLink(getUriForCurriculum(uriInfo, restCourseVO.getCourseId()), "curriculum");
		}

		return Response.status(Status.OK)
				.entity(restCourseVO)
				.build();
	}

	/**
	 * This method fetches course books
	 * @param courseId
	 * @return Response
	 */
	@Override
	@GET
	@Path("/{courseId}/books")
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	@PermissionsTag(value={Constants.USER_ADMIN,Constants.USER_STUDENT})
	public Response getCourseBooks(@PathParam("courseId")int courseId) {
		List<BookVO> bookVOs = courseService.getCourseBooks(courseId);
		List<RestBookVO> restBookVOs = new ArrayList<>();
		for (BookVO bookVO : bookVOs) {
			RestBookVO restBookVO = new RestBookVO(bookVO.getBookID(), bookVO.getBookName());
			restBookVOs.add(restBookVO);
		}
		GenericEntity<List<RestBookVO>> entity = new GenericEntity<List<RestBookVO>>(restBookVOs){};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * This method fetches course curriculum
	 * @param courseId
	 * @return Response
	 */
	@Override
	@GET
	@Path("/{courseId}/curriculum")
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	@PermissionsTag(value={Constants.USER_ADMIN,Constants.USER_STUDENT})
	public Response getCourseCurriculum(@PathParam("courseId")int courseId) {
		List<CurriculumVO> curriculumVO = courseService.getCourseCurriculum(courseId);
		List<RestCurriculumVO> restcurriculumVO = new ArrayList<>();
		for (CurriculumVO curriculumvo : curriculumVO) {
			RestCurriculumVO restcurriculumVOs = new RestCurriculumVO(curriculumvo.getCurriculumId(), curriculumvo.getTopicName());
			//restcurriculumVOs.addLink(getUriForCurriculum(uriInfo, courseId), "self");
			restcurriculumVO.add(restcurriculumVOs);
		}
		GenericEntity<List<RestCurriculumVO>> entity = new GenericEntity<List<RestCurriculumVO>>(restcurriculumVO){};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * This method fetches course schedule
	 * @param courseId
	 * @return Response
	 */
	@Override
	@GET
	@Path("{courseId}/schedules")
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	@PermissionsTag(value={Constants.USER_ADMIN,Constants.USER_STUDENT})
	public Response getCourseSchedule(@PathParam("courseId")int courseId) {
		List<ScheduleVO> scheudleVO = courseService.getCourseSchedule(courseId);
		List<RestScheduleVO> restcurriculumVO = new ArrayList<>();
		for (ScheduleVO schedulevo : scheudleVO) {
			RestScheduleVO restScheduleVOs = new RestScheduleVO(schedulevo.getScheduleId(), schedulevo.getStartDate(), schedulevo.getEndDate(), schedulevo.getStartTime(), schedulevo.getEndTime(), schedulevo.getDaysOfWeek());
			restcurriculumVO.add(restScheduleVOs);
		}
		GenericEntity<List<RestScheduleVO>> entity = new GenericEntity<List<RestScheduleVO>>(restcurriculumVO){};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * This method adds course
	 * @param restCourseVo
	 * @param uriInfo
	 * @return Response
	 */
	@Override
	@POST
	@Produces(value ={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
	@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	@PermissionsTag(Constants.USER_ADMIN)
	public Response addCourse(RestCourseVO restCourseVo, @Context UriInfo uriInfo){
		CourseVO courseVO = new CourseVO(restCourseVo.getCourseId(), restCourseVo.getCourseName(), restCourseVo.getCourseAmount(), restCourseVo.getCourseDesc(), restCourseVo.getScheduleList(), restCourseVo.getBooksList(), restCourseVo.getCurriculumList());;;
		courseVO = courseService.addCourse(courseVO);
		restCourseVo.setCourseId(courseVO.getCourseId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(courseVO.getCourseId())).build();
		return Response.created(uri)
				.entity(courseVO)
				.build();
	}

	/**
	 * This method updates course
	 * @param courseId
	 * @param restCourseVo
	 * @return Response
	 */
	@Override
	@PUT
	@Path("/{courseId}")
	@Produces(value ={MediaType.TEXT_PLAIN,MediaType.TEXT_XML,MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	@EntityTag
	@PermissionsTag(Constants.USER_ADMIN)
	public String updateCourse(@PathParam("courseId") int courseId, RestCourseVO restCourseVo){
		String result = null;
		restCourseVo.setCourseId(courseId);
		CourseVO courseVO = new CourseVO(courseId, restCourseVo.getCourseName(), restCourseVo.getCourseAmount(), restCourseVo.getCourseDesc(), restCourseVo.getScheduleList(), restCourseVo.getBooksList(), restCourseVo.getCurriculumList());
		result = courseService.updateCourse(courseId, courseVO);
		//return Response.status(Status.OK).entity(result).build();
		return result;
	}

	/**
	 * This method deletes course
	 * @param courseId
	 * @return Response
	 */
	@Override
	@DELETE
	@Path("/{courseId}")
	@EntityTag
	@PermissionsTag(Constants.USER_ADMIN)
	public Response deleteCourse(@PathParam("courseId") int courseId){
		String result = courseService.disableCourse(courseId);
		return Response.status(Status.OK).entity(result).build();
	}


	/**
	 * This method gives the supported operations for this web service
	 * @return String
	 */
	@Override
	@OPTIONS
	public String getSupportedOperations(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}

	/**
	 * This method gives the path info for this web service
	 * @param uriInfo
	 * @return String
	 */
	@Override
	@GET
	@Path("/context")
	public String getPathInfo(@Context UriInfo uriInfo){
		MultivaluedMap<String,String> pathParams = uriInfo.getPathParameters();
		uriInfo.getRequestUri();
		MultivaluedMap<String, String> queryParams=uriInfo.getQueryParameters();
		return "Path Params "+pathParams.values().toString() +" request uri "+uriInfo.getRequestUri().toString() +" Query Params " + queryParams.values().toString();
	}

	private String getUriForSelf(UriInfo uriInfo, RestCourseVO restCourseVO) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(CourseResourceImpl.class)
				.path(Long.toString(restCourseVO.getCourseId()))
				.build()
				.toString();
		return uri;
	}

	private String getUriForBooks(UriInfo uriInfo, RestCourseVO restCourseVO) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(CourseResourceImpl.class)
				.path(Integer.toString(restCourseVO.getCourseId()))
				.path(BookResourceImpl.class)
				.build()
				.toString();
		return uri;
	}

	private String getUriForSchedules(UriInfo uriInfo, RestCourseVO restCourseVO) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(CourseResourceImpl.class)
				.path(Integer.toString(restCourseVO.getCourseId()))
				.path(ScheduleResourceImpl.class)
				.build()
				.toString();
		return uri;
	}

	private String getUriForCurriculum(UriInfo uriInfo, int courseId) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(CourseResourceImpl.class)
				.path(CourseResourceImpl.class, "getCourseCurriculum")
				.resolveTemplate("courseId", courseId)
				.build()
				.toString();
		return uri;
	}
}
