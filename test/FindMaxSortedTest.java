import ESC.Week4.FindMaxUsingSorting;
import org.junit.Test;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import ESC.Week4.Sorter;
import org.jmock.Expectations;

/**
 * Created by HanWei on 17/2/2017.
 */
public class FindMaxSortedTest {
    @Test
    public void findMaxSortTest() {
        Mockery context = new JUnit4Mockery();

        final Sorter sorter = context.mock(ESC.Week4.Sorter.class);
        int[] inpArray = new int[]{5, 2, 6, 44, 2, 723, 4};

        context.checking(new Expectations() {{
            oneOf(sorter).sort(inpArray);
            will(returnValue(new int[] {2,2,4,5,6,44,723}));
        }});

        FindMaxUsingSorting.findmax(inpArray, sorter);
        context.assertIsSatisfied();
    }
}
