package ch.bbw.zork;

import ch.bbw.zork.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Zork2 {
    public final static List<String> validCommands = Arrays.asList("help",
        "move",
        "scan",
        "check",
        "take",
        "drop",
        "stash",
        "fetch",
        "quit",
        "map",
        "inventory"
    );
    public static HashMap<String, ICommand> commands;

	public static void main(String[] args) {
        loadCommands();
		Game zorkgame = new Game();
		zorkgame.play();
	}

    private static void loadCommands() {
        // Help
        HelpCommand helpCommand = new HelpCommand(0);
        helpCommand.setSummary("Prints this information screen");
        helpCommand.setHelpText("Prints this information screen");
        helpCommand.setExampleString("");

        MoveCommand moveCommand = new MoveCommand(5);
        moveCommand.setSummary("Moves the player in the specified direction (north, south, east, west)");
        moveCommand.setHelpText(String.join("\n",
                "Moves the player character into the next room by walking through the door in the provided direction.",
                "Only Cardinal Directions are allowed: North, East, South and West",
                "If no valid direction is specified, then the action fails and no time is lost."
        ));
        moveCommand.setExampleString("move north");

        // Scan
        ScanCommand scanCommand = new ScanCommand(15);
        scanCommand.setSummary("Scans the current room and prints the furniture and items that you can see");
        scanCommand.setHelpText("");
        scanCommand.setExampleString("");

        // Check
        CheckCommand checkCommand = new CheckCommand(5);
        checkCommand.setSummary("Examines a piece of furniture to reveal hidden items.");
        checkCommand.setHelpText("");
        checkCommand.setExampleString("");

        // Take
        TakeCommand takeCommand = new TakeCommand(5);
        takeCommand.setSummary("Takes an item from the room into your hands (max 2)");
        takeCommand.setHelpText("");
        takeCommand.setExampleString("");

        // Drop
        DropCommand dropCommand = new DropCommand(5);
        dropCommand.setSummary("Drops the held item in the current room.");
        dropCommand.setHelpText("");
        dropCommand.setExampleString("");

        // Stash
        StashCommand stashCommand = new StashCommand(10);
        stashCommand.setSummary("Stashes an item in your hand in the backpack if available.");
        stashCommand.setHelpText("");
        stashCommand.setExampleString("");

        // Fetch
        FetchCommand fetchCommand = new FetchCommand(10);
        fetchCommand.setSummary("Retrieves an item from the backpack if available. Into your hand");
        fetchCommand.setHelpText("");
        fetchCommand.setExampleString("");

        // Map
        MapCommand mapCommand = new MapCommand(0);
        mapCommand.setSummary("Displays a map of the currently discovered rooms");
        mapCommand.setHelpText("");
        mapCommand.setExampleString("");

        // Inventory
        InventoryCommand inventoryCommand = new InventoryCommand(0);
        inventoryCommand.setSummary("Lists all items in the players hands and backpack");
        inventoryCommand.setHelpText("");
        inventoryCommand.setExampleString("");

        // Quit
        QuitCommand quitCommand = new QuitCommand(0);
        quitCommand.setSummary("Quits the game");
        quitCommand.setHelpText("");
        quitCommand.setExampleString("");

        // Create Hashmap
        commands = new HashMap<>();
        commands.put("help", helpCommand);
        commands.put("move", moveCommand);
        commands.put("scan", scanCommand);
        commands.put("check", checkCommand);
        commands.put("take", takeCommand);
        commands.put("drop", dropCommand);
        commands.put("stash", stashCommand);
        commands.put("fetch", fetchCommand);
        commands.put("map", mapCommand);
        commands.put("inventory", inventoryCommand);
        commands.put("quit", quitCommand);
    }
}



