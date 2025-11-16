package ch.bbw.zork;

import ch.bbw.zork.enums.GameState;

import java.util.Random;

public class Game {
    private GameState gameState;
    private int remainingTime;
    public static House house;
    public static Player player;
    public static String seed;

    public Game() {
        Random rand = new Random();
        seed = Parser.padLeft(Integer.toBinaryString(rand.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE)), '0', 32);
        Parser.clearScreen();
        player = new Player();
        house = new House();
        remainingTime = 9999;
        gameState = GameState.NONE;

        house.generateHouse();

        update();
    }

    private void update() {
        while (gameState == GameState.NONE) {
            if (remainingTime <= 0) {
                gameState = GameState.LOSE;
                continue;
            }

            if (!Zork2.parser.executeCommand(Zork2.parser.getCommandInputs())) {
                System.out.println("Invalid Input");
            }
        }
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
