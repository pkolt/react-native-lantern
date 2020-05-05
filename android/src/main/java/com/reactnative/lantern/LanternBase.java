package com.reactnative.lantern;

public abstract class LanternBase {
    abstract public void turn(boolean state);
    abstract public boolean getTurnState();

    public void turnOn() {
        turn(true);
    };

    public void turnOff() {
        turn(false);
    };
}
