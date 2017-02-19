import ESC.Week4.BiSectionExample;
import org.junit.Before;
import org.junit.Test;

public class BiSectionTest {
	private BiSectionExample bi;
	
	@Before 
	public void runBeforeEachTest()
	{
		bi = new BiSectionExample();
	}
	
	@Test
	public void test4MethodCoverage () {
//		System.out.print(bi.root(0.5, 100.3, 0.1));
		assert (bi.root(120, 100.3, 0.1) >= 100);
		//question: should we assert the returned value is the exact value we expect?
	}
	
	@Test
	public void test4LoopCoverage1 () {//loop once
		assert(bi.root(0,100,80) > 50);
	}

    @Test
    public void test4LoopCoverage2 () {
        assert(bi.root(1,100,0) > 49);  // Stuck in loop
    }

    @Test
    public void test4LoopCoverage3 () {
        assert(bi.root(0,1000,400) > 500);  // loop twice
    }

}
