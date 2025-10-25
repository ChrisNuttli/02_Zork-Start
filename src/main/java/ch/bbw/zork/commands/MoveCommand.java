package ch.bbw.zork.commands;

import ch.bbw.zork.Command;

public class MoveCommand extends Command implements ICommand {
	public MoveCommand(int time) {
        super("move", time);
        this.setValidArgs("north",
            "east",
            "south",
            "west",
            "n",
            "e",
            "s",
            "w",
            "up",
            "right",
            "down",
            "left"
        );
        this.setMinArgs(1);
        this.setMaxArgs(1);
	}

    public void processCommand(String... args) {
        // TODO: Implement Method
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }
    }
}
