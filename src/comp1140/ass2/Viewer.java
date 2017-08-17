package comp1140.ass2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * A very simple viewer for piece placements in the Patchwork game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 5: implement the simple placement viewer
        root.getChildren().clear();
        root.getChildren().add(controls);
        int currentY = 100;
        int currentX = 50;
        Image image;
        int i = 0;
        ArrayList<ImageView> patchList = new ArrayList<ImageView>();
        for (char t: placement.toCharArray()){
            image = new Image(Viewer.class.getResourceAsStream("gui/" + URI_BASE + t + ".png"));
            currentY += image.getHeight();
            patchList.add(new ImageView(image));
            patchList.get(i).setX(currentX);
            patchList.get(i).setY(VIEWER_HEIGHT-currentY);
            i++;
        }
        root.getChildren().addAll(patchList);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Display");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(250);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        root.getChildren().add(controls);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
