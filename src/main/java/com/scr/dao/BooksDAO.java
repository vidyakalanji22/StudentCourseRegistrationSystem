package com.scr.dao;

import java.sql.SQLException;
import java.util.List;

import com.scr.vo.BookVO;

public interface BooksDAO {
	/**
	 * This method adds book details to BOOKS table
	 * 
	 * @param bookName - bookName gives the name of the book
	 * @return - returns status message
	 * @throws SQLException 
	 */
	public BookVO addBook(BookVO bookVO);
	/**
	 * This method lists all the books 
	 * @return - returns books list
	 * @throws SQLException 
	 */
	public List<BookVO> getBooksList();
	/**
	 * This method updates the book name
	 * @param book_name
	 * @param book_id
	 * @return statusMessage
	 * @throws SQLException 
	 */
	public String updateBookName(BookVO bookVO);
	/**
	 * This method deletes the book
	 * @param bookName - bookName gives the name of the book
	 * @return - statusMessage and deletes the book
	 * @throws - Exception
	 */
	public String deleteBook(int bookID);
	
	/**
	 * Gets the Course Books
	 * @param connection
	 * @param courseId
	 * @return List<String>
	 */
	public List<BookVO> getCourseBooks(int courseId);
	
}
