package examples.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.*;
import examples.pubhub.model.*;
import examples.pubhub.utilities.*;

/**
 * Servlet implementation class AddTagServlet
 */
@WebServlet("/AddTag")
public class AddTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess;
		String isbn13 = request.getParameter("isbn13");
		String tag = request.getParameter("tag_name");


		BookTag bTag = new BookTag(isbn13,tag);
		
		BookTagDAO tDao = DAOUtilities.getBookTagDAO();

		if(bTag.getIsbn13() != "" && bTag.getTagName() != ""){
			isSuccess = tDao.addBookTag(bTag);
			request.setAttribute("book_tags", bTag);
			isSuccess = true;
		}else {
			//ASSERT: couldn't find book with isbn. Update failed.
			isSuccess = false;
		}

		if(isSuccess){
			request.getSession().setAttribute("message", "Book tag successfully added!");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		}else {
			request.getSession().setAttribute("message", "There was a problem adding this book tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}

}
