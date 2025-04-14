package com.spacewars.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBarView extends Group {
    public static final Color COLOR_SHIELD = new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(),
            Color.BLUE.getBlue(), .5);
    public static final Color COLOR_ARMOR = new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(),
            Color.ORANGE.getBlue(), .5);
    public static final Color COLOR_HULL = new Color(Color.RED.getRed(), Color.RED.getGreen(),
            Color.RED.getBlue(), .5);
    public static final Color COLOR_MAX = new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(),
            Color.WHITE.getBlue(), .5);
    private double shieldMaxValue;
    private double shieldValue;
    private double armorMaxValue;
    private double armorValue;
    private double hullMaxValue;
    private double hullValue;
    private double barWidth;
    private double barHeight;
    private Rectangle shieldRectangle;
    private Rectangle armorRectangle;
    private Rectangle hullRectangle;
    private Rectangle shieldMaxRectangle;
    private Rectangle armorMaxRectangle;
    private Rectangle hullMaxRectangle;

    public HealthBarView(int shieldMaxValue, int armorMaxValue, int hullMaxValue, double barWidth, double barHeight) {
        this.shieldMaxValue = shieldMaxValue;
        this.armorMaxValue = armorMaxValue;
        this.hullMaxValue = hullMaxValue;
        this.barWidth = barWidth;
        this.barHeight = barHeight;

        shieldMaxRectangle = new Rectangle();
        shieldMaxRectangle.setFill(Color.GRAY);
        shieldMaxRectangle.setStroke(Color.BLACK);
        shieldMaxRectangle.setX(0);
        shieldMaxRectangle.setY(0);
        shieldMaxRectangle.setWidth(barWidth);
        shieldMaxRectangle.setHeight(barHeight);
        getChildren().add(shieldMaxRectangle);

        armorMaxRectangle = new Rectangle();
        armorMaxRectangle.setFill(Color.GRAY);
        armorMaxRectangle.setStroke(Color.BLACK);
        armorMaxRectangle.setX(barWidth);
        armorMaxRectangle.setY(0);
        armorMaxRectangle.setWidth(barWidth);
        armorMaxRectangle.setHeight(barHeight);
        getChildren().add(armorMaxRectangle);

        hullMaxRectangle = new Rectangle();
        hullMaxRectangle.setFill(Color.GRAY);
        hullMaxRectangle.setStroke(Color.BLACK);
        hullMaxRectangle.setX(barWidth * 2);
        hullMaxRectangle.setY(0);
        hullMaxRectangle.setWidth(barWidth);
        hullMaxRectangle.setHeight(barHeight);
        getChildren().add(hullMaxRectangle);

        shieldRectangle = new Rectangle();
        shieldRectangle.setFill(COLOR_SHIELD);
        shieldRectangle.setStroke(Color.BLACK);
        shieldRectangle.setX(0);
        shieldRectangle.setY(0);
        shieldRectangle.setWidth(barWidth);
        shieldRectangle.setHeight(barHeight);
        getChildren().add(shieldRectangle);

        armorRectangle = new Rectangle();
        armorRectangle.setFill(COLOR_ARMOR);
        armorRectangle.setStroke(Color.BLACK);
        armorRectangle.setX(barWidth);
        armorRectangle.setY(0);
        armorRectangle.setWidth(barWidth);
        armorRectangle.setHeight(barHeight);
        getChildren().add(armorRectangle);

        hullRectangle = new Rectangle();
        hullRectangle.setFill(COLOR_HULL);
        hullRectangle.setStroke(Color.BLACK);
        hullRectangle.setX(2 * barWidth);
        hullRectangle.setY(0);
        hullRectangle.setWidth(barWidth);
        hullRectangle.setHeight(barHeight);
        getChildren().add(hullRectangle);

        this.setValues(shieldMaxValue, armorMaxValue, hullMaxValue);
    }

    public void setValues(double shieldValue, double armorValue, double hullValue) {
        this.shieldValue = shieldValue;
        this.armorValue = armorValue;
        this.hullValue = hullValue;

        updateDrawing();
    }

    private void updateDrawing() {
        double shieldPercent = ( shieldValue > 0 ? shieldValue : 0) / shieldMaxValue;
        double armorPercent = ( armorValue > 0 ? armorValue : 0) / armorMaxValue;
        double hullPercent = ( hullValue > 0 ? hullValue : 0) / hullMaxValue;

        double shieldY = barHeight - barHeight * shieldPercent;
        double armorY = barHeight - barHeight * armorPercent;
        double hullY = barHeight - barHeight * hullPercent;

        shieldRectangle.setY(shieldY);
        shieldRectangle.setHeight(barHeight - shieldY);
        armorRectangle.setY(armorY);
        armorRectangle.setHeight(barHeight - armorY);
        hullRectangle.setY(hullY);
        hullRectangle.setHeight(barHeight - hullY);
    }

    public double getShieldValue() {
        return shieldValue;
    }

    public double getArmorValue() {
        return armorValue;
    }

    public double getHullValue() {
        return hullValue;
    }
}
