package worldofzuulfx.Minigame;

import java.util.Random;
import worldofzuulfx.ConsoleInfo;

public class RockPaperScissors {

    private RockPaperScissorsMoves computerMove;
    private RockPaperScissorsMoves playerMove;
    private int moveComparison;
    
    public RockPaperScissors() {
        computerMove = null;
        playerMove = null;
    }

    /**
     * The play starts the game of Rock, Paper, Scissors. If player wins he gets
     * a beer. If Player loses his decrement energy.
     */
    public void play() {

        ConsoleInfo.setConsoleData("Rock (R), Paper (P) or Scissors (S)");
        while (playerMove == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        computerMove = computerMove();

        moveComparison = playerMove.compareMove(computerMove);
        switch (moveComparison) {
            case 0:
                ConsoleInfo.setConsoleData("Sorry. No winner, it was a tie.");
                break;
            case 1:
                ConsoleInfo.setConsoleData("You won because " + playerMove + " defeats " + computerMove);
                break;
            case -1:
                ConsoleInfo.setConsoleData("You lost: " + computerMove + " defeats " + playerMove);
                break;

        }
    }

    /**
     * Generates the move of the computer through a random generated number
     * which is then used to grab a random move.
     *
     * @return
     */
    public RockPaperScissorsMoves computerMove() {
        RockPaperScissorsMoves[] possibleMoves = RockPaperScissorsMoves.values();
        Random RNG = new Random();
        int index = RNG.nextInt(possibleMoves.length);
        return possibleMoves[index];
    }

    /**
     * @return the moveComparison
     */
    public int getMoveComparison() {
        return moveComparison;
    }

    /**
     * @param playerMove the playerMove to set
     */
    public void setPlayerMove(RockPaperScissorsMoves playerMove) {

        this.playerMove = playerMove;

    }
}
