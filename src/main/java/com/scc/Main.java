package com.scc;

import com.scc.dao.UserDAO;
import com.scc.dao.adminDAO;
import com.scc.dao.initializeDAO;

import java.util.Scanner;

public class Main {
    // Use a single shared Scanner for everything
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO USER_DAO = new UserDAO();
    private static Object system;

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n================================");
            System.out.println("           FLEETIFY");
            System.out.println("================================");
            System.out.println("1. LOGIN");
            System.out.println("2. REGISTER");
            System.out.println("3. EXIT");
            System.out.print("Enter option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n================================");
                    System.out.println("            LOGIN");
                    System.out.println("================================");
                    System.out.print("Enter username: ");
                    String loginuser = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPass = sc.nextLine();

                    if (USER_DAO.login(loginuser, loginPass)) {
                        System.out.println("Redirecting to Dashboard.");

                        dashboardLoop: while (true) {
                            System.out.println("\n========== FLEETIFY ==========");
                            System.out.println("[1] Register");
                            System.out.println("[2] Trips");
                            System.out.println("[3] View");
                            System.out.println("[4] Update");
                            System.out.println("[0] Logout");
                            System.out.println("==============================");
                            System.out.print("Select an option: ");
                            String choicee = sc.nextLine().trim();

                            switch (choicee) {
                                case "1":
                                    System.out.println("\n========== Register ==========");
                                    System.out.println("[1] Register Vehicle");
                                    System.out.println("[2] Register Driver");
                                    System.out.println("[3] Register Route");
                                    System.out.println("[4] Register User");

                                    System.out.println("Select an option: ");
                                    String choiceee = sc.nextLine().trim();

                                    switch (choiceee){
                                        case "1":
                                            System.out.print("Enter Vehicle ID: ");
                                            String vId = sc.nextLine();
                                            if(vId.length() <= 3){
                                                System.out.print("Error: Must have been the wind.");
                                            }
                                            System.out.print("Enter Plate Number: ");
                                            String plate = sc.nextLine();
                                            if(plate.length() <= 3){
                                                System.out.println("Error: Must have been the wind.");
                                            }
                                            System.out.print("Enter Passenger capacity: ");
                                            int cap = Integer.parseInt(sc.nextLine());

                                            if (cap <= 0) {
                                                System.out.println("Invalid Passenger Capacity.");
                                                break;
                                            }
                                            System.out.print("Enter Vehicle type: ");
                                            String type = sc.nextLine();

                                            if(type.length() <= 0){
                                                System.out.println("Error: Must have been the wind.");
                                            }

                                            initializeDAO InitializeDAO = new initializeDAO();


                                            if(InitializeDAO.registerVehicle(vId, plate, cap, type, "IDLE")){
                                                System.out.println("Vehicle registered successfully.");
                                                break;
                                            }

                                            try {






                                                boolean savedToDb = initializeDAO.registerVehicle(vId, plate, cap, type,"IDLE");

                                                if (savedToDb) {
                                                    System.out.println("Vehicle registered and saved to SQLite successfully!");
                                                } else {
                                                    System.out.println("Warning: Added locally, but failed to save to database (ID or Plate might already exist in SQLite).");
                                                }
                                            }
                                            catch (NumberFormatException e) {
                                                System.out.println("Error: Capacity must be a valid integer.");
                                            }

                                            break;

                                    }
                                    break;
                                case "0":
                                    System.out.println("Logging out...");
                                    break dashboardLoop; // Breaks out of the dashboard loop back to main menu
                                default:
                                    System.out.println("Invalid dashboard option.");
                                    break;
                            }
                        }
                    }
                    break;

                case "2":
                    System.out.println("\n================================");
                    System.out.println("            REGISTER");
                    System.out.println("================================");
                    System.out.println("1. ADMIN");
                    System.out.println("2. STAFF");
                    System.out.print("Enter option: ");
                    String option = sc.nextLine().trim();

                    switch (option) {
                        case "1":
                            System.out.println("\n================================");
                            System.out.println("            REGISTER ADMIN");
                            System.out.println("================================");
                            System.out.print("Enter Username: ");
                            String username = sc.nextLine();
                            System.out.print("Enter Password: ");
                            String password = sc.nextLine();
                            System.out.print("Enter Security Code: ");
                            String securityCode = sc.nextLine();

                            adminDAO AdminDAO = new adminDAO();

                            if (AdminDAO.registerAdmin(username, password, securityCode)) {
                                System.out.println("Admin account registered successfully");
                            }
                            break;

                        case "2":
                            System.out.println("\n================================");
                            System.out.println("            REGISTER STAFF");
                            System.out.println("================================");
                            System.out.println("[1] Transit Schedulers");
                            System.out.println("[2] Fleet Dispatchers");
                            System.out.println("[3] Human Resources");
                            System.out.println("================================");
                            System.out.print("Enter role: ");
                            String role = sc.nextLine().trim();

                            switch (role) {
                                case "1":
                                    System.out.println("\n================================");
                                    System.out.println("      Transit Schedulers");
                                    System.out.println("================================");
                                    System.out.print("Enter username: ");
                                    String usernamee = sc.nextLine();
                                    System.out.print("Enter password: ");
                                    String passwordd = sc.nextLine();
                                    System.out.print("Enter Security Code: ");
                                    String securityCodee = sc.nextLine();

                                    UserDAO UserDAO = new UserDAO();

                                    if (UserDAO.registerUser(usernamee, passwordd, securityCodee, "Transit Scheduler")) {
                                        System.out.println("Account Successfully registered.");
                                    }
                                    break;

                                case "2":
                                    System.out.println("\n================================");
                                    System.out.println("      Fleet Dispatchers");
                                    System.out.println("================================");
                                    System.out.print("Enter username: ");
                                    String usernameee = sc.nextLine();
                                    System.out.print("Enter password: ");
                                    String passworddd = sc.nextLine();
                                    System.out.print("Enter Security Code: ");
                                    String securityCodeee = sc.nextLine();

                                    UserDAO UserDAOO = new UserDAO();

                                    if (UserDAOO.registerUser(usernameee, passworddd, securityCodeee, "Fleet Dispatcher")) {
                                        System.out.println("Account Successfully registered.");
                                    }
                                    break;

                                case "3":
                                    System.out.println("\n================================");
                                    System.out.println("      Human Resources");
                                    System.out.println("================================");
                                    System.out.print("Enter username: ");
                                    String usernameeee = sc.nextLine();
                                    System.out.print("Enter password: ");
                                    String passwordddd = sc.nextLine();
                                    System.out.print("Enter Security Code: ");
                                    String securityCodeeee = sc.nextLine();

                                    UserDAO UserDAOOO = new UserDAO();

                                    if (UserDAOOO.registerUser(usernameeee, passwordddd, securityCodeeee, "Human Resource")) {
                                        System.out.println("Account Successfully registered.");
                                    }
                                    break;

                                default:
                                    System.out.println("Error: Invalid role selection.");
                                    break;
                            }
                            break; // FIXED: Added break

                        default:
                            System.out.println("Error: Invalid registration option.");
                            break;
                    }
                    break;

                case "3":
                    System.out.println("Exiting Program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Error: Invalid main menu option.");
                    break;
            }
        }
    }
}