package ch.bbw.zork.enums;

public enum ItemData {
    BACKPACK(75.0),
    CROWBAR(35.0),
    FLASHLIGHT(50.0),
    KEY(1, 5),
    CODE_NOTE(3, 7),
    LOCATION_NOTE(3, 10),
    TIME_NOTE(1, 5),
    JEWELRY(1, 1);

    private final double spawnProbability;
    private final int maxSpawns;
    private final int minSpawns;

    private ItemData(double spawnProbability) {
        this(spawnProbability, 1);
    }

    private ItemData(double spawnProbability, int maxSpawns) {
        this(spawnProbability, maxSpawns, 0);
    }

    private ItemData(int minSpawns, int maxSpawns) {
        this(100, maxSpawns, minSpawns);
    }

    private ItemData(double spawnProbability, int maxSpawns, int minSpawns) {
        this.spawnProbability = spawnProbability;
        this.maxSpawns = maxSpawns;
        this.minSpawns = minSpawns;
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
}
