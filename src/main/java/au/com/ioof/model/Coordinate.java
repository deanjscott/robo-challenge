package au.com.ioof.model;

/**
 * A coordinate that a robot can occupy
 */
public class Coordinate {
    private int x,y;

    public Coordinate(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public Coordinate(String x, String y) {
        this.y = Integer.parseInt(y);
        this.x = Integer.parseInt(x);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String toString(){
        return "Coordinate X(" + x + ") and Y(" + y +")";
    }
}
