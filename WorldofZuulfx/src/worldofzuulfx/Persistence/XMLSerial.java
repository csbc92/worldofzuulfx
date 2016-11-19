/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import worldofzuulfx.Exam.Question;
import worldofzuulfx.Room;

public class XMLSerial {

    // private XMLEncoder encoder;
    public XMLSerial() {

    }

    public void serialQuestion(ArrayList<Question> list) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("questions.xml")));
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("ERROR: While Creating or Opening the File serial.xml");
        }
        encoder.writeObject(list);
        encoder.close();
    }

    public ArrayList<Question> deserialQuestion() {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("questions.xml")));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File serial.xml not found");
        }
        return (ArrayList<Question>) decoder.readObject();

    }

    public void serialRooms(ArrayList<Room> list) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("rooms.xml")));
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("ERROR: While Creating or Opening the File rooms.xml");
        }
        encoder.writeObject(list);
        encoder.close();
    }

    public ArrayList<Room> deserialRooms() {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("rooms.xml")));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File rooms.xml not found");
        }
        return (ArrayList<Room>) decoder.readObject();

    }

}
