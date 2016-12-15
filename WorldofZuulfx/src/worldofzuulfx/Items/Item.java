package worldofzuulfx.Items;

import javafx.scene.image.Image;
import worldofzuulfx.Player;
import worldofzuulfx.sprites.SpriteBase;

public abstract class Item extends SpriteBase {

    private int weight;
    protected String description;
    private Boolean isLocked;
    private String id;

    /**
     * Instatiates an item - however since this is a abstract class the class
     * itself can not be instantiated but a subclass of this can.
     * @param image The Image to be shown
     * @param id The ID of the Item
     * @param description The description of Item
     * @param weight The weight of the Item
     */
    public Item(Image image, String id, String description, int weight) {
        super(image);
        super.setCanCollide(true);
        this.id = id;
        this.description = description;
        this.weight = weight;
        isLocked = false;

    }

    /**
     *
     * @return The description of the Item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return True if the Item is locked otherwise false
     */
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * @param isLocked the isLocked to set
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * @return The weight of the item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * This abstract method is implemented in all classe which extends this Item
     * class. The method gives the functionality to use different items in
     * different ways.
     *
     * @param player The player who uses the item.
     */
    public abstract void use(Player player);

    /**
     * @return The ID of item
     */
    public String getID() {
        return id;
    }

    @Override
    public void collides(SpriteBase spriteBase) {
        
        if (spriteBase instanceof Player) {
            Player player = (Player)spriteBase;
            // Pick up the item
            player.pickupItem(this);

            // Reset the nextPos since a collision was detected

            player.move(player.getX(), player.getY());
        }
    }
}
