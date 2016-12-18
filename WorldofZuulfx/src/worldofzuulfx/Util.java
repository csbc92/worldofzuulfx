package worldofzuulfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Properties;

public class Util {

    private static Properties prop;
    private static OutputStream output = null;
    private static InputStream input;

    /**
     * Used to store the prop-object as a file
     *
     * @param filename The name of the file.
     */
    public static void storeFile(String filename) {
        try {
            if (getPropFile() != null) {
                output = new FileOutputStream(filename);
                getPropFile().store(output, null);
            }
        } catch (IOException io) {
            System.out.println("Couldn't save the file");
            io.printStackTrace();
        }
    }

    /**
     * Used to load a given file based on the file name.
     * If the prop-object is null a new prop-object will be instantiated
     * @param filename
     */
    public static void loadFile(String filename) {
        try {
            input = new FileInputStream(filename);
            if (prop == null) {
                newPropFile();
            }
            // load a properties file
            getPropFile().load(input);     
        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't load the file");
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    /**
     * Instantiate a new prop-object
     */
    public static void newPropFile() {
        prop = new Properties();

    }

    /**
     * Sets a property with a given key and value
     * @param name key
     * @param data value
     */
    public static void setProp(String name, String data) {
        getPropFile().setProperty(name, data);
    }

    /**
     * Get a property value based on a given key
     * @param name key
     * @return A value based on the key. If the key is not found a String with "null" is returned.
     */
    public static String getProp(String name) {
        return getPropFile().getProperty(name, "null");

    }

    /**
     * @return the prop
     */
    public static Properties getPropFile() {
        return prop;
    }

    /**
     * Converts a String to a two-dimensional int array
     * 
     * @param source A given string to be converted
     * @param outerdelim Outer demlimiter e.g. "\r"
     * @param innerdelim Inner delimiter e.g. ","
     * @return Two-dimensional int array
     */
    public static int[][] strTo2d(String source, String outerdelim, String innerdelim) {
        // Replaces all characters except outerdelim with ""
 
        String s = source.trim().replaceAll("[^" + outerdelim + "]", "");
        // Counts all characters and creates an two-dimensional int array based on this number plus one.
        int[][] result = new int[s.length() + 1][];
        
        int count = 0;
        // Iterates through the lines which is created by splitting the String s.
        for (String line : source.split("[" + outerdelim + "]")) {

            String subString = line.replaceAll("\\[", "").replaceAll("\\]", "").trim();
            
            String[] subStringResult = subString.split(innerdelim);
            //Converts a String Array to int using a stream array
            int[] subIntResult = Arrays.stream(subStringResult).mapToInt(Integer::parseInt).toArray();
            result[count++] = subIntResult;
        }
        return result;
    }

}
