/**
 * 
 */
package com.scr.vo;

public class CurriculumVO {
	
	private int curriculumId;
	private String topicName;
	
	public CurriculumVO(){
		super();
	}
	/**
	 * @param curriculumId
	 */
	public CurriculumVO(int curriculumId) {
		super();
		this.curriculumId = curriculumId;
	}
	/**
	 * @param curriculumId
	 * @param topicName
	 */
	public CurriculumVO(int curriculumId, String topicName) {
		super();
		this.curriculumId = curriculumId;
		this.topicName = topicName;
	}
	/**
	 * @param topicName
	 */
	public CurriculumVO(String topicName) {
		super();
		this.topicName = topicName;
	}
	/**
	 * @return the curriculumId
	 */
	public int getCurriculumId() {
		return curriculumId;
	}
	/**
	 * @param curriculumId the curriculumId to set
	 */
	public void setCurriculumId(int curriculumId) {
		this.curriculumId = curriculumId;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" Curriculum ID : "+curriculumId);
		stringBuffer.append("Curriculum Name : "+topicName);
		return stringBuffer.toString();
	}

}
