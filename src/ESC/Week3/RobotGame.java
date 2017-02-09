package ESC.Week3;

class Robot {
	private String name;
    private String state;
    private IBehaviour behaviour;

	public Robot (String name)
	{
		this.name = name;
	}

	public void behave ()
	{
        System.out.println("Moving " + behaviour.moveCommand() + " units.");
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBehavior() {
        if (state == null){
            state = "Normal";
            behaviour = new Normal();
        }
		switch (state) {
            case "Normal":
                state = "Aggressive";
                behaviour = new Aggressive();
                break;
            case "Aggressive":
                state = "Defensive";
                behaviour = new Defensive();
                break;
            case "Defensive":
                state = "Normal";
                behaviour = new Normal();
                break;
        }
	}
}

interface IBehaviour {
	int moveCommand();
}

class Normal implements IBehaviour {
    public int moveCommand() {
        System.out.println("Normal");
        return 0;
    }
}

class Defensive implements IBehaviour {
    public int moveCommand() {
        System.out.println("Defensive");
        return 1;
    }
}

class Aggressive implements IBehaviour {
    public int moveCommand() {
        System.out.println("Aggressive");
        return 2;
    }
}

public class RobotGame {

	public static void main(String[] args) {

		Robot r1 = new Robot("Big Robot");
		Robot r2 = new Robot("George v.2.1");
		Robot r3 = new Robot("R2");

		r1.setBehavior();
		r2.setBehavior();
		r3.setBehavior();

        r1.behave();
		r2.behave();
		r3.behave();

		//change the behaviors of each robot.
		r1.setBehavior();
		r2.setBehavior();
		r3.setBehavior();
		
		r1.behave();
		r2.behave();
		r3.behave();
	}
}