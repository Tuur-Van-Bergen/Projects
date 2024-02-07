import argparse
import json

flashcards = []

parser = argparse.ArgumentParser(description="Flashcards Program")
parser.add_argument("--import_from", help="Import flashcards from a file")
parser.add_argument("--export_to", help="Export flashcards to a file")
args = parser.parse_args()
cards = []
used_terms = []
used_definitions = []
log = ""


def check_values(value):
    for i in range(len(cards)):
        if cards[i][1] == value:
            return cards[i][0]
    return None


def add():
    global log
    card = ['', '', 0]
    print(f'The card: ')
    log += 'The card:\n'
    card[0] = input()
    log += f'{card[0]}\n'
    valid_term = False
    while not valid_term:
        if card[0] in used_terms:
            print(f'The card "{card[0]}" already exists. Try again:')
            log += f'The card "{card[0]}" already exists. Try again:\n'
            card[0] = input()
            log += f'{card[0]}\n'
        else:
            used_terms.append(card[0])
            valid_term = True
    print(f'The definition of the card: ')
    log += f'The definition of the card:\n'
    card[1] = input()
    log += f'{card[1]}\n'
    valid_definition = False
    while not valid_definition:
        if card[1] in used_definitions:
            print(f'The definition "{card[1]}" already exists. Try again:')
            log += f'The definition "{card[1]}" already exists. Try again:\n'
            card[1] = input()
            log += f'{card[0]}\n'
        else:
            used_definitions.append(card[1])
            valid_definition = True
    print(f'The pair ("{card[0]}":"{card[1]}") has been added.')
    log += f'The pair ("{card[0]}":"{card[1]}") has been added.\n'
    cards.append(card)


def remove():
    global log
    print('Which card?')
    log += 'Which card?\n'
    removed = input()
    log += f'{removed}\n'
    card_removed = False
    i = 0
    for card in cards:
        if card[0] == removed:
            cards.pop(i)
            card_removed = True
        i += 1

    if not card_removed:
        print(f'''Can't remove "{removed}": there is no such card.''')
        log += f'''Can't remove "{removed}": there is no such card.\n'''
    else:
        print('The card has been removed')
        log += 'The card has been removed\n'


def ask():
    global log
    print('How many times to ask?')
    log += 'How many times to ask?\n'
    ask_definition = int(input())
    log += f'{ask_definition}\n'
    for i in range(ask_definition):
        print(f'Print the definition of "{cards[i % len(cards)][0]}":')
        log += f'Print the definition of "{cards[i % len(cards)][0]}":\n'
        guess = input()
        log += f'{guess}\n'
        if guess == cards[i % len(cards)][1]:
            print('Correct!')
            log += 'Correct!\n'
        elif check_values(guess) is not None:
            print(f'Wrong. The right answer is "{cards[i % len(cards)][1]}", but your definition is correct for \
"{check_values(guess)}"')
            log += f'Wrong. The right answer is "{cards[i % len(cards)][1]}", but your definition is correct for \
"{check_values(guess)}"\n'
            cards[i % len(cards)][2] += 1
        else:
            print(f'Wrong. The right answer is "{cards[i % len(cards)][1]}"')
            log += f'Wrong. The right answer is "{cards[i % len(cards)][1]}"\n'
            cards[i % len(cards)][2] += 1


def import_file(file_name):
    global log
    try:
        with open(file_name, 'r') as f:
            content = json.load(f)
            count = 0
            for j in range(len(content)):
                card_replaced = False
                for card in cards:
                    if card[0] == content[j][0]:
                        card[1] = content[j][1]
                        card[2] = content[j][2]
                        card_replaced = True
                        count += 1
                if not card_replaced:
                    cards.append([content[j][0], content[j][1], content[j][2]])
                    count += 1
        log += f"{count} cards have been loaded.\n"
        return f"{count} cards have been loaded."
    except FileNotFoundError:
        log += f"File not found.\n"
        return f"File not found."


def export_file(file_name):
    global log
    with open(file_name, 'w') as f:
        json.dump(cards, f)
    log += f'{len(cards)} cards have been saved.\n'
    return f'{len(cards)} cards have been saved.'


def logger():
    global log
    print('File name:')
    log += 'File name:\n'
    file_name = input()
    log += f'{file_name}\n'
    print(f'The log has been saved.')
    log += f'The log has been saved.\n'
    with open(file_name, 'w') as f:
        f.write(log)


def hardest_card():
    global log, cards
    hardest = ''
    score = 0
    for card in cards:
        try:
            integer_part = card[2]
            if integer_part:
                if int(integer_part) == score:
                    hardest += f', "{card[0]}"'
                elif int(integer_part) > score:
                    score = int(integer_part)
                    hardest = f'"{card[0]}"'
        except Exception:
            continue
    if not hardest:
        print('There are no cards with errors.')
        log += 'There are no cards with errors.\n'
    elif len(hardest.split()) == 1:
        print(f'The hardest card is {hardest}. You have {score} errors answering it.')
        log += f'The hardest card is {hardest}. You have {score} errors answering it.\n'
    else:
        print(f'The hardest cards are {hardest}. You have {score} errors answering them.')
        log += f'The hardest cards are {hardest}. You have {score} errors answering them.\n'


def reset_stats():
    global log
    for card in cards:
        card[2] = 0
    print('Card statistics have been reset.')
    log += 'Card statistics have been reset.\n'


if args.import_from:
    print(import_file(args.import_from))
print('Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):')
log += 'Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\n'
action = input()
log += f'{action}\n'
while action != 'exit':
    if action == 'add':
        add()
    elif action == 'remove':
        remove()
    elif action == 'import':
        print('File name')
        log += 'File name\n'
        file = input()
        log += f'{file}\n'
        print(import_file(file))
    elif action == 'export':
        print('File name:')
        log += 'File name:\n'
        file = input()
        log += f'{file}\n'
        print(export_file(file))
    elif action == 'ask':
        ask()
    elif action == "log":
        logger()
    elif action == "hardest card":
        hardest_card()
    elif action == "reset stats":
        reset_stats()
    print()
    print('\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):')
    log += 'Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):\n'
    action = input()
    log += f'{action}\n'
if args.export_to:
    print(export_file(args.export_to))
print('Bye bye!')
