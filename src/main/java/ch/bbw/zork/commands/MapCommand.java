package ch.bbw.zork.commands;

import ch.bbw.zork.Command;

public class MapCommand extends Command implements ICommand{
    public MapCommand(int time) {
        super("map", time);
    }

    public void processCommand(String... args) {
        // TODO: Implement Method
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }
    }
}
