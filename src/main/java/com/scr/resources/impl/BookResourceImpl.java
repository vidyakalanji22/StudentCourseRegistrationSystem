package com.scr.resources.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.scr.resources.BookResource;
import com.scr.resources.beans.RestBookVO;
import com.scr.service.BookService;
import com.scr.service.impl.BookServiceImpl;
import com.scr.vo.BookVO;

@Path("/books")
public class BookResourceImpl implements BookResource{
	BookService bookservice = new BookServiceImpl();

	/**
	 * This method returns the list of all the books.
	 * @return Response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks(@Context UriInfo uriinfo){
		List<RestBookVO> restbookVO = new ArrayList<RestBookVO>(); 
		Response response = null;

		List<BookVO> bookVO = bookservice.getBooksList();
		// Printing the values
		for (BookVO bookvo : bookVO) {
			RestBookVO restbookVOs = new RestBookVO(bookvo.getBookID(), bookvo.getBookName());
			// Adding link
			//restbookVOs.addLink(getUriForSelf(uriinfo, restbookVOs), "self");
			restbookVO.add(restbookVOs);
		}
		GenericEntity<List<RestBookVO>> entity = new GenericEntity<List<RestBookVO>>(restbookVO){};
		// Binding the status and entity
		response = Response.status(Status.OK).entity(entity).build();
		return response;

	}

	/**
	 * This method adds a new student record.
	 * @return Response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBook(RestBookVO bookbean, @Context UriInfo uriinfo){
		Response response = null;	
		BookVO bookvo = new BookVO(bookbean.getBookName());
		bookvo = bookservice.addBook(bookvo);

		// Setting status code and location headers
		String newID = String.valueOf(bookvo.getBookID());
		URI uri = uriinfo.getAbsolutePathBuilder().path(newID).build();
		response = Response.created(uri).entity(bookvo).build();

		return response;
	}

	/**
	 * This method updates the existing book name.
	 * @return Response
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBookName(RestBookVO bookbean, @Context UriInfo uriinfo){
		String status = null;
		Response response = null;
		BookVO bookvo = new BookVO(bookbean.getBookID(), bookbean.getBookName());
		status = bookservice.updateBookName(bookvo);

		// Setting status code and location headers
		String newId = String.valueOf(bookvo.getBookID());
		URI uri = uriinfo.getAbsolutePathBuilder().path(newId).build();
		response = Response.status(Status.NO_CONTENT).header(status, uri).build();

		return response;
	}

	/**
	 * This method deletes the existing book.
	 * @return Response
	 */
	@DELETE
	@Path("/{bookID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBook(@PathParam("bookID") int bookID,@Context UriInfo uriinfo){
		String status = null;
		Response response = null;
		status = bookservice.deleteBook(bookID);

		// Setting status code and location headers
		String newId = String.valueOf(bookID);
		URI uri = uriinfo.getAbsolutePathBuilder().path(newId).build();
		response = Response.status(Status.OK).header(status, uri).build();
		return response;
	}

//	private String getUriForSelf(UriInfo uriInfo, RestBookVO restbookVO) {
//		String uri = uriInfo.getBaseUriBuilder()
//				.path(BookResourceImpl.class)
//				.path(Long.toString(restbookVO.getBookID()))
//				.build()
//				.toString();
//		return uri;
//	}
}  


