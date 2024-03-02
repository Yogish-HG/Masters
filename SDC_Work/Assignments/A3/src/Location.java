/**
 * Identify a location on a map by the street name a side of the street
 */
public class Location {
    private String streetId;
    private StreetSide streetSide;

    /**
     * Create a location on the map
     * @param street -- the id of the street where we are.
     * @param whichSide -- the side of the street where we start.
     */
    public Location( String street, StreetSide whichSide ) {
        streetId = street;
        streetSide = whichSide;
    }

    /**
     * Return the street id represented by this location
     * @return -- the street of this location
     */
    public String getStreetId() {
        return streetId;
    }

    /**
     * Return the side of the current street represented by this location
     * @return -- the side of the street of this location
     */
    public StreetSide getStreetSide() {
        return streetSide;
    }
}
