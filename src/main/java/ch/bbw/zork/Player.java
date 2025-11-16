package ch.bbw.zork;

public class Player {
    private String name;
    private int x;
    private int y;

    public Player() {
        this.name = "";
        while (this.name.equals("")) {
            this.name = Zork2.parser.promptInput("Enter your name: ");
        }
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
