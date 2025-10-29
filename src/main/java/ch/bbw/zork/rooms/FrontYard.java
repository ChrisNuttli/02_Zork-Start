package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.Tuple;
import ch.bbw.zork.interfaces.ExitNorth;

import java.util.Random;

public class FrontYard extends Room {
	public FrontYard() {
		super("front yard", 1);
	}

    @Override
    public Tuple<Integer, Integer> generateCoordinates() {
        Random rand = new Random();
        int dirInt = rand.nextInt(Direction.values().length);
        Tuple<Integer, Integer> coordinates;
        switch (dirInt) {
            case 1:
                coordinates = new Tuple<>(2, 4);
            case 2:
                coordinates = new Tuple<>(4, 2);
            case 3:
                coordinates = new Tuple<>(2, 0);
            default:
                coordinates = new Tuple<>(0, 2);
        }

        this.setCoordinates(coordinates);
        return coordinates;
    }
}
