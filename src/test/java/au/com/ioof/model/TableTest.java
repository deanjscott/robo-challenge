package au.com.ioof.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dean on 13/12/2016.
 */
public class TableTest {

    @Test
    public void testValidCoordinate() throws Exception {
        Table table = new Table(5,5);
        assertEquals(false, table.validCoordinate(new Coordinate(5,5)));
        assertEquals(false, table.validCoordinate(new Coordinate(-1,1)));
        assertEquals(false, table.validCoordinate(new Coordinate(6,0)));
        assertEquals(false, table.validCoordinate(new Coordinate(0,5)));
        assertEquals(false, table.validCoordinate(new Coordinate(7,7)));

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++) {
                assertEquals(true, table.validCoordinate(new Coordinate(i, j)));
            }
        }
    }

}