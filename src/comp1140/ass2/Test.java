package comp1140.ass2;

/**
 * Created by lachl on 1/10/2017.
 */
public class Test {
    public static void main(String[] args) {
        Player player = new Player(0, 5, 0);
        PatchGame.patchCircle = "COEDHaPWMbfegNXBLIcZdRYSTFJQVGKUA";
        PatchGame.neutralToken = PatchGame.getNewNeutralToken();

        System.out.println("OBHD");
        System.out.println(player.placementValid("OBHD"));
        System.out.println("------------------------------------------");

        player.buyPatch("OBHD");
        PatchGame.neutralToken = 4;
        player.advancePlayer(6);

        String move = player.generatePatchPlacement();
        System.out.println(move);
        System.out.println(player.placementValid(move));
        System.out.println("------------------------------------------");

        System.out.println("aAFA");
        System.out.println(player.placementValid("aAFA"));
        System.out.println("------------------------------------------");
    }
}
