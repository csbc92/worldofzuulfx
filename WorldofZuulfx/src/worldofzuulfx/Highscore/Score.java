
package worldofzuulfx.Highscore;

public class Score {

    private String name;
    private Integer highscore;

    Score(String name, Integer highscore) {
        this.name = name;
        this.highscore = highscore;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the highscore
     */
    public Integer getHighscore() {
        return highscore;
    }

    /**
     * This is used when displaying the objects in the listview "lvHighscores"
     * @return
     */
    @Override
    public String toString() {
        return this.name + " " + this.highscore + " points";
    }
}
