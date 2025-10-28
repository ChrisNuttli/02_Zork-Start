package ch.bbw.zork.commands;

import ch.bbw.zork.Command;
import ch.bbw.zork.House;

public class MapCommand extends Command implements ICommand{
    public MapCommand(int time) {
        super("map", time);
    }

    public void processCommand(String... args) {
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }

        System.out.print(House.rooms.get(0));
    }
}
