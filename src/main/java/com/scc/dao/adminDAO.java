package com.scc.dao;

import java.sql.*;

public class adminDAO {

    private static final String ADMIN_CODE = "FLEETIFY-ADMIN";



    // Removed 'id' from parameters since the database auto-increments it
    public boolean registerAdmin(String username, String password, String securityCode) {
        if (!ADMIN_CODE.equals(securityCode)) {
            System.out.println("Error: Invalid Security Code. Admin registration denied.");
            return false;
        }

        // Exclude 'id' from the columns list
        String sql = "INSERT INTO admin(username, password, securityCode, role, status) VALUES(?, ?, ?, 'ADMIN', ?)";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, securityCode);
            pstmt.setString(4, "INACTIVE");

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


        String userSql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(userSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");

                    // Check if regular user account is pending approval
                    if ("INACTIVE".equalsIgnoreCase(status)) {
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
    public boolean updateAdmin (String username, String newStatus){
        String updatesql = "UPDATE admin SET status = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(updatesql)){

            pstmt.setString(1, newStatus);
            pstmt.setString(2, username);

            // pstmt.executeUpdate() returns the number of rows changed
            int rowsAffected = pstmt.executeUpdate();

            // If rowsAffected > 0, it means the username existed and was successfully updated!
            return rowsAffected > 0;

        } catch(SQLException e) {
            System.out.println("Error updating user status: " + e.getMessage());
            return false;
        }
    }
    public boolean updateUserStatusSafely(String username, String newStatus, String currentAdmin) {
        // Block self-deactivation at the database logic level
        if (username.equalsIgnoreCase(currentAdmin) && newStatus.equalsIgnoreCase("INACTIVE")) {
            System.out.println("Security Error: Admins are not allowed to deactivate their own account.");
            return false;
        }

        String updatesql = "UPDATE user SET status = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(updatesql)) {

            pstmt.setString(1, newStatus);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch(SQLException e) {
            System.out.println("Error updating user status: " + e.getMessage());
            return false;
        }
    }
    public void displayAllUsers() {
        String sql = "SELECT id, username, securityCode, role, status FROM admin";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=====================================================================================");
            System.out.println("                               REGISTERED USERS LIST                                 ");
            System.out.println("=====================================================================================");
            // Formatted header matching your 5 columns
            System.out.printf("%-5s | %-18s | %-16s | %-20s | %-10s\n", "ID", "Username", "Security Code", "Role", "Status");
            System.out.println("-------------------------------------------------------------------------------------");

            boolean hasUsers = false;
            while (rs.next()) {
                hasUsers = true;
                System.out.printf("%-5s | %-18s | %-16s | %-20s | %-10s\n",
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("securityCode"), // Matches your exact column name
                        rs.getString("role"),
                        rs.getString("status")
                );
            }

            if (!hasUsers) {
                System.out.println("No users found in the database.");
            }
            System.out.println("=====================================================================================");

        } catch (SQLException e) {
            System.out.println("Error displaying users: " + e.getMessage());
        }
    }
}