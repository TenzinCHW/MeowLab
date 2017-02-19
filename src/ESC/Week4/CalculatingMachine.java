package ESC.Week4;

public class CalculatingMachine {       
    private final Printer printer;
    private final Calculator calculator;

    public CalculatingMachine(Printer printer, Calculator calculator) {
        this.printer = printer;
        this.calculator = calculator;
    }

    public void processAdd(int x, int y) {
        int result = calculator.add(x, y);
        printer.print("result is " + result);
    }

    public static interface Printer {
        void print(String string);
    }

    public static interface Calculator {
        int add(int x, int y);
    }   
}