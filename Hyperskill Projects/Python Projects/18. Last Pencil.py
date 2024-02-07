import random

print("How many pencils would you like to use:")

while True:
    try:
        pencils_amount = int(input())
        if pencils_amount <= 0:
            print("The number of pencils should be positive")
        else: break
        
    except:
        print("The number of pencils should be numeric")

print("Who will be the first (John, Jack):")

while True:
    name = input()
    if name != "Jack" and name != "John":
        print("Choose between 'John' and 'Jack'")
    else:
        break

while pencils_amount != 0:
    pencils = []
    x = 0
    is_valid_number = False
    is_valid_number_taken = False
    
    while x != pencils_amount:
        pencils.append("|")
        x += 1
        
    print("".join(pencils))
    
    
    print(f"{name}'s turn:")
    if name == "Jack":
        if pencils_amount == 1:
            pencils_amount -= 1
            print("1")
        elif pencils_amount % 4 == 0:
            pencils_amount -= 3
            print("3")
        elif pencils_amount % 4 == 1:
            chosen = random.randint(1,3)
            pencils_amount -= chosen
            print(chosen)
        elif pencils_amount % 4 == 2:
            pencils_amount -= 1
            print("1")
        elif pencils_amount % 4 == 3:
            pencils_amount -= 2
            print("2")
        
    else:
        while not is_valid_number:
            pencils_taken = input()
            if pencils_taken in ("1", "2", "3"):
                pencils_taken = int(pencils_taken)
                is_valid_number = True
            else:
                print("Possible values: '1', '2' or '3'")
    
        while not is_valid_number_taken:
            
            if pencils_amount >= pencils_taken:
                pencils_amount -= pencils_taken
                is_valid_number_taken = True
            else:
                print("Too many pencils were taken")
                pencils_taken = int(input())

    if name == "Jack":
        name = "John"
    else:
        name = "Jack"
        
    if pencils_amount == 0:
        print(f"{name} won!")
        break

