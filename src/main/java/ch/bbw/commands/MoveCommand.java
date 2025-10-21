package ch.bbw.commands;

import ch.bbw.zork.Command;

public class MoveCommand extends Command implements ICommand {

	public MoveCommand(int time) {
		super("move", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
