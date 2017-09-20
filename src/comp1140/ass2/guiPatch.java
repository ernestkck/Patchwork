package comp1140.ass2;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
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
            if (event.getSceneX() > 240 && event.getSceneX() < 465 && event.getSceneY() > 325 && event.getSceneY() < 550){
                int layout = (int) getLayoutX();
                //horizontal = 'A' + (layout-240)/25;
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
    public void rotate(){
        if (rotation == 'H') {
            rotation = 'A';
        }
        else {
            rotation += 1;
        }
        if (rotation == 'H' || rotation == 'D') setRotate(-1);
        setRotate(90);
    }



}
