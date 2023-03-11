package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBConnection {
	
	private static DBSimulate db;
	



	public static void connectDB() {
		db = new DBSimulate();
		loadMockData();
	}
	
	// Record queries
	public static void addCost(Cost cost) {
		db.addCost(cost);
	}
	public static void deleteCost(Cost cost) {
		db.deleteRecord(cost);
	}
	public static void updateCost(Cost old, Cost updated) {
		db.updateCost(old, updated);
	}
//	public static Cost getCost(Cost cost) {
//		Cost queryResult = db.getCost(cost);
//		return createCostFromQuery(queryResult);
//
//	}
	public static ArrayList<Cost> getAllCost() {
		ArrayList<Cost> result = new ArrayList<>();
		for(Cost cost : db.getAllCost()) {
			result.add(createCostFromQuery(cost));
		}
		return result;
	}
	public static void getRecordsFromDateToDate(Date from, Date to) {
		
	}
	
	public static Cost createCostFromQuery(Cost queryResult) {
		int id = queryResult.getId();
		Category category = new Category(queryResult.getCategory().getId(), queryResult.getCategory().getName());
		String description = queryResult.getDescription();
		double sum = queryResult.getSum();
		String currency = queryResult.getCurrency();
		return new Cost(id, sum, currency ,category, description, queryResult.getDate());
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
		Category food = new Category("Food");
		Category entertainment = new Category("Entertainment");
		Category games = new Category("Games");
		
		addCategory(clothing);
		addCategory(bills);
		addCategory(food);
		addCategory(entertainment);
		addCategory(games);
		
		String currency = "ILS";



		Cost rec1 = new Cost(
				150, currency ,games,
				"Hogwarts Legacy",
				new Date(2023, 2, 15)
		);
		Cost rec2 = new Cost(
				2000, currency ,bills,
				"Rent",
				new Date(2023, 2, 11));

		Cost rec3 = new Cost(
				1500,currency ,food,
				"Junk Food",
				new Date(2023, 2, 5));

		Cost rec4 = new Cost(
				50, currency ,entertainment,
				"Netflix",
				new Date(2023, 2, 1));

		Cost rec5 = new Cost(
				30, currency ,entertainment,
				"Spotify",
				new Date(2023,2,	1));

		Cost rec6 = new Cost(
				300, currency ,entertainment,
				"Concert",
				new Date(2023, 2, 8));

		Cost rec7 = new Cost(
				3000, currency ,bills,
				"Tax",
				new Date(2023,2, 20));

		Cost rec8 = new Cost(
				500, currency ,entertainment,
				"Plane tickets",
				new Date(2023, 2, 20));
		
		addCost(rec1);
		addCost(rec2);
		addCost(rec3);
		addCost(rec4);
		addCost(rec5);
		addCost(rec6);
		addCost(rec7);
		addCost(rec8);
		
//		System.out.println(db);
	}
	
	public static DBSimulate getDb(){
		return db;
	}
	
	
	
}

