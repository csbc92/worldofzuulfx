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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import worldofzuulfx.Interfaces.BarValueListener;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import worldofzuulfx.Items.ItemFactory;

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
    @FXML
    private Pane pMenu;
    @FXML
    private Text tItemInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeConsole();
        gpMain.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        tItemInfo.textProperty().bind(ConsoleInfo.itemProperty());
        pMenu.setVisible(true);
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

            if (key.getCode() == KeyCode.A) {
                game.getPlayer().getInventory().nextItem();
                game.getPlayer().getInventory().draw(false);
            }
            if (key.getCode() == KeyCode.Z) {
                game.getPlayer().getInventory().previousItem();
                game.getPlayer().getInventory().draw(false);
            }

            if (key.getCode() == KeyCode.S) {
                game.getPlayer().pickupItem(ItemFactory.makeBeer());

            }
            if (key.getCode() == KeyCode.D) {
                game.getPlayer().useItem(game.getPlayer().getInventory().getSelectedItem());

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

        pMenu.setVisible(false);
        pBackground.setVisible(true);
        pObjects.setVisible(true);
        pSprites.setVisible(true);

        Layers layers = new Layers(pBackground, pObjects, pSprites, pInventory);
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
