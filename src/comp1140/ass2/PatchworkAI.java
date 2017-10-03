package comp1140.ass2;

import java.util.ArrayList;

public class PatchworkAI {
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
    // Generate the first valid move found without checking for values
    public static String generatePatchPlacement(String patchCircle, String placement) {

        Player player1 = new Player(0, 5, 0);
        Player player2 = new Player(0, 5, 0);

        boolean player1Turn = true;
        boolean player1OldTurn = true;
        Player currentPlayer = player1;
        int position = 0, neutralToken = -1;
        char lastPatch;
        ArrayList<Patch> patches = new ArrayList<>();
        String availablePatches = "";

        while (position < placement.length()){
            if (placement.charAt(position) == '.'){
                if (player1Turn){
                    player1.advancePlayer(player2.getTimeSquare() + 1);
                    currentPlayer = player2;
                }
                else{
                    player2.advancePlayer(player1.getTimeSquare() + 1);
                    currentPlayer = player1;
                }
                player1OldTurn = player1Turn;
                player1Turn = !player1Turn;
                position++;
            }
            else{
                String patch = placement.substring(position, position + 4);
                neutralToken = patchCircle.indexOf(patch.substring(0,1));
                patchCircle = patchCircle.replace(patch.substring(0,1), "");
                if ((player1Turn && placement.charAt(position) != 'h') || (player1OldTurn && placement.charAt(position) == 'h')){
                    player1.buyPatch(patch);
                    if (player1.getTimeSquare() > player2.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = false;
                        currentPlayer = player2;
                    }
                }
                else{
                    player2.buyPatch(patch);
                    if (player2.getTimeSquare() > player1.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = true;
                        currentPlayer = player1;
                    }
                }
                position += 4;
            }
        }
        if(patchCircle == "") return null;
        if(player1.getTimeSquare() == 53 || player2.getTimeSquare() == 53) return null;

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
        System.out.println("patchCircle: " +patchCircle);
        System.out.println("placement: " + placement);
        //System.out.println("neutralToken " +neutralToken +": " + patchCircle.charAt(neutralToken));

        for (int i = 0; i < 3; i++){
            availablePatches = "" + patchCircle.charAt((neutralToken + i)%patchCircle.length());
            Patch patch = Patch.valueOf(availablePatches);
            if (patch.getButtonCost() <= currentPlayer.getButtonsOwned()){
                patches.add(patch);
            }
        }
        if(patches.isEmpty()) return ".";
        System.out.println(patches.toString());
        for (Patch patch : patches){
            for (int column = 'A'; column <= 'I'; column++){
                for (int row = 'A'; row <= 'I'; row++){
                    for (int rotation = 'A'; rotation <= 'H'; rotation++) {
                        String newPatch = "" + patch.getChar() + (char) column + (char) row + (char) rotation;
                        if (currentPlayer.isPlacementValid(newPatch)) {
                            return newPatch;
                        }
                    }
                }
            }
        }
        return ".";
    }
}
