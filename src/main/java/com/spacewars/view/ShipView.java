package com.spacewars.view;

import com.spacewars.util.ImageHelper;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShipView extends Group {

    private Group root;
    private Circle teamIndicator;
    private HealthBarView healthBarView;
    private int width = 50;

    public static final Color COLOR_ALLY = Color.LAWNGREEN;
    public static final Color COLOR_ENEMY = Color.ORANGERED;

    public ShipView(Group root, String imageFilepath, int x, int y, Color shipColor,
                    int shieldMax, int armorMax, int hullMax) {
        this.root = root;

        drawShipView(imageFilepath, x, y, shipColor, shieldMax, armorMax, hullMax);
    }

    public void drawShipView(String imageFilepath, int x, int y, Color color,
                             int shieldMax, int armorMax, int hullMax) {
        this.setLayoutX(x);
        this.setLayoutY(y);

        teamIndicator = getTeamIndicator(color, width/2.0, width/2.0);
        getChildren().add(teamIndicator);

        healthBarView = new HealthBarView(shieldMax, armorMax, hullMax, 5.0, 15.0);
        healthBarView.setLayoutX(0);
        healthBarView.setLayoutY(0);
        getChildren().add(healthBarView);

        ImageView image = ImageHelper.createImageAtLocation(imageFilepath, 0, 0, width, width);
        getChildren().add(image);
    }
    private Circle getTeamIndicator(Color color, double x, double y) {
        Circle highlight = new Circle();
        highlight.setFill(color);

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(25);
        highlight.setEffect(blur);

        highlight.setCenterX(x);
        highlight.setCenterY(y);
        highlight.setRadius(10);

        return highlight;
    }

    public Group getRoot() {
        return root;
    }

    public Circle getTeamIndicator() {
        return teamIndicator;
    }

    public HealthBarView getHealthBarView() {
        return healthBarView;
    }

    public void destroy() {
        root.getChildren().remove(this);
    }

    public int getWidth() {
        return width;
    }
}
