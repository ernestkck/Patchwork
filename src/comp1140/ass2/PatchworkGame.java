package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {

    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {
    }

    /**
     * Determine whether a patch placement is well-formed according to the following:
     * - either it is the single character string ".", or
     * - it consists of exactly four characters:
     * - the first character is in the range A .. Z or a .. h
     * - the second character is in the range A .. I
     * - the third character is in the range A .. I
     * - the fourth character is in the range A .. H
     *
     * @param placement A string describing a patch placement
     * @return True if the tile placement is well-formed
     */
    static boolean isPatchPlacementWellFormed(String placement) {
        if(placement.equals(".")) return true;
        if(placement.length() != 4) return false;
        char[] c = placement.toCharArray();
        if(c[0] < 'A' || (c[0] > 'Z' && c[0] < 'a') || c[0] > 'h') return false;
        if(c[1] < 'A' || c[1] >'I') return false;
        if(c[2] < 'A' || c[2] >'I') return false;
        if(c[3] < 'A' || c[3] >'H') return false;
        return true;
    }

    /**
     * Determine whether a game placement string is well-formed:
     * - it consists of a sequence of patch placement strings, where
     * - each patch placement is well-formed, either as a single-character advance string "."
     * or a four-character patch tile placement
     * - no patch appears more than once in the placement, except the special tile 'h'
     *
     * @param placement A string describing a placement of one or more tiles
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {

        if (isEmpty(placement)) return false;

        String filtered = placement.replace(".","");

        if (filtered.length() % 4 == 0){
            String patch = "";
            String placements = "";
            int hCount = 0;
            for (int i = 0; i < filtered.length(); i++){
                patch += filtered.charAt(i);
                if ((i + 1) % 4 == 0){
                    for (char chr : placements.toCharArray()){
                        if (chr == patch.charAt(0)){
                            return false;
                        }
                    }

                    if (!isPatchPlacementWellFormed(patch) || (patch.charAt(0) == 'h' && hCount >= 5)){
                        return false;
                    }

                    if (patch.charAt(0) != 'h'){
                        placements += patch.charAt(0);
                    }
                    else{
                        hCount += 1;
                    }
                    patch = "";
                }

            }
        }
        else{
            return false;
        }
        return true;
    }

    static boolean isEmpty(String s){
        if(s != null && s.length() != 0) return false;
        return true;
    }
    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each tile placement must follow the game's placement rules.
     *
     * @param patchCircle a patch circle string
     * @param placement   A placement string
     * @return true if the placement is valid
     */
    static boolean isPlacementValid(String patchCircle, String placement) {
        if(isEmpty(patchCircle) || isEmpty(placement)) {
            System.out.println("isEmpty");
            return false;
        }
        if (!isPlacementWellFormed(placement)) {
            System.out.println("not well formed");
            return false;
        }
        char[] patchArray = patchCircle.toCharArray();
        char[] placementArray = placement.toCharArray();
        Player playerA = new Player(0,0,0);
        Player playerB = new Player(0,0,0);
        boolean turn = false;
        boolean previousTurn = false;
        boolean[][] locationGrid;
        for (int i = 0; i< placementArray.length; i++){
            if (placementArray[i] == '.'){
                if (turn){
                    playerB.setTimeSquare(playerA.getTimeSquare()+1);
                }
                else{
                    playerA.setTimeSquare(playerB.getTimeSquare()+1);
                }
            }
            else {
                if (turn){
                    playerB.updateTimeSquare(Patch.valueOf("" + placementArray[i]).getTimeCost());
                }
                else{
                    playerA.updateTimeSquare(Patch.valueOf("" + placementArray[i]).getTimeCost());
                }
                locationGrid = Patch.valueOf("" + placementArray[i]).getLocationGrid();
                if ((placementArray[i+3]-65) / 4 == 1) {
                    boolean[][] tempGrid = new boolean[locationGrid.length][locationGrid[0].length];
                    for (int a = 0; a < locationGrid.length; a++){
                        for (int b = 0; b < locationGrid[0].length; b++){
                            tempGrid[a][b] = locationGrid[a][locationGrid[0].length-b-1];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if ((placementArray[i+3]-65)%2==1){
                    boolean[][] tempGrid = new boolean[locationGrid[0].length][locationGrid.length];
                    for (int row = 0; row < locationGrid.length; row++){
                        for (int col = 0; col < locationGrid[0].length; col++){
                            tempGrid[col][row] = locationGrid[locationGrid.length - 1 - row][col];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if (((placementArray[i+3]-65) / 2) % 2 == 1) {
                    boolean[][] tempGrid = new boolean[locationGrid.length][locationGrid[0].length];
                    for (int a = 0; a < locationGrid.length; a++){
                        for (int b = 0; b < locationGrid[0].length; b++){
                            tempGrid[a][b] = locationGrid[locationGrid.length-1-a][locationGrid[0].length-1-b];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if (placementArray[i] == 'h') turn = previousTurn;
                if (turn) {
                    for (int a = 0; a < locationGrid.length; a++) {
                        for (int b = 0; b < locationGrid[0].length; b++) {
                            if (locationGrid[a][b]){
                                if (playerB.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] && locationGrid[a][b]) {
                                    System.out.println("Grid B overlap");
                                    return false;
                                }
                                playerB.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] = locationGrid[a][b];
                            }
                        }
                    }
                }
                else {
                    for (int a = 0; a < locationGrid.length; a++) {
                        for (int b = 0; b < locationGrid[0].length; b++) {
                            if (locationGrid[a][b]){
                                if (playerA.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] && locationGrid[a][b]) {
                                    System.out.println("Grid A overlap");
                                    return false;
                                }
                                playerA.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] = locationGrid[a][b];
                            }
                        }
                    }
                }
                i+=3;
            }
            previousTurn = turn;
            if (playerA.getTimeSquare() > playerB.getTimeSquare()){
                turn = true;
            }
            else if (playerB.getTimeSquare() > playerA.getTimeSquare()){
                turn = false;
            }
        }
        return true;
    }

    /**
     * Determine the score for a player given a placement, following the
     * scoring rules for the game.
     *
     * @param placement   A placement string
     * @param firstPlayer True if the score for the first player is requested,
     *                    otherwise the score for the second player should be returned
     * @return the score for the requested player, given the placement
     */
    static int getScoreForPlacement(String patchCircle, String placement, boolean firstPlayer) {
        char[] placementArray = placement.toCharArray();
        Player playerA = new Player(0,5,0);
        Player playerB = new Player(0,5,0);
        boolean turn = false;
        boolean previousTurn = false;
        int previousTime;
        boolean[][] locationGrid;
        Board events = new Board();
        System.out.println(Arrays.toString(events.buttonEvent));
        for (int i = 0; i< placementArray.length; i++){
            if (placementArray[i] == '.'){
                if (turn){
                    previousTime = playerB.getTimeSquare();
                    playerB.setTimeSquare(playerA.getTimeSquare()+1);
                    playerB.updateButtonsOwned(playerB.getTimeSquare()-previousTime);
                }
                else{
                    previousTime = playerA.getTimeSquare();
                    playerA.setTimeSquare(playerB.getTimeSquare()+1);
                    playerA.updateButtonsOwned(playerB.getTimeSquare()-previousTime);
                }
            }
            else {
                if (turn){
                    previousTime = playerA.getTimeSquare();
                    playerB.buyPatch(Patch.valueOf("" + placementArray[i]));
                    if (events.triggeredButtonEvent(previousTime, playerB.getTimeSquare())){
                            playerB.collectIncome();
                            System.out.println("player b collected " + playerB.getButtonIncome() + " after going from " + previousTime + " to " + playerB.getTimeSquare());
                    }
                    System.out.println(playerB.getButtonsOwned());
                }
                else{
                    previousTime = playerB.getTimeSquare();
                    playerA.buyPatch(Patch.valueOf("" + placementArray[i]));
                    if (events.triggeredButtonEvent(previousTime, playerA.getTimeSquare())){
                        playerA.collectIncome();
                        System.out.println("player a collected " + playerA.getButtonIncome()+ " after going from " + previousTime + " to " + playerA.getTimeSquare());
                    }
                    System.out.println(playerA.getButtonsOwned());
                }
                locationGrid = Patch.valueOf("" + placementArray[i]).getLocationGrid();
                if ((placementArray[i+3]-65) / 4 == 1) {
                    boolean[][] tempGrid = new boolean[locationGrid.length][locationGrid[0].length];
                    for (int a = 0; a < locationGrid.length; a++){
                        for (int b = 0; b < locationGrid[0].length; b++){
                            tempGrid[a][b] = locationGrid[a][locationGrid[0].length-b-1];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if ((placementArray[i+3]-65)%2==1){
                    boolean[][] tempGrid = new boolean[locationGrid[0].length][locationGrid.length];
                    for (int row = 0; row < locationGrid.length; row++){
                        for (int col = 0; col < locationGrid[0].length; col++){
                            tempGrid[col][row] = locationGrid[locationGrid.length - 1 - row][col];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if (((placementArray[i+3]-65) / 2) % 2 == 1) {
                    boolean[][] tempGrid = new boolean[locationGrid.length][locationGrid[0].length];
                    for (int a = 0; a < locationGrid.length; a++){
                        for (int b = 0; b < locationGrid[0].length; b++){
                            tempGrid[a][b] = locationGrid[locationGrid.length-1-a][locationGrid[0].length-1-b];
                        }
                    }
                    locationGrid = tempGrid;
                }
                if (placementArray[i] == 'h') turn = previousTurn;
                if (turn) {
                    for (int a = 0; a < locationGrid.length; a++) {
                        for (int b = 0; b < locationGrid[0].length; b++) {
                            if (locationGrid[a][b]){
                                playerB.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] = locationGrid[a][b];
                            }
                        }
                    }
                }
                else {
                    for (int a = 0; a < locationGrid.length; a++) {
                        for (int b = 0; b < locationGrid[0].length; b++) {
                            if (locationGrid[a][b]){
                                playerA.getGrid()[a+placementArray[i+2]-65][b+placementArray[i+1]-65] = locationGrid[a][b];
                            }
                        }
                    }
                }
                i+=3;
            }
            previousTurn = turn;
            if (playerA.getTimeSquare() > playerB.getTimeSquare()){
                turn = true;
            }
            else if (playerB.getTimeSquare() > playerA.getTimeSquare()){
                turn = false;
            }
        }
        int blankSpace = 0;
        if (firstPlayer){
            for (boolean[] row: playerA.getGrid()){
                for (boolean column: row){
                    if (!column){
                        blankSpace += 2;
                    }
                }
            }
            System.out.println(playerA.getButtonsOwned() - blankSpace);
            return playerA.getButtonsOwned() - blankSpace;
        }
        for (boolean[] row: playerB.getGrid()){
            for (boolean column: row){
                if (!column){
                    blankSpace += 2;
                }
            }
        }
        System.out.println(playerB.getButtonsOwned() - blankSpace);
        return playerB.getButtonsOwned() - blankSpace;
    }

}
