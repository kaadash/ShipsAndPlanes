package meansOfTransport;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kadash on 18.10.15.
 */
public class MilitaryAircraft extends Aeroplane {
    private String ammoType;
    public MilitaryAircraft(ArrayList<Point> allDestination, Pane context) {
        super(allDestination, context);
    }

}
