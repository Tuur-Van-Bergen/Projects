import random

existing_pieces = []

count = {
    0: 0,
    1: 0,
    2: 0,
    3: 0,
    4: 0,
    5: 0,
    6: 0
}

def generate_piece():
    while True:
        new_piece = [random.randint(0, 6), random.randint(0, 6)]
        if new_piece not in existing_pieces:
            existing_pieces.append(new_piece)
            return new_piece

def find_highest_double(domino_pieces):
    highest_double = None
    for piece in domino_pieces:
        if piece[0] == piece[1]:
            if highest_double is None or piece[0] > highest_double[0]:
                highest_double = piece
    return highest_double

def has_legal_move(pieces, snake_ends):
    for piece in pieces:
        for end in snake_ends:
            if piece[0] == end or piece[1] == end:
                return True
    return False

def get_highest_count():
    count = {
        0: 0,
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0
    }
    for piece in domino_snake:
        count[piece[0]] += 1
        count[piece[1]] += 1
    for piece in computer_pieces:
        count[piece[0]] += 1
        count[piece[1]] += 1
    pieces_with_scores = [(piece, int(count[piece[0]]) + int(count[piece[1]])) for piece in computer_pieces]
    ordered_computer_pieces = sorted(pieces_with_scores, key=lambda x: x[1], reverse=True)
    sorted_pieces = [piece for piece, score in ordered_computer_pieces]

    return sorted_pieces


status = 0
stock_pieces = [generate_piece() for _ in range(14)]
computer_pieces = [generate_piece() for _ in range(7)]
player_pieces = [generate_piece() for _ in range(7)]
domino_snake = []

highest_double_computer = find_highest_double(computer_pieces)
highest_double_player = find_highest_double(player_pieces)


while highest_double_computer is None and highest_double_player is None:
    stock_pieces = []
    computer_pieces = []
    player_pieces = []
    domino_snake = []
    stock_pieces = [generate_piece() for _ in range(14)]
    computer_pieces = [generate_piece() for _ in range(7)]
    player_pieces = [generate_piece() for _ in range(7)]
    domino_snake = []
    highest_double_computer = find_highest_double(computer_pieces)
    highest_double_player = find_highest_double(player_pieces)

if highest_double_computer is None or (highest_double_player is not None and highest_double_player[0] > highest_double_computer[0]):
    domino_starting_piece = highest_double_player
    player_pieces.remove(domino_starting_piece)
    status = 0
    
else:
    domino_starting_piece = highest_double_computer
    computer_pieces.remove(domino_starting_piece)
    status = 1

domino_snake.append(domino_starting_piece)

def board():
    global status
    result = []
    if len(domino_snake) <= 6:
        result = "".join(map(str, domino_snake))
    else: 
        result = "".join(map(str, domino_snake[:3])) + "..." + "".join(map(str, domino_snake[-3:]))
    print("=" * 70)
    print(f"Stock size: {len(stock_pieces)}")
    print(f"Computer pieces: {len(computer_pieces)}\n")
    print(f"{result}\n")
    print("Your pieces:")
    for i in range(len(player_pieces)):
        print(f"{i + 1}:{player_pieces[i]}")
    print()

    if not has_legal_move(player_pieces, [domino_snake[0][0], domino_snake[-1][1]]) and len(stock_pieces) == 0:
        print("Status: The game is a draw!")
        return
    
    elif status == 0:
        print("Status: Computer is about to make a move. Press Enter to continue...")
        enter = input()
        piece = 0
        x = 0
        while piece == 0:
            sorted_pieces = get_highest_count()
            if len(sorted_pieces) - 1 < x:
                if len(stock_pieces) > 0:
                        computer_pieces.append(stock_pieces[0])
                        stock_pieces.remove(stock_pieces[0])
                break

            piece = sorted_pieces[x]
            if piece[0] == domino_snake[0][0]:
                reversed_piece = [piece[1], piece[0]]
                domino_snake.insert(0, reversed_piece)
                computer_pieces.remove(piece)
            elif piece[1] == domino_snake[0][0]:
                domino_snake.insert(0, piece)
                computer_pieces.remove(piece)
            elif piece[0] == domino_snake[-1][1]:
                domino_snake.append(piece)
                computer_pieces.remove(piece)
            elif piece[1] == domino_snake[-1][1]:
                reversed_piece = [piece[1], piece[0]]
                domino_snake.append(reversed_piece)
                computer_pieces.remove(piece)
            else:
                piece = 0
                x += 1
            
        status += 1
        
    elif status == 1:
        print("Status: It's your turn to make a move. Enter your command.")
        while True:
            piece = input()
            try:
                piece = int(piece)
                if piece == 0:
                    if len(stock_pieces) > 0:
                        player_pieces.append(stock_pieces[0])
                        stock_pieces.remove(stock_pieces[0])
                    break
                if abs(piece) in range(1, len(player_pieces) + 1):
                    if piece < 0:
                        if player_pieces[abs(piece) - 1][0] == domino_snake[0][0]:
                            player_pieces[abs(piece) - 1] = [player_pieces[abs(piece) - 1][1], player_pieces[abs(piece) - 1][0]]
                            domino_snake.insert(0, player_pieces[abs(piece) - 1])
                            player_pieces.remove(player_pieces[abs(piece) - 1])
                            break
                        elif player_pieces[abs(piece) - 1][1] == domino_snake[0][0]:
                            domino_snake.insert(0, player_pieces[abs(piece) - 1])
                            player_pieces.remove(player_pieces[abs(piece) - 1])
                            break
                        else:
                            print("Illegal move. Please try again.")
                                        
                    elif piece > 0:
                        if player_pieces[abs(piece) - 1][0] == domino_snake[-1][1]:
                            domino_snake.append(player_pieces[abs(piece) - 1])
                            player_pieces.remove(player_pieces[abs(piece) - 1])
                            break
                        elif player_pieces[abs(piece) - 1][1] == domino_snake[-1][1]:
                            player_pieces[abs(piece) - 1] = [player_pieces[abs(piece) - 1][1], player_pieces[abs(piece) - 1][0]]
                            domino_snake.append(player_pieces[abs(piece) - 1])
                            player_pieces.remove(player_pieces[abs(piece) - 1])
                            break
                        else:
                            print("Illegal move. Please try again.")
                else:
                    print("Invalid input. Please try again.")
            except ValueError:
                print("Invalid input. Please try again.")

        status -= 1

    
        


def end_game():
    global status
    result = []
    if len(domino_snake) <= 6:
        result = "".join(map(str, domino_snake))
    else: 
        result = "".join(map(str, domino_snake[:3])) + "..." + "".join(map(str, domino_snake[-3:]))
    print("=" * 70)
    print(f"Stock size: {len(stock_pieces)}")
    print(f"Computer pieces: {len(computer_pieces)}\n")
    print(f"{result}\n")
    print("Your pieces:")
    for i in range(len(player_pieces)):
        print(f"{i + 1}:{player_pieces[i]}")
    print()
    if len(computer_pieces) == 0:
        print("Status: The game is over. The computer won!")
    elif len(player_pieces) == 0:
        print("Status: The game is over. You won!")

while True:
    board()
    
    if not has_legal_move(player_pieces, [domino_snake[0][0], domino_snake[-1][1]]) and len(stock_pieces) == 0:
        print("Status: The game is a draw!")
        break

    if len(player_pieces) == 0:
        print("Status: The game is over. You won!")
        break

    if len(computer_pieces) == 0:
        print("Status: The game is over. The computer won!")
        break
end_game()

