package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	// Prompt user for the file name
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	fileName := scanner.Text()

	// Open the file
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("Error opening the file:", err)
		return
	}
	defer file.Close()

	// Read taboo words into a set for case-insensitive comparison
	tabooWords := make(map[string]struct{})
	scanner = bufio.NewScanner(file)
	for scanner.Scan() {
		word := scanner.Text()
		tabooWords[strings.ToLower(word)] = struct{}{}
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading the file:", err)
		return
	}

	// Process sentences
	for {
		// Prompt user for a sentence or the exit command
		scanner = bufio.NewScanner(os.Stdin)
		scanner.Scan()
		userSentence := scanner.Text()

		// Check if the user input is the exit command
		if strings.ToLower(userSentence) == "exit" {
			fmt.Println("Bye!")
			return
		}

		// Process the sentence and print the result
		resultSentence := processSentence(userSentence, tabooWords)
		fmt.Println(resultSentence)
	}
}

// Process the sentence and replace taboo words with asterisks
func processSentence(sentence string, tabooWords map[string]struct{}) string {
	words := strings.Fields(sentence)
	for i, word := range words {
		lowercaseWord := strings.ToLower(word)
		if _, isTaboo := tabooWords[lowercaseWord]; isTaboo {
			words[i] = strings.Repeat("*", len(word))
		}
	}
	return strings.Join(words, " ")
}
