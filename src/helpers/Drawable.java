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
    protected ImageView imageViewMeanOfTransport = new ImageView(new Image("images/aircraft.png"));
    protected Pane context;
    protected Point2D currentPosition;

    public Drawable(Pane context){
        this.context = context;
        int sizeImage = 32;
        imageViewMeanOfTransport.setFitHeight(sizeImage);
        imageViewMeanOfTransport.setFitWidth(sizeImage);
        context.getChildren().add(imageViewMeanOfTransport);
    }
    public void animate() {
        imageViewMeanOfTransport.setLayoutX(this.getCurrentPosition().getX());
        imageViewMeanOfTransport.setLayoutY(this.getCurrentPosition().getY());
    }
    protected void addClickActionToObject(TabPane root, String windowTitle){
        imageViewMeanOfTransport.setOnMouseClicked(event -> {
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
