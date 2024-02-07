msg_0 = "Enter an equation"
msg_1 = "Do you even know what numbers are? Stay focused!"
msg_2 = "Yes ... an interesting math operation. You've slept through all classes, haven't you?"
msg_3 = "Yeah... division by zero. Smart move..."
msg_4 = "Do you want to store the result? (y / n):"
msg_5 = "Do you want to continue calculations? (y / n):"
msg_6 = " ... lazy"
msg_7 = " ... very lazy"
msg_8 = " ... very, very lazy"
msg_9 = "You are"
msg_10 = "Are you sure? It is only one digit! (y / n)"
msg_11 = "Don't be silly! It's just one number! Add to the memory? (y / n)"
msg_12 = "Last chance! Do you really want to embarrass yourself? (y / n)"

def is_one_digit(v):
    if v.is_integer() and v > -10 and v < 10:
        return True
    else:
        return False


def check(v1, v2, v3):
    
    msg = ""
    if is_one_digit(v1) and is_one_digit(v2):
        msg = msg + msg_6
    if (v1 == 1 or v2 == 1) and v3 == "*":
        msg = msg + msg_7
    if (v1 == 0 or v2 == 0) and (v3 == "*" or v3 == "+" or v3 == "-"):
        msg = msg + msg_8
    if msg != "":
        msg = msg_9 + msg
        return msg
    return None


def convert_to_number(s):
    return float(s)


memory = 0


def calculate():
    global memory
    while True:
        print(msg_0)
        string = input()
        string = string.replace('M', str(memory))
        string_split = string.split()

        is_number = False
        is_sign = True
        is_division_by_zero = False
        

        if "." in string_split[0] or "." in string_split[2]:
            try:
                float(string_split[0])
                float(string_split[2])
                is_number = True
            except:
                print(msg_1)
        elif "." not in string_split[0] and "." not in string_split[2]:
            try:
                int(string_split[0])
                int(string_split[2])
                is_number = True
            except:
                print(msg_1)

        if "+" not in string_split[1] and "-" not in string_split[1] and "*" not in string_split[1] and "/" not in string_split[1]:
            print(msg_2)
            is_sign = False

        msg = check(convert_to_number(string_split[0]), convert_to_number(string_split[2]), string_split[1])
        if msg is not None:
            print(msg)

        if string_split[1] == "/" and string_split[2] == '0':
            print(msg_3)
            is_division_by_zero = True

        
            

        if is_sign and is_number and not is_division_by_zero:
            break

    if string_split[1] == "+":
        result = float(string_split[0]) + float(string_split[2])
    elif string_split[1] == "-":
        result = float(string_split[0]) - float(string_split[2])
    elif string_split[1] == "*":
        result = float(string_split[0]) * float(string_split[2])
    elif string_split[1] == "/":
        result = float(string_split[0]) / float(string_split[2])
    print(result)
    print(msg_4)
    if input() == "y":
        if isinstance(result, float) and result.is_integer() and is_one_digit(result):
            print(msg_10)
            if input() == "y":
                print(msg_11)
                if input() == "y":
                    print(msg_12)
                    if input() == "y":
                        memory = int(result)
        elif isinstance(result, float) and result.is_integer():
            memory = int(result)
        else:
            memory = result

    print(msg_5)
    if input() == "y":
        calculate()
    else:
        return True


calculate()
