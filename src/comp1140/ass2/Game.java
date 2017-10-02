package comp1140.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends Application{ //this class contains the main method that runs the game, it will also be the GUI

    static Player playerA = new Player(0,5,0);
    static Player playerB = new Player(0,5,0);

    public static String patchCircle = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg";

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;
    private static String randCircle(){
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg";
        String out = "";
        Random rand = new Random();
        int index;
        ArrayList<Character> circleList = new ArrayList();
        for (char t: alpha.toCharArray()){
            circleList.add(new Character(t));
        }
        while (circleList.size() > 0){
            index = rand.nextInt(circleList.size());
            out += circleList.get(index);
            circleList.remove(index);
        }
        return out;
    }
    private static String PATCH_CIRCLE = randCircle();
    private static final String[] parts = PATCH_CIRCLE.split("A");
    private static final String ADJUSTED_CIRCLE = parts[1] + parts[0] + "A";
    private static boolean turn = true;
    private static String placementString = "";
    private static GuiPatch currentPatch = new GuiPatch('h');
    private static int neutralToken = 0;
    private static final String URI_BASE = "assets/";
    public static ArrayList<GuiPatch> patchList = new ArrayList();
    private Text buttonsA = new Text("Buttons: " + playerA.getButtonsOwned());
    private Text buttonsB = new Text("Buttons: " + playerB.getButtonsOwned());
    private Text incomeA = new Text("Income: " + playerA.getButtonIncome());
    private Text incomeB = new Text("Income: " + playerB.getButtonIncome());
    private Button advance = new Button("Advance");
    private Text turnText = new Text("Player One's Turn");
    private Player currentPlayer = playerA;
    private boolean endA = false;
    private boolean endB = false;
    private int endFirst = 0;
    private Text placementText = new Text("Placement: ");
    private static Text patchInfo = new Text();
    private ImageView explanation = new ImageView(new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + "controlsexplained.png")));
    public static boolean specialTile = false;
    private double[][] timeSquareCoords = {
            {420, 125},
            {450, 125},
            {475, 125},
            {500, 125},
            {525, 125},
            {550, 125},
            {550, 149.5},
            {550, 174},
            {550, 198.5},
            {550, 223},
            {550, 247.5},
            {550, 272},
            {550, 296.5},
            {525.5, 296.5},
            {501, 296.5},
            {476.5, 296.5},
            {452, 296.5},
            {427.5, 296.5},
            {403, 296.5},
            {380, 296.5},
            {380, 247.5},
            {380, 223},
            {380, 198.5},
            {380, 174},
            {380, 149.5},
            {403, 149.5},
            {452, 149.5},
            {476.5, 149.5},
            {501, 149.5},
            {525.5, 149.5},
            {525.5, 174},
            {525.5, 198.5},
            {525.5, 247.5},
            {525.5, 272},
            {501, 272},
            {476.5, 272},
            {452, 272},
            {427.5, 272},
            {403, 272},
            {403, 247.5},
            {403, 223},
            {403, 198.5},
            {403, 174},
            {427.5, 174},
            {476.5, 174},
            {501, 174},
            {501, 198.5},
            {501, 223},
            {501, 247.5},
            {476.5, 247.5},
            {427.5, 247.5},
            {427.5, 223},
            {427.5, 198.5},
            {470, 210},
    };
    private Circle circleA = new Circle(10);
    private Circle circleB = new Circle(10);

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        makeBoard();
        makePatchCircle();
        setDraggable();
        setButtons();
        updatePlayer();
        circleA.toFront();
        explanation.toFront();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void isSevenSquare(Player player){
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
                if(i>=2) return;
            }
        }
        int max = Arrays.stream(first).filter(x -> x<9).max().getAsInt();
        int min = Arrays.stream(last).filter(x -> x>-1).min().getAsInt();
        if(min - max + 1 >= 7){
            player.updateButtonsOwned(7);
            specialTile = true;
        }
    }
    public void makePatchCircle(){
        for (char t: ADJUSTED_CIRCLE.toCharArray()) {
            patchList.add(new GuiPatch(t));
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
        explanation.setOnMouseClicked(event -> {
            root.getChildren().remove(explanation);
        });
        patchInfo.setText("Button Cost: \nTime Cost: \nIncome: ");
        root.getChildren().addAll(explanation, bg, tb);
    }
    public void setDraggable(){
        for (int i = 0; i < patchList.size(); i++){
            patchList.get(i).setDraggable(false);
        }
        if (patchList.size() <= neutralToken){
            neutralToken--;
        }
        patchList.get(neutralToken).setDraggable(true);
        if (neutralToken+1 == patchList.size()){
            patchList.get(0).setDraggable(true);
            patchList.get(1).setDraggable(true);
        }
        else if (neutralToken+2 == patchList.size()){
            patchList.get(neutralToken+1).setDraggable(true);
            patchList.get(0).setDraggable(true);
        }
        else {
            patchList.get(neutralToken+1).setDraggable(true);
            patchList.get(neutralToken+2).setDraggable(true);
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
        Button confirm = new Button("Confirm");
        confirm.setLayoutX(700);
        confirm.setLayoutY(250);
        confirm.setOnAction(event -> {
            System.out.println("placementString: " + placementString);
            System.out.println("currentPatch: " + currentPatch.toString());
            boolean checkCoords = currentPatch.toString().toCharArray()[1] >= 'A' && currentPatch.toString().toCharArray()[1] <= 'H' && currentPatch.toString().toCharArray()[2] >= 'A' && currentPatch.toString().toCharArray()[2] <= 'H';
            if (PatchworkGame.isPlacementValid(PATCH_CIRCLE, placementString + currentPatch.toString()) && currentPlayer.getButtonsOwned()-currentPatch.getPatch().getButtonCost() >= 0){
                advance.setDisable(false);
                placePatch(currentPatch);
                placementString += currentPatch.toString();
                if (Board.triggeredPatchEvent(currentPlayer.getTimeSquare(), currentPlayer.getTimeSquare()+currentPatch.getPatch().getTimeCost()) && checkCoords){
                    specialPatch(currentPatch.toString());
                }
                else {
                    currentPlayer.buyPatch(currentPatch.toString());
                    updatePlayer();
                    updateButtons();
                }
                currentPatch = new GuiPatch('h');
            }
            else {
                currentPatch.toAnchor();
            }

        });
        advance.setLayoutX(700);
        advance.setLayoutY(300);
        advance.setOnAction(event -> {
            placementString += '.';
            currentPatch.toAnchor();
            int oldTime = currentPlayer.getTimeSquare();
            if (currentPlayer == playerA){
                currentPlayer.advancePlayer(playerB.getTimeSquare()+1);
            }
            else {
                currentPlayer.advancePlayer(playerA.getTimeSquare()+1);
            }
            if (Board.triggeredPatchEvent(oldTime, currentPlayer.getTimeSquare())){
                specialPatch(".");
            }
            else {
                updatePlayer();
            }
            currentPatch = new GuiPatch('h');
        });
        circleA.setFill(Color.BLUE);
        circleA.setCenterX(3);
        circleB.setCenterY(3);
        circleB.setFill(Color.RED);
        circleA.toFront();
        circleA.setLayoutX(timeSquareCoords[playerA.getTimeSquare()][0]);
        circleA.setLayoutY(timeSquareCoords[playerA.getTimeSquare()][1]);
        circleB.setLayoutX(timeSquareCoords[playerB.getTimeSquare()][0]);
        circleB.setLayoutY(timeSquareCoords[playerB.getTimeSquare()][1]);
        circleA.setStroke(Color.BLACK);
        circleB.setStroke(Color.BLACK);
        placementText.setLayoutX(780);
        placementText.setLayoutY(270);
        turnText.setFont(new Font(30));
        turnText.setX(320);
        turnText.setY(50);
        patchInfo.setFont(new Font(20));
        patchInfo.setLayoutX(50);
        patchInfo.setLayoutY(500);
        root.getChildren().addAll(buttonsA, buttonsB, incomeA, incomeB, confirm, placementText, turnText, advance, circleA, circleB, patchInfo);
    }
    public void updateButtons(){
        buttonsA.setText("Buttons: " + playerA.getButtonsOwned());
        buttonsB.setText("Buttons: " + playerB.getButtonsOwned());
        incomeA.setText("Income: " + playerA.getButtonIncome());
        incomeB.setText("Income: " + playerB.getButtonIncome());
        if (turn){
            turnText.setText("Player One's Turn");
        }
        else {
            turnText.setText("Player Two's Turn");
        }
    }

    public void specialPatch(String patchPlace){
        advance.setDisable(true);
        for (int i = 0; i < patchList.size(); i++){
            patchList.get(i).setDraggable(false);
        }
        GuiPatch hPatch = new GuiPatch('h');
        hPatch.setLayoutX(100);
        hPatch.setLayoutY(300);
        hPatch.setDraggable(true);
        hPatch.anchor();
        root.getChildren().add(hPatch);
        boolean tmp = turn;
        if (patchPlace != ".") {
            currentPlayer.buyPatch(patchPlace);
        }
        updatePlayer();
        turn = tmp;
        updateButtons();
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

        Tuple out = new Tuple(player1, player2, player1Turn);
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
    public void placePatch(GuiPatch patch){
        if (patch.getName() != 'h') {
            neutralToken = patchList.indexOf(patch);
            patchList.remove(patch);
        }
        patch.setDisable(true);
        placementText.setText("Placement: " + patch.toString());
        updatePatchCircle();
        setDraggable();
    }
    public static void updatePlacementString(String newPatch){
        placementString += newPatch;
    }
    public static void updateNeutralToken(int t){
        neutralToken = t;
    }
    public void updatePatchCircle(){
        double height = 0;
        double currentX = 10;
        double prevWidth = 0;
        int index;
        for (int i = neutralToken; i < patchList.size()+neutralToken; i++){
            index = i % patchList.size();//Math.floorMod(i, patchList.size());
            height = patchList.get(index).getHeight();
            currentX += prevWidth+10;
            patchList.get(index).setLayoutX(currentX);
            patchList.get(index).setLayoutY(690-height);
            patchList.get(index).anchor();
            prevWidth = patchList.get(index).getWidth();
        }
    }
    public static void updatePatchCircle(String p){
        patchCircle = patchCircle.replace(p, "");
    }
    public void updatePlayer(){
        System.out.println(currentPlayer.getTimeSquare());
        System.out.println(timeSquareCoords.length);
        if (currentPlayer == playerA){
            if (playerA.getTimeSquare() < timeSquareCoords.length-1) {
                circleA.setLayoutX(timeSquareCoords[playerA.getTimeSquare()][0]);
                circleA.setLayoutY(timeSquareCoords[playerA.getTimeSquare()][1]);
            }
            else {
                circleA.setLayoutX(timeSquareCoords[timeSquareCoords.length-1][0]);
                circleA.setLayoutY(timeSquareCoords[timeSquareCoords.length-1][1]);
                endA = true;
                if (endB) endGame();
                else endFirst = 1;
            }
        }
        else {
            if (playerB.getTimeSquare() < timeSquareCoords.length-1) {
                circleB.setLayoutX(timeSquareCoords[playerB.getTimeSquare()][0]);
                circleB.setLayoutY(timeSquareCoords[playerB.getTimeSquare()][1]);
            }
            else {
                circleB.setLayoutX(timeSquareCoords[timeSquareCoords.length-1][0]);
                circleB.setLayoutY(timeSquareCoords[timeSquareCoords.length-1][1]);
                endB = true;
                if (endA) endGame();
                else endFirst = 2;
            }
        }
        if (playerB.getTimeSquare() > playerA.getTimeSquare()){
            turn = true;
            circleA.toFront();
            currentPlayer = playerA;
        }
        else if (playerA.getTimeSquare() > playerB.getTimeSquare()){
            turn = false;
            circleB.toFront();
            currentPlayer = playerB;
        }
        updateButtons();
    }
    public static boolean getTurn(){
        return turn;
    }
    public static void setCurrentPatch(GuiPatch patch){
        currentPatch = patch;
    }
    public static String getPatchCircle(){
        return PATCH_CIRCLE;
    }
    public static String getPlacementString(){
        return placementString;
    }
    public void loadPlacements(String patchCircle, String placement){
        PATCH_CIRCLE = patchCircle;
        placementString = placement;
        neutralToken = 0;
        patchList.clear();
        makePatchCircle();
        int position = 0;
        while (position < placementString.length()){
            if (placementString.charAt(position) == '.'){
                position ++;
            }
            else{
                for (int i = 0; i < patchList.size(); i++){
                    if (patchList.get(i).getName() == placementString.charAt(position)) placePatch(patchList.get(i));
                }
                position += 4;
            }
        }
    }
    public static int getNeutralToken(){
        return neutralToken;
    }
    private void endGame(){
        int scoreA = PatchworkGame.getScoreForPlacement(PATCH_CIRCLE, placementString, true);
        int scoreB = PatchworkGame.getScoreForPlacement(PATCH_CIRCLE, placementString, false);
        root.getChildren().clear();
        Text winner;
        if (scoreA > scoreB){
            winner = new Text("Player One wins");
        }
        else if (scoreB > scoreA){
            winner = new Text("Player Two wins");
        }
        else if(endFirst == 1){
            winner = new Text("Player One got to the final space first\nPlayer One wins");
        }
        else{
            winner = new Text("Player Two got to the final space first\nPlayer Two wins");
        }
        winner.setFont(new Font(50));
        winner.setLayoutX(300);
        winner.setLayoutY(300);
        Text scores = new Text("Player One's score: " + scoreA + "\nPlayer Two's score: " + scoreB);
        scores.setFont(new Font(30));
        scores.setLayoutX(250);
        scores.setLayoutY(400);
        root.getChildren().addAll(winner, scores);
    }
    public static void setInfo(Patch patch){
        patchInfo.setText("Button Cost: " + patch.getButtonCost() + "\nTime Cost: " + patch.getTimeCost() + "\nIncome: " + patch.getButtonIncome());
    }
    public static void clearInfo(){
        patchInfo.setText("Button Cost: \nTime Cost: \nIncome: ");
    }
}
