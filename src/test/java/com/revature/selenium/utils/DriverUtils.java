package com.revature.selenium.utils;

import com.revature.utilities.ConnectionUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DriverUtils {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return driver;
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void cleanUsersTable() {
        Connection connection = null;
        try {
            connection = ConnectionUtil.createConnection();
            connection.setAutoCommit(false);
            deleteAllUsersFromTable(connection);
            connection.commit();
            System.out.println("Users table cleaned successfully.");
        } catch (SQLException e) {
            System.err.println("Error cleaning users table: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private static void deleteAllUsersFromTable(Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM users";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.executeUpdate();
        }
    }
}