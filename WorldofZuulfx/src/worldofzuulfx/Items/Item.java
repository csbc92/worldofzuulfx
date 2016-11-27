/*
 * World of zuul, item class, 
 */
package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Player;
import worldofzuulfx.sprites.SpriteBase;


 
/**
 * @author march
 * @version (15/10/2016)
 */

public abstract class Item extends SpriteBase{
    private int weight; 
    protected String description;
    private Boolean isLocked;
    private String id;


    public Item(Image image, String id, String description, int weight) {
        super(image);
        super.setCanCollide(true);
        this.id = id;
        this.description = description;
        this.weight = weight;
        isLocked = false;

    }

    public String getDescription() {
    	return description;
    }

    /**
     * @return the isLocked
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
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }
    
    public abstract void use(Player player);

    /**
     * @return the ID
     */
    public String getID() {
        return id;
    }
}


