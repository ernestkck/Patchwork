package comp1140.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private static final String PATCH_CIRCLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg";
    private static final String[] parts = PATCH_CIRCLE.split("A");
    private static final String ADJUSTED_CIRCLE = parts[1] + parts[0] + "A";
    private static boolean turn = true;
    private static guiPatch currentPatch;
    private int neutral_token = 0;
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
        double prevWidth = 0;
        for (char t: ADJUSTED_CIRCLE.toCharArray()){
            patchList.add(new guiPatch(t));
            /*height = patchList.get(patchList.size()-1).getHeight();
            if (patchList.size() == 1) {
                patchList.get(patchList.size()-1).setLayoutX(10);
                patchList.get(patchList.size()-1).setLayoutY(690-height);
                patchList.get(patchList.size()-1).anchor();
            }
            else{
                currentX += prevWidth+10;
                patchList.get(patchList.size()-1).setLayoutX(currentX);
                patchList.get(patchList.size()-1).setLayoutY(690-height);
                patchList.get(patchList.size()-1).anchor();
            }
            prevWidth = patchList.get(patchList.size() - 1).getWidth();*/
        }
        updatePatchCircle();
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
        if (neutral_token+1 == patchList.size()){
            patchList.get(0).setDraggable(true);
            patchList.get(1).setDraggable(true);
        }
        else if (neutral_token+2 == patchList.size()){
            patchList.get(neutral_token+1).setDraggable(true);
            patchList.get(0).setDraggable(true);
        }
        else {
            patchList.get(neutral_token+1).setDraggable(true);
            patchList.get(neutral_token+2).setDraggable(true);
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
        Button movePatch = new Button("Move Patch");
        movePatch.setLayoutX(150);
        movePatch.setLayoutY(250);
        movePatch.setOnAction(event -> {
            updatePatchCircle();
            setDraggable();
        });
        Button changeTurn = new Button("Change Turn");
        changeTurn.setLayoutX(700);
        changeTurn.setLayoutY(300);
        changeTurn.setOnAction(event -> {
            turn = !turn;
        });
        Button confirm = new Button("Confirm");
        confirm.setLayoutX(700);
        confirm.setLayoutY(250);
        confirm.setOnAction(event -> {
            placePatch(currentPatch);
        });
        root.getChildren().addAll(buttonsA, buttonsB, incomeA, incomeB, movePatch, confirm, changeTurn);
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
    public void placePatch(guiPatch patch){
        System.out.println(neutral_token);
        neutral_token = patchList.indexOf(patch);
        System.out.println(neutral_token);
        patchList.remove(patch);
        patch.setDisable(true);
        updatePatchCircle();
        setDraggable();
    }
    public void updatePatchCircle(){
        double height = 0;
        double currentX = 10;
        double prevWidth = 0;
        int index;
        System.out.println(patchList.get(neutral_token).getPatch().name());
        for (int i = neutral_token; i < patchList.size()+neutral_token; i++){
            index = Math.floorMod(i, patchList.size());
            height = patchList.get(index).getHeight();
            currentX += prevWidth+10;
            patchList.get(index).setLayoutX(currentX);
            patchList.get(index).setLayoutY(690-height);
            patchList.get(index).anchor();
            prevWidth = patchList.get(index).getWidth();
        }
    }
    public static boolean getTurn(){
        return turn;
    }
    public static void setCurrentPatch(guiPatch patch){
        currentPatch = patch;
    }

}
