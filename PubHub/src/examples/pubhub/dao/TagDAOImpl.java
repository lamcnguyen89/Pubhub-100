package examples.pubhub.dao;

//import java.sql.Blob;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
//I've got to find out what these are for:
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * 
 * Implementation for TagDAO, responsible for querying and CRUD functions involving book tags
 *
 */
public class TagDAOImpl implements TagDAO {

	Connection connection = null; // Our connection to the database
	PreparedStatement stmt = null; // We use prepared statements to help protect against SQL injection
	
	/*__________________________________________________________*/
	
	@Override
	public List<Tag> getAllBooks() {
		
		List<Tag> books = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT isbn_13 FROM book_tags";
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Tag book = new Tag();
				
				book.setIsbn13(rs.getString("isbn_13"));
				
				books.add(book);

			}
			
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return books;	
		
	}
	
	/*__________________________________________________________*/
	
	@Override
	public Tag getBookByISBN(String isbn) {
		Tag book = null;

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				book = new Tag();
				book.setIsbn13(rs.getString("isbn_13"));
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return book;
	}
	
	/*__________________________________________________________*/
	
	@Override
	public List<Tag> getAllTags() {
		
		List<Tag> tags = new ArrayList<> ();
		
		try {
			
			connection = DAOUtilities.getConnection(); // Get database connection from the manager
			String sql = "SELECT tags from book_tags"; // Our SQL Query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery(); // Queries the database
			
			// So long as the resultSet actually contains results...
			while (rs.next()) {
				// We need to populate a Tag object with info for each row from our query result.
				Tag tag = new Tag();
				
				// Each variable in our Tag object maps to a column in a row from our results.
				tag.setTag(rs.getString("tags"));
								
				// Finally we add it to the list of tag objects returned by this query
				tags.add(tag);
			}
			
			rs.close();
				
		} catch(SQLException e) {
			e.printStackTrace();
			
		} finally {
			// We need to make sure our statements and connections are closed,
			// Or else a memory leak could occur
			closeResources();
		}
		
		return tags;
	}
	
	/*__________________________________________________________*/
	
	@Override
	public List<Book> getBooksByTag(String tag) {
		List<Book> books = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM books WHERE isbn_13 IN (SELECT isbn_13 FROM book_tags WHERE tags = ?)";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tag);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Book newbook = new Book();
				
				newbook.setIsbn13(rs.getString("isbn_13"));
				newbook.setAuthor(rs.getString("author"));
				newbook.setTitle(rs.getString("title"));
				newbook.setPublishDate(rs.getDate("publish_date").toLocalDate());
				newbook.setPrice(rs.getDouble("price"));
				newbook.setContent(rs.getBytes("content"));

				books.add(newbook);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return books;
	}
	
	/*__________________________________________________________*/
	
	@Override
	public List<Tag> getTagsByBook(String isbn13) {
		
		List<Tag> tags = new ArrayList<> ();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Tag tag = new Tag();
				
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setTag(rs.getString("tags"));
				
				tags.add(tag);
			}
			rs.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
		
	}
	
	/*__________________________________________________________*/
	
	@Override
	public boolean addTag(Tag tag) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO book_tags VALUES (?,?)"; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			//But that's okay, we can set them all before we execute
			stmt.setString(1, tag.getIsbn13());
			stmt.setString(2, tag.getTag());
	
			
			// If we were able to add our book to the DB, we want to return true.
			// This if statement both executes our query, and looks at the return
			// value  to determine how many rows were changed
			if (stmt.executeUpdate() !=0)
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
	
	/*__________________________________________________________*/
	
	@Override
	public boolean removeTag(Tag tag) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tags WHERE isbn_13=? AND tags=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tag.getIsbn13());
			stmt.setString(2, tag.getTag());
			
			if (stmt.executeUpdate() !=0)
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
	/*__________________________________________________________*/

	// Closing all resources is important, to prevent memory leaks.
	// Ideally, you really want to close them in the reverse order you open them
	private void closeResources() {
		
		try {
			if (stmt !=null)
				stmt.close();
		} catch(SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection !=null)
				connection.close();
		} catch(SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
		
	}
	
	/*__________________________________________________________*/

}
