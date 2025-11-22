package ch.bbw.zork;

import java.util.Arrays;
import java.util.List;

public class Zork2 {
    public static boolean DEBUG = true;
    public static Parser parser;
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

	public static void main(String[] args) {
        System.out.println("Welcome to the Zork Game!");
        parser = new Parser(System.in);
        boolean quit = false;
        while (!quit) {
            switch(parser.chooseOption("Choose an option: ", new String[]{ "New Game", "Exit" })) {
                case 1:
                    System.out.println("You selected New Game!");
                    Game game = new Game();
                    game.gameStart();
                    break;
                case 2:
                    System.out.println("You selected Exit!");
                    quit = true;
                    break;
            }
        }
	}
}



