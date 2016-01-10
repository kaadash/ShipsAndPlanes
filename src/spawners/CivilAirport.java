package spawners;

import controllers.CivilSpawnerController;
import controllers.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import travelDependency.Passenger;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class CivilAirport extends Airport implements Runnable {
    private final String imagePath = "images/Airport-icon.png";

    private ArrayList<Passenger> passengersInCity = new ArrayList<Passenger>();

    public CivilAirport(Point civilAirportPosition, Pane context) {
        super(civilAirportPosition, context);
        this.imageViewOfObject.setImage(new Image(this.imagePath));
    }
    public void openInformationPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            TabPane root = fxmlLoader.load(getClass().getResource("civilSpawnerLayout.fxml").openStream());
            CivilSpawnerController civilSpawnerController = (CivilSpawnerController) fxmlLoader.getController();
            civilSpawnerController.updateView(this);
            addClickActionToObject(root, "Civil Airport Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void addClickActionToObject(TabPane root, String windowTitle){
        imageViewOfObject.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        });
    }
    public void checkAndAddNewPassengers() {
        int counterOfPassengersToAdd = 0;
        for(Passenger passenger : Dashboard.waitingPassengers) {

            if ((passenger.getCurrentPosition().getX() == (int)getRightLaneStartingPoint().getX()
                    && passenger.getCurrentPosition().getY() == (int)getRightLaneStartingPoint().getY())
                    || (passenger.getCurrentPosition().getX() == (int)getLeftLaneStartingPoint().getX()
                    && passenger.getCurrentPosition().getY() == (int)getLeftLaneStartingPoint().getY())) {
                this.passengersInCity.add(Dashboard.waitingPassengers.get(counterOfPassengersToAdd));
            }
            counterOfPassengersToAdd++;
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Passenger> getPassengersInCity() {
        return passengersInCity;
    }

    public void setPassengersInCity(ArrayList<Passenger> passengersInCity) {
        this.passengersInCity = passengersInCity;
    }

    @Override
    public void run() {
        while(true) {
            try {
                passengersInCity.clear();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        checkAndAddNewPassengers();
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
