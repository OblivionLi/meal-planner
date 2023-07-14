package mealplanner;

import java.util.ArrayList;
import java.util.List;

public enum MealCategory {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private final String category;

    MealCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public static MealCategory fromString(String category) {
        for (MealCategory mealCategory : MealCategory.values()) {
            if (mealCategory.category.equalsIgnoreCase(category)) {
                return mealCategory;
            }
        }
        throw new IllegalArgumentException("Invalid MealCategory: " + category);
    }

    public static List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        for (MealCategory category : MealCategory.values()) {
            categories.add(category.getCategory());
        }
        return categories;
    }
}
