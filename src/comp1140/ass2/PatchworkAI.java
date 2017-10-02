package comp1140.ass2;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;

public class PatchworkAI {
    private final PatchworkGame game;
    private final Player player;

    public PatchworkAI(PatchworkGame game, Player player) {
        this.game = game;
        this.player = player;
    }

    public static void main(String[] args) {
        String circle = "QJfXRIbTcdegKVZSUFWCYGAaBLDEOHPMN";
        String placement = "";
        for (int i = 0; i < 100; i++){
            String movement = generateBestPatchPlacement(circle, placement);
            System.out.println(movement);
            placement += movement;
            System.out.println(placement);
            System.out.println("-----------------------------------------");
        }
        System.out.println(PatchworkGame.isPlacementValid(circle, "LFHBEGHBHADC"));
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
        Tuple players = PatchGame.playersFromGameState(patchCircle, placement);
        boolean player1Turn = (boolean)players.getObjectAt(2);
        Player player;

        if (player1Turn){
            player = (Player)players.getObjectAt(0);
        }
        else{
            player = (Player)players.getObjectAt(1);
        }

        if (player.getTimeSquare() >= 53){
            return "";
        }
        int circlePosition = Game.circlePosFromGameState(patchCircle, placement);
        ArrayList<Patch> patches = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            Patch patch = Patch.valueOf("" + patchCircle.charAt((circlePosition + i) % patchCircle.length()));
            if (patch.getButtonCost() <= player.getButtonsOwned()
                    &&  player.getTimeSquare() + patch.getTimeCost() <= 53){
                patches.add(patch);
            }
        }
        if (patches.isEmpty()){
            return ".";
        }
        Patch patch = patches.get(0);
        for (int i = 1; i < patches.size(); i++){
            if (player.patchValue(patches.get(i)) >  player.patchValue(patch)
                    || (player.patchValue(patches.get(i)) == player.patchValue(patch)
                    &&  patches.get(i).getButtonCost()    <  patch.getButtonCost())){
                patch = patches.get(i);
            }
        }
        String bestLocation = ".";
        int bestScore = 0;
        for (int column = 'A'; column <= 'I'; column++){
            for (int row = 'A'; row <= 'I'; row++){
                for (int rotation = 'A'; rotation <= 'H'; rotation++) {
                    String newPatch = "" + patch.getChar() + (char) column + (char) row + (char) rotation;
                    if (PatchworkGame.isPlacementValid(patchCircle, placement + newPatch)) {
                        int score = placementValue(player, newPatch);
                        if (score > bestScore) {
                            bestScore = score;
                            bestLocation = newPatch;
                        }
                    }
                }
            }
        }
        return bestLocation;
    }

    private static int placementValue(Player player, String patch){
        return player.patchPositionValue(patch);
    }

    public static String generatePatchPlacement(String patchCircle, String placement) {
        return ".";
    }
}
