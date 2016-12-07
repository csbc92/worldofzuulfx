package worldofzuulfx.Interfaces;

import worldofzuulfx.Events.NavigateEvent;

/**
 * An interface which gives the functionality to listen for when a player navigates to another room.
 * navigated describes what happens when a Player navigates.
 */
public interface NavigateListener {

    public void navigated(NavigateEvent event);

}
