package ch.bbw.zork;

import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.enums.RoomData;
import ch.bbw.zork.interfaces.Unlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public Room generateNeighbor() {
        if (this.rooms.size() == 2) {
            return null;
        }

        Direction dirA = this.rooms.keySet().iterator().next();
        Room roomA = this.rooms.get(dirA);

        int[] coords = Game.getHouse().getNeighborCoordinates(roomA.getX(), roomA.getY(), dirA.getOpposite());
        ArrayList<RoomData> candidates = Game.getHouse().getCandidatesForCoordinates(coords);

//        if (roomA.getRoomData() == RoomData.CORRIDOR) {
//            candidates.remove(RoomData.CORRIDOR);
//        }

        if (candidates.isEmpty() && Constants.MAX_CORRIDORS > Game.getHouse().countRoom(RoomData.CORRIDOR) && roomA.getRoomData() != RoomData.CORRIDOR) {
            candidates.add(RoomData.CORRIDOR);
//            throw new IllegalStateException("No possible candidates for the neighbor of room " + roomA.getName() + " was found");
        }

        if (candidates.size() > 1) {
            int maxDoors = 0;
            for (RoomData rd : candidates) {
//                if (rd == RoomData.CORRIDOR) {continue;}
                maxDoors = Math.max(maxDoors, rd.getDoorFrames().length);
            }

            int finalMaxDoors = maxDoors;
            candidates.removeIf(rd -> rd.getDoorFrames().length < finalMaxDoors);
            if (finalMaxDoors < 3 && Constants.MAX_CORRIDORS > Game.getHouse().countRoom(RoomData.CORRIDOR) && roomA.getRoomData() != RoomData.CORRIDOR) {
                candidates.add(RoomData.CORRIDOR);
            }

            int randomIndex = Game.getRandom().nextInt(candidates.size());
            RoomData roomData = candidates.get(randomIndex);
            Room neighbor = new Room(coords[0], coords[1], roomData);
            neighbor.addDoor(dirA, this);
            return neighbor;
        }
        else {
            RoomData roomData = candidates.get(0);
            Room neighbor = new Room(coords[0], coords[1], roomData);
            neighbor.addDoor(dirA, this);
            return neighbor;
        }
    }

    public void generateNeighbor(RoomData roomData) {
        Direction dirA = rooms.keySet().iterator().next();
        Direction dirB = dirA.getOpposite();
        Room roomA = rooms.get(dirA);

        int[] coordinatesB = Game.getHouse().getNeighborCoordinates(roomA.getX(), roomA.getY(), dirB);
        Room roomB = new Room(coordinatesB[0], coordinatesB[1], roomData);
        this.rooms.put(dirB, roomB);
        roomB.addDoor(dirA, this);
    }

	public Room getRoom(Direction dir) {
		return this.rooms.get(dir);
	}

    public void addRoom(Direction dir, Room room) {
        this.rooms.put(dir.getOpposite(), room);
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

    public Room traverse(Direction direction) {
        if (this.lock != null && this.lock.isLocked()) {
            throw new RuntimeException("The door seems to be locked.");
        }

        return this.rooms.get(direction);
    }

    public int getDepthScore() {
        int score = 0;
        for (Room room : this.rooms.values()) {
            score = Math.max(score, room.getDepthScore());
        }

        return score;
    }
}
