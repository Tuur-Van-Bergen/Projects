import re
from decimal import Decimal, ROUND_HALF_UP

print("Learning progress tracker")
x = True
student_mail = {}
student_list = []
student_name = {}
student_number = 10000
Python = [0, 0]
Python_enrolled = []
Python_score = []
DSA = [0, 0]
DSA_enrolled = []
DSA_score = []
Databases = [0, 0]
Databases_enrolled = []
Databases_score = []
Flask = [0, 0]
Flask_enrolled = []
Flask_score = []


def add_students():
    global student_mail, student_list, student_number
    print("Enter student credentials or 'back' to return:")
    y = True
    count = 0
    while y:
        input_string = input().split()
        lastname = " ".join(input_string[1:-1])
        if len(input_string) > 0:
            if input_string[0] == "back":
                print(f'Total {count} students were added')
                y = False
            elif len(input_string) < 3:
                print("Incorrect credentials")
            elif ("." in input_string[0] or len(input_string[0]) < 2 or re.match("^[-'].*", input_string[0])
                  or re.match(".*[-']$", input_string[0]) or re.match(".*[-'][-'].*", input_string[0])
                  or not re.match("^[a-zA-Z-']*$", input_string[0])):
                print("Incorrect first name.")
            elif ("." in lastname or len(lastname) < 2 or re.match("^[-'].*", lastname)
                  or re.match(".*[-']$", lastname) or re.match(".*[-'][-'].*", lastname)
                  or not re.match("^[a-zA-Z-' ]*$", lastname)):
                print("Incorrect last name.")
            elif not re.match("^[a-zA-Z.0-9]*[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$", input_string[-1]):
                print("Incorrect email.")
            elif input_string[-1] in student_mail.values():
                print("This email is already taken.")
            else:
                print("The student has been added")
                student_mail[student_number] = input_string[-1]
                student_list.append(f'{student_number} 0 0 0 0')
                student_name[student_number] = ' '.join(input_string[:-1])
                count += 1
                student_number += 1
        else:
            print("Incorrect credentials")


def is_numerical_and_nonnegative(value):
    try:
        num = int(value)
        if num >= 0:
            return True
        else:
            return False
    except ValueError:
        return False


def add_points():
    global Python, Python_enrolled, DSA, DSA_enrolled, Databases, Databases_enrolled, Flask, Flask_enrolled
    print('''Enter an id and points or 'back' to return:''')
    loop_not_broken = True
    while loop_not_broken:
        input_string = input().split()
        match = False
        for student in student_list:
            if student.split()[0] == input_string[0]:
                match = True
                break

        if len(input_string) > 0:
            if input_string[0] == "back":
                loop_not_broken = False
            elif len(input_string) != 5:
                print("Incorrect points format.")
            elif not match:
                print(f'No student is found for id={input_string[0]}.')
            elif (not is_numerical_and_nonnegative(input_string[1]) or not is_numerical_and_nonnegative(input_string[2])
                  or not is_numerical_and_nonnegative(input_string[3]) or not is_numerical_and_nonnegative(
                        input_string[4])):
                print("Incorrect points format.")
            else:
                for i, s in enumerate(student_list):
                    first_word = s.split()[0]
                    if first_word == input_string[0]:
                        current_score = s.split()
                        new_score = [input_string[0]]
                        for j in range(1, 5):
                            new_score.append(str(int(current_score[j]) + int(input_string[j])))
                        student_list[i] = " ".join(new_score)
                if input_string[1] != '0':
                    if int(input_string[0]) not in Python_enrolled:
                        Python_enrolled.append(int(input_string[0]))
                        Python_score.append(int(input_string[1]))
                    else:
                        Python_score[Python_enrolled.index(int(input_string[0]))] += int(input_string[1])
                    Python[0] += int(input_string[1])
                    Python[1] += 1
                if input_string[2] != '0':
                    if int(input_string[0]) not in DSA_enrolled:
                        DSA_enrolled.append(int(input_string[0]))
                        DSA_score.append(int(input_string[2]))
                    else:
                        DSA_score[DSA_enrolled.index(int(input_string[0]))] += int(input_string[2])
                    DSA[0] += int(input_string[2])
                    DSA[1] += 1
                if input_string[3] != '0':
                    if int(input_string[0]) not in Databases_enrolled:
                        Databases_enrolled.append(int(input_string[0]))
                        Databases_score.append(int(input_string[3]))
                    else:
                        Databases_score[Databases_enrolled.index(int(input_string[0]))] += int(input_string[3])
                    Databases[0] += int(input_string[3])
                    Databases[1] += 1
                if input_string[4] != '0':
                    if int(input_string[0]) not in Flask_enrolled:
                        Flask_enrolled.append(int(input_string[0]))
                        Flask_score.append(int(input_string[4]))
                    else:
                        Flask_score[Flask_enrolled.index(int(input_string[0]))] += int(input_string[4])
                    Flask[0] += int(input_string[4])
                    Flask[1] += 1
                print('Points updated.')


