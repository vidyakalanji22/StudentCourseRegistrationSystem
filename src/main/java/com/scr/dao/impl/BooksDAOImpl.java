package com.scr.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scr.dao.BooksDAO;
import com.scr.exception.ResourceAlreadyExistsException;
import com.scr.util.Constants;
import com.scr.util.DBConnectionManager;
import com.scr.util.PropertyLoader;
import com.scr.vo.BookVO;

public class BooksDAOImpl implements BooksDAO{

	private Properties dbQueries = PropertyLoader.getDbProperties();

	/**
	 * This method adds book details to BOOKS table
	 * 
	 * @param bookName - bookName gives the name of the book
	 * @return - returns status message
	 * @throws SQLException 
	 */
	public BookVO addBook(BookVO bookVO){

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String bookName = bookVO.getBookName();
		ResultSet rs = null;

		String statusMessage = "Error occurred while adding the book";
		try {
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			boolean bookExists = checkbookExists(connection, bookName);
			if (bookExists)
				throw new ResourceAlreadyExistsException("Book " + bookName + " already exists");

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("add.book"), PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, bookName);
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if(rs.next())
				bookVO.setBookID(rs.getInt(Constants.BOOK_ID));
			connection.commit();
			statusMessage = Constants.SUCCESS;
		} catch (SQLException e) {
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			e.printStackTrace();
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			e.printStackTrace();
			statusMessage = e.getMessage();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return bookVO;
	}

	/**
	 * This method checks if book ID already exists or not
	 * @param connection
	 * @param bookName
	 * @return boolean value
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private boolean checkbookIDExists(Connection connection, int bookID) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean bookExists = false;
		int count = 0;
		try {
			// Setting auto commit to false to avoid committing of data immediately after executing
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.book.id"));
			preparedStatement.setInt(1, bookID);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			//System.out.println("count = " + count);
			if (count > 0) {
				bookExists = true;
			}
		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		//System.out.println("bookExists ===== > " + bookExists);
		return bookExists;

	}

	/**
	 * This method checks if book name already exists or not
	 * @param connection
	 * @param bookName
	 * @return boolean value
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private boolean checkbookExists(Connection connection, String bookName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean bookExists = false;
		int count = 0;
		try {
			// Setting auto commit to false to avoid committing of data immediately after executing
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("check.book.name"));
			preparedStatement.setString(1, bookName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			//System.out.println("count = " + count);
			if (count > 0) {
				bookExists = true;
			}
		} catch (Exception exp) {
			System.out.println("Error : " + exp);
		} finally {
			DBConnectionManager.close(null, preparedStatement, resultSet);
		}
		//System.out.println("bookExists ===== > " + bookExists);
		return bookExists;

	}

	/**
	 * This method lists all the books 
	 * @return - returns books list
	 * @throws SQLException 
	 */
	public List<BookVO> getBooksList() {
		String bookName = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<BookVO> booksList = new ArrayList<BookVO>();
		try{
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("fetch.books.list"));
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				bookName = resultSet.getString(Constants.BOOK_NAME);		
				//Insertion
				booksList.add(new BookVO( resultSet.getInt(Constants.BOOK_ID),bookName));
			}
			//Printing
			System.out.println("The list of books:" +booksList);

		} catch(Exception e){
			System.out.println("Error occured while getting the details" +e.getMessage());
		}
		finally {
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return booksList;
	}

	/**
	 * This method updates the book name
	 * @param book_name
	 * @param book_id
	 * @return statusMessage
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String updateBookName(BookVO bookVO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage=null;

		try {
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("update.book.name"));

			preparedStatement.setString(1, bookVO.getBookName());
			preparedStatement.setInt(2, bookVO.getBookID() );
			preparedStatement.executeUpdate();
			statusMessage = Constants.SUCCESS;
			connection.commit();

		} catch (Exception exp) {
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			exp.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}

	/**
	 * This method deletes the book
	 * @param bookName - bookName gives the name of the book
	 * @return - statusMessage and deletes the book
	 * @throws - Exception
	 */
	public String deleteBook(int bookID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		//String bookName = bookVO.getBookName();
		try{
			// Connection to the database
			connection = DBConnectionManager.getConnection();
			// Setting auto commit to false to avoid committing of data immediately after executing
			connection.setAutoCommit(false);
			boolean bookExists = checkbookIDExists(connection, bookID);
			if (bookExists==true)
			{
				preparedStatement = connection.prepareStatement(dbQueries.getProperty("delete.book"));
				preparedStatement.setInt(1, bookID);

				// Executing the delete operation
				preparedStatement.executeUpdate();
				connection.commit();
				statusMessage = Constants.SUCCESS;
			}else{
				System.out.println("Book does not exist!!!!!!!!!!!!");
				statusMessage = Constants.FAILURE;
			}
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			statusMessage = Constants.FAILURE;
		} catch (Exception e) {
			statusMessage = Constants.FAILURE;
		} finally {
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}	
	/**
	 * Gets the Course Books
	 * @param connection
	 * @param courseId
	 * @return List<String>
	 */
	public List<BookVO> getCourseBooks(int courseId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<BookVO> booksList = null;
		Connection connection = null;

		try{
			connection = DBConnectionManager.getConnection();

			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.get.books"));
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();
			booksList =new ArrayList<BookVO>();
			while(resultSet.next()){
				booksList.add(new BookVO(Constants.BOOK_NAME));
			}

		}catch(SQLException sqlExp){
			System.out.println("SQLException occurred in getCourseBooks() " + sqlExp.getMessage());
		}catch(Exception exp){
			System.out.println("Exception occurred in getCourseBooks() "+exp.getMessage());
		}finally{
			DBConnectionManager.close(connection, preparedStatement, resultSet);
		}
		return booksList;
	}

	/**
	 * Inserts books for a course
	 * @param connection
	 * @param courseId
	 * @param booksList
	 * @return boolean
	 * @throws Exception
	 */
	public String addCourseBook(int courseId, List<BookVO> booksList) throws Exception {
		PreparedStatement preparedStatement = null;
		String statusMessage = null;
		Connection connection = null;
		try{
			connection = DBConnectionManager.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(dbQueries.getProperty("course.insert.book"));
			for(BookVO bookVO : booksList){
				preparedStatement.setInt(1, courseId);
				preparedStatement.setInt(2, bookVO.getBookID());
				preparedStatement.addBatch();
			}
			int count[] = preparedStatement.executeBatch();
			connection.commit();
			if(count.length > 0)
				statusMessage = Constants.SUCCESS;
		}catch(SQLException sqlExp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("SQLException occurred in insertCourseBook() " + sqlExp.getMessage());
		}catch(Exception exp){
			statusMessage = Constants.FAILURE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(statusMessage);
			}
			System.out.println("Exception occurred in insertCourseBook() "+exp.getMessage());
			throw exp;
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBConnectionManager.close(connection, preparedStatement, null);
		}
		return statusMessage;
	}
 
}


