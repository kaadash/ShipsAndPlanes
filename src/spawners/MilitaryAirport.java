package spawners;

import controllers.CivilSpawnerController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.awt.*;
import java.io.IOException;


/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAirport extends Airport implements Runnable{
    public MilitaryAirport(Point militaryAirportPosition, Pane context) {
        super(militaryAirportPosition, context);
        this.imagePath = "images/AIRPORT.jpg";
        this.imageViewOfObject.setImage(new Image(this.imagePath));
        openInformationPanel();
    }
    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("civilSpawnerLayout.fxml").openStream());
            CivilSpawnerController civilSpawnerController = (CivilSpawnerController) fxmlLoader.getController();
            civilSpawnerController.updateView(this);
            addClickActionToObject(root, "Military Airport Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openInformationPanel();
                    }
                });
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
