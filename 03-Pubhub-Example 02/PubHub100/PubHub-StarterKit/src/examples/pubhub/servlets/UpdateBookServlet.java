package examples.pubhub.servlets;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/UpdateBook")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		
		BookDAO dao = DAOUtilities.getBookDAO();
		BookTagDAO btdao = DAOUtilities.getBookTagDAO();
		Book book = dao.getBookByISBN(isbn13);
		if(book != null){
// The only fields we want to be updatable are title, author and price. A new ISBN has to be applied for
			// And a new edition of a book needs to be re-published.
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPrice(Double.parseDouble(request.getParameter("price")));
			dao.updateBook(book);
			request.setAttribute("book", book);
			isSuccess = true;
		}else {
			//ASSERT: couldn't find book with isbn. Update failed.
			isSuccess = false;
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		}else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}

}
