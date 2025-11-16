package ch.bbw.zork;

public class MapTile {
    private int x;
    private int y;
    private Room room;
    private int entropy;

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Room getRoom() {
        return room;
    }
}
