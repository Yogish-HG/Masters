/**
 * Capture a two-dimensional (x, y) point, with integer coordinates.
 */
public class Point {
    private int x, y;

    /**
     * Create a point at a given location.
     * @param x -- x coordinate
     * @param y -- y coordinate
     */
    public Point( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the x coordinate of this point.
     * @return -- the integer x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y coordinate of this point.
     * @return -- the integer y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Report the straight-line distance from this point to another given point
     * @param to -- the target destination point to which we want to know the distance
     * @return -- the distance
     */
    public Double distanceTo( Point to ) {
        Double distance = Math.sqrt( (to.x - this.x)*(to.x - this.x) + (to.y - this.y)*(to.y - this.y));
        return distance;
    }

    /**
     * If leaving the current point, going to turnAt and then turning to turnTo, determine whether
     * that is a right turn, a left turn, a straight through, or a U-turn.  Straight is defined as being a
     * deviation in direction of <= "degreeTolearance" degrees.  A U-turn is a return towards the start within
     * that same tolerance level.
     * @param turnAt -- the point to which we're heading right now and will eventually turn
     * @param turnTo -- the point to where we turn at the turnAt point
     * @param degreeTolerance -- the degree variation that we allow before deciding that we have made a turn
     * @return -- one of Left, Right, Straight, or UTurn
     */
    public TurnDirection turnType( Point turnAt, Point turnTo, int degreeTolerance ) {
        /* The sine function asks for radians, so we must convert our degree measure to radians. */
        Double tolerance = Math.sin( (degreeTolerance/180.0)*Math.PI );

        /* Conceptually make vectors this -> turnAt and turnAt -> turnTo.  The sine of the angle between
         * these vectors defines how much of a turn and the sign of that sine tells us if it is to the left
         * or the right.
         */
        Double firstLeg = distanceTo( turnAt );
        Double secondLeg = turnAt.distanceTo( turnTo );
        Double turnSize = ((this.y - turnAt.y)*(turnTo.x - this.x) + (turnAt.x - this.x)*(turnTo.y - this.y)) / (firstLeg * secondLeg);

        if (Math.abs(turnSize) <= tolerance) {
            /* Small angle.  Look to the sign of the cosine of the angle between the vectors to determine
             * if we're going ahead or turning around.
             */
            if ( ((turnAt.x - this.x)*(turnTo.x - turnAt.x) + (turnAt.y - this.y)*(turnTo.y - turnAt.y)) >= 0.0 ) {
                return TurnDirection.Straight;
            } else {
                return TurnDirection.UTurn;
            }
        } else if (turnSize > 0.0) {
            return TurnDirection.Left;
        } else {
            return TurnDirection.Right;
        }
    }
}
