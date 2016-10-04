package com.scr.service.impl;

import java.util.List;

import com.scr.dao.StudentsDAO;
import com.scr.dao.impl.StudentDAOImpl;
import com.scr.exception.ConstrainViolationException;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.GenericException;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.service.StudentService;
import com.scr.util.CommonUtils;
import com.scr.util.Constants;
import com.scr.vo.StudentVO;

public class StudentServiceImpl implements StudentService {
	private StudentsDAO studentDAO = new StudentDAOImpl();
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#getStudents()
	 */
	@Override
	public List<StudentVO> getStudents(){
		List<StudentVO> studentVOList =null;
		try {
			studentVOList = studentDAO.getStudents();
			if(studentVOList == null || studentVOList.isEmpty())
				throw new DataNotFoundException(Constants.NO_DATA_MESSAGE);
			
		} catch (GenericException gExp) {
			throw gExp;
		}
		return studentVOList;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#getStudentDetails(int)
	 */
	@Override
	public StudentVO getStudentDetails(int studentId){
		StudentVO studentVO = null;
		try {
			studentVO = studentDAO.getStudentDetails(studentId);
		}catch (GenericException gExp) {
			throw gExp;
		}
		return studentVO;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#getStudentEnrolledCourses(int)
	 */
	@Override
	public StudentVO getStudentEnrolledCourses(int studentId){
		StudentVO studentVO = null;
		try {
			studentVO = studentDAO.getStudentEnrolledCourses(studentId);
		} catch (GenericException gExp) {
			throw gExp;
		}
		return studentVO;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#createStudent(com.scr.vo.StudentVO)
	 */
	@Override
	public StudentVO createStudent(StudentVO studentVO){
		StudentVO studentVo = null;
		try {
			/**
			 * Password Encryption
			 */
			String encryptedPassword = CommonUtils.encrypt(Constants.KEY, Constants.INTVECTOR, studentVO.getPassword());
			studentVO.setPassword(encryptedPassword);
			studentVo = studentDAO.createStudent(studentVO);
			System.out.println("After creating Student" + studentVo.getStudentId());
		}catch (ResourceAlreadyExistsException exp) {
			throw new ResourceAlreadyExistsException("Student with emailId: " + studentVO.getEmailId() + " already exists");
		}
		return studentVo;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public StudentVO login(String email, String password) {
		StudentVO studentVO = null;
		try{
			
			/**
			 * Compare the digests
			 */
			
			String encryptedPassword = CommonUtils.encrypt(Constants.KEY, Constants.INTVECTOR, password);
			
			studentVO = studentDAO.login(email, encryptedPassword);
		}catch (GenericException gExp) {
			throw gExp;
		}
		return studentVO;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#enrollCourse(int, int, int)
	 */
	@Override
	public String enrollCourse(int studentId, int courseId, int scheduleId) {
		String statusMessage = "";
		try {
			statusMessage = studentDAO.enrollCourse(studentId, courseId, scheduleId);
			if(!statusMessage.equalsIgnoreCase(Constants.SUCCESS)){
				throw new GenericException(statusMessage);
			}
		} catch (GenericException exp) {
			throw new GenericException(exp.getMessage());
		}
		return statusMessage;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#updateStudent(com.scr.vo.StudentVO)
	 */
	@Override
	public String updateStudent(StudentVO studentVO) {
		String statusMessage = "";
		try {
			String encryptedPassword = CommonUtils.encrypt(Constants.KEY, Constants.INTVECTOR, studentVO.getPassword());
			studentVO.setPassword(encryptedPassword);
			statusMessage = studentDAO.updateStudent(studentVO);
		}catch (DataNotFoundException dnfExp) {
			throw dnfExp;
		}catch (GenericException gExp) {
			throw gExp;
		}
		return statusMessage;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#deleteStudent(int)
	 */
	@Override
	public String deleteStudent(int studentId) {
		String statusMessage = "";
		try {
			statusMessage = studentDAO.deleteStudent(studentId);
		}catch(ConstrainViolationException cvExp){ 
			throw cvExp;
		}catch (DataNotFoundException dfnExp) {
			throw dfnExp;
		}catch (GenericException gExp) {
			throw gExp;
		}
		return statusMessage;
	}
	
	/* (non-Javadoc)
	 * @see com.scr.service.impl.StudentService#dropCourse(int, int, int)
	 */
	@Override
	public String dropCourse(int studentId, int courseId, int scheduleId){
		String statusMessage = "";
		try {
			statusMessage = studentDAO.dropCourse(studentId, courseId, scheduleId);
			if(!statusMessage.equalsIgnoreCase(Constants.SUCCESS)){
				throw new ResourceNotCreatedException(statusMessage);
			}
		} catch (GenericException gExp) {
			throw gExp;
		}
		return statusMessage;
	}
	
}
