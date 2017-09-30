package comp1140.ass2;

public class Player {

    private int timeSquare;
    private int buttonsOwned;
    private int buttonIncome;
    public boolean[][] grid;

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
    public boolean[][] getGrid(){
        return grid;
    }
    public boolean[][] getGridWithPatch(String newPatch){
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));
        boolean[][] newGrid = grid;
        boolean[][] patchGrid = patch.getTransformedGrid(newPatch.charAt(3));
        for (int row = 0; row < patchGrid.length; row++){
            for (int col = 0; col < patchGrid[0].length; col++){
                if (patchGrid[row][col]){
                    grid[row + newPatch.charAt(2) - 'A'][col + newPatch.charAt(1) - 'A'] = patchGrid[row][col];
                }
            }
        }
        return newGrid;
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

    private int incomeCollections(){
        int out = 0;
        for (int event : Board.getButtonEvent()){
            if (event > getTimeSquare()){
                out++;
            }
        }
        return out;
    }
    public int patchValue(Patch patch){
        return incomeCollections() * patch.getButtonIncome() - patch.getButtonCost() - patch.getTimeCost()
                + 2 * patch.getSpacesCovered();
    }

    public boolean placementValid(String newPatch){
        if (newPatch == null || newPatch.equals("") || Game.patchCircle == null || Game.patchCircle.equals("")){
            System.out.println("Some necessary data was left empty");
            return false;
        }

        if (!PatchworkGame.isPlacementWellFormed(Game.placement + newPatch)){
            System.out.println("The game would not be well formed if this patch is added");
            return false;
        }

        Patch patch = Patch.valueOf("" + newPatch.charAt(0));

        if (getButtonsOwned() < patch.getButtonCost()){
            System.out.println("Player does not own enough buttons to buy this patch");
            return false;
        }

        if (patch.getChar() != 'h'){
            boolean isAvailablePatch = false;
            for (int i = 0; i < 3; i++){
                if (Game.patchCircle.charAt((Game.neutralToken + i) % Game.patchCircle.length()) == patch.getChar()){
                    isAvailablePatch = true;
                    break;
                }
            }
            if (!isAvailablePatch){
                System.out.println("The patch chosen was not one of the available patches");
                return false;
            }
        }

        boolean[][] patchGrid = patch.getTransformedGrid(newPatch.charAt(3));

        if (patchGrid.length    + newPatch.charAt(2) - 'A' > grid.length
        ||  patchGrid[0].length + newPatch.charAt(1) - 'A' > grid[0].length){
            System.out.println("The position the patch was added would render it off the grid");
            return false;
        }

        boolean adjacent = false;
        for (int row = 0; row < patchGrid.length; row++){
            for (int column = 0; column < patchGrid[0].length; column++){
                if (patchGrid[row][column]) {
                    int playerRow = row + newPatch.charAt(2) - 'A';
                    int playerCol = column + newPatch.charAt(1) - 'A';

                    if (grid[playerRow][playerCol]) {
                        System.out.println("The patch would overlap the player's grid");
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
            System.out.println("The patch added was not adjacent to any previous patch");
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
        updateTimeSquare(patch.getTimeCost());
        updateGrid(newPatch);

        Game.placement += newPatch;
        Game.neutralToken = Game.patchCircle.indexOf(patch.getChar());
        Game.patchCircle = Game.patchCircle.replace("" + patch.getChar(), "");
    }
    public void advancePlayer(int newTime){
        updateButtonsOwned(Math.min(newTime, 54) - getTimeSquare());
        if (Board.triggeredButtonEvent(getTimeSquare(), newTime)){
            collectIncome();
        }
        setTimeSquare(Math.min(newTime, 54));

        Game.placement += '.';
    }
    public void collectIncome(){
        buttonsOwned += getButtonIncome();
    }




}
