import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.List;

/**
 * Created by HanWei on 16/2/2017.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses ({ GameLogicTest.class })  // Add test classes here.

public class TestAllGame {
    public static void main(String[] args) {
        JUnit4TestAdapter suite = new JUnit4TestAdapter (TestAll.class);
        junit.textui.TestRunner.run(suite);

        List<Test> list = suite.getTests();
    	System.out.println("test count " + list.size());
    	for (int i = 0; i < list.size(); i++) {
    		junit.textui.TestRunner.run(list.get(i));
    	}
    }
}
