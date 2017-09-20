package comp1140.ass2;

public class Player {

    private int timeSquare;
    private int buttonsOwned;
    private int buttonIncome;
    private boolean[][] grid;

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
        Patch patch = Patch.valueOf("" + newPatch.charAt(0));
        boolean[][] newGrid = patch.getTransformedGrid(newPatch.charAt(3));
        for (int row = 0; row < newGrid.length; row++){
            for (int col = 0; col < newGrid[0].length; col++){
                if (newGrid[row][col]){
                    grid[row + newPatch.charAt(2) - 'A'][col + newPatch.charAt(1) - 'A'] = newGrid[row][col];
                }
            }
        }
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
