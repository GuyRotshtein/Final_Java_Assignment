package Model;

public class Category {
	/*id of the Category*/
	private int id;
	/*the name of the Category*/
	private String name;

	/*constructor get two paramter  : id and name*/
	public Category(int id, String name) {
		super();
		setId(id);
		setName(name);
	}

	/*constructor get one paramter name*/
	public Category(String name) {
		super();
		this.name = name;
	}
	

	/*return the id of the Catagory*/
	public int getId() {
		return id;
	}

	/*set the id to value of the parameter*/
	public void setId(int id) {
		this.id = id;
	}

	/*get the name od the catagory , return string*/
	public String getName() {
		return name;
	}

	/*change the name of the Category*/
	public void setName(String name) {
		this.name = name;
	}

	/*return all the fields of the Catagory as a string*/
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
