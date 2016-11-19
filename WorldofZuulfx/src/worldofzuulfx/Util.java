/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;

/**
 *
 * @author JV
 */
public class Util {

    /**
     * 
     * @param list
     * @return
     */
    public static String arrayToString(ArrayList<String> list) {
        String result = "";
        for (String string : list) {
            result += "  " + string;
        }
        return result;
    }
}
