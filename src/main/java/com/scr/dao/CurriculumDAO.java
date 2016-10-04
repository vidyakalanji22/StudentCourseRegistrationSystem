package com.scr.dao;

import java.util.List;

import com.scr.vo.CurriculumVO;


public interface CurriculumDAO {				

	/**
	 * This method lists all the curriculum details
	 * @param Topic_Name
	 * @return map
	 */
	public List<CurriculumVO> getAllCurriculum();

	/**
	 * This method lists the curriculum details related to the particular courseID
	 * @param courseID
	 * 	@return list of the topic name related to the particular course ID
	 */
	public List<CurriculumVO> getCourseCurriculum(int courseID);


	/**
	 * Inserts courseId and curriculum_id into course_curriculum table
	 * @param courseId
	 * @param curriculumList
	 * @return  statusmessage 
	 */
	public String addCourseCurriculum(int courseId, List<CurriculumVO> curriculumList);

	/** 
	 * This method inserts data into Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return statusmessage
	 */
	public CurriculumVO addCurriculum(CurriculumVO curriculumVO);



	/**
	 * Updates the data in Curriculum table
	 * @param curriculumId
	 * @param topicName
	 * @return String
	 */
	public String updateCurriculum(CurriculumVO curriculumVO);


	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 */
	public String deleteCurriculum(int curriculumId);



}


