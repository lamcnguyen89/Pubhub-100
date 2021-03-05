package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {
	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	@Override
	public ArrayList<BookTag> getAllBookTags(Book book) {
		
		ArrayList<BookTag> bookTagList = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Book_Tags WHERE isbn_13=?";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			stmt.setString(1, book.getIsbn13());
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate a Book object with info for each row from our query result
				BookTag bookT = new BookTag();
				bookT.setIsbn13(rs.getString("isbn_13"));
				bookT.setTagName(rs.getString("tag_name"));

				// Each variable in our Book object maps to a column in a row from our results.
				bookTagList.add(bookT);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return bookTagList;
	}
	
	public ArrayList<Book> getAllBooksByTag(String tag) {
			
		
		ArrayList<Book> book = new ArrayList<>();
			try {
				
				connection = DAOUtilities.getConnection();
				
				String sql = "SELECT * FROM Books WHERE isbn_13 IN (SELECT isbn_13 from Book_Tags WHERE tag_name ILIKE ?)";	// Note the ? in the query
				stmt = connection.prepareStatement(sql);
				
				// This command populate the 1st '?' with the title and wildcards, between ' '
				stmt.setString(1, tag);	
				
				ResultSet rs = stmt.executeQuery();
				Book book1= null;
		         while(rs.next()){
		             book1 = new Book();
		             book1.setIsbn13(rs.getString("isbn_13"));
		 			book1.setTitle(rs.getString("title"));
		 			book1.setAuthor(rs.getString("author"));
		 			book1.setPublishDate(LocalDate.now());
		 			book1.setPrice(Double.parseDouble(rs.getString("price")));
		             book.add(book1); 

		         }
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}
			
			return book;

		
	}

	@Override
	public boolean addBookTag(BookTag bTag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql ="INSERT INTO Book_Tags(isbn_13, tag_name) VALUES (?, ?)" ; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, bTag.getIsbn13());
			stmt.setString(2, bTag.getTagName());
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean removeBookTag(BookTag bTag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM Book_Tags WHERE tag_name=? AND isbn_13 =?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, bTag.getTagName());
			stmt.setString(2, bTag.getIsbn13());

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
