/**
 * 
 */
package com.scr.dao;

import java.util.List;

import com.scr.exception.ConstrainViolationException;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.GenericException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.vo.StudentVO;

public interface StudentsDAO {

	/**
	 * This method adds student details to STUDENT table
	 * 
	 * @param studentVO
	 *            - studentVO contains student details which needs to be
	 *            persisted
	 * @return - returns status message
	 */
	public StudentVO createStudent(StudentVO studentVO) throws ResourceAlreadyExistsException, ResourceNotCreatedException, GenericException;
	
	/**
	 * This method updates student details
	 * @param studentVO
	 */
	public String updateStudent(StudentVO studentVO) throws DataNotFoundException, GenericException;
	
	/**
	 * This method deletes student's record from STUDENTS table
	 * @param studentVO
	 * @return status message stating success or failure
	 */
	public String deleteStudent(int studentId) throws  DataNotFoundException, GenericException, ConstrainViolationException;
	
	
	/**
	 * This method returns all the students details
	 * @return list of students
	 * @throws Exception 
	 */
	public List<StudentVO> getStudents() throws GenericException;
	
	
	/**
	 * This method a student's details
	 * @param studentVO
	 * @return studentVO object 
	 */
	public StudentVO getStudentDetails(int studentId)throws DataNotFoundException, GenericException;
	
	/**
	 * This method a student's details
	 * @param studentVO
	 * @return studentVO object 
	 */
	public StudentVO login(String email, String password) throws DataNotFoundException, GenericException;
	
	
	/**
	 * This method lists course details which are enrolled by a student
	 * @param email
	 * @return studentVO contains student details and course details
	 */
	public StudentVO getStudentEnrolledCourses(int studentId) throws DataNotFoundException, GenericException;


	/**
	 * This method checks if student has already enrolled for a course or not.
	 * If not student will be enrolled for a course
	 * 
	 * @param email
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	public String enrollCourse(int studentId, int courseId, int scheduleId)  throws ResourceAlreadyExistsException, DataNotFoundException, GenericException;
	
	/**
	 * This method drops course to which student enrolled earlier
	 * 
	 * @param email
	 * @param courseId
	 * @param scheduleId
	 * @return status message
	 */
	public String dropCourse(int studentId, int courseId, int scheduleId) throws GenericException;


}
