package ch.bbw.commands;

import ch.bbw.zork.Command;
import ch.bbw.zork.Game;
import ch.bbw.zork.Parser;

import java.util.InputMismatchException;

public class QuitCommand extends Command implements ICommand {

	public QuitCommand(int time) {
		super("quit", time);
	}

	public void processCommand() {
		System.out.println("Are you sure you want to quit (y/N)?");
		boolean answerValid = false;
		do {
			try {
				boolean answer = Parser.readYesNo(false);
				if (answer) {
					Game.quitGame = true;
				}
				answerValid = true;
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.printf("An Error has occurred:%n%s", e.getMessage());
			}
		} while (!answerValid);
	}

	public void processCommand(String arg) {
		this.processCommand();
	}

	public boolean checkArgValidity(String arg) {
		return true;
	}
}
