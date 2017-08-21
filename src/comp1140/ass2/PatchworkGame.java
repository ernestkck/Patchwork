package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {

    
    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {

    }

    /**
     * Determine whether a patch placement is well-formed according to the following:
     * - either it is the single character string ".", or
     * - it consists of exactly four characters:
     * - the first character is in the range A .. Z or a .. h
     * - the second character is in the range A .. I
     * - the third character is in the range A .. I
     * - the fourth character is in the range A .. H
     *
     * @param placement A string describing a patch placement
     * @return True if the tile placement is well-formed
     */
    static boolean isPatchPlacementWellFormed(String placement) {
        if(placement.equals(".")) return true;
        if(placement.length() != 4) return false;
        char[] c = placement.toCharArray();
        if(c[0] < 'A' || (c[0] > 'Z' && c[0] < 'a') || c[0] > 'h') return false;
        if(c[1] < 'A' || c[1] >'I') return false;
        if(c[2] < 'A' || c[2] >'I') return false;
        if(c[3] < 'A' || c[3] >'H') return false;
        return true;
    }

    /**
     * Determine whether a game placement string is well-formed:
     * - it consists of a sequence of patch placement strings, where
     * - each patch placement is well-formed, either as a single-character advance string "."
     * or a four-character patch tile placement
     * - no patch appears more than once in the placement, except the special tile 'h'
     *
     * @param placement A string describing a placement of one or more tiles
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {

        if (isEmpty(placement)) return false;

        String filtered = placement.replace(".","");

        if (filtered.length() % 4 == 0){
            String patch = "";
            String placements = "";
            for (int i = 0; i < filtered.length(); i++){
                patch += filtered.charAt(i);
                if ((i + 1) % 4 == 0){
                    for (char chr : placements.toCharArray()){
                        if (chr == patch.charAt(0) && chr != 'h'){
                            return false;
                        }

                    }

                    if (!isPatchPlacementWellFormed(patch)){
                        return false;
                    }

                    placements += patch.charAt(0);
                    patch = "";
                }

            }
        }
        else{
            return false;
        }
        return true;
    }

    static boolean isEmpty(String s){
        if(s != null && s.length() != 0) return false;
        return true;
    }
    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each tile placement must follow the game's placement rules.
     *
     * @param patchCircle a patch circle string
     * @param placement   A placement string
     * @return true if the placement is valid
     */
    static boolean isPlacementValid(String patchCircle, String placement) {
        // FIXME Task 6: determine whether a placement is valid
        if(isEmpty(patchCircle) || isEmpty(placement)) return false;
        return true;
    }

    /**
     * Determine the score for a player given a placement, following the
     * scoring rules for the game.
     *
     * @param placement   A placement string
     * @param firstPlayer True if the score for the first player is requested,
     *                    otherwise the score for the second player should be returned
     * @return the score for the requested player, given the placement
     */
    static int getScoreForPlacement(String patchCircle, String placement, boolean firstPlayer) {
        // FIXME Task 7: determine the score for a player given a placement
        return 0;
    }

}
