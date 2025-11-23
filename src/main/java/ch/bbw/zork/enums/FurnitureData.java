package ch.bbw.zork.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static ch.bbw.zork.enums.ItemData.*;

public enum FurnitureData {
    TABLE(
            "table",
            "A simple wooden table",
            "",
            new String[]{"Tabletop"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY,
                            LOCATION_NOTE
                    }
            },
            null
    ),
    TOILET(
            "toilet",
            "A standard porcelain toilet",
            "",
            null,
            null,
            null
    ),
    BED(
            "bed",
            "A comfortable bed",
            "Under the Bed",
            new String[]{"Under the Bed"},
            new ItemData[][] {
                    new ItemData[] {
                            FLASHLIGHT,
                            CODE_NOTE
                    }
            },
            null
    ),
    SOFA(
            "sofa",
            "A soft sofa",
            "",
            null,
            null,
            null
    ),
    CHAIR(
            "chair",
            "A basic chair",
            "",
            null,
            null,
            null
    ),
    DESK(
            "desk",
            "A working desk",
            "",
            new String[]{"Tabletop", "Drawer"},
            new ItemData[][] {
                    new ItemData[] {
                            TIME_NOTE
                    },
                    new ItemData[] {
                            KEY,
                            BACKPACK
                    }
            },
            null
    ),
    WARDROBE(
            "wardrobe",
            "A large wardrobe",
            "Behind a fake wall",
            new String[]{"Main compartment", "Drawer"},
            new ItemData[][] {
                    new ItemData[] {
                            BACKPACK
                    },
                    new ItemData[] {
                            JEWELRY
                    }
            },
            new boolean[]{true, true}
    ),
    FRIDGE(
            "fridge",
            "A refrigerator for food storage",
            "",
            new String[]{"Main Compartment", "Freezer"},
            new ItemData[][] {
                    new ItemData[] {
                            LOCATION_NOTE
                    },
                    new ItemData[] {
                            KEY
                    }
            },
            null
    ),
    STOVE(
            "stove",
            "A kitchen stove",
            "",
            null,
            null,
            null
    ),
    BATHTUB(
            "bathtub",
            "A bathtub for bathing",
            "",
            null,
            null,
            null
    ),
    SINK(
            "sink",
            "A wash basin",
            "",
            null,
            null,
            null
    ),
    SHOWER(
            "shower",
            "A standing shower",
            "",
            null,
            null,
            null
    ),
    MIRROR(
            "mirror",
            "A wall mirror",
            "",
            null,
            null,
            null
    ),
    NIGHTSTAND(
            "nightstand",
            "A small bedside table",
            "",
            new String[]{"Drawer"},
            new ItemData[][] {
                    new ItemData[] {
                            FLASHLIGHT
                    }
            },
            new boolean[]{true}
    ),
    LAMP(
            "lamp",
            "A standing lamp",
            "",
            null,
            null,
            null
    ),
    SHELF(
            "shelf",
            "A storage shelf",
            "Behind a hidden door at the back",
            new String[]{"Shelf top"},
            new ItemData[][] {
                    new ItemData[] {
                            LOCATION_NOTE
                    }
            },
            null
    ),
    BOOKCASE(
            "bookcase",
            "A tall bookcase",
            "",
            new String[]{"Bookcase"},
            new ItemData[][] {
                    new ItemData[] {
                            TIME_NOTE,
                            CODE_NOTE
                    }
            },
            null
    ),
    CABINET(
            "cabinet",
            "A storage cabinet",
            "Under a fake floor",
            new String[]{"Left Door", "Right Door"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY
                    },
                    new ItemData[] {
                            BACKPACK
                    }
            },
            new boolean[]{true, true}
    ),
    WASHING_MACHINE(
            "washing machine",
            "A washing machine",
            "",
            null,
            null,
            null
    ),
    DRYER(
            "dryer",
            "A clothes dryer",
            "",
            null,
            null,
            null
    ),
    FREEZER(
            "freezer",
            "A large freezer",
            "",
            new String[]{"Freezer"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY
                    }
            },
            new boolean[]{false}
    ),
    TOOLBOX(
            "toolbox",
            "A box for tools",
            "",
            new String[]{"Toolbox"},
            new ItemData[][] {
                    new ItemData[] {
                            CROWBAR
                    }
            },
            new boolean[]{false}
    ),
    LADDER(
            "ladder",
            "A wooden ladder",
            "",
            null,
            null,
            null
    ),
    BOX(
            "inside",
            "A simple cardboard box",
            "",
            new String[]{"Box"},
            new ItemData[][] {
                    new ItemData[] {
                            LOCATION_NOTE
                    }
            },
            new boolean[]{false}
    ),
    BARREL(
            "inside",
            "A wooden barrel",
            "",
            new String[]{"Barrel"},
            new ItemData[][] {
                    new ItemData[] {
                            JEWELRY
                    }
            },
            new boolean[]{false}
    ),
    WINE_RACK(
            "wine rack",
            "A rack for wine bottles",
            "",
            new String[]{"Rack"},
            new ItemData[][] {
                    new ItemData[] {
                            CODE_NOTE
                    }
            },
            new boolean[]{false}
    ),
    BENCH(
            "bench",
            "A wooden bench",
            "",
            null,
            null,
            null
    ),
    COAT_RACK(
            "coat rack",
            "A rack for coats",
            "Behind a sliding door in the wall",
            null,
            null,
            null
    ),
    SHOE_RACK(
            "shoe rack",
            "A rack for shoes",
            "",
            null,
            null,
            null
    ),
    SIDEBOARD(
            "sideboard",
            "A dining sideboard",
            "",
            new String[]{"Top", "Left Door", "Right Door", "Top Drawer", "Middle Drawer", "Bottom Drawer"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY
                    },
                    new ItemData[] {
                            BACKPACK
                    },
                    new ItemData[] {
                            JEWELRY
                    },
                    new ItemData[] {
                            CODE_NOTE
                    },
                    new ItemData[] {
                            LOCATION_NOTE
                    },
                    new ItemData[] {
                            TIME_NOTE
                    }
            },
            new boolean[]{false, true, true, false, false, false}
    ),
    GRILL(
            "grill",
            "An outdoor grill",
            "",
            null,
            null,
            null
    ),
    GARDEN_TABLE(
            "garden table",
            "An outdoor table",
            "",
            new String[]{"Table top"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY
                    }
            },
            new boolean[]{false}
    ),
    GARDEN_CHAIR(
            "garden chair",
            "An outdoor chair",
            "",
            null,
            null,
            null
    ),
    FLOWER_POT(
            "flower pot",
            "A decorative flower pot",
            "",
            new String[]{"Under Pot"},
            new ItemData[][] {
                    new ItemData[] {
                            KEY
                    }
            },
            null
    ),
    COFFEE_TABLE(
            "coffee table",
            "A low living room table",
            "",
            new String[]{"Table Top"},
            new ItemData[][] {
                    new ItemData[] {
                            FLASHLIGHT
                    }
            },
            null
    ),
    TV_STAND(
            "tv stand",
            "A stand for a television",
            "Behind the TV",
            null,
            null,
            null
    ),
    FILING_CABINET(
            "filing cabinet",
            "An office filing cabinet",
            "Behind the drawer built into the wall",
            new String[]{"Top Drawer", "Bottom Drawer"},
            new ItemData[][] {
                    new ItemData[] {
                            LOCATION_NOTE
                    },
                    new ItemData[] {
                            KEY,
                            CODE_NOTE
                    }
            },
            new boolean[]{true, true}
    ),
    COMPUTER(
            "printer",
            "A desktop Computer",
            "",
            new String[]{"Files"},
            new ItemData[][] {
                    new ItemData[] {
                            TIME_NOTE,
                            LOCATION_NOTE
                    }
            },
            new boolean[]{true}
    ),
    SAFE(
            "safe",
            "A Safe for storing valuables",
            "",
            new String[]{"Inside"},
            new ItemData[][] {
                    new ItemData[] {
                            JEWELRY
                    }
            },
            new boolean[]{true}
    );

    private final String name;
    private final String description;
    private final String safeHidingSpot;
    private final String[] containerNames;
    private final boolean[] containerCanBeLocked;
    private final ItemData[][] containerValidItems;
    private final HashMap<RoomData, Integer> maxPerRoom;

    FurnitureData(String name, String description, String hidingSpot, String[] containerNames, ItemData[][] containerValidItems, boolean[] containerCanBeLocked) {
        this.name = name;
        this.description = description;
        this.safeHidingSpot = hidingSpot;
        this.maxPerRoom = new HashMap<>();
        this.containerNames = containerNames;
        if (containerNames != null) {
            if (containerCanBeLocked == null) {
                this.containerCanBeLocked = new boolean[containerNames.length];
                for (int i = 0; i < containerNames.length; i++) {
                    this.containerCanBeLocked[i] = false;
                }
            } else {
                this.containerCanBeLocked = containerCanBeLocked;
            }

            this.containerValidItems = containerValidItems;
        } else {
            this.containerCanBeLocked = null;
            this.containerValidItems = null;
        }

        switch (name) {
            case "table":
                this.maxPerRoom.put(RoomData.DINING_ROOM, 1);
                this.maxPerRoom.put(RoomData.KITCHEN, 1);
                break;
            case "toilet":
                this.maxPerRoom.put(RoomData.BATHROOM, 1);
                break;
            case "bed":
                this.maxPerRoom.put(RoomData.BEDROOM, 1);
                break;
            case "sofa":
                this.maxPerRoom.put(RoomData.LIVING_ROOM, 2);
                break;
            case "chair":
                this.maxPerRoom.put(RoomData.DINING_ROOM, 6);
                this.maxPerRoom.put(RoomData.OFFICE, 2);
                break;
            case "desk":
                this.maxPerRoom.put(RoomData.OFFICE, 2);
                this.maxPerRoom.put(RoomData.BEDROOM, 1);
                break;
            case "wardrobe":
                this.maxPerRoom.put(RoomData.BEDROOM, 1);
                break;
            case "fridge":
                this.maxPerRoom.put(RoomData.KITCHEN, 1);
                break;
            case "stove":
                this.maxPerRoom.put(RoomData.KITCHEN, 1);
                break;
            case "bathtub":
                this.maxPerRoom.put(RoomData.BATHROOM, 1);
                break;
            case "sink":
                this.maxPerRoom.put(RoomData.BATHROOM, 1);
                this.maxPerRoom.put(RoomData.KITCHEN, 1);
                break;
            case "shower":
                this.maxPerRoom.put(RoomData.BATHROOM, 1);
                break;
            case "mirror":
                this.maxPerRoom.put(RoomData.BATHROOM, 1);
                this.maxPerRoom.put(RoomData.CORRIDOR, 1);
                break;
            case "nightstand":
                this.maxPerRoom.put(RoomData.BEDROOM, 2);
                break;
            case "lamp":
                this.maxPerRoom.put(RoomData.BEDROOM, 2);
                this.maxPerRoom.put(RoomData.LIVING_ROOM, 2);
                this.maxPerRoom.put(RoomData.CORRIDOR, 1);
                break;
            case "shelf":
                this.maxPerRoom.put(RoomData.ATTIC, 3);
                this.maxPerRoom.put(RoomData.BASEMENT, 3);
                this.maxPerRoom.put(RoomData.CELLAR, 3);
                break;
            case "bookcase":
                this.maxPerRoom.put(RoomData.LIVING_ROOM, 2);
                this.maxPerRoom.put(RoomData.OFFICE, 2);
                break;
            case "cabinet":
                this.maxPerRoom.put(RoomData.KITCHEN, 2);
                this.maxPerRoom.put(RoomData.DINING_ROOM, 1);
                break;
            case "washing_machine":
                this.maxPerRoom.put(RoomData.BASEMENT, 1);
                break;
            case "dryer":
                this.maxPerRoom.put(RoomData.BASEMENT, 1);
                break;
            case "freezer":
                this.maxPerRoom.put(RoomData.BASEMENT, 1);
                this.maxPerRoom.put(RoomData.CELLAR, 1);
                break;
            case "toolbox":
                this.maxPerRoom.put(RoomData.BASEMENT, 1);
                break;
            case "ladder":
                this.maxPerRoom.put(RoomData.ATTIC, 1);
                break;
            case "box":
                this.maxPerRoom.put(RoomData.ATTIC, 5);
                this.maxPerRoom.put(RoomData.CELLAR, 5);
                break;
            case "barrel":
                this.maxPerRoom.put(RoomData.CELLAR, 3);
                break;
            case "wine_rack":
                this.maxPerRoom.put(RoomData.CELLAR, 1);
                break;
            case "bench":
                this.maxPerRoom.put(RoomData.CORRIDOR, 1);
                this.maxPerRoom.put(RoomData.FRONT_YARD, 2);
                break;
            case "coatrack":
                this.maxPerRoom.put(RoomData.CORRIDOR, 1);
                break;
            case "shoe_rack":
                this.maxPerRoom.put(RoomData.CORRIDOR, 1);
                break;
            case "sideboard":
                this.maxPerRoom.put(RoomData.DINING_ROOM, 1);
                break;
            case "grill":
                this.maxPerRoom.put(RoomData.FRONT_YARD, 1);
                break;
            case "garden_table":
                this.maxPerRoom.put(RoomData.FRONT_YARD, 1);
                break;
            case "garden_chair":
                this.maxPerRoom.put(RoomData.FRONT_YARD, 4);
                break;
            case "flower_pot":
                this.maxPerRoom.put(RoomData.FRONT_YARD, 5);
                break;
            case "coffee_table":
                this.maxPerRoom.put(RoomData.LIVING_ROOM, 1);
                break;
            case "tv_stand":
                this.maxPerRoom.put(RoomData.LIVING_ROOM, 1);
                break;
            case "filing_cabinet":
                this.maxPerRoom.put(RoomData.OFFICE, 2);
                break;
            case "printer":
                this.maxPerRoom.put(RoomData.OFFICE, 1);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMax(RoomData roomData) {
        try {
            return this.maxPerRoom.get(roomData);
        }
        catch(NullPointerException e) {
            return 0;
        }
    }

    public ArrayList<ItemData> validItemsInContainers(String container) {
        ArrayList<ItemData> validItems = new ArrayList<>();
        for (int i = 0; i < this.containerNames.length; i++) {
            if (this.containerNames[i].equals(container)) {
                validItems.addAll(Arrays.asList(this.containerValidItems[i]));
                break;
            }
        }

        return validItems;
    }

    public String getHidingSpot() {
        return safeHidingSpot;
    }

    public ArrayList<String> getContainerNames() {
        if (this.containerNames == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(this.containerNames));
    }

    public boolean getContainerCanBeLocked(String containerName) {
        for (int i = 0; i < this.containerNames.length; i++) {
            if (this.containerNames[i].equalsIgnoreCase(containerName)) {
                return this.containerCanBeLocked[i];
            }
        }

        throw new RuntimeException("Invalid container name: " + containerName);
    }
}



