package readability;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide a filename as a command-line argument.");
            return;
        }

        String filename = args[0];
        String text = readTextFromFile(filename);

        int words = countWords(text);
        int sentences = countSentences(text);
        int characters = countCharacters(text);
        int syllables = countSyllables(text);
        int polysyllables = countPolysyllables(text);

        double ariScore = calculateARIScore(words, sentences, characters);
        double fkScore = calculateFKScore(words, sentences, syllables);
        double smogScore = calculateSMOGScore(polysyllables, sentences);
        double clScore = calculateCLScore(characters, words, sentences);

        int ageARI = getAgeBracket(ariScore);
        int ageFK = getAgeBracket(fkScore);
        int ageSMOG = getAgeBracket(smogScore);
        int ageCL = getAgeBracket(clScore);

        double averageAge = ((((ageARI) + (ageFK) + (ageSMOG) + (ageCL))) / 4.0);

        System.out.println("The text is:\n" + text);
        System.out.println("\nWords: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        String method = "all";
        System.out.println("\nEnter the score you want to calculate (" + method.toUpperCase() + "):");
        String inputMethod = "all";

        if ("ARI".equalsIgnoreCase(inputMethod) || "ALL".equalsIgnoreCase(inputMethod)) {
            System.out.println("Automated Readability Index: " + String.format("%.2f", ariScore) + " (about " + ageARI + "-year-olds).");
        }

        if ("FK".equalsIgnoreCase(inputMethod) || "ALL".equalsIgnoreCase(inputMethod)) {
            System.out.println("Flesch–Kincaid readability tests: " + String.format("%.2f", fkScore) + " (about " + ageFK + "-year-olds).");
        }

        if ("SMOG".equalsIgnoreCase(inputMethod) || "ALL".equalsIgnoreCase(inputMethod)) {
            System.out.println("Simple Measure of Gobbledygook: " + String.format("%.2f", smogScore) + " (about " + ageSMOG + "-year-olds).");
        }

        if ("CL".equalsIgnoreCase(inputMethod) || "ALL".equalsIgnoreCase(inputMethod)) {
            System.out.println("Coleman–Liau index: " + String.format("%.2f", clScore) + " (about " + ageCL + "-year-olds).");
        }

        System.out.println("\nThis text should be understood in average by " + String.format("%.2f", averageAge) + "-year-olds.");
    }

    private static String readTextFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return content.toString();
    }

    private static int countWords(String text) {
        // Split the text into words by spaces
        String[] words = text.split("\\s+");
        return words.length;
    }

    private static int countSyllables(String text) {
        String[] words = text.split("\\s+");
        int syllableCount = 0;

        for (String word : words) {
            int wordSyllables = countSyllablesInWord(word);
            syllableCount += wordSyllables;
        }

        return syllableCount;
    }

    private static int countSyllablesInWord(String word) {
        int syllableCount = 0;
        boolean isPrevVowel = false;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.toLowerCase().charAt(i);
            boolean isVowel = "aeiouy".indexOf(ch) != -1;

            if (isVowel && !isPrevVowel) {
                syllableCount++;
            }

            isPrevVowel = isVowel;
        }

        if (syllableCount == 0) {
            syllableCount = 1;
        }

        // If the last letter is 'e', reduce one syllable (silent 'e')
        if (word.toLowerCase().endsWith("e")) {
            syllableCount--;
        }

        return syllableCount;
    }

    private static int countPolysyllables(String text) {
        String[] words = text.split("\\s+");
        int polysyllableCount = 0;

        for (String word : words) {
            int wordSyllables = countSyllablesInWord(word);
            if (wordSyllables > 2) {
                polysyllableCount++;
            }
        }

        return polysyllableCount;
    }

    private static int countSentences(String text) {
        // Split the text into sentences based on ".", "!", or "?"
        String[] sentences = text.split("[.!?]+");
        int sentenceCount = 0;
        for (String sentence : sentences) {
            sentence = sentence.trim();
            // Check if the sentence is not empty (e.g., due to consecutive punctuation marks)
            if (!sentence.isEmpty()) {
                sentenceCount++;
            }
        }
        return sentenceCount;
    }

    private static int countCharacters(String text) {
        // Count the number of visible characters (excluding spaces, tabs, and newlines)
        return text.replaceAll("[\\s\t\n]", "").length();
    }

    private static double calculateARIScore(int words, int sentences, int characters) {
        double score = 4.71 * (double) characters / words + 0.5 * (double) words / sentences - 21.43;
        return score;
    }

    private static int getAgeBracket(double score) {
        int roundedScore = (int) Math.ceil(score);
        switch (roundedScore) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 8;
            case 4:
                return 9;
            case 5:
                return 10;
            case 6:
                return 11;
            case 7:
                return 12;
            case 8:
                return 13;
            case 9:
                return 14;
            case 10:
                return 15;
            case 11:
                return 16;
            case 12:
                return 17;
            case 13:
                return 18;
            default:
                return 22;

        }
    }

    private static double calculateFKScore(int words, int sentences, int syllables) {
        return 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
    }

    private static double calculateSMOGScore(int polysyllables, int sentences) {
        return 1.043 * Math.sqrt((double) polysyllables * 30 / sentences) + 3.1291;
    }

    private static double calculateCLScore(int characters, int words, int sentences) {
        double L = (double) characters / words * 100;
        double S = (double) sentences / words * 100;
        return 0.0588 * L - 0.296 * S - 15.8;
    }


}
