package au.com.ioof.types;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dean on 14/12/2016.
 */
public class MovementTypeTest {

    @Test
    public void testGetMovementType() throws Exception {
        assertEquals(MovementType.LEFT, MovementType.getMovementType("LEFT"));
        assertEquals(MovementType.RIGHT, MovementType.getMovementType("RIGHT"));
        assertEquals(MovementType.MOVE, MovementType.getMovementType("MOVE"));
        assertEquals(null, MovementType.getMovementType("PRINT"));
    }
}