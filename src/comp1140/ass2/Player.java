package comp1140.ass2;

public class Player {

    int timeSquare;
    int buttonsOwned;
    int buttonIncome;
    boolean[][] grid;

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
    public void buyPatch(patch.Patch newPatch){
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
}
