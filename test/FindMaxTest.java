import ESC.Week4.FindMax;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by HanWei on 13/2/2017.
 */
@RunWith(Parameterized.class)

public class FindMaxTest {
    public int a;
    public int[] b;

    public FindMaxTest(int[] b) {
        a = b[0];
        this.b = b;
//        int meow[] = {19, 1, 3, 5, 2, 19, 14};
//        int woof[] = {200, 64, 39, 59, 153, 200, 193};
    }

    @Parameters
    public static Collection<Object[]> parameters() {
//        int[][] testCases = {{19, 1, 3, 5, 2, 19, 14}, {200, 64, 39, 59, 153, 200, 193}};
//        return Arrays.asList(new Object[][]{{0, 0, 0}, {2, 1, 1}});
        return Arrays.asList(new Object[][] {{new int[]{19, 1, 3, 5, 2, 19, 14}}, {new int[] {200, 64, 39, 59, 153, 200, 193}}});
        // Each array in the first array is one set of tests
        // Each item inside that array is one param for the test case
    }

    @Test
    public void testMaxPass() {
        assertTrue(FindMax.max(new int[]{5,6,17,8,2}) == 17);
    }

    @Test
    public void testMaxFail() {
        assertTrue(FindMax.max(new int[]{1,2,4,19}) == 19);
    }

    @Test
    public void testMaxError() {
        assertTrue(FindMax.max(new int[]{}) == -1);
    }

//    @Test
//    public void testMax() {
//        assertEquals(a, FindMax.max(b));
//    }
}
