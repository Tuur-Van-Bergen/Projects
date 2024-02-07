package numbers;
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println("\n");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be processed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
        System.out.println("\n");
        long numberOne = 1;
        long numberTwo = 1;
        String actionOne = "";
        String actionTwo = "";
        String actionThree = "";
        String actionFour = "";
        String actionFive = "";
        String actionSix = "";
        String actionSeven = "";
        String actionEight = "";
        while (numberOne != 0) {
            System.out.print("Enter a request:");
            String inputLine = scanner.nextLine();
            String[] parts = inputLine.trim().split(" ");
            int numberOfInputs = parts.length;
            if (numberOfInputs == 1) {
                numberOne = Long.parseLong(parts[0]);
                System.out.println("\n");
                if (numberOne == 0) {
                    System.out.println("Goodbye!");
                } else if (numberOne < 0) {
                    System.out.println("The first parameter should be a natural number or zero.");
                } else if (numberTwo < 0) {
                    System.out.println("The second parameter should be a natural number");
                } else {
                    System.out.println("Properties of " + numberOne);
                    System.out.println("even: " + isEven(numberOne));
                    System.out.println("odd: " + isOdd(numberOne));
                    System.out.println("buzz: " + isBuzz(numberOne));
                    System.out.println("duck: " + isDuck(numberOne));
                    System.out.println("palindromic: " + isPalindromic(numberOne));
                    System.out.println("gapful: " + isGapful(numberOne));
                    System.out.println("spy: " + isSpy(numberOne));
                    System.out.println("square: " + isSquare(numberOne));
                    System.out.println("sunny: " + isSunny(numberOne));
                    System.out.println("jumping: " + isJumping(numberOne));
                    System.out.println("sad: " + isSad(numberOne));
                    System.out.println("happy: " + isHappy(numberOne));
                    System.out.println("\n");
                }
            } else if (numberOfInputs == 2) {
                numberOne = Long.parseLong(parts[0]);
                numberTwo = Long.parseLong(parts[1]);
                if (numberOne == 0) {
                    System.out.println("Goodbye!");
                } else if (numberOne < 0) {
                    System.out.println("The first parameter should be a natural number or zero.");
                } else if (numberTwo < 0) {
                    System.out.println("The second parameter should be a natural number");
                } else {
                    for (int i = 0; i < numberTwo; i++) {
                        printNumberProperties(numberOne);
                        numberOne++;
                        System.out.println();
                    }
                }
            } else if (numberOfInputs > 2) {
                numberOne = Long.parseLong(parts[0]);
                numberTwo = Long.parseLong(parts[1]);
                if (numberOfInputs == 3) {
                    actionOne = parts[2].toUpperCase();
                    if (isPropertyValid(actionOne)) {
                        for (int i = 0; i < numberTwo; i++) {
                            while (!getPropertyFunction(actionOne).test(numberOne)) {
                                numberOne++;
                            }
                            printNumberProperties(numberOne);
                            numberOne++;
                        }
                    } else {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 4) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) || !getPropertyFunction(actionTwo).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                            System.out.println(findMutuallyExclusivePair(actionOne, actionTwo));
                        }
                    } else if (!isPropertyValid(actionOne) && !isPropertyValid(actionTwo)) {
                        System.out.println("The properties [" + actionOne + ", " + actionTwo + "] are wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 5) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                            System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 6) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    actionFour = parts[5].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree, actionFour)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree, actionFour)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne) ||
                                       !getPropertyFunction(actionFour).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                            System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree, actionFour));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFour)) {
                        System.out.println("The property [" + actionFour + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 7) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    actionFour = parts[5].toUpperCase();
                    actionFive = parts[6].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree, actionFour, actionFive)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree, actionFour, actionFive)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne) ||
                                       !getPropertyFunction(actionFour).test(numberOne) ||
                                       !getPropertyFunction(actionFive).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                           System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree, actionFour, actionFive));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFour)) {
                        System.out.println("The property [" + actionFour + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFive)) {
                        System.out.println("The property [" + actionFive + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 8) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    actionFour = parts[5].toUpperCase();
                    actionFive = parts[6].toUpperCase();
                    actionSix = parts[7].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne) ||
                                       !getPropertyFunction(actionFour).test(numberOne) ||
                                       !getPropertyFunction(actionFive).test(numberOne) ||
                                       !getPropertyFunction(actionSix).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                           System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFour)) {
                        System.out.println("The property [" + actionFour + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFive)) {
                        System.out.println("The property [" + actionFive + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionSix)) {
                        System.out.println("The property [" + actionSix + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 9) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    actionFour = parts[5].toUpperCase();
                    actionFive = parts[6].toUpperCase();
                    actionSix = parts[7].toUpperCase();
                    actionSeven = parts[8].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne) ||
                                       !getPropertyFunction(actionFour).test(numberOne) ||
                                       !getPropertyFunction(actionFive).test(numberOne) ||
                                       !getPropertyFunction(actionSix).test(numberOne) ||
                                       !getPropertyFunction(actionSeven).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                            System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFour)) {
                        System.out.println("The property [" + actionFour + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFive)) {
                        System.out.println("The property [" + actionFive + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionSix)) {
                        System.out.println("The property [" + actionSix + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionSeven)) {
                        System.out.println("The property [" + actionSeven + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                } else if (numberOfInputs == 10) {
                    actionOne = parts[2].toUpperCase();
                    actionTwo = parts[3].toUpperCase();
                    actionThree = parts[4].toUpperCase();
                    actionFour = parts[5].toUpperCase();
                    actionFive = parts[6].toUpperCase();
                    actionSix = parts[7].toUpperCase();
                    actionSeven = parts[8].toUpperCase();
                    actionEight = parts[9].toUpperCase();
                    if (arePropertiesValid(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven, actionEight)) {
                        if (!areMutuallyExclusive(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven, actionEight)) {
                            for (int i = 0; i < numberTwo; i++) {
                                while (!getPropertyFunction(actionOne).test(numberOne) ||
                                       !getPropertyFunction(actionTwo).test(numberOne) ||
                                       !getPropertyFunction(actionThree).test(numberOne) ||
                                       !getPropertyFunction(actionFour).test(numberOne) ||
                                       !getPropertyFunction(actionFive).test(numberOne) ||
                                       !getPropertyFunction(actionSix).test(numberOne) ||
                                       !getPropertyFunction(actionSeven).test(numberOne) ||
                                       !getPropertyFunction(actionEight).test(numberOne)) {
                                    numberOne++;
                                }
                                printNumberProperties(numberOne);
                                numberOne++;
                            }
                        } else {
                            System.out.println(findMutuallyExclusivePair(actionOne, actionTwo, actionThree, actionFour, actionFive, actionSix, actionSeven, actionEight));
                        }
                    } else if (!isPropertyValid(actionOne)) {
                        System.out.println("The property [" + actionOne + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionTwo)) {
                        System.out.println("The property [" + actionTwo + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionThree)) {
                        System.out.println("The property [" + actionThree + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFour)) {
                        System.out.println("The property [" + actionFour + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionFive)) {
                        System.out.println("The property [" + actionFive + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionSix)) {
                        System.out.println("The property [" + actionSix + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionSeven)) {
                        System.out.println("The property [" + actionSeven + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    } else if (!isPropertyValid(actionEight)) {
                        System.out.println("The property [" + actionEight + "] is wrong.");
                        System.out.println("Available properties: [SAD, HAPPY, JUMPING, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY]");
                    }
                }
            }
        }
    }
    
    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean isOdd(long number) {
        return number % 2 != 0;
    }
    
    public static boolean isBuzz(long number) {
        long lastDigit = number % 10;
        if (number % 7 == 0) {
            return true;
        } else if (lastDigit == 7) {
            return true;
        } 
        return false;
    }

    public static boolean isDuck(long number) {
        String numberStr = String.valueOf(number);
        for (int i = 1; i < numberStr.length(); i++) {
            if (numberStr.charAt(i) == '0') {
                return true;
            }
        }
        return false;
    }

    public static boolean isPalindromic(long number) {
        String numberStr = String.valueOf(number);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = numberStr.length() - 1; i >= 0; i--) {
            stringBuilder.append(numberStr.charAt(i));
        }
        String reversed = stringBuilder.toString();
        return reversed.equals(numberStr);
    }

    public static boolean isGapful(long number) {
        long concat = 0;
        String numberStr = String.valueOf(number);
        if (numberStr.length() >= 3) {
            concat = getFirstDigit(number);
            concat *= 10;
            concat += (number % 10);
            return number % concat == 0;
        }
        return false;
    }

    public static boolean isSpy(long number) {
        String numberStr = String.valueOf(number);
        char[] charArray = numberStr.toCharArray();
        long sum = 0;
        long product = 1;
        for (int i = 0; i < numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            if (digit == 0) {
                return false;
            }
            sum += digit;
            product *= digit;
        }
        return (sum == product);
    }

    public static boolean isSquare(long number) {
        double sqrt = Math.sqrt(number);
        int sqrtAsInt = (int) sqrt;
        return (sqrtAsInt * sqrtAsInt) == number;
    }

    public static boolean isSunny(long number) {
        return isSquare(number + 1); 
    }

    public static boolean isJumping(long number) {
        String numberStr = String.valueOf(number);
        for (int i = 1; i < numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            int previousDigit = Character.getNumericValue(numberStr.charAt(i - 1));
            if (digit != previousDigit + 1 && digit != previousDigit - 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSad(long number) {
        return !isHappy(number);
    }

    public static boolean isHappy(long number) {
        long num = number;
        Set<Long> seen = new HashSet<>();
    
        while (num != 1 && !seen.contains(num)) {
            seen.add(num);
            num = calculateSumOfSquaresOfDigits(num);
        }
    
        return num == 1;
    }

    public static boolean isNotEven(long number) {
        return !isEven(number);
    }

    public static boolean isNotOdd(long number) {
        return !isOdd(number);
    }
    
    public static boolean isNotBuzz(long number) {
        return !isBuzz(number);
    }

    public static boolean isNotDuck(long number) {
        return !isDuck(number);
    }

    public static boolean isNotPalindromic(long number) {
        return !isPalindromic(number);
    }

    public static boolean isNotGapful(long number) {
        return !isGapful(number);
    }

    public static boolean isNotSpy(long number) {
        return !isSpy(number);
    }

    public static boolean isNotSquare(long number) {
        return !isSquare(number);
    }

    public static boolean isNotSunny(long number) {
         return !isSunny(number);
    }

    public static boolean isNotJumping(long number) {
        return !isJumping(number);    
    }

    public static boolean isNotSad(long number) {
        return !isSad(number);
    }

    public static boolean isNotHappy(long number) {
        return !isHappy(number);
    }
    
    private static long calculateSumOfSquaresOfDigits(long num) {
        String numberStr = String.valueOf(num);
        long sum = 0;
    
        for (int i = 0; i < numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            sum += (long) Math.pow(digit, 2);
        }
    
        return sum;
    }

    
    public static long getFirstDigit(long number) {
        number = Math.abs(number);
        while (number >= 10) {
            number /= 10;
        }
        return number;
    }

    public static boolean isPropertyValid(String property) {
        return property.equals("EVEN") || property.equals("ODD") || property.equals("BUZZ") ||
                property.equals("DUCK") || property.equals("PALINDROMIC") || property.equals("GAPFUL") ||
                property.equals("SPY") || property.equals("SQUARE") || property.equals("SUNNY") || 
                property.equals("JUMPING") || property.equals("SAD") || property.equals("HAPPY") || 
                property.equals("-EVEN") || property.equals("-ODD") || property.equals("-BUZZ") ||
                property.equals("-DUCK") || property.equals("-PALINDROMIC") || property.equals("-GAPFUL") ||
                property.equals("-SPY") || property.equals("-SQUARE") || property.equals("-SUNNY") || 
                property.equals("-JUMPING") || property.equals("-SAD") || property.equals("-HAPPY");
    }

    public static Predicate<Long> getPropertyFunction(String property) {
        switch (property) {
            case "EVEN":
                return Main::isEven;
            case "ODD":
                return Main::isOdd;
            case "BUZZ":
                return Main::isBuzz;
            case "DUCK":
                return Main::isDuck;
            case "PALINDROMIC":
                return Main::isPalindromic;
            case "GAPFUL":
                return Main::isGapful;
            case "SPY":
                return Main::isSpy;
            case "SQUARE":
                return Main::isSquare;
            case "SUNNY":
                return Main::isSunny;
            case "JUMPING":
                return Main::isJumping;
            case "SAD":
                return Main::isSad;
            case "HAPPY":
                return Main::isHappy;
            case "-EVEN":
                return Main::isNotEven;
            case "-ODD":
                return Main::isNotOdd;
            case "-BUZZ":
                return Main::isNotBuzz;
            case "-DUCK":
                return Main::isNotDuck;
            case "-PALINDROMIC":
                return Main::isNotPalindromic;
            case "-GAPFUL":
                return Main::isNotGapful;
            case "-SPY":
                return Main::isNotSpy;
            case "-SQUARE":
                return Main::isNotSquare;
            case "-SUNNY":
                return Main::isNotSunny;
            case "-JUMPING":
                return Main::isNotJumping;
            case "-SAD":
                return Main::isNotSad;
            case "-HAPPY":
                return Main::isNotHappy;
            default:
                return n -> false;
        }
    }

    public static boolean arePropertiesValid(String... properties) {
        for (String property : properties) {
            if (!isPropertyValid(property)) {
                return false;
            }
        }
    
        return true;
    }

    public static boolean areMutuallyExclusive(String... actions) {
        if (actions.length < 2 || actions.length > 7) {
            return false;
        }
        String[][] mutuallyExclusivePairs = {
            {"EVEN", "ODD"},
            {"DUCK", "SPY"},
            {"SUNNY", "SQUARE"},
            {"SAD", "HAPPY"},
            {"-EVEN", "-ODD"},
            {"-DUCK", "-SPY"},
            {"-SUNNY", "-SQUARE"},
            {"-SAD", "-HAPPY"},
            {"EVEN", "-EVEN"},
            {"ODD", "-ODD"},
            {"BUZZ", "-BUZZ"},
            {"DUCK", "-DUCK"},
            {"PALINDROMIC", "-PALINDROMIC"},
            {"GAPFUL", "-GAPFUL"},
            {"SPY", "-SPY"},
            {"SQUARE", "-SQUARE"},
            {"SUNNY", "-SUNNY"},
            {"JUMPING", "-JUMPING"},
            {"HAPPY", "-HAPPY"},
            {"SAD", "-SAD"}
        };
            
        for (String[] pair : mutuallyExclusivePairs) {
            boolean firstPropertyFound = false;
            boolean secondPropertyFound = false;
            for (String action : actions) {
                if (action.equals(pair[0])) {
                    firstPropertyFound = true;
                } else if (action.equals(pair[1])) {
                    secondPropertyFound = true;
                }
            }
            if (firstPropertyFound && secondPropertyFound) {
                return true;
            }
        }
        return false;
    }

    public static String findMutuallyExclusivePair(String... actions) {
        String[][] mutuallyExclusivePairs = {
            {"EVEN", "ODD"},
            {"DUCK", "SPY"},
            {"SUNNY", "SQUARE"},
            {"SAD", "HAPPY"},
            {"-EVEN", "-ODD"},
            {"-DUCK", "-SPY"},
            {"-SUNNY", "-SQUARE"},
            {"-SAD", "-HAPPY"},
            {"EVEN", "-EVEN"},
            {"ODD", "-ODD"},
            {"BUZZ", "-BUZZ"},
            {"DUCK", "-DUCK"},
            {"PALINDROMIC", "-PALINDROMIC"},
            {"GAPFUL", "-GAPFUL"},
            {"SPY", "-SPY"},
            {"SQUARE", "-SQUARE"},
            {"SUNNY", "-SUNNY"},
            {"JUMPING", "-JUMPING"},
            {"HAPPY", "-HAPPY"},
            {"SAD", "-SAD"}
        };
    
        for (String[] pair : mutuallyExclusivePairs) {
            boolean firstPropertyFound = false;
            boolean secondPropertyFound = false;
            for (String action : actions) {
                if (action.equals(pair[0])) {
                    firstPropertyFound = true;
                } else if (action.equals(pair[1])) {
                    secondPropertyFound = true;
                }
            }
            if (firstPropertyFound && secondPropertyFound) {
                return "The request contains mutually exclusive properties: [" + pair[0] + ", " + pair[1] + "]\nThere are no numbers with these properties.";
            }
        }
        return null;
    }
        
    public static void printNumberProperties(long number) {
        System.out.print(number);
        if (isEven(number)) {
            System.out.print(" is even");
        }
        if (isOdd(number)) {
            System.out.print(" is odd");
        }
        if (isBuzz(number)) {
            System.out.print(", buzz");
        }
        if (isDuck(number)) {
            System.out.print(", duck");
        }
        if (isPalindromic(number)) {
            System.out.print(", palindromic");
        }
        if (isGapful(number)) {
            System.out.print(", gapful");
        }
        if (isSpy(number)) {
            System.out.print(", spy");
        }
        if (isSquare(number)) {
            System.out.print(", square");
        }
        if (isSunny(number)) {
            System.out.print(", sunny");
        }
        if (isJumping(number)) {
            System.out.print(", jumping");
        }
        if (isSad(number)) {
            System.out.print(", sad");
        }
        if (isHappy(number)) {
            System.out.print(", happy");
        }
        System.out.println();
    }
}
