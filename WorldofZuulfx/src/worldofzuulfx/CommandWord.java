package worldofzuulfx;

public enum CommandWord{                                                       //creates an enum in which the values are assigned a string
    GO("go"), QUIT("quit"), HELP("help"), USE("use"), SEARCH("search"),
    TAKE("take"), QUEST("quest"), INFO("info"), UNKNOWN("?"), CHALLENGE("challenge");                        //A list of the different commands
    
    private String commandString;                                               //creates a string with the name commandString
    
    CommandWord(String commandString){                                          //Creates an object of CommandWord that takes the parameters commandString
        
        this.commandString = commandString;                                     //the instanced parameter commandString is assigned the value of the parameter
    }
    
    public String toString(){                                                   //The commandword is returned as a string
        return commandString;
    }
    }

