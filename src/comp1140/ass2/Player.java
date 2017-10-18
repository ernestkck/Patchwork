package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private int timeSquare;
    private int buttonsOwned;
    private int buttonIncome;
    private boolean[][] grid;
    private int[] hPlacement = new int[3];

    public static int trappedWeighting = 2;
    public static int lineWeighting    = 2;

    public static int trappedOffset    = 5;
    public static int lineOffset       = 0;

    public Player(int timeSquare, int buttonsOwned, int buttonIncome){
        this.timeSquare = timeSquare;
        this.buttonsOwned = buttonsOwned;
        this.buttonIncome = buttonIncome;
        grid = new boolean[9][9];
    }

    public int getTimeSquare(){
        return timeSquare;
    }
    public int getButtonsOwned(){
        return buttonsOwned;
    }
    public int getButtonIncome(){
        return buttonIncome;
    }
    public int getSpaces(){
        int spaces = 0;
        for (boolean[] rows : getGrid()){
            for (boolean square : rows){
                if(!square){
                    spaces++;
                }
            }
        }
        return spaces;
    }
    public int getScore(){
        return getButtonsOwned() - 2 * getSpaces();
    }

    public int[] getHPlacement(){
        hPlacement[2] = -1;
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 9; j++){
                if(!grid[i][j]){
                    boolean newGrid[][] = getGridWithPatch("h" + (char) (j+'A') + (char) (i+'A') + "A");
                    if(isSevenSquare(newGrid)){
                        hPlacement[0] = i;
                        hPlacement[1] = j;
                        return hPlacement;
                    }
                    int adj = 0;
                    int up = Math.max(0, i-1);
                    int down = Math.min(8, i+1);
                    int left = Math.max(0, j-1);
                    int right = Math.min(8, j+1);
                    if(i == 0 || grid[up][j]) adj++;
                    if(i == 8 || grid[down][j]) adj++;
                    if(j == 0 || grid[i][left]) adj++;
                    if(j == 8 || grid[i][right]) adj++;
                    if(adj>hPlacement[2]){
                        hPlacement[0] = i;
                        hPlacement[1] = j;
                        hPlacement[2] = adj;
                        //System.out.println("[1]Found: " + hPlacement[0] + "," + hPlacement[1] + " adj = " + adj);
                    }
                    else if(adj == hPlacement[2]){
                        if(j == 8 || i == 8) {
                            hPlacement[0] = i;
                            hPlacement[1] = j;
                            //System.out.println("[2]Found: " + hPlacement[0] + "," + hPlacement[1] + " adj = " + adj);
                        }
                    }
                }
            }
        }
        if(hPlacement[2] == -1){
            System.out.println("Could not find any tile to place H patch");
        }
        return hPlacement;
    }
    public String getHPlacementAsString(){
        int[] s = getHPlacement();
        return "h" + (char)(s[1]+'A') + (char)(s[0]+'A') + "A";
    }
    public int getIncomeEventsAvailable(){
        int out = 0;
        for (int event : Board.getButtonEvent()){
            if (event > getTimeSquare()){
                out++;
            }
        }
        return out;
    }
    public int getPatchValue(Patch patch){
        return getIncomeEventsAvailable() * patch.getButtonIncome() - patch.getButtonCost() - patch.getTimeCost()
                + 2 * patch.getSpacesCovered();
    }
    public int getPatchPositionValue(String newPatch){
        boolean[][] newGrid = getGridWithPatch(newPatch);
        int out = 0;
        for (int i = 0; i < newGrid.length; i++) {
            int spaces1 = 0;
            int spaces2 = 0;
            for (int j = 0; j < newGrid[0].length; j++) {
                if (!newGrid[i][j]) {
                    int trapped = 0;
                    if (i + 1 >= 9 || newGrid[i + 1][j]){
                        trapped++;
                    }
                    if (i - 1 <  0 || newGrid[i - 1][j]){
                        trapped++;
                    }
                    if (j + 1 >= 9 || newGrid[i][j + 1]){
                        trapped++;
                    }
                    if (j - 1 <  0 || newGrid[i][j - 1]){
                        trapped++;
                    }
                    if (trapped > 0){
                        out -= Math.pow(trappedWeighting, trapped + trappedOffset);
                    }
                    spaces1++;
                }
                else if (newGrid[i][j] || j == newGrid[0].length - 1){
                    out += Math.pow(2, spaces1);
                }

                if (!newGrid[j][i]) {
                    spaces2++;
                }
                else if (newGrid[j][i] || j == newGrid.length - 1){
                    out += Math.pow(lineWeighting, spaces2 + lineOffset);
                }
            }
        }
        return out;
    }

    public boolean[][] getGrid(){
        return grid;
    }
    public boolean[][] getGridWithPatch(String newPatch){
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));
        boolean[][] newGrid = new boolean[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                newGrid[i][j] = grid[i][j];
            }

        }
        boolean[][] patchGrid = patch.getTransformedGrid(newPatch.charAt(3));
        for (int row = 0; row < patchGrid.length; row++){
            for (int col = 0; col < patchGrid[0].length; col++){
                if (patchGrid[row][col]){
                    newGrid[row + newPatch.charAt(2) - 'A'][col + newPatch.charAt(1) - 'A'] = patchGrid[row][col];
                }
            }
        }
        return newGrid;
    }
    public String getGridAsString(){
        String out = "";
        for (boolean[] i : grid){
            for (boolean j : i){
                if (j){
                    out += '#';
                }
                else{
                    out += '.';
                }
            }
            out += '\n';
        }
        return out;
    }

    public void setButtonsOwned(int buttons){
        buttonsOwned = buttons;
    }
    public void setTimeSquare(int time){
        timeSquare = time;
    }
    public void setButtonIncome(int income){
        buttonIncome = income;
    }

    public void updateButtonsOwned(int buttons){
        buttonsOwned += buttons;
    }
    public void updateTimeSquare(int time){
        timeSquare += time;
    }
    public void updateButtonIncome(int income){
        buttonIncome += income;
    }
    public void updateGrid(String newPatch){
        grid = getGridWithPatch(newPatch);
    }

    public String generatePatchPlacement(){
        if (getTimeSquare() >= 53){
            return "";
        }

        ArrayList<Patch> patches = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            Patch patch = Patch.valueOf("" + PatchGame.patchCircle.charAt((PatchGame.neutralToken + i) % PatchGame.patchCircle.length()));
            if (patch.getButtonCost() <= getButtonsOwned() && getPatchValue(patch) > 0){
                patches.add(patch);
            }
        }
        if (patches.isEmpty()){
            return ".";
        }

        patches.sort((a, b) -> getPatchValue(b) - getPatchValue(a) != 0 ? getPatchValue(b) - getPatchValue(a) : a.getButtonCost() - b.getButtonCost());
        String bestLocation = "";
        int bestScore = -10000;
        for (Patch patch : patches){
            for (int column = 'A'; column <= 'I'; column++){
                for (int row = 'A'; row <= 'I'; row++){
                    for (int rotation = 'A'; rotation <= 'H'; rotation++) {
                        String newPatch = "" + patch.getChar() + (char) column + (char) row + (char) rotation;
                        if (isPlacementValid(newPatch)) {
                            if(isSevenSquare(getGridWithPatch(newPatch))){
                                bestLocation = newPatch;
                                return bestLocation;
                            }
                            int score = getPatchPositionValue(newPatch);
                            if (score > bestScore) {
                                bestScore = score;
                                bestLocation = newPatch;
                            }
                        }
                    }
                }
            }
            if (!bestLocation.equals("")){
                return bestLocation;
            }
        }
        return ".";
    }

    public void buyPatch(String newPatch){
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));

        updateButtonsOwned(-patch.getButtonCost());
        updateButtonIncome(patch.getButtonIncome());
        if (Board.triggeredButtonEvent(getTimeSquare(), getTimeSquare() + patch.getTimeCost())){
            collectIncome();
        }
        updateGrid(newPatch);
        PatchGame.placement += newPatch;
        if (patch.getChar() != 'h'){
            PatchGame.neutralToken = PatchGame.patchCircle.indexOf(patch.getChar());
            PatchGame.patchCircle = PatchGame.patchCircle.replace("" + patch.getChar(), "");
            if(Board.triggeredPatchEvent(getTimeSquare(), getTimeSquare()+patch.getTimeCost())) {
                PatchGame.placement += getHPlacementAsString();
                placeHPatch();
            }
        }
        updateTimeSquare(patch.getTimeCost());


    }
    public void advancePlayer(int newTime){
        updateButtonsOwned(Math.min(newTime, 53) - getTimeSquare());
        if (Board.triggeredButtonEvent(getTimeSquare(), newTime)){
            collectIncome();
        }
        PatchGame.placement += '.';
        setTimeSquare(Math.min(newTime, 53));

        if(Board.triggeredPatchEvent(getTimeSquare(), newTime)) {
            PatchGame.placement += getHPlacementAsString();
            placeHPatch();
        }
    }
    public void collectIncome(){
        buttonsOwned += getButtonIncome();
    }
    public void placeHPatch(){
        int[] coord = getHPlacement();
        //System.out.println("Placing h patch on " + coord[0] + "," + coord[1]);
        grid[coord[0]][coord[1]] = true;
    }

    public boolean isPlacementValid(String newPatch){
        if (newPatch == null || newPatch.equals("") || PatchGame.patchCircle == null || PatchGame.patchCircle.equals("")){
            //System.out.println("Some necessary data was left empty");
            return false;
        }

        if (!PatchworkGame.isPlacementWellFormed(PatchGame.placement + newPatch)){
            //System.out.println("The game would not be well formed if this patch is added");
            return false;
        }


        Patch patch = Patch.valueOf("" + newPatch.charAt(0));

        if (getButtonsOwned() < patch.getButtonCost()){
            //System.out.println("Player does not own enough buttons to buy this patch");
            return false;
        }

        if (patch.getChar() != 'h'){
            boolean isAvailablePatch = false;
            for (int i = 0; i < 3; i++){
                if (PatchGame.patchCircle.charAt((PatchGame.neutralToken + i) % PatchGame.patchCircle.length()) == patch.getChar()){
                    isAvailablePatch = true;
                    break;
                }
            }
            if (!isAvailablePatch){
                //System.out.println("The patch chosen was not one of the available patches");
                return false;
            }
        }

        boolean[][] patchGrid = patch.getTransformedGrid(newPatch.charAt(3));

        if (patchGrid.length    + newPatch.charAt(2) - 'A' > grid.length
                ||  patchGrid[0].length + newPatch.charAt(1) - 'A' > grid[0].length){
            //System.out.println("The position the patch was added would render it off the grid");
            return false;
        }

        for (int row = 0; row < patchGrid.length; row++){
            for (int column = 0; column < patchGrid[0].length; column++){
                if (patchGrid[row][column]) {
                    int playerRow = row + newPatch.charAt(2) - 'A';
                    int playerCol = column + newPatch.charAt(1) - 'A';

                    if (grid[playerRow][playerCol]) {
                        //System.out.println("The patch would overlap the player's grid");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean isSevenSquare(boolean[][] grid){
        if(Game.getSpecialTile()) return false;
        int[] consec, first, last;
        int consecRows = 0;
        consec = new int[9];
        first = new int[9];
        last = new int[9];
        for(int i=0; i<9; i++){
            first[i] = 9;
            last[i] = -1;
            consec[i] = 0;
            for(int j=0; j<9; j++) {
                if (grid[i][j]) {
                    consec[i]++;
                    first[i] = Math.min(first[i], j);
                    last[i] = Math.max(last[i], j);
                } else {
                    if (consec[i] < 7) {
                        consec[i] = 0;
                        first[i] = 9;
                        last[i] = -1;
                    }
                }
            }
            if(consec[i]>=7){
                consecRows++;
            }
            if(consec[i]<7 && consecRows<7){
                consecRows = 0;
                if(i>=2) return false;
            }
        }
        int max = Arrays.stream(first).filter(x -> x<9).max().getAsInt();
        int min = Arrays.stream(last).filter(x -> x>-1).min().getAsInt();
        if(min - max + 1 >= 7){
            return true;
        }
        return false;
        // update buttons 7
        // setSpecialTile();
    }
}
