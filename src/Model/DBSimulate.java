package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DBSimulate {
	
	private static int CATEGORY_COUNTER = 0;
	private static int Cost_COUNTER = 0;
	
	private HashMap<Integer, Category> categories;
	private HashMap<Integer, Cost> costs;
	
	public DBSimulate() {
		categories = new HashMap<>();
		costs = new HashMap<>();
	}
	
	public void addCost(Cost cost) {
		costs.put(Cost_COUNTER++, new Cost(Cost_COUNTER, cost.getSum(), cost.getCurrency(), cost.getCategory(),
				cost.getDescription(), cost.getDate()));
	}
	
	public void addCategory(Category category) {
		categories.put(CATEGORY_COUNTER++, new Category(CATEGORY_COUNTER, category.getName()));
	}
	
	
	public Cost getCpst(Cost cost) {
		return costs.get(cost.getId());
	}
	
	public Category getCategory(Category category) {
		return categories.get(category.getId());
	}
	
	public void updateCost(Cost old, Cost updated) {
		costs.put(old.getId(), updated);
	}
	
	public void updateCategory(Category old, Category updated) {
		categories.put(old.getId(), updated);
	}
	
	
	
	public void deleteRecord(Cost cost) {
		costs.remove(cost.getId());
	}
	
	public void deleteCategory(Category category) {
		categories.remove(category.getId());
	}
	
	
	public ArrayList<Cost> getAllCost() {
		ArrayList<Cost> result = new ArrayList<>();
		for(Integer key : costs.keySet()) {
			result.add(costs.get(key));
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
		for(Integer key : costs.keySet()) {
			result += "\n\t" + key + ".\t" + costs.get(key).toString();
		}
				
		
		return result;
	}
	
	
	
	

}
