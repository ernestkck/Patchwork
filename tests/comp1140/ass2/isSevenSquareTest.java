package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Determine whether a 7x7 square of spaces is completely filled
 * If it is completely filled, the player will receive the special tile and get 7 buttons
 */
public class isSevenSquareTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    @Test
    public void testTrue(){
        int i, j, x, y;
        for(i=0; i<=2; i++){
            for(j=0; j<=2; j++) {
                Player player1 = new Player(0, 5, 0);
                Player player2 = new Player(0, 5, 0);
                Game.specialTile = false;
                for (x = 0; x < 7; x++) {
                    for (y = 0; y < 7; y++) {
                        player1.grid[x + i][y + j] = true;
                        player2.grid[x + i][y + j] = true;
                    }
                }
                Game.isSevenSquare(player1);
                Game.isSevenSquare(player2);
                assertTrue("specialTile is not true: " + i + "," + j + " - " + (i+6) + "," + (j+6), Game.specialTile);
                assertTrue("player1's buttonsOwned is not 12: " + i + "," + j + " - " + (i+6) + "," + (j+6), player1.getButtonsOwned() == 12);
                assertFalse("player2 has 12 buttons", player2.getButtonsOwned() == 12);
            }
        }
    }

    @Test
    public void testEmpty(){
        Player player = new Player(0,5,0);
        Game.specialTile = false;
        Game.isSevenSquare(player);
        assertFalse("specialTile is not false for empty grid", Game.specialTile);
    }

    @Test
    public void testFalse(){
        Random rand = new Random();
        Player player1 = new Player(0,5,0);
        Player player2 = new Player(0,5,0);
        Game.specialTile = false;
        for(int i=0; i<49;i++){
            player1.grid[rand.nextInt(7)][rand.nextInt(7)] = true;
            player2.grid[rand.nextInt(7)][rand.nextInt(7)] = true;
        }
        Game.isSevenSquare(player1);
        Game.isSevenSquare(player2);
        assertFalse("specialTile is true: ", Game.specialTile);
        assertFalse("player1 has 12 buttons", player1.getButtonsOwned() == 12);
        assertFalse("player2 has 12 buttons", player2.getButtonsOwned() == 12);
    }

}
