package com.frequem.epic.iface;

public interface Actionable {
    public void doAction(Action a);
    public void revertAction();
    public void advanceAction();
}
