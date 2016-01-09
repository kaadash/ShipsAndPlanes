package helpers;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.awt.geom.Point2D;

/**
 * Created by kadash on 28.11.15.
 */
public abstract class Drawable {
    protected ImageView imageViewOfObject = new ImageView();
    protected Pane context;
    protected Point2D currentPosition;

    public Drawable(Pane context){
        this.context = context;
        int sizeImage = 32;
        imageViewOfObject.setFitHeight(sizeImage);
        imageViewOfObject.setFitWidth(sizeImage);
        context.getChildren().add(imageViewOfObject);
    }
    public void animate() {
        imageViewOfObject.setLayoutX(this.getCurrentPosition().getX());
        imageViewOfObject.setLayoutY(this.getCurrentPosition().getY());
    }
    protected void addClickActionToObject(TabPane root, String windowTitle){
        imageViewOfObject.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        });
    }
    public Point2D getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(Point2D currentPosition) {
        this.currentPosition = currentPosition;
    }
}
