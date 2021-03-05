package examples.pubhub.model;

public class Tag {
	
	private String isbn13;
	private String tag;
	
	
	//Default Constructor
	public Tag() {
		this.isbn13 = null;
		this.tag = null;
	}
	
	//Get ISBN-13 Number for tag
	public String getIsbn13() {
		return isbn13;
	}
	
	//Set ISBN=13 Number for tag
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13; 
	}
	
	//Get Tag Name
	public String getTag() {
		return tag;
	}
	
	//Set Tag Name
	public void setTag(String tag) {
		this.tag = tag;
	}
	

	

}
