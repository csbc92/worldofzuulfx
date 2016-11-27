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
 * @author JV
 */
public final class ConsoleInfo {

    private static StringProperty info = new SimpleStringProperty();
//    // methods that set/format logData based on changes from your UI

    // provide public access to the property
    public static StringProperty consoleProperty() {
        return info;
    }

    public static void setConsoleData(String data) {
        //if (info.getValue().equals("")) {
        //    info.set( data + "\n");
        //} else {
           info.set(info.get() + data + "\n");
       //}

    }

    public static String getConsoleData() {
        return info.get();
    }

    public static void clearData() {
        // info.set("");
    }

}
