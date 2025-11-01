package ch.bbw.zork;

public class Tuple<I, J> {
	public final I first;
	public final J second;

	public Tuple(I first, J second) {
		this.first = first;
		this.second = second;
	}

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
