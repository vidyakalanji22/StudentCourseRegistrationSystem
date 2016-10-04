package com.scr.resources;

import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scr.resources.beans.RestCurriculumVO;

public interface CurriculumResource {

	/**
	 * This method returns the list of all the students.
	 * @return Response
	 */
	Response getAllCurriculum(@Context UriInfo uriInfo);

	/** 
	 * This method inserts data into Curriculum table
	 * @param RestCurriculumVO
	 * @return Response
	 */
	Response addCurriculum(@Valid RestCurriculumVO restCurriculumVO,  @Context UriInfo uriinfo);

	/**
	 * Updates the data in Curriculum table
	 * @param RestCurriculumVO
	 * @return Response
	 */
	Response updateCurriculum(@Valid RestCurriculumVO restCurriculumVO, @Context UriInfo uriinfo);

	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 * @return Response
	 */
	Response deleteCurriculum(int curriculumId);


}
