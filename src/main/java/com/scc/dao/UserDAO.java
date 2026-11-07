package com.scc.dao;

import java.sql.*;

public class UserDAO {

    private static final String USER_CODE = "FLEETIFY-USER";

    public boolean registerUser( String username, String password, String securityCode, String role) {

        if(!USER_CODE.equals(securityCode)){
            System.out.println("Error: Invalid Security Code. Registration Denied");
            return false;

        }
        // Assuming regular users register here
        String sql = "INSERT INTO user(username, password, securityCode, role, status) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, securityCode);
            pstmt.setString(4, role.toUpperCase());
            pstmt.setString(5, "INACTIVE");
            pstmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String username, String password) {


        String userSql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getUserConnection();
             PreparedStatement pstmt = conn.prepareStatement(userSql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Found in the user table!
                    System.out.println("Login Success! Welcome User.");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error checking admin: " + e.getMessage());
            return false;
        }

        // --- STEP 2: If not found in admin, check the USERS table ---

        return false;
    }
    public boolean updateUser (String username,String newRole, String newStatus){
        String updatesql = "UPDATE user SET role = ?, status = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(updatesql)){

            pstmt.setString(1, newRole);
            pstmt.setString(2, newStatus);
            pstmt.setString(3, username);

            // pstmt.executeUpdate() returns the number of rows changed
            int rowsAffected = pstmt.executeUpdate();

            // If rowsAffected > 0, it means the username existed and was successfully updated!
            return rowsAffected > 0;

        } catch(SQLException e) {
            System.out.println("Error updating user status: " + e.getMessage());
            return false;
        }
    }
    public void displayAllUsers() {
        String sql = "SELECT id, username, securityCode, role, status FROM user";

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
    public boolean isUserExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count is greater than 0
            }

        } catch (SQLException e) {
            System.out.println("Error checking user: " + e.getMessage());
        }
        return false;
    }

}