package ESC.Week3;
import java.util.Scanner;

public class FactoryPatternDemoOriginal {

	public static void main(String[] args){
			
		EnemyShip theEnemy = null;
				
		Scanner userInput = new Scanner(System.in);
			
		String enemyShipOption = "";
			
		System.out.print("What type of ship? (U or R)");
			
		if (userInput.hasNextLine()){	
			enemyShipOption = userInput.nextLine();				
		}
			
		if (enemyShipOption == "U"){	
			theEnemy = new UFOEnemyShip();				
		} else if (enemyShipOption == "R"){
			theEnemy = new RocketEnemyShip();	
		} else {		
			theEnemy = new BigUFOEnemyShip();	
		}
			
		doStuffEnemy(theEnemy);
	}
		
	public static void doStuffEnemy(EnemyShip anEnemyShip){
		anEnemyShip.displayEnemyShip();
		anEnemyShip.followHeroShip();
		anEnemyShip.enemyShipShoots();
	}
}

abstract class EnemyShip {
	
	private String name;
	private double speed;
	private double directionX;
	private double directionY;
	private double amtDamage;
	
	public String getName() { return name; }
	public void setName(String newName) { name = newName; }
	
	public double getDamage() { return amtDamage; }
	public void setDamage(double newDamage) { amtDamage = newDamage; }
	
	public void followHeroShip(){		
		System.out.println(getName() + " is following the hero");		
	}
	
	public void displayEnemyShip(){		
		System.out.println(getName() + " is on the screen");
	}
	
	public void enemyShipShoots() {
		System.out.println(getName() + " attacks and does " + getDamage() + " damage to hero");
	}
	
}

class UFOEnemyShip extends EnemyShip {
	public UFOEnemyShip(){	
		setName("UFO Enemy Ship");		
		setDamage(20.0);		
	}
}

class BigUFOEnemyShip extends EnemyShip {
	public BigUFOEnemyShip(){	
		setName("Big UFO Enemy Ship");		
		setDamage(40.0);		
	}
}

class RocketEnemyShip extends EnemyShip {
	public RocketEnemyShip(){
		setName("Rocket Enemy Ship");
		setDamage(10.0);
	}
}