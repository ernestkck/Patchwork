package comp1140.ass2;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.ArrayList;

public class Board {    //Author: Lachlan Grainger (u5847571)
    private static final int[] buttonEvent = IntStream.range(5, 54).filter(x -> x % 6 == 5).toArray();
    private static final ArrayList<Integer> patchEvent = new ArrayList<Integer>(Arrays.asList(20, 26, 32, 44, 50));

    public static int[] getButtonEvent(){
        return buttonEvent;
    }
    public static ArrayList<Integer> getPatchEvent() {
        return patchEvent;
    }

    public static boolean triggeredButtonEvent(int lastPos, int newPos){
        int out = 0;
        for (int num : buttonEvent){
            if (num <= newPos && num > lastPos){
                return true;
            }
        }
        return false;
    }
    public static boolean triggeredPatchEvent(int lastPos, int newPos){
        for (Integer num : patchEvent){
            if (num <= newPos && num > lastPos){
                patchEvent.remove(num);
                return true;
            }
        }
        return false;
    }
}
