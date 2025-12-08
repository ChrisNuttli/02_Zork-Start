package ch.bbw.zork;

import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.enums.LockType;
import ch.bbw.zork.enums.RoomData;
import ch.bbw.zork.interfaces.Unlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Door {
	private HashMap<Direction, Room> rooms;
    private Lock lock;

	public Door(Direction dirA, Room roomA, Direction dirB, Room roomB) {
		this.rooms = new HashMap<>();
		this.rooms.put(dirA, roomA);
		this.rooms.put(dirB, roomB);

        roomA.addDoor(dirA.getOpposite(), this);
        roomB.addDoor(dirB.getOpposite(), this);
	}

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        if (this.lock != null) {
            Game.getHouse().removeItem(lock.getKey());
            return;
        }
        this.lock = lock;
    }

    public boolean isLocked() {
        if (this.lock == null) { return false; }
        return this.lock.isLocked();
    }

    public void tryUnlock(Unlock key) {
        if (this.lock == null) {
            throw new IllegalStateException("This passage does not have a lock");
        }

        this.lock.tryUnlock(key);
    }

    public void lockDoor() {
        setLock(new Lock(LockType.KEY_HOLE));
    }

    public Room traverse(Direction direction) {
        if (this.lock != null && this.lock.isLocked()) {
            throw new RuntimeException("The door seems to be locked.");
        }

        return this.rooms.get(direction);
    }

    @Override
    public String toString() {
        Iterator<Room> roomIterator = this.rooms.values().iterator();
        return String.format("Locked: %s\tRoomA: %s\t\t\tRoomB: %s", isLocked(), roomIterator.next().getName(), roomIterator.next().getName());
    }
}
