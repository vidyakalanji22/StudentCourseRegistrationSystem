package com.scr.service;

import java.util.List;

import com.scr.vo.CurriculumVO;

public interface CurriculumService {
	
	/**
	 * This method returns the list of all the students.
	 * @return List<CurriculumVO>
	 */
	List<CurriculumVO> getAllCurriculum();
	
	/**
	 * This method lists the curriculum details related to the particular courseID
	 * @param courseID
	 * 	@return list of the topic name related to the particular course ID
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseID);
	
	/** 
	 * This method inserts data into Curriculum table
	 * @param curriculumVO
	 * @return String
	 */
	CurriculumVO addCurriculum(CurriculumVO curriculumVO);
	
	/**
	 * Updates the data in Curriculum table
	 * @param curriculumVO
	 * @return String
	 */
	String updateCurriculum(CurriculumVO curriculumVO);
	
	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 * @return String
	 */
	String deleteCurriculum(int curriculumId);

}