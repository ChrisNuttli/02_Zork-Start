package ch.bbw.commands;

import ch.bbw.zork.Command;
import ch.bbw.zork.CommandWords;

public class HelpCommand extends Command implements ICommand {

	public HelpCommand(int time) {
		super("help", time);
	}

	public void processCommand() {
		System.out.println("The goal of the game is to break into a home to find the safe and");
		System.out.println("escape with it's contents before the time runs out.");
		System.out.println("Each action takes a certain amount of time, and the residents are");
		System.out.printf("expected to return in 2 hours.%n");
		System.out.println("Here is a list of all commands:");
		System.out.println("command\tsummary\ttime");
		System.out.printf("%-10s %-80s %-10s %n", "Command", "Description", "Time");
		System.out.println("--------------------------------------------------------------------------------------------------");
		CommandWords.commands.forEach((w,c) ->
				System.out.printf("%-10s %-80s %-2d min %n", w, c.getSummary(), c.getTime())
		);
		System.out.println("For more Detailed info about a specific command, use 'help <command>'");
	}

	public void processCommand(String arg) {
		this.processCommand(arg);
	}

	public boolean checkArgValidity(String arg) {
		return false;
	}
}
