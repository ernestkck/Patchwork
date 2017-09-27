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
    public int patchValue(char newPatch){
        Patch patch = Patch.valueOf("" + newPatch);
        return incomeCollections() * patch.getButtonIncome() - patch.getButtonCost() - patch.getTimeCost() + 2 * patch.getSpacesCovered();
    }
    public int patchValue(Patch patch){
        return incomeCollections() * patch.getButtonIncome() - patch.getButtonCost() - patch.getTimeCost()
                + 2 * patch.getSpacesCovered();
    }
    public void buyPatch(String newPatch){
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));
        updateButtonsOwned(-patch.getButtonCost());
        updateButtonIncome(patch.getButtonIncome());
        for (int i = 0; i < Board.triggeredButtonEvent(getTimeSquare(),getTimeSquare() + patch.getTimeCost()); i++){
            collectIncome();
        }
        updateTimeSquare(patch.getTimeCost());
        updateGrid(newPatch);
    }
    public void advancePlayer(int newTime){
        updateButtonsOwned(Math.min(newTime, 53) - getTimeSquare());
        for (int i = 0; i < Board.triggeredButtonEvent(getTimeSquare(), newTime); i++){
            collectIncome();
        }
        setTimeSquare(Math.min(newTime, 53));
    }
    public void collectIncome(){
        buttonsOwned += getButtonIncome();
    }




}
