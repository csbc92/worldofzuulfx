/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author JV
 */
public class Highscore {

    private ArrayList<Score> highscoreList;
    private final Integer topPlayers;

    /**
     *
     * @param topNum The number of Players to be shown on the highscore board
     */
    public Highscore(Integer topNum) {
        this.highscoreList = new ArrayList<>();
        this.topPlayers = topNum;
    }

    private class Score {

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
    }

    public void add(String name, Integer highscore) {
        highscoreList.add(new Score(name, highscore));
        sortList();
        shortenList();

    }

    private void sortList() {
        Collections.sort(highscoreList, Comparator.comparing(Score::getHighscore).reversed());
    }

    private void shortenList() {
        if (highscoreList.size() > topPlayers) {
            highscoreList.subList(topPlayers, highscoreList.size()).clear();
        }

    }

    public void printAll() {
        for (Score score : highscoreList) {
            System.out.println(score.getName() + " " + score.getHighscore());
        }
    }
}
