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
                for (x = 0; x < 7; x++) {
                    for (y = 0; y < 7; y++) {
                        player1.getGrid()[x + i][y + j] = true;
                        player2.getGrid()[x + i][y + j] = true;
                    }
                }
                assertTrue("player1 returned false for testTrue: " + i + "," + j + " - " + (i+6) + "," + (j+6), player1.isSevenSquare(player1.getGrid()));
                assertTrue("player2 returned false for testTrue: " + i + "," + j + " - " + (i+6) + "," + (j+6), player2.isSevenSquare(player2.getGrid()));
            }
        }
    }

    @Test
    public void testEmpty(){
        Player player = new Player(0,5,0);
        assertFalse("returned true for empty grid", player.isSevenSquare(player.getGrid()));
    }

    @Test
    public void testFalse(){
        Random rand = new Random();
        Player player1 = new Player(0,5,0);
        Player player2 = new Player(0,5,0);
        for(int i=0; i<49;i++){
            player1.getGrid()[rand.nextInt(7)][rand.nextInt(7)] = true;
            player2.getGrid()[rand.nextInt(7)][rand.nextInt(7)] = true;
        }
        assertFalse("player1 returned true for testFalse: ", player1.isSevenSquare(player1.getGrid()));
        assertFalse("player2 returned true for testFalse: ", player2.isSevenSquare(player2.getGrid()));
    }

}
