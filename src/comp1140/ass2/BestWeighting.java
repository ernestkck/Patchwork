package comp1140.ass2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BestWeighting {
    public static void main(String[] args) {
        int[] trapWeighting = IntStream.range(3, 5).toArray();
        int[] lineWeighting = IntStream.range(1, 5).toArray();
        int[] trapOffset = IntStream.range(0, 6).toArray();
        int[] lineOffset = IntStream.range(0, 6).toArray();

        Scanner scn = new Scanner(System.in);
        System.out.print("Number of Games: ");
        int loops = scn.nextInt();
        double bestDeviation = 200;
        double bestScore = 14.699;
        int[] index = new int[4];

        for (int trapWeight : trapWeighting){
            for (int lineWeight : lineWeighting){
                for (int trapOff : trapOffset){
                    for (int lineOff : lineOffset){
                        ArrayList<Integer> scores = new ArrayList<>();

                        Player.trappedWeighting = trapWeight;
                        Player.lineWeighting    = lineWeight;
                        Player.trappedOffset    = trapOff;
                        Player.lineOffset       = lineOff;

                        for (int j = 0; j < loops; j++){
                            PatchGame.patchCircle = PatchGame.randCircle();
                            PatchGame.neutralToken = PatchGame.getNewNeutralToken();
                            PatchGame.placement = "";

                            Player player1 = new Player(0, 5, 0);
                            Player player2 = new Player(0, 5, 0);
                            boolean player1Turn = true;

                            while (true){
                                String check = PatchGame.placement;

                                if (player1Turn){
                                    String move = player1.generatePatchPlacement();
                                    if (move.equals(".")){
                                        player1.advancePlayer(player2.getTimeSquare() + 1);
                                        player1Turn = false;
                                    }
                                    else if (!move.equals("")){
                                        player1.buyPatch(move);
                                        if (player1.getTimeSquare() > player2.getTimeSquare()){
                                            player1Turn = false;
                                        }
                                    }
                                }
                                else {
                                    String move = player2.generatePatchPlacement();
                                    if (move.equals(".")){
                                        player2.advancePlayer(player1.getTimeSquare() + 1);
                                        player1Turn = true;
                                    }
                                    else if (!move.equals("")){
                                        player2.buyPatch(move);
                                        if (player2.getTimeSquare() > player1.getTimeSquare()){
                                            player1Turn = true;
                                        }
                                    }
                                }
                                if (check == PatchGame.placement){
                                    break;
                                }
                            }
                            scores.add(player1.getScore());
                            scores.add(player2.getScore());
                        }
                        double score = 0;
                        double deviation = 0;
                        for (int i : scores){
                            score += i;
                        }
                        score /= 2 * loops;

                        for (int i : scores){
                            deviation += Math.pow(i - score, 2);
                        }
                        deviation /= 2 * loops;
                        deviation = Math.sqrt(deviation);

                        if (score > bestScore){
                            bestScore = score;
                            bestDeviation = deviation;
                            index[0] = trapWeight;
                            index[1] = lineWeight;
                            index[2] = trapOff;
                            index[3] = lineOff;
                            System.out.println("-------------------------------------------------------------------------------------");
                            System.out.println(String.format("SCORE: %s, DEVIATION: %s, CODE: %d, %d, %d, %d", score, deviation, index[0], index[1], index[2], index[3]));
                            System.out.println("-------------------------------------------------------------------------------------");
                        }
                        else{
                            System.out.println("SCORE: " + score + ", CODE: " + trapWeight + ", " + lineWeight + ", " + trapOff + ", " + lineOff);
                        }
                    }
                }
            }
        }
        System.out.println("BEST SCORE:     " + bestScore);
        System.out.println("BEST DEVIATION: " + bestDeviation);
        System.out.println("TRAP WEIGHTING: " + index[0]);
        System.out.println("LINE WEIGHTING: " + index[1]);
        System.out.println("TRAP OFFSET:    " + index[2]);
        System.out.println("LINE OFFSET:    " + index[3]);
    }
}
