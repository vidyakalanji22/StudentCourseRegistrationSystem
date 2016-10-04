package com.scr.resources.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.security.web.csrf.CsrfToken;

import com.scr.dao.StudentsDAO;
import com.scr.dao.impl.StudentDAOImpl;
import com.scr.etag.EntityTag;
import com.scr.permissions.PermissionsTag;
import com.scr.resources.StudentResource;
import com.scr.resources.beans.RestStudentVO;
import com.scr.service.StudentService;
import com.scr.service.impl.StudentServiceImpl;
import com.scr.util.Constants;
import com.scr.vo.StudentVO;

@Path("/students")
//@PermitAll
public class StudentsResourceImpl implements StudentResource {

	StudentsDAO studentDAOObject = new StudentDAOImpl();
	@Context HttpServletRequest req;

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermissionsTag({Constants.USER_STUDENT,Constants.USER_ADMIN})
	public Response getStudents(@Context UriInfo uriInfo) {

		StudentService studentServiceImpl = new StudentServiceImpl();

		List<StudentVO> studentsList =  studentServiceImpl.getStudents();

		List<RestStudentVO> studentBeanList = new ArrayList<RestStudentVO>();
		for (StudentVO studentVO : studentsList) {
			RestStudentVO restStudentVO = new RestStudentVO(studentVO.getStudentId(),studentVO.getFirstName(),studentVO.getLastName(),studentVO.getEmailId(),studentVO.getPassword(),studentVO.getUserFlag());
			restStudentVO.addLink(getUriForSelf(uriInfo, restStudentVO), "self");
			studentBeanList.add(restStudentVO);
		}
		GenericEntity<List<RestStudentVO>> entity = new GenericEntity<List<RestStudentVO>>(studentBeanList) {};
		return Response.status(Status.OK).entity(entity).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scr.rest.StudentRestInterface#getStudentDetails(java.lang.String)
	 */
	@Override
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@EntityTag
	@PermissionsTag({Constants.USER_STUDENT,Constants.USER_ADMIN})
	public Response getStudentDetails(@PathParam("studentId") int studentId, @Context UriInfo uriInfo, @Context Request request) {
      
		StudentService studentServiceImpl = new StudentServiceImpl();
		StudentVO studentDetails = studentServiceImpl.getStudentDetails(studentId);

		RestStudentVO restStudentVO = new RestStudentVO(studentDetails.getStudentId(),studentDetails.getFirstName(),studentDetails.getLastName(),studentDetails.getEmailId(),studentDetails.getPassword(),studentDetails.getUserFlag());
		restStudentVO.addLink(getUriForSelf(uriInfo, restStudentVO), "self");

		GenericEntity<RestStudentVO> entity = new GenericEntity<RestStudentVO>(restStudentVO) {};
		return Response.status(Status.OK).entity(entity).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scr.rest.StudentRestInterface#getStudentEnrolledCourses(java.lang.
	 * String)
	 */

	@Override
	@GET
	@Path("/{studentId}/courses")
	@Produces(MediaType.APPLICATION_JSON)
	@PermissionsTag({Constants.USER_STUDENT,Constants.USER_ADMIN})
	public Response getStudentEnrolledCourses(@PathParam("studentId") int studentId, @Context UriInfo uriInfo) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		StudentVO studentEnrollmentDetails = studentServiceImpl.getStudentEnrolledCourses(studentId);

		RestStudentVO restStudentVO = new RestStudentVO(studentEnrollmentDetails.getStudentId(),studentEnrollmentDetails.getFirstName(),studentEnrollmentDetails.getLastName(),studentEnrollmentDetails.getEmailId(),studentEnrollmentDetails.getPassword(),studentEnrollmentDetails.getUserFlag(),studentEnrollmentDetails.getCourseList());
		restStudentVO.addLink(getUriForSelf(uriInfo, restStudentVO), "self");
		restStudentVO.addLink(getUriForCourses(uriInfo, restStudentVO), "courses");

		GenericEntity<RestStudentVO> entity = new GenericEntity<RestStudentVO>(restStudentVO) {};
		return Response.status(Status.OK).entity(entity).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#login(java.lang.String,
	 * java.lang.String)
	 */

	@Override
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	//@PermitAll
	public Response login(RestStudentVO restStudent, @Context UriInfo uriInfo) {
		HttpSession session= req.getSession(true);
    	Object student = session.getAttribute("student");
    	String email = restStudent.getEmailId();
    	String password = restStudent.getPassword();
    	
		StudentService studentServiceImpl = new StudentServiceImpl();
		StudentVO studentVO = studentServiceImpl.login(email, password);
		
		if (student!=null) {
    		System.out.println(student.toString());
    	} else {
    		student = studentVO;
    		session.setAttribute("student", student);
    	}

		RestStudentVO restStudentVO = new RestStudentVO(studentVO.getStudentId(),studentVO.getFirstName(),studentVO.getLastName(),studentVO.getEmailId(),null,studentVO.getUserFlag());
		restStudentVO.addLink(getUriForSelf(uriInfo, restStudentVO), "self");

		GenericEntity<RestStudentVO> entity = new GenericEntity<RestStudentVO>(restStudentVO) {};
		 CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class
                 .getName());
		 
