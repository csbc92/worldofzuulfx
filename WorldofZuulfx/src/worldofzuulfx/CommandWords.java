package worldofzuulfx;
import java.util.HashMap;


public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;

    public CommandWords()
    {
        // Constructor - input all valid commands in validCommands.
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) { // Iterates through each command.
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command); // Inserts commands if they are valid.
            }
        }
    }

    public CommandWord getCommandWord(String commandWord)
    {
        // Request command with accompanying string.
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            // If accompanying command doesn’t exist, return “unknown”.
            return CommandWord.UNKNOWN;
        }
    }
    
    public boolean isCommand(String aString)
    {
        //  Check if a command with accompanying string exist.
        return validCommands.containsKey(aString);
    }

    public void showAll() 
    {
        // All commands are printed out on screen.
        for(String command : validCommands.keySet()) {
            ConsoleInfo.setConsoleData(command + "  ");
        }
        ConsoleInfo.setConsoleData("");
    }
}
