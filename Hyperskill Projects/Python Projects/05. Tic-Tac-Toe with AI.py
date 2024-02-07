import re
import random
import math

board = [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ']
winner = False


def print_board():
    global board
    print('---------')
    print(f"| {' '.join(board[0:3])} |")
    print(f"| {' '.join(board[3:6])} |")
    print(f"| {' '.join(board[6:9])} |")
    print('---------')


def check_win():
    global board, winner
    if board[:3] == ['X', 'X', 'X'] or board[3:6] == ['X', 'X', 'X'] or board[6:] == ['X', 'X', 'X'] or \
            board[::3] == ['X', 'X', 'X'] or board[1::3] == ['X', 'X', 'X'] or board[2::3] == ['X', 'X', 'X'] or \
            (board[0] == 'X' and board[4] == 'X' and board[8] == 'X') or \
            (board[2] == 'X' and board[4] == 'X' and board[6] == 'X'):
        winner = True
    elif board[:3] == ['O', 'O', 'O'] or board[3:6] == ['O', 'O', 'O'] or board[6:] == ['O', 'O', 'O'] or \
            board[::3] == ['O', 'O', 'O'] or board[1::3] == ['O', 'O', 'O'] or board[2::3] == ['O', 'O', 'O'] or \
            (board[0] == 'O' and board[4] == 'O' and board[8] == 'O') or \
            (board[2] == 'O' and board[4] == 'O' and board[6] == 'O'):
        winner = True


def get_result():
    global board, winner
    if board[:3] == ['X', 'X', 'X'] or board[3:6] == ['X', 'X', 'X'] or board[6:] == ['X', 'X', 'X'] or \
            board[::3] == ['X', 'X', 'X'] or board[1::3] == ['X', 'X', 'X'] or board[2::3] == ['X', 'X', 'X'] or \
            (board[0] == 'X' and board[4] == 'X' and board[8] == 'X') or \
            (board[2] == 'X' and board[4] == 'X' and board[6] == 'X'):
        print('X wins')
        winner = True
    elif board[:3] == ['O', 'O', 'O'] or board[3:6] == ['O', 'O', 'O'] or board[6:] == ['O', 'O', 'O'] or \
            board[::3] == ['O', 'O', 'O'] or board[1::3] == ['O', 'O', 'O'] or board[2::3] == ['O', 'O', 'O'] or \
            (board[0] == 'O' and board[4] == 'O' and board[8] == 'O') or \
            (board[2] == 'O' and board[4] == 'O' and board[6] == 'O'):
        print('O wins')
        winner = True
    elif not any(letter == ' ' for letter in board):
        print('Draw')


def easy():
    global board, turn
    valid = False
    while not valid:
        rand = random.randint(0, 8)
        if board[rand] == ' ':
            board[rand] = turn
            print('Making move level "easy"')
            print_board()
            valid = True


def medium():
    global board, turn
    if (board[:3].count('X') == 2 or board[:3].count('O') == 2) and board[:3].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[1] == ' ':
            board[1] = turn
        elif board[2] == ' ':
            board[2] = turn
    elif (board[3:6].count('X') == 2 or board[3:6].count('O') == 2) and board[3:6].count(' ') == 1:
        if board[3] == ' ':
            board[3] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[5] == ' ':
            board[5] = turn
    elif (board[6:].count('X') == 2 or board[6:].count('O') == 2) and board[6:].count(' ') == 1:
        if board[6] == ' ':
            board[6] = turn
        elif board[7] == ' ':
            board[7] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[::3].count('X') == 2 or board[::3].count('O') == 2) and board[::3].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[3] == ' ':
            board[3] = turn
        elif board[6] == ' ':
            board[6] = turn
    elif (board[1::3].count('X') == 2 or board[1::3].count('O') == 2) and board[1::3].count(' ') == 1:
        if board[1] == ' ':
            board[1] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[7] == ' ':
            board[7] = turn
    elif (board[2::3].count('X') == 2 or board[2::3].count('X') == 2) and board[2::3].count(' ') == 1:
        if board[2] == ' ':
            board[2] = turn
        elif board[5] == ' ':
            board[5] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[0::4].count('X') == 2 or board[0::4].count('O') == 2) and board[0::4].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[2:7:2].count('X') == 2 or board[2:7:2].count('O') == 2) and board[2:7:2].count(' ') == 1:
        if board[2] == ' ':
            board[2] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[6] == ' ':
            board[6] = turn
    else:
        valid = False
        while not valid:
            rand = random.randint(0, 8)
            if board[rand] == ' ':
                board[rand] = turn
                valid = True
    print('Making move level "medium"')
    print_board()


def minimax(depth, is_maximizing_player):
    global board, turn
    if depth == 0 or is_full():
        return get_heuristic()

    if is_maximizing_player:
        best_value = -math.inf
        for i in range(len(board)):
            if board[i] == ' ':
                board[i] = turn
                value = minimax(depth - 1, False)
                board[i] = ' '
                best_value = max(best_value, value)
        return best_value
    else:
        best_value = math.inf
        for i in range(len(board)):
            if board[i] == ' ':
                board[i] = 'O' if turn == 'X' else 'X'
                value = minimax(depth - 1, True)
                board[i] = ' '
                best_value = min(best_value, value)
        return best_value


def get_heuristic():
    global board, turn
    opponent = 'X' if turn == 'O' else 'O'
    score = 0

    # Check for horizontal lines of 3
    for i in range(0, 7, 3):
        score += get_horizontal_line_score(i, opponent)

    # Check for vertical lines of 3
    for i in range(3):
        score += get_vertical_line_score(i, opponent)

    # Check for diagonals of 3
    score += get_diagonal_line_score(opponent)

    return score


def get_horizontal_line_score(index, opponent):
    global board, turn
    count = 0
    for i in range(index, index + 3):
        if board[i] == turn:
            count += 1
        elif board[i] == opponent:
            count -= 1
    return count


def get_vertical_line_score(index, opponent):
    global board, turn
    count = 0
    for i in range(index, 7, 3):
        if board[i] == turn:
            count += 1
        elif board[i] == opponent:
            count -= 1
    return count


def get_diagonal_line_score(opponent):
    global board, turn
    count = 0
    for i in range(0, 9, 4):
        if board[i] == turn:
            count += 1
        elif board[i] == opponent:
            count -= 1
    return count


def is_full():
    global board
    return ' ' not in board


def choose_best_move():
    global board, turn
    best_value = -math.inf
    best_move = -1
    for i in range(len(board)):
        if board[i] == ' ':
            board[i] = turn
            value = minimax(9, False)
            board[i] = ' '
            if value > best_value:
                best_value = value
                best_move = i
    return best_move


def hard():
    global board, turn
    if (board[:3].count('X') == 2 or board[:3].count('O') == 2) and board[:3].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[1] == ' ':
            board[1] = turn
        elif board[2] == ' ':
            board[2] = turn
    elif (board[3:6].count('X') == 2 or board[3:6].count('O') == 2) and board[3:6].count(' ') == 1:
        if board[3] == ' ':
            board[3] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[5] == ' ':
            board[5] = turn
    elif (board[6:].count('X') == 2 or board[6:].count('O') == 2) and board[6:].count(' ') == 1:
        if board[6] == ' ':
            board[6] = turn
        elif board[7] == ' ':
            board[7] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[::3].count('X') == 2 or board[::3].count('O') == 2) and board[::3].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[3] == ' ':
            board[3] = turn
        elif board[6] == ' ':
            board[6] = turn
    elif (board[1::3].count('X') == 2 or board[1::3].count('O') == 2) and board[1::3].count(' ') == 1:
        if board[1] == ' ':
            board[1] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[7] == ' ':
            board[7] = turn
    elif (board[2::3].count('X') == 2 or board[2::3].count('O') == 2) and board[2::3].count(' ') == 1:
        if board[2] == ' ':
            board[2] = turn
        elif board[5] == ' ':
            board[5] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[0::4].count('X') == 2 or board[0::4].count('O') == 2) and board[0::4].count(' ') == 1:
        if board[0] == ' ':
            board[0] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[8] == ' ':
            board[8] = turn
    elif (board[2:7:2].count('X') == 2 or board[2:7:2].count('O') == 2) and board[2:7:2].count(' ') == 1:
        if board[2] == ' ':
            board[2] = turn
        elif board[4] == ' ':
            board[4] = turn
        elif board[6] == ' ':
            board[6] = turn
    else:
        board[choose_best_move()] = turn
    print('Making move level "hard"')
    print_board()


def make_move():
    global board, turn
    coordinates = input('Enter the coordinates: ')
    valid = False
    while not valid:
        if not re.match(r'^[1-9] [1-9]$', coordinates):
            print('You should enter numbers!')
            coordinates = input('Enter the coordinates: ')
        elif int(coordinates.split()[0]) not in [1, 2, 3] or int(coordinates.split()[1]) not in [1, 2, 3]:
            print('Coordinates should be from 1 to 3!')
            coordinates = input('Enter the coordinates: ')
        else:
            if int(coordinates.split()[0]) == 1:
                if board[int(coordinates.split()[1]) - 1] == ' ':
                    board[int(coordinates.split()[1]) - 1] = turn
                    valid = True
                    print_board()
                else:
                    print('This cell is occupied! Choose another one!')
                    coordinates = input('Enter the coordinates: ')
            elif int(coordinates.split()[0]) == 2:
                if board[int(coordinates.split()[1]) + 2] == ' ':
                    board[int(coordinates.split()[1]) + 2] = turn
                    valid = True
                    print_board()
                else:
                    print('This cell is occupied! Choose another one!')
                    coordinates = input('Enter the coordinates: ')
            else:
                if board[int(coordinates.split()[1]) + 5] == ' ':
                    board[int(coordinates.split()[1]) + 5] = turn
                    valid = True
                    print_board()
                else:
                    print('This cell is occupied! Choose another one!')
                    coordinates = input('Enter the coordinates: ')


input_command = input('Input command: ')
while input_command != 'exit':
    if len(input_command.split()) == 3:
        if input_command.split()[0] != "start" or \
                input_command.split()[1] not in ['user', 'easy', 'medium', 'hard'] or \
                input_command.split()[2] not in ['user', 'easy', 'medium', 'hard']:
            print('Bad parameters!')
        else:
            print_board()
            while any(letter == ' ' for letter in board) and not winner:
                if board.count('X') <= board.count('O'):
                    turn = 'X'
                    if input_command.split()[1] == 'user':
                        make_move()
                    elif input_command.split()[1] == 'easy':
                        easy()
                    elif input_command.split()[1] == 'medium':
                        medium()
                    elif input_command.split()[1] == 'hard':
                        hard()
                    check_win()
                else:
                    turn = 'O'
                    if input_command.split()[2] == 'user':
                        make_move()
                    elif input_command.split()[2] == 'easy':
                        easy()
                    elif input_command.split()[2] == 'medium':
                        medium()
                    elif input_command.split()[2] == 'hard':
                        hard()
                    check_win()
            get_result()
            print()
    else:
        print('Bad parameters!')
    input_command = input('Input command: ')
    board = [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ']
