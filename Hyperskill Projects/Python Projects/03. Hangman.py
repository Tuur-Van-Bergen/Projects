import random

print("H A N G M A N")
action = ""
wins = 0
loses = 0
while action != "exit":
    action = input("Type \"play\" to play the game, \"results\" to show the scoreboard, and \"exit\" to quit: > results")
    if action == "play":
        word_list = ['python', 'java', 'swift', 'javascript']
        word = random.choice(word_list)
        x=0
        censored_word = "-" * (len(word))
        letters_guessed = set()
        while x != 8:
            print(censored_word)
            letter = input("Input a letter:")
            if not len(letter) == 1:
                print("Please, input a single letter.")
            elif not letter.isalpha() or  not letter.islower():
                print("Please, enter a lowercase letter from the English alphabet.")
            elif letter in letters_guessed:
                print("You've already guessed this letter.")
            elif letter not in word:
                print("That letter doesn't appear in the word.")
                letters_guessed.add(letter)
                x += 1
            else:
                letters_guessed.add(letter)
            censored_word = ''.join(letter if letter in letters_guessed else '-' for letter in word)
            if censored_word == word:
                print(f"You guessed the word {word}!")
                break
                
        if censored_word == word:
            print("You survived!")
            wins += 1
        else: 
            print("You lost!")
            loses += 1
    elif action == "results":
        print(f"You won: {wins} times.")
        print(f"You lost: {loses} times.")
