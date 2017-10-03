package comp1140.ass2;

import java.util.ArrayList;

public class Player {

    private int timeSquare;
    private int buttonsOwned;
    private int buttonIncome;
    private boolean[][] grid;
    private int[] hPlacement = new int[3];

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

    public int[] getHPlacement(){
        hPlacement[2] = -1;
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 9; j++){
                if(!grid[i][j]){
                    int adj = 0;
                    int up = Math.max(0, i-1);
                    int down = Math.min(8, i+1);
                    int left = Math.max(0, j-1);
                    int right = Math.min(8, j+1);
                    if(grid[up][j]) adj++;
                    if(grid[down][j]) adj++;
                    if(grid[i][left]) adj++;
                    if(grid[i][right]) adj++;
                    if(adj>hPlacement[2]){
                        hPlacement[0] = i;
                        hPlacement[1] = j;
                        hPlacement[2] = adj;
                    }
                }
            }
        }
        if(hPlacement[2] == -1){
            System.out.println("Could not find any tile to place H patch");
        }
        else{
            System.out.println("Placing H patch on " + hPlacement[0] + "," + hPlacement[1]);
        }
        return hPlacement;
    }
    public int getScore(){
        return getButtonsOwned() - 2 * getSpaces();
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
                    spaces1++;
                }
                else if (newGrid[i][j] || j == newGrid[0].length - 1){
                    out += Math.pow(2, spaces1);
                }

                if (!newGrid[j][i]) {
                    spaces2++;
                }
                else if (newGrid[j][i] || j == newGrid.length - 1){
                    out += Math.pow(2, spaces2);
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
        int bestScore = 0;
        for (Patch patch : patches){
            for (int column = 'A'; column <= 'I'; column++){
                for (int row = 'A'; row <= 'I'; row++){
                    for (int rotation = 'A'; rotation <= 'H'; rotation++) {
                        String newPatch = "" + patch.getChar() + (char) column + (char) row + (char) rotation;
                        if (isPlacementValid(newPatch)) {
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

        boolean adjacent = false;
        for (int row = 0; row < patchGrid.length; row++){
            for (int column = 0; column < patchGrid[0].length; column++){
                if (patchGrid[row][column]) {
                    int playerRow = row + newPatch.charAt(2) - 'A';
                    int playerCol = column + newPatch.charAt(1) - 'A';

                    if (grid[playerRow][playerCol]) {
                        //System.out.println("The patch would overlap the player's grid");
                        return false;
                    }

                    if ((playerRow + 1 <  9 && grid[playerRow + 1][playerCol])
                    ||  (playerRow - 1 >= 0 && grid[playerRow - 1][playerCol])
                    ||  (playerCol + 1 <  9 && grid[playerRow][playerCol + 1])
                    ||  (playerCol - 1 >= 0 && grid[playerRow][playerCol - 1])){
                        adjacent = true;
                    }
                }
            }
        }
        if (!adjacent && getTimeSquare() != 0){
            //System.out.println("The patch added was not adjacent to any previous patch");
            return false;
        }

        return true;
    }

    public void buyPatch(String newPatch){
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));

        updateButtonsOwned(-patch.getButtonCost());
        updateButtonIncome(patch.getButtonIncome());
        if (Board.triggeredButtonEvent(getTimeSquare(), getTimeSquare() + patch.getTimeCost())){
            collectIncome();
        }
        PatchGame.placement += newPatch;
        PatchGame.neutralToken = PatchGame.patchCircle.indexOf(patch.getChar());
        PatchGame.patchCircle = PatchGame.patchCircle.replace("" + patch.getChar(), "");
        placeHPatch(getTimeSquare() + patch.getTimeCost());
        updateTimeSquare(patch.getTimeCost());
        updateGrid(newPatch);


    }
    public void advancePlayer(int newTime){
        updateButtonsOwned(Math.min(newTime, 53) - getTimeSquare());
        if (Board.triggeredButtonEvent(getTimeSquare(), newTime)){
            collectIncome();
        }
        placeHPatch(newTime);
        setTimeSquare(Math.min(newTime, 53));

        PatchGame.placement += '.';
    }
    public void collectIncome(){
        buttonsOwned += getButtonIncome();
    }
    public void placeHPatch(int newTime){
        if(Board.triggeredPatchEvent(getTimeSquare(), newTime)){
            int[] coord = getHPlacement();
            grid[coord[0]][coord[1]] = true;
            coord[0] += 'A';
            coord[1] += 'A';
            PatchGame.placement += "h" + (char) coord[1] + (char) coord[0] + "A";
        }
    }



}
