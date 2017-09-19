package comp1140.ass2;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class guiPatch extends ImageView {
    Patch patch;

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
    }

}
