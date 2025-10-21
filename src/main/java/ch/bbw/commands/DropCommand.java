package ch.bbw.commands;

import ch.bbw.zork.Command;

public class DropCommand extends Command implements ICommand {

	public DropCommand(int time) {
		super("drop", time);
	}

	public void processCommand() {

	}

	public void processCommand(String arg) {

	}

	public boolean checkArgValidity(String arg) {
		return false;
	}

	@Override
	public String toString() {
		return String.join("\n",
				""
		);
	}
}
