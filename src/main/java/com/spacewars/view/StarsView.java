package com.spacewars.view;

import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class StarsView extends Group {
    public static final Color[] STAR_COLORS = new Color[]{
            Color.PURPLE,
            Color.GREEN,
            Color.BLUE,
            Color.RED,
            Color.WHITE
    };
    private Group root;
    private ArrayList<Circle> starList = new ArrayList<>();

    public StarsView() {}
    public StarsView(Group root, int maxX, int maxY) {
        drawSpace(root, maxX, maxY, (maxX/maxY) * 100);
    }

    public void drawSpace(Group root, int maxX, int maxY, int starCount) {
        destroy();

        Random random = new Random();
        this.root = root;

        for(int i = 0; i < starCount; i++) {
            drawStar(random.nextInt(maxX), random.nextInt(maxY));
        }

        root.getChildren().add(this);
    }

    private void drawStar(int x, int y) {
        Random random = new Random();

        Circle star = new Circle();
        Color starColor;
        if (random.nextInt(3) == 0) starColor = Color.WHITE;
        else starColor = STAR_COLORS[random.nextInt(STAR_COLORS.length)];
        star.setFill(starColor);

        star.setRadius(1);
        star.setCenterX(x);
        star.setCenterY(y);

        int size = random.nextInt(5) + 2;
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(size);
        star.setEffect(blur);

        starList.add(star);
        getChildren().add(star);
    }

    public void destroy() {
        starList = new ArrayList<>();
        if(root != null) root.getChildren().remove(this);
    }

    public Group getRoot() {
        return root;
    }

    public ArrayList<Circle> getStarList() {
        return starList;
    }
}
