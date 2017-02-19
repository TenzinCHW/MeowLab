import ESC.Week4.Russian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by HanWei on 18/2/2017.
 */

@RunWith(Parameterized.class)

public class RussianFault {
    int m;
    int n;

    public RussianFault(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {Integer.MAX_VALUE, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MIN_VALUE},
                {Integer.MIN_VALUE, Integer.MAX_VALUE},
                {Integer.MIN_VALUE, Integer.MIN_VALUE}});
    }

    @Test
    public void faultTest() {
        assertEquals(m * n, Russian.multiply(m, n));
    }
}
