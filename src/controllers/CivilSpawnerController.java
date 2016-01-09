package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import meansOfTransport.AircraftCarrier;
import spawners.CivilAirport;

import java.awt.*;
import java.awt.geom.Point2D;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by kadash on 07.01.16.
 */
public class CivilSpawnerController implements Initializable {
    private CivilAirport civilAirportContext;

    @FXML
    private Label ammoTypeLabel;
    @FXML
    private Label currentPositionLabel;
    @FXML
    private Label IDLabel;

    public void updateView(CivilAirport civilAirport) {
        civilAirportContext = civilAirport;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
