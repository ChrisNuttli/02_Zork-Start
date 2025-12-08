package ch.bbw.zork.Items;

import ch.bbw.zork.Game;

public class Note extends Item {
    private String text;

    public Note(String description, String text) {
        super("Note", description, 0, 0);
        this.text = text;
    }

    public Note(String description) {
        super("Note", description, 0, 0);
    }

    public String getText() {
        return text;
    }

    protected void setText(String text) {
        this.text = text;
    }
}
