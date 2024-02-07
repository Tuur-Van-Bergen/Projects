def print_board():
    column_n = len(board[0])
    row_n = len(board)
    total_cells = column_n * row_n
    cell_size = len(str(total_cells))
    border_length = column_n * (cell_size + 1) + 3

    print(" " + "-" * border_length)
    for i, row in enumerate(board[::-1], 1):
        print(f"{len(board) - i + 1}|", end=" ")
        for cell in row:
            print(cell.ljust(cell_size), end=" ")
        print("|")
    print(" " + "-" * border_length)
    print("  ", end="")
    for i in range(1, column_n + 1):
        print(str(i).rjust(cell_size + 1, ' '), end="")
    print()


def get_dimensions():
    while True:
        dimensions = input("Enter your board dimensions: ").split()
        if len(dimensions) != 2:
            print("Invalid dimensions!")
        else:
            try:
                x, y = int(dimensions[0]), int(dimensions[1])
                if x <= 0 or y <= 0:
                    print("Invalid dimensions!")
                else:
                    return x, y
            except ValueError:
                print("Invalid dimensions!")


def get_starting_position(x, y):
    while True:
        position = input("Enter the knight's starting position: ").split()
        if len(position) != 2:
            print("Invalid position!")
        else:
            try:
                x_pos, y_pos = int(position[0]), int(position[1])
                if not 1 <= x_pos <= x or not 1 <= y_pos <= y:
                    print("Invalid position!")
                else:
                    return x_pos, y_pos
            except ValueError:
                print("Invalid position!")


def check_moves(x, y, x_pos, y_pos):
    if y_pos + 2 <= y and x_pos + 1 <= x and board[y_pos + 1][x_pos] != " *" and board[y_pos + 1][x_pos] != " X":
        result = check_possible_moves(x, y, x_pos + 1, y_pos + 2)
        board[y_pos + 1][x_pos] = " " + str(result)
    if y_pos + 2 <= y and x_pos - 1 > 0 and board[y_pos + 1][x_pos - 2] != " *" and board[y_pos + 1][x_pos - 2] != " X":
        result = check_possible_moves(x, y, x_pos - 1, y_pos + 2)
        board[y_pos + 1][x_pos - 2] = " " + str(result)
    if y_pos - 2 > 0 and x_pos + 1 <= x and board[y_pos - 3][x_pos] != " *" and board[y_pos - 3][x_pos] != " X":
        result = check_possible_moves(x, y, x_pos + 1, y_pos - 2)
        board[y_pos - 3][x_pos] = " " + str(result)
    if y_pos - 2 > 0 and x_pos - 1 > 0 and board[y_pos - 3][x_pos - 2] != " *" and board[y_pos - 3][x_pos - 2] != " X":
        result = check_possible_moves(x, y, x_pos - 1, y_pos - 2)
        board[y_pos - 3][x_pos - 2] = " " + str(result)
    if y_pos + 1 <= y and x_pos + 2 <= x and board[y_pos][x_pos + 1] != " *" and board[y_pos][x_pos + 1] != " X":
        result = check_possible_moves(x, y, x_pos + 2, y_pos + 1)
        board[y_pos][x_pos + 1] = " " + str(result)
    if y_pos - 1 > 0 and x_pos + 2 <= x and board[y_pos - 2][x_pos + 1] != " *" and board[y_pos - 2][x_pos + 1] != " X":
        result = check_possible_moves(x, y, x_pos + 2, y_pos - 1)
        board[y_pos - 2][x_pos + 1] = " " + str(result)
    if y_pos + 1 <= y and x_pos - 2 > 0 and board[y_pos][x_pos - 3] != " *" and board[y_pos][x_pos - 3] != " X":
        result = check_possible_moves(x, y, x_pos - 2, y_pos + 1)
        board[y_pos][x_pos - 3] = " " + str(result)
    if y_pos - 1 > 0 and x_pos - 2 > 0 and board[y_pos - 2][x_pos - 3] != " *" and board[y_pos - 2][x_pos - 3] != " X":
        result = check_possible_moves(x, y, x_pos - 2, y_pos - 1)
        board[y_pos - 2][x_pos - 3] = " " + str(result)


def possible_moves(x, y, x_pos, y_pos, next_x, next_y):
    if y_pos + 2 == next_y and x_pos + 1 == next_x and y_pos + 2 <= y and x_pos + 1 <= x:
        return True
    if y_pos + 2 == next_y and x_pos - 1 == next_x and y_pos + 2 <= y and x_pos - 1 > 0:
        return True
    if y_pos - 2 == next_y and x_pos + 1 == next_x and y_pos - 2 > 0 and x_pos + 1 <= x:
        return True
    if y_pos - 2 == next_y and x_pos - 1 == next_x and y_pos - 2 > 0 and x_pos - 1 > 0:
        return True
    if y_pos + 1 == next_y and x_pos + 2 == next_x and y_pos + 1 <= y and x_pos + 2 <= x:
        return True
    if y_pos - 1 == next_y and x_pos + 2 == next_x and y_pos - 1 > 0 and x_pos + 2 <= x:
        return True
    if y_pos + 1 == next_y and x_pos - 2 == next_x and y_pos + 1 <= y and x_pos - 2 > 0:
        return True
    if y_pos - 1 == next_y and x_pos - 2 == next_x and y_pos - 1 > 0 and x_pos - 2 > 0:
        return True
    return False


