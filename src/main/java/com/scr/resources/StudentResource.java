package com.scr.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scr.resources.beans.RestStudentVO;

public interface StudentResource {

	/**
	 * This method gives the list of all the students.
	 * @return Response
	 */
	public Response getStudents(@Context UriInfo uriInfo);

	/**
	 * This method gives the student details.	
	 * @param studentId
	 * @return Response
	 */
	Response getStudentDetails(@PathParam("studentId") int studentId,  @Context UriInfo uriInfo, @Context Request request);

	/**
	 * This method gets the details of the student enrolled courses and schedule.
	 * @param studentId
	 * @return Response
	 */
	Response getStudentEnrolledCourses(@PathParam("studentId") int studentId, @Context UriInfo uriInfo);

	/**
	 * This method validates the login
	 * @param email
	 * @param password
	 * @return Response
	 */
	Response login(RestStudentVO restStudentVO, UriInfo uriInfo);
//	Response login(@NotNull(message="Email can not be empty")
//	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,}+", message = "Invalid email format. Note: Upper case alphabets are not allowed") @QueryParam("email") String email, @NotNull(message="Password can not be empty")
//	@Pattern(regexp = "[a-zA-Z0-9.@_*$]{4,20}+", message = "Invalid Password pattern. Note: Password length should be between 4-20") @QueryParam("password") String password, UriInfo uriInfo);

	/**
	 * Adds a new student record.
	 * @param restStudentVO
	 * @return Response
	 */
	public Response createStudent(RestStudentVO restStudentVO, @Context UriInfo uriInfo);

	/**
	 * Student can enroll for a particular course at a particular schedule.
	 * @param studentId
	 * @param courseId
	 * @param scheduleId
	 * @return Response
	 */
	Response enrollCourse(RestStudentVO restStudentVO, @PathParam("studentId") int studentId, @QueryParam("courseId") int courseId, @QueryParam("scheduleId") int scheduleId, @Context UriInfo uriInfo); 

	/**
	 * Updates the student details
	 * @param studentId
	 * @param RestStudentVO
	 * @return Response
	 */
	Response updateStudent(@PathParam("studentId") int studentId, RestStudentVO restStudentVO, @Context Request request);

	/**
	 * Deletes the student.
	 * @param studentId
	 * @return Response
	 */
	public Response deleteStudent(@PathParam("studentId") int studentId, @Context UriInfo uriInfo);

	/**
	 * Student can drop a course.
	 * @param studentId
	 * @param courseId
	 * @param scheduleId
	 * @return Response
	 */
	public Response dropCourse(@QueryParam("studentId") int studentId, @QueryParam("courseId") int courseId,
			@QueryParam("scheduleId") int scheduleId);

	/**
	 * User logs out of the session
	 * @return
	 */
	public Response logout(@Context HttpServletRequest webRequest);
	
	
	public Response getUserObject(@Context HttpServletRequest webRequest);


}