/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Exam;

import java.util.HashMap;

public class Question {

    private String description;
    private String answer;
    private String correctAnswer;
    private Boolean used;
    private String type;
    private HashMap<String, String> options;

    /**
     *
     * @param correctAnswer the correct answer.
     * @param description the question itself.
     * @param options possible answers.
     * @param type
     */
    public Question(String correctAnswer, String description, HashMap<String, String> options, String type) {
        this.correctAnswer = correctAnswer;
        this.description = description;
        this.options = options;
        this.used = false;
        this.type = type;
    }
    public Question() {
        
    }

    /**
     * 
     * @return True if the answer equals correctAnswer
     */
    public Boolean isCorrect() {
        if (getAnswer().equalsIgnoreCase(getCorrectAnswer())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param answer User's answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the options. Options is a HashMap containing question options.
     */
    public HashMap<String, String> getOptions() {
 //       HashMap<String, String> copy = new HashMap<>(getOptions());
 //       return copy;
        return options;
    }

    /**
     * @return the question type e.g. ISE COS or OOP
     */
    public String getType() {
        return type;
    }

    /**
     * @param used the used to set
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }
    
    public String getAnswer(){
       return answer;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer the correctAnswer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    /**
     * @return the used
     */
    public Boolean getUsed() {
        return used;
    }
}
