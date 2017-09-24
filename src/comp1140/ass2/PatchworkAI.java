package comp1140.ass2;

import java.util.ArrayList;
import java.util.Random;

public class PatchworkAI {
    private final PatchworkGame game;
    private final Player player;

    public PatchworkAI(PatchworkGame game, Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Generate a valid move that follows from the given patch circle and game placement string.
     * @param patchCircle a patch circle string to initialize the game
     * @param placement  A valid placement string indicating a game state
     * @return a valid patch placement string, which will be "." if the player chooses to advance
     */
    public String determineWhetherAdvance(String patchCircle, String placement){
        if (false){
            return ".";
        }
        else{
            return generatePatchPlacement(patchCircle, placement);
        }
    }

    public static String generateBestPatchPlacement(String patchCircle, String placement){
        // FIXME Task 12: Good comuputer opponent
        return null;
    }

    public static String generatePatchPlacement(String patchCircle, String placement) {
        Tuple player = Game.playersFromGameState(patchCircle, placement);
        Player player1 = (Player)player.getLeft();
        Player player2 = (Player)player.getRight();
        if (player2.getTimeSquare() > 53){
            return placement;
        }
        int circlePosition = Game.circlePosFromGameState(patchCircle, placement);
        ArrayList<Character> patches = new ArrayList<>();
        for (int i = 0; i < patches.size(); i++){
            Patch patch = Patch.valueOf("" + patchCircle.charAt((circlePosition + i) % patchCircle.length()));
            if (patch.getButtonCost() <= player2.getButtonsOwned() && player2.getTimeSquare() + patch.getTimeCost() <= 53){
                patches.add(patch.getChar());
            }
        }
        if (patches.isEmpty() && player2.getTimeSquare() <= 53){
            return placement + '.';
        }

        return placement;
    }
}
