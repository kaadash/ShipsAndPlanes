package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by kadash on 28.11.15.
 */
public class Drawable {
    public ImageView draw (String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
