package com.frequem.epic.iface;

import java.util.ArrayList;

public interface SpriteContainable {
    public void addSprite(Sprite s);
    public void removeSprite(Sprite s);
    public ArrayList<Sprite> getSprites();
}
