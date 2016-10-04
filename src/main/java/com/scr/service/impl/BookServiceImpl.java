package com.scr.service.impl;

import java.util.List;

import com.scr.dao.BooksDAO;
import com.scr.dao.impl.BooksDAOImpl;
import com.scr.exception.DataNotFoundException;
import com.scr.exception.ResourceNotCreatedException;
import com.scr.service.BookService;
import com.scr.vo.BookVO;

public class BookServiceImpl implements BookService {

	private BooksDAO booksdao = new BooksDAOImpl();
	
	public BookServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<BookVO> getBooksList(){
		List<BookVO> bookvo = booksdao.getBooksList();
		if(bookvo==null){
			throw new DataNotFoundException("No Books Found");
		}else if(bookvo!=null && bookvo.isEmpty()){
			throw new DataNotFoundException("No Courses Found");
		}
		return bookvo;
	}
	
	@Override
	public BookVO addBook(BookVO bookvo){
		bookvo = booksdao.addBook(bookvo);
		if(bookvo.getBookName()!=null){
			return bookvo;
		}else{
			throw new ResourceNotCreatedException("Cannot add book");
		} 
		
	}
	
	@Override
	public String updateBookName(BookVO bookvo) {
		String result = booksdao.updateBookName(bookvo);
		return result;
	}
	
	@Override
	public String deleteBook(int bookID){
		String result = booksdao.deleteBook(bookID);
		return result;
	}
}
