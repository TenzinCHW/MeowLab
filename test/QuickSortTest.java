import ESC.Week4.QuickSort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by HanWei on 16/2/2017.
 */

@RunWith(Parameterized.class)

public class QuickSortTest {
    QuickSort sorted;

    int a[];
    int b[];

    public QuickSortTest(int[] inputArr, int[] expected) {
        a = inputArr;
        b = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 3, 5, 14, 19}, new int[]{ 1, 3, 5, 2, 19, 14}},
                {new int[]{7, 39, 59, 64, 153, 193, 200}, new int[]{64, 39, 59, 153, 200, 193, 7}
                }});
        // Each array in the first array is one set of tests
        // Each item inside that array is one param for the test case
    }

    @Before
    public void runBeforeEachTest() {
        sorted = new QuickSort();
    }

    @Test
    public void quickTest() {
        sorted.sort(b);
        assertEquals(Arrays.toString(a), Arrays.toString(b));
    }

}