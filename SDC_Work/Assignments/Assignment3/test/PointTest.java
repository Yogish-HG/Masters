import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void turnType() {
        Point origin = new Point( 0, 0 );
        Point p1 = new Point( 0, 1 );
        Point p2 = new Point( 1, 1 );
        Point p3 = new Point( 1, 0 );
        Point p4 = new Point( 1, 10 );
        Point p5 = new Point( 1, -10 );

        assertEquals( TurnDirection.Right, origin.turnType( p1, p4, 1 ) );
        assertEquals( TurnDirection.Straight, origin.turnType( p1, p4, 20 ) );
        assertEquals( TurnDirection.Right, origin.turnType( p1, p5, 1 ) );
        assertEquals( TurnDirection.UTurn, origin.turnType( p1, p5, 20 ) );
        assertEquals( TurnDirection.Left, p3.turnType( p2, p1, 1 ) );
    }
}