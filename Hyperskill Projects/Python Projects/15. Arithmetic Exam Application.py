import random

operations = ["+", "-", "*"]
correct = 0
i = 0
y = 0
while y == 0:
    print("Which level do you want? Enter a number:")
    print("1 - simple operations with numbers 2-9")
    print("2 - integral squares of 11-29")
    level = input()
    try:
        if int(level) == 1:
            while i != 5:
                int1 = random.randint(2,9)
                int2 = random.randint(2,9)
                operation = operations[random.randint(0,2)]
                print(str(int1) + " " + operation + " " + str(int2))
                if operation == "+":
                    result = int1 + int2
                elif operation == "-":
                    result = int1 - int2
                elif operation == "*":
                    result = int1 * int2
            
                x = 0
                while x != 1:
                    user_input = input()
                    try:
                        if int(user_input) == result:
                            print("Right!")
                            correct += 1
                            i += 1
                            x += 1
                        elif int(user_input) != result:
                            print("Wrong!")
                            i += 1
                            x += 1
                    except:
                        print("Incorrect format.")
            
            y += 1
        elif int(level) == 2:
            while i != 5:
                int1 = random.randint(11,29)
                print(int1)
                result = int1 * int1
                x = 0
                while x != 1:
                    user_input = input()
                    try:
                        if int(user_input) == result:
                            print("Right!")
                            correct += 1
                            i += 1
                            x += 1
                        elif int(user_input) != result:
                            print("Wrong!")
                            i += 1
                            x += 1
                    except:
                        print("Incorrect format.")
                        
            
            y += 1
                
    except:
        print("Incorrect format.")

print("Your mark is " + str(correct) + "/5.Would you like to save the result? Enter yes or no.")
save_file = input()
if save_file == "yes" or save_file == "YES" or save_file == "y" or save_file == "Yes":
    print("What is your name?")
    name = input()
    
    if int(level) == 1:
        with open('results.txt', 'a', encoding='utf-8') as results:
            results.write(f'{name}: {correct}/5 in level 1 (simple operations with numbers 2-9).\n')
            results.close()
    elif int(level) == 2:
        with open('results.txt', 'a', encoding='utf-8') as results:
            results.write(f'{name}: {correct}/5 in level 2 (integral squares of 11-29).\n')
            results.close()
    print('The results are saved in "results.txt".')
    exit()
else:
    exit()
