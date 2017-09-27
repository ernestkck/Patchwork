package comp1140.ass2;

import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jack1 on 27/09/2017.
 */
public class circlePosFromGameStateTest {

    static final String PATCH_CIRCLES[] = {
            "QJfXRIbTcdegKVZSUFWCYGAaBLDEOHPMN",
            "XgDEfCIYcaHdZbRJGAeWKBFLMNOPQUSTV",
            "cYbTeZVDLIPEagQHdWNfCAFBJRGKMOSUX",
            "OMPSQVALTNBXGCJWcUYaKIHZfRbdegDEF",
            "RcSGTZVJdUaWXKebfIgAHOPYLBCMNDQEF",
            "cSdDKfLTOHeBUJMVCgNEFPWXGQRYZAabI",
            "bRcECeQVOULZPYWBaTfSXAMDdgFHGIJKN",
            "RDZAaUeGFBVJEbSdHCMWXIcKTLNfOYgPQ",
            "aQPAVIHRJdNYbSKBLWMXCOZTUDEcefgFG",
            "GcQYRPCVEfSeTIgADUZWBFXHNdJMKaLOb",
            "GPMVIaeOgNBTRQFHYJKLSEWUXZfDbcdAC"
    };

    static final String PLACEMENTS[] = {
            "LHAAOAAAHADA.MAFANCAA..QFBCXBACIBHAhBBATDCAdGEC.eHFG",
            "eAFABAAAMACAOBHFUAGA..TCFA..VBBAXFGAhEHAECAB.fHGBhFHACBEB.IGEA",
            "FAGARAAAGADAMCIB.UAGA..XGGA..bCACeBGBVDFAhAAAIFGA..hIHAEEAAgDEA",
            "LHAATAAA.XGDBJACA..UAFAaHFC.KAHBHEBAZCAARDDFeFHDhHGA.EEFBODFA",
            "OHACLAAA.CAEADAGAQFAD.EDAFFBFDRECB.SBHB",
            ".aAAA.....IADA.dAFA.KAHB...TBAAhAHA.HCCA.UDGAhBAA.JDAF.MECB",
            "MAAADAAAdADAFHGAGAFA.KAHBRACAEGEA.eCCAQBAA.hIGAODABLEFAZDCB.hAFAPAFABDEAaDGDTCFAhEHASEHHXECAAECBHFABIGCAJFEB.hAHAcCABCGGD.YHAAhGIAWHFA.gEAF",
            "aAGAeAAAGADBBADAEAFAdCGBHDGD..MAIBXDEDKBGBLCAAhAAAOFHD..YABEhAAAPCEBRDFF.ACBAUEAAhABAVCCBSGGD.WCBFTGCAhAFANBAAgFAAQGEChCFAFGDAJEHBIHCAfHAB",
            "VGEARAAAdADA......NAFA.bFCDBAGAMFIB..OAHDUFABEEGBhIHAeCAB..gDBChEAAFCCHQDGChAAAICAAHDDB.SEADWAACCBBEZBDATGBAhABAfEEAPFHDYBHBhCHAXGEA",
            "ZGFABAAAHEDHJACA.aFGELAFBGCBA..QDFARBAA.VBDEhDDAfAHA.hGDAeCCAIHCFACIBUEAAhAAAWBHHFDGDdEDAOACG.cBABPFFBSGADhDBADEIBMHAAKGHDhAHACAACEAGB...",
            ".CAAAGABAMFIB..IHGEaADA.eDHFOAFD.BAHATBGAhBIAQCDDHFEDYAFEhFGALCHBWHBAhIAAZDAFDEAB.dEEDVBCARFCFJGEEECAEfGHAhAAAbDAGcFABABDA...gDEChFDAKHAA."
    };


    @Test
    public void testPos() {
        for (int i = 0; i < PLACEMENTS.length; i++) {
            testGame game = new testGame(PATCH_CIRCLES[i], PLACEMENTS[i]);
            assertTrue("The position given: " + Game.circlePosFromGameState(PATCH_CIRCLES[i], PLACEMENTS[i]) + " is not equal to the position of the neutral token: " + game.getNeutral_token(), Game.circlePosFromGameState(PATCH_CIRCLES[i], PLACEMENTS[i]) == game.getNeutral_token());
        }
    }
}
