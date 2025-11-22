package ch.bbw.zork.enums;

import static ch.bbw.zork.enums.Direction.*;

public enum RoomData {
    ATTIC("Attic", "", new Direction[]{ SOUTH }),
    BASEMENT("Basement", "", new Direction[]{ EAST }),
    BATHROOM("Bathroom", "", new Direction[]{ WEST }),
    BEDROOM("Bedroom", "", new Direction[]{ EAST, SOUTH }),
    CELLAR("Cellar", "", new Direction[]{ NORTH }),
    CORRIDOR("Corridor", "", new Direction[]{  NORTH, EAST, SOUTH, WEST }),
    DINING_ROOM("Dining Room", "", new Direction[]{ EAST, WEST }),
    FRONT_YARD("Front Yard", "", new Direction[]{ NORTH }),
    KITCHEN("Kitchen", "", new Direction[]{ EAST, SOUTH, WEST }),
    LIVING_ROOM("Living Room", "", new Direction[]{ NORTH, EAST, WEST }),
    OFFICE("Office", "", new Direction[]{ SOUTH, WEST });

    private final String name;
    private final String description;
    private final Direction[] doorFrames;
    private final RoomShape shape;

    private RoomData(String name, String description, Direction[] doorFrames) {
        this.name = name;
        this.description = description;
        this.doorFrames = doorFrames;

        if (doorFrames.length == 1) {
            this.shape = RoomShape.DEAD_END;
        }
        else if (doorFrames.length == 3) {
            this.shape = RoomShape.FORK;
        }
        else if (doorFrames.length == 4) {
            this.shape = RoomShape.CROSS;
        }
        else if ((doorFrames[0] == Direction.NORTH && doorFrames[1] == SOUTH) ||
                (doorFrames[0] == SOUTH && doorFrames[1] == Direction.NORTH) ||
                (doorFrames[0] == Direction.EAST && doorFrames[1] == Direction.WEST) ||
                (doorFrames[0] == Direction.WEST && doorFrames[1] == Direction.EAST)) {
            this.shape = RoomShape.STRAIGHT;
        }
        else {
            this.shape = RoomShape.BEND;
        }
    }

    public Direction[] getDoorFrames() {
        return doorFrames;
    }

    public RoomShape getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
