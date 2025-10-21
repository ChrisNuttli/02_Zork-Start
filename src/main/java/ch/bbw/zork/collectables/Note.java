package ch.bbw.zork.collectables;

public abstract class Note extends Collectable {
	private final String content;

	public Note(String name, String description, String content) {
		super(name, description);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
