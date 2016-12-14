package worldofzuulfx.Minigame;

/**
 * The moves in the minigame Rock, Paper or Scissors.
 */
//http://www.wikihow.com/Make-a-Rock,-Paper,-Scissors-Game-in-Java
public enum RockPaperScissorsMoves {
    ROCK, PAPER, SCISSORS;

    /**
     * The move the player inputs is compared with the otherMove, and returns
     * the results of the comparison.
     *
     * @param otherMove
     * @return
     */
    public int compareMove(RockPaperScissorsMoves otherMove) {
        // If it is a tie it will return 0.
        if (this == otherMove) {
            return 0;
        }

        // The game checks the different cases of the enum, then defines the result of that comparison.
        switch (this) {
            case ROCK:
                return (otherMove == SCISSORS ? 1 : -1);
            case PAPER:
                return (otherMove == ROCK ? 1 : -1);
            case SCISSORS:
                return (otherMove == PAPER ? 1 : -1);
        }

        return 0;

    }
}
