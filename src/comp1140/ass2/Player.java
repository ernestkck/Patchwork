package comp1140.ass2;

public class Player {

    int timeSquare;
    int buttonsOwned;
    int buttonIncome;
    int spaces;
    boolean[][] grid;

    public Player(int timeSquare, int buttonsOwned, int buttonIncome){
        this.timeSquare = timeSquare;
        this.buttonsOwned = buttonsOwned;
        this.buttonIncome = buttonIncome;
        this.spaces = 81;
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
        return spaces;
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
    public void setSpaces(int newspaces) {
        spaces = newspaces;
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
    public void updateSpaces(int change) {
        spaces -= change;
    }
    public void buyPatch(Patch newPatch){
        updateButtonsOwned(-newPatch.getButtonCost());
        updateTimeSquare(newPatch.getTimeCost());
        updateButtonIncome(newPatch.getButtonIncome());
    }
    public void collectIncome(){
        buttonsOwned += buttonIncome;
    }
    public boolean updateGrid(String patchString){
        return false;
    }
    public boolean[][] getGrid(){ return grid;}
}
