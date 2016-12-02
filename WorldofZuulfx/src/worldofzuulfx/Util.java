/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JV
 */
public class Util {

    private static Properties prop;
    private static OutputStream output = null;
    private static InputStream input;

    public static void storeFile(String filename) {
        try {
            if (getPropFile() != null) {
                output = new FileOutputStream(filename);
                getPropFile().store(output, null);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void loadFile(String filename) {
        try {
            input = new FileInputStream(filename);

            // load a properties file
            getPropFile().load(input);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void newPropFile() {
        prop = new Properties();

    }

    public static void setProp(String name, String data) {
        getPropFile().setProperty(name, data);

    }

    public static String getProp(String name) {
        return getPropFile().getProperty(name, "null");

    }

    /**
     * @return the prop
     */
    public static Properties getPropFile() {        
        return prop;
    }
    
    public static int [][] strTo2d (String source, String outerdelim, String innerdelim) {

    int [][] result = new int [source.trim().replaceAll ( "[^" + outerdelim + "]", "").length () + 1][]; 
    int count = 0;
    for (String line : source.split ("[" + outerdelim + "]"))
    {   
        String subString = line.replaceAll("\n", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
        String [] subStringResult = subString.split(innerdelim);
        int [] subIntResult = Arrays.stream(subStringResult).mapToInt(Integer::parseInt).toArray();
        result [count++] = subIntResult;
    }
    return result;
}


}
