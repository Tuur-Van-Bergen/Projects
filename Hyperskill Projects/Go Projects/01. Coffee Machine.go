package main

import "fmt"
func remaining(water, milk, coffee, disposablecups, money int) {
    fmt.Printf("The coffee machine has:\n")
    fmt.Printf("%d ml of water\n", water)
    fmt.Printf("%d ml of milk\n", milk)
    fmt.Printf("%d g of coffee beans\n", coffee)
    fmt.Printf("%d disposable cups\n", disposablecups)
    fmt.Printf("$%d of money\n", money)
}

func buy(water, milk, coffee, disposablecups, money int) (int, int, int, int, int) {
    var choice string
    fmt.Print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    fmt.Scanln(&choice)
    if choice == "1" {
        if water >= 250 && coffee >= 16 && disposablecups >= 1 {
            water -= 250
            milk -= 0
            coffee -= 16
            disposablecups -= 1
            money += 4
        } else {
            if water < 250 {
                fmt.Println("Sorry, not enough water!")
            } else if coffee < 16 {
                fmt.Println("Sorry, not enough coffee beans!")
            } else {
                fmt.Println("Sorry, not enough disposable cups!")
            }
        }
    } else if choice == "2" {
        if water >= 350 && milk >= 75 && coffee >= 20 && disposablecups >= 1 {
            water -= 350
            milk -= 75
            coffee -= 20
            disposablecups -= 1
            money += 7
        } else {
            if water < 350 {
                fmt.Println("Sorry, not enough water!")
            } else if milk < 75 {
                fmt.Println("Sorry, not enough milk!")
            } else if coffee < 20 {
                fmt.Println("Sorry, not enough coffee beans!")
            } else {
                fmt.Println("Sorry, not enough disposable cups!")
            }
        }
    } else if choice == "3" {
        if water >= 200 && milk >= 100 && coffee >= 12 && disposablecups >= 1 {
            water -= 200
            milk -= 100
            coffee -= 12
            disposablecups -= 1
            money += 6
        } else {
            if water < 200 {
                fmt.Println("Sorry, not enough water!")
            } else if milk < 100 {
                fmt.Println("Sorry, not enough milk!")
            } else if coffee < 12 {
                fmt.Println("Sorry, not enough coffee beans!")
            } else {
                fmt.Println("Sorry, not enough disposable cups!")
            }
        }
    }
    return water, milk, coffee, disposablecups, money
}

func fill(water, milk, coffee, disposablecups int) (int, int, int, int) {
    var temp int
    fmt.Println("Write how many ml of water you want to add:")
    fmt.Scanln(&temp)
    water += temp
    fmt.Println("Write how many ml of milk you want to add:")
    fmt.Scanln(&temp)
    milk += temp
    fmt.Println("Write how many grams of coffee beans you want to add:")
    fmt.Scanln(&temp)
    coffee += temp
    fmt.Println("Write how many disposable cups you want to add:")
    fmt.Scanln(&temp)
    disposablecups += temp
    return water, milk, coffee, disposablecups
}

func menu(water, milk, coffee, disposablecups, money int) {
    var action string
    fmt.Println()
    fmt.Println("Write action (buy, fill, take, remaining, exit):")
    fmt.Scanln(&action)
    if action == "buy" {
        water, milk, coffee, disposablecups, money = buy(water, milk, coffee, disposablecups, money)
        fmt.Println()
        menu(water, milk, coffee, disposablecups, money)
    } else if action == "fill" {
        water, milk, coffee, disposablecups = fill(water, milk, coffee, disposablecups)
        fmt.Println()
        menu(water, milk, coffee, disposablecups, money)
    } else if action == "take" {
        fmt.Printf("I gave you $%d\n", money)
        fmt.Println()
        money = 0
        menu(water, milk, coffee, disposablecups, money)
    } else if action == "remaining" {
        remaining(water, milk, coffee, disposablecups, money)
        fmt.Println()
        menu(water, milk, coffee, disposablecups, money)
    }
}

func main() {
    var water, milk, coffee, disposablecups, money int
    water = 400
    milk = 540
    coffee = 120
    disposablecups = 9
    money = 550
    menu(water, milk, coffee, disposablecups, money)
}
