package ch.bbw.zork;

import ch.bbw.zork.Items.Item;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.Unlock;

import java.util.HashMap;

public class Door {
	private HashMap<Direction, Room> rooms;
    private Lock lock;

	public Door(Direction dirA, Room roomA, Direction dirB, Room roomB) {
		this.rooms = new HashMap<>();
		this.rooms.put(dirA, roomA);
		this.rooms.put(dirB, roomB);
	}

	public Room getRoom(Direction dir) {
		return this.rooms.get(dir);
	}

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public boolean isLocked() {
        if (this.lock == null) { return false; }
        return this.lock.isLocked();
    }

    public boolean tryUnlock(Unlock key) {
        if (this.lock == null) {
            throw new IllegalStateException("This passage does not have a lock");
        }

        try {
            return this.lock.tryUnlock(key);
        }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }
}
