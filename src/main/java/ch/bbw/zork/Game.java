package ch.bbw.zork;

import ch.bbw.zork.enums.GameState;
import ch.bbw.zork.enums.RoomData;

import java.util.InputMismatchException;
import java.util.Random;

public class Game {
    private GameState gameState;
    private static int remainingTime;
    private static House house;
    private static Player player;
    private String seed;
    private static Random random;
    private Parser parser;

    public Game() {
        Random rand = new Random();
        setSeed(rand.nextInt());
        Parser.clearScreen();
        this.parser = Zork2.getParser();
        player = new Player();
        this.parser.setPlayer(player);

        house = new House();
        remainingTime = 9999;
        gameState = GameState.NONE;

        for (int i = 0; i < Constants.MAX_GEN_ITERATIONS; i++) {
            try {
                house.generateHouse();
                break;
            }
            catch (Exception e) {
                if (i == Constants.MAX_GEN_ITERATIONS - 1) {
                    throw e;
                }
            }
        }

        Room frontYard = house.getRoom(RoomData.FRONT_YARD);
        player.setCurrentRoom(frontYard);

        if (Zork2.DEBUG) {
            System.out.println(house.getMap());
        }

        gameStart();
    }

    public void gameStart() {
        Parser.clearScreen();
        System.out.println("You stand in the Front Yard of your Victims house.");
        while (this.gameState == GameState.NONE) {
            update();
        }

        if (this.gameState == GameState.WIN) {
            System.out.println("You won!");
        }
        else {
            System.out.println("Better luck next time!");
        }
    }

    private void update() {
        try {
            String[] input = parser.getCommandInputs();
            parser.processCommand(input);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        if (remainingTime <= 0) {
            gameState = GameState.LOSE;
        }
    }

    public void setSeed(int randomInt) {
        seed = Helper.padLeft(Integer.toBinaryString(randomInt), '0', 30);
        if (seed.length() > 30) {
            seed = seed.subSequence(seed.length()-31, seed.length()-1).toString();
        }
        int seedInt = Integer.parseInt(seed, 2);
        random = new Random(seedInt);
    }

    public static void addTime(int time) {
        remainingTime += time;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Random getRandom() {
        return random;
    }

    public static House getHouse() {
        return house;
    }
}
