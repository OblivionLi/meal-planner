package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanController {
    private final DatabaseConnector databaseConnector;

    public PlanController() {
        this.databaseConnector = new DatabaseConnector();
    }

    public void addPlans(List<String> plannedMeals) {
        List<Plan> plans = new ArrayList<>();

        for (var plan : plannedMeals) {
            String[] planParts = plan.split(":");

            Plan createdPlan = new Plan(planParts[0], planParts[1], planParts[2]);
            plans.add(createdPlan);
        }

        this.databaseConnector.establishConnection();
        Connection connection = this.databaseConnector.getConnection();

        try {
            for (var plan : plans) {
                this.insertPlan(connection, plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.closeConnection();
        }
    }

    private void insertPlan(Connection connection, Plan plan) throws SQLException {
        int planId;
        try (PreparedStatement mealStatement = connection.prepareStatement("INSERT INTO plan (plan_id, day, meal_category, meal_option) VALUES (?, ?, ?, ?)")) {
            planId = getNextAvailableId(connection);
            mealStatement.setInt(1, planId);
            mealStatement.setString(2, plan.day());
            mealStatement.setString(3, plan.mealCategory());
            mealStatement.setString(4, plan.mealName());
            mealStatement.executeUpdate();
        }
    }

    private int getNextAvailableId(Connection connection) throws SQLException {
        int nextId = 1;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(plan_id) FROM plan")) {
            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1;
            }
        }
        return nextId;
    }

    public List<Plan> getAllPlans() {
        List<Plan> plans = new ArrayList<>();

        this.databaseConnector.establishConnection();
        Connection connection = this.databaseConnector.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM plan")) {
            try (ResultSet planResultSet = statement.executeQuery()) {
                while (planResultSet.next()) {
                    String day = planResultSet.getString("day");
                    String mealCategory = planResultSet.getString("meal_category");
                    String mealName = planResultSet.getString("meal_option");

                    Plan plan = new Plan(day, mealCategory, mealName);
                    plans.add(plan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.databaseConnector.closeConnection();
        }

        return plans;
    }

    public boolean isPlanReady() {
        return !this.getAllPlans().isEmpty();
    }

    public List<String> generateShoppingList() {
        List<String> shoppingList = new ArrayList<>();
        List<Plan> plans = this.getAllPlans();
        MealController mealController = new MealController();
        Map<String, Integer> ingredientCount = new HashMap<>();

        for (Plan plan : plans) {
            String mealName = plan.mealName();
            Meal meal = mealController.findMealByName(mealName);

            List<String> ingredients = meal.getIngredients();
            for (String ingredient : ingredients) {
                if (ingredientCount.containsKey(ingredient)) {
                    int count = ingredientCount.get(ingredient);
                    ingredientCount.put(ingredient, count + 1);
                } else {
                    ingredientCount.put(ingredient, 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : ingredientCount.entrySet()) {
            String ingredient = entry.getKey();
            int count = entry.getValue();
            String ingredientEntry = ingredient;

            if (count > 1) {
                ingredientEntry += " x" + count;
            }

            shoppingList.add(ingredientEntry);
        }

        return shoppingList;
    }

}