def list_students():
    if len(student_list) == 0:
        print('No students found.')
    else:
        print('Students:')
        for student in student_list:
            print(student.split()[0])


def find():
    global student_list
    print('''Enter an id or 'back' to return:''')
    y = True
    while y:
        id_number = input()
        student_score = []
        matched = False
        new_student_list = []
        for student in student_list:
            if student.split()[0] == id_number:
                matched = True
                student_score = student.split()
            else:
                new_student_list.append(student)
        student_list = new_student_list
        if id_number == "back":
            y = False
        elif not matched:
            print(f'No student is found for id={id_number}.')
        else:
            print(f'{id_number} points: Python={student_score[1]}; DSA={student_score[2]}; '
                  f'Databases={student_score[3]}; Flask={student_score[4]}')


def statistics_students():
    print('''Type the name of a course to see details or 'back' to quit:''')
    most_popular, least_popular = popular()
    highest_activity, lowest_activity = activity()
    easiest_course, hardest_course = difficulty_course()
    if not most_popular:
        most_popular.append('n/a')
    if not least_popular or most_popular == least_popular:
        least_popular.clear()
        least_popular.append('n/a')
    if not highest_activity:
        highest_activity.append('n/a')
    if not lowest_activity or highest_activity == lowest_activity:
        lowest_activity.clear()
        lowest_activity.append('n/a')
    if not hardest_course:
        hardest_course.append('n/a')
    if not easiest_course:
        easiest_course.append('n/a')
    print(f'Most popular: {", ".join(most_popular)}')
    print(f'Least popular: {", ".join(least_popular)}')
    print(f'Highest activity: {", ".join(highest_activity)}')
    print(f'Lowest activity: {", ".join(lowest_activity)}')
    print(f'Easiest course: {", ".join(easiest_course)}')
    print(f'Hardest course: {", ".join(hardest_course)}')
    y = True
    while y:
        course = input()
        if course.lower() == 'back':
            y = False
        elif course.lower() == 'python':
            print('Python')
            print('id\t\tpoints\tcompleted')
            sorted_python = list(reversed(sorted(Python_score)))
            enrolled_duplicate = Python_enrolled[:]
            for i in range(len(Python_score)):
                if Python_enrolled[Python_score.index(sorted_python[0])] in enrolled_duplicate:
                    a = Python_enrolled[Python_score.index(sorted_python[0])]
                    print(f'{a}\t'
                          f'{sorted_python[0]}\t\t'
                          f'''{Decimal(str(sorted_python[0] / 600 * 100)).quantize(Decimal('1.0'),
                                                                                   rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_python.remove(sorted_python[0])
                elif Python_enrolled[Python_score.index(sorted_python[0],
                                                        Python_score.index(
                                                            sorted_python[0]) + 1)] in enrolled_duplicate:
                    a = Python_enrolled[Python_score.index(sorted_python[0],
                                                           Python_score.index(sorted_python[0]) + 1)]
                    print(f'{a}\t'
                          f'{sorted_python[0]}\t\t'
                          f'''{Decimal(str(sorted_python[0] / 600 * 100)).quantize(Decimal('1.0'),
                                                                                   rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_python.remove(sorted_python[0])
                else:
                    a = Python_enrolled[Python_score.index(sorted_python[0],
                                                           Python_score.index(sorted_python[0],
                                                                              Python_score.index(sorted_python[0]
                                                                                                 ) + 1) + 1)]
                    print(f'{a}\t'
                          f'{sorted_python[0]}\t\t'
                          f'''{Decimal(str(sorted_python[0] / 600 * 100)).quantize(Decimal('1.0'),
                                                                                   rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_python.remove(sorted_python[0])

        elif course.lower() == 'dsa':
            print('DSA')
            print('id\t\tpoints\tcompleted')
            sorted_dsa = list(reversed(sorted(DSA_score)))
            enrolled_duplicate = DSA_enrolled[:]
            for i in range(len(DSA_score)):
                if DSA_enrolled[DSA_score.index(sorted_dsa[0])] in enrolled_duplicate:
                    a = DSA_enrolled[DSA_score.index(sorted_dsa[0])]
                    print(f'{a}\t'
                          f'{sorted_dsa[0]}\t\t'
                          f'''{Decimal(str(sorted_dsa[0] / 400 * 100)).quantize(Decimal('1.0'),
                                                                                rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_dsa.remove(sorted_dsa[0])
                elif DSA_enrolled[DSA_score.index(sorted_dsa[0],
                                                  DSA_score.index(
                                                      sorted_dsa[0]) + 1)] in enrolled_duplicate:
                    a = DSA_enrolled[DSA_score.index(sorted_dsa[0],
                                                     DSA_score.index(sorted_dsa[0]) + 1)]
                    print(f'{a}\t'
                          f'{sorted_dsa[0]}\t\t'
                          f'''{Decimal(str(sorted_dsa[0] / 400 * 100)).quantize(Decimal('1.0'),
                                                                                rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_dsa.remove(sorted_dsa[0])
                else:
                    a = DSA_enrolled[DSA_score.index(sorted_dsa[0],
                                                     DSA_score.index(sorted_dsa[0],
                                                                     DSA_score.index(sorted_dsa[0]
                                                                                     ) + 1) + 1)]
                    print(f'{a}\t'
                          f'{sorted_dsa[0]}\t\t'
                          f'''{Decimal(str(sorted_dsa[0] / 400 * 100)).quantize(Decimal('1.0'),
                                                                                rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_dsa.remove(sorted_dsa[0])
        elif course.lower() == 'databases':
            print('Databases')
            print('id\t\tpoints\tcompleted')
            sorted_databases = list(reversed(sorted(Databases_score)))
            enrolled_duplicate = Databases_enrolled[:]
            for i in range(len(Databases_score)):
                if Databases_enrolled[Databases_score.index(sorted_databases[0])] in enrolled_duplicate:
                    a = Databases_enrolled[Databases_score.index(sorted_databases[0])]
                    print(f'{a}\t'
                          f'{sorted_databases[0]}\t\t'
                          f'''{Decimal(str(sorted_databases[0] / 480 * 100)).quantize(Decimal('1.0'),
                                                                                      rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_databases.remove(sorted_databases[0])
                elif Databases_enrolled[Databases_score.index(sorted_databases[0],
                                                              Databases_score.index(
                                                                  sorted_databases[0]) + 1)] in enrolled_duplicate:
                    a = Databases_enrolled[Databases_score.index(sorted_databases[0],
                                                                 Databases_score.index(sorted_databases[0]) + 1)]
                    print(f'{a}\t'
                          f'{sorted_databases[0]}\t\t'
                          f'''{Decimal(str(sorted_databases[0] / 480 * 100)).quantize(Decimal('1.0'),
                                                                                      rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_databases.remove(sorted_databases[0])
                else:
                    a = Databases_enrolled[Databases_score.index(sorted_databases[0],
                                                                 Databases_score.index(sorted_databases[0],
                                                                                       Databases_score.index(
                                                                                           sorted_databases[0]
                                                                                       ) + 1) + 1)]
                    print(f'{a}\t'
                          f'{sorted_databases[0]}\t\t'
                          f'''{Decimal(str(sorted_databases[0] / 480 * 100)).quantize(Decimal('1.0'),
                                                                                      rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_databases.remove(sorted_databases[0])
        elif course.lower() == 'flask':
            print('Flask')
            print('id\t\tpoints\tcompleted')
            sorted_flask = list(reversed(sorted(Flask_score)))
            enrolled_duplicate = Flask_enrolled[:]
            for i in range(len(Flask_score)):
                if Flask_enrolled[Flask_score.index(sorted_flask[0])] in enrolled_duplicate:
                    a = Flask_enrolled[Flask_score.index(sorted_flask[0])]
                    print(f'{a}\t'
                          f'{sorted_flask[0]}\t\t'
                          f'''{Decimal(str(sorted_flask[0] / 550 * 100)).quantize(Decimal('1.0'),
                                                                                  rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_flask.remove(sorted_flask[0])
                elif Flask_enrolled[Flask_score.index(sorted_flask[0],
                                                      Flask_score.index(
                                                          sorted_flask[0]) + 1)] in enrolled_duplicate:
                    a = Flask_enrolled[Flask_score.index(sorted_flask[0],
                                                         Flask_score.index(sorted_flask[0]) + 1)]
                    print(f'{a}\t'
                          f'{sorted_flask[0]}\t\t'
                          f'''{Decimal(str(sorted_flask[0] / 550 * 100)).quantize(Decimal('1.0'),
                                                                                  rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_flask.remove(sorted_flask[0])
                else:
                    a = Flask_enrolled[Flask_score.index(sorted_flask[0],
                                                         Flask_score.index(sorted_flask[0],
                                                                           Flask_score.index(sorted_flask[0]
                                                                                             ) + 1) + 1)]
                    print(f'{a}\t'
                          f'{sorted_flask[0]}\t\t'
                          f'''{Decimal(str(sorted_flask[0] / 550 * 100)).quantize(Decimal('1.0'),
                                                                                  rounding=ROUND_HALF_UP)}%''')
                    enrolled_duplicate.remove(a)
                    sorted_flask.remove(sorted_flask[0])
        else:
            print('Unknown course.')


def popular():
    global Python_enrolled, DSA_enrolled, Databases_enrolled, Flask_enrolled
    most_popular = []
    least_popular = []
    len_enrolled = [len(Python_enrolled), len(DSA_enrolled), len(Databases_enrolled), len(Flask_enrolled)]
    if len(Python_enrolled) == max(len_enrolled) and max(len_enrolled) != 0:
        most_popular.append('Python')
    if len(Python_enrolled) == min(len_enrolled) and min(len_enrolled) != 0:
        least_popular.append('Python')
    if len(DSA_enrolled) == max(len_enrolled) and max(len_enrolled) != 0:
        most_popular.append('DSA')
    if len(DSA_enrolled) == min(len_enrolled) and min(len_enrolled) != 0:
        least_popular.append('DSA')
    if len(Databases_enrolled) == max(len_enrolled) and max(len_enrolled) != 0:
        most_popular.append('Databases')
    if len(Databases_enrolled) == min(len_enrolled) and min(len_enrolled) != 0:
        least_popular.append('Databases')
    if len(Flask_enrolled) == max(len_enrolled) and max(len_enrolled) != 0:
        most_popular.append('Flask')
    if len(Flask_enrolled) == min(len_enrolled) and min(len_enrolled) != 0:
        least_popular.append('Flask')
    return most_popular, least_popular


def activity():
    global Python, DSA, Databases, Flask
    highest_activity = []
    lowest_activity = []
    len_activity = [Python[1], DSA[1], Databases[1], Flask[1]]
    if Python[1] == max(len_activity) and max(len_activity) != 0:
        highest_activity.append('Python')
    if Python[1] == min(len_activity) and min(len_activity) != 0:
        lowest_activity.append('Python')
    if DSA[1] == max(len_activity) and max(len_activity) != 0:
        highest_activity.append('DSA')
    if DSA[1] == min(len_activity) and min(len_activity) != 0:
        lowest_activity.append('DSA')
    if Databases[1] == max(len_activity) and max(len_activity) != 0:
        highest_activity.append('Databases')
    if Databases[1] == min(len_activity) and min(len_activity) != 0:
        lowest_activity.append('Databases')
    if Flask[1] == max(len_activity) and max(len_activity) != 0:
        highest_activity.append('Flask')
    if Flask[1] == min(len_activity) and min(len_activity) != 0:
        lowest_activity.append('Flask')
    return highest_activity, lowest_activity


def difficulty_course():
    global Python, DSA, Databases, Flask
    easiest_course = []
    hardest_course = []
    dif_course = []
    if Python[1] != 0:
        dif_course.append(Python[0] / Python[1])
    if DSA[1] != 0:
        dif_course.append(DSA[0] / DSA[1])
    if Databases[1] != 0:
        dif_course.append(Databases[0] / Databases[1])
    if Flask[1] != 0:
        dif_course.append(Flask[0] / Flask[1])

    if Python[1] != 0:
        if Python[0] / Python[1] == max(dif_course):
            hardest_course.append('Python')
        if Python[0] / Python[1] == min(dif_course):
            easiest_course.append('Python')
    if DSA[1] != 0:
        if DSA[0] / DSA[1] == max(dif_course):
            hardest_course.append('DSA')
        if DSA[0] / DSA[1] == min(dif_course):
            easiest_course.append('DSA')
    if Databases[1] != 0:
        if Databases[0] / Databases[1] == max(dif_course):
            hardest_course.append('Databases')
        if Databases[0] / Databases[1] == min(dif_course):
            easiest_course.append('Databases')
    if Flask[1] != 0:
        if Flask[0] / Flask[1] == max(dif_course):
            hardest_course.append('Flask')
        if Flask[0] / Flask[1] == min(dif_course):
            easiest_course.append('Flask')
    return hardest_course, easiest_course


def notify():
    added = []
    for i in range(len(Python_score)):
        if Python_score[i] == 600:
            print(f'To: {student_mail[Python_enrolled[i]]}')
            print(f'Re: Your Learning Progress')
            print(f'Hello, {student_name[Python_enrolled[i]]}! You have accomplished our Python course!')
            Python_score[i] += 1
            if student_name[Python_enrolled[i]] not in added:
                added.append(student_name[Python_enrolled[i]])
    for i in range(len(DSA_score)):
        if DSA_score[i] == 400:
            print(f'To: {student_mail[DSA_enrolled[i]]}')
            print(f'Re: Your Learning Progress')
            print(f'Hello, {student_name[DSA_enrolled[i]]}! You have accomplished our DSA course!')
            DSA_score[i] += 1
            if student_name[DSA_enrolled[i]] not in added:
                added.append(student_name[DSA_enrolled[i]])
    for i in range(len(Databases_score)):
        if Databases_score[i] == 480:
            print(f'To: {student_mail[Databases_enrolled[i]]}')
            print(f'Re: Your Learning Progress')
            print(f'Hello, {student_name[Databases_enrolled[i]]}! You have accomplished our Databases course!')
            Databases_score[i] += 1
            if student_name[Databases_enrolled[i]] not in added:
                added.append(student_name[Databases_enrolled[i]])
    for i in range(len(Flask_score)):
        if Flask_score[i] == 550:
            print(f'To: {student_mail[Flask_enrolled[i]]}')
            print(f'Re: Your Learning Progress')
            print(f'Hello, {student_name[Flask_enrolled[i]]}! You have accomplished our Flask course!')
            Flask_score[i] += 1
            if student_name[Flask_enrolled[i]] not in added:
                added.append(student_name[Flask_enrolled[i]])
    print(f'Total {len(added)} students have been notified.')


while x:
    command = input()
    if len(command.strip()) == 0:
        print("No input.")
    elif command == "exit":
        print("Bye!")
        x = False
    elif command == "add students":
        add_students()
    elif command == 'back':
        print("Enter 'exit' to exit the program.")
    elif command == 'list':
        list_students()
    elif command == 'add points':
        add_points()
    elif command == 'find':
        find()
    elif command == 'statistics':
        statistics_students()
    elif command == 'notify':
        notify()
    else:
        print("Error: unknown command!")
