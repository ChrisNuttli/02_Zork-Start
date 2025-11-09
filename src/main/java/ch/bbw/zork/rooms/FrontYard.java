package ch.bbw.zork.rooms;

import ch.bbw.zork.Direction;
import ch.bbw.zork.RoomShape;

import static ch.bbw.zork.Constants.*;

public class FrontYard extends Room {
    public FrontYard() {
        this(0,0);
    }

	public FrontYard(int x, int y) {
		super("front yard", FRONT_YARD_SHAPE, new Class[]{
                Corridor.class,
                Kitchen.class,
        });

        this.setCoordinates(x, y);

        // Set the direction to where the door should face
        if (y == 0) {
            // Front yard is on the northern edge of the map
            this.setDoorDirections(new Direction[]{Direction.SOUTH});
        }
        else if (x == MAP_WIDTH-1) {
            // Front yard is on the eastern edge of the map
            this.setDoorDirections(new Direction[]{Direction.WEST});
        }
        else if (y == MAP_HEIGHT-1) {
            // Front yard is on the southern edge of the map
            this.setDoorDirections(new Direction[]{Direction.NORTH});
        }
        else if (x == 0) {
            // Front yard is on the western edge of the map
            this.setDoorDirections(new Direction[]{Direction.EAST});
        }
        else {
            throw new RuntimeException("Illegal Room Coordinates: Front yard must be placed on the edge of the map!");
        }
	}

    public static RoomShape getShape() {
        return FRONT_YARD_SHAPE;
    }
}
