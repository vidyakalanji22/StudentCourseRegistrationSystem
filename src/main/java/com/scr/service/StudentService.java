package com.scr.service;

import java.util.List;

import com.scr.vo.StudentVO;

public interface StudentService {

	List<StudentVO> getStudents();

	StudentVO getStudentDetails(int studentId);

	StudentVO getStudentEnrolledCourses(int studentId);

	StudentVO createStudent(StudentVO studentVO);

	StudentVO login(String email, String password);

	String enrollCourse(int studentId, int courseId, int scheduleId);

	String updateStudent(StudentVO studentVO);

	String deleteStudent(int studentId);

	String dropCourse(int studentId, int courseId, int scheduleId);

}