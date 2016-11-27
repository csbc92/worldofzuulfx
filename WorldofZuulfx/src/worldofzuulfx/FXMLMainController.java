/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import sun.plugin.javascript.navig4.Layer;
import worldofzuulfx.Interfaces.BarValueListener;

/**
 *
 * @author JV
 */
public class FXMLMainController implements Initializable, BarValueListener{

    @FXML
    private TextArea taConsol;
    private Game game;
    @FXML
    private AnchorPane apConsole;
    @FXML
    private GridPane gpMain;
    @FXML
    private Button butNewGame;
    @FXML
    private Pane pSprites;
    @FXML
    private Pane pBackground;
    @FXML
    private Pane pObjects;
    @FXML
    private Pane pInventory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeConsole();
    }

    private void addInputControls(Scene scene) {

        // keyboard handler: key pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                game.getPlayer().setNextPosX(game.getPlayer().getBounds().getX() + game.getPlayer().getDx());
            }
            if (key.getCode() == KeyCode.LEFT) {
                game.getPlayer().setNextPosX(game.getPlayer().getBounds().getX() - game.getPlayer().getDx());

            }
            if (key.getCode() == KeyCode.UP) {
                game.getPlayer().setNextPosY(game.getPlayer().getBounds().getY() - game.getPlayer().getDy());

            }
            if (key.getCode() == KeyCode.DOWN) {
                game.getPlayer().setNextPosY(game.getPlayer().getBounds().getY() + game.getPlayer().getDy());
            }

        });
    }
    
    public void write(String s) {
        taConsol.appendText(s);
    }

    public void writeln(String s) {
        write(s + "\n");
    }

    @FXML
    private void onClickNewGame(ActionEvent event) {

        butNewGame.setVisible(false);
        Layers layers = new Layers(pBackground, pObjects, pSprites);
        addInputControls(pBackground.getScene());
        game = new Game(layers); //En instans af spillet oprettes.
        
        // Listen for when the players energy changes.
        game.getPlayer().getEnergyBar().addBarValueListener(this);
    }

    private void initializeConsole() {
        taConsol.textProperty().bind(ConsoleInfo.consoleProperty());
        
        taConsol.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                //TODO: Scroll to the bottom
                taConsol.positionCaret(Integer.MAX_VALUE);
            }
        });
    }

    @Override
    public void barValueChanged(Bar bar) {
        // TODO: Update the UI with the new energyvalue
        int val = bar.getValue();
    }

}
