package ch.bbw.zork.commands;

import ch.bbw.zork.Command;

public class ScanCommand extends Command implements ICommand {

	public ScanCommand(int time) {
		super("scan", time);
	}

	public void processCommand(String... args) {
        // TODO: Implement Method
        if (!this.checkArgValidity(args)) {
            throw new RuntimeException("Provided args are invalid");
        }
	}
}
