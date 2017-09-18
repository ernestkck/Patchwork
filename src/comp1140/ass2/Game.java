package comp1140.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application{ //this class contains the main method that runs the game, it will also be the GUI

    static Player playerA = new Player(0,5,0);
    static Player playerB = new Player(0,5,0);
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
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
        ArrayList<ImageView> patchList = new ArrayList();
        double width;
        double prevWidth = 0;
        double height = 0;
        Image image;
        char patch;
        for (int i = 65; i < 91; i++){
            patch = (char) i;
            image = new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + patch + ".png"));
            width = image.getWidth();
            patchList.add(new ImageView(image));
            patchList.get(patchList.size()-1).setPreserveRatio(true);
            patchList.get(patchList.size()-1).setFitWidth(width*0.5);
            if (i == 65) {
                patchList.get(patchList.size()-1).setX(20);
                patchList.get(patchList.size()-1).setY(10);
            }
            else if (i < 77){
                patchList.get(patchList.size()-1).setX(20+patchList.get(patchList.size()-2).getX()+prevWidth*0.5);
                patchList.get(patchList.size()-1).setY(10);
            }
            else if (i==77){
                patchList.get(patchList.size()-1).setX(patchList.get(patchList.size()-2).getX());
                patchList.get(patchList.size()-1).setY(20+patchList.get(patchList.size()-2).getY()+height*0.5);
            }
            else if (i < 83){
                patchList.get(patchList.size()-1).setX(780);
                patchList.get(patchList.size()-1).setY(10+patchList.get(patchList.size()-2).getY()+height*0.5);
            }
            else {
                patchList.get(patchList.size()-1).setX(patchList.get(patchList.size()-2).getX()-15-width*0.5);
                patchList.get(patchList.size()-1).setY(patchList.get(patchList.size()-2).getY());
            }
            prevWidth = width;
            height = image.getHeight();
        }
        for (int i = 97; i < 103; i++){
            patch = (char) i;
            image = new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + patch + "_.png"));
            width = image.getWidth();
            patchList.add(new ImageView(image));
            patchList.get(patchList.size()-1).setPreserveRatio(true);
            patchList.get(patchList.size()-1).setFitWidth(width*0.5);
            height = image.getHeight();
            if (i==97){
                patchList.get(patchList.size()-1).setX(patchList.get(patchList.size()-2).getX()-40-width*0.5);
                patchList.get(patchList.size()-1).setY(patchList.get(patchList.size()-2).getY()+20);
            }
            else {
                patchList.get(patchList.size() - 1).setX(patchList.get(patchList.size() - 2).getX());
                patchList.get(patchList.size() - 1).setY(patchList.get(patchList.size() - 2).getY()-height*0.5-25);
            }

        }
        ArrayList<DraggableNode> db = new ArrayList<>();
        for (int i = 0; i < patchList.size(); i++){
            db.add(new DraggableNode(patchList.get(i)));
        }
        root.getChildren().addAll(bg, tb);
        root.getChildren().addAll(db);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void isSevenSquare(Player player){
        //TODO: Implement code that checks
        if (false)  player.updateButtonIncome(7);
    }



}
