package com.scr.service.impl;


import java.util.List;

import com.scr.dao.CurriculumDAO;
import com.scr.dao.impl.CurriculumDAOImpl;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.service.CurriculumService;
import com.scr.vo.CurriculumVO;


public class CurriculumServiceImpl implements CurriculumService {
	
	CurriculumDAO CurriculumDAO= new CurriculumDAOImpl();

	public CurriculumServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method returns the list of all the students.
	 * @return List<CurriculumVO>
	 */
	@Override
	public List<CurriculumVO> getAllCurriculum(){
		List<CurriculumVO> curriculumVOs = CurriculumDAO.getAllCurriculum();
		if(curriculumVOs==null || (curriculumVOs!=null && curriculumVOs.isEmpty())){
			throw new DataNotFoundException("No Curriculum Found");
		}
		return curriculumVOs;
	}
	
	@Override
	public List<CurriculumVO> getCourseCurriculum(int courseID) {
		List<CurriculumVO> curriculumVOs = CurriculumDAO.getCourseCurriculum(courseID);
		if(curriculumVOs==null || (curriculumVOs!=null && curriculumVOs.isEmpty())){
			throw new DataNotFoundException("No Curriculum Found");
		}
		return curriculumVOs;
	}
	
	/** 
	 * This method inserts data into Curriculum table
	 * @param curriculumVO
	 * @return CurriculumVO
	 */
	@Override
	public CurriculumVO addCurriculum(CurriculumVO curriculumVO){
		CurriculumVO result = CurriculumDAO.addCurriculum(curriculumVO);
		if(curriculumVO!=null && curriculumVO.getCurriculumId()>0){
			return result;
		}else{
			throw new ResourceNotCreatedException("Cannot add Curriculum");
		}
	}
	
	/**
	 * Updates the data in Curriculum table
	 * @param curriculumVO
	 * @return String
	 */
	@Override
	public String updateCurriculum(CurriculumVO curriculumVO) {
		String result = null;;
		if(curriculumVO!=null && curriculumVO.getCurriculumId()>0){
			result = CurriculumDAO.updateCurriculum(curriculumVO);
		}
		return result;
	}
	
	/**
	 * deletes the data based on curriculumId in Curriculum table
	 * @param curriculumId
	 * @return CurriculumVO
	 */
	@Override
	public String deleteCurriculum(int curriculumId){
		String result = CurriculumDAO.deleteCurriculum(curriculumId);
		return result;
	}

}
