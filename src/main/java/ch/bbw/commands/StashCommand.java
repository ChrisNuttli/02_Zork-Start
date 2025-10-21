package ch.bbw.commands;

import ch.bbw.zork.Command;

public class StashCommand extends Command implements ICommand {
	public StashCommand(int time) {
		super("stash", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
