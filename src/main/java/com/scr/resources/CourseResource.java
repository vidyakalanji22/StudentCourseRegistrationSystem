package com.scr.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scr.resources.beans.CourseFilterBean;
import com.scr.resources.beans.RestCourseVO;

public interface CourseResource {

	/**
	 * This method gets courses based on the query
	 * @param filterBean
	 * @param uriInfo
	 * @return Response
	 */
	Response getCourses(CourseFilterBean filterBean, UriInfo uriInfo);

	/**
	 * This method gets course details
	 * @param courseId
	 * @param uriInfo
	 * @return Response
	 */
	Response getCourseDetails(int courseId, UriInfo uriInfo);

	/**
	 * This method adds course
	 * @param restCourseVo
	 * @param uriInfo
	 * @return Response
	 */
	Response addCourse(RestCourseVO restCourseVo, UriInfo uriInfo);

	/**
	 * This method updates course
	 * @param courseId
	 * @param restCourseVo
	 * @return Response
	 */
	String updateCourse(int courseId, RestCourseVO restCourseVo);

	/**
	 * This method deletes course
	 * @param courseId
	 * @return Response
	 */
	Response deleteCourse(int courseId);

	/**
	 * This method gives the supported operations for this web service
	 * @return String
	 */
	String getSupportedOperations();
	/**
	 * This method gives the path info for this web service
	 * @param uriInfo
	 * @return String
	 */
	String getPathInfo(UriInfo uriInfo);

	/**
	 * This method fetches course books
	 * @param courseId
	 * @return Response
	 */
	Response getCourseBooks(int courseId);
	/**
	 * This method fetches course curriculum
	 * @param courseId
	 * @return Response
	 */
	Response getCourseCurriculum(int courseId);

	/**
	 * This method fetches course schedule
	 * @param courseId
	 * @return Response
	 */
	Response getCourseSchedule(int courseId);

}