package comp1140.ass2;

import java.util.stream.IntStream;

public class Board {
    public final int[] buttonEvent = IntStream.range(5, 54).filter(x -> x % 6 == 5).toArray();
    public final int[] patchEvent  = IntStream.range(20, 51).filter(x -> x % 6 == 2 && x != 38).toArray();

    public int[] getButtonEvent(){
        return buttonEvent;
    }

    public int[] getPatchEvent() {
        return patchEvent;
    }

    public int triggeredButtonEvent(int lastPos, int newPos){
        int out = 0;
        for (int num : buttonEvent){
            if (num <= newPos && num > lastPos){
                out += 1;
            }
        }
        return out;
    }

    public int triggeredPatchEvent(int lastPos, int newPos){
        int out = 0;
        for (int num : patchEvent){
            if (num <= newPos && num > lastPos){
                out += 1;
            }
        }
        return out;
    }
}
