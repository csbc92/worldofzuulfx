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
import worldofzuulfx.sprites.SpriteBase.spriteActions;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileLoader;
import worldofzuulfx.tiles.TileMap;

/**
 *
 * @author cclausen
 */
public class Main extends Application {

    private Scene scene;
    private static Game game;
    private Pane background;
    private Pane sprites;
    private FXMLMainController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(getClass().getResource("FXMLMain.fxml"));
        scene = new Scene(root);

        //       initiate();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
 
     //   game = new Game(background, sprites, scene); //En instans af spillet oprettes.
 
    }

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
