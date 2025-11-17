package ch.bbw.zork.Rooms;

import ch.bbw.zork.Constants;
import ch.bbw.zork.Game;
import ch.bbw.zork.Passage;
import ch.bbw.zork.Room;
import ch.bbw.zork.enums.Direction;
import ch.bbw.zork.interfaces.NorthPassage;

import java.util.Random;

public class FrontYard extends Room implements NorthPassage {
    private Passage passageNorth;

    public FrontYard() {
        super("Front Yard", ""); // TODO: Add Description
        this.setCoordinates(Math.floorDiv(Constants.MAP_WIDTH, 2), Constants.MAP_HEIGHT - 1);
        initialize();
    }

    private void initialize() {
        this.addPossibleNeighbor(Direction.NORTH,"Kitchen");
        this.addPossibleNeighbor(Direction.NORTH,"Corridor");
    }

    @Override
    public Passage getPassageNorth() {
        return passageNorth;
    }

    @Override
    public void setPassageNorth(Passage passageNorth) {
        this.passageNorth = passageNorth;
    }

    @Override
    public Room getNeighborNorth() {
        return this.getNeighbor(Direction.NORTH);
    }

    @Override
    public int[] getCoordinatesNorth() {
        return new int[]{ this.getX(), this.getY()-1 };
    }
}
