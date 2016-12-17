
package worldofzuulfx.Quest;

import java.util.HashMap;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Items.Book;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.Items.Note;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;
import worldofzuulfx.Room.RoomList;

public class QuestInventory {

    private HashMap<String, Quest> allGameQuests;
    private QuestHandler questHandler;

    public QuestInventory() {

    }

    public HashMap<String, Quest> getAllGameQuests() {
        return allGameQuests;
    }

    public void initQuests(RoomList roomHandler, Player player) {
        // Initialize Quests and Quest handling
        this.allGameQuests = new HashMap<>();
        QuestFactory qFactory = new QuestFactory(player);
        questHandler = new QuestHandler(player);

        // Create quests
        Quest goToCampusQ = qFactory.roomQuest("goToCampusQ", "Campus", "Go to Campus", null);
        goToCampusQ.setPostAction(() -> {
            String postCompleteMessage = "Find your way to the exam room.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToExamnRoomQ = qFactory.roomQuest("goToExamnRoomQ", "Exam", "Go to the exam room.", null);

        roomHandler.getRoom("Exam").setLocked(false); // Temporarily unlock so the player can navigate here
        goToExamnRoomQ.setPostAction(() -> {

            String postCompleteMessage = "To participate in the final exam you'll have to explore the university "
                    + "and collect ECTS-points "
                    + "and important notes that you were too lazy to take during the semester."
                    + "\n\nThe exam room will be locked until you have collected enough ECTS-points. "
                    + "\n\nYou have been given a new Quest. " + 
                    "A hint for the quest can be seen below the 'Active Quest' label.";
            ConsoleInfo.setConsoleData(postCompleteMessage);

            roomHandler.getRoom("Exam").setLocked(true); // Lock the exam room again

            // unlock rooms so the player can move further
            roomHandler.getRoom("Gydehutten").setLocked(false);
            roomHandler.getRoom("Bookstore").setLocked(false);
        });

        Quest goToU163RoomQ = qFactory.roomQuest("goToU163RoomQ", "U163", "Go to teaching room U163.", new Reward(ItemFactory.makeCoffeeVoucher(), 0));
        goToU163RoomQ.setPostAction(() -> {

            String postCompleteMessage = "This is the teaching room for Object Oriented Programming."
                    + "\n\nDaniel are excited to prepare you for the upcoming exam, "
                    + "but Anders is not very impressed with this being the first time you show up for OOP lessons "
                    + "in this semester. "
                    + "\n\nAs a penalty, Anders wants you to get him a cup of coffee in the canteen. "
                    + "\n\nTo buy Coffee, Anders has given you Coffee Vouchers. "
                    + "You can exchange Coffee Vouchers for coffee in the canteen. "
                    + "\n\nHurry up! The lesson is about to begin!";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToCanteenQ = qFactory.roomQuest("goToCanteenQ", "Canteen", "Go to the Canteen.", null);
        goToCanteenQ.setPostAction(() -> {
            String postCompleteMessage = "This is the canteen where you can exchange Coffee Vouchers for coffee"
                    + "\nYou can scroll between items in your inventory by using the keys 'A' and 'Z'"
                    + "\n\nUse the Coffee Voucher by first selecting it in the inventory and then press the 'U'-key on the keyboard."
                    + "\nYou can drop any item by selecting it and then hit the 'D' key.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest coffeeQ = qFactory.pickupItemQuest("coffeeQ", "coffee", "Buy a coffee.", null);
        coffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Right on! Bring the coffee to Anders in U163.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        Quest coffeeQReturnToU163 = qFactory.roomQuest("coffeeQReturnToU163", "U163", "Find your way back to U163.", null);
        coffeeQReturnToU163.setPostAction(() -> {
            String postCompleteMessage = "Deliver the coffee to Anders by first selecting the coffee in your inventory using the 'A' / 'Z' keys. "
                    + "\nWhen the coffee is selected approach Anders by moving towards him. "
                    + "\nGive Anders the coffee by hitting the 'U' key.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        NPC anders = roomHandler.getRoom("U163").getNPC("Anders");
        Quest deliverCoffeeQ = qFactory.deliveryQuest("deliverCoffeeQ", "coffee", anders, "Deliver the coffee to Anders.", null);
        deliverCoffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Anders:"
                    + "\n\"Coffee is the source for maintaining your energy-level. "
                    + "Throughout the game you have to maintain your energy-level by drinking coffee "
                    + "but the amount of Coffee Vouchers is limited so use them wisely. "
                    + "If you run out of energy, you lose 1 HP. "
                    + "\n\nBe careful. You only have a certain amount of HP. Lose them all and the game is lost.\""
                    + "\n\nTo drink a Coffee select the item in the inventory and hit the 'U'-key.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        /*
        * OOP QUESTS
        */
        
        Quest leaveU163Q = qFactory.roomQuest("leaveU163Q", "Knoldene", "Leave U163", null);
        leaveU163Q.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"The OOP lesson has not finished yet. To attend to the course, you need your OOP book. "
                    + "You can collect the book in the book store.\""
                    + "\n\nFind your way to the book store and collect the book.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
            
            Book oopBook = ItemFactory.makeBook("OOP-book", Book.BookColor.RED);
            oopBook.setX(288);
            oopBook.setY(96);
            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(oopBook);
        });

        Quest goToBookStoreQ = qFactory.roomQuest("goToBookStoreQ", "Bookstore", "Go to the Bookstore", null);
        goToBookStoreQ.setPostAction(() -> {
            String postCompleteMessage = "This is the Book store. "
                    + "\nIn here you can collect books that are needed to attend to different courses at the university.";
            ConsoleInfo.setConsoleData(postCompleteMessage);            
        });
        
        Quest pickupOOPBookQ = qFactory.pickupItemQuest("pickupOOPBookQ", "OOP-book", "Pick up the OOP-book", null);
        pickupOOPBookQ.setPostAction(() -> {
            String postCompleteMessage = "Return to U163 and participate in the OOP lecture.";
            ConsoleInfo.setConsoleData(postCompleteMessage);            
        });

        Quest returnToU163 = qFactory.roomQuest("returnToU163", "U163", "Return to U163", null);
        returnToU163.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"Now that you have the book, the course can begin.\""
                    
                    + "\n\nDeliver the OOP-book to Daniel by first selecting the book in your inventory using the 'A' / 'Z' keys. "
                    + "\nWhen the OOP-book is selected approach Daniel by moving towards him. "
                    + "\nGive Daniel the OOP-book by hitting the 'U' key.";
    
            ConsoleInfo.setConsoleData(postCompleteMessage);
            
        });
        
        NPC daniel = roomHandler.getRoom("U163").getNPC("Daniel");
        Note oopNote = ItemFactory.makeNote("oop-notes", "OOP Lecture notes.",
                  "\nInt are whole numbers, such as 1, 2 and 3."
                + "\nDouble which support dicimals such as 1,2 or 3,4"
                + "\nBoolean can only be \'true\' or \'false\'"
                + "\nString is use for text based information."
                + "\nA Class always start with a capital letters like \'class Dog\'"
                + "\nA class con contain some fields, contructors and some method declarations."
                + "\nfields are where we link datatypes to a name such as legs = int;, in a class called Dog"
                + "\nA Contructor is where you can create objects "
                + "from a class blueprint, a contructor looks a lot like a method"
                + "but they use the name of the class and got no return type"
                + "\nexample"
                + "\nPublic Dog (int legs, string race, string name, boolean vaccine){"
                + "\n     Leg = legs;"
                + "\n     Race = race;"
                + "\n     Name = name;"
                + "\n     det IsItHealthy = vaccine"
                + "\n}");
        Quest oopLecture = qFactory.deliveryQuest("oopLecture", "OOP-book", daniel, "Participate in OOP lecture by giving Daniel the OOP-book", new Reward(null, 10));
        oopLecture.setPostAction(() -> {
            String postCompleteMessage = "You have now completed the OOP lecture"
                    + "\nLook in your inventory for your notes."
                    + "\nYou can read your notes by selecting them and then hit the 'U' key.";
            player.getInventory().addItem(oopNote);
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
       
        /*
        * ISE QUESTS
        */
        
        Quest goToU170 = qFactory.roomQuest("u170Lecture", "U170", "Go to U170", null);
        goToU170.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Welcome to the ISE-lecture."
                    + "\nBefore we can start the lecture you need your ISE Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
            
            Book iseBook = ItemFactory.makeBook("ISE-Book", Book.BookColor.ORANGE);
            iseBook.setX(289);
            iseBook.setY(97);
            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(iseBook);
        });
        
        Quest bookstoreIse = qFactory.roomQuest("bookstoreIse", "Bookstore", "Go to Bookstore", null);
        bookstoreIse.setPostAction(() -> {
            String postCompleteMessage = "Bookstore"
                    + "\nPickup your ISE-Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        Quest pickupISEBookQ = qFactory.pickupItemQuest("pickupISEBookQ", "ISE-Book", "Pick up the ISE-book", null);
        pickupISEBookQ.setPostAction(() -> {
            String postCompleteMessage = "Return to U170 and participate in the ISE lecture.";
            ConsoleInfo.setConsoleData(postCompleteMessage);            
        });

        Quest returnToU170 = qFactory.roomQuest("returnToU170", "U170", "Return to U170", null);
        returnToU170.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        NPC lone = roomHandler.getRoom("U170").getNPC("Lone");
        Note iseNote = ItemFactory.makeNote("ise-notes", "ISE Lecture notes", "The Waterfall model, a model with different phases where you follow a specific pattern, "
                + "where each phase must be executed before moving on to the next"
                + "\nSpiral model, a model that can be considered a linear model but it isn’t, "
                + "you are going through all the phases repeated in a spiral motion, until you have the finished project"
                + "The first step of a new project is to figure out the requirements and needs for the project, "
                + "if your project is for a customer then he needs to be involved in this"
                + "\n1: Listen to the requirement the customer asks for, it is important."
                + "\n2: Give the customer advices, is there something you can add or is the idea already created."
                + "\n3: Go thought the steps of each model");
        Quest iseLecture = qFactory.deliveryQuest("iseLecture", "ISE-Book", lone, "Participate in ISE lecture by giving Lone the ISE-book", new Reward(null, 10));
        iseLecture.setPostAction(() -> {
            String postCompleteMessage = "You have now completed the ISE lecture"
                    + "\nLook in your inventory for your notes";
            player.getInventory().addItem(iseNote);
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        
        /*
        * COS QUESTS
        */
        
        Quest goToU180 = qFactory.roomQuest("u180Lecture", "U180", "Go to U180", null);
        goToU180.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Welcome to the COS-lecture."
                    + "\nBefore we can start the lecture you need your COS Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);

            Book cosBook = ItemFactory.makeBook("COS-Book", Book.BookColor.GREEN);
            cosBook.setX(288);
            cosBook.setY(96);
            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(cosBook);
        });

        Quest bookstoreCos = qFactory.roomQuest("bookstoreCos", "Bookstore", "Go to Bookstore", null);
        bookstoreCos.setPostAction(() -> {
            String postCompleteMessage = "Bookstore"
                    + "\nSearch for the COS-Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        Quest pickupCOSBookQ = qFactory.pickupItemQuest("pickupCOSBookQ", "COS-Book", "Pick up the COS-book", null);
        pickupCOSBookQ.setPostAction(() -> {
            String postCompleteMessage = "Return to U180 and participate in the COS lecture.";
            ConsoleInfo.setConsoleData(postCompleteMessage);            
        });

        Quest returnToU180 = qFactory.roomQuest("returnToU170", "U180", "Return to U180", null);
        returnToU180.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        
        
        NPC erik = roomHandler.getRoom("U180").getNPC("Erik");
        Note cosNote = ItemFactory.makeNote("cos-notes", "COS Lecture notes", "Everyhing you do is 1's and 0's, also known as Binary."
                + "\nA sequence of four 1's or 0's is also known as a bite - for example \'1101\'"
                + "\nA byte is a sequence of two bites, so a sequence of 8 binary numbers like \'11011011\'"
                + "you can also add or subtract in binary - for example:"
                + "\nAddition:"
                + "\n1101 -> in decimal = 9"
                + "\n0011 -> in decimal = 3"
                + "\n1100 -> in decimal = 12"
                + "\nSubstation:"
                + "\n1001 -> in decimal = 9"
                + "\n0011 -> in decimal = 3"
                + "\n0110 -> in decimal = 6"
                + "\n Datamanipulation is when we do something to a set of data, "
                + "can be moving it from one part of the memory to another."
                + "\nWe have a set of instruction, also know as a Op-code."
                + "\nOperand which will tell us how, where and what to do with a set of data."
                + "\nAn example could be:"
                + "\n156C: load register 5 iwth the bit pattern found in the memory cell at address 6C."
                + "\nOr"
                + "\n40A4 where we move the bit pattern found in register A and copy it to register 4)");
        Quest cosLecture = qFactory.deliveryQuest("cosLecture", "COS-book", erik, "Participate in the COS lecture by giving Erik the COS-book", new Reward(null, 10));
        cosLecture.setPostAction(() -> {
            String postCompleteMessage = "You have now completed the COS lecture"
                    + "\nLook in your inventory for your notes";
            player.getInventory().addItem(cosNote);
            ConsoleInfo.setConsoleData(postCompleteMessage);
            
            Item computer = ItemFactory.makeComputer();
            computer.setX(256);
            computer.setY(208);
            roomHandler.getRoom("downunder").getRoomInventory().addItem(computer);
        });
        
        Quest pickupComputerQ = qFactory.pickupItemQuest("getComputerQ", "Computer", "Find your computer in downunder", null);
        pickupComputerQ.setPostAction(() -> {
            String postCompleteMessage = "You are now ready to attend the exam. Hurry up before the time runs out!";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        Quest gotoExamRoomQ = qFactory.roomQuest("gotoExamRoomQ", "exam", "Go to the exam room!", null);
        gotoExamRoomQ.setPostAction(() -> {
            String postCompleteMessage = "Select your Computer in your inventory and hit the 'U' key "
                    + "to participate in the final exam!";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });
        
        
        // Give the player an initial quest
        player.setActiveQuest(goToCampusQ, true);

        // Chain quests together in a "tutorial"
        goToCampusQ.setChainQuest(goToExamnRoomQ);
        goToExamnRoomQ.setChainQuest(goToU163RoomQ);
        goToU163RoomQ.setChainQuest(goToCanteenQ);
        goToCanteenQ.setChainQuest(coffeeQ);
        coffeeQ.setChainQuest(coffeeQReturnToU163);
        coffeeQReturnToU163.setChainQuest(deliverCoffeeQ);
        deliverCoffeeQ.setChainQuest(leaveU163Q);
        
        // Chain OOP
        leaveU163Q.setChainQuest(goToBookStoreQ);
        goToBookStoreQ.setChainQuest(pickupOOPBookQ);
        pickupOOPBookQ.setChainQuest(returnToU163);
        returnToU163.setChainQuest(oopLecture);
        oopLecture.setChainQuest(goToU170);

        // Chain ISE
        goToU170.setChainQuest(bookstoreIse);
        bookstoreIse.setChainQuest(pickupISEBookQ);
        pickupISEBookQ.setChainQuest(returnToU170);
        returnToU170.setChainQuest(iseLecture);
        iseLecture.setChainQuest(goToU180);
        
        // Chain COS
        goToU180.setChainQuest(bookstoreCos);
        bookstoreCos.setChainQuest(pickupCOSBookQ);
        pickupCOSBookQ.setChainQuest(returnToU180);
        returnToU180.setChainQuest(cosLecture);
        cosLecture.setChainQuest(pickupComputerQ);
        pickupComputerQ.setChainQuest(gotoExamRoomQ);

        // Save a reference to the rest of the quests
        this.allGameQuests.put("goToCampusQ", goToCampusQ);
        this.allGameQuests.put("goToExamRoomQ", goToExamnRoomQ);
        this.allGameQuests.put("goToU163RoomQ", goToU163RoomQ);
        this.allGameQuests.put("goToCanteenQ", goToCanteenQ);
        this.allGameQuests.put("coffeeQ", coffeeQ);
        this.allGameQuests.put("coffeeQReturnToU163", coffeeQReturnToU163);
        this.allGameQuests.put("deliverCoffeeQ", deliverCoffeeQ);
        this.allGameQuests.put("leaveU163Q", leaveU163Q);
        this.allGameQuests.put("goToBookStoreQ", goToBookStoreQ);
        this.allGameQuests.put("pickupOOPBookQ", pickupOOPBookQ);
        this.allGameQuests.put("returnToU163", returnToU163);
        this.allGameQuests.put("oopLecture", oopLecture);
        this.allGameQuests.put("u170Lecuture", goToU170);
        this.allGameQuests.put("bookstoreIse", bookstoreIse);
        this.allGameQuests.put("pickupISEBook", pickupISEBookQ);
        this.allGameQuests.put("returnToU170", returnToU170);
        this.allGameQuests.put("iseLecture", iseLecture);
        this.allGameQuests.put("u180Lecture", goToU180);
        this.allGameQuests.put("bookstoreCos", bookstoreCos);
        this.allGameQuests.put("pickupCOSBookQ", pickupCOSBookQ);
        this.allGameQuests.put("returnToU180", returnToU180);
        this.allGameQuests.put("cosLecture", cosLecture);
        this.allGameQuests.put("pickupComputerQ", pickupComputerQ);
        this.allGameQuests.put("gotoExamRoomQ", gotoExamRoomQ);
    }
}
