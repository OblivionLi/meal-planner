package mealplanner;

import java.sql.*;

public class DatabaseConnector {
    static final String DB_URL = "jdbc:postgresql:meals_db";
    static final String USER = "your-name";
    static final String PASS = "your-pass";
    private Connection connection;

    public DatabaseConnector() {
        this.connection = null;
    }

    public void establishConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTablesIfNotExist() {
        try (Statement statement = connection.createStatement()) {
            // Create meals table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals (" +
                    "meal_id INT PRIMARY KEY," +
                    "category VARCHAR(255)," +
                    "meal VARCHAR(255))"
            );

            // Create ingredients table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients (" +
                    "ingredient_id INT PRIMARY KEY," +
                    "ingredient VARCHAR(255)," +
                    "meal_id INT," +
                    "FOREIGN KEY (meal_id) REFERENCES meals (meal_id))");

            // Create plan table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan (" +
                    "plan_id INT PRIMARY KEY," +
                    "day VARCHAR(10)," +
                    "meal_category VARCHAR(50)," +
                    "meal_option VARCHAR(100))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
