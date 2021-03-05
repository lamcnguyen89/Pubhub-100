package examples.pubhub.dao;

import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;

/**
 * Interface for our Data Access Object to handle database queries related to Book Tags.
 */
public interface BookTagDAO {

	public ArrayList<BookTag> getAllBookTags(Book book); //retrieve all tags that have been added to a given book
	public ArrayList<Book> getAllBooksByTag(String tag); //retrieve all books that have a given tag
	
	
	public boolean addBookTag(BookTag bTag);
	public boolean removeBookTag(BookTag bTag);

}
