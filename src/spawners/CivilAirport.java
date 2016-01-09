package spawners;

import controllers.AeroplaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Point;
import java.io.IOException;

/**
 * Created by kadash on 18.10.15.
 */
public class CivilAirport extends Airport{
    private final String imagePath = "images/Airport-icon.png";

    public CivilAirport(Point civilAirportPosition, Pane context) {
        super(civilAirportPosition, context);
        this.imageViewOfObject.setImage(new Image(this.imagePath));
    }

    public String getImagePath() {
        return imagePath;
    }
}
