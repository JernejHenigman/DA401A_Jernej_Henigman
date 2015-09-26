package androiddevelopment.assignment_4;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nejc on 25.9.2015.
 */
public class Treasure {

    public final int N_TREASURES = 3;
    private final double RADIUS = 15;
    private final double[][] TREASURE_LOCATIONS = {{55.600976, 13.026251},{55.603439, 13.020287}, {55.604957, 13.017147},{55.608279, 13.005161}};
    public final int[] RIGHT_ANSWERS = {1,0,2,0};
    private static int TREASURE_FOUND_COUNTER = 0;
    private Location current_player_location;
    private Location current_treasure_location;
    private LatLng latLng;


    public Treasure() {

    }

    public void setCurrentPlayerLocation(Location location) {
        current_player_location = new Location("");
        current_player_location.setLongitude(location.getLongitude());
        current_player_location.setLatitude(location.getLatitude());
    }

    public void setCurrentTreasureLocation() {
        current_treasure_location = new Location("");
        current_treasure_location.setLongitude(TREASURE_LOCATIONS[TREASURE_FOUND_COUNTER][1]);
        current_treasure_location.setLatitude(TREASURE_LOCATIONS[TREASURE_FOUND_COUNTER][0]);
    }

    public void incrementTreasureFoundCounter() {
        TREASURE_FOUND_COUNTER += 1;
    }

    public LatLng getCurrentTreasureLocation() {
        latLng = new LatLng(current_treasure_location.getLatitude(),current_treasure_location.getLongitude());
        return latLng;

    }

    public int getTreasureFoundCounter() {
        return TREASURE_FOUND_COUNTER;
    }

    public boolean isTreasureFound() {

        if (current_player_location.distanceTo(current_treasure_location) <= RADIUS) {
            return true;
        }

        return false;
    }

}