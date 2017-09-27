package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by lachl on 26/09/2017.
 */
public class ComprehensivePlacementTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(5000);

    @Test
    public void Empty_Circle() {
        assertFalse("Null patch circle string is not OK, but passed",  PatchworkGame.placementValid(null, GoodPlacements[0]));
        assertFalse("Empty patch circle string is not OK, but passed", PatchworkGame.placementValid("",   GoodPlacements[0]));
    }

    @Test
    public void Empty_Placment() {
        assertFalse("Null placement string is not OK, but passed",  PatchworkGame.placementValid(GoodCircle, null));
        assertFalse("Empty placement string is not OK, but passed", PatchworkGame.placementValid(GoodCircle, ""));
    }

    @Test
    public void Bad_Circles() {
        for (String[] circles : BadCircles) {
            String circle = circles[0];
            String reason = circles[1];
            assertFalse("Circle '" + circle + "' is invalid since " + reason + ", but passed anyway.",
                    PatchworkGame.placementValid(circle, "."));
        }
    }

    @Test
    public void Bad_Placment() {
        for (String[] placements : BadPlacements) {
            String placement = placements[0];
            String reason    = placements[1];
            assertFalse("Placement '" + placement + "' is invalid since " + reason + ", but passed anyway.",
                    PatchworkGame.placementValid(GoodCircle, placement));
        }
    }

    @Test
    public void Good_Placment() {
        for (String placement : GoodPlacements){
            assertTrue("Placement '" + placement + "' is invalid since it is valid, but failed anyway",
                    PatchworkGame.placementValid(GoodCircle, placement));
        }
    }

    private static final String GoodCircle = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg";

    private static final String[][] BadCircles = {
            {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefh" , "it contains a 'h' tile"       },
            {"ABCDEFGNOPQRSTXYZabcdef"           , "it is missing a patches"      },
            {"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgA", "it includes extra patches"    },
            {"ABCDEFWHIJKLMNOWQRSTUWWXYZabcdefW" , "it contains duplicate patches"},
            {"ABCDEFG...FENSKKKLASLDKKJDJ53434a" , "it contains bad data"         }
    };

    private static final String[] GoodPlacements = {
            "BAAA.EBBA",
            "DAAAGCDA..JACEMADB.....PABDSBAFhCBA...VAFAXBEBhFFA..aCDAdGDDhAEAgCGABAACFCAH",
            "BAACCAACDACA.EABA.FADEGBAAHBEC...JAEEKAGCLBCB.hBIA.NCAAOCHFQEDAhCAAREGBUCBBVGFA.XBFAhCCAaHGEdHADeEBFAAIBMEABSGCBhCCA.TDEAWCHHZGBAhIIAcGEDfEFF."

    };

    private static final String[][] BadPlacements = {
            {"BAAA.EAAA"                                       , "it overlaps"                                        },
            {"BADE.EIID"                                       , "it's off the grid"                                  },
            {"BAAA.ECCA"                                       , "it's not adjacent"                                  },
            {"BAAA.JBBB"                                       , "it's not available from current patch selections"   },
            {"BAAA.EBBA.FADA.ICDA"                             , "player does not have enough buttons to purchase it" },
            {"BAACCAACDACA.EABA.FADEGBAAHBEC...JAEEKAGCLBCB.." , "the 'h' tile placement is incorrect"                },
            {"BAACCAACDACA.EABA.FADEGBAAHBEC...JAEEKAGCLBCB.hBIA.NCAAOCHFQEDAhCAAREGBUCBBVGFA.XBFAhCCAaHGEdHADeEBFAAIBMEABSGCBhCCA.TDEAWCHHZGBAhIIAcGEDfEFF.."
                                                               , "a move is made after the game has finished"         }
    };
}
