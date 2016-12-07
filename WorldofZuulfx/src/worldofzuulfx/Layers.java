package worldofzuulfx;

import javafx.scene.layout.Pane;

/**
 * Contains informations about every single layers which is drawn on throughout the game.
 *
 */
public class Layers {

    private Pane backgroundLayer;
    private Pane objectsLayer;
    private Pane playerLayer;
    private Pane inventoryLayer;

    public Layers(Pane backgroundLayer, Pane objectsLayer, Pane playerLayer, Pane inventoryLayer) {
        this.backgroundLayer = backgroundLayer;
        this.objectsLayer = objectsLayer;
        this.playerLayer = playerLayer;
        this.inventoryLayer = inventoryLayer;
    }

    public Pane getBackgoundLayer() {
        return this.backgroundLayer;
    }

    public Pane getObjectsLayer() {
        return this.objectsLayer;
    }

    public Pane getPlayerLayer() {
        return this.playerLayer;
    }

    /**
     * @return the inventoryLayer
     */
    public Pane getInventoryLayer() {
        return inventoryLayer;
    }
}
