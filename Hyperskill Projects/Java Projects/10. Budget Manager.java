package budget;

import java.util.*;
import java.text.DecimalFormat;
import java.io.Serializable;
import java.io.*;
import java.util.stream.Collectors;

/**
 * This program counts the cost of purchases made by the user.
 */
public class Main implements Serializable {
    /**
     * Represents the menu.
     */
    public static final String MENU =
            """
                    Choose your action:
                    1) Add income
                    2) Add purchase
                    3) Show list of purchases
                    4) Balance
                    5) Save
                    6) Load
                    7) Analyze (Sort)
                    0) Exit""";
    private static final Scanner keyboard = new Scanner(System.in);
    private static List<Purchase> purchases = new ArrayList<>();
    private static List<Purchase> purchasesFood = new ArrayList<>();
    private static List<Purchase> purchasesClothes = new ArrayList<>();
    private static List<Purchase> purchasesEntertainment = new ArrayList<>();
    private static List<Purchase> purchasesOther = new ArrayList<>();
    /**
     * The status of the program.
     */
    private static Status running = Status.RUNNING;
    /**
     * The user's balance.
     */
    private static double balance = 0;

    /**
     * The main method.
     * Operates a loop over the main menu until exited.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        while (running == Status.RUNNING) {
            System.out.println(MENU);
            switch (menuSelection.values()[keyboard.nextInt()]) {
                case INCOME: {
                    addIncome();
                    break;
                }
                case PURCHASE: {
                    addPurchase();
                    break;
                }
                case LIST_PURCHASES: {
                    listPurchases();
                    break;
                }
                case BALANCE: {
                    balance();
                    break;
                }
                case SAVE: {
                    savePurchases();
                    break;
                }
                case LOAD: {
                    loadPurchases();
                    break;
                }
                case ANALYZE: {
                    analyze();
                    break;
                }
                case EXIT: {
                    shutdown();
                }
                default: {
                    // Do Nothing
                }
            }
        }
    }

    /**
     * Asks the user for an income, then adds it to the balance.
     */
    private static void addIncome() {
        System.out.println("\nEnter an income:");
        balance += keyboard.nextInt();
        System.out.println("Income was added!\n");
    }
    /**
     * Asks the user for a purchase, then subtracts it from the balance.
     */
    private static void addPurchase() {
        keyboard.nextLine();
        System.out.println("""
                    
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""");
        int purchaseType = keyboard.nextInt();
        while (purchaseType != 5) {
            keyboard.nextLine();
            System.out.println("\nEnter purchase name: ");
            String name = keyboard.nextLine();
            System.out.println("Enter its price: ");
            double price = keyboard.nextDouble();
            balance -= price;
            if (balance < 0) {
                balance = 0;
            }
            switch (purchaseType) {
                case 1:
                    purchasesFood.add(new Purchase(name, price));
                    purchases.add(new Purchase(name, price));
                    break;
                case 2:
                    purchasesClothes.add(new Purchase(name, price));
                    purchases.add(new Purchase(name, price));
                    break;
                case 3:
                    purchasesEntertainment.add(new Purchase(name, price));
                    purchases.add(new Purchase(name, price));
                    break;
                case 4:
                    purchasesOther.add(new Purchase(name, price));
                    purchases.add(new Purchase(name, price));
                    break;
            }
            System.out.println("Purchase was added!");
            System.out.println("""
                    
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back""");
            purchaseType = keyboard.nextInt();
        }
        System.out.println();
    }

