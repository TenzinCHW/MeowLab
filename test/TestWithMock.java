import ESC.Week4.CalculatingMachine;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import ESC.Week4.CalculatingMachine.Calculator;
import ESC.Week4.CalculatingMachine.Printer;
import org.jmock.Expectations;

public class TestWithMock {
    @Test
    public void testCalculatingMachine() {
        Mockery context = new JUnit4Mockery();

        final Printer printer = context.mock(Printer.class);
        final Calculator calculator = context.mock(Calculator.class);

        context.checking(new Expectations() {{
            oneOf(calculator).add(1, 2);
            will(returnValue(3));
            oneOf(printer).print("result is 3");
        }});

        CalculatingMachine machine = new CalculatingMachine(printer, calculator);
        machine.processAdd(1, 2);

        context.assertIsSatisfied();
    }
}