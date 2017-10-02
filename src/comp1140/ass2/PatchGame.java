package comp1140.ass2;

import java.util.ArrayList;
import java.util.Random;

public class PatchGame {

    public static void main(String[] args) {
        Player player1 = new Player(0, 5, 0);
        Player player2 = new Player(0, 5, 0);
        boolean player1Turn = true;

        System.out.println(patchCircle);
        for (int i = 0; i < 100; i++){
            String check = placement;
            String move = "";

            if (player1Turn){
                move = player1.generatePatchPlacement();
                if (move.equals(".")){
                    player1.advancePlayer(player2.getTimeSquare() + 1);
                    player1Turn = false;
                }
                else if (!move.equals("")){
                    if (!player1.placementValid(move)){
                        System.out.println("move '" + move + "' is invalid but generated anyway");
                    }
                    player1.buyPatch(move);
                    if (player1.getTimeSquare() > player2.getTimeSquare()){
                        player1Turn = false;
                    }
                }
            }
            else {
                move = player2.generatePatchPlacement();
                if (move.equals(".")){
                    player2.advancePlayer(player1.getTimeSquare() + 1);
                    player1Turn = true;
                }
                else if (!move.equals("")){
                    if (!player2.placementValid(move)){
                        System.out.println("move '" + move + "' is invalid but generated anyway");
                    }
                    player2.buyPatch(move);
                    if (player2.getTimeSquare() > player1.getTimeSquare()){
                        player1Turn = true;
                    }
                }
            }



            System.out.println(move);
            System.out.println(placement);
            System.out.println("Player 1: BUTTONS = " + player1.getButtonsOwned() + "; INCOME = " + player1.getButtonIncome() + "; TIME = " + player1.getTimeSquare());
            System.out.println("Player 2: BUTTONS = " + player2.getButtonsOwned() + "; INCOME = " + player2.getButtonIncome() + "; TIME = " + player2.getTimeSquare());
            System.out.println("-------------------------------------------------");
            if (check == placement){
                break;
            }
        }
    }

    public static String placement = "";
    public static String patchCircle = randCircle();
    public static int neutralToken = getNewNeutralToken();

    public static int getNewNeutralToken(){
        return (patchCircle.indexOf('A') + 1) % patchCircle.length();
    }
    private static String randCircle(){
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg";
        String out = "";
        Random rand = new Random();
        ArrayList<Character> circleList = new ArrayList<Character>();
        for (char chr : alpha.toCharArray()){
            circleList.add(chr);
        }
        while (circleList.size() > 0){
            int index = rand.nextInt(circleList.size());
            out += circleList.get(index);
            circleList.remove(index);
        }
        return out;
    }
    public static Tuple playersFromGameState(String patchCircle, String placement){
        Player player1 = new Player(0, 5, 0);
        Player player2 = new Player(0, 5, 0);

        boolean player1Turn = true;
        boolean player1OldTurn = true;
        int position = 0;

        while (position < placement.length()){
            if (placement.charAt(position) == '.'){
                if (player1Turn){
                    player1.advancePlayer(player2.getTimeSquare() + 1);
                }
                else{
                    player2.advancePlayer(player1.getTimeSquare() + 1);
                }
                player1OldTurn = player1Turn;
                player1Turn = !player1Turn;
                position++;
            }
            else{
                String patch = placement.substring(position, position + 4);
                if ((player1Turn && placement.charAt(position) != 'h') || (player1OldTurn && placement.charAt(position) == 'h')){
                    player1.buyPatch(patch);
                    if (player1.getTimeSquare() > player2.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = false;
                    }
                }
                else{
                    player2.buyPatch(patch);
                    if (player2.getTimeSquare() > player1.getTimeSquare()){
                        player1OldTurn = player1Turn;
                        player1Turn = true;
                    }
                }
                position += 4;
            }
        }

        Tuple out = new Tuple(player1, player2, player1Turn);
        return out;
    }
}
