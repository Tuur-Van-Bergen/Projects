import random

print("Enter the number of friends joining (including you):")
number_of_attendants = int(input())
list_of_attendants = {}
if number_of_attendants <= 0:
    print("No one is joining for the party")
else : 
    x = 0
    print("Enter the name of every friend (including you), each on a new line:")
    while x != number_of_attendants:
        attendant = input()
        list_of_attendants[attendant] = 0
        x += 1
    print("Enter the total bill value:")
    bill = int(input())
    print("Do you want to use the \"Who is lucky?\" feature? Write Yes/No:")
    lucky_method = input()
    if lucky_method == "Yes":
        lucky_person = random.choice(list(list_of_attendants.keys()))
        print(f"{lucky_person} is the lucky one!")
        cost_per_person = round(bill / (number_of_attendants - 1), 2)
        for attendant in list_of_attendants:
            if attendant == lucky_person:
                list_of_attendants[attendant] = 0
            else:
                list_of_attendants[attendant] = cost_per_person
        print(list_of_attendants)
        
    else:
        cost_per_person = round(bill / number_of_attendants, 2)
        for attendant in list_of_attendants:
            list_of_attendants[attendant] = cost_per_person
        print("No one is going to be lucky")
        print(list_of_attendants)
    
        
