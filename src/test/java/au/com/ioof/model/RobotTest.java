package au.com.ioof.model;

import au.com.ioof.types.Direction;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dean on 14/12/2016.
 */
public class RobotTest {

    @Test
    public void testisPlacedTrue() throws Exception {
        Robot robot = new Robot();
        robot.setCurrentCoordinate(new Coordinate(1,1));
        robot.setDirection(Direction.NORTH);
        assertEquals(true, robot.isPlaced());
    }

    @Test
    public void testisNotPlaced() throws Exception {
        Robot robot = new Robot();
        assertEquals(false, robot.isPlaced());
    }

    @Test
    public void testToString(){
        Robot robot = new Robot();
        assertEquals("Robot is currently at null and direction null", robot.toString());
        robot.setDirection(Direction.NORTH);
        robot.setCurrentCoordinate(new Coordinate(1,2));
        assertEquals("Robot is currently at Coordinate X(1) and Y(2) and direction NORTH", robot.toString());
    }
}