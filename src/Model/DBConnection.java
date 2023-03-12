/* page we connect the system to the derby*/
package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBConnection {
	
	private static DBSimulate db;
	


	/*connection to the derby*/
	public static void connectDB() {
		db = new DBSimulate();
		loadMockData();
	}
	
	/* add new Cost the  derby*/
	public static void addCost(Cost cost) {
		db.addCost(cost);
	}

	/*delete cost*/
	public static void deleteCost(Cost cost) {
		db.deleteRecord(cost);
	}

	/*update the cost */
	public static void updateCost(Cost old, Cost updated) {
		db.updateCost(old, updated);
	}

	/*get all the Cost from the database*/
	public static ArrayList<Cost> getAllCost() {
		ArrayList<Cost> result = new ArrayList<>();
		for(Cost cost : db.getAllCost()) {
			result.add(createCostFromQuery(cost));
		}
		return result;
	}
	/*get all the costs f*/
	public static void getCostsFromDateToDate(Date from, Date to) {
		
	}
	/*return the cost as string */
	public static Cost createCostFromQuery(Cost queryResult) {
		int id = queryResult.getId();
		Category category = new Category(queryResult.getCategory().getId(), queryResult.getCategory().getName());
		String description = queryResult.getDescription();
		double sum = queryResult.getSum();
		String currency = queryResult.getCurrency();
		return new Cost(id, sum, currency ,category, description, queryResult.getDate());
	}
	
	/* Category queries return all Categories*/
	public static ArrayList<Category> getAllCategories() {
		ArrayList<Category> result = new ArrayList<>();
		for(Category category : db.getAllCategories()) {
			result.add(createCategoryFromQuery(category));
		}
			
		return db.getAllCategories();
	}

	/* get Category from the database  */
	public static Category getCategory(Category category) {
		Category queryResult = db.getCategory(category);
		return createCategoryFromQuery(queryResult);
	}

	/*check fo the add category*/
	public static void addCategory(Category category) {
		db.addCategory(new Category(category.getId(), category.getName()));
	}

	/*delete Category*/
	public static void deleteCategory(Category category) {
		db.deleteCategory(category);
		
	}

	/*create Category from Query */
	public static Category createCategoryFromQuery(Category queryResult) {
		int id = queryResult.getId();
		String name = queryResult.getName();
		return new Category(id, name);
	}
	
	/* Mock Data*/
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

