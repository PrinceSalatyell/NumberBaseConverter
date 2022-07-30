package converter;
import java.util.*;
import java.math.*;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    static String base = "0123456789abcdefghijklmnopqrstuvwxyz";
    static int src = 0;
    static int trgt = 0;

    static void ftPutNbrBase(BigInteger number, int b) {
        String base_ = base.substring(0, b);
        if (number.divide(BigInteger.valueOf(b)).equals(BigInteger.ZERO))
            System.out.print(base_.charAt(Integer.parseInt(String.valueOf(number))));
        else {
            ftPutNbrBase(number.divide(BigInteger.valueOf(b)), b);
            ftPutNbrBase(number.remainder(BigInteger.valueOf(b)), b);
        }
    }

    static void fracToDec(String number, int b) {
        String s = "";
        BigDecimal n = BigDecimal.ZERO;
        for (int i = 0; i < number.length(); i++) {
            n = n.add(new BigDecimal(String.valueOf(Character.getNumericValue(number.charAt(i)) * Math.pow(b, -(i + 1)))));
        }
        fracFromDec(n, trgt);
    }

    static void fracFromDec(BigDecimal number, int b) {
        String base_ = base.substring(0, b);
        BigDecimal result;
        String res = "0.";
        int i = 201;
        while (--i > 0)
        {
            res = res.concat(String.valueOf(base_.charAt(Integer.parseInt(String.valueOf(number.multiply(BigDecimal.valueOf(b)).setScale(0, RoundingMode.FLOOR))))));
            number = number.multiply(BigDecimal.valueOf(b)).subtract(number.multiply(BigDecimal.valueOf(b)).setScale(0, RoundingMode.FLOOR));
            if (number.compareTo(number.setScale(0, RoundingMode.CEILING)) == 0)
                break;
        }
        res = res.concat("00000");
        if (res.length() > 6)
            System.out.print(res.substring(1, 7));
        else
            System.out.print(res.substring(1));
    }

    public static void main(String[] args) {
        String answer = "";
        BigInteger number;
        String decimal = "";
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            answer = scanner.nextLine();
            if (answer.equals("/exit"))
                break;
            src = Integer.parseInt(answer.split(" ")[0]);
            trgt = Integer.parseInt(answer.split(" ")[1]);
            while (true)
            {
                System.out.printf("Enter number in base %d to convert to base %d (To go back type /back)\n", src, trgt);
                answer = scanner.nextLine();
                if (answer.equals("/back"))
                    break;
                if (answer.contains(".")) {
                    number = new BigInteger(answer.substring(0, answer.indexOf('.')), src);
                    decimal = answer.substring(answer.indexOf('.') + 1);
                }
                else
                    number = new BigInteger(answer, src);
                System.out.print("Conversion result: ");
                ftPutNbrBase(number, trgt);
                if (!decimal.isEmpty()) {
                    fracToDec(decimal, src);
                }
                decimal = "";
                System.out.println();
                System.out.println();
            }
            System.out.println();
        }
    }
}
