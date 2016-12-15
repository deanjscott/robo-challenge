package au.com.ioof.model;

import au.com.ioof.types.Direction;

/**
 * Robot class containing the position and direction of the robot
 */
public class Robot {

    public Robot(Coordinate currentCoordinate, Direction direction) {
        this.currentCoordinate = currentCoordinate;
        this.direction = direction;
    }

    public Robot(){

    }

    private Coordinate currentCoordinate;
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    /**
     * Returns true if the robot has been placed, i.e the coordinates and direction have been set
     * @return
     */
    public boolean isPlaced(){
        if(currentCoordinate == null || direction == null){
            return false;
        }
        return true;
    }

    public String toString(){
        return "Robot is currently at " + getCurrentCoordinate() + " and direction " + getDirection();
    }
}
