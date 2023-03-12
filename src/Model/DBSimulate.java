package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DBSimulate {
	/*count the Category quantity*/
	private static int CATEGORY_COUNTER = 0;
	/*count the cost quantity*/
	private static int Cost_COUNTER = 0;

	/*hash map od Categories*/
	private HashMap<Integer, Category> categories;
	/*hash map od Cost*/
	private HashMap<Integer, Cost> costs;

	/*create the hushmap*/
	public DBSimulate() {
		categories = new HashMap<>();
		costs = new HashMap<>();
	}

	/*
	  add cost to the hushmap of the Cost
	 */
	public void addCost(Cost cost) {
		costs.put(Cost_COUNTER++, new Cost(Cost_COUNTER, cost.getSum(), cost.getCurrency(), cost.getCategory(),
				cost.getDescription(), cost.getDate()));
	}

	/*
	  add Category to the hushmap of the Category
	 */
	public void addCategory(Category category) {
		categories.put(CATEGORY_COUNTER++, new Category(CATEGORY_COUNTER, category.getName()));
	}
	

	/*get Cost from the hushmap of the cost*/
	public Cost getCpst(Cost cost) {
		return costs.get(cost.getId());
	}

	/*get Category from the hushmap of the Category*/
	
	public Category getCategory(Category category) {
		return categories.get(category.getId());
	}

	/*update cost from the hushmap of the cost search by id*/
	
	public void updateCost(Cost old, Cost updated) {
		costs.put(old.getId(), updated);
	}

	/*update Category from the hushmap of the Category search by id*/
	public void updateCategory(Category old, Category updated) {
		categories.put(old.getId(), updated);
	}
	
	
	/*delete cost from the hushmap by id*/
	public void deleteRecord(Cost cost) {
		costs.remove(cost.getId());
	}

	/*delete Category from the hush_map by id*/
	public void deleteCategory(Category category) {
		categories.remove(category.getId());
	}
	
	/*get all cost in the hush_map*/
	public ArrayList<Cost> getAllCost() {
		ArrayList<Cost> result = new ArrayList<>();
		for(Integer key : costs.keySet()) {
			result.add(costs.get(key));
		}
		return result;
	}

	/*get all Categories from the hush map and return them as arraylist*/
	public ArrayList<Category> getAllCategories(){
		ArrayList<Category> result = new ArrayList<>();
		for(Integer key : categories.keySet()) {
			result.add(categories.get(key));
		}
		return result;
	}
	
	/* return category and Cost as string from hush map*/
	public String toString() {
		String result = "";
		result += "Categories:";
		for(Integer key : categories.keySet()) {
			result += "\n\t" + key + ".\t" + categories.get(key).toString();
		}
		
		result += "\nRecords:";
		for(Integer key : costs.keySet()) {
			result += "\n\t" + key + ".\t" + costs.get(key).toString();
		}
				
		
		return result;
	}
	
	
	
	

}
