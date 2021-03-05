package examples.pubhub.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/RemoveTag")

public class RemoveTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		boolean isSuccess=false;
		
		String inputIsbn13 = request.getParameter("isbn13");
		String inputTag = request.getParameter("tag");
		
		Tag tag = new Tag();
		tag.setIsbn13(inputIsbn13);
		tag.setTag(inputTag);
		
		TagDAO tagdao = DAOUtilities.getTagDAO();
		isSuccess = tagdao.removeTag(tag);
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Book Tag successfully removed");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + inputIsbn13);
		}else {
			request.getSession().setAttribute("message", "There was a problem updating this tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
		
	}
	

}


		
