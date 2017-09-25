package comp1140.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Game extends Application{ //this class contains the main method that runs the game, it will also be the GUI

    static Player playerA = new Player(0,5,0);
    static Player playerB = new Player(0,5,0);
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;
    private static final String PATCH_CIRCLE = "STUVWXYZabcdeKLMNOPQRfgABCDEFGHIJ";
    private int neutral_token = 12;
    private static final String URI_BASE = "assets/";
    private ArrayList<guiPatch> patchList = new ArrayList();
    private Text buttonsA = new Text("Buttons: " + playerA.getButtonsOwned());
    private Text buttonsB = new Text("Buttons: " + playerB.getButtonsOwned());
    private Text incomeA = new Text("Income: " + playerA.getButtonIncome());
    private Text incomeB = new Text("Income: " + playerB.getButtonIncome());
    private boolean specialTile = false;

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        makeBoard();
        makePatchCircle();
        setDraggable();
        setButtons();
        Circle test = new Circle();
        test.setRadius(2);
        test.setLayoutX(240+225);
        test.setLayoutY(325);
        root.getChildren().add(test);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void isSevenSquare(Player player){
        if(specialTile) return;
        boolean[][] grid = player.getGrid();
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
                    last[i] = Math.max(first[i], j);
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
            if(consec[i]<7){
                consecRows = 0;
                if(i>=2) return;
            }
        }
        int max = Arrays.stream(first).max().getAsInt();
        int min = Arrays.stream(last).min().getAsInt();
        if(min - max >= 7){
            player.updateButtonIncome(7);
            specialTile = true;
        }
    }
    public void makePatchCircle(){
        double width = 0;
        double height = 0;
        double currentX = 10;
        double currentY = 10;
        double prevWidth = 0;
        double prevHeight = 0;
        int stage = 0;
        for (char t: PATCH_CIRCLE.toCharArray()){
            patchList.add(new guiPatch(t));
            width = patchList.get(patchList.size()-1).getWidth();
            height = patchList.get(patchList.size()-1).getHeight();
            if (patchList.size() == 1) {
                patchList.get(patchList.size()-1).setX(10);
                patchList.get(patchList.size()-1).setY(10);
                patchList.get(patchList.size()-1).anchor();
                stage = 1;
            }
            else if (currentX+prevWidth+width+10 < 933 && stage == 1){
                currentX += prevWidth+10;
                patchList.get(patchList.size()-1).setX(currentX);
                patchList.get(patchList.size()-1).setY(10);
                patchList.get(patchList.size()-1).anchor();
            }
            else if (stage == 1){
                stage = 2;
                currentY += prevHeight+10;
                patchList.get(patchList.size()-1).setX(933-width-10);
                patchList.get(patchList.size()-1).setY(currentY);
                patchList.get(patchList.size()-1).anchor();
            }
            else if (currentY+prevHeight+height+10 < 700 && stage == 2){
                currentY += prevHeight+10;
                patchList.get(patchList.size()-1).setX(933-width-10);
                patchList.get(patchList.size()-1).setY(currentY);
                patchList.get(patchList.size()-1).anchor();
            }
            else if (stage == 2){
                stage = 3;
                currentX = patchList.get(patchList.size()-2).getX() - prevWidth-10;
                patchList.get(patchList.size()-1).setX(currentX);
                patchList.get(patchList.size()-1).setY(700-height-10);
            }
            else if (currentX-width-10 > 0 && stage == 3) {
                currentX -= width+10;
                patchList.get(patchList.size()-1).setX(currentX);
                patchList.get(patchList.size()-1).setY(700-height-10);
            }
            else {
                stage = 4;
                currentY = patchList.get(patchList.size()-2).getY() - height - 10;
                patchList.get(patchList.size() - 1).setX(10);
                patchList.get(patchList.size() - 1).setY(currentY);
                patchList.get(patchList.size()-1).anchor();
            }
            prevWidth = patchList.get(patchList.size() - 1).getWidth();
            prevHeight = patchList.get(patchList.size() - 1).getHeight();
        }
        root.getChildren().addAll(patchList);
    }
    public void makeBoard(){
        ImageView bg = new ImageView(new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + "grid.png")));
        ImageView tb = new ImageView(new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + "timeBoard.png")));
        bg.setPreserveRatio(true);
        bg.setFitWidth(933 * 0.5);
        bg.setX((933 - 933*0.5)/2);
        bg.setY(275);
        tb.setPreserveRatio(true);
        tb.setFitWidth(290 * 0.7);
        tb.setX((933-(290*0.7))/2);
        tb.setY(110);
        root.getChildren().addAll(bg, tb);
    }
    public void setDraggable(){
        for (int i = 0; i < patchList.size(); i++){
            patchList.get(i).setDraggable(false);
        }
        patchList.get(neutral_token).setDraggable(true);
        if (neutral_token-1 < 0){
            patchList.get(patchList.size()-1).setDraggable(true);
        }
        else {
            patchList.get(neutral_token-1).setDraggable(true);
        }
        if (neutral_token+1 == patchList.size()){
            patchList.get(0).setDraggable(true);
        }
        else {
            patchList.get(neutral_token+1).setDraggable(true);
        }
    }
    private void setButtons(){
        buttonsA.setText("Buttons: " + playerA.getButtonsOwned());
        buttonsA.setX(120);
        buttonsA.setY(200);
        buttonsA.setFont(new Font(20));
        buttonsB.setText("Buttons: " + playerB.getButtonsOwned());
        buttonsB.setX(633);
        buttonsB.setY(200);
        buttonsB.setFont(new Font(20));
        incomeA.setText("Income: " + playerA.getButtonIncome());
        incomeA.setX(120);
        incomeA.setY(230);
        incomeA.setFont(new Font(20));
        incomeB.setText("Income: " + playerB.getButtonIncome());
        incomeB.setX(633);
        incomeB.setY(230);
        incomeB.setFont(new Font(20));
        root.getChildren().addAll(buttonsA, buttonsB, incomeA, incomeB);
    }
    public void updateButtons(){
        buttonsA.setText("Buttons: " + playerA.getButtonsOwned());
        buttonsB.setText("Buttons: " + playerB.getButtonsOwned());
        incomeA.setText("Income: " + playerA.getButtonIncome());
        incomeB.setText("Income: " + playerB.getButtonIncome());
    }

    public static Tuple playersFromGameState(String patchCircle, String placement){
        Player player1 = new Player(0, 5, 0);
        Player player2 = new Player(0, 5, 0);

        boolean player1Turn = true;
        boolean player1OldTurn = true;
        int position = 0;

        while (position < placement.length()){
            if (placement.charAt(position) == '.'){
                if (player1Turn){
                    player1.advancePlayer(player2.getTimeSquare() + 1);
                }
                else{
                    player2.advancePlayer(player1.getTimeSquare() + 1);
                }
                player1OldTurn = player1Turn;
                player1Turn = !player1Turn;
                position++;
            }
            else{
                String patch = placement.substring(position, position + 4);
                if ((player1Turn && placement.charAt(position) != 'h') || (player1OldTurn && placement.charAt(position) == 'h')){
                    player1.buyPatch(patch);
                    if (player1.getTimeSquare() > player2.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = false;
                    }
                }
                else{
                    player2.buyPatch(patch);
                    if (player2.getTimeSquare() > player1.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = true;
                    }
                }
                position += 4;
            }
        }

        Tuple out = new Tuple(player1, player2);
        return out;
    }
    public static int circlePosFromGameState(String patchCircle, String placement){
        int position = 0;
        char patch = 'A';
        while (position < placement.length()){
            if (placement.charAt(position) == '.'){
                position ++;
            }
            else{
                patch = placement.charAt(position);
                position += 4;
            }
        }
        return (patchCircle.indexOf(patch) + 1) % patchCircle.length();
    }

}
