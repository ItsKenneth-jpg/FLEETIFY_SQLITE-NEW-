package com.scc;

import com.scc.dao.UserDAO;
import com.scc.dao.adminDAO;
import com.scc.dao.initializeDAO;

import java.util.Scanner;

public class Main {
    //  single shared Scanner for everything
    private static final Scanner sc = new Scanner(System.in);
    private static final adminDAO ADMIN_DAO = new adminDAO();
    private static final UserDAO USER_DAO = new UserDAO();
    private static final initializeDAO ROUTE_DAO = new initializeDAO();
    private static Object system;

    static void main(String[] args) {

        while (true) {
            System.out.println("\n================================");
            System.out.println("           FLEETIFY");
            System.out.println("================================");
            System.out.println("1. LOGIN ADMIN");
            System.out.println("2. LOGIN STAFF");
            System.out.println("3. REGISTER");
            System.out.println("4. EXIT");
            System.out.print("Enter option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n================================");
                    System.out.println("            LOGIN ADMIN");
                    System.out.println("================================");
                    System.out.print("Enter username: ");
                    String loginuser = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPass = sc.nextLine();

                    if (ADMIN_DAO.login(loginuser, loginPass)) {
                        System.out.println("Redirecting to Dashboard.");

                        dashboardLoop: while (true) {
                            String currentLoggedInAdmin = loginuser;
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
                                    System.out.println("[0] Return");

                                    System.out.print("Select an option: ");
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


                                            if(initializeDAO.registerVehicle(vId, plate, cap, type, "IDLE")){
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
                                        case "2":
                                            System.out.print("Enter Driver ID: ");
                                            String dId = sc.nextLine();
                                            if(dId.length() <= 3){
                                                System.out.println("Error: Invalid driver ID: ");
                                                break;
                                            }
                                            System.out.print("Enter Driver name: ");
                                            String name = sc.nextLine();
                                            if(name.length() <= 2 ){
                                                System.out.println("Error: Invalid driver ID.");
                                                break;
                                            }
                                            System.out.print("Enter License ID: ");
                                            String lic = sc.nextLine();
                                            if(lic.length() <= 3){
                                                System.out.println("Error: Invalid License ID");
                                                break;
                                            }

                                            initializeDAO InitializeDAOO = new initializeDAO();

                                            if(initializeDAO.registerDriver(dId, name, lic,"Driver", "IDLE")){
                                                System.out.println("Driver registered successfully.");
                                                break;
                                            }
                                            break;
                                        case "3":
                                            System.out.print("Enter route ID: ");
                                            String rId = sc.nextLine();
                                            if(rId.length() <= 3){
                                                System.out.println("Error: Invalid route Id.");
                                                break;
                                            }
                                            System.out.print("Enter Starting Point: ");
                                            String orig = sc.nextLine();
                                            if (orig.length() <= 3) {
                                                System.out.println("Error: Cannot Identify starting point.");
                                                break;
                                            }
                                            System.out.print("Enter Destination: ");
                                            String dest = sc.nextLine();
                                            if (dest.length() <= 3) {
                                                System.out.println("Error: Cannot Identify destination point.");
                                                break;
                                            }
                                            initializeDAO InitializeDAOOO = new initializeDAO();

                                            if(initializeDAO.registerRoute(rId, orig, dest, "IDLE")){
                                                System.out.println("Route registered sucessfully.");
                                                break;
                                            }
                                            break;
                                        case "4":
                                            registerLoop: while (true){
                                                System.out.println("\n========== Register User==========");
                                                System.out.println("[1] Administrator");
                                                System.out.println("[2] Transit Schedulers");
                                                System.out.println("[3] Fleet Dispatchers");
                                                System.out.println("[4] Human Resources");
                                                System.out.println("[0] Return");

                                                System.out.print("Select an option: ");
                                                String opt = sc.nextLine().trim();

                                                switch (opt){
                                                    case "1":
                                                        System.out.println("==========ADMINISTRATOR==========");
                                                        System.out.print("Enter Username: ");
                                                        String username = sc.nextLine();
                                                        System.out.print("Enter Password: ");
                                                        String password = sc.nextLine();
                                                        System.out.print("Enter Security Code: ");
                                                        String securityCode = sc.nextLine();

                                                        adminDAO AdminDAO = new adminDAO();

                                                        if (AdminDAO.registerAdmin(username, password, securityCode)){
                                                            System.out.println("Admin account registered Successful!");
                                                            break;

                                                        }
                                                        break;
                                                    case "2":
                                                        System.out.println("==========Transit Schedulers==========");
                                                        System.out.print("Enter username: ");
                                                        String usernamee = sc.nextLine();
                                                        System.out.print("Enter password: ");
                                                        String passwordd = sc.nextLine();
                                                        System.out.print("Enter Security Code: ");
                                                        String securityCodee = sc.nextLine();

                                                        UserDAO UserDAO = new UserDAO();

                                                        if (UserDAO.registerUser(usernamee, passwordd, securityCodee, "Transit Scheduler")) {
                                                            System.out.println("Account Successfully registered.");
                                                            break;
                                                        }
                                                        break;
                                                    case "3":
                                                        System.out.println("==========Fleet Dispatchers==========");
                                                        System.out.print("Enter username: ");
                                                        String usernameee = sc.nextLine();
                                                        System.out.print("Enter password: ");
                                                        String passworddd = sc.nextLine();
                                                        System.out.print("Enter Security Code: ");
                                                        String securityCodeee = sc.nextLine();

                                                        UserDAO UserDAOO = new UserDAO();

                                                        if (UserDAOO.registerUser(usernameee, passworddd, securityCodeee, "Fleet Dispatcher")) {
                                                            System.out.println("Account Successfully registered.");
                                                            break;
                                                        }
                                                        break;
                                                    case "4":

                                                        System.out.println("==========Human Resources==========");
                                                        System.out.print("Enter Username: ");
                                                        String usernameeee = sc.nextLine();
                                                        System.out.print("Enter Password: ");
                                                        String passwordddd = sc.nextLine();
                                                        System.out.print("Enter Security Code: ");
                                                        String securityCodeeee = sc.nextLine();

                                                        UserDAO UserDAOOO = new UserDAO();

                                                        if(UserDAOOO.registerUser(usernameeee, passwordddd, securityCodeeee, "Human Resources")){
                                                            System.out.println("Account Successfully registered.");
                                                            break;
                                                        }
                                                        break;
                                                    case "0":
                                                        System.out.println("Returning...");
                                                        break registerLoop;
                                                    default:
                                                        System.out.println("Error: Invalid Choice!");

                                                        break;

                                                }
                                            }

                                            break;

                                        case "0":
                                            System.out.println("Returning...");
                                            break;

                                    }

                                    break;
                                case "2":

                                    break;
                                case "3":
                                    UserDAO viewDaoo = new UserDAO();
                                    viewDaoo.displayAllUsers();

                                    break;
                                case "4":
                                    System.out.println("[1] Update User Status");
                                    System.out.println("[0] Return");
                                    System.out.print("Select an option: ");

                                    String updateChoice = sc.nextLine().trim();

                                    switch(updateChoice){
                                        case "1":
                                        System.out.println("\n========== UPDATE USER STATUS ==========");
                                            System.out.println("[1] STAFF");
                                            System.out.println("[2] ADMIN");
                                            System.out.print("Enter Option: ");
                                            String Updatechoicee = sc.nextLine().trim();

                                            switch(Updatechoicee){
                                                case "1":
                                                    System.out.println("\n========== UPDATE STAFF STATUS ==========");

                                                    UserDAO viewDao = new UserDAO();
                                                    viewDao.displayAllUsers();


                                                    String targetUserr = "";

                                                    while (true) {
                                                        System.out.print("Enter username to update: ");
                                                        targetUserr = sc.nextLine().trim();

                                                        if (viewDao.isUserExists(targetUserr)) {
                                                            break; // Username exists! Exit the loop and proceed.
                                                        } else {
                                                            System.out.println("Error: Username '" + targetUserr + "' does not exist in the database. Please try again.");
                                                        }
                                                    }

// 1. Role Validation Loop
                                                    String newRole = "";
                                                    while (true) {

                                                        System.out.println("==========ROLES==========");
                                                        System.out.println(" Transit Schedulers");
                                                        System.out.println(" Fleet Dispatchers");
                                                        System.out.println(" Human Resources");
                                                        System.out.println("=========================");

                                                        System.out.print("Enter new role: ");
                                                        newRole = sc.nextLine().trim();

                                                        // Use .equalsIgnoreCase() and check if it matches valid options
                                                        if (newRole.equalsIgnoreCase("Transit Scheduler") ||
                                                                newRole.equalsIgnoreCase("Fleet Dispatcher") ||
                                                                newRole.equalsIgnoreCase("Human Resources") ||
                                                                newRole.equalsIgnoreCase("Human Resource")) {
                                                            break; // Valid role! Exit loop.
                                                        } else {
                                                            System.out.println("Error: Invalid role. Please try again.");
                                                        }
                                                    }

// 2. Status Validation Loop
                                                    String newStatus = "";
                                                    while (true) {
                                                        System.out.println("[1] Set ACTIVE");
                                                        System.out.println("[2] Set INACTIVE");
                                                        System.out.print("Select status choice: ");
                                                        String statusChoice = sc.nextLine().trim();

                                                        if (statusChoice.equals("1")) {
                                                            newStatus = "ACTIVE";
                                                            break; // Valid status! Exit loop.
                                                        } else if (statusChoice.equals("2")) {
                                                            newStatus = "INACTIVE";
                                                            break; // Valid status! Exit loop.
                                                        } else {
                                                            System.out.println("Error: Invalid status option. Please enter 1 or 2.");
                                                        }
                                                    }


                                                    UserDAO statusDao = new UserDAO();

                                                    if (statusDao.updateUser(targetUserr, newRole, newStatus)) {
                                                        System.out.println("Success! User '" + targetUserr + "' updated to Role: [" + newRole + "] and Status: [" + newStatus + "].");
                                                    } else {
                                                        System.out.println("Failed to update. Double-check that the username '" + targetUserr + "' exists in your database.");
                                                    }
                                                    break;
                                                case "2":
                                                    System.out.println("\n========== UPDATE ADMIN STATUS ==========");

                                                    adminDAO viewDaooo = new adminDAO();
                                                    viewDaooo.displayAllUsers();

                                                    System.out.print("Enter username to update: ");
                                                    String targetUserrr = sc.nextLine().trim();

                                                    if (targetUserrr.equalsIgnoreCase(currentLoggedInAdmin)) {
                                                        System.out.println("Error: You cannot modify or deactivate your own active account!");
                                                        break; // Stops the update process right here
                                                    }

                                                    System.out.println("[1] SET ACTIVE");
                                                    System.out.println("[2] SET INACTIVE");
                                                    System.out.print("Enter Option: ");
                                                    String statuss = sc.nextLine().trim();

                                                    String statusss = "";
                                                    if (statuss.equals("1")) {
                                                        statusss = "ACTIVE";
                                                    } else if (statuss.equals("2")) {
                                                        statusss = "INACTIVE";
                                                    } else {
                                                        System.out.println("Error: Invalid status selection.");
                                                        break;
                                                    }

                                                    adminDAO statusDaoo = new adminDAO();


                                                    if (statusDaoo.updateAdmin(targetUserrr, statusss    )) {
                                                        System.out.println("User '" + targetUserrr + "' successfully updated to " + statusss + "!");
                                                    }

                                                    else
                                                    {
                                                        System.out.println("Failed to update. Username may not exist in the database.");
                                                    }
                                                    break;
                                            }



                                        break;
                                    }

                                case "0":
                                    System.out.println("Returning...");
                                    break;
                                default:
                                    System.out.println("Invalid dashboard option.");
                                    break;
                            }
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n================================");
                    System.out.println("            LOGIN STAFF");
                    System.out.println("================================");
                    System.out.print("Enter username: ");
                    String loginuserr = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPasss = sc.nextLine();

                    if (USER_DAO.login(loginuserr, loginPasss)) {
                        System.out.println("Redirecting to Dashboard.");
                    }
                        break;

                case "3":
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

                case "4":
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