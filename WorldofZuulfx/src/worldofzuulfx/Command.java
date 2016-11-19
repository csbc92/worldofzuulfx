/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two parts: a CommandWord and a string
 * (for example, if the command was "take map", then the two parts
 * are TAKE and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

package worldofzuulfx;

public class Command
{
    private CommandWord commandWord;    //creates a private Commandword with the name comandWord
    private String secondWord;          //creates a private string with the name secondWord

    public Command(CommandWord commandWord, String secondWord)      //Creates a Command object taking the instance parameters of commandWrd and secondWord
    {
        //constructor
        this.commandWord = commandWord;                             //Creates a constructor that assigns the value of the parameters to the instance parameters
        this.secondWord = secondWord;
    }
    
    public CommandWord getCommandWord()     //this is created to call the private commandWord, because it is more convenient to acces the method than the variable
    {
        return commandWord;                 //returns the value of comandWord
    }

    public String getSecondWord()           //this is created to acces the value placed within secondWord, so it can be accessed from other classes
    {
        return secondWord;                  //returns the value of secondWord
    }

    public boolean isUnknown()              //creates a boolean that checks whether the commandWord is known or not, if it is unknown then return true
    {
        return (commandWord == CommandWord.UNKNOWN);    //returns whether is is true or not
    }

    public boolean hasSecondWord()          //A boolean that chekcs whether secondWord is present or not
    {
        return (secondWord != null);        //returns true if secondWord is defined returns false if secondWord is not defined
    }
}

