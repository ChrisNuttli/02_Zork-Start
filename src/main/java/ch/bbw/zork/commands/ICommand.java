package ch.bbw.zork.commands;

public interface ICommand {
	void processCommand(String... args);
	boolean checkArgValidity(String... args);

	String getSummary();
	int getTime();
}
