package battleship;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;

        char[][] player1GameBoard = createGameBoard(rows, cols);
        char[][] player1HiddenGameBoard = createGameBoard(rows, cols);

        char[][] player2GameBoard = createGameBoard(rows, cols);
        char[][] player2HiddenGameBoard = createGameBoard(rows, cols);

        // Place ships for player 1
        System.out.println("Player 1, place your ships on the game field:");
        printGameBoard(player1GameBoard);
        placeShips(player1GameBoard);
        for (int i = 0; i < rows; i++) {
            Arrays.fill(player1HiddenGameBoard[i], '~');
        }

        // Clear the screen before the next player's turn
        System.out.println("Press Enter and pass the move to another player");
        new Scanner(System.in).nextLine();
        clearScreen();

        // Place ships for player 2
        System.out.println("Player 2, place your ships on the game field:");
        printGameBoard(player2GameBoard);
        placeShips(player2GameBoard);
        for (int i = 0; i < rows; i++) {
            Arrays.fill(player2HiddenGameBoard[i], '~');
        }

        // Clear the screen before the game starts
        System.out.println("Press Enter to start the game");
        new Scanner(System.in).nextLine();
        clearScreen();

        Scanner scanner = new Scanner(System.in);
        System.out.println("The game starts!");
        while (true) {
            System.out.println("Player 2's Hidden Board:");
            printGameBoard(player2HiddenGameBoard);
            System.out.println("---------------------");
            System.out.println("Player 1's Board:");
            printGameBoard(player1GameBoard);
            System.out.println("Player 1, it's your turn!");
            playerMove(player2HiddenGameBoard, player2GameBoard);
            if (allShipsSunk(player2GameBoard)) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            clearScreen();
            

            
            System.out.println("Player 1's Hidden Board:");
            printGameBoard(player1HiddenGameBoard);
            System.out.println("---------------------");
            System.out.println("Player 2's Board:");
            printGameBoard(player2GameBoard);
            System.out.println("Player 2, it's your turn!");
            playerMove(player1HiddenGameBoard, player1GameBoard);
            if (allShipsSunk(player1GameBoard)) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            clearScreen();
        }
    }


    public static char[][] createGameBoard(int rows, int cols) {
        char[][] gameBoard = new char[rows][cols];

        // Initialize the game board with '~'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameBoard[i][j] = '~';
            }
        }

        return gameBoard;
    }

    public static void printGameBoard(char[][] gameBoard) {
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        // Print the header row with numbers
        System.out.print("  ");
        for (int i = 1; i <= cols; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print the game board rows with letters
        char rowLetter = 'A';
        for (int i = 0; i < rows; i++) {
            System.out.print(rowLetter + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
            rowLetter++;
        }
        System.out.println();
    }

    public static void placeShips(char[][] gameBoard) {
        Scanner scanner = new Scanner(System.in);
        String[] shipNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] shipLengths = {5, 4, 3, 3, 2};

        for (int i = 0; i < shipNames.length; i++) {
            System.out.println("Enter the coordinates of the " + shipNames[i] + " (" + shipLengths[i] + " cells):");
            String[] coordinates = scanner.nextLine().trim().toUpperCase().split("\\s+");

            while (!isValidCoordinates(coordinates, gameBoard, shipLengths[i])) {
                System.out.println("Error! " + getErrorMessage(coordinates, gameBoard, shipLengths[i]) + " Try again:");
                coordinates = scanner.nextLine().trim().toUpperCase().split("\\s+");
            }

            int row1 = coordinates[0].charAt(0) - 'A';
            int col1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
            int row2 = coordinates[1].charAt(0) - 'A';
            int col2 = Integer.parseInt(coordinates[1].substring(1)) - 1;

            placeShipOnBoard(gameBoard, row1, col1, row2, col2);
            printGameBoard(gameBoard);
        }
    }

    public static boolean isValidCoordinates(String[] coordinates, char[][] gameBoard, int shipLength) {
        if (coordinates.length != 2) {
            return false;
        }
    
        int row1 = coordinates[0].charAt(0) - 'A';
        int col1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
        int row2 = coordinates[1].charAt(0) - 'A';
        int col2 = Integer.parseInt(coordinates[1].substring(1)) - 1;
    
        // Check if the coordinates are within the game board
        if (row1 < 0 || row1 >= gameBoard.length || col1 < 0 || col1 >= gameBoard[0].length ||
            row2 < 0 || row2 >= gameBoard.length || col2 < 0 || col2 >= gameBoard[0].length) {
            return false;
        }
    
        // Check if the ship length matches the expected length
        if ((row1 == row2 && Math.abs(col1 - col2) + 1 != shipLength) ||
            (col1 == col2 && Math.abs(row1 - row2) + 1 != shipLength)) {
            return false;
        }
    
        // Check if the ship is placed horizontally or vertically
        if (row1 != row2 && col1 != col2) {
            return false;
        }
    
        // Check if the ship crosses or touches any other ship
        Set<Character> shipCharacters = new HashSet<>();
        shipCharacters.add('O');
        int minRow = Math.min(row1, row2);
        int maxRow = Math.max(row1, row2);
        int minCol = Math.min(col1, col2);
        int maxCol = Math.max(col1, col2);
    
        for (int i = minRow - 1; i <= maxRow + 1; i++) {
            for (int j = minCol - 1; j <= maxCol + 1; j++) {
                if (i >= 0 && i < gameBoard.length && j >= 0 && j < gameBoard[0].length &&
                    gameBoard[i][j] != '~' && shipCharacters.contains(gameBoard[i][j])) {
                    return false;
                }
            }
        }
    
        return true;
    }

    
    public static String getErrorMessage(String[] coordinates, char[][] gameBoard, int shipLength) {
        int row1 = coordinates[0].charAt(0) - 'A';
        int col1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
        int row2 = coordinates[1].charAt(0) - 'A';
        int col2 = Integer.parseInt(coordinates[1].substring(1)) - 1;
    
        // Check if the coordinates are within the game board
        if (row1 < 0 || row1 >= gameBoard.length || col1 < 0 || col1 >= gameBoard[0].length ||
            row2 < 0 || row2 >= gameBoard.length || col2 < 0 || col2 >= gameBoard[0].length) {
            return "Coordinates are out of the game board boundaries!";
        }
    
        // Check if the ship length matches the expected length
        if ((row1 == row2 && Math.abs(col1 - col2) + 1 != shipLength) ||
            (col1 == col2 && Math.abs(row1 - row2) + 1 != shipLength)) {
            return "Wrong length of the ship!";
        }
    
        // Check if the ship is placed horizontally or vertically
        if (row1 != row2 && col1 != col2) {
            return "Ships can only be placed horizontally or vertically!";
        }
    
        // Check if the ship crosses or touches any other ship
        Set<Character> shipCharacters = new HashSet<>();
        shipCharacters.add('O');
        for (int i = Math.min(row1, row2); i <= Math.max(row1, row2); i++) {
            for (int j = Math.min(col1, col2); j <= Math.max(col1, col2); j++) {
                if (gameBoard[i][j] != '~' && shipCharacters.contains(gameBoard[i][j])) {
                    return "You placed the ship too close to another one!";
                }
            }
        }
    
        return "";
    }
    
    public static void placeShipOnBoard(char[][] gameBoard, int row1, int col1, int row2, int col2) {
        char shipCharacter = 'O';
        for (int i = Math.min(row1, row2); i <= Math.max(row1, row2); i++) {
            for (int j = Math.min(col1, col2); j <= Math.max(col1, col2); j++) {
                gameBoard[i][j] = shipCharacter;
            }
        }
    }

    public static boolean isCellValid(int row, int col, char[][] gameBoard) {
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        return (row >= 0 && row < rows && col >= 0 && col < cols && gameBoard[row][col] == 'O');
    }
    
    public static boolean allShipsSunk(char[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isShipSunk(int row, int col, char[][] gameBoard) {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        for (int dir = 0; dir < 4; dir++) {
            int newRow = row + dx[dir];
            int newCol = col + dy[dir];

            if (isCellValid(newRow, newCol, gameBoard)) {
                return false; // If adjacent cell contains 'O', ship is not sunk
            }
        }

        return true; // All adjacent cells do not contain 'O', ship is sunk
    }

    public static void playerMove(char[][] hiddenOpponentGameBoard, char[][] opponentGameBoard) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String target = scanner.nextLine().toUpperCase();
            if (target.isEmpty()) {
                System.out.println("Error! You entered an empty input. Try again:");
                continue;
            }
            int row = target.charAt(0) - 'A';
            int col = Integer.parseInt(target.substring(1)) - 1;
    
            if (row < 0 || row >= hiddenOpponentGameBoard.length || col < 0 || col >= hiddenOpponentGameBoard[0].length) {
                System.out.println("Error! You entered wrong coordinates! Try again:");
                continue; // Go to the next iteration of the loop to get valid coordinates
            }
    
            if (opponentGameBoard[row][col] == 'O' || opponentGameBoard[row][col] == 'X') {
                opponentGameBoard[row][col] = 'X';
                hiddenOpponentGameBoard[row][col] = 'X';
                if (isShipSunk(row, col, opponentGameBoard)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
                break;
            } else if (opponentGameBoard[row][col] == '~' || opponentGameBoard[row][col] == 'M') {
                System.out.println("You missed.");
                opponentGameBoard[row][col] = 'M';
                hiddenOpponentGameBoard[row][col] = 'M';
                break;
            }
        }
    }

    public static void clearScreen() {
        System.out.flush();
    }
}
