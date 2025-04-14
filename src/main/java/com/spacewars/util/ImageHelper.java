package com.spacewars.util;

import com.spacewars.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

public class ImageHelper {
    public static ImageView createImageAtLocation(String imageFilepath, double x, double y, int scaleWidth, int scaleHeight) {
        URL imageURL = Main.class.getResource(imageFilepath);
        if(imageURL == null) throw new RuntimeException(new IOException("Resource: " + imageFilepath + " not found!"));

        ImageView imageView = null;

        try {
            imageView = new ImageView(new Image(imageURL.openStream()));
            imageView.setFitHeight(scaleHeight);
            imageView.setFitWidth(scaleWidth);
            imageView.setX(x);
            imageView.setY(y);
        }
        catch(IOException ex) {
            throw new RuntimeException(ex);
        }

        return imageView;
    }
}
