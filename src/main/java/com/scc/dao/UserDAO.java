package com.scc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}