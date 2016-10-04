package com.scr.util;

public class SqlQueryConstants {
	
	public static final String SELECT_ALL_STUDENTS_QUERY = "SELECT * FROM STUDENTS";
	public static final String SELECT_ALL_COURSES_QUERY = "SELECT * FROM COURSES";
	public static final String COURSES_QUERY = "SELECT course_id, course_name, course_amount, course_desc FROM COURSES";
	public static final String INSERT_STUDENT_QUERY = "INSERT INTO students(first_name,last_name, email, password) VALUES (?,?,?,?)";
	public static final String INSERT_COURSE_QUERY = "INSERT INTO courses(course_name,amount) VALUES (?,?)";
	public static final String DELETE_COURSE_QUERY = "";
	public static final String GET_STUDENT_ID_QUERY = "SELECT * FROM students WHERE email=?";
	public static final String GET_COURSE_ID_QUERY = "SELECT course_id FROM courses WHERE COURSE_NAME=?";
	public static final String GET_SCHEDULE_ID_QUERY = "SELECT schedule_id from schedule WHERE start_date=?,end_date=?,start_time=?,end_time=?";
	public static final String CREATE_STUDENT_COURSE_QUERY = "INSERT INTO student_course_schedule(student_id,course_id,schedule_id) VALUES (?,?,?)";
	public static final String GET_CURRICULUM_ID_QUERY = "SELECT curriculum_id FROM course_curriculum WHERE course_id=?";
	public static final String COURSE_SCHEDULE = "select s.start_date, s.end_date, s.start_time, s.end_time from course_schedule cs, courses c, schedule s WHERE "
 + " cs.course_id = c.course_id and cs.schedule_id = s.schedule_id and c.course_id=1 and c.course_enabled_flag='Y' ";
	public static final String DELETE_BOOK_QUERY = "DELETE from books where book_name=?";
}
