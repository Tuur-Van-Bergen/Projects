package bullscows;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int length;
        if (scanner.hasNextInt()) {
            length = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Error: Invalid input! Please enter a valid integer.");
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbols = scanner.nextInt();
        StringBuilder secretCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            secretCode.append("*");
        }
        if (possibleSymbols <= 10) {
            System.out.println("The secret is prepared: " + secretCode + " (0-" + (possibleSymbols - 1) + ").");
            System.out.println("Okay, let's start a game!");
        } else {
            System.out.println("The secret is prepared: " + secretCode + " (0-9, a-" + (char) (possibleSymbols - 11 + 'a') + ").");
            System.out.println("Okay, let's start a game!");
        }

        if (length > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
        } else if ( length > possibleSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + possibleSymbols + " unique symbols.");
        } else if ( length == 0) {
            System.out.println("Error: it's not possible to generate a code with a length 0");
        } else if (possibleSymbols > 36) {
            System.out.println("Error: can't generate a secret number with more then 36 unique digits.");
        } else {
            String secretNumber = generateSecretNumber(length, possibleSymbols);
            print(secretNumber);
        }
    }


    public static String generateSecretNumber(int length, int possibleSymbols) {

        Random random = new Random();




        StringBuilder secretNumberBuilder = new StringBuilder();
        List<Character> availableSymbols = new ArrayList<>();

        // Populate the availableSymbols list with digits 0 to 9 and characters 'a' to 'z'
        for (int i = 0; i < possibleSymbols; i++) {
            if (i <= 9) {
                availableSymbols.add((char) ('0' + i));
            } else {
                availableSymbols.add((char) ('a' + i - 10));
            }
        }

        // Shuffle the list of available symbols
        Collections.shuffle(availableSymbols);

        // Generate the secret number
        for (int i = 0; i < length; i++) {
            secretNumberBuilder.append(availableSymbols.get(i));
        }

        return secretNumberBuilder.toString();
    }

    public static void print(String code) {
        Scanner scanner = new Scanner(System.in);
        int cows = 0;
        int bulls = 0;
        int turn = 1;
        while (bulls != code.length()) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.nextLine();
            if (code.length() != guess.length()){
                System.out.println("Error: guess must have the same amount of characters as the code.");
            }

            cows = 0;
            bulls = 0;
            for (int i = 0; i < code.length(); i++) {
                if ('-' == guess.charAt(i)) System.out.println("Error: Out of range");
                else if (' ' == guess.charAt(i)) System.out.println("Error: Out of range");
                else {
                    if (guess.charAt(i) == code.charAt(i)) {
                        bulls++;
                    }
                    for (int j = 0; j < code.length(); j++) {
                        if (guess.charAt(i) == code.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }
            System.out.print("Grade: ");
            if (bulls == code.length() && code.length() == 1) {
                System.out.println(bulls + " bull.");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else if (bulls == code.length()) {
                System.out.println(bulls + " bulls.");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else if (cows == 1 && bulls == 1) {
                System.out.println(bulls + " bull and " + cows + " cow.");
            } else if (cows == 1 && bulls != 0) {
                System.out.println(bulls + " bulls and " + cows + " cow.");
            } else if (cows != 0 && bulls == 1) {
                System.out.println(bulls + " bull and " + cows + " cows.");
            } else if (cows != 0 && bulls != 0) {
                System.out.println(bulls + " bulls and " + cows + " cows.");
            } else if (cows == 1) {
                System.out.println(cows + " cow.");
            } else if (bulls == 1) {
                System.out.println(bulls + " bull.");
            } else if (cows != 0) {
                System.out.println(cows + " cows.");
            } else if (bulls != 0) {
                System.out.println(bulls + " bulls.");
            } else {
                System.out.println("None.");
            }
            turn++;
        }
    }
}
