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

public class RussianWhiteBox {
    int m;
    int n;

    public RussianWhiteBox(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {0, 1},
                {0, 2},
                {-1, 5},
                {1000, 5032}
        });
    }

    @Test
    public void whiteBoxTest() {
        assertEquals(m * n, Russian.multiply(m, n));
    }

}
