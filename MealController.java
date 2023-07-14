package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealController {
    private final DatabaseConnector databaseConnector;

    public MealController() {
        this.databaseConnector = new DatabaseConnector();
    }

    public void addMeal(MealCategory mealCategory, String mealName, List<String> mealIngredients) {
        Meal meal = new Meal(mealName, mealIngredients, mealCategory);

        this.databaseConnector.establishConnection();
        Connection connection = this.databaseConnector.getConnection();

        try {
            int mealId = this.insertMeal(connection, meal);

            this.insertIngredients(connection, meal.getIngredients(), mealId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private int insertMeal(Connection connection, Meal meal) throws SQLException {
        int mealId;
        try (PreparedStatement mealStatement = connection.prepareStatement("INSERT INTO meals (meal_id, meal, category) VALUES (?, ?, ?)")) {
            mealId = getNextAvailableId(connection, "meals", "meal_id");
            mealStatement.setInt(1, mealId);
            mealStatement.setString(2, meal.getName());
            mealStatement.setString(3, meal.getCategory());
            mealStatement.executeUpdate();
        }
        return mealId;
    }

    private void insertIngredients(Connection connection, List<String> ingredients, int mealId) throws SQLException {
        try (PreparedStatement ingredientStatement = connection.prepareStatement("INSERT INTO ingredients (ingredient_id, ingredient, meal_id) VALUES (?, ?, ?)")) {
            int ingredientId = getNextAvailableId(connection, "ingredients", "ingredient_id");
            for (String ingredient : ingredients) {
                ingredientStatement.setInt(1, ingredientId);
                ingredientStatement.setString(2, ingredient);
                ingredientStatement.setInt(3, mealId);
                ingredientStatement.executeUpdate();
                ingredientId++;
            }
        }
    }

    private int getNextAvailableId(Connection connection, String tableName, String columnName) throws SQLException {
        int nextId = 1;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(" + columnName + ") FROM " + tableName)) {
            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1;
            }
        }
        return nextId;
    }

    public List<Meal> getAllMealsForCategory(String category) {
        List<Meal> meals = new ArrayList<>();

        this.databaseConnector.establishConnection();
        Connection connection = this.databaseConnector.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE category = ? ORDER BY meal ASC")) {
            statement.setString(1, category);

            try (ResultSet mealResultSet = statement.executeQuery()) {
                while (mealResultSet.next()) {
                    int mealId = mealResultSet.getInt("meal_id");
                    String mealName = mealResultSet.getString("meal");
                    String mealCategory = mealResultSet.getString("category");

                    List<String> ingredients = new ArrayList<>();
                    try (PreparedStatement ingredientStatement = connection.prepareStatement("SELECT ingredient FROM ingredients WHERE meal_id = ?")) {
                        ingredientStatement.setInt(1, mealId);
                        ResultSet ingredientResultSet = ingredientStatement.executeQuery();
                        while (ingredientResultSet.next()) {
                            String ingredient = ingredientResultSet.getString("ingredient");
                            ingredients.add(ingredient);
                        }
                    }

                    Meal meal = new Meal(mealName, ingredients, MealCategory.fromString(mealCategory));
                    meals.add(meal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.databaseConnector.closeConnection();
        }

        return meals;
    }

    public Meal findMealByName(String mealNameFilter) {
        this.databaseConnector.establishConnection();
        Connection connection = this.databaseConnector.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM meals WHERE meal = ?")) {
            statement.setString(1, mealNameFilter);

            try (ResultSet mealResultSet = statement.executeQuery()) {
                while (mealResultSet.next()) {
                    int mealId = mealResultSet.getInt("meal_id");
                    String mealName = mealResultSet.getString("meal");
                    String mealCategory = mealResultSet.getString("category");

                    List<String> ingredients = new ArrayList<>();
                    try (PreparedStatement ingredientStatement = connection.prepareStatement("SELECT ingredient FROM ingredients WHERE meal_id = ?")) {
                        ingredientStatement.setInt(1, mealId);
                        ResultSet ingredientResultSet = ingredientStatement.executeQuery();
                        while (ingredientResultSet.next()) {
                            String ingredient = ingredientResultSet.getString("ingredient");
                            ingredients.add(ingredient);
                        }
                    }

                    return new Meal(mealName, ingredients, MealCategory.fromString(mealCategory));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.databaseConnector.closeConnection();
        }

        return null;
    }
}
