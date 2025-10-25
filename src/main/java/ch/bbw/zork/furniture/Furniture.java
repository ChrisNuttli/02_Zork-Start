package ch.bbw.zork.furniture;

import java.util.UUID;

public abstract class Furniture {
    private final String ID;
	private final String name;
	private final String description;

	public Furniture(String name, String description) {
        this.ID = UUID.randomUUID().toString();
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
