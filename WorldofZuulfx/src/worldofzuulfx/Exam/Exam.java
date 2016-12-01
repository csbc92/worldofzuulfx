/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Exam;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Persistence.XMLSerial;

public class Exam {

    private ArrayList<Question> questionList;
    private double QuestionPoint;
    private double points;
    private final int maxPoints;
    private double requiredPoints;
    private Properties prop;
    private XMLSerial serial;

    /**
     *
     * @param maxPoints - maximum point.
     */
    public Exam(int maxPoints) {
        this.points = 0;
        this.maxPoints = maxPoints;
        this.requiredPoints = 50;
        this.questionList = new ArrayList<>();
//        oldSetup(); // Denne skal slettes før aflevering!!!
        serial = new XMLSerial();
//        serial.serialQuestion(questionList); // Denne skal måske slettes!
        this.questionList = serial.deserialQuestion();
        QuestionPoint = maxPoints / questionList.size();

    }

    public void oldSetup() {
    }

    /**
     * The exam begin. One question is asked at a time. The user is requried to
     * answer the question. There are several options per question.
     */
    public void play() {
        // TODO Exam
        ConsoleInfo.clearData();
        ConsoleInfo.setConsoleData("Welcome to your exam");
        ConsoleInfo.setConsoleData("");
        for (Question question : questionList) {
            ConsoleInfo.setConsoleData(question.getDescription());
            listOptions(question);
            //TODO Giv et svar til eksamen
//            question.setAnswer(Starter.gui.getTxt());

        }
        result();
    }

    /**
     * Iterates through the questionList. The user obtains points for every
     * correct answer.
     */
    public void result() {
        for (Question question : questionList) {
            if (question.isCorrect()) {
                points += QuestionPoint;
            }
        }

        if (points >= requiredPoints) {
            ConsoleInfo.setConsoleData("Congratulations, you have passed the exam!");
        } else {
            ConsoleInfo.setConsoleData("Sorry, you did not pass the exam...");
        }
        ConsoleInfo.setConsoleData("You got " + points + " of " + maxPoints + " points");
    }

    /**
     * Prints out question options.
     *
     * @param question
     */
    public void listOptions(Question question) {
        for (Entry<String, String> option : question.getOptions().entrySet()) {
            ConsoleInfo.setConsoleData(option.getKey() + "  " + option.getValue());
        }
    }

    /**
     *
     * @param type
     * @return
     */
    public String getRandomNote(String type) {
        ArrayList<Question> list;
        Boolean found;
        int randomIndex;

        found = false;
        list = new ArrayList<>();
        // sorts the Question list based on the given type.
        for (Question question : questionList) {
            if (question.getType().equalsIgnoreCase(type)) {
                list.add(question);
            }
        }
        // returns a random Note, which has not been used.
        while (!found) {
            randomIndex = (int) (Math.random() * list.size());
            if (!list.get(randomIndex).getUsed()) {
                list.get(randomIndex).setUsed(true);
                return list.get(randomIndex).getDescription() + "\n"
                        + list.get(randomIndex).getOptions().get(list.get(randomIndex).getCorrectAnswer());
            }
        }
        return "";
    }
}
