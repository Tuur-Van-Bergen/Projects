const input = require('sync-input');
let wins = 0;
let loses = 0;
let words = ["python", "java", "swift", "javascript"];
let wordsCensored = ["------", "----", "-----", "----------"];
console.log(`H A N G M A N`);
while (true) {
  let action = input('Type "play" to play the game, "results" to show the scoreboard, and "exit" to quit:');
  if (action === "play") {
    let randomInt = Math.floor(Math.random() * words.length);
    let guessedWord = wordsCensored[randomInt];
    let guessedLetters = [];
    let regex = /[a-z]/;
    let i = 0;
    while (i < 8) {
      console.log("\n" + guessedWord);
      let answer = input(`Input a letter: `);
      if (answer.length > 1 || answer.length == 0) {
        console.log("Please, input a single letter.")
        continue;
      } else if (!regex.test(answer)) {
        console.log("Please, enter a lowercase letter from the English alphabet.");
        continue;
      } else {
        if (guessedLetters.includes(answer)) {
          console.log("You've already guessed this letter.");
        } else if (words[randomInt].includes(answer)) {
          guessedLetters.push(answer);
          guessedWord = guessedWord.split('').map((char, index) => {
            if (words[randomInt][index] === answer) {
              return answer;
            } else {
              return char;
            }
          }).join('');
          
          if (guessedWord === words[randomInt]) {
            break;
          } else {
            continue;
          }
        } else {
          guessedLetters.push(answer);
          console.log("That letter doesn't appear in the word");
          i += 1;
        }
      }
    }
    if (i === 8) {
      console.log("\nYou lost!");
      loses += 1;
    } else {
      console.log("You guessed the word " + guessedWord + "!\nYou survived!\n");
      wins += 1;
    }
  } else if (action === "results") {
    console.log(`You won: ${wins} times.`)
    console.log(`You lost: ${loses} times.`)
  } else if (action === "exit") {
    break;
  }
}


