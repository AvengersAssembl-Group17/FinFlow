package finflow.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String WELCOME_PREFIX = "Hello ";
    public static final String WELCOME_SUFFIX = ", welcome back!";
	public static final Map<String, String> IMAGE_MAP = new HashMap<>();
    public static final String CURRENCY_FORMAT = "$%.2f";
    public static final int RECENT_TRANSACTION_LIMIT = 3;
    public static final String ACTION_INCOME = "Income";
    public static final String ACTION_EXPENSE = "Expense";
    static {
        IMAGE_MAP.put("Salary", "salary_image");
        IMAGE_MAP.put("Education", "education_image");
        IMAGE_MAP.put("Grocery", "grocery_image");
        IMAGE_MAP.put("Rent", "rent_image");
        IMAGE_MAP.put("Travel", "travel_image");
        IMAGE_MAP.put("Food", "food_image");
        IMAGE_MAP.put("Entertainment", "entertainment_image");
        IMAGE_MAP.put("Clothes", "clothes_image");
        IMAGE_MAP.put("Utilities", "utilities_image");
    }
    
    
}
