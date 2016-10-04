package com.scr.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.scr.resources.beans.RestBookVO;

public interface BookResource {

	/**
	 * This method returns the list of all the books.
	 * @return Response
	 */
	Response getBooks(UriInfo uriinfo);

	/**
	 * This method adds a new student record.
	 * @return Response
	 */
	Response addBook(RestBookVO bookbean, UriInfo uriinfo);

	/**
	 * This method updates the existing book name.
	 * @return Response
	 */
	Response updateBookName(RestBookVO bookbean, UriInfo uriinfo);

	/**
	 * This method deletes the existing book.
	 * @return Response
	 */
	Response deleteBook(int bookID, UriInfo uriinfo);

}