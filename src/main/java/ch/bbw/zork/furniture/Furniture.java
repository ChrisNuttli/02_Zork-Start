package ch.bbw.zork.furniture;

public abstract class Furniture {
	private final String name;
	private final String description;

	public Furniture(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
