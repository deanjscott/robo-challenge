package au.com.ioof.types;

/**
 * Allowable instruction types for a robot
 */
public enum InstructionType {
    PLACE, REPORT;

    public static InstructionType getInstruction(String instruction){
        try{
            return InstructionType.valueOf(instruction);
        } catch(IllegalArgumentException e){
            return null;
        }
    }
}
