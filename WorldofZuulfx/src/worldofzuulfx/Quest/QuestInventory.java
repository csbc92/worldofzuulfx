/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Quest;

import java.util.HashMap;
import worldofzuulfx.ConsoleInfo;

import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.Layers;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.NPC.PartyGuy;
import worldofzuulfx.Player;
import worldofzuulfx.Room;
import worldofzuulfx.RoomHandler;

/**
 *
 * @author JV
 */
public class QuestInventory {

    private HashMap<String, Quest> allGameQuests;
    private QuestHandler questHandler;

    public QuestInventory() {

    }

    public HashMap<String, Quest> getAllGameQuests() {
        return allGameQuests;
    }

    public void initQuests(RoomHandler roomHandler, Player player) {
        // Initialize Quests and Quest handling
        this.allGameQuests = new HashMap<>();
        QuestFactory qFactory = new QuestFactory(player);
        questHandler = new QuestHandler(player);

        // Create quests
        Quest goToCampusQ = qFactory.roomQuest("Campus", "Go to Campus", null);
        goToCampusQ.setPostAction(() -> {
            String postCompleteMessage = "The final exam for the semester awaits, but you're not prepared. "
                    + "\nYou have spent your time on anything else than studying."
                    + "\n\nFind your way to the exam room by using the map. "
                    + "Remember you can navigate using the go-command.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToExamnRoomQ = qFactory.roomQuest("Exam", "Go to the examn room.", null);
        roomHandler.getRoom("Exam").setLocked(false); // Temporarily unlock so the player can navigate here
        goToExamnRoomQ.setPostAction(() -> {

            String postCompleteMessage = "To prepare for the final exam you'll have to explore the university "
                    + "and collect both ECTS-points "
                    + "and important notes that you were too lazy to take during the semester."
                    + "\n\nThe exam room will unlock when you have collected enough ECTS-points. "
                    + "\n\nYou have been given a new Quest. "
                    + "To view its content type ‘quest’ in the console. You can type this command if you are lost.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
            // Navigate the player out of the exam room
            Room campus = roomHandler.getRoom("Campus");
            // TODO
            player.navigateSilentlyTo(campus);
            roomHandler.getRoom("Exam").setLocked(true); // Lock the exam room again

            // unlock rooms so the player can move further
            roomHandler.getRoom("Gydehutten").setLocked(false);
            roomHandler.getRoom("Bookstore").setLocked(false);
        });

        Quest goToU163RoomQ = qFactory.roomQuest("U163", "Go to teaching room U163.", null);
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
            player.getInventory().addItem(ItemFactory.makeCoffeeVoucher());
        });

        Quest goToCanteenQ = qFactory.roomQuest("Canteen", "Go to the Canteen.", null);
        goToCanteenQ.setPostAction(() -> {
            String postCompleteMessage = "This is the canteen where you can exchange Coffee Vouchers for coffee"
                    + "\n\nUse the Coffee Voucher by typing 'use' followed by 'coffee voucher' in the console.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest coffeeQ = qFactory.pickupItemQuest("coffee", "Buy a coffee.", null);
        coffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Quickly! Bring the coffee to Anders in U163.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        NPC anders = roomHandler.getRoom("U163").getNPC("Anders");
        Quest deliverCoffeeQ = qFactory.deliveryQuest("coffee", anders, "Deliver the coffee to Anders.", null);
        deliverCoffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Anders:"
                    + "\n\"Coffee is the source for maintaining your energy-level. "
                    + "Throughout the game you have to maintain your energy-level by drinking coffee "
                    + "but the amount of Coffee Vouchers is limited so use them wisely. "
                    + "If you run out of energy, you will black out and wake up at a random place in the university. "
                    + "\n\nBe careful. You can only black out a few times before you remain unconscious and the game is lost.\""
                    + "\n\nTo drink a Coffee type ‘use coffee’ followed by ‘drink’ in the console.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest leaveU163Q = qFactory.roomQuest("Knoldene", "Leave U163", null);
        leaveU163Q.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"The OOP lesson has not finished yet. To attend to the course, you need your OOP book. "
                    + "You can collect the book in the Book store.\""
                    + "\n\nFind your way to the Book store and collect the book. "
                    + "Remember that you can use the map if you’re lost.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToBookStoreQ = qFactory.roomQuest("Bookstore", "Go to the Bookstore", null);
        goToBookStoreQ.setPostAction(() -> {
            String postCompleteMessage = "This is the Book store. "
                    + "\nIn here you can collect books that are needed to attend to different courses at the university. "
                    + "\n\nTo search a room for items type ‘search’ in the console. "
                    + "To collect an item type ‘take’ followed by the name of the item you want to collect. "
                    + "e.g. ‘take oopbook’ in the console.";
            ConsoleInfo.setConsoleData(postCompleteMessage);

            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(ItemFactory.makeBook("OOP-Book"));
        });

        Quest returnToU163 = qFactory.roomQuest("U163", "Return to U163", null);
        returnToU163.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"Now that you have the book, the course can begin.\"";

            ConsoleInfo.setConsoleData(postCompleteMessage);
            //TODO Spawn af PartyGuy skal håndteres
           // initPartyGuy();
        });

        Quest u170Lecture = qFactory.roomQuest("U170", "Participate in lecture", null);
        u170Lecture.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Welcome to the ISE-lecture."
                    + "\nBefore we can start the lecture you need your ISE Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);

            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(ItemFactory.makeBook("ISE-Book"));
        });

        Quest bookstoreIse = qFactory.roomQuest("Bookstore", "Go to Bookstore", null);
        bookstoreIse.setPostAction(() -> {
            String postCompleteMessage = "Bookstore"
                    + "\nSearch for the ISE-Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest returnToU170 = qFactory.roomQuest("U170", "Return to U170", null);
        returnToU170.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest u180Lecture = qFactory.roomQuest("U180", "Participate in lecture", null);
        u180Lecture.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Welcome to the COS-lecture."
                    + "\nBefore we can start the lecture you need your BOS Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);

            roomHandler.getRoom("Bookstore").getRoomInventory().addItem(ItemFactory.makeBook("COS-Book"));
        });

        Quest bookstoreCos = qFactory.roomQuest("Bookstore", "Go to Bookstore", null);
        bookstoreCos.setPostAction(() -> {
            String postCompleteMessage = "Search for the COS Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);

        });

        Quest returnToU180 = qFactory.roomQuest("u180", "Return to U180", null);
        returnToU180.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";

            ConsoleInfo.setConsoleData(postCompleteMessage);

        });

        // Give the player an initial quest
        player.setActiveQuest(goToCampusQ, true);

        // Chain quests together in a "tutorial"
        goToCampusQ.setChainQuest(goToExamnRoomQ);
        goToExamnRoomQ.setChainQuest(goToU163RoomQ);
        goToU163RoomQ.setChainQuest(goToCanteenQ);
        goToCanteenQ.setChainQuest(coffeeQ);
        coffeeQ.setChainQuest(deliverCoffeeQ);
        deliverCoffeeQ.setChainQuest(leaveU163Q);
        leaveU163Q.setChainQuest(goToBookStoreQ);
        goToBookStoreQ.setChainQuest(returnToU163);

        // Chain the rest of the quests together in the game
        u170Lecture.setChainQuest(bookstoreIse);
        bookstoreIse.setChainQuest(returnToU170);

        u180Lecture.setChainQuest(bookstoreCos);
        bookstoreCos.setChainQuest(returnToU180);

        // Save a reference to the rest of the quests
        this.allGameQuests.put("goToCampusQ", goToCampusQ);
        this.allGameQuests.put("goToExamRoomQ", goToExamnRoomQ);
        this.allGameQuests.put("goToU163RoomQ", goToU163RoomQ);
        this.allGameQuests.put("goToCanteenQ", goToCanteenQ);
        this.allGameQuests.put("coffeeQ", coffeeQ);
        this.allGameQuests.put("deliverCoffeeQ", deliverCoffeeQ);
        this.allGameQuests.put("leaveU163Q", leaveU163Q);
        this.allGameQuests.put("goToBookStoreQ", goToBookStoreQ);
        this.allGameQuests.put("returnToU163", returnToU163);
        this.allGameQuests.put("u170Lecuture", u170Lecture);
        this.allGameQuests.put("bookstoreIse", bookstoreIse);
        this.allGameQuests.put("returnToU170", returnToU170);
        this.allGameQuests.put("u180Lecture", u180Lecture);
        this.allGameQuests.put("bookstoreCos", bookstoreCos);
        this.allGameQuests.put("returnToU180", returnToU180);
    }
}
