water = 400
milk = 540
coffee_beans = 120
disposable_cups = 9
money = 550


def remaining():
    print(f'The coffee machine has:\n{water} ml of water\n{milk} ml of milk\n{coffee_beans} g of coffee beans\n\
{disposable_cups} disposable cups\n${money} of money\n')


def buy():
    global water, coffee_beans, disposable_cups, milk, money
    print('What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ')
    coffee = input()
    if coffee == '1':
        if water < 250:
            print('Sorry, not enough water!')
        elif coffee_beans < 16:
            print('Sorry, not enough coffee beans!')
        elif disposable_cups < 1:
            print('Sorry not enough disposable cups!')
        else:
            print('I have enough resources, making you a coffee!')
            water -= 250
            coffee_beans -= 16
            disposable_cups -= 1
            money += 4
        print()
    elif coffee == '2':
        if water < 350:
            print('Sorry, not enough water!')
        elif milk < 75:
            print('Sorry, not enough milk!')
        elif coffee_beans < 20:
            print('Sorry, not enough coffee beans!')
        elif disposable_cups < 1:
            print('Sorry not enough disposable cups!')
        else:
            print('I have enough resources, making you a coffee!')
            water -= 350
            milk -= 75
            coffee_beans -= 20
            disposable_cups -= 1
            money += 7
        print()
    elif coffee == '3':
        if water < 200:
            print('Sorry, not enough water!')
        elif milk < 100:
            print('Sorry, not enough milk!')
        elif coffee_beans < 12:
            print('Sorry, not enough coffee beans!')
        elif disposable_cups < 1:
            print('Sorry not enough disposable cups!')
        else:
            print('I have enough resources, making you a coffee!')
            water -= 200
            milk -= 100
            coffee_beans -= 12
            disposable_cups -= 1
            money += 6
        print()
    elif coffee == 'back':
        print()


def fill():
    global water, coffee_beans, disposable_cups, milk
    print('Write how many ml of water you want to add: ')
    water += int(input())
    print('Write how many ml of milk you want to add: ')
    milk += int(input())
    print('Write how many grams of coffee beans you want to add: ')
    coffee_beans += int(input())
    print('Write how many disposable cups you want to add: ')
    disposable_cups += int(input())
    print()


def take():
    global money
    print(f'I gave you ${money}')
    money = 0
    print()


print('Write action (buy, fill, take, remaining, exit):')
action = input()
while action != 'exit':
    if action == 'buy':
        buy()
    elif action == 'fill':
        fill()
    elif action == 'take':
        take()
    elif action == 'remaining':
        remaining()
    action = input()

