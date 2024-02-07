const input = require('sync-input');

console.log(`Welcome to Currency Converter!
1 USD equals 1 USD
1 USD equals 113.5 JPY
1 USD equals 0.89 EUR
1 USD equals 74.36 RUB
1 USD equals 0.75 GBP`);

const conversionRates = {
  JPY: 113.5,
  EUR: 0.89,
  RUB: 74.36,
  USD: 1,
  GBP: 0.75
};

function switchTo(to) {
  let rate;
  switch (to) {
    case "USD":
      rate = 1;
      break;
    case "JPY":
      rate = 113.5;
      break;
    case "EUR":
      rate = 0.89;
      break;
    case "RUB":
      rate = 74.36;
      break;
    case "GBP":
      rate = 0.75;
      break;
  }
  return rate;
}

function runExchange() {
  console.log("What do you want to convert?");
  let from = input("From: ").trim().toUpperCase();
  if (!(from in conversionRates)) {
    console.log("Unknown currency");
    alertUser();
  }

  let to = input("To: ").toUpperCase();
  if (!(to in conversionRates)) {
    console.log("Unknown currency");
    alertUser();
  }

  let amount = Number(input("Amount: "));
  if (isNaN(amount)) {
    console.log("The amount has to be a number")
    return;
  } else if (!(amount >= 1)) {
    console.log("The amount cannot be less than 1");
    return;
  }

  console.log(`Result: ${amount} ${from} equals ${(amount / switchTo(from) * switchTo(to)).toFixed(4)} ${to}`);
  alertUser();
}

function alertUser() {
  let answer = Number(input("What do you want to do?\n1-Convert currencies 2-Exit program\n"));
  if (answer === 1) {
    runExchange();
    alertUser(); // Recursively call alertUser() after runExchange()
  } else if (answer === 2) {
    console.log("Have a nice day!");
    return;
  } else {
    console.log("Unknown input");
    alertUser(); // Recursively call alertUser() for unknown or invalid input
  }
}

alertUser();
