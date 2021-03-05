package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import examples.pubhub.model.Book;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/BooksByTag")

public class BooksByTagServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String tag = request.getParameter("tagSearch");

		TagDAO dao = DAOUtilities.getTagDAO();
		List<Book> books = dao.getBooksByTag(tag);

		request.setAttribute("bookList", books);
		request.setAttribute("tag", tag);
		
		request.getRequestDispatcher("searchByTag.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}

