package meansOfTransport;

import javafx.scene.layout.Pane;
import spawners.Airport;
import spawners.Harbor;
import spawners.MilitaryAirport;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAircraft extends Aeroplane  {
    private String ammoType;
    public MilitaryAircraft(ArrayList<MilitaryAirport> allDestination, Pane context, int id) {
        super(context);
        for (MilitaryAirport militaryAirport : allDestination) {
            this.route.add(militaryAirport );
        }
        this.ID = id;
    }

}
