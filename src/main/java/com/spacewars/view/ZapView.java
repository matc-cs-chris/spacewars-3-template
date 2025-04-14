package com.spacewars.view;

import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ZapView extends Group {
    Polygon line;
    Circle circle;

    public ZapView(double x, double y, double endX, double endY, Color startColor, Color endColor) {
        line = new Polygon(new double[] {
                x+1, y+1,
                x-1, y-1,
                endX-1, endY-1,
                endX+1, endY+1
        });

        Stop[] stops= new Stop[] {
                new Stop(.15, startColor),
                new Stop(1, endColor)
        };

        LinearGradient linearGradient;
        if(endY > y) {
            linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        }
        else {
            linearGradient = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
        }

        line.setFill(linearGradient);
        GaussianBlur lineBlur = new GaussianBlur();
        lineBlur.setRadius(5);
        line.setEffect(lineBlur);
        line.setOpacity(.5);
        getChildren().add(line);

        Circle circle = new Circle();
        circle.setRadius(15);
        circle.setCenterX(endX);
        circle.setCenterY(endY);
        circle.setFill(endColor);
        circle.setOpacity(.5);
        GaussianBlur circleBlur = new GaussianBlur();
        circleBlur.setRadius(25);
        circle.setEffect(circleBlur);
        getChildren().add(circle);
    }

    public void destroy() {
        getChildren().remove(line);
        getChildren().remove(circle);
        line = null;
        circle = null;
    }
}
