package au.com.ioof.types;

/**
 * Allowable directions that a robot can face
 */
public enum Direction {
    NORTH(1,0),SOUTH(-1,0),EAST(0,1),WEST(0,-1);

    static{
        //set up all the direction references after the enums have been created
        NORTH.leftDirection = WEST;
        NORTH.rightDirection = EAST;
        SOUTH.rightDirection = WEST;
        SOUTH.leftDirection = EAST;
        EAST.leftDirection = NORTH;
        EAST.rightDirection = SOUTH;
        WEST.leftDirection = SOUTH;
        WEST.rightDirection = NORTH;
    }

    private int xDirectionIncrement;
    private int yDirectionIncrement;
    private Direction leftDirection;
    private Direction rightDirection;

    Direction(int yDirectionIncrement, int xDirectionIncrement) {
        this.xDirectionIncrement = xDirectionIncrement;
        this.yDirectionIncrement = yDirectionIncrement;
    }

    public int getxDirectionIncrement() {
        return xDirectionIncrement;
    }

    public int getyDirectionIncrement() {
        return yDirectionIncrement;
    }

    public Direction getLeftDirection() {
        return leftDirection;
    }

    public Direction getRightDirection() {
        return rightDirection;
    }


    public static Direction getDirection(String direction){
        try{
            return Direction.valueOf(direction.toUpperCase());
        } catch(IllegalArgumentException | NullPointerException e){
            return null;
        }
    }
}
