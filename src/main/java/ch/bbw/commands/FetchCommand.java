package ch.bbw.commands;

import ch.bbw.zork.Command;

public class FetchCommand extends Command implements ICommand {

	public FetchCommand(int time) {
		super("fetch", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
