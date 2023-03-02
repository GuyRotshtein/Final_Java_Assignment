package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import Exceptions.InvalidRecordException;


public class Record
{
	private int id;
	private double sum;
	private String currency;
	private Category category;
	private String description;
	private LocalDate date;
	
	public Record(double sum, String currency, Category category, String description, LocalDate date) {
		super();
		this.sum = sum;
		this.currency = currency;
		this.category = category;
		this.description = description;
		this.date = date;
	}
	
	public Record(int id, double sum, String currency ,Category category, String description, LocalDate date) {
		super();
		this.id = id;
		this.sum = sum;
		this.currency = currency;
		this.category = category;
		this.description = description;
		this.date = date;
	}

	public double getSum() {
		return sum;
	}
	public void setSum(double sum) throws InvalidRecordException {
		if(sum < 0) throw new InvalidRecordException(InvalidRecordException.INVALID_SUM_MSG);
		this.sum = sum;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDate() {
		return date;
	}

	public Date getRecordDate(){
		ZoneId defaultZoneID = ZoneId.systemDefault();
		Date actualDate = Date.from(getDate().atStartOfDay(defaultZoneID).toInstant());
		return actualDate;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

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

	@Override
	public String toString() {
		return "Record [id=" + id + ", sum=" + sum + ", category=" + category + ", description=" + description
				+ ", date=" + date + "]";
	}
	
	
	
	

}
