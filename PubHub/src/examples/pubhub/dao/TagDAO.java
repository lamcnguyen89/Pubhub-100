package examples.pubhub.dao;

import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Tag;
import examples.pubhub.model.Book;

public interface TagDAO {
	
	// Method to get a book based on the isbn present in in the BOOK TAG table
	public Tag getBookByISBN(String isbn);
	
	//A method to retrieve all books based on a tag
	public List<Tag> getAllBooks();
	
	//A method to retrieve all unique tags in the database
	public List<Tag> getAllTags();
	
	//A method to retrieve all books that have a given tag. 
	//Hint: This will require either a SQL JOIN statement or a nested query.
	public List<Book> getBooksByTag(String tag);
	
	//A method to retrieve all tags that have been added to a given book
	public List<Tag> getTagsByBook(String isbn);

	//A method to add a tag to a book, 
	//given the tag name and a reference to a book 
	//(either a Book reference variable or just an ISBN-13)
	public boolean addTag(Tag tag);
	
	//A method to remove a tag from a book, 
	//given the tag name and a reference to a book 
	//(either a Book reference variable or just an ISBN-13)
	public boolean removeTag(Tag tag);
	

}	

