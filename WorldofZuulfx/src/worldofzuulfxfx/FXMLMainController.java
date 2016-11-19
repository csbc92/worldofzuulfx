/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfxfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import worldofzuulfx.ConsoleInfo;

/**
 * FXML Controller class
 *
 * @author JV
 */
public class FXMLMainController implements Initializable {

    @FXML
    private AnchorPane masterPane;
    @FXML
    private TextArea taConsol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        taConsol.textProperty().bind(ConsoleInfo.consoleProperty());
    }    

    @FXML
    private void handlePaneClick(MouseEvent event) {
    }
    
}
