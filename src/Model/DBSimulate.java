package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DBSimulate {
	
	private static int CATEGORY_COUNTER = 0;
	private static int RECORD_COUNTER = 0;
	
	private HashMap<Integer, Category> categories;
	private HashMap<Integer, Record> records;
	
	public DBSimulate() {
		categories = new HashMap<>();
		records = new HashMap<>();
	}
	
	public void addRecord(Record record) {
		records.put(RECORD_COUNTER++, new Record(RECORD_COUNTER, record.getSum(), record.getCurrency(), record.getCategory(), record.getDescription(), record.getDate()));
	}
	
	public void addCategory(Category category) {
		categories.put(CATEGORY_COUNTER++, new Category(CATEGORY_COUNTER, category.getName()));
	}
	
	
	public Record getRecord(Record record) {
		return records.get(record.getId());
	}
	
	public Category getCategory(Category category) {
		return categories.get(category.getId());
	}
	
	public void updateRecord(Record old, Record updated) {
		records.put(old.getId(), updated);
	}
	
	public void updateCategory(Category old, Category updated) {
		categories.put(old.getId(), updated);
	}
	
	
	
	public void deleteRecord(Record record) {
		records.remove(record.getId());
	}
	
	public void deleteCategory(Category category) {
		categories.remove(category.getId());
	}
	
	
	public ArrayList<Record> getAllRecords() {
		ArrayList<Record> result = new ArrayList<>();
		for(Integer key : records.keySet()) {
			result.add(records.get(key));
		}
		return result;
	}
	
	public ArrayList<Category> getAllCategories(){
		ArrayList<Category> result = new ArrayList<>();
		for(Integer key : categories.keySet()) {
			result.add(categories.get(key));
		}
		return result;
	}
	
	
	public String toString() {
		String result = "";
		result += "Categories:";
		for(Integer key : categories.keySet()) {
			result += "\n\t" + key + ".\t" + categories.get(key).toString();
		}
		
		result += "\nRecords:";
		for(Integer key : records.keySet()) {
			result += "\n\t" + key + ".\t" + records.get(key).toString();
		}
				
		
		return result;
	}
	
	
	
	

}
