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
    boolean isOffset = false;

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
            double grid;
            getTurn();
            if (turn) grid = 0;
            else grid = (9*25) + 5.5;
            if (event.getSceneX() > 250+grid && event.getSceneX() < 465+grid && event.getSceneY() > 325 && event.getSceneY() < 550){
                double x = 0;
                double y = 0;
                if (isOffset){
                    x = rotateOffset(((rotation-65) % 4)*90, getHeight(), getWidth(), 'X');
                    y = rotateOffset(((rotation-65) % 4)*90, getHeight(), getWidth(), 'Y');
                }
                int layoutH = (int) Math.round((getLayoutX()-239-grid-x)/25);
                int layoutV = (int) Math.round((getLayoutY()-325+y)/25);
                horizontal = Character.toChars('A' + layoutH)[0];
                vertical = Character.toChars('A' + layoutV)[0];
                setLayoutX(grid + 239 + (horizontal-'A')*25+x);
                setLayoutY(325 + (vertical-'A')*25-y);
                setCurrentPatch();

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
    public void toAnchor(){
        setLayoutX(anchorX);
        setLayoutY(anchorY);
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
        double rotateDouble = ((rotation-65) % 4)*90;
        if (!isOffset && (rotation-65)%2 == 1) {
            setLayoutX(getLayoutX() + rotateOffset(rotateDouble, getHeight(), getWidth(), 'X'));
            setLayoutY(getLayoutY() - rotateOffset(rotateDouble, getHeight(), getWidth(), 'Y'));
            isOffset = true;
        }
        else if (isOffset && (rotation-65)%2 == 0){
            setLayoutX(getLayoutX() - rotateOffset(rotateDouble, getHeight(), getWidth(), 'X'));
            setLayoutY(getLayoutY() + rotateOffset(rotateDouble, getHeight(), getWidth(), 'Y'));
            isOffset = false;
        }
        setRotate(getRotate()+90);
    }
    public void getTurn(){
        turn = Game.getTurn();
    }
    public void setCurrentPatch(){
        Game.setCurrentPatch(this);
    }
    private double rotateOffset(double angle, double height, double width, char axis){
        if (axis == 'X'){
            switch ((int) (angle/90)%2){
                case 1:  return ((height-width)/2);
                default: return 0;
            }
        }
        else {
            switch ((int) (angle/90)%2){
                case 1:  return  (height-width)/2;
                default: return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "" + getName() + horizontal + vertical + rotation;
    }
    public char getName(){
        return patch.getName();
    }
}
