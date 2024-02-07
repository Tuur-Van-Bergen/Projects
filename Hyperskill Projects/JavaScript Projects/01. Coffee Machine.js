const input = require('sync-input')
let water = 400;
let milk = 540;
let coffeeBeans = 120;
let cups = 9;
let money = 550;
while(true){
  action = input("Write action (buy, fill, take, remaining, exit): ").toLowerCase();
  if (action === "buy") {
    let choice = input(`What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n`);
    if (choice === "1") {
      if (water < 250) {
        console.log("Sorry, not enough water!")
      } else if (coffeeBeans < 16) {
        console.log("Sorry, not enough coffee beans!")
      } else if (cups < 1) {
        console.log("Sorry, not enough cups!")
      } else {
        console.log(`I have enough resources, making you a coffee!\n`)
        water -= 250;
        coffeeBeans -= 16;
        cups -= 1
        money += 4;
      }
    } else if (choice === "2") {
      if (water < 350) {
        console.log("Sorry, not enough water!")
      } else if (milk < 75) {
        console.log("Sorry, not enough milk!")
      } else if (coffeeBeans < 20) {
        console.log("Sorry, not enough coffee beans!")
      } else if (cups < 1) {
        console.log("Sorry, not enough cups!")
      } else {
        console.log(`I have enough resources, making you a coffee!\n`)
        water -= 350;
        milk -= 75;
        coffeeBeans -= 20;
        cups -= 1
        money += 7;
      }
    } else if (choice === "3") {
      if (water < 200) {
        console.log("Sorry, not enough water!")
      } else if (milk < 100) {
        console.log("Sorry, not enough milk!")
      } else if (coffeeBeans < 12) {
        console.log("Sorry, not enough coffee beans!")
      } else if (cups < 1) {
        console.log("Sorry, not enough cups!")
      } else {
        console.log(`I have enough resources, making you a coffee!\n`)
        water -= 200;
        milk -= 100;
        coffeeBeans -= 12;
        cups -= 1
        money += 6;
      }
    } else if (choice === "back") {
      continue;
    }
  } else if (action === "fill") {
    let waterAdded = Number(input(`Write how many ml of water you want to add: \n`));
    water += waterAdded
    let milkAdded = Number(input(`Write how many ml of milk you want to add:\n`));
    milk += milkAdded;
    let coffeeBeansAdded = Number(input(`Write how many grams of coffee beans you want to add: \n`));
    coffeeBeans += coffeeBeansAdded;
    let cupsAdded = Number(input(`Write how many disposable cups you want to add: \n`));
    cups += cupsAdded;
  } else if (action === "take") {
    console.log(`I gave you $${money}\n`);
    money = 0;
  } else if (action === "remaining") {
    console.log(`
The coffee machine has:
${water} ml of water
${milk} ml of milk
${coffeeBeans} g of coffee beans
${cups} disposable cups
$${money} of money
`);
  } else if (action === "exit") {
    break;
  }
}


