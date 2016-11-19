package worldofzuulfx.Quest;

import worldofzuulfx.Items.Item;

/**
 * The class represents a reward that a quest may return after it is completed.
 */
public class Reward {
    private final Item item;
    private final int ectsPoints;
    
    public Reward(Item item, int ectsPoints) {
        this.item = item;
        this.ectsPoints = ectsPoints;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getECTSPoints() {
        return this.ectsPoints;
    }
    
    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        
        if (this.item != null) {
            sBuilder.append("[Item: ").append(this.item.getDescription()).append("] ");
        }
        
        sBuilder.append("[ECTS-points: ").append(this.ectsPoints).append("]");
        
        return sBuilder.toString();
    }
}
