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

import com.scr.resources.CurriculumResource;
import com.scr.resources.beans.RestCurriculumVO;
import com.scr.service.CurriculumService;
import com.scr.service.impl.CurriculumServiceImpl;
import com.scr.vo.CurriculumVO;

@Path("/curriculum")
public class CurriculumResourceImpl implements CurriculumResource  {

	CurriculumService curriculumService = new CurriculumServiceImpl();

	/**
	 * This method returns the list of all the students.
	 * @return List<StudentVO>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCurriculum(@Context UriInfo uriInfo){
		List<CurriculumVO> curriculumVO = curriculumService.getAllCurriculum();
		List<RestCurriculumVO> restcurriculumVO = new ArrayList<>();
		for (CurriculumVO curriculumvo : curriculumVO) {
			RestCurriculumVO restcurriculumVOs = new RestCurriculumVO(curriculumvo.getCurriculumId(), curriculumvo.getTopicName());
			//restcurriculumVOs.addLink(getUriForSelf(uriInfo, restcurriculumVOs), "self");
			restcurriculumVO.add(restcurriculumVOs);
		}
		GenericEntity<List<RestCurriculumVO>> entity = new GenericEntity<List<RestCurriculumVO>>(restcurriculumVO){};
		return Response.status(Status.OK).entity(entity).build();
	}

	/** 
	 * This method inserts data into Curriculum table
	 * @param RestCurriculumVO
	 * @return Response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCurriculum(RestCurriculumVO curriculumBean,@Context UriInfo uriinfo){
		Response response = null;
		CurriculumVO curriculumVO= new CurriculumVO(curriculumBean.getCurriculumId(),curriculumBean.getTopicName());
		curriculumVO= curriculumService.addCurriculum(curriculumVO);	 

		// Setting status code and location headers
		String newID = String.valueOf(curriculumVO.getCurriculumId());
		URI uri = uriinfo.getAbsolutePathBuilder().path(newID).build();
		response = Response.created(uri).entity(curriculumVO).build();
		return response;
	}


	/**
	 *Updates the data in Curriculum table
	 * @param RestCurriculumVO
	 * @return Response 
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCurriculum(RestCurriculumVO curriculumBean, @Context UriInfo uriinfo){
		String statusMessage="";
		Response response = null;
		CurriculumVO curriculumVO= new CurriculumVO(curriculumBean.getCurriculumId(),curriculumBean.getTopicName());
		statusMessage= curriculumService.updateCurriculum(curriculumVO);

		// Setting status code and location headers
		String newID = String.valueOf(curriculumVO.getCurriculumId());
		URI uri = uriinfo.getAbsolutePathBuilder().path(newID).build();
		response = Response.created(uri).entity(statusMessage).build();
		return response;
	}

	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 * @return Response
	 */
	@DELETE
	@Path("/{curriculumId}")
	public Response deleteCurriculum(@PathParam("curriculumId") int curriculumId){
		String statusMessage="";
		Response response = null;

		statusMessage= curriculumService.deleteCurriculum(curriculumId);
		response = Response.status(Status.OK).entity(statusMessage).build();
		return response;
	}


	@OPTIONS
	public String getSupportedOperations(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}

//	private String getUriForSelf(UriInfo uriInfo, RestCurriculumVO restcurriculumVO) {
//		String uri = uriInfo.getBaseUriBuilder()
//				.path(CurriculumResourceImpl.class)
//				.path(Long.toString(restcurriculumVO.getCurriculumId()))
//				.build()
//				.toString();
//		return uri;
//	}

}


