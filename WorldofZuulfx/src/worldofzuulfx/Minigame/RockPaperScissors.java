package worldofzuulfx.Minigame;

import java.util.Random;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Inventory;
import worldofzuulfx.Items.Drink;

public class RockPaperScissors {

    Inventory i = new Inventory();
    private RockPaperScissorsMoves computerMove;
    private RockPaperScissorsMoves playerMove;
    private int moveComparison;

    /**
     * The play starts the game of Rock, Paper, Scissors. If player wins get
     * beer. Player lose decrement energy.
     */
    public void play() {
        RockPaperScissorsMoves playerMove = getPlayerMove();
        RockPaperScissorsMoves computerMove = computerMove();

        ConsoleInfo.setConsoleData("You chose " + playerMove);
        ConsoleInfo.setConsoleData("The computer chose " + computerMove);

        moveComparison = playerMove.compareMove(computerMove);
        switch (moveComparison) {
            case 0:
                ConsoleInfo.setConsoleData("Sorry. No winner, it was a tie.");
                break;
            case 1:
                ConsoleInfo.setConsoleData("You won because " + playerMove + " defeats " + computerMove);
                i.addItem(new Drink("Øl", 1, 1, true));
                break;
            case -1:
                ConsoleInfo.setConsoleData("You lost: " + computerMove + " defeats " + playerMove);
                break;

        }
    }

    /**
     * Takes the input from the user and makes it upper case, then checks if it
     * is a legit input. If the input it legit it will iterate through until the
     * right move is found
     *
     * @return the player's move
     */
    public RockPaperScissorsMoves getPlayerMove() {
        ConsoleInfo.setConsoleData("Rock, Paper or Scissors");
        
        //TODO
        String playerInput = "Get input";

        // Checks if the input is legit
        if (playerInput.equals("ROCK") || playerInput.equals("SCISSORS") || playerInput.equals("PAPER")) {

            switch (playerInput) {
                case "ROCK":
                    return RockPaperScissorsMoves.ROCK;
                case "SCISSORS":
                    return RockPaperScissorsMoves.SCISSORS;
                case "PAPER":
                    return RockPaperScissorsMoves.PAPER;
            }
        }
        return getPlayerMove();
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
}
