package com.reactlibrarylantern;

public abstract class LanternBase {
    abstract public void turn(boolean state);
    abstract public boolean isTurnOn();

    public void turnOn() {
        turn(true);
    };

    public void turnOff() {
        turn(false);
    };
}
