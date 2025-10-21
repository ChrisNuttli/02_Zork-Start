package ch.bbw.commands;

public interface ICommand {
	void processCommand();
	void processCommand(String arg);
	boolean checkArgValidity(String arg);

	String getSummary();
	int getTime();
}
