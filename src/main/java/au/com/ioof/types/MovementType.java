package au.com.ioof.types;

/**
 * allowable movement types for a robot
 */
public enum MovementType {
    MOVE,LEFT,RIGHT;

    public static MovementType getMovementType(String movement){
        try{
            return MovementType.valueOf(movement.toUpperCase());
        } catch(IllegalArgumentException | NullPointerException e){
            return null;
        }
    }
}
