package carsharing;

import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String databaseFileName = (args.length > 0 && args[0].equals("-databaseFileName")) ? args[1] : "carsharing";
        String databaseURL = "jdbc:h2:./src/carsharing/db/" + databaseFileName;

        createDatabaseAndTablesIfNotExists(databaseURL);

        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            int mainMenuChoice = getUserChoice();

            if (mainMenuChoice == 0) {
                System.out.println("Goodbye!");
                break;
            } else if (mainMenuChoice == 1) {
                managerMenu(databaseURL);
            } else if (mainMenuChoice == 2) {
                loginCustomerMenu(databaseURL);
            } else if (mainMenuChoice == 3) {
                createCustomer(databaseURL);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createDatabaseAndTablesIfNotExists(String databaseURL) {
        try (Connection connection = DriverManager.getConnection(databaseURL + ";IFEXISTS=FALSE")) {
            createCompanyTable(connection);
            createCarTable(connection);
            createCustomerTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCompanyTable(Connection connection) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) UNIQUE NOT NULL" +
                ")";
        executeUpdate(connection, createTableQuery);
    }

    private static void createCarTable(Connection connection) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS CAR (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) UNIQUE NOT NULL," +
                "COMPANY_ID INT NOT NULL," +
                "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ")";
        executeUpdate(connection, createTableQuery);
    }

    private static void createCustomerTable(Connection connection) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(255) UNIQUE NOT NULL," +
                "RENTED_CAR_ID INT," +
                "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                ")";
        executeUpdate(connection, createTableQuery);
    }

    private static void managerMenu(String databaseURL) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            connection.setAutoCommit(true);

            while (true) {
                System.out.println("1. Company list");
                System.out.println("2. Create a company");
                System.out.println("0. Back");

                int managerMenuChoice = getUserChoice();

                if (managerMenuChoice == 0) {
                    break;
                } else if (managerMenuChoice == 1) {
                    showCompanyListWithMenu(connection);
                } else if (managerMenuChoice == 2) {
                    createCompany(connection);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loginCustomerMenu(String databaseURL) {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, NAME FROM CUSTOMER ORDER BY ID");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            connection.setAutoCommit(true);
            if (!resultSet.isBeforeFirst()) {
                System.out.println("The customer list is empty!");
                return;
            }

            int index = 1;
            System.out.println("Customer list:");
            while (resultSet.next()) {
                System.out.println(index + ". " + resultSet.getString("NAME"));
                index++;
            }
            System.out.println("0. Back");

            int userChoice = getUserChoice();
            if (userChoice > 0 && userChoice <= index) {
                rentCarMenu(connection, userChoice);
            } else if (userChoice != 0) {
                System.out.println("Invalid choice. Please try again.");
                showCompanyListWithMenu(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void showCompanyListWithMenu(Connection connection) {
        String selectCompaniesQuery = "SELECT ID, NAME FROM COMPANY ORDER BY ID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectCompaniesQuery)) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("The company list is empty!");
                return;
            }

            int index = 1;
            while (resultSet.next()) {
                System.out.println(index + ". " + resultSet.getString("NAME"));
                index++;
            }
            System.out.println("0. Back");

            int userChoice = getUserChoice();
            if (userChoice > 0 && userChoice <= index) {
                companyMenu(connection, userChoice);
            } else if (userChoice != 0) {
                System.out.println("Invalid choice. Please try again.");
                showCompanyListWithMenu(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void companyMenu(Connection connection, int companyId) {
        String companyName = getCompanyName(connection, companyId);

        while (true) {
            System.out.println("'" + companyName + "' company");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int companyMenuChoice = getUserChoice();

            if (companyMenuChoice == 0) {
                break;
            } else if (companyMenuChoice == 1) {
                showCarList(connection, companyId);
            } else if (companyMenuChoice == 2) {
                createCar(connection, companyId);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void rentCarMenu(Connection connection, int customerId) {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int userChoice = getUserChoice();

            if (userChoice == 0) {
                break;
            } else if (userChoice == 1) {
                chooseCompany(connection, customerId);
            } else if (userChoice == 2) {
                returnRentedCar(connection, customerId);
            } else if (userChoice == 3) {
                rentedCar(connection, customerId);
            }else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void chooseCompany(Connection connection, int customerId) {
        String selectCompaniesQuery = "SELECT ID, NAME FROM COMPANY ORDER BY ID";
        String selectCurrentCarQuery = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ?";
        try (Statement statement = connection.createStatement();
             PreparedStatement currentCar = connection.prepareStatement(selectCurrentCarQuery);
             ResultSet resultSet = statement.executeQuery(selectCompaniesQuery)) {
            currentCar.setInt(1, customerId);
            try (ResultSet cars = currentCar.executeQuery()) {
                if (cars.next()) {
                    Integer currentValue = cars.getInt("RENTED_CAR_ID");
                    if (currentValue == 0) {
                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("The company list is empty!");
                            return;
                        }

                        System.out.println("Choose a company");
                        int index = 1;
                        while (resultSet.next()) {
                            System.out.println(index + ". " + resultSet.getString("NAME"));
                            index++;
                        }
                        System.out.println("0. Back");

                        int userChoice = getUserChoice();
                        if (userChoice > 0 && userChoice <= index) {
                            chooseCarFromCompany(connection, userChoice, customerId);
                        } else if (userChoice != 0) {
                            System.out.println("Invalid choice. Please try again.");
                            chooseCompany(connection, customerId);
                        }
                    } else {
                        System.out.println("You've already rented a car!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseCarFromCompany(Connection connection, int companyId, int customerId) {
        String selectCarsQuery = "SELECT ID, NAME FROM CAR " +
                "WHERE company_id = ? " +
                "AND ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL) " +
                "ORDER BY ID";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCarsQuery)) {
            preparedStatement.setInt(1, companyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("The car list is empty!");
                } else {
                    int index = 1;
                    System.out.println("Choose a car:");
                    Map<Integer, String> carMap;
                    carMap = new HashMap<>();
                    while (resultSet.next()) {
                        System.out.println(index + ". " + resultSet.getString("NAME"));
                        carMap.put(index, resultSet.getString("NAME"));
                        index++;
                    }
                    System.out.println("0. Back");

                    int userChoice = getUserChoice();

                    if (userChoice > 0 && userChoice <= index) {
                        String updateQuery = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE id = ?";
                        try (PreparedStatement preparedStatement2 = connection.prepareStatement(updateQuery)) {
                            preparedStatement2.setInt(1, userChoice);
                            preparedStatement2.setInt(2, customerId);
                            preparedStatement2.executeUpdate();
                        }
                        System.out.println("You rented '" + carMap.get(userChoice) + "'");
                    } else if (userChoice != 0) {
                        System.out.println("Invalid choice. Please try again.");
                        chooseCarFromCompany(connection, companyId, customerId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void rentedCar(Connection connection, int customerId) {
        String selectCarIdQuery = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ?";
        String selectCarQuery = "SELECT NAME, COMPANY_ID FROM CAR WHERE ID = ?";
        String selectCompanyQuery = "SELECT NAME FROM COMPANY WHERE ID = ?";

        try (PreparedStatement selectCarId = connection.prepareStatement(selectCarIdQuery)) {
            selectCarId.setInt(1, customerId);

            try (ResultSet resultSet = selectCarId.executeQuery()) {
                if (resultSet.next()) {
                    int carId = resultSet.getInt("RENTED_CAR_ID");

                    if (carId != 0) { // assuming 0 indicates no rented car; adjust accordingly
                        try (PreparedStatement selectCar = connection.prepareStatement(selectCarQuery)) {
                            selectCar.setInt(1, carId);

                            try (ResultSet resultSet2 = selectCar.executeQuery()) {
                                if (resultSet2.next()) {
                                    int companyId = resultSet2.getInt("COMPANY_ID");

                                    try (PreparedStatement selectCompany = connection.prepareStatement(selectCompanyQuery)) {
                                        selectCompany.setInt(1, companyId);

                                        try (ResultSet resultSet3 = selectCompany.executeQuery()) {
                                            if (resultSet3.next()) {
                                                System.out.println("Your rented car:");
                                                System.out.println(resultSet2.getString("NAME"));
                                                System.out.println("Company:");
                                                System.out.println(resultSet3.getString("NAME"));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void returnRentedCar(Connection connection, int customerId) {
        String selectCarIdQuery = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ?";
        String updateCarQuery = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";

        try (PreparedStatement selectCarId = connection.prepareStatement(selectCarIdQuery);
        PreparedStatement updateCar = connection.prepareStatement(updateCarQuery)) {
            selectCarId.setInt(1, customerId);
            updateCar.setInt(1, customerId);

            try (ResultSet resultSet = selectCarId.executeQuery()) {
                if (resultSet.next()) {
                    int carId = resultSet.getInt("RENTED_CAR_ID");
                    if (carId != 0) {
                        updateCar.executeUpdate();
                        System.out.println("You've returned a rented car!");
                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCompanyName(Connection connection, int companyId) {
        String selectCompanyQuery = "SELECT NAME FROM COMPANY WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCompanyQuery)) {
            preparedStatement.setInt(1, companyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("NAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void showCarList(Connection connection, int companyId) {
        String companyName = getCompanyName(connection, companyId);

        String selectCarsQuery = "SELECT ID, NAME FROM CAR WHERE COMPANY_ID = ? ORDER BY ID";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCarsQuery)) {
            preparedStatement.setInt(1, companyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("The car list is empty!");
                } else {
                    int index = 1;
                    System.out.println("'" + companyName + "' cars:");

                    while (resultSet.next()) {
                        System.out.println(index + ". " + resultSet.getString("NAME"));
                        index++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void createCustomer(String databaseURL) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the customer name: ");
        String customerName = scanner.nextLine();

        String insertCustomerQuery = "INSERT INTO CUSTOMER (NAME) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(databaseURL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertCustomerQuery)) {
            preparedStatement.setString(1, customerName);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The customer was added!");
            } else {
                System.out.println("Failed to add the customer. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createCar(Connection connection, int companyId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the car name: ");
        String carName = scanner.nextLine();

        // Check if the company with the given ID exists
        if (!companyExists(connection, companyId)) {
            System.out.println("Error: Company does not exist.");
            return;
        }

        // Insert the new car into the database
        String insertCarQuery = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCarQuery)) {
            preparedStatement.setString(1, carName);
            preparedStatement.setInt(2, companyId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The car was added!");
            } else {
                System.out.println("Failed to add the car. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean companyExists(Connection connection, int companyId) {
        String selectCompanyQuery = "SELECT ID FROM COMPANY WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCompanyQuery)) {
            preparedStatement.setInt(1, companyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void createCompany(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the company name: ");
        String companyName = scanner.nextLine();

        // Insert the new company into the database
        String insertCompanyQuery = "INSERT INTO COMPANY (NAME) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCompanyQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, companyName);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The company was added!");
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println();
                }
            } else {
                System.out.println("Failed to add the company. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void executeUpdate(Connection connection, String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
