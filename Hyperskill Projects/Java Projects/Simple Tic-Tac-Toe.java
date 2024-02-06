import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "         ";
        char[] chars = input.toCharArray();

        // Print the initial grid
        printGrid(chars);

        // Prompt the user to make a move
        int row;
        int col;
        boolean gameOver = false;
        char currentPlayer = 'X';

        while (!gameOver) {
            System.out.print("Enter the coordinates: ");

            try {
                // Read the user's input coordinates
                row = scanner.nextInt();
                col = scanner.nextInt();

                // Convert coordinates to array indices
                int index = getIndex(row, col);

                if (isValidMove(chars, index)) {
                    // Update the grid with the user's move
                    chars[index] = currentPlayer;

                    // Print the updated grid
                    printGrid(chars);

                    // Check if the current player wins
                    if (checkWin(chars, currentPlayer)) {
                        System.out.println(currentPlayer + " wins");
                        gameOver = true;
                        break;
                    } else if (isDraw(chars)) {
                        System.out.println("Draw");
                        gameOver = true;
                        break;
                    }

                    // Switch to the next player
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                scanner.nextLine(); // Consume the invalid input
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Coordinates should be from 1 to 3!");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void printGrid(char[] chars) {
        System.out.println("---------");
        System.out.println("| " + chars[0] + " " + chars[1] + " " + chars[2] + " |");
        System.out.println("| " + chars[3] + " " + chars[4] + " " + chars[5] + " |");
        System.out.println("| " + chars[6] + " " + chars[7] + " " + chars[8] + " |");
        System.out.println("---------");
    }

    private static int getIndex(int row, int col) {
        return (row - 1) * 3 + (col - 1);
    }

    private static boolean isValidMove(char[] chars, int index) {
        return chars[index] == ' ';
    }

    private static boolean checkWin(char[] chars, char player) {
        // Check rows
        for (int i = 0; i <= 6; i += 3) {
            if (chars[i] == player && chars[i + 1] == player && chars[i + 2] == player) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (chars[i] == player && chars[i + 3] == player && chars[i + 6] == player) {
                return true;
            }
        }

        // Check diagonals
        if (chars[0] == player && chars[4] == player && chars[8] == player) {
            return true;
        }
        if (chars[2] == player && chars[4] == player && chars[6] == player) {
            return true;
        }

        return false;
    }

    private static boolean isDraw(char[] chars) {
        for (char c : chars) {
            if (c == ' ') {
                return false; // There is still an empty cell, game is not a draw
            }
        }
        return true; // All cells are filled, game is a draw
    }
}
