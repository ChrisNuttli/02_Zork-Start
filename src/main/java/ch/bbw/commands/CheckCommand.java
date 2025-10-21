package ch.bbw.commands;

import ch.bbw.zork.Command;

public class CheckCommand extends Command implements ICommand {

	public CheckCommand(int time) {
		super("check", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
