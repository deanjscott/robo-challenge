package au.com.ioof.control;

import au.com.ioof.exception.InvalidMovementException;
import au.com.ioof.exception.InvalidUserCommandException;
import au.com.ioof.model.Coordinate;
import au.com.ioof.model.Robot;
import au.com.ioof.model.Table;
import au.com.ioof.types.Direction;
import au.com.ioof.types.InstructionType;
import au.com.ioof.types.MovementType;

import java.io.PrintStream;

/**
 * Controller class that controls a robots movement over a given table
 */
public class RoboController {

    private Robot robot;
    private Table table;
    private PrintStream reportOutputStream;

    /**
     * Default constructor
     *
     * @param robot              the robot to control
     * @param table              the table to move the robot over
     * @param reportOutputStream the stream to print the reports to
     */
    public RoboController(Robot robot, Table table, PrintStream reportOutputStream) {
        this.robot = robot;
        this.table = table;
        this.reportOutputStream = reportOutputStream;
    }

    /**
     * Move the robot executing the one movement type given
     *
     * @param movement the single move you want the robot to execute
     */
    public void moveRobot(MovementType movement) throws InvalidMovementException {
        if (!robot.isPlaced()) {
            //ignore commands if the robot has not been placed
            return;
        }
        switch (movement) {
            case MOVE:
                moveRobotForward();
                break;
            case LEFT:
                robot.setDirection(robot.getDirection().getLeftDirection());
                break;
            case RIGHT:
                robot.setDirection(robot.getDirection().getRightDirection());
                break;
            default:
                break;
        }
    }

    /**
     * Place the robot at the desired coordinate
     *
     * @param coordinate the coordinates that the robot should be placed
     */
    public void placeRobot(Coordinate coordinate, Direction direction) throws InvalidUserCommandException {
        if (!table.validCoordinate(coordinate)) {
            throw new InvalidUserCommandException("Given coordinates are outside table " + coordinate);
        }
        robot.setCurrentCoordinate(coordinate);
        robot.setDirection(direction);
    }


    /**
     * Print out to the System.out the current state of the robot
     */
    public void printReport() {
        if (!robot.isPlaced()) {
            //ignore commands if the robot has not been placed
            return;
        }
        reportOutputStream.println(robot.toString());
    }

    /**
     * <p>Process a user command, user commands should be one of the following:<br/>
     * <ul>
     * <li>PLACE [X COORDINATE],[Y COORDINATE],[DIRECTION]</li>
     * <li>MOVE</li>
     * <li>LEFT</li>
     * <li>RIGHT</li>
     * <li>REPORT</li>
     * </ul>
     * <p>
     *
     * @param userCommand
     * @throws InvalidUserCommandException
     */
    public void processUserInstruction(String userCommand) throws InvalidUserCommandException {
        if (userCommand == null) {
            return;
        }

        String[] inputs = userCommand.trim().split(" ");
        String commandString = "";
        if (inputs.length > 0) {
            commandString = inputs[0];
        }

        InstructionType instruction = InstructionType.getInstruction(commandString);
        if (instruction != null) {
            executeInstruction(instruction, inputs);
            return;
        }

        MovementType movement = MovementType.getMovementType(commandString);
        if (movement != null) {
            try {
                moveRobot(movement);
                return;
            } catch (InvalidMovementException e) {
                throw new InvalidUserCommandException("Movement[" + movement + "] ignored, robot will fall off the table if issued");
            }
        }
        throw new InvalidUserCommandException("Invalid command issued[" + userCommand + "]");
    }

    /**
     * Execute a user instruction, using the given user input data
     *
     * @param instruction the instruction to execute
     * @param userInputs  the full user command string including the instruction text
     * @throws InvalidUserCommandException
     */
    private void executeInstruction(InstructionType instruction, String... userInputs) throws InvalidUserCommandException {
        if (instruction == InstructionType.PLACE) {
            if (userInputs.length != 2) {
                throw new InvalidUserCommandException("PLACE instruction is incorrect, correct example is PLACE 0,0,NORTH");
            } else {
                String[] coordinates = userInputs[1].trim().split(",");
                Direction direction = Direction.getDirection(coordinates[2]);
                if (direction == null) {
                    throw new InvalidUserCommandException("Invalid direction[" + coordinates[2] + "] given");
                }
                try {
                    Coordinate newCoordinate = new Coordinate(coordinates[0], coordinates[1]);
                    placeRobot(newCoordinate, direction);
                } catch (NumberFormatException e) {
                    throw new InvalidUserCommandException("Invalid x y coordinates given[" + coordinates[0] + "," + coordinates[1] + "]");
                }
            }
        } else if (instruction == InstructionType.REPORT) {
            printReport();
        }
    }

    /**
     * Move the robot forward
     *
     * @throws InvalidMovementException if the robot will move off the table
     */
    private void moveRobotForward() throws InvalidMovementException {
        Coordinate newCoordinate = new Coordinate(
                robot.getCurrentCoordinate().getX() + robot.getDirection().getxDirectionIncrement(),
                robot.getCurrentCoordinate().getY() + robot.getDirection().getyDirectionIncrement());
        //test if the new Coordinate is valid on the table
        if (table.validCoordinate(newCoordinate)) {
            robot.setCurrentCoordinate(newCoordinate);
        } else {
            //throw exception
            throw new InvalidMovementException("Movement is invalid as it falls outside the table ");
        }
    }
}
