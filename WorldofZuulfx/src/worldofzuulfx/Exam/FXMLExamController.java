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
import worldofzuulfx.Main;

public class FXMLExamController implements Initializable {
    
    @FXML
    private Button btnNotes;
    @FXML
    private Button btnSubmit;
    @FXML
    private GridPane gridPane;
    
    private HashSet<ToggleGroup> toggleGroups;
    private int grade;
    
    private ExamInterface examInterface;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeToggleGroups();
    }
    
    public void setExamInterface(ExamInterface examInterface) {
        this.examInterface = examInterface;
    }

    public void handleSubmit(ActionEvent event) {
        
        // Evaluate answers..
        int rightAnswerCounter = 0;
        
        for (ToggleGroup group : toggleGroups) {
            
            RadioButton selected = (RadioButton)group.getSelectedToggle();
            
            if (selected != null) {
                Boolean b = (Boolean)selected.getUserData();

                if (b != null) {
                    rightAnswerCounter++;
                }
            }
        }
        evaluate(rightAnswerCounter);
        
        this.examInterface.examSubmitted(grade);
        
    }
    
    private int evaluate(int correctAnswers) {
        
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
    
    public int getGrade() {
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
    
    
    // http://stackoverflow.com/questions/24986776/how-do-i-get-all-nodes-in-a-scene-in-javafx
    private static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

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
 