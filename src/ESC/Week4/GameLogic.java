package ESC.Week4;

import java.util.HashMap;

/**
 * Created by HanWei on 16/2/2017.
 */
public class GameLogic {
    public static int xMax = 20;
    public static int xMin = -20;
    public static int yMax = 20;
    public static int yMin = -20;
    private int HP;
    private int ammo;
    private int speed;
    private boolean dead;
    private int x;
    private int y;
    private int dirx;
    private int diry;
    private HashMap<String, Integer> inventory;

    public int getHP() {
        return HP;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isDead() {
        return dead;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirx() {
        return dirx;
    }

    public int getDiry() {
        return diry;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public GameLogic() {
        HP = 100;
        ammo = 5;
        speed = 1;
        x = 0;
        y = 0;
        dirx = 0;
        diry = 1;

        inventory = new HashMap<>();
        inventory.put("Potion", 5);
    }

    /*
    Moves the player object
    Pre-condition: !isDead
    Post-condition: x or y must change unless moving in direction of wall
    @return
    @param moveDirX, moveDirY
     */
    public void move(int x, int y) {
        if (dead) return;
        this.x += x * speed;
        this.y += y * speed;

        if (this.x > xMax) this.x = xMax;
        if (this.y > yMax) this.y = yMax;
        if (this.x < xMin) this.x = xMin;
        if (this.y < yMin) this.y = yMin;

        dirx = x;
        diry = y;
        System.out.println("Now at x: " + this.x + " y: " + this.y);
    }

    /*
    Shoots a projectile from player object
    Pre-condition: !isDead, ammo > 0
    Post-condition: ammo must decrease
    @return
    @param
     */
    public void shoot() {
        if (dead) return;
        if (ammo == 0) {
            System.out.println("NO AMMO");
            return;
        }
        ammo--;
        System.out.println("Pewpew at x: " + dirx + " and y: " + diry);
    }

    /*
    Called when a projectile object collides with player object
    Pre-condition: !isDead
    Post-condition: HP must decrease
    @return
    @param damageTaken
     */
    public void takeHit(int damage) {
        if (dead) return;
        if (HP <= damage) {
            HP = 0;
            dead = true;
            System.out.println("YOU ARE DEAD");
        } else {
            HP -= damage;
            System.out.println("Your HP is now " + HP);
        }
    }

    /*
    Called when player uses a potion
    Pre-condition: inventory.get("Potion") > 0
    Post-condition: inventory.get("Potion") must reduce by 1
    @return
    @param
     */
    public void usePot() {
        if (dead) return;
        int potions = inventory.get("Potion");
        if (potions > 0) {
            inventory.replace("Potion", potions - 1);
            if (HP + 50 > 100) {
                HP = 100;
            } else {
                HP += 50;
            }
        }
        else System.out.println("YOU AIN'T GOT NO POTIONS LEFT");
    }
}
