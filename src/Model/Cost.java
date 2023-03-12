package Model;
import Exceptions.InvalidRecordException;

import java.sql.Date;


public class Cost
{
	/*the id Cost*/
	private int id;

	/*the sum of the Cost*/
	private double sum;

	/*The currency type of the expense*/
	private String currency;

	/*What category does the expense belong to,from the Category options*/
	private Category category;

	/*Describes what the money was spent on*/
	private String description;

	/*The date the Cost occurred*/
	private Date date;

	/*
		the constructor of the cost getting  all fields as arguments :
		sum as Double
		Currency as string
		Category  as category
		Description as string
		Date as sql date


	*/
	public Cost(double sum, String currency, Category category, String description, Date date) {
		super();
		this.sum = sum;
		this.currency = currency;
		this.category = category;
		this.description = description;
		this.date = date;
	}

	/*constructor with all  parameters  and add id to him*/
	public Cost(int id, double sum, String currency ,Category category, String description, Date date) {
		super();
		this.id = id;
		this.sum = sum;
		this.currency = currency;
		this.category = category;
		this.description = description;
		this.date = date;
	}

	/*return the sum of the Cost as double*/
	public double getSum() {
		return sum;
	}

	/*change  the sum of the Cost*/
	public void setSum(double sum) throws InvalidRecordException {
		if(sum < 0) throw new InvalidRecordException(InvalidRecordException.INVALID_SUM_MSG);
		this.sum = sum;
	}

	/*get the Category of the Cost as Category*/
	public Category getCategory() {
		return category;
	}

	/*change  the Category*/
	public void setCategory(Category category) {
		this.category = category;
	}

	/*get the description of the Cost as string ,explained what you bought*/
	public String getDescription() {
		return description;
	}

	/* change the description of the Cost*/
	public void setDescription(String description) {
		this.description = description;
	}

	/*get Date of the Cost*/
	public Date getDate() {
		return date;
	}

	/*change of the date to other date*/
	public void setDate(Date date) {
		this.date = date;
	}


	/*get the id of the Cost*/
	public int getId() {
		return id;
	}

	/*change  the id to other id number*/
	public void setId(int id) {
		this.id = id;
	}

	/*get the Currency of the Cost*/
	public String getCurrency() {
		return currency;
	}

	/*change the Currency of the Cost*/
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/* get all data cost as array of  string*/
	public String[] getDataArr(){
		String result[] = new String[6];
		result[0] = Integer.toString(this.id);
		result[1] = Double.toString(this.sum);
		result[2] = this.currency;
		result[3] = this.category.getName();
		result[4] = this.description;
		result[5] = this.date.toString();
		return result;
	}

	/*get the data as string*/
	@Override
	public String toString() {
		return "Record [id=" + id + ", sum=" + sum + ", category=" + category + ", description=" + description
				+ ", date=" + date + "]";
	}
	
	
	
	

}
