/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;

/**
 *
 * @author JV
 */
public class FXMLMainController implements Initializable {

    @FXML
    private AnchorPane masterPane;
    @FXML
    private TextArea taConsol;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        taConsol.textProperty().bind(ConsoleInfo.consoleProperty());
        
    }

    @FXML
    private void handlePaneClick(MouseEvent event) {

    }

    public void write(String s) {
        taConsol.appendText(s);
    }

    public void writeln(String s) {
        write(s + "\n");
    }

}
