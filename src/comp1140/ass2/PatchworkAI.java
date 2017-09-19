package comp1140.ass2;

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
        Random r = new Random();
        char lastPatch;
        char[] availablePatches = new char[3];

        for (int i = placement.length() - 1; i >= 0; i--){
            if (placement.charAt(i) != '.'){
                lastPatch = placement.charAt(i-3);
            }
        }
        return placement + ".";
    }
}
