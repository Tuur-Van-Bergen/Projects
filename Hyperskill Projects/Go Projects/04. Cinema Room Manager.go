package main

import (
	"fmt"
)

var ticketsSold = 0
var currentIncome = 0

func fillSeatsWithS(rows, seatsAtEachRow int, seats [][]string) {
	for i := 0; rows > i; i++ {
		for j := 0; j < seatsAtEachRow; j++ {
			seats[i][j] = "S"
		}
	}
}

func printSeats(rows int, seatsAtEachRow int, seats [][]string) {
	fmt.Println("Cinema:")
	for i := 0; i < rows; i++ {
		if i == 0 {
			fmt.Print(" ")
			for j := 1; j <= seatsAtEachRow; j++ {
				fmt.Printf("%2d", j)
			}
			fmt.Print("\n")
		}
		fmt.Printf("%d", i+1)
		for j := 0; j < seatsAtEachRow; j++ {
			fmt.Printf("%2s", seats[i][j])
		}
		fmt.Println()
	}
}

func boughtSeatWithB(rowNumber, seatNumberInRow int, seats [][]string) [][]string {
	seats[rowNumber-1][seatNumberInRow-1] = "B"
	ticketsSold += 1
	return seats
}

func takeNumberOfRowsAndSeats() (int, int) {
	var rows, seatsAtEachRow int
	fmt.Println("Enter the number of rows:")
	fmt.Scanln(&rows)
	fmt.Println("Enter the number of seats in each row:")
	fmt.Scanln(&seatsAtEachRow)
	return rows, seatsAtEachRow
}

func buyTicket() (int, int) {
	var rowNumber, seatNumberInRow int
	fmt.Println("\nEnter a row number:")
	fmt.Scan(&rowNumber)
	fmt.Println("Enter a seat number in that row:")
	fmt.Scan(&seatNumberInRow)

	return rowNumber, seatNumberInRow
}

func totalIncome(rows, seatsAtEachRow int) int {
	totalNumberOfSeats := rows * seatsAtEachRow
	var totalIncome int
	var ticketPrice int
	if totalNumberOfSeats <= 60 {
		ticketPrice = 10
		totalIncome = ticketPrice * totalNumberOfSeats
	} else {
		totalIncome = (rows/2)*10*seatsAtEachRow + 8*(totalNumberOfSeats-(rows/2*seatsAtEachRow))

	}
	return totalIncome
}

func calculateTicketPrice(rows, seatsAtEachRow, rowNumber, seatNumberInRow int) int {
	var ticketPrice int
	if rows*seatsAtEachRow <= 60 {
		ticketPrice = 10
		currentIncome += 10
	} else {
		if rowNumber <= rows/2 {
			ticketPrice = 10
			currentIncome += 10
		} else {
			ticketPrice = 8
			currentIncome += 8
		}
	}
	return ticketPrice
}

func statistics(rows, seatsAtEachRow int) {
	fmt.Printf("\nNumber of purchased tickets: %d\n", ticketsSold)
	fmt.Printf("Percentage: %.2f%%\n", float64(ticketsSold)/(float64(rows)*float64(seatsAtEachRow))*100)
	fmt.Printf("Current income: $%d\n", currentIncome)
	fmt.Printf("Total income: $%d\n", totalIncome(rows, seatsAtEachRow))
}

func main() {
	var rows, seatsAtEachRow = takeNumberOfRowsAndSeats()
	seats := make([][]string, rows)
	for i := range seats {
		seats[i] = make([]string, seatsAtEachRow)
	}
	fillSeatsWithS(rows, seatsAtEachRow, seats)
	for {
		fmt.Print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n")
		var answer int
		fmt.Scan(&answer)
		if answer == 1 {
			printSeats(rows, seatsAtEachRow, seats)
		} else if answer == 2 {
		Innerloop:
			for {
				var rowNumber, seatNumberInRow = buyTicket()

				if rowNumber > rows || seatNumberInRow > seatsAtEachRow {
					fmt.Println("\nWrong input!")
				} else if seats[rowNumber-1][seatNumberInRow-1] != "B" {
					ticketPrice := calculateTicketPrice(rows, seatsAtEachRow, rowNumber, seatNumberInRow)
					fmt.Printf("\nTicket price: $%d\n", ticketPrice)
					seats = boughtSeatWithB(rowNumber, seatNumberInRow, seats)
					break Innerloop
				} else if seats[rowNumber-1][seatNumberInRow-1] == "B" {
					fmt.Println("\nThat ticket has already been purchased!")
				}
			}

		} else if answer == 3 {
			statistics(rows, seatsAtEachRow)
		} else if answer == 0 {
			return
		}
	}

}
