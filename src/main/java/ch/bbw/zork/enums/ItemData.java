package ch.bbw.zork.enums;

public enum ItemData {
    // TODO: add Descriptions, space and weight
    BACKPACK("", 75.0),
    CROWBAR("", 35.0),
    FLASHLIGHT("", 50.0),
    KEY("", 1, 5),
    CODE_NOTE("", 3, 7),
    LOCATION_NOTE("", 3, 10),
    TIME_NOTE("", 1, 5),
    JEWELRY("", 1, 1);

    private final double spawnProbability;
    private final int maxSpawns;
    private final int minSpawns;
    private final String description;
    private final int space;
    private final double weight;

    private ItemData(String description, double spawnProbability) {
        this(description, spawnProbability, 1);
    }

    private ItemData(String description, double spawnProbability, int maxSpawns) {
        this(description, spawnProbability, maxSpawns, 0, 0, 0);
    }

    private ItemData(String description, int minSpawns, int maxSpawns) {
        this(description, 100, maxSpawns, minSpawns, 0, 0);
    }

    private ItemData(String description, double spawnProbability, int maxSpawns, int minSpawns, int space, double weight) {
        this.spawnProbability = spawnProbability;
        this.maxSpawns = maxSpawns;
        this.minSpawns = minSpawns;
        this.description = description;
        this.space = space;
        this.weight = weight;
    }

    public double getSpawnProbability() {
        return spawnProbability;
    }

    public int getMaxSpawns() {
        return maxSpawns;
    }

    public int getMinSpawns() {
        return minSpawns;
    }

    public String getDescription() {
        return description;
    }

    public int getSpace() {
        return this.space;
    }

    public double getWeight() {
        return this.weight;
    }
}
