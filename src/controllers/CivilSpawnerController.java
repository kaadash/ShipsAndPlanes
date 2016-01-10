package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import spawners.CivilAirport;
import spawners.MilitaryAirport;
import travelDependency.Passenger;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kadash on 07.01.16.
 */
public class CivilSpawnerController implements Initializable {
    private CivilAirport civilAirportContext;
    private MilitaryAirport militaryAirportContext;

    @FXML
    private Label ammoTypeLabel;
    @FXML
    private Label passengersValueLabel;
    @FXML
    private Label currentPositionLabel;
    @FXML
    private Label currentVehiclesLabel;
    @FXML
    private TableView<Passenger> tableView;

    public void updateView(CivilAirport civilAirport) {
        civilAirportContext = civilAirport;
        passengersValueLabel.setText(Integer.toString(civilAirport.getPassengersInCity().size()));
        currentPositionLabel.setText(civilAirport.getRightLaneStartingPoint().toString());
        currentVehiclesLabel.setText(Integer.toString(civilAirport.getCurrentVehicles()) +
                "/" + Integer.toString(civilAirport.getMaxCapability()));
        ObservableList<Passenger> data = FXCollections.observableArrayList(civilAirport.getPassengersInCity());
        tableView.setItems(data);
    }

    public void updateView(MilitaryAirport militaryAirport) {
        militaryAirportContext = militaryAirport;
        currentPositionLabel.setText(militaryAirport.getCurrentPosition().toString());
        passengersValueLabel.setText("none - this is military airport");
        currentVehiclesLabel.setText(Integer.toString(militaryAirport.getCurrentVehicles()) +
                "/" + Integer.toString(militaryAirport.getMaxCapability()));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
