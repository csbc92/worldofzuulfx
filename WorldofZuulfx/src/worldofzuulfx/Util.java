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
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JV
 */
public class Util {

    public static Properties prop;
    public static OutputStream output = null;
    public static InputStream input;

    public static void storeFile(String filename) throws FileNotFoundException {
        try {
            if (prop != null) {
                output = new FileOutputStream(filename);
                prop.store(output, null);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void loadFile(String filename) {
        try {
            input = new FileInputStream("config.properties");
            
            // load a properties file
            prop.load(input);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void newPropFile() {

        prop = new Properties();

    }
    
    public static void setProp(String name, String data){
        prop.setProperty(name, data);
     
    }
    
    public static void getProp (String name){
           prop.getProperty(name, "null");
    }

}
