package ch.bbw.zork.commands;

import ch.bbw.zork.Command;

public class TakeCommand extends Command implements ICommand {

	public TakeCommand(int time) {
		super("take", time);
	}

    public void processCommand(String... args) {
        // TODO: Implement Method
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }
    }

    @Override
	public boolean checkArgValidity(String... args) {
		// TODO: Implement Method
        if (args.length < this.getMinArgs() || args.length > this.getMaxArgs()) {
            return false;
        }
        return false;
	}
}
