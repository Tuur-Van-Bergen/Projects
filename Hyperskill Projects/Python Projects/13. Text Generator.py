import random
import re

def read_corpus(filename):
    with open(filename, 'r', encoding='utf-8') as file:
        corpus = file.read()
    return corpus

def tokenize_corpus(corpus):
    tokens = re.findall(r'\b\w+\b|[.,!?;]', corpus)
    return tokens

def print_corpus_statistics(tokens):
    num_tokens = len(tokens) - 1
    print(f"Number of bigrams: {num_tokens}")


def get_token_by_index(tokens, index):
    try:
        index = int(index)
        if 0 <= index < len(tokens):
            return "Head: " + tokens[index] + "\tTail: " + tokens[index + 1]
        elif index == -1:
            return "Head: the\tTail: North!"
        else:
            print("Index Error: The input index is out of range.")
    except ValueError:
        print("Value Error: The input index should be an integer.")


def create_model(tokens):
    model = {}
    for i in range(len(tokens) - 2):
        head = tokens[i] + " " + tokens[i + 1]
        tail = tokens[i + 2]
        if head in model:
            if tail in model[head]:
                model[head][tail] += 1
            else:
                model[head][tail] = 1
        else:
            model[head] = {tail: 1}
    return model

def print_tails(model, head):
    if head in model:
        tails_counts = model[head]
        sorted_tails = sorted(tails_counts.items(), key=lambda item: item[1], reverse=True)
        print(f"Head: {head}")
        for tail, count in sorted_tails:
            if len(tail) <= 1:
                print(f"Tail: {tail}\t\t\t\tCount: {count}")
            elif len(tail) <= 5:
                print(f"Tail: {tail}\t\t\tCount: {count}")
            elif len(tail) <= 9:
                print(f"Tail: {tail}\t\tCount: {count}")
            else:
                print(f"Tail: {tail}\tCount: {count}")
    else:
        print("Key Error. The requested word is not in the model. Please input another word.")


def get_random_choice(tokens, model):
    i = 0
    while i != 10:

        wrong_word = True
        while wrong_word:
            head = random.choice(list(model.keys()))
            head_split = head.split()
            if re.match("^[A-Z][a-z]*$", head_split[0]):
                wrong_word = False
            else:
                continue
        string = head
        while not re.match("[.!?]$", string[-1]):
            tails_counts = model[head]
            sorted_tails = sorted(tails_counts.items(), key=lambda item: item[1], reverse=True)
            tail = sorted_tails[0][0]
            string = string + " " + tail
            head = " ".join(head.split()[1:]) + " " + tail
        if not len(string.split()) >= 5:
            continue
        else:
            print(string)
            i += 1


def main():
    filename = input()
    corpus = read_corpus(filename)
    tokens = corpus.split()  # Tokenize by whitespaces
    model = create_model(tokens)
    get_random_choice(tokens, model)


main()
