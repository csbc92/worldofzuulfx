/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author JV
 */
public class Highscores {

//    private ArrayList<Score> highscoreList;
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

    public class Score {

        private String name;
        private Integer highscore;

        private Score(String name, Integer highscore) {
            this.name = name;
            this.highscore = highscore;
        }

        /**
         * @return the name
         */
        private String getName() {
            return name;
        }

        /**
         * @return the highscore
         */
        private Integer getHighscore() {
            return highscore;
        }

        @Override
        public String toString() {
            return this.name + " " + this.highscore + " points";
        }
    }

    public void add(String name, Integer highscore) {
        getHighscoreList().add(new Score(name, highscore));
        sortList();
        shortenList();

    }

    private void sortList() {
        Collections.sort(getHighscoreList(), Comparator.comparing(Score::getHighscore).reversed());
    }

    private void shortenList() {
        if (getHighscoreList().size() > topPlayers) {
            getHighscoreList().subList(topPlayers, getHighscoreList().size()).clear();
        }

    }

    public void saveHighscores() {
        Util.newPropFile();
        for (Score score : getHighscoreList()) {
            Util.setProp(score.getName(), (score.getHighscore().toString()));
        }
        Util.storeFile("highscore.data");
        
        
    }

    public void loadHighscores() {
        Util.newPropFile();
        Util.loadFile("highscore.data");
        Util.getPropFile();
        for (Map.Entry<Object, Object> en : Util.getPropFile().entrySet()) {
            this.add(en.getKey().toString(), Integer.parseInt(en.getValue().toString()));
        }
    }

}
