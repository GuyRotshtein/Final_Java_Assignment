package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DBConnection {
	
	private static DBSimulate db;
	
	
	public static void connectDB() {
		db = new DBSimulate();
		loadMockData();
	}
	
	// Record queries
	public static void addRecord(Record record) {
		db.addRecord(record);
	}
	public static void deleteRecord(Record record) {
		db.deleteRecord(record);
	}
	public static void updateRecord(Record old, Record updated) {
		db.updateRecord(old, updated);
	}
	public static Record getRecord(Record record) {
		Record queryResult = db.getRecord(record);
		return createRecordFromQuery(queryResult);
		
	}
	public static ArrayList<Record> getAllRecords() {
		ArrayList<Record> result = new ArrayList<>();
		for(Record record : db.getAllRecords()) {
			result.add(createRecordFromQuery(record));
		}
		return result;
	}
	public static void getRecordsFromDateToDate(Date from, Date to) {
		
	}
	
	public static Record createRecordFromQuery(Record queryResult) {
		int id = queryResult.getId();
		Category category = new Category(queryResult.getCategory().getId(), queryResult.getCategory().getName());
		String description = queryResult.getDescription();
		double sum = queryResult.getSum();
		String currency = queryResult.getCurrency();
		return new Record(id, sum, currency ,category, description, queryResult.getDate());
	}
	
	// Category queries
	public static ArrayList<Category> getAllCategories() {
		ArrayList<Category> result = new ArrayList<>();
		for(Category category : db.getAllCategories()) {
			result.add(createCategoryFromQuery(category));
		}
			
		return db.getAllCategories();
	}
	public static Category getCategory(Category category) {
		Category queryResult = db.getCategory(category);
		return createCategoryFromQuery(queryResult);
	}
	public static void addCategory(Category category) {
		db.addCategory(new Category(category.getId(), category.getName()));
	}
	public static void deleteCategory(Category category) {
		db.deleteCategory(category);
		
	}
	
	public static Category createCategoryFromQuery(Category queryResult) {
		int id = queryResult.getId();
		String name = queryResult.getName();
		return new Category(id, name);
	}
	
	// Mock Data
	public static void loadMockData() {
		Category clothing = new Category("Clothing");
		Category bills = new Category("Bills");
		Category food = new Category("food");
		Category entertainment = new Category("etertainment");
		Category games = new Category("Games");
		
		addCategory(clothing);
		addCategory(bills);
		addCategory(food);
		addCategory(entertainment);
		addCategory(games);
		
		String currency = "ILS";
		
		Record rec1 = new Record(150, currency ,games, "Hogwarts Legacy", LocalDate.of(2023, 2, 15));
		Record rec2 = new Record(2000, currency ,bills, "Rent", LocalDate.of(2023, 2, 11));
		Record rec3 = new Record(1500, currency ,food, "Junk Food", LocalDate.of(2023, 2, 5));
		Record rec4 = new Record(50, currency ,entertainment, "Netflix", LocalDate.of(2023, 2, 1));
		Record rec5 = new Record(30, currency ,entertainment, "Spotify", LocalDate.of(2023, 2, 1));
		Record rec6 = new Record(300, currency ,entertainment, "Concert", LocalDate.of(2023, 2, 8));
		Record rec7 = new Record(3000, currency ,bills, "Tax", LocalDate.of(2023, 2, 20));
		Record rec8 = new Record(500, currency ,entertainment, "Plane tickets", LocalDate.of(2023, 2, 20));
		
		addRecord(rec1);
		addRecord(rec2);
		addRecord(rec3);
		addRecord(rec4);
		addRecord(rec5);
		addRecord(rec6);
		addRecord(rec7);
		addRecord(rec8);
		
		System.out.println(db);		
	}
	
	public static DBSimulate getDb(){
		return db;
	}
	
	
	
}

