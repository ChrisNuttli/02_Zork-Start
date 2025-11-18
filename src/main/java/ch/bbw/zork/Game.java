package ch.bbw.zork;

import ch.bbw.zork.enums.GameState;

import java.util.Random;

public class Game {
    private GameState gameState;
    private int remainingTime;
    public static House house;
    public static Player player;
    public static String seed;
    public static Random random;

    public Game() {
        Random rand = new Random();
        setSeed(rand.nextInt());
        Parser.clearScreen();
        player = new Player();
        house = new House();
        remainingTime = 9999;
        gameState = GameState.NONE;

        house.generateHouse();
    }

    public void gameStart() {
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
        Zork2.parser.waitForInput();

        if (remainingTime <= 0) {
            gameState = GameState.LOSE;
        }

        if (!Zork2.parser.processCommand(Zork2.parser.getCommandInputs())) {
            System.out.println("Invalid Input");
        }
    }

    public void setSeed(int randomInt) {
        seed = Parser.padLeft(Integer.toBinaryString(randomInt), '0', 30);
        if (seed.length() > 30) {
            seed = seed.subSequence(seed.length()-31, seed.length()-1).toString();
        }
        int seedInt = Integer.parseInt(Game.seed, 2);
        random = new Random(seedInt);
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void addTime(int time) {
        remainingTime += time;
    }

    public Player getPlayer() {
        return player;
    }
}
