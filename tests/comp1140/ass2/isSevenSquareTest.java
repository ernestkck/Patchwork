package comp1140.ass2;

import org.junit.Test;

import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Determine whether a 7x7 square of spaces is completely filled
 * If it is completely filled, the player will receive the special tile and get 7 buttons
 */
public class isSevenSquareTest {

    @Test(timeout = 10000)
    public void testTrue(){
        int i, j, x, y;
        for(i=0; i<=2; i++){
            for(j=0; j<=2; j++) {
                Player player = new Player(0, 0, 0);
                Game.specialTile = false;
                for (x = 0; x < 7; x++) {
                    for (y = 0; y < 7; y++) {
                        player.grid[x + i][y + j] = true;
                    }
                }
                Game.isSevenSquare(player);
                assertTrue("specialTile is not true: " + i + "," + j + " - " + (i+6) + "," + (j+6), Game.specialTile);
                assertTrue("buttonsOwned is not 7: " + i + "," + j + " - " + (i+6) + "," + (j+6), player.getButtonsOwned() == 7);
            }
        }
    }

}
