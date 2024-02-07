package converter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String check = scanner.next();
            if (check.equals("/exit")) {
                break;
            }
            int sourceBase = Integer.parseInt(check);
            int targetBase = scanner.nextInt();

            boolean converter = true;
            while (converter) {
                System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type /back)");
                String sourceNumber = scanner.next();
                if (sourceNumber.equals("/back")) {
                    converter = false;
                } else {
                    String result = convertToString(sourceNumber, sourceBase, targetBase);
                    System.out.println("Converted result: " + result);
                }
            }
        }

        scanner.close();
    }

    public static String convertToString(String sourceNumber, int sourceBase, int targetBase) {
        if (sourceBase < 2 || sourceBase > 36 || targetBase < 2 || targetBase > 36) {
            throw new IllegalArgumentException("Source and target bases must be between 2 and 36.");
        }

        String[] parts = sourceNumber.split("\\.");
        BigInteger integerPart = new BigInteger(parts[0], sourceBase);
        StringBuilder result = new StringBuilder();

        if (parts.length > 1) {
            String fractionalPart = parts[1];
            BigInteger numerator = new BigInteger(fractionalPart, sourceBase);
            BigInteger denominator = BigInteger.valueOf(sourceBase).pow(fractionalPart.length());
            StringBuilder fractionalResult = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                numerator = numerator.multiply(BigInteger.valueOf(targetBase));
                BigInteger[] divMod = numerator.divideAndRemainder(denominator);
                int digit = divMod[0].intValue();
                fractionalResult.append(Integer.toString(digit, targetBase));
                numerator = divMod[1];
            }

            result.append(fractionalResult.toString());
        }

        String integerStr = integerPart.toString(targetBase);

        if (result.length() > 0) {
            return integerStr + "." + result.toString();
        } else {
            return integerStr;
        }
    }
}
