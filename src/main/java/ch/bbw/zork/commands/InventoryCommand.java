package ch.bbw.zork.commands;

import ch.bbw.zork.Command;

public class InventoryCommand extends Command implements ICommand {
    public InventoryCommand(int time) {
        super("inventory", time);
    }

    public void processCommand(String... args) {
        // TODO: Implement Method
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }
    }
}
