package main

import (
    "bufio"
    "os"
    "strings"
    "encoding/json"
	"fmt"
	"io/ioutil"
    "flag"
)

var logMessages []string
var exportFileName *string

type Card struct {
    Term string
    Definition string
    WrongAttempts int
}

func setTerm(cards []Card) string {
    reader := bufio.NewReader(os.Stdin)
    term, _ := reader.ReadString('\n')
    term = strings.TrimSpace(term)
    for i := 0; i < len(cards); i++ {
        if cards[i].Term == term {
            log(fmt.Sprintf("The term \"%s\" already exists. Try again:", term))
            return setTerm(cards)
        }
    }
    return term
}

func setDef(cards []Card) string {
    reader := bufio.NewReader(os.Stdin)
    definition, _ := reader.ReadString('\n')
    definition = strings.TrimSpace(definition)
    for i := 0; i < len(cards); i++ {
        if cards[i].Definition == definition {
            log(fmt.Sprintf("The definition \"%s\" already exists. Try again:", definition))
            return setDef(cards)
        }
    }
    return definition
}

func add(cards []Card) []Card {
    log("The card")
    term := setTerm(cards)
    log("The definition of the card")
    definition := setDef(cards)
    cards = append(cards, Card{term, definition, 0})
    log(fmt.Sprintf("The pair (\"%s\":\"%s\") has been added.", term, definition))
    return cards
}

func remove(cards []Card) ([]Card, string) {
	var updatedCards []Card
    var termToRemove string
    log("Which card?")
    fmt.Scanln(&termToRemove)
	for _, card := range cards {
		if card.Term != termToRemove {
			updatedCards = append(updatedCards, card)
		}
	}
	return updatedCards, termToRemove
}

func ask(cards []Card) {
    reader := bufio.NewReader(os.Stdin)
    var ask int
    log("How many times to ask?")
    fmt.Scanln(&ask)
    for i := 0; i < ask; i++ {
        log(fmt.Sprintf("Print the definition of \"%s\":", cards[i % len(cards)].Term))
        answer, _ := reader.ReadString('\n')
        answer = strings.TrimSpace(answer)
        if answer == cards[i % len(cards)].Definition {
            log("Correct!")
        } else {
            cards[i % len(cards)].WrongAttempts += 1
            rightTerm := ""
            for j := 0; j < len(cards); j++ {
                if cards[j].Definition == answer {
                    rightTerm = cards[j].Term
                }
            }
            if rightTerm != "" {
                log(fmt.Sprintf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", cards[i % len(cards)].Definition, rightTerm))
            } else {
                log(fmt.Sprintf("Wrong. The right answer is \"%s\".", cards[i % len(cards)].Definition))
            }
        }
    }
}

func importFromFile(filename string) ([]Card, error) {
	fileContent, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	var loadedCards []Card
	err = json.Unmarshal(fileContent, &loadedCards)
	if err != nil {
		return nil, err
	}

	return loadedCards, nil
}

func exportToFile(cards []Card, filename string) error {
	jsonData, err := json.MarshalIndent(cards, "", "  ")
	if err != nil {
		return err
	}

	return ioutil.WriteFile(filename, jsonData, 0644)
}

func log(message string) {
	fmt.Println(message)
	logMessages = append(logMessages, message)
}

func saveLogsToFile(fileName string) {
	file, err := os.OpenFile(fileName, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644)
	if err != nil {
		log(fmt.Sprintf("Error opening file:", err))
		return
	}
	defer file.Close()

	for _, message := range logMessages {
		if _, err := fmt.Fprintln(file, message); err != nil {
			log(fmt.Sprintf("Error writing to file:", err))
			return
		}
	}
}



func menu(cards []Card) {
    var action, fileName string
    log("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
    reader := bufio.NewReader(os.Stdin)
	action, err := reader.ReadString('\n')
    action = strings.TrimSpace(action)
    if err != nil {
		log(fmt.Sprintf("Error reading input:", err))
		return
	}
    if action == "add" {
        cards = add(cards)
        log("")
        menu(cards)
    } else if action == "remove" {
        newCards, removed := remove(cards)
        if len(newCards) == len(cards) {
            log(fmt.Sprintf("Can't remove \"%s\": there is no such card.", removed))
        } else {
            log("The card has been removed.")
        }
        cards = newCards
        log("")
        menu(cards)
    } else if action == "import" {
        log("File name:")
        fmt.Scanln(&fileName)
    	loadedCards, err := importFromFile(fileName)
    	if err != nil {
    		log("File not found.")
    	}
        for i := 0; i < len(cards); i++ {
            exists := false
            for j := 0; j < len(loadedCards); j++ {
                if cards[i].Term == loadedCards[j].Term {
                    exists = true
                }
            }
            if exists == false {
                loadedCards = append(loadedCards, cards[i])
            }
        }
        cards = loadedCards
        log(fmt.Sprintf("%d cards have been loaded", len(cards)))
        log("")
        menu(cards)
    } else if action == "export" {
        log("File name:")
        fmt.Scanln(&fileName)
        err := exportToFile(cards, fileName)
    	if err != nil {
    		log("File not found.")
    	}
    	log(fmt.Sprintf("%d cards have been saved", len(cards)))
        log("")
        menu(cards)
    } else if action == "ask" {
        ask(cards)
        log("")
        menu(cards)
    } else if action == "exit" {
        log("Bye bye!")
        if *exportFileName != "" {
            err := exportToFile(cards, *exportFileName)
            if err != nil {
                log(fmt.Sprintf("Error exporting flashcards:", err))
            } else {
                log(fmt.Sprintf("%d cards have been saved.\n", len(cards)))
            }
        }
    } else if action == "log" {
        log("File name:")
        fmt.Scanln(&fileName)
        saveLogsToFile(fileName)
        log("The log has been saved.")
        log("")
        menu(cards)
    } else if action == "hardest card" {
        max := 0
        var hardest []Card
        for i := 0; i < len(cards); i++ {
            if cards[i].WrongAttempts > max {
                max = cards[i].WrongAttempts
                hardest = []Card{}
                hardest = append(hardest, cards[i])
            } else if cards[i].WrongAttempts == max {
                hardest = append(hardest, cards[i])
            }
        }
        if max == 0 {
            log("There are no cards with errors.")
        } else {
            if len(hardest) == 1 {
                log(fmt.Sprintf("The hardest card is \"%s\". You have %d errors answering it.", hardest[0].Term, max))
            } else {
                hard := fmt.Sprintf("\"%s\"", hardest[0].Term)
                for i := 1; i < len(hardest); i++ {
                    hard += fmt.Sprintf(", \"%s\"", hardest[i].Term)
                }
                log(fmt.Sprintf("The hardest cards are %s. You have %d errors answering them.", hard, max))
            }
        }
        log("")
        menu(cards)
    } else if action == "reset stats" {
        for i := 0; i < len(cards); i++ {
            cards[i].WrongAttempts = 0
        }
        log("Card statistics have been reset.")
        log("")
        menu(cards)
    }
}

func main() {
    var cards []Card
    importFileName := flag.String("import_from", "", "File to import flashcards from")
	exportFileName = flag.String("export_to", "", "File to export flashcards to")
	flag.Parse()
	if *importFileName != "" {
		// Import flashcards from the specified file
		loadedCards, err := importFromFile(*importFileName)
		if err != nil {
			log(fmt.Sprintf("Error importing flashcards:", err))
		} else {
			cards = loadedCards
			log(fmt.Sprintf("%d cards have been loaded", len(cards)))
            log("")
		}
	}
    menu(cards)
}
