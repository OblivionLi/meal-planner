package mealplanner;

import java.util.List;

public class Meal {
    private final String name;
    private final List<String> ingredients;
    private final MealCategory category;

    public Meal(String name, List<String> ingredients, MealCategory mealCategory) {
        this.name = name;
        this.ingredients = ingredients;
        this.category = mealCategory;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public String getCategory() {
        return this.category.getCategory();
    }
}
