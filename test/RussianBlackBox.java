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

public class RussianBlackBox {
    int m;
    int n;

    public RussianBlackBox(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {4, 0},
                {0, 3},
                {5, 5},
                {-1, 5},
                {6, -1},
                {-9, -4},
                {1000, 5032},
                {-4923, -1234},
                {1249, -4329},
                {-2392, 4930}});
    }

    @Test
    public void blackBoxTest() {
        assertEquals(m * n, Russian.multiply(m, n));
    }
}
