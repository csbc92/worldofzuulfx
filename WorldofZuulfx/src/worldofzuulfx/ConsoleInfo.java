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
    // methods that set/format logData based on changes from your UI

    // provide public access to the property
    public static StringProperty consoleProperty() {
        return conInfo;
    }

    public static void setConsoleData(String data) {
        conInfo.set(conInfo.get() + data + "\n");
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

    public static void clearData() {
        // info.set("");
    }

}
