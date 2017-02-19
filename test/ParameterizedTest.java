import ESC.Week4.Sum;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

import java.util.*;

@RunWith(Parameterized.class)   // THIS IS IMPORTANT

public class ParameterizedTest {
    public int sum, a, b;

    public ParameterizedTest(int sum, int a, int b) {
        this.sum = sum;
        this.a = a;
        this.b = b;
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{{0, 0, 0}, {2, 1, 1}});
    }

   @Test public void additionTest() {
	   Assert.assertEquals(sum, Sum.sum(a, b));
   }
}
