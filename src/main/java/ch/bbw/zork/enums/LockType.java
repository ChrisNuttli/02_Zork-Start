package ch.bbw.zork.enums;

public enum LockType {
    KEY_HOLE("Key hole", "A hole for inserting a Key into"),
    NUMPAD("Numpad", "A Numpad for entering a 4 Digit Code");

    private final String name;
    private final String description;

    private LockType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
