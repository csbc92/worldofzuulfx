
package worldofzuulfx.Highscore;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuulfx.Util;

/**
 * This class handles all aspects of Highscores. Add highscore, Remove highscore
 * and Sort highscore
 *
 */
public class Highscores {

    private ObservableList<Score> highscoreList;
    private final Integer topPlayers;

    /**
     *
     * @param topNum The number of Players to be shown on the highscore board
     */
    public Highscores(Integer topNum) {
        this.highscoreList = FXCollections.observableArrayList();
        this.topPlayers = topNum;
    }

    /**
     * @return the highscoreList
     */
    public ObservableList<Score> getHighscoreList() {
        return highscoreList;
    }

    /**
     * Adds a new Highscore, then sorts the list based on points and then
     * shortens the list to a given number based on topPlayers.
     *
     * @param name The name of the Player
     * @param highscore The points which the player obtained during the game
     */
    public void add(String name, Integer highscore) {
        getHighscoreList().add(new Score(name, highscore));
        sortList();
        shortenList();

    }

    /**
     *
     */
    private void sortList() {
        Collections.sort(getHighscoreList(), Comparator.comparing(Score::getHighscore).reversed());
    }

    private void shortenList() {
        //Checks if the Highscore list exceeds the numbers of allowed player on the Highscore list.
        if (getHighscoreList().size() > topPlayers) {
            getHighscoreList().subList(topPlayers, getHighscoreList().size()).clear();
        }

    }

    /**
     * Stores the highscorelist in a file named "highcore.data".
     */
    public void saveHighscores() {
        // First a new Properties Object is created
        // Read more about this in the Util-class.
        Util.newPropFile();
        // Iterate through the highscorelist and adds a new property into the property-object.
        for (Score score : getHighscoreList()) {
            Util.setProp(score.getName(), (score.getHighscore().toString()));
        }
        // Saves the property-object as a file.
        Util.storeFile("highscore.data");
    }

    /**
     * Loads the highscores from a file named highscore.data First a
     * property-object is created, which is later loaded with data from the
     * file.
     */
    public void loadHighscores() {
        Util.newPropFile();
        Util.loadFile("highscore.data");
        // Iterates through the set, which contain highscore informations.
        // which is added to the observable list "highscoreList".
        for (Map.Entry<Object, Object> en : Util.getPropFile().entrySet()) {
            this.add(en.getKey().toString(), Integer.parseInt(en.getValue().toString()));
        }
    }

}