    /**
     * Lists the users purchases, then the sum total of all purchases.
     */
    private static void listPurchases() {
        System.out.println("""

                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""");
        int purchaseType = keyboard.nextInt();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        System.out.println();
        switch (purchaseType) {
            case 1:
                System.out.println("Food:");
                if (!purchasesFood.isEmpty()) {
                    double sum = 0;
                    for (Purchase purchase : purchasesFood) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                    System.out.println("Total sum: $" + decimalFormat.format(sum));
                } else {
                    System.out.println("The purchase list is empty");
                }
                listPurchases();
                break;
            case 2:
                System.out.println("Clothes:");
                if (!purchasesClothes.isEmpty()) {
                    double sum = 0;
                    for (Purchase purchase : purchasesClothes) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                    System.out.println("Total sum: $" + decimalFormat.format(sum));
                } else {
                    System.out.println("The purchase list is empty");
                }
                listPurchases();
                break;
            case 3:
                System.out.println("Entertainment:");
                if (!purchasesEntertainment.isEmpty()) {
                    double sum = 0;
                    for (Purchase purchase : purchasesEntertainment) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                    System.out.println("Total sum: $" + decimalFormat.format(sum));
                } else {
                    System.out.println("The purchase list is empty");
                }
                listPurchases();
                break;
            case 4:
                System.out.println("Other:");
                if (!purchasesOther.isEmpty()) {
                    double sum = 0;
                    for (Purchase purchase : purchasesOther) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                    System.out.println("Total sum: $" + decimalFormat.format(sum));
                } else {
                    System.out.println("The purchase list is empty");
                }
                listPurchases();
                break;
            case 5:
                System.out.println("All:");
                double sum = 0;
                if (!purchasesFood.isEmpty()) {
                    for (Purchase purchase : purchasesFood) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                }
                if (!purchasesClothes.isEmpty()) {
                    for (Purchase purchase : purchasesClothes) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                }
                if (!purchasesEntertainment.isEmpty()) {
                    for (Purchase purchase : purchasesEntertainment) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                }
                if (!purchasesOther.isEmpty()) {
                    for (Purchase purchase : purchasesOther) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        sum += purchase.getCost();
                    }
                }
                if (sum == 0) {
                    System.out.println("The purchase list is empty");
                } else {
                    System.out.println("Total sum: $" + decimalFormat.format(sum));
                }
                listPurchases();
                break;
            case 6:
                break;
        }
    }

    /**
     * Prints the user's balance.
     */
    private static void balance() {
        System.out.printf("\nBalance: $%f\n\n", balance);
    }

    /**
     * Save purchases to file.
     */
    public static void savePurchases() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("purchases.txt"))) {
            // Write each list to the file
            outputStream.writeObject(purchasesFood);
            outputStream.writeObject(purchasesClothes);
            outputStream.writeObject(purchasesEntertainment);
            outputStream.writeObject(purchasesOther);
            outputStream.writeObject(purchases);
            outputStream.writeObject(balance);
            System.out.println("\nPurchases were saved!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load purchases from file.
     */
    public static void loadPurchases() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("purchases.txt"))) {
            // Read each list from the file
            purchasesFood = (List<Purchase>) inputStream.readObject();
            purchasesClothes = (List<Purchase>) inputStream.readObject();
            purchasesEntertainment = (List<Purchase>) inputStream.readObject();
            purchasesOther = (List<Purchase>) inputStream.readObject();
            purchases = (List<Purchase>) inputStream.readObject();
            balance = (double) inputStream.readObject();
            System.out.println("\nPurchases were loaded!\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void analyze() {
        System.out.println("""
                
                How do you want to sort?
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""");
        int sortType = keyboard.nextInt();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        switch (sortType) {
            case 1:
                if (!purchases.isEmpty()) {
                    System.out.println("\nAll:");
                    purchases.sort(Comparator.comparingDouble(Purchase::getCost).reversed());
                    double cost = 0;
                    for (Purchase purchase : purchases) {
                        System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                        cost += purchase.getCost();
                    }
                    System.out.println("Total: $" + decimalFormat.format(cost) + "\n");
                } else {
                    System.out.println("\nThe purchase list is empty!");
                }
                analyze();
                break;
            case 2:
                double cost = 0;
                double costFood = 0;
                double costClothes = 0;
                double costEntertainment = 0;
                double costOther = 0;
                for (Purchase purchase : purchasesFood) {
                    costFood += purchase.getCost();
                    cost += purchase.getCost();
                }
                for (Purchase purchase : purchasesClothes) {
                    costClothes += purchase.getCost();
                    cost += purchase.getCost();
                }
                for (Purchase purchase : purchasesEntertainment) {
                    costEntertainment += purchase.getCost();
                    cost += purchase.getCost();
                }
                for (Purchase purchase : purchasesOther) {
                    costOther += purchase.getCost();
                    cost += purchase.getCost();
                }
                Map<String,Double> types = new HashMap<>();
                types.put("Food", costFood);
                types.put("Entertainment", costEntertainment);
                types.put("Clothes", costClothes);
                types.put("Other", costOther);
                Map<String, Double> sortedMap = types.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));

                System.out.println("\nTypes:");
                sortedMap.forEach((key, value) -> System.out.println(key + " - $" + decimalFormat.format(value)));
                System.out.println("Total sum: $" + decimalFormat.format(cost));
                analyze();
                break;
            case 3:
                System.out.println("""
                                        
                        Choose the type of purchase
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other""");
                int type = keyboard.nextInt();
                if (type == 1) {
                    if (!purchasesFood.isEmpty()) {
                        System.out.println("\nAll:");
                        purchasesFood.sort(Comparator.comparingDouble(Purchase::getCost).reversed());
                        double a = 0;
                        for (Purchase purchase : purchasesFood) {
                            System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                            a += purchase.getCost();
                        }
                        System.out.println("Total sum: $" + decimalFormat.format(a) + "\n");
                    } else {
                        System.out.println("\nThe purchase list is empty!");
                    }
                } else if (type == 2) {
                    if (!purchasesClothes.isEmpty()) {
                        System.out.println("\nAll:");
                        purchasesClothes.sort(Comparator.comparingDouble(Purchase::getCost).reversed());
                        double a = 0;
                        for (Purchase purchase : purchasesClothes) {
                            System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                            a += purchase.getCost();
                        }
                        System.out.println("Total sum: $" + decimalFormat.format(a) + "\n");
                    } else {
                        System.out.println("\nThe purchase list is empty!");
                    }
                } else if (type == 3) {
                    if (!purchasesEntertainment.isEmpty()) {
                        System.out.println("\nAll:");
                        purchasesEntertainment.sort(Comparator.comparingDouble(Purchase::getCost).reversed());
                        double a = 0;
                        for (Purchase purchase : purchasesEntertainment) {
                            System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                            a += purchase.getCost();
                        }
                        System.out.println("Total sum: $" + decimalFormat.format(a) + "\n");
                    } else {
                        System.out.println("\nThe purchase list is empty!");
                    }
                } else if (type == 4) {
                    if (!purchasesOther.isEmpty()) {
                        System.out.println("\nAll:");
                        purchasesOther.sort(Comparator.comparingDouble(Purchase::getCost).reversed());
                        double a = 0;
                        for (Purchase purchase : purchasesOther) {
                            System.out.println(purchase.getName() + " $" + decimalFormat.format(purchase.getCost()));
                            a += purchase.getCost();
                        }
                        System.out.println("Total sum: $" + decimalFormat.format(a) + "\n");
                    } else {
                        System.out.println("\nThe purchase list is empty!");
                    }
                }
                analyze();
                break;
            case 4:
                System.out.println();
                break;
        }
    }

    /**
     * Shuts down the program.
     */
    private static void shutdown() {
        System.out.println("\nBye!");
        running = Status.SHUTDOWN;
        keyboard.close();
        System.exit(0);
    }

    /**
     * An enum of the possible list choices.
     */
    private enum menuSelection {
        EXIT,
        INCOME,
        PURCHASE,
        LIST_PURCHASES,
        BALANCE,
        SAVE,
        LOAD,
        ANALYZE
    }

    /**
     * An enum of the program status.
     */
    private enum Status {
        RUNNING,
        SHUTDOWN
    }
}
/**
 * Represents an item.
 */
public class Purchase implements Serializable {
    /**
     * A description of the item.
     */
    private String name;
    /**
     * The cost of the item.
     */
    private double cost;

    /**
     * Constructor for defining an item.
     *
     * @param name the name of the item
     * @param cost the cost of the item
     */
    public Purchase(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the cost of the item.
     *
     * @return the cost of the item
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the item.
     *
     * @param cost the cost of the item
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Returns the purchase converted into a string.
     *
     * @return the purchase as a string
     */
    public String toString() {
        return String.format("%s$%f", name, cost);
    }
}
