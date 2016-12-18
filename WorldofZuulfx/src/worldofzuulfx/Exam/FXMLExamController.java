/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Exam;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class FXMLExamController implements Initializable {
    
    @FXML
    private Button btnNotes;
    @FXML
    private Button btnSubmit;
    @FXML
    private GridPane gridPane;
    
    private HashSet<ToggleGroup> toggleGroups;
    
    private ExamCallback examSubmitCallBack;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeToggleGroups();
    }
    
    public void setExamSubmittedCallback(ExamCallback examSubmitCallBack) {
        this.examSubmitCallBack = examSubmitCallBack;
    }

    /**
     * Iterates through every toggle group 
     * and checks if the correct radiobutton(Answer) has been choosen.
     * @param event
     */
    public void handleSubmit(ActionEvent event) {
        
        // Evaluate answers..
        int rightAnswerCounter = 0;
        
        for (ToggleGroup group : toggleGroups) {
            
            RadioButton selected = (RadioButton)group.getSelectedToggle();
            
            if (selected != null) {
                Boolean isRightAnswer = (Boolean)selected.getUserData();

                if (isRightAnswer != null) {
                    rightAnswerCounter++;
                }
            }
        }
        int grade = evaluateExam(rightAnswerCounter);
        
        // Execute the Call back method and give the grade as parameter.
        if (this.examSubmitCallBack != null) {
            this.examSubmitCallBack.examSubmittedCallback(grade);
        }
    }
    
    /**
     * Calculates a grade based on the correct answers
     * @param correctAnswers 
     * @return A grade
     */
    private int evaluateExam(int correctAnswers) {
        
        int grade;
        
        if (correctAnswers == 8) {
            grade = 12;
        } else if (correctAnswers == 7) {
            grade = 10;
        } else if (correctAnswers >= 5) {
            grade = 7;
        } else if (correctAnswers == 4) {
            grade = 4;
        } else if (correctAnswers == 3) {
            grade = 2;
        } else if (correctAnswers >= 1) {
            grade = 0;
        } else {
            grade = -3;
        }
        
        return grade;
    }
    
    private void initializeToggleGroups() {
        toggleGroups = new HashSet<>();
        
        for (Node node : getAllNodes(gridPane)) {
            
            if (node instanceof RadioButton) {
                RadioButton radBtn = (RadioButton)node;
                // if the radiobutton has an ID
                if (radBtn.getId() != null) {
                    radBtn.setUserData(true);
                }
                
                // Add the togglegroup to a collection for later use
                ToggleGroup tglGrp = radBtn.getToggleGroup();
                if (!toggleGroups.contains(tglGrp)) {
                    toggleGroups.add(tglGrp);
                }
            }
        }
    }
    
    /**
     * 
     * @param root the root to get the nodes from.
     * @return A list containing all nodes from a given root.
     */
    // http://stackoverflow.com/questions/24986776/how-do-i-get-all-nodes-in-a-scene-in-javafx
    private static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }
    /**
     * Fills a list with all nodes from a given parent.
     * @param parent The parent to get the nodes from.
     * @param nodes The list which the nodes are added to.
     */
    // http://stackoverflow.com/questions/24986776/how-do-i-get-all-nodes-in-a-scene-in-javafx
    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                // Recursive call
                addAllDescendents((Parent)node, nodes);
        }
    }
}
 