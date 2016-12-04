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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.Minigame.RockPaperScissorsMoves;

/**
 *
 * @author JV
 */
public class FXMLMainController implements Initializable, BarValueListener {

    @FXML
    private TextArea taConsol;
    private Game game;
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
    @FXML
    private TabPane tabControl;
    @FXML
    private Tab tabGame;
    @FXML
    private Tab tabNewGame;
    private AnchorPane pmain;
    @FXML
    private AnchorPane pMain;
    @FXML
    private Text tHealth;
    @FXML
    private Pane pRPS;
    @FXML
    private Button butRock;
    @FXML
    private Button butPaper;
    @FXML
    private Button butScissor;
    @FXML
    private Text tECTS;
    @FXML
    private Button butHighscore;
    @FXML
    private Tab tabExam;
    @FXML
    private Tab tabHighscore;
    @FXML
    private Button butBack;
    @FXML
    private RadioButton rbNormal;
    @FXML
    private ToggleGroup tgGameLevel;
    @FXML
    private RadioButton rbAbnormal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeConsole();
        highscores = new Highscores(5);
        highscores.loadHighscores();
        lvHighscore.itemsProperty().set(highscores.getHighscoreList());
        
        rbNormal.setUserData(0);
        rbAbnormal.setUserData(1);

        tItemInfo.textProperty().bind(ConsoleInfo.itemProperty());
        lQuest.textProperty().bind(ConsoleInfo.questProperty());
        pRPS.setVisible(false);
        tabControl.getSelectionModel().select(tabNewGame);

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
                if (game.getRPS() != null) {

                    if (key.getCode() == KeyCode.R) {
                        game.getRPS().setPlayerMove(RockPaperScissorsMoves.ROCK);
                    }
                    if (key.getCode() == KeyCode.P) {
                        game.getRPS().setPlayerMove(RockPaperScissorsMoves.PAPER);
                    }
                    if (key.getCode() == KeyCode.S) {
                        game.getRPS().setPlayerMove(RockPaperScissorsMoves.SCISSORS);
                    }
                }
            }
        });
    }

    @FXML
    private void onClickNewGame(ActionEvent event) {

        initializeGame();

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

    private void initializeGame() {
        int gameLevel;
        // Sets the correct panes visible.
        pBackground.setVisible(true);
        pObjects.setVisible(true);
        pSprites.setVisible(true);
        pInfo.setVisible(true);
        tabControl.getSelectionModel().select(tabGame);
        pMain.requestFocus(); // Important that pMain request the focus otherwise the eventhandler will not work.

        Layers layers = new Layers(pBackground, pObjects, pSprites, pInventory);
        addInputControls(pBackground.getScene());
        
        // GameLevel chooses which game to be loaded - Normal or Hogwarts mode.
        gameLevel = (Integer) tgGameLevel.selectedToggleProperty().get().getUserData();
                
        game = new Game(layers,gameLevel ); //En instans af spillet oprettes.

        // Listen for when the players energy changes.
        game.getPlayer().getEnergyBar().addBarValueListener(this);
        progEnergy.setProgress(1);
        tHealth.setText(String.valueOf(game.getPlayer().getHp().getValue()));
        tECTS.textProperty().bind(ConsoleInfo.ectsProperty());
    }

    @Override
    public void barValueChanged(Bar bar) {
        progEnergy.setProgress((double) bar.getValue() / 100);
        tHealth.setText(String.valueOf(game.getPlayer().getHp().getValue()));
    }

    @FXML
    private void onTabClick(MouseEvent event) {
        pMain.requestFocus();
    }

    @FXML
    private void onbutHighscoreClick(ActionEvent event) {
        tabControl.getSelectionModel().select(tabHighscore);
    }

    @FXML
    private void onbutBackClick(ActionEvent event) {
        tabControl.getSelectionModel().select(tabNewGame);
    }
}
