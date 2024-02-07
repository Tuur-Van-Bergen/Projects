string = "_________"
rows = [string[i:i+3] for i in range(0, len(string), 3)]
string_split = []
for char in range(len(string)):
    string_split.append(string[char])

character = '_'
num1 = 0
num2 = 0
turn = 0
x = 0
o = 0
for char in string:
    if char == 'X':
        x += 1
    elif char == 'O':
        o += 1

print("---------")
print("| "+" ".join(rows[0])+" |" )
print("| "+" ".join(rows[1])+" |" )
print("| "+" ".join(rows[2])+" |" )
print("---------")


while True:
    choice = input()
    parts = choice.split()
    try:
        num1 = int(parts[0])
        num2 = int(parts[1])
        if num1 < 0 and num1 > 4 and num2 < 0 and num2 > 4:
            print("Coordinates should be from 1 to 3!")
            continue
        else:
            if num1 == 1 and num2 == 1:
                if string_split[0] == '_':
                    if turn % 2 == 0:
                        string_split[0] = 'X'
                    else:
                        string_split[0] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 1 and num2 == 2:
                if string_split[1] == '_':
                    if turn % 2 == 0:
                        string_split[1] = 'X'
                    else:
                        string_split[1] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 1 and num2 == 3:
                if string_split[2] == '_':
                    if turn % 2 == 0:
                        string_split[2] = 'X'
                    else:
                        string_split[2] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 2 and num2 == 1:
                if string_split[3] == '_':
                    if turn % 2 == 0:
                        string_split[3] = 'X'
                    else:
                        string_split[3] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 2 and num2 == 2:
                if string_split[4] == '_':
                    if turn % 2 == 0:
                        string_split[4] = 'X'
                    else:
                        string_split[4] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 2 and num2 == 3:
                if string_split[5] == '_':
                    if turn % 2 == 0:
                        string_split[5] = 'X'
                    else:
                        string_split[5] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 3 and num2 == 1:
                if string_split[6] == '_':
                    if turn % 2 == 0:
                        string_split[6] = 'X'
                    else:
                        string_split[6] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 3 and num2 == 2:
                if string_split[7] == '_':
                    if turn % 2 == 0:
                        string_split[7] = 'X'
                    else:
                        string_split[7] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
            elif num1 == 3 and num2 == 3:
                if string_split[8] == '_':
                    if turn % 2 == 0:
                        string_split[8] = 'X'
                    else:
                        string_split[8] = 'O'
                    string = "".join(string_split)
                    rows = [string[i:i+3] for i in range(0, len(string), 3)]
                    print("---------")
                    print("| "+" ".join(rows[0])+" |" )
                    print("| "+" ".join(rows[1])+" |" )
                    print("| "+" ".join(rows[2])+" |" )
                    print("---------")
                    turn += 1
                else:
                    print("This cell is occupied! Choose another one!")
        if string[0:3] == 'XXX' or string[3:6] == 'XXX' or string[6:9] == 'XXX' or \
                (string[0] == 'X' and string[3] == 'X' and string[6] == 'X') or \
                (string[1] == 'X' and string[4] == 'X' and string[7] == 'X') or \
                (string[2] == 'X' and string[5] == 'X' and string[8] == 'X') or \
                (string[0] == 'X' and string[4] == 'X' and string[8] == 'X') or \
                (string[2] == 'X' and string[4] == 'X' and string[6] == 'X'):
            print("X wins")
            break
        elif string[0:3] == 'OOO' or string[3:6] == 'OOO' or string[6:9] == 'OOO' or \
                (string[0] == 'O' and string[3] == 'O' and string[6] == 'O') or \
                (string[1] == 'O' and string[4] == 'O' and string[7] == 'O') or \
                (string[2] == 'O' and string[5] == 'O' and string[8] == 'O') or \
                (string[0] == 'O' and string[4] == 'O' and string[8] == 'O') or \
                (string[2] == 'O' and string[4] == 'O' and string[6] == 'O'):
            print("O wins")
            break
        elif character not in string:
            print("Draw")
            break
    except:
        print("You should enter numbers!")
        continue
