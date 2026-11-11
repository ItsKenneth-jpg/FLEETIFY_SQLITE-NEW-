package com.scc.dao;

import java.sql.*;

public class initializeDAO {


    public static boolean registerVehicle(String id, String plateNumber, int capacity, String type, String status) {
        String sql = "INSERT INTO vehicle (id, plate_number, capacity, type,  status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getVehicleConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, plateNumber);
            pstmt.setInt(3, capacity);
            pstmt.setString(4, type);
            pstmt.setString(5, status);


            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Database Error registering vehicle: " + e.getMessage());
            return false;
        }
    }
    public static boolean registerDriver(String id, String name, String license, String role, String status){
        String sql = "INSERT INTO driver (id, name, license, role, status) VALUES (?, ?,?,?, ?)";

        try (Connection conn = DatabaseConnection.getDriverConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, license);
            pstmt.setString(4, role);
            pstmt.setString(5, status);

            pstmt.executeUpdate();
            return true;



        } catch (SQLException e){
            System.out.println("Database Error registering Driver: " + e.getMessage());
            return false;
        }
    }

    public static boolean registerRoute (String id, String origin, String destination, String status){
        String sql = "INSERT INTO route(id, origin, destination, status) VALUES (?, ?, ?, 'IDLE')";

        try(Connection conn = DatabaseConnection.getRouteConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, id);
            pstmt.setString(2, origin);
            pstmt.setString(3, destination);


            pstmt.executeUpdate();
            return true;

        }
        catch (SQLException e){
            System.out.println("Database Error registering Driver: "+ e.getMessage());
            return false;
        }

    }
    public boolean displayvehicle(){
        String sql = "SELECT id, plate_number, capacity, type, status FROM vehicle";

        try(Connection conn = DriverManager.getConnection("jdbc:sqlite:initialize.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=====================================================================================");
            System.out.println("                               REGISTERED VEHICLES LIST                                 ");
            System.out.println("=====================================================================================");
            System.out.printf("%-5s | %-18s | %-16s | %-20s | %-10s\n", "ID", "Plate Number", "Capacity", "type", "Status");
            System.out.println("-------------------------------------------------------------------------------------");

            boolean vehicles = false;

            while (rs.next()){
                vehicles = true;
                System.out.printf("%-5s | %-18s | %-16s | %-20s | %-10s\n",
                        rs.getString( "id"),
                        rs.getString("plate_number"),
                        rs.getString("capacity"),
                        rs.getString("type"),
                        rs.getString("status")
                );

            }
            if (!vehicles){
                System.out.println("No Registered Vehicle found in database.");
            }
            System.out.println("=====================================================================================");



        }
        catch(SQLException e){
            System.out.println("Error displaying user." + e.getMessage());
            return false;

        }

        return false;
    }
    public boolean displaydriver() {
        String sql ="SELECT id, name, license, role, status FROM driver";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:initialize.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=====================================================================================");
            System.out.println("                               REGISTERED DRIVERS LIST                                 ");
            System.out.println("=====================================================================================");
            System.out.printf("%-5s | %-18s | %-16s | %-20s | %-10s\n", "ID", "Name", "License", "Role", "Status");
            System.out.println("-------------------------------------------------------------------------------------");

            boolean driver = false;

            while (rs.next()){
                driver = true;
                System.out.printf("%-5s | %-18s | %-20s | %-20s | %-10s\n",
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("license"),
                rs.getString("role"),
                rs.getString("status")
                );

            }
            if(!driver){
                System.out.println("No registered driver found in database.");

            }
            System.out.println("=====================================================================================");

        }
        catch (SQLException e){
            System.out.println("Error displaying." + e.getMessage());
            return false;
        }
        return false;
    }
}