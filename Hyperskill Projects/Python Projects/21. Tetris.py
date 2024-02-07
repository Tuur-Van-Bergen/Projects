pieces = {
    "O": [[4, 14, 15, 5], [4, 14, 15, 5], [4, 14, 15, 5], [4, 14, 15, 5]],
    "I": [[4, 14, 24, 34], [3, 4, 5, 6], [4, 14, 24, 34], [3, 4, 5, 6]],
    "S": [[5, 4, 14, 13], [4, 14, 15, 25], [5, 4, 14, 13], [4, 14, 15, 25]],
    "Z": [[4, 5, 15, 16], [5, 15, 14, 24], [4, 5, 15, 16], [5, 15, 14, 24]],
    "L": [[4, 14, 24, 25], [5, 15, 14, 13], [4, 5, 15, 25], [6, 5, 4, 14]],
    "J": [[5, 15, 25, 24], [15, 5, 4, 3], [5, 4, 14, 24], [4, 14, 15, 16]],
    "T": [[4, 14, 24, 15], [4, 13, 14, 15], [5, 15, 25, 14], [4, 5, 6, 15]]
}

def get_max_col(piece):
    max_col = 0
    for p in piece:
        c = p % 10
        if c > max_col:
            max_col = c
    return max_col


def get_max_row(piece):
    max_row = 0
    for p in piece:
        r = p // 10
        if r > max_row:
            max_row = r
    return max_row


def print_board(board):
    for row in board:
        print(' '.join(row))
    print()


def place_piece(piece, row, col):
    for p in piece:
        r = p // 10
        c = p % 10
        if 0 <= (row + r) < y and 0 <= (col + c) < x:
            board[row + r][col + c] = '0'


def clear_piece(piece, row, col):
    for p in piece:
        r = p // 10
        c = p % 10
        if 0 <= (row + r) < y and 0 <= (col + c) < x:
            board[row + r][col + c] = '-'


x, y = map(int, input().split())
piece_index = 0
row = 0
max_col = 0
col = 0

board = [['-' for _ in range(x)] for _ in range(y)]
print_board(board)
max_row = 0
piece = ""
piece_name = ""


def move_down():
    global row
    if row + max_row < y:
        row += 1


def move_left():
    global col
    if -(x // 2 - max_col // 2) - 1 < col:
        col -= 1
        move_down()

def move_right():
    global col
    global max_col
    if col + max_col + 1 < x:
        col += 1
        move_down()


def rotate_piece_clockwise(piece_name):
    global piece_index, piece
    if piece_index == 3:
        piece_index = 0
    else:
        piece_index += 1
    piece = pieces[piece_name][piece_index]
    move_down()


def collision(piece, row, col):
    for p in piece:
        r = p // 10
        c = p % 10
        if board[row + r + 1][col + c] == '0':
            return True  # Collision detected
    return False  # No collision


def is_bottom(piece, row):
    for p in piece:
        r = p // 10
        if row + r >= y - 1:
            return True
    return False


def break_rows():
    global board
    place_piece(piece, row, col)  # Freeze the current piece
    rows_to_clear = []
    for i in range(y):
        if all(cell == '0' for cell in board[i]):
            rows_to_clear.append(i)

    for row_index in rows_to_clear:
        board.pop(row_index)
        board.insert(0, ['-' for _ in range(x)])



while True:
    command = input()
    if command == 'exit':
        break
    elif command == 'piece':
        row = 0
        col = 0
        piece_name = input()
        piece_index = 0
        piece = pieces[piece_name][piece_index]
        max_col = get_max_col(piece)
        place_piece(piece, row, col)
    elif command == 'down' and not is_bottom(piece, row):
        clear_piece(piece, row, col)
        if not collision(piece, row, col):
            move_down()
        place_piece(piece, row, col)
    elif command == 'left' and not is_bottom(piece, row):
        clear_piece(piece, row, col)
        if not collision(piece, row, col):
            move_left()
        place_piece(piece, row, col)
    elif command == 'right' and not is_bottom(piece, row):
        clear_piece(piece, row, col)
        if not collision(piece, row, col):
            move_right()
        place_piece(piece, row, col)
    elif command == 'rotate' and not is_bottom(piece, row):
        clear_piece(piece, row, col)
        if not collision(piece, row, col):
            rotate_piece_clockwise(piece_name)
        place_piece(piece, row, col)
    elif command == 'break':
        break_rows()

    print_board(board)
    if any(all(row[col] == '0' for row in board) for col in range(x)) and command != 'piece':
        break
