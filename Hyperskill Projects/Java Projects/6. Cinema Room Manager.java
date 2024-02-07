import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        char[][] seatingArrangement = createSeatingArrangement(rows, seats);
        int totalSeats = rows * seats;

        int option;
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = calculateTotalIncome(rows, seats);

        do {
            printMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    printSeatingArrangement(seatingArrangement);
                    break;
                case 2:
                    int ticketPrice = buyTicket(seatingArrangement, scanner);
                    if (ticketPrice > 0) {
                        purchasedTickets++;
                        currentIncome += ticketPrice;
                    }
                    break;
                case 3:
                    printStatistics(purchasedTickets, totalSeats, currentIncome, totalIncome);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);
    }

    private static char[][] createSeatingArrangement(int rows, int seats) {
        char[][] seatingArrangement = new char[rows][seats];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                seatingArrangement[i][j] = 'S';
            }
        }

        return seatingArrangement;
    }

    private static void printMenu() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    private static void printSeatingArrangement(char[][] seatingArrangement) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= seatingArrangement[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < seatingArrangement.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < seatingArrangement[i].length; j++) {
                System.out.print(seatingArrangement[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int buyTicket(char[][] seatingArrangement, Scanner scanner) {
        int rows = seatingArrangement.length;
        int seats = seatingArrangement[0].length;

        int rowNumber, seatNumber;
        do {
            System.out.println("\nEnter a row number:");
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumber = scanner.nextInt();

            if (rowNumber < 1 || rowNumber > rows || seatNumber < 1 || seatNumber > seats) {
                System.out.println("Wrong input! Please try again.");
            } else if (seatingArrangement[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                seatingArrangement[rowNumber - 1][seatNumber - 1] = 'B';
                int ticketPrice = calculateTicketPrice(rows, seats, rowNumber);
                System.out.println("Ticket price: $" + ticketPrice);
                return ticketPrice;
            }
        } while (true);
    }

    private static int calculateTicketPrice(int rows, int seats, int chosenRow) {
        int totalSeats = rows * seats;
        int frontHalfRows = rows / 2;

        if (totalSeats <= 60 || chosenRow <= frontHalfRows) {
            return 10;
        } else {
            return 8;
        }
    }

    private static void printStatistics(int purchasedTickets, int totalSeats, int currentIncome, int totalIncome) {
        double occupancyPercentage = (double) purchasedTickets / totalSeats * 100;

        System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", occupancyPercentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private static int calculateTotalIncome(int rows, int seats) {
        int totalSeats = rows * seats;

        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontHalfRows = rows / 2;
            int backHalfRows = rows - frontHalfRows;
            return frontHalfRows * seats * 10 + backHalfRows * seats * 8;
        }
    }
}
