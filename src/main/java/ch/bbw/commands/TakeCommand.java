package ch.bbw.commands;

import ch.bbw.zork.Command;

public class TakeCommand extends Command implements ICommand {

	public TakeCommand(int time) {
		super("take", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
