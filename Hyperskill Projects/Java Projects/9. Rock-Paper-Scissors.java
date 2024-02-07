import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name:");
        String userName = scanner.nextLine();
        System.out.println("Hello, " + userName + "!");

        Map<String, Integer> scores = readScores();

        int userScore = scores.getOrDefault(userName, 0);
        System.out.println("Your rating: " + userScore);

        List<String> options = readOptions(scanner);

        System.out.println("Okay, let's start.");

        while (true) {
            String userChoice = scanner.nextLine().toLowerCase();

            if (userChoice.equals("!exit")) {
                break;
            } else if (options.contains(userChoice)) {
                String computerChoice = getRandomChoice(options);
                int result = determineResult(userChoice, computerChoice, options);

                if (result == 0) {
                    System.out.println("There is a draw (" + computerChoice + ")");
                    userScore += 50;
                } else if (result == 1) {
                    System.out.println("Well done. The computer chose " + computerChoice + " and failed");
                    userScore += 100;
                } else {
                    System.out.println("Sorry, but the computer chose " + computerChoice);
                }

                scores.put(userName, userScore);
            } else if (userChoice.equals("!rating")) {
                System.out.println("Your rating: " + userScore);
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("Bye!");
        scanner.close();
    }

    public static Map<String, Integer> readScores() {
        Map<String, Integer> scores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("rating.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.put(name, score);
                }
            }
        } catch (IOException e) {
            // If the file is not found or an error occurs while reading, ignore it and continue with an empty scores map.
        }
        return scores;
    }

    public static List<String> readOptions(Scanner scanner) {
        System.out.println("Enter the list of options (separated by comma):");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return new ArrayList<>(Arrays.asList("rock", "paper", "scissors"));
        } else {
            return new ArrayList<>(Arrays.asList(input.split(",")));
        }
    }

    public static String getRandomChoice(List<String> options) {
        Random random = new Random();
        return options.get(random.nextInt(options.size()));
    }

    public static int determineResult(String userChoice, String computerChoice, List<String> orderedOptions) {
        int totalOptions = orderedOptions.size();
        int userChoiceIndex = orderedOptions.indexOf(userChoice);
        int computerChoiceIndex = orderedOptions.indexOf(computerChoice);
    
        int half = totalOptions / 2;
        int startIndex = userChoiceIndex + 1;
        int endIndex = userChoiceIndex + half;
    
        // Wrap around the list if needed
        if (startIndex >= totalOptions) {
            startIndex -= totalOptions;
        }
        if (endIndex >= totalOptions) {
            endIndex -= totalOptions;
        }
    
        List<String> winningOptions = new ArrayList<>();
        if (startIndex <= endIndex) {
            winningOptions.addAll(orderedOptions.subList(startIndex, endIndex + 1));
        } else {
            winningOptions.addAll(orderedOptions.subList(startIndex, totalOptions));
            winningOptions.addAll(orderedOptions.subList(0, endIndex + 1));
        }
    
        if (userChoiceIndex == computerChoiceIndex) {
            return 0; // Draw
        } else if (winningOptions.contains(computerChoice)) {
            return -1; // Computer wins
        } else {
            return 1; // User wins
        }
    }
}
