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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import sun.plugin.javascript.navig4.Layer;

/**
 *
 * @author JV
 */
public class FXMLMainController implements Initializable {

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
        taConsol.textProperty().bind(ConsoleInfo.consoleProperty());
        taConsol.setDisable(true);
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
        Layers.backgroundLayer = pBackground;
        Layers.spritesLayer = pSprites;
        Layers.objectsLayer = pObjects;
        Layers.inventorysLayer = pInventory;
        addInputControls(pBackground.getScene());
        game = new Game(); //En instans af spillet oprettes.
    }

}
