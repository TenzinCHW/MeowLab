package ESC.Week3;

public class PassbyValue {
	public static void main (String[] args) throws Exception {
		MyDataStructure mine = new MyDataStructure ();
		mine.num = 4;
		changeValue(mine);
		System.out.println(mine.num);
	}
	
	public static void changeValue (MyDataStructure m) {
		m = new MyDataStructure ();
	}

}

class MyDataStructure {
	public int num = 3;	
}
