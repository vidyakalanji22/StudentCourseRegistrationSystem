package com.scr.service;

import java.util.List;

import com.scr.vo.BookVO;

public interface BookService {

	/**
	 * This method returns the list of all the books.
	 * @return List<BookVO>
	 */
	List<BookVO> getBooksList();
	/**
	 * This method adds a new student record.
	 * @return String
	 */
	BookVO addBook(BookVO bookvo);
	/**
	 * This method updates the existing book name.
	 * @return String
	 */
	String updateBookName(BookVO bookvo);
	/**
	 * This method deletes the existing book.
	 * @return String
	 */
	String deleteBook(int bookID);
	

}