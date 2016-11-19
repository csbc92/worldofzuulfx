/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.Player;

public class Note extends Item {

    protected String content;

    public Note(String description, int weight, String contentString) {
        super(description, weight);
        content = contentString;

    }

    public String getContent() {
        return content;
    }

    @Override
    public void use(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