def check_possible_moves(x, y, x_pos, y_pos):
    count = 0
    if y_pos + 2 <= y and x_pos + 1 <= x and board[y_pos + 1][x_pos] != " *" and board[y_pos + 1][x_pos] != " X":
        count += 1
    if y_pos + 2 <= y and x_pos - 1 > 0 and board[y_pos + 1][x_pos - 2] != " *" and board[y_pos + 1][x_pos - 2] != " X":
        count += 1
    if y_pos - 2 > 0 and x_pos + 1 <= x and board[y_pos - 3][x_pos] != " *" and board[y_pos - 3][x_pos] != " X":
        count += 1
    if y_pos - 2 > 0 and x_pos - 1 > 0 and board[y_pos - 3][x_pos - 2] != " *" and board[y_pos - 3][x_pos - 2] != " X":
        count += 1
    if y_pos + 1 <= y and x_pos + 2 <= x and board[y_pos][x_pos + 1] != " *" and board[y_pos][x_pos + 1] != " X":
        count += 1
    if y_pos - 1 > 0 and x_pos + 2 <= x and board[y_pos - 2][x_pos + 1] != " *" and board[y_pos - 2][x_pos + 1] != " X":
        count += 1
    if y_pos + 1 <= y and x_pos - 2 > 0 and board[y_pos][x_pos - 3] != " *" and board[y_pos][x_pos - 3] != " X":
        count += 1
    if y_pos - 1 > 0 and x_pos - 2 > 0 and board[y_pos - 2][x_pos - 3] != " *" and board[y_pos - 2][x_pos - 3] != " X":
        count += 1
    return count


def create_board(x, y, x_pos, y_pos):
    global board
    board = [["__" for _ in range(x)] for _ in range(y)]
    board[y_pos - 1][x_pos - 1] = " X"
    check_moves(x, y, x_pos, y_pos)


def change_board(x, y, x_pos, y_pos, prev_x, prev_y):
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] != " X" and board[i][j] != " *":
                board[i][j] = "__"
    board[y_pos - 1][x_pos - 1] = " X"
    board[prev_y - 1][prev_x - 1] = " *"
    check_moves(x, y, x_pos, y_pos)

def solve_puzzle(x, y, x_pos, y_pos):
    visited = [[False for _ in range(x)] for _ in range(y)]
    moves_x = [1, 1, 2, 2, -1, -1, -2, -2]
    moves_y = [2, -2, 1, -1, 2, -2, 1, -1]

    def is_valid_move(next_x, next_y):
        return 1 <= next_x <= x and 1 <= next_y <= y and not visited[next_y - 1][next_x - 1]

    def find_solution(current_x, current_y, move_number):
        board[current_y - 1][current_x - 1] = f"{move_number:2d}"
        visited[current_y - 1][current_x - 1] = True

        if move_number == x * y:
            return True

        next_moves = [(current_x + dx, current_y + dy) for dx, dy in zip(moves_x, moves_y)]
        next_moves = [(nx, ny) for nx, ny in next_moves if is_valid_move(nx, ny)]
        next_moves.sort(key=lambda move: sum(is_valid_move(nx, ny) for nx, ny in
                                              ((move[0] + dx, move[1] + dy) for dx, dy in zip(moves_x, moves_y))))

        for next_x, next_y in next_moves:
            if find_solution(next_x, next_y, move_number + 1):
                return True

        board[current_y - 1][current_x - 1] = "__"
        visited[current_y - 1][current_x - 1] = False
        return False

    return find_solution(x_pos, y_pos, 1)


def print_solution():
    column_n = len(board[0])
    row_n = len(board)
    total_cells = column_n * row_n
    cell_size = len(str(total_cells))
    border_length = column_n * (cell_size + 1) + 3

    print(" " + "-" * border_length)
    for i, row in enumerate(board[::-1], 1):
        print(f"{len(board) - i + 1}|", end=" ")
        for cell in row:
            print(cell.rjust(cell_size), end=" ")
        print("|")
    print(" " + "-" * border_length)
    print("  ", end="")
    for i in range(1, column_n + 1):
        print(str(i).rjust(cell_size + 1, ' '), end="")
    print()


def main():
    x, y = get_dimensions()
    x_pos, y_pos = get_starting_position(x, y)
    create_board(x, y, x_pos, y_pos)
    choice = input("Do you want to try the puzzle? (y/n): ")
    while choice.lower() not in ['y', 'n']:
        print("Invalid input!")
        choice = input("Do you want to try the puzzle? (y/n): ")

    if choice.lower() == 'y':
        solution_found = solve_puzzle(x, y, x_pos, y_pos)
        if not solution_found:
            print("No solution exists!")
            return
        else:
            print("You can start solving the puzzle now!")
            squares_visited = 1
            while check_possible_moves(x, y, x_pos, y_pos) != 0:
                user_input = input("Enter your next move: ")
                invalid_move = True
                while invalid_move:
                    user_input = user_input.split()
                    if possible_moves(x, y, x_pos, y_pos, int(user_input[0]), int(user_input[1])):
                        board[y_pos - 1][x_pos - 1] = " *"
                        prev_x = x_pos
                        prev_y = y_pos
                        x_pos = int(user_input[0])
                        y_pos = int(user_input[1])
                        change_board(x, y, x_pos, y_pos, prev_x, prev_y)
                        print_board()
                        squares_visited += 1
                        invalid_move = False
                    else:
                        user_input = input("Invalid move! Enter your next move: ")
            if squares_visited == x * y:
                print("What a great tour! Congratualations!")
            else:
                print("No more possible moves!")
                print(f"Your knight visited {squares_visited} squares!")

    elif choice.lower() == 'n':
        solution_found = solve_puzzle(x, y, x_pos, y_pos)
        if not solution_found:
            print("No solution exists!")
        else:
            print()
            print("Here's the solution!")
            print_solution()


main()