		 Cookie cookie = null;
         if (csrf != null) {
             cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
             cookie.setPath("/");
             
         }
		
//		NewCookie[] newCookie = new NewCookie[1];
//		NewCookie cookie2 = new NewCookie("XSRF-TOKEN", cookie.getValue());
//		newCookie[0] = cookie2;
		
		return Response.status(Status.OK).entity(entity).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#createStudent(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createStudent(RestStudentVO restStudentVO, @Context UriInfo uriInfo) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		
		StudentVO studentVO = new StudentVO(restStudentVO.getFirstName(), restStudentVO.getLastName(),
				restStudentVO.getEmailId(), restStudentVO.getPassword(), restStudentVO.getUserFlag());
		studentVO = studentServiceImpl.createStudent(studentVO);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(studentVO.getStudentId())).build();
		return Response.created(uri)
				.entity(studentVO).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#enrollCourse(java.lang.String,
	 * int, int)
	 */

	@Override
	@POST
	@Path("/{studentId}/enroll")
	@Consumes(MediaType.APPLICATION_JSON)
	@PermissionsTag({Constants.USER_STUDENT})
	public Response enrollCourse(RestStudentVO restStudentVO, @PathParam("studentId") int studentId, @QueryParam("courseId") int courseId,
			@QueryParam("scheduleId") int scheduleId, @Context UriInfo uriInfo) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		String statusMessage = studentServiceImpl.enrollCourse(studentId, courseId, scheduleId);

		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(studentId)).path(CourseResourceImpl.class).build();
		return Response.created(uri)
				.entity(restStudentVO)
				.entity(statusMessage)
				.build();
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#updateStudent(int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@PUT
	@Path("/{studentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@EntityTag
	@PermissionsTag({Constants.USER_STUDENT,Constants.USER_ADMIN})
	public Response updateStudent(@PathParam("studentId") int studentId, RestStudentVO restStudentVO, @Context Request request) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		String message = "";
		System.out.println("HashCode : " + restStudentVO.hashCode());
		StudentVO studentVO = new StudentVO(studentId, restStudentVO.getFirstName(), restStudentVO.getLastName(),
				restStudentVO.getEmailId(), restStudentVO.getPassword(), restStudentVO.getUserFlag(),new java.util.Date());
		message = studentServiceImpl.updateStudent(studentVO);
		StringBuffer statusMessage = new StringBuffer(message);
		statusMessage.append(" time ").append(new Date());
		return Response.status(Status.OK).entity(statusMessage.toString()).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#deleteStudent(java.lang.String)
	 */
	@Override
	@DELETE
	@Path("/{studentId}")
	@EntityTag
	@PermissionsTag(Constants.USER_ADMIN)
	public Response deleteStudent(@PathParam("studentId") int studentId, @Context UriInfo uriInfo) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		String statusMessage = "";
		statusMessage = studentServiceImpl.deleteStudent(studentId);
		if(statusMessage.contains(Constants.FAILURE_MESSAGE)){
			Response response = getStudentEnrolledCourses(studentId, uriInfo);
			RestStudentVO studentVO = (RestStudentVO)response.getEntity();
			return Response.status(Status.BAD_REQUEST).entity(studentVO).links(response.getLink("courses")).build();
		}
		return Response.status(Status.OK).entity(statusMessage).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.scr.rest.StudentRestInterface#dropCourse(java.lang.String, int,
	 * int)
	 */
	@Override
	@DELETE
	@Path("/drop")
	@PermissionsTag(Constants.USER_STUDENT)
	public Response dropCourse(@QueryParam("studentId") int studentId, @QueryParam("courseId") int courseId,
			@QueryParam("scheduleId") int scheduleId) {
		StudentService studentServiceImpl = new StudentServiceImpl();
		String statusMessage = "";
		statusMessage = studentServiceImpl.dropCourse(studentId, courseId, scheduleId);
		return Response.status(Status.OK).entity(statusMessage).build();
	}

	private String getUriForSelf(UriInfo uriInfo, RestStudentVO restStudentVO) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(StudentsResourceImpl.class)
				.path(Long.toString(restStudentVO.getStudentId()))
				.build()
				.toString();
		return uri;
	}

	private String getUriForCourses(UriInfo uriInfo, RestStudentVO RestStudentVO) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(StudentsResourceImpl.class)
				.path(Long.toString(RestStudentVO.getStudentId()))
				.path(CourseResourceImpl.class)
				.build()
				.toString();
		return uri;
	}
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest webRequest){
		HttpSession session = webRequest.getSession(false);
		String statusMessage = null;
		if(session!=null){
			session.invalidate();
			statusMessage = "User successfully logged out!!!";
		}
		return Response.status(Status.OK).entity(statusMessage).build();
	}
	
	@GET
	@Path("/session")
	public Response getUserObject(@Context HttpServletRequest webRequest){
		StudentVO session = (StudentVO)webRequest.getSession().getAttribute("student");
		return Response.status(Status.OK).entity(session).build();
	}


}
