/*
 * World of zuul, item class, 
 */
package worldofzuulfx.Items;

import worldofzuulfx.Player;


 
/**
 * @author march
 * @version (15/10/2016)
 */

public abstract class Item{
    private int weight; 
    protected String description;
    private Boolean isLocked;


    public Item(String description, int weight) {

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
}


