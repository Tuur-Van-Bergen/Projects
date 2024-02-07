package minesweeper;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int width = 9;
        int height = 9;
        char[][] raster = new char[height][width];
        char[][] board = new char[height][width];
        System.out.print("How many mines do you want on the field?");
        int mines = scanner.nextInt();
        char[][] correctRaster = createBoard(board, mines);

        /*      Initialize field         */
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                raster[i][j] = '.';
            }
        }

        /*      Show field      */
        printBoard(raster);
        boolean playing = true;
        /*      Gameplay        */
        while(playing){
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            int y = scanner.nextInt() - 1;
            int x= scanner.nextInt() - 1;
            String operation = scanner.next();
            if (operation.equals("free")) {
                if (correctRaster[x][y] == 'X') {
                    for(int i = 0; i < 9; i++) {
                        for(int j = 0; j < 9; j++) {
                            if (correctRaster[i][j] == 'X') {
                                raster[i][j] = 'X';
                            }
                        }
                    }
                    printBoard(raster);
                    System.out.println("You stepped on a mine and failed!");
                    playing = false;
                } else {
                    updateBoard(raster, correctRaster, x, y);
                    printBoard(raster);
                }
            } else if (operation.equals("mine")) {
                if (raster[x][y] == '.') {
                    raster[x][y] = '*';
                    printBoard(raster);
                } else if (raster[x][y] == '*') {
                    raster[x][y] = '.';
                    printBoard(raster);
                }
                boolean allCorrect = true;

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if ((correctRaster[i][j] == 'X' && raster[i][j] != '*') || (correctRaster[i][j] != 'X' && raster[i][j] == '*')) {
                            allCorrect = false;
                            break; // No need to continue checking, we found a discrepancy
                        }
                    }
                    if (!allCorrect) {
                        break; // No need to continue checking, we found a discrepancy
                    }
                }

                if (allCorrect) {
                    System.out.println("Congratulations! You found all the mines!");
                }

                playing = !allCorrect;

            }

        }
    }

    public static void printBoard(char[][] raster){
        int width = 9;
        int height = 9;
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for(int i = 0; i < width; i++) {
            System.out.print((i+1) + "|");
            for(int j = 0; j < height; j++) {
                System.out.print(raster[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public static char[][] createBoard(char[][] correctRaster, int mines) {
        Random random = new Random();

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                correctRaster[i][j] = '.';
            }
        }

        /*      Generate random mine field      */
        for(int x = 0; x < mines; x++) {
            boolean check = true;
            while(check) {
                int row = random.nextInt(9);
                int col = random.nextInt(9);
                if (correctRaster[row][col] == '.') {
                    correctRaster[row][col] = 'X';
                    check = false;
                }
            }
        }

        /*      Show the numbers        */
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int count = getCount(correctRaster, i, j);
                if (correctRaster[i][j] == 'X') {
                    correctRaster[i][j] = 'X';
                } else if (count == 0) {
                    correctRaster[i][j] = '/';
                } else {
                    correctRaster[i][j] = (char) ('0' + count);
                }
            }
        }
        return correctRaster;
    }

    private static int getCount(char[][] correctRaster, int i, int j) {
        int count = 0;
        // Check left diagonal (upper and lower)
        if (i > 0 && j > 0 && correctRaster[i - 1][j - 1] == 'X') {
            count = count + 1;
        }
        if (i > 0 && j < 9 - 1 && correctRaster[i - 1][j + 1] == 'X') {
            count = count + 1;
        }

        // Check right diagonal (upper and lower)
        if (i < 9 - 1 && j > 0 && correctRaster[i + 1][j - 1] == 'X') {
            count = count + 1;
        }
        if (i < 9 - 1 && j < 9 - 1 && correctRaster[i + 1][j + 1] == 'X') {
            count = count + 1;
        }

        // Check horizontal (left and right)
        if (i > 0 && correctRaster[i - 1][j] == 'X') {
            count = count + 1;
        }
        if (i < 9 - 1 && correctRaster[i + 1][j] == 'X') {
            count = count + 1;
        }

        // Check vertical (above and below)
        if (j > 0 && correctRaster[i][j - 1] == 'X') {
            count = count + 1;
        }
        if (j < 9 - 1 && correctRaster[i][j + 1] == 'X') {
            count = count + 1;
        }
        return count;
    }

    public static void updateBoard(char[][] raster, char[][] correctRaster, int x, int y) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9 || raster[x][y] != '.' && raster[x][y] != '*' ) {
            return;
        }

        if (correctRaster[x][y] != 'X') {
            raster[x][y] = correctRaster[x][y];

            if (correctRaster[x][y] == '/') {
                updateBoard(raster, correctRaster, x - 1, y - 1);
                updateBoard(raster, correctRaster, x - 1, y);
                updateBoard(raster, correctRaster, x - 1, y + 1);
                updateBoard(raster, correctRaster, x, y - 1);
                updateBoard(raster, correctRaster, x, y + 1);
                updateBoard(raster, correctRaster, x + 1, y - 1);
                updateBoard(raster, correctRaster, x + 1, y);
                updateBoard(raster, correctRaster, x + 1, y + 1);
            }
        }
    }
}
