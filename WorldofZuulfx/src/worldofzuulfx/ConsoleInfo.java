/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public final class ConsoleInfo {

    private static StringProperty conInfo = new SimpleStringProperty();

    private static StringProperty itemInfo = new SimpleStringProperty();

    private static StringProperty questInfo = new SimpleStringProperty();
    
    private static StringProperty rpsInfo = new SimpleStringProperty();
    
    private static StringProperty ectsInfo = new SimpleStringProperty();
    
    private static StringProperty roomInfo = new SimpleStringProperty();

    /**
     * Provides public access to the Console property
     * It's data are shown in the Console(TextArea) - taConsole
     * e.g. "You are outside"
     * @return
     */
    public static StringProperty consoleProperty() {
        return conInfo;
    }

    /**
     * Sets the Console's String data
     * @param data The text to be shown
     */
    public static void setConsoleData(String data) {
        conInfo.set(data);
    }

    /**
     * Access the Console's string Data
     * @return
     */
    public static String getConsoleData() {
        return conInfo.get();
    }

    /**
     * Provides public access to the Item property
     * It's data are shown in the Item info text - tItemInfo
     * e.g. "Drink the beer"
     * @return
     */
    public static StringProperty itemProperty() {
        return itemInfo;
    }

    /**
     * Sets the Item's String data
     * @param data
     */
    public static void setItemData(String data) {
        itemInfo.set(data);
    }

    /**
     * Access the Item's String data
     * @return
     */
    public static String getItemData() {
        return itemInfo.get();
    }

    /**
     * Provides public access to the Quest property
     * It's data are shown in the Quest info label - lQuest
     * e.g. "Go to U170"
     * @return
     */
    public static StringProperty questProperty() {
        return questInfo;
    }

    /**
     * Access the Quest's String data
     * @return
     */
    public static String getQuestData() {
        return questInfo.get();
    }

    /**
     * Sets the Quest's String data
     * @param data The string
     */
    public static void setQuestData(String data) {
        questInfo.set(data);
    }
    
    /**
     * Provides public access to the RPS property
     * It's data are shown in the Console(TextArea) - taConsole
     * e.g. "Rock(R), Paper(P) or Scissor(S)"
     * @return
     */
    public static StringProperty rpsProperty() {
        return rpsInfo;
    }

    /**
     * Access RPS's String data
     * @return
     */
    public static String getRPSData() {
        return rpsInfo.get();
    }

    /**
     * Sets the RPS's String data
     * @param data
     */
    public static void setRPSData(String data) {
        rpsInfo.set(data);
    }
    
    /**
     * Provides public access to the ECTS property
     * It's data are shown in the ECTS textField - tECTS
     * e.g. "20"
     * @return
     */
    public static StringProperty ectsProperty() {
        return ectsInfo;
    }

    /**
     * Access the ECTS's String data
     * @return
     */
    public static String getECTSData() {
        return ectsInfo.get();
    }

    /**
     * Sets the ECTS's String data
     * @param data
     */
    public static void setECTSData(String data) {
        ectsInfo.set(data);
    }
    
    /**
     * Provides public access to the Room property
     * It's data are shown in the Current room textfield - tRoom
     * e.g. "Gydehutten"
     * @return
     */
    public static StringProperty roomProperty() {
        return roomInfo;
    }

    /**
     * Access the Room's String data
     * @return
     */
    public static String getRoomData() {
        return roomInfo.get();
    }

    /**
     * Sets the Room's String data
     * @param data
     */
    public static void setRoomData(String data) {
        roomInfo.set(data);
    }
}
