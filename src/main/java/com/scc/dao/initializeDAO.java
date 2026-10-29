package com.scc.dao;

import java.sql.*;

public class initializeDAO {


    public static boolean registerVehicle(String id, String plateNumber, int capacity, String status) {
        String sql = "INSERT INTO vehicle (id, plate_number, capacity, status) VALUES (?, ?, ?, 'IDLE')";

        try (Connection conn = DatabaseConnection.getVehicleConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, plateNumber);
            pstmt.setInt(3, capacity);


            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Database Error registering vehicle: " + e.getMessage());
            return false;
        }
    }
    public static boolean registerDriver(String id, String name, String license, String status){
        String sql = "INSERT INTO driver (id, name, license, status) VALUES (?, ?,?, ?)";

        try (Connection conn = DatabaseConnection.getDriverConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, license);
            pstmt.setString(4, status);

            pstmt.executeUpdate();
            return true;



        } catch (SQLException e){
            System.out.println("Database Error registering Driver: " + e.getMessage());
            return false;
        }
    }
}