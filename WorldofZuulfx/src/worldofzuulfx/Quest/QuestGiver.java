package worldofzuulfx.Quest;

import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.Main;
import worldofzuulfx.Player;
import worldofzuulfx.Room;

public class QuestGiver implements NavigateListener {
    private Player player; 
    public QuestGiver(Player player){
        this.player = player;
        player.addNavigateListener(this);       
    }

    @Override
    public void navigated(NavigateEvent navEvent) {
//        Room room = navEvent.getNewRoom();
//        if (room.getID().equals("U170")){
//            Quest quest = Main.getGame().getQuestInventory().getAllGameQuests().get("U170Lecture");
//            }
//        if (room.getID().equals("U180")){
//            Quest quest = Main.getGame().getQuestInventory().getAllGameQuests().get("U180Lecture");
//            
//            
//        }
    }
}