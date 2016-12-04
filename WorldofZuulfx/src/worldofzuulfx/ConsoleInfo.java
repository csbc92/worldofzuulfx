/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 */
public final class ConsoleInfo {

    private static StringProperty conInfo = new SimpleStringProperty();

    private static StringProperty itemInfo = new SimpleStringProperty();

    private static StringProperty questInfo = new SimpleStringProperty();
    
    private static StringProperty rpsInfo = new SimpleStringProperty();
    
    private static StringProperty ectsInfo = new SimpleStringProperty();

    // provide public access to the property
    public static StringProperty consoleProperty() {
        return conInfo;
    }

    public static void setConsoleData(String data) {
        conInfo.set(data);
//        conInfo.set(conInfo.get() + data + "\n");
    }

    public static String getConsoleData() {
        return conInfo.get();
    }

    public static StringProperty itemProperty() {
        return itemInfo;
    }

    public static void setItemData(String data) {
        itemInfo.set(data);
    }

    public static String getItemData() {
        return itemInfo.get();
    }

    public static StringProperty questProperty() {
        return questInfo;
    }

    public static String getQuestData() {
        return questInfo.get();
    }

    public static void setQuestData(String data) {
        questInfo.set(data);
    }
    
    public static StringProperty rpsProperty() {
        return rpsInfo;
    }

    public static String getRPSData() {
        return rpsInfo.get();
    }

    public static void setRPSData(String data) {
        rpsInfo.set(data);
    }
    
    public static StringProperty ectsProperty() {
        return ectsInfo;
    }

    public static String getECTSData() {
        return ectsInfo.get();
    }

    public static void setECTSData(String data) {
        ectsInfo.set(data);
    }
}
