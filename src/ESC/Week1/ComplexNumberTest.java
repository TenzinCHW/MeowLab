package ESC.Week1;

import java.util.Scanner;

/**
 * Created by HanWei on 26/1/2017.
 */
public class ComplexNumberTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("First real: ");
            double firstReal = in.nextDouble();
            System.out.println("First imaginary: ");
            double firstImage = in.nextDouble();
            System.out.println("Polar? (true/false): ");
            boolean polar1 = in.nextBoolean();
            System.out.println("Second real: ");
            double secondReal = in.nextDouble();
            System.out.println("Second imaginary: ");
            double secondImage = in.nextDouble();
            System.out.println("Polar? (true/false)");
            boolean polar2 = in.nextBoolean();

            ComplexNumber meow = new ComplexNumber(firstReal, firstImage, polar1);
            ComplexNumber woof = new ComplexNumber(secondReal, secondImage, polar2);
            System.out.println(meow);
            System.out.println(woof);
            ComplexNumber ruff = meow.add(woof);
            System.out.println(ruff);
            ruff = meow.subtract(woof);
            System.out.println(ruff);
            ruff = meow.multiply(woof);
            System.out.println(ruff);
            ruff = meow.divide(woof);
            System.out.println(ruff);
        } catch (Exception e) {
            System.out.println("You did not enter a valid number. Must be a double.");
        }
    }
}
