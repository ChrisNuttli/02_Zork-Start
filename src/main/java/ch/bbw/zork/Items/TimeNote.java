package ch.bbw.zork.Items;

import ch.bbw.zork.Game;
import ch.bbw.zork.interfaces.IncreaseTime;

public class TimeNote extends Note implements IncreaseTime {
    private int timeIncrease;
    private boolean increased;

    public TimeNote() {
        super("A Note with a message written for one of the residents");
        this.increased = false;
        this.timeIncrease = Game.getRandom().nextInt(80) + 10;
        this.setText(getMessage(this.timeIncrease));
    }

    private static String getMessage(int time) {
        if (time <= 20) {
            return "I’ve been called into an unexpected late shift at work, so please go spend the evening at Aunt Lisa’s until we’re back. \nDad";
        }
        else if (time <= 40) {
            return "We're going late-night shopping. Wait at Jessica's place, we'll get you as soon as we are home.\n-Love Mom";
        }
        else if (time <= 60) {
            return "We’ve been invited to an emergency dinner with clients, so please leave and stay at the community center until we’re back.\nMom";
        }
        else if (time <= 80) {
            return "A sudden appointment came up, so walk to the park and remain there until we return.\n-Love Dad";
        }
        else {
            return "The car broke down and we’ll be delayed, so go wait at your friend’s house until we get home.\nXOXO Mom";
        }
    }

    @Override
    public void setTimeIncrease(int time) {
        this.timeIncrease = time;
    }

    @Override
    public int getTimeIncrease() {
        return this.timeIncrease;
    }

    @Override
    public boolean wasIncreased() {
        return increased;
    }

    @Override
    public void setWasIncreased(boolean wasIncreased) {
        this.increased = wasIncreased;
    }
}
