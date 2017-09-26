package comp1140.ass2;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class guiPatch extends ImageView {
    Patch patch;
    char rotation = 'A';
    char horizontal;
    char vertical;
    double anchorX, anchorY;
    double mouseX, mouseY;
    boolean turn = Game.getTurn();

    guiPatch(char patch){
        this.patch = Patch.valueOf("" + patch);
        if (patch <= 'Z'){
            setImage(new Image(guiPatch.class.getResourceAsStream("gui/assets/" + patch + ".png")));
        }
        else {
            setImage(new Image(guiPatch.class.getResourceAsStream("gui/assets/" + patch + "_.png")));
        }
        setPreserveRatio(true);
        setFitWidth(getWidth());
        setDraggable(false);
    }

    public Patch getPatch() {
        return patch;
    }
    public double getWidth(){
        return getImage().getWidth()/2;
    }
    public double getHeight(){
        return getImage().getHeight()/2;
    }
    public void setDraggable(boolean bool) {
        ColorAdjust colorAdjust = new ColorAdjust();
        if (bool) {
            colorAdjust.setContrast(0);
        }
        else {
            colorAdjust.setContrast(-0.7);
        }
        setEffect(colorAdjust);
        setDisable(!bool);

        setOnScroll(event -> {
            rotate();
        });

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            toFront();
            double movementX = event.getSceneX() - mouseX;
            double movementY = event.getSceneY() - mouseY;
            setLayoutX(getLayoutX() + movementX);
            setLayoutY(getLayoutY() + movementY);
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            event.consume();
        });

        setOnMouseReleased(event -> {
            if (event.getSceneX() > 250 && event.getSceneX() < 465 && event.getSceneY() > 325 && event.getSceneY() < 550 && turn){
                int layoutH = (int) Math.round((getLayoutX()-239)/25);
                int layoutV = (int) Math.round((getLayoutY()-325)/25);
                System.out.println(layoutV);
                horizontal = Character.toChars('A' + layoutH)[0];
                vertical = Character.toChars('A' + layoutV)[0];
                System.out.println("" + horizontal + vertical);
                setLayoutX(239 + (horizontal-'A')*25);
                setLayoutY(325 + (vertical-'A')*25);


            }
            else {
                setLayoutX(anchorX);
                setLayoutY(anchorY);
            }
        });


    }
    public void anchor(){
        anchorX = getLayoutX();
        anchorY = getLayoutY();
    }
    public void setPosition(char x, char y){
        Game.setPosition(x, y, this);
    }
    public void rotate(){
        if (rotation == 'H') {
            rotation = 'A';
        }
        else {
            rotation += 1;
        }
        if (rotation == 'H') setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        if (rotation == 'D') setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setRotate(getRotate()+90);
    }



}
