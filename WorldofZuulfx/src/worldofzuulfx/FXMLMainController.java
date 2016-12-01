/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import worldofzuulfx.Highscores.Score;

/**
 *
 * @author JV
 */
public class FXMLMainController implements Initializable, BarValueListener {

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
    @FXML
    private ListView<Score> lvHighscore;

    private Highscores highscores;
    private int interval;
    @FXML
    private Pane pInfo;
    @FXML
    private ProgressBar progEnergy;
    @FXML
    private Text tfTimeLeft;
    private Stage stage;
    private Timer gameTimer;
    @FXML
    private Label lQuest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeConsole();
        highscores = new Highscores(5);
        highscores.loadHighscores();
        lvHighscore.itemsProperty().set(highscores.getHighscoreList());

//        gpMain.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        tItemInfo.textProperty().bind(ConsoleInfo.itemProperty());
        lQuest.textProperty().bind(ConsoleInfo.questProperty());
        pMenu.setVisible(true);
        pInfo.setVisible(false);

    }

    private void addInputControls(Scene scene) {

        // keyboard handler: key pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (!game.isFinished()) {

                if (key.getCode() == KeyCode.RIGHT) {
                    game.getPlayer().setNearNPC(null);
                    game.getPlayer().setDroppedItem(false);
                    game.getPlayer().setNextPosX(game.getPlayer().getBounds().getX() + game.getPlayer().getDx());

                }
                if (key.getCode() == KeyCode.LEFT) {
                    game.getPlayer().setNearNPC(null);
                    game.getPlayer().setDroppedItem(false);
                    game.getPlayer().setNextPosX(game.getPlayer().getBounds().getX() - game.getPlayer().getDx());

                }
                if (key.getCode() == KeyCode.UP) {
                    game.getPlayer().setNearNPC(null);
                    game.getPlayer().setDroppedItem(false);
                    game.getPlayer().setNextPosY(game.getPlayer().getBounds().getY() - game.getPlayer().getDy());

                }
                if (key.getCode() == KeyCode.DOWN) {
                    game.getPlayer().setNearNPC(null);
                    game.getPlayer().setDroppedItem(false);
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

                if (key.getCode() == KeyCode.D) {
                    game.getPlayer().drop(game.getPlayer().getInventory().getSelectedItem());

                }
                if (key.getCode() == KeyCode.U) {
                    game.getPlayer().useItem(game.getPlayer().getInventory().getSelectedItem());

                }
            }

        });
    }

    @FXML
    private void onClickNewGame(ActionEvent event) {

        pMenu.setVisible(false);
        pBackground.setVisible(true);
        pObjects.setVisible(true);
        pSprites.setVisible(true);
        pInfo.setVisible(true);

        Layers layers = new Layers(pBackground, pObjects, pSprites, pInventory);
        addInputControls(pBackground.getScene());
        game = new Game(layers); //En instans af spillet oprettes.

        // Listen for when the players energy changes.
        game.getPlayer().getEnergyBar().addBarValueListener(this);
        progEnergy.setProgress(1);
        
        // Create a timer which keeps decreasing the timeleft
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int value = game.getPlayer().getTimeLeft();
                if (value == 1 || game.isFinished()) {
                    gameTimer.cancel();
                    game.setFinished();
                }
                game.getPlayer().setTimeLeft(--value);
                tfTimeLeft.setText(value + " sec");
            }
        }, 1000, 1000);
        // Creates a listener which listens for onClose event.
        stage = (Stage) pInfo.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                gameTimer.cancel();
            }
        });

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
        progEnergy.setProgress((double) bar.getValue() / 100);
    }
}
