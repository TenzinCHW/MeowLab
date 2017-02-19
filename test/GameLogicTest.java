import ESC.Week4.GameLogic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by HanWei on 16/2/2017.
 */

public class GameLogicTest {
    GameLogic game;

    @Before
    public void runBeforeEachTest() {
        game = new GameLogic();
    }

    @Test
    public void validMovement() {
        game.move(5, 19);
        assertTrue(game.getX() == 5 && game.getY() == 19 && game.getDirx() == 5 && game.getDiry() == 19);
    }

    @Test
    public void walkingDead() {
        int x = game.getX();
        int y = game.getY();
        game.takeHit(100);
        game.move(1,1);
        assertTrue(x == game.getX() && y == game.getY());
    }

    @Test
    public void hitUpperRightWall() {
        game.move(21, 21);
        assertTrue(game.getX() == 20 && game.getY() == 20 && game.getDirx() == 21 && game.getDiry() == 21);
    }

    @Test
    public void hitLowerLeftWall() {
        game.move(-21, -21);
        assertTrue(game.getX() == -20 && game.getY() == -20 && game.getDirx() == -21 && game.getDiry() == -21);
    }

    @Test
    public void stillHaveAmmo() {
        game.shoot();
        game.shoot();
        assertTrue(game.getAmmo() == 3);
    }

    @Test
    public void noMoreAmmo() {
        for (int i = 0; i < 6; i++) {
            game.shoot();
        }
        assertTrue(game.getAmmo() == 0);
    }

    @Test
    public void shootingDead() {
        int ammo = game.getAmmo();
        game.takeHit(100);
        game.shoot();
        assertEquals(game.getAmmo(), ammo);
    }

    @Test
    public void stayingAlive() {
        game.takeHit(99);
        assertTrue(game.getHP() == 1 && !game.isDead());
    }

    @Test
    public void dieLiao() {
        game.takeHit(9001);
        assertTrue(game.getHP() == 0 && game.isDead());
    }

    @Test
    public void dieAgain() {
        game.takeHit(100);
        game.takeHit(1);
        assertEquals(game.getHP(), 0);
    }

    @Test
    public void notHighEnuff() {
        game.takeHit(99);
        game.usePot();
        assertTrue(game.getHP() == 51 && game.getInventory().get("Potion") == 4);
    }

    @Test
    public void tooHigh() {
        game.takeHit(30);
        game.usePot();
        assertTrue(game.getHP() == 100 && game.getInventory().get("Potion") == 4);
    }

    @Test
    public void tooLittlePot() {
        for (int i = 0; i < 6; i++) {
            game.usePot();
        }
        assertTrue(game.getInventory().get("Potion") == 0);
    }

    @Test
    public void tryingToRevive() {
        int pot = game.getInventory().get("Potion");
        game.takeHit(100);
        game.usePot();
        int newPot = game.getInventory().get("Potion");
        assertEquals(newPot, pot);
    }
}
