package au.com.ioof.model;

/**
 * A table that a robot can move over
 */
public class Table {

    private int length;
    private int breadth;

    public Table(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    /**
     * Will return true if the given coordinate is a valid table coordinate
     *
     * @param coordinate the coordinate to check
     * @return true if given coordinate is a valid table coordinate
     */
    public boolean validCoordinate(Coordinate coordinate) {
        return coordinate != null
                && Math.abs(coordinate.getX()) == coordinate.getX()
                && Math.abs(coordinate.getY()) == coordinate.getY()
                && coordinate.getX() < length
                && coordinate.getY() < breadth;
    }

}
