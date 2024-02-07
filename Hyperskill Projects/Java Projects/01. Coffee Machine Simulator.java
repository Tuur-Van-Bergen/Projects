package machine;
import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int water = 400;
        int milk = 540;
        int beans = 120;
        int cups = 9;
        int money = 550;
        String exit  = "false";
        while (exit.equals("false")) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.nextLine().toUpperCase();;
            if (action.equals("BUY")) {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                String kindOfCoffee = scanner.nextLine();
                if (kindOfCoffee.equals("1")) {
                    if (water >= 250 && beans >= 16 && cups >= 1) {
                        water -= 250;
                        beans -= 16;
                        cups -= 1;
                        money += 4;
                        System.out.println("I have enough resources, making you a coffee!");
                    } else if (beans >= 16 && cups >= 1) {
                        System.out.println("Sorry, not enough water!");
                    } else if (water >= 250 && beans >= 16) {
                        System.out.println("Sorry, not enough cups!");
                    } else if (water >= 250 && cups >= 1) {
                        System.out.println("Sorry, not enough coffee beans!");
                    }
                    
                } else if (kindOfCoffee.equals("2")) {
                    if (water >= 350 && milk >= 75 && beans >= 20 && cups >= 1) {
                        water -= 350;
                        milk -= 75;
                        beans -= 20;
                        cups -= 1;
                        money += 7;
                        System.out.println("I have enough resources, making you a coffee!");
                    } else if (milk >= 75 && beans >= 20 && cups >= 1) {
                        System.out.println("Sorry, not enough water!");
                    } else if (water >= 350 && milk >= 75 && beans >= 20) {
                        System.out.println("Sorry, not enough cups!");
                    } else if (water >= 350 && milk >= 75 && cups >= 1) {
                        System.out.println("Sorry, not enough coffee beans!");
                    } else if (water >= 350 && beans >= 20 && cups >= 1) {
                        System.out.println("Sorry, not enough milk!");
                    }
                } else if (kindOfCoffee.equals("3")) {
                    if (water >= 200 && milk >= 100 && beans >= 12 && cups >= 1) {
                        water -= 200;
                        milk -= 100;
                        beans -= 12;
                        cups -= 1;
                        money += 6;
                        System.out.println("I have enough resources, making you a coffee!");
                    } else if (milk >= 100 && beans >= 12 && cups >= 1) {
                        System.out.println("Sorry, not enough water!");
                    } else if (water >= 200 && milk >= 100 && beans >= 12) {
                        System.out.println("Sorry, not enough cups!");
                    } else if (water >= 200 && milk >= 100 && cups >= 1) {
                        System.out.println("Sorry, not enough coffee beans!");
                    } else if (water >= 200 && beans >= 12 && cups >= 1) {
                        System.out.println("Sorry, not enough milk!");
                    }
                } else if (kindOfCoffee.equalsIgnoreCase("back")) {
                    System.out.println();
                }
                System.out.println();
            } else if (action.equals("FILL")) {
                System.out.println("Write how many ml of water you want to add:");
                water += scanner.nextInt();
                System.out.println("Write how many ml of milk you want to add:");
                milk += scanner.nextInt();
                System.out.println("Write how many grams of coffee beans you want to add:");
                beans += scanner.nextInt();
                System.out.println("Write how many disposable cups you want to add:");
                cups += scanner.nextInt();
                System.out.println();
                action = scanner.nextLine();
            } else if (action.equals("TAKE")) {
                System.out.println("I gave you $" + money);
                money = 0;
                System.out.println();
            } else if (action.equals("REMAINING")) {
                System.out.println("The coffee machine has:");
                System.out.println(water + " ml of water");
                System.out.println(milk + " ml of milk");
                System.out.println(beans + " g of coffee beans");
                System.out.println(cups + " disposable cups");
                System.out.println("$" + money + " of money\n");
            } else if (action.equals("EXIT")) {
                exit = "true";
            } else {
                System.out.println("\n");
            }
        }
    }
}
