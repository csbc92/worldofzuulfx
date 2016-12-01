
package worldofzuulfx.NPC;

import worldofzuulfx.Player;
import worldofzuulfx.Room;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import worldofzuulfx.Game;
import worldofzuulfx.Quest.Quest;

/**
 *
 * @author hjaltefromholtrindom
 */
public class PartyGuy extends NPC{
    int[] partyRNG = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private int random;
    
    public PartyGuy (String ID, String name, Image img){
        super(ID, name, img);
    }
    /**
    * teleports the player to the designated room
    */
    public void partyTime(Player with, Room room, ArrayList<Room> rooms, HashMap<String, Quest> q){
        if(this.getCurrentRoom() == with.getCurrentRoom()){
            with.navigateTo(room);
        }
        this.getCurrentRoom().removePerson(this);
        spawn(rooms, q);
    }    
    /**
    * adds partyguy to a random room
    */
    public void spawn(ArrayList<Room> rooms, HashMap<String, Quest> q){
            if(q.get("goToBookStoreQ").isCompleted()){
                setRandom((int)(Math.random()*rooms.size()));
                this.navigateTo(rooms.get(getRandom()));
            }
                 
    }
        
    public void setRandom(int r){
        this.random = r;
    }
    
    public int getRandom(){
        return random;
    }

}
