package comp1140.ass2;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.ArrayList;

public class Board {
    static final int[] buttonEvent = IntStream.range(5, 54).filter(x -> x % 6 == 5).toArray();
    public final ArrayList<Integer> patchEvent = new ArrayList(Arrays.asList(20, 26, 32, 44, 50));

    public int[] getButtonEvent(){
        return buttonEvent;
    }

    public ArrayList<Integer> getPatchEvent() {
        return patchEvent;
    }

    static int triggeredButtonEvent(int lastPos, int newPos){
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
                patchEvent.remove(num);
            }
        }
        return out;
    }
}
