package ch.bbw.zork.enums;

public enum CommandData {
    HELP(),
    QUIT(),
    MOVE(1, 1, new String[] {"direction"}),
    SCAN(),
    CHECK(1, 1, new String[] {"int"}),
    TAKE(1, 2, new String[] {"int", "String"});

    private final int minArgumentCount;
    private final int maxArgumentCount;
    private final String[] argumentTypes;

    private CommandData() {
        this.minArgumentCount = 0;
        this.maxArgumentCount = 0;
        this.argumentTypes = new  String[0];
    }

    private CommandData(int minArgumentCount, int maxArgumentCount, String[] argumentTypes) {
        this.minArgumentCount = minArgumentCount;
        this.maxArgumentCount = maxArgumentCount;
        this.argumentTypes = argumentTypes;
    }

    public int getMinArgumentCount() {
        return minArgumentCount;
    }

    public int getMaxArgumentCount() {
        return maxArgumentCount;
    }

    public String[] getArgumentTypes() {
        return argumentTypes;
    }
}
