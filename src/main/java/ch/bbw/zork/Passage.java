package ch.bbw.zork;

import ch.bbw.zork.enums.Direction;

import java.util.HashMap;

public class Passage extends Furniture {
	private HashMap<Direction, Room> rooms;

	public Passage(Direction dirA, Room roomA, Direction dirB, Room roomB) {
		super("Door", "", false);
		this.rooms = new HashMap<>();
		this.rooms.put(dirA, roomA);
		this.rooms.put(dirB, roomB);
	}

	public Room getRoom(Direction dir) {
		return this.rooms.get(dir);
	}
}
