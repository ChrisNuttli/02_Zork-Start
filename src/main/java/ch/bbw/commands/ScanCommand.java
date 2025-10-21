package ch.bbw.commands;

import ch.bbw.zork.Command;

public class ScanCommand extends Command implements ICommand {

	public ScanCommand(int time) {
		super("scan", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
