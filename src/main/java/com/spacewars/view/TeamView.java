package com.spacewars.view;

import javafx.scene.Group;

import java.util.ArrayList;

public class TeamView extends Group {
    private ArrayList<ShipView> shipViews = new ArrayList<>();
    private int x;
    private int y;

    public void destroy() {
        for(ShipView shipView : shipViews) {
            shipView.destroy();
        }

        shipViews.clear();
    }

    public ArrayList<ShipView> getShipViews() {
        return shipViews;
    }

    public void setShipViews(ArrayList<ShipView> shipViews) {
        this.shipViews = shipViews;
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
