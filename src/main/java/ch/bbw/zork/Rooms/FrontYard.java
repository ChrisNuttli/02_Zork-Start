package ch.bbw.zork.Rooms;

import ch.bbw.zork.Constants;
import ch.bbw.zork.Game;
import ch.bbw.zork.Room;

import java.util.Random;

public class FrontYard extends Room {
    public FrontYard(int[] coords) {
        super("Front Yard", "", coords[0], coords[1]);
    }

    public FrontYard(int x, int y) {
        super("Front Yard", "", x, y);
    }

    public static int[] getRandomCoordinates() {
        Random rand = new Random(Integer.parseInt(Game.seed, 2));
        int roomX = -1;
        int roomY = -1;

        while (roomX != 0 && roomX != Constants.MAP_WIDTH+1) {
            roomX = rand.nextInt(0, Constants.MAP_WIDTH);
        }
        while (roomY != 0 && roomY != Constants.MAP_HEIGHT+1) {
            roomY = rand.nextInt(0, Constants.MAP_HEIGHT);
        }

        return new int[]{roomX, roomY};
    }
}
