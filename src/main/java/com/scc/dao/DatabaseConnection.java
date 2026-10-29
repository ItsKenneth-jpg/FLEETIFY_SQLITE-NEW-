package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String USERS_DB_URL = "jdbc:sqlite:users.db";
    private static final String INITIALIZE_DB_URL = "jdbc:sqlite:initialize.db";

    public static Connection getUserConnection() throws SQLException {
        return DriverManager.getConnection(USERS_DB_URL);

    }
    public static Connection getVehicleConnection() throws SQLException {
        return DriverManager.getConnection(INITIALIZE_DB_URL);
    }
    public static Connection getDriverConnection() throws SQLException {
        return DriverManager.getConnection(INITIALIZE_DB_URL);
    }
}