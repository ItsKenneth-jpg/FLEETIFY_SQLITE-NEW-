package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class adminDAO {

    private static final String ADMIN_CODE = "FLEETIFY-ADMIN";



    // Removed 'id' from parameters since the database auto-increments it
    public boolean registerAdmin(String username, String password, String securityCode) {
        if (!ADMIN_CODE.equals(securityCode)) {
            System.out.println("Error: Invalid Security Code. Admin registration denied.");
            return false;
        }

        // Exclude 'id' from the columns list
        String sql = "INSERT INTO admin(username, password, securityCode, role, status) VALUES(?, ?, ?, 'ADMIN', 'PENDING')";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, securityCode);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error: Username already exists or database issue. Details: " + e.getMessage());
            return false;
        }
    }
}