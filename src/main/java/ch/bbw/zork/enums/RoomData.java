package ch.bbw.zork.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static ch.bbw.zork.enums.Direction.*;

public enum RoomData {
    ATTIC(
            "Attic",
            "",
            new Direction[]{ SOUTH },
            new String[][] {
                    new String[]{
                            "Corridor",
                            "Living Room"
                    }
            }
    ),
    BASEMENT(
            "Basement",
            "",
            new Direction[]{ EAST },
            new String[][] {
                    new String[]{
                            "Corridor",
                            "Kitchen"
                    }
            }
    ),
    BATHROOM(
            "Bathroom",
            "",
            new Direction[]{ WEST },
            new String[][] {
                    new String[]{
                            "Bedroom",
                            "Corridor",
                            "Living Room"
                    }
            }
    ),
    BEDROOM(
            "Bedroom",
            "",
            new Direction[]{ EAST, SOUTH },
            new String[][] {
                    new String[]{
                            "Office",
                            "Bathroom"
                    },
                    new String[]{
                            "Living Room",
                            "Corridor",
                    }
            }
    ),
    CELLAR(
            "Cellar",
            "",
            new Direction[]{ NORTH },
            new String[][] {
                    new String[]{
                            "Corridor",
                            "Kitchen"
                    }
            }
    ),
    CORRIDOR(
            "Corridor",
            "",
            new Direction[]{  NORTH, EAST, SOUTH, WEST },
            new String[][] {
                    new String[]{
                            "Office",
                            "Bedroom",
                            "Kitchen",
                            "Attic",
                    },
                    new String[]{
                            "Bathroom",
                            "Kitchen",
                            "Living Room"
                    },
                    new String[]{
                            "Cellar",
                            "Front Yard"
                    },
                    new String[]{
                            "Living Room",
                            "Basement",
                    },
            }
    ),
    DINING_ROOM(
            "Dining Room",
            "",
            new Direction[]{ EAST, WEST },
            new String[][] {
                    new String[]{
                            "Office",
                            "Living Room",
                    },
                    new String[]{
                            "Kitchen",
                            "Corridor"
                    },
            }
    ),
    FRONT_YARD(
            "Front Yard",
            "",
            new Direction[]{ NORTH },
            new String[][] {
                    new String[]{
                            "Kitchen",
                            "Corridor",
                    }
            }
    ),
    KITCHEN(
            "Kitchen",
            "",
            new Direction[]{ EAST, SOUTH, WEST },
            new String[][] {
                    new String[]{
                            "Living Room",
                            "Dining Room",
                    },
                    new String[]{
                            "Front Yard",
                            "Cellar",
                            "Corridor"
                    },
                    new String[]{
                            "Corridor",
                            "Basement",
                    },
            }
    ),
    LIVING_ROOM(
            "Living Room",
            "",
            new Direction[]{ NORTH, EAST, WEST },
            new String[][] {
                    new String[]{
                            "Attic",
                            "Office",
                            "Bedroom",
                    },
                    new String[]{
                            "Bathroom",
                            "Corridor",
                    },
                    new String[]{
                            "Kitchen",
                            "Dining Room",
                            "Corridor"
                    },
            }
    ),
    OFFICE(
            "Office",
            "",
            new Direction[]{ SOUTH, WEST },
            new String[][] {
                    new String[]{
                            "Living Room",
                            "Corridor",
                    },
                    new String[]{
                            "Dining Room",
                            "Bedroom",
                    },
            }
    );

    private final String name;
    private final String description;
    private final Direction[] doorFrames;
    private final RoomShape shape;
    private final HashMap<Direction, ArrayList<String>> validNeighbors;

    private RoomData(String name, String description, Direction[] doorFrames, String[][] validNeighborNames) {
        this.name = name;
        this.description = description;
        this.doorFrames = doorFrames;
//        this.validNeighborNames = validNeighborNames;

        this.validNeighbors = new HashMap<>();
        for (int i = 0; i < this.doorFrames.length; i++) {
            Direction dir = this.doorFrames[i];
            String[] neighborNames = validNeighborNames[i];
            if (!this.validNeighbors.containsKey(dir)) {
                this.validNeighbors.put(dir, new ArrayList<>());
            }

            ArrayList<String> neighbors = this.validNeighbors.get(dir);
            neighbors.addAll(Arrays.asList(neighborNames));
            this.validNeighbors.put(dir, neighbors);
        }

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

    public ArrayList<RoomData> getValidNeighborData(Direction direction) {
        ArrayList<RoomData> validNeighborData = new ArrayList<>();
        ArrayList<String> neighbors = this.validNeighbors.get(direction);
        for (String neighborName : neighbors) {
            switch(neighborName) {
                case "Attic":
                    validNeighborData.add(RoomData.ATTIC);
                    break;
                case "Office":
                    validNeighborData.add(RoomData.OFFICE);
                    break;
                case "Bedroom":
                    validNeighborData.add(RoomData.BEDROOM);
                    break;
                case "Basement":
                    validNeighborData.add(RoomData.BASEMENT);
                    break;
                case "Bathroom":
                    validNeighborData.add(RoomData.BATHROOM);
                    break;
                case "Front Yard":
                    validNeighborData.add(RoomData.FRONT_YARD);
                    break;
                case "Living Room":
                    validNeighborData.add(RoomData.LIVING_ROOM);
                    break;
                case "Kitchen":
                    validNeighborData.add(RoomData.KITCHEN);
                    break;
                case "Cellar":
                    validNeighborData.add(RoomData.CELLAR);
                    break;
                case "Corridor":
                    validNeighborData.add(RoomData.CORRIDOR);
                    break;
                case "Dining Room":
                    validNeighborData.add(RoomData.DINING_ROOM);
                    break;
            }
        }

        return validNeighborData;
    }
}
