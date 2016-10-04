/**
 * 
 */
package com.scr.vo;

public class BookVO {

	private int bookID;
	private String bookName;
	
	public BookVO() {
		super();
	}
	
	/**
	 * @param bookName
	 */
	public BookVO(String bookName) {
		super();
		this.bookName = bookName;
	}
	
	public BookVO(int bookID, String bookName) {
		this.bookID = bookID;
		this.bookName = bookName;
	}
	
	/**
	 * @param bookID
	 */
	public BookVO(int bookID) {
		super();
		this.bookID = bookID;
	}
	/**
	 * @return the bookID
	 */
	public int getBookID() {
		return bookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" Book ID : "+bookID);
		stringBuffer.append("Book Name : "+bookName);
		return stringBuffer.toString();
	}

}
