package ch.bbw.zork;

import ch.bbw.zork.enums.GameState;

import java.util.Random;

public class Game {
    private GameState gameState;
    private int remainingTime;
    private House house;
    private Player player;
    private String seed;
    private Random random;
    private Parser parser;

    public Game(Parser parser) {
        Random rand = new Random();
        setSeed(rand.nextInt());
        Parser.clearScreen();
        this.parser = parser;
        player = new Player();
        this.parser.setPlayer(player);

        house = new House(this);
        remainingTime = 9999;
        gameState = GameState.NONE;

        house.generateHouse();

//        if (Zork2.DEBUG) System.out.println(house.getMap());
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
        int seedInt = Integer.parseInt(seed, 2);
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

    public Random getRandom() {
        return random;
    }
}
