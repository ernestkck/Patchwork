package comp1140.ass2;

import java.util.ArrayList;

public class PatchworkAI {      //Authors: Lachlan Grainger (u5847571), Ernest Kwan (u6381103)
    private final PatchworkGame game;
    private final Player player;

    public PatchworkAI(PatchworkGame game, Player player) {
        this.game = game;
        this.player = player;
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

        return player.generatePatchPlacement();
    }

    /**
     * Generate a valid move that follows from the given patch circle and game placement string.
     * If the game is already finished, return null.
     * @param patchCircle a patch circle string to initialize the game
     * @param placement  A valid placement string indicating a game state
     * @return a valid patch placement string, which will be "." if the player chooses to advance
     */
    // Generate the first valid move found without checking for patch position values and h tiles
    public static String generatePatchPlacement(String patchCircle, String placement) {
        Tuple players = PatchGame.playersFromGameState(patchCircle, placement);
        Player currentPlayer, player1, player2;
        player1 = (Player) players.getObjectAt(0);
        player2 = (Player) players.getObjectAt(1);
        if((boolean)players.getObjectAt(2)) currentPlayer = player1;
        else    currentPlayer = player2;
        patchCircle = (String) players.getObjectAt(3);
        int neutralToken = (int)players.getObjectAt(4);

        if(patchCircle == "") return null;
        if(player1.getTimeSquare() == 53 || player2.getTimeSquare() == 53) return null;

        char lastPatch;
        if(placement.isEmpty() || neutralToken == -1) {
            lastPatch = 'A';
            neutralToken = patchCircle.indexOf('A') + 1;
        }
        else {
            int i = placement.length() - 1;
            while (i>0 && placement.charAt(i) == '.') {
                i--;
                if (i > 3 && placement.charAt(i - 3) == 'h') {
                    i -= 4;
                }
            }
            lastPatch = placement.charAt(i - 3);
        }
        //System.out.println("last patch used: " + lastPatch);
        //System.out.println("patchCircle: " +patchCircle);
        //System.out.println("placement: " + placement);

        ArrayList<Patch> patches = new ArrayList<>();
        String availablePatches = "";
        for (int i = 0; i < 3; i++){
            availablePatches = "" + patchCircle.charAt((neutralToken + i)%patchCircle.length());
            Patch patch = Patch.valueOf(availablePatches);
            if (patch.getButtonCost() <= currentPlayer.getButtonsOwned()){
                patches.add(patch);
            }
        }
        if(patches.isEmpty()) return ".";   // not enough buttons to buy
        //System.out.println(patches.toString());
        for (Patch patch : patches){
            for (int column = 'A'; column <= 'I'; column++){
                for (int row = 'A'; row <= 'I'; row++){
                    for (int rotation = 'A'; rotation <= 'H'; rotation++) {
                        String newPatch = "" + patch.getChar() + (char) column + (char) row + (char) rotation;
                        if (PatchworkGame.isPlacementValid(patchCircle, placement+newPatch)) {
                            return newPatch;
                        }
                    }
                }
            }
        }
        return "."; // no valid placements
    }
}
