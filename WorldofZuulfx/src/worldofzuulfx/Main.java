/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;


import java.util.ArrayList;

import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import worldofzuulfx.sprites.Player;
import worldofzuulfx.sprites.SpriteBase.spriteActions;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileLoader;
import worldofzuulfx.tiles.TileMap;

/**
 *
 * @author cclausen
 */
public class Main extends Application {

    private AnimationTimer timer;
//    private DrawablePlayer player;
    private Scene scene;
    private TileMap tileMap;
    private static Game game;
    private Pane background;
    private Pane sprites;
    private HashMap<String, Room> rooms;
    private FXMLMainController controller;
    private String consoleTxt;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("FXMLMain.fxml"));
        controller = (FXMLMainController) fxmlLoader.getController();

        background = new Pane();
        sprites = new Pane();
        root.getChildren().add(background);
        root.getChildren().add(sprites);
        scene = new Scene(root);

        //       initiate();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

//        addInputControls(scene);
//        gameLoop();

        game = new Game(background, sprites, scene); //En instans af spillet oprettes.
 //       getGame().play(); // Metoden play fra klassen Game exekveres.   

    }

    private void gameLoop() {

        // game loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

//                updateSprites();
//
//                checkCollisions();
//
//                cleanupSprites();

            }

        };

        timer.start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

//    public void updateSprites() {
////        player.move();
//        
//        player.getSprite().updateUI();
//    }
//
//    public void checkCollisions() {
//        boolean result = false;
//        for (Tile tile : tileMap.getTileTerrain()) {
//            if (player.getSprite().collidesWith(tile)) {
//                result = true;
//            }
//        }
//    }
//
//    public void cleanupSprites() {
//
//    }
//
//    private void addInputControls(Scene scene) {
//
//        // keyboard handler: key pressed
//        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
//            if (key.getCode() == KeyCode.RIGHT) {
//                player.getSprite().move(spriteActions.RIGHT);
//
//            }
//            if (key.getCode() == KeyCode.LEFT) {
//                player.getSprite().move(spriteActions.LEFT);
//
//            }
//            if (key.getCode() == KeyCode.UP) {
//                player.getSprite().move(spriteActions.UP);
//
//            }
//            if (key.getCode() == KeyCode.DOWN) {
//                player.getSprite().move(spriteActions.DOWN);
//
//            }
//
//        });
//    }

    private void initiate() {
//                TileLoader tLoader = new TileLoader(new Image("http://i.imgur.com/XcTrhwJ.png"), 32, 32);
//        HashMap<Integer, Tile> tiles = tLoader.getTiles();
//
//        int[][] tileLayout = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 50, 50, 50, 50, 50, 50, 50, 50, 0},
//        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};
//
//        tileMap = new TileMap(tileLayout, tiles);
//        tileMap.draw(background);
//
//        player = new Player(sprites, new Image("http://i.imgur.com/zLwFeje.png"), 64, 64);
//        player.setDx(32);
//        player.setDy(16);
//        player.setCanCollide(true);
//        TileLoader tLoader = new TileLoader(new Image("http://i.imgur.com/E04tZvB.png"), 32, 32);
//        HashMap<Integer, Tile> tiles = tLoader.getTiles();
//
//        this.rooms = new HashMap<>();
//        DrawableRoom canteen = new DrawableRoom("Canteen", "Canteen", tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
//        {0, 5, 13, 49, 57, 13, 13, 117, 21, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
//        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
//        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
//        });
//        rooms.put(canteen.getID(), canteen);
//        canteen.draw(background);
//
//        player = new DrawablePlayer("Player-name", sprites, new Image("http://i.imgur.com/zLwFeje.png"), 64.0, 64.0);
//        player.getSprite().setCanCollide(true);
//        player.getSprite().setDx(32);
//        player.getSprite().setDy(16);
//        player.navigateTo(canteen);

    }

    public static Game getGame() {
        return game;
    }



    /**
     * @return the controller
     */
    public FXMLMainController getController() {
        return controller;
    }

}
