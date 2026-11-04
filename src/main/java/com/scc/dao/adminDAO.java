package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean login(String username, String password) {

        // --- STEP 1: Check the ADMIN table first ---
        String adminSql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(adminSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Found in the admin table!
                    System.out.println("Login Success! Welcome admin.");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error checking admin: " + e.getMessage());
        }

        // --- STEP 2: If not found in admin, check the USERS table ---
        String userSql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(userSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");

                    // Check if regular user account is pending approval
                    if ("PENDING".equalsIgnoreCase(status)) {
                        System.out.println("Error: Your account is pending admin approval.");
                        return false;
                    }

                    // Regular user is active/approved
                    System.out.println("Login successful! Welcome, " + username + ".");
                    return true;

                } else {
                    // Not found in admin OR users table
                    System.out.println("Error: Invalid username or password.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error checking user: " + e.getMessage());
            return false;
        }
    }
}