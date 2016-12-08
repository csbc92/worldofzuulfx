package worldofzuulfx.Quest;

import worldofzuulfx.Items.Item;

/**
 * The class represents a reward that a quest may return after it is completed.
 */
public class Reward {
    private final Item item;
    private final int ectsPoints;
    
    /**
     * Instantiates a Reward-object. 
     * A Reward can be an Item and ECTS-points or just one of them.
     * @param item 
     * @param ectsPoints
     */
    public Reward(Item item, int ectsPoints) {
        this.item = item;
        this.ectsPoints = ectsPoints;
    }
    
    /**
     *
     * @return The Item (Reward)
     */
    public Item getItem() {
        return this.item;
    }
    
    /**
     *
     * @return The ECTS-points (Reward)
     */
    public int getECTSPoints() {
        return this.ectsPoints;
    }
}
