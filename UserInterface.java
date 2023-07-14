package mealplanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class UserInterface {
    private final Scanner scanner;
    private final MealController mealController;
    private final PlanController planController;

    UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.mealController = new MealController();
        this.planController = new PlanController();
    }

    public void start() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.establishConnection();
        databaseConnector.createTablesIfNotExist();
        databaseConnector.closeConnection();

        while (true) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            String userInput = this.scanner.nextLine().toLowerCase();

            if (userInput.equals("exit")) {
                System.out.println("Bye!");
                break;
            }

            switch (userInput) {
                case "add" -> this.addMeals();
                case "show" -> this.showMeal();
                case "plan" -> this.planMeals();
                case "save" -> this.saveMealToFile();
                default -> {}
            }
        }
    }

    private void saveMealToFile() {
        if (!planController.isPlanReady()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }

        System.out.println("Input a filename:");
        String fileName = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(fileName)) {
            List<String> shoppingList = planController.generateShoppingList();
            for (String ingredient : shoppingList) {
                writer.println(ingredient);
            }
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the file.");
        }
    }

    private void planMeals() {
        List<String> chosenMeals = new ArrayList<>();
        for (var day : Day.getAllDays()) {
            System.out.println(day);
            for (var category : MealCategory.getAllCategories()) {
                String mealName = this.getMealForCategoryAndDay(category, day);
                chosenMeals.add(day + ":" + category + ":" + mealName);
            }
            System.out.println("Yeah! We planned the meals for " + day + ".");
            System.out.println();
        }

        this.planController.addPlans(chosenMeals);

        StringBuilder sb = new StringBuilder();
        String currentDay = "";

        for (String meal : chosenMeals) {
            String[] parts = meal.split(":");
            String day = parts[0];
            String mealType = parts[1];
            String mealName = parts[2];

            if (!day.equals(currentDay)) {
                sb.append(day).append("\n");
                currentDay = day;
            }

            sb.append(mealType.substring(0, 1).toUpperCase()).append(mealType.substring(1)).append(": ").append(mealName).append("\n");
        }

        System.out.println(sb);
    }

    private void showMeal() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");

        MealCategory selectedCategory;
        while (true) {
            String mealCategoryOption = this.scanner.nextLine();
            switch (mealCategoryOption.toLowerCase()) {
                case "breakfast" -> selectedCategory = MealCategory.BREAKFAST;
                case "lunch" -> selectedCategory = MealCategory.LUNCH;
                case "dinner" -> selectedCategory = MealCategory.DINNER;
                default -> {
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    continue;
                }
            }
            break;
        }

        List<Meal> meals = new ArrayList<>(this.mealController.getAllMealsForCategory(selectedCategory.getCategory()));
        if (meals.isEmpty()) {
            System.out.println("No meals found.");
            return;
        }

        System.out.println("Category: " + selectedCategory.getCategory());
        System.out.println();
        for (int i = meals.size() - 1; i >= 0; i--) {
            var mealData = meals.get(i);
            String mealName = mealData.getName();
            List<String> ingredients = mealData.getIngredients();

            System.out.println("Name: " + mealName);
            System.out.println("Ingredients:");
            for (var ingredient : ingredients) {
                System.out.println(ingredient);
            }
            System.out.println();
        }
    }

    private String getMealForCategoryAndDay(String category, String day) {
        List<Meal> meals = new ArrayList<>(this.mealController.getAllMealsForCategory(category));
        if (meals.isEmpty()) {
            return "No meals found.";
        }

        List<String> mealsForCategory = new ArrayList<>();
        for (var meal : meals) {
            System.out.println(meal.getName());
            mealsForCategory.add(meal.getName());
        }

        System.out.println("Choose the " + category + " for " + day + " from the list above:");
        String mealName;
        while (true) {
            String userInput = this.scanner.nextLine();
            if (!mealsForCategory.contains(userInput)) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                continue;
            }

            mealName = userInput;
            break;
        }

        return mealName;
    }

    private void addMeals() {
        MealCategory mealCategory = this.getMealCategory();
        String mealName = this.getMealName();
        List<String> mealIngredients = this.getMealIngredients();

        this.mealController.addMeal(mealCategory, mealName, mealIngredients);
        System.out.println("The meal has been added!");
    }

    private MealCategory getMealCategory() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        MealCategory selectedCategory;
        while (true) {
            String mealCategoryOption = this.scanner.nextLine();
            switch (mealCategoryOption.toLowerCase()) {
                case "breakfast" -> selectedCategory = MealCategory.BREAKFAST;
                case "lunch" -> selectedCategory = MealCategory.LUNCH;
                case "dinner" -> selectedCategory = MealCategory.DINNER;
                default -> {
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    continue;
                }
            }
            break;
        }

        return selectedCategory;
    }

    private String getMealName() {
        System.out.println("Input the meal's name:");

        while (true) {
            String mealName = this.scanner.nextLine();

            if (mealName.equals("") || !mealName.matches("[a-zA-Z ]+")) {
                System.out.println("Wrong format. Use letters only!");
                continue;
            }

            return mealName.toLowerCase();
        }
    }

    private List<String> getMealIngredients() {
        System.out.println("Input the ingredients:");
        List<String> mealIngredients = new ArrayList<>();

        while (true) {
            String ingredients = this.scanner.nextLine();

            boolean isInputInvalid = false;
            boolean hasEmptyIngredient = false;

            String[] ingredientList = ingredients.split(",");
            for (String ingredient : ingredientList) {
                String trimmedIngredient = ingredient.trim();

                if (!trimmedIngredient.matches("[a-zA-Z ]+")) {
                    isInputInvalid = true;
                    break;
                }

                if (trimmedIngredient.isEmpty()) {
                    hasEmptyIngredient = true;
                    break;
                }

                if (mealIngredients.contains(trimmedIngredient)) {
                    continue;
                }

                mealIngredients.add(trimmedIngredient);
            }

            if (isInputInvalid) {
                System.out.println("Wrong format. Use letters only!");
                continue;
            }

            if (hasEmptyIngredient) {
                System.out.println("Wrong format. Remove empty ingredients!");
                continue;
            }

            break;
        }

        return mealIngredients;
    }
}
