import random
import sqlite3

conn = sqlite3.connect('card.s3db')
cur = conn.cursor()
create_table_query = '''
CREATE TABLE IF NOT EXISTS card (
    id INTEGER PRIMARY KEY,
    number TEXT,
    pin TEXT,
    balance INTEGER DEFAULT 0
)
'''

cur.execute(create_table_query)

conn.commit()


def get_balance(card):
    cur.execute(f'''
SELECT
    balance
FROM
    card
WHERE
    number = {card}
''')
    return cur.fetchone()[0]


def do_transfer(card_number):
    print('Transfer')
    print('Enter the card number:')
    card = input()
    cur.execute("SELECT * FROM card WHERE number = ?", (card,))
    result = cur.fetchone()
    new_num = []
    num = list(card[:-1])
    i = 1
    for n in num:
        n = int(n)
        if i % 2 == 1:
            n *= 2
        new_num.append(n)
        i += 1
    new_num2 = []
    for n in new_num:
        if n > 9:
            n -= 9
        new_num2.append(n)
    checksum_number = (10 - (sum(new_num2) % 10)) % 10
    if card_number == card:
        print('''You can't transfer money to the same account!''')
    elif checksum_number != int(card[-1]):
        print('Probably you made a mistake in the card number. Please try again!')
    elif result:
        print('Enter how much money you want to transfer:')
        amount = int(input())
        cur.execute("SELECT * FROM card WHERE number = ?", (card_number,))
        result = cur.fetchone()
        if amount > result[3]:
            print('Not enough money!')
        else:
            cur.execute(f'''
UPDATE card
SET balance = {get_balance(card) + amount}
WHERE
    number = {card}
''')
            conn.commit()
            cur.execute(f'''
UPDATE card
SET balance = {get_balance(card_number) - amount}
WHERE
    number = {card_number}
''')
            conn.commit()
            print('Success!')
    else:
        print('Such a card does not exist.')


Active = True
while Active:
    print('''1. Create an account
2. Log into account
0. Exit''')
    inp = int(input())
    print()
    if inp == 0:
        print('Bye!')
        Active = False
    elif inp == 1:
        card_num = '400000' + str(random.randint(100000000, 999999999))
        new_num = []
        num = list(card_num)
        i = 1
        for n in num:
            n = int(n)
            if i % 2 == 1:
                n *= 2
            new_num.append(n)
            i += 1
        new_num2 = []
        for n in new_num:
            if n > 9:
                n -= 9
            new_num2.append(n)
        checksum_number = (10 - (sum(new_num2) % 10)) % 10
        num.append(str(checksum_number))
        pin = str(random.randint(1000, 9999))
        cur.execute(f'''
INSERT INTO card (id, number, pin, balance)
VALUES ({''.join(num[6:-1])},{''.join(num)} ,{pin} , 0)
''')
        conn.commit()
        print('Your card has been created')
        print('Your card number:')
        print(''.join(num))
        print('Your card PIN:')
        print(pin)
        print()
    elif inp == 2:
        print('Enter your card number:')
        card_num = input()
        print('Enter your PIN:')
        pin = input()
        print()
        cur.execute(f'''
SELECT
    pin
FROM
    card
WHERE
    number = {card_num}
''')
        fetched = cur.fetchone()
        if fetched:
            if fetched[0] == pin:
                print('You have successfully logged in!')
                logged_in = True
                while logged_in:
                    print()
                    print('1. Balance')
                    print('2. Add income')
                    print('3. Do transfer')
                    print('4. Close account')
                    print('5. Log out')
                    print('0. Exit')
                    inp = int(input())
                    print()
                    if inp == 0:
                        print('Bye!')
                        Active = False
                        logged_in = False
                    elif inp == 1:
                        print(f'Balance: {get_balance(card_num)}')
                    elif inp == 2:
                        print('Enter income:')
                        income = int(input())
                        cur.execute(f'''
UPDATE card
SET balance = {get_balance(card_num) + income}
WHERE
    number = {card_num}
''')
                        conn.commit()
                        print('Income was added!')
                    elif inp == 3:
                        do_transfer(card_num)
                    elif inp == 4:
                        print('The account has been closed!')
                        cur.execute(f'''
DELETE FROM card
WHERE number = {card_num}
''')
                        conn.commit()
                    elif inp == 5:
                        print('You have successfully logged out!')
                        print()
                        logged_in = False
            else:
                print('Wrong card number or PIN!')
            print()
        else:
            print('Wrong card number or PIN!')
            print()
