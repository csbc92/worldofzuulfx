/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.scene.layout.Pane;
import worldofzuulfx.Interfaces.BarValueListener;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import worldofzuulfx.Exam.FXMLExamController;
import worldofzuulfx.Highscores.Score;
import worldofzuulfx.Minigame.RockPaperScissorsMoves;
import worldofzuulfx.Exam.ExamCallback;

public class FXMLMainController implements Initializable, BarValueListener, ExamCallback {

    private Stage stage;
    private Timer gameTimer;
    private Highscores highscores;
    private Game game;

    @FXML
    private TextArea taConsol;
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
    @FXML
    private Pane pInfo;
    @FXML
    private ProgressBar progEnergy;
    @FXML
    private Label lQuest;
    @FXML
    private TabPane tabControl;
    @FXML
    private Tab tabGame;
    @FXML
    private Tab tabNewGame;
    @FXML
    private AnchorPane pMain;
    @FXML
    private Text tHealth;
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
    @FXML
    private Text tfTimeLeft;
    @FXML
    private Text tRoom;
    @FXML
    private Tab tabEndGame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeConsole();
        initializeExamTab();
        initialzeHighscore();
        // Sets the userData for the two radiobutton. 
        //The selected value is later used to choose which game mode to be played
        rbNormal.setUserData(0);
        rbAbnormal.setUserData(1);
        // Binds properties to their respective textfield
        tItemInfo.textProperty().bind(ConsoleInfo.itemProperty());
        lQuest.textProperty().bind(ConsoleInfo.questProperty());
        tabControl.getSelectionModel().select(tabNewGame);

    }

    private void initializeExamTab() {
        try {
            // Load the FXML document and Controller from another file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("worldofzuulfx/Exam/FXMLExam.fxml"));
            Pane content = fxmlLoader.load();
            FXMLExamController controller = fxmlLoader.getController();

            controller.setExamSubmittedCallback(this);

            // Set the tab's content
            tabExam.setContent(content);

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
            System.exit(0);
        }
    }

    /**
     * Shifts the current view to the exam view.
     */
    public void executeExam() {
        tabControl.getSelectionModel().select(tabExam);
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
        if (game == null) {
            initializeGame();

            // Create a timer which keeps decreasing the timeleft variable
            gameTimer = new Timer();
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int value = game.getPlayer().getTimeLeft();
                    // Checks if the time ran out or if the game was finished for som other reason
                    if (value == 1 || game.isFinished()) {
                        gameTimer.cancel();
                        game.setFinished();
                        tabControl.getSelectionModel().select(tabEndGame);

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
    }

    private void initializeConsole() {
        taConsol.textProperty().bind(ConsoleInfo.consoleProperty());
    }

    private void initialzeHighscore() {
        // Creates an instance of the Highscore-class and loads all saved highscores.
        highscores = new Highscores(5);
        highscores.loadHighscores();
        lvHighscore.itemsProperty().set(highscores.getHighscoreList());
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

        game = new Game(layers, gameLevel); //En instans af spillet oprettes.

        // Listen for when the players energy changes.
        game.getPlayer().getEnergyBar().addBarValueListener(this);
        progEnergy.setProgress(1);
        tHealth.setText(String.valueOf(game.getPlayer().getHp().getValue()));
        tECTS.textProperty().bind(ConsoleInfo.ectsProperty());
        tRoom.textProperty().bind(ConsoleInfo.roomProperty());
    }

    /**
     * Updates the UI - i.e. the energy-bar and the Health textfield
     *
     * @param bar
     */
    @Override
    public void barValueChanged(Bar bar) {
        // Divides the current value of the bar by 100.
        // The progressbar only accepts value in the range 0..1
        progEnergy.setProgress((double) bar.getValue() / 100);
        tHealth.setText(String.valueOf(game.getPlayer().getHp().getValue()));
        // If the current value of the Energy-bar is below 1 then
        if (bar.getValue() < 1 && game.getPlayer().getHp().getValue() == 1) {
            game.setFinished();
        }
    }

    @FXML
    private void onTabClick(MouseEvent event) {
        // Makes sure that pMain (The game) has focus otherwise the keyboard inputs won't be captured
        pMain.requestFocus();
    }

    @FXML
    private void onbutHighscoreClick(ActionEvent event) {
        // Changes the current view to Highscore view
        tabControl.getSelectionModel().select(tabHighscore);
    }

    @FXML
    private void onbutBackClick(ActionEvent event) {
        // Changes Highscore view to Mainmenu
        tabControl.getSelectionModel().select(tabNewGame);
    }

    @Override
    public void examSubmittedCallback(int grade) {
        // Calculate highscore
        Player player = game.getPlayer();
        int score = (player.getTimeLeft() + player.getEnergy()) * player.getHp().getValue() * grade;

        // The score cannot be negative
        if (score < 0) {
            score = 0;
        }

        highscores.add(player.getName(), score);
        highscores.saveHighscores();

        // Select highscore tab
        tabControl.getSelectionModel().select(tabHighscore);
    }
}
