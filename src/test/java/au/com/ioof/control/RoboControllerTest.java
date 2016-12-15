package au.com.ioof.control;

import au.com.ioof.exception.InvalidUserCommandException;
import au.com.ioof.model.Coordinate;
import au.com.ioof.model.Robot;
import au.com.ioof.model.Table;
import au.com.ioof.types.Direction;
import au.com.ioof.types.MovementType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoboControllerTest {

    @Mock
    PrintStream outputStream;

    @Mock
    Robot robot;

    @Mock
    Table table;

    RoboController controller;

    @Before
    public void setUp() throws Exception {
        controller = new RoboController(robot,table,outputStream);
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
    }

    @Test
    public void testMoveRobotForward() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.NORTH).when(robot).getDirection();
        doReturn(new Coordinate(0,0)).when(robot).getCurrentCoordinate();
        doReturn(true).when(table).validCoordinate(any(Coordinate.class));
        controller.moveRobot(MovementType.MOVE);
        verify(robot).setCurrentCoordinate(refEq(new Coordinate(0,1)));
    }

    @Test
    public void testMoveRobotForwardNotPlaced() throws Exception {
        doReturn(false).when(robot).isPlaced();
        controller.moveRobot(MovementType.MOVE);
        verify(robot, never()).setCurrentCoordinate(any());
        verify(robot, never()).setDirection(any());
    }

    @Test
    public void testTurnRobotLEFT() throws Exception {
        testTurnRobot(Direction.NORTH,Direction.WEST,MovementType.LEFT);
        testTurnRobot(Direction.WEST,Direction.SOUTH,MovementType.LEFT);
        testTurnRobot(Direction.SOUTH,Direction.EAST,MovementType.LEFT);
        testTurnRobot(Direction.EAST,Direction.NORTH,MovementType.LEFT);
    }

    @Test
    public void testTurnRobotRIGHT() throws Exception {
        testTurnRobot(Direction.NORTH,Direction.EAST,MovementType.RIGHT);
        testTurnRobot(Direction.EAST,Direction.SOUTH,MovementType.RIGHT);
        testTurnRobot(Direction.SOUTH,Direction.WEST,MovementType.RIGHT);
        testTurnRobot(Direction.WEST,Direction.NORTH,MovementType.RIGHT);
    }

    private void testTurnRobot(Direction currentDirection, Direction expectedDirection, MovementType movementType) throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(currentDirection).when(robot).getDirection();
        controller.moveRobot(movementType);
        verify(robot).setDirection(expectedDirection);
    }

    @Test
    public void testPlaceRobotValidSpot() throws Exception {
        Coordinate coordinate = new Coordinate(1,2);
        Direction direction = Direction.NORTH;
        doReturn(true).when(table).validCoordinate(coordinate);
        controller.placeRobot(coordinate, direction);
        verify(robot).setDirection(direction);
        verify(robot).setCurrentCoordinate(coordinate);
    }

    @Test(expected = InvalidUserCommandException.class)
    public void testPlaceRobotInvalidSpot() throws Exception {
        Coordinate coordinate = new Coordinate(1,2);
        Direction direction = Direction.NORTH;
        doReturn(false).when(table).validCoordinate(coordinate);
        controller.placeRobot(coordinate, direction);
    }

    @Test
    public void testPrintReportPlaced() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.EAST).when(robot).getDirection();
        doReturn(new Coordinate(1,2)).when(robot).getCurrentCoordinate();
        controller.printReport();
        verify(outputStream).println(any(String.class));
    }

    @Test
    public void testPrintReportNotPlaced() throws Exception {
        doReturn(false).when(robot).isPlaced();
        controller.printReport();
        verify(outputStream, never()).println(any(String.class));
    }

    @Test
    public void testProcessUserInstructionPLACE() throws Exception {
        doReturn(true).when(table).validCoordinate(any(Coordinate.class));
        controller.processUserInstruction("PLACE 1,1,NORTH");
        verify(robot).setDirection(Direction.NORTH);
        verify(robot).setCurrentCoordinate(refEq(new Coordinate(1,1)));
    }

    @Test
    public void testProcessUserInstructionMOVE() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.NORTH).when(robot).getDirection();
        doReturn(new Coordinate(0,0)).when(robot).getCurrentCoordinate();
        doReturn(true).when(table).validCoordinate(any(Coordinate.class));
        controller.processUserInstruction("MOVE");
        verify(robot).setCurrentCoordinate(refEq(new Coordinate(0,1)));
    }

    @Test
    public void testProcessUserInstructionLEFT() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.NORTH).when(robot).getDirection();
        doReturn(new Coordinate(0,0)).when(robot).getCurrentCoordinate();
        doReturn(true).when(table).validCoordinate(any(Coordinate.class));
        controller.processUserInstruction("LEFT");
        verify(robot).setDirection(Direction.WEST);
    }

    @Test
    public void testProcessUserInstructionRIGHT() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.NORTH).when(robot).getDirection();
        doReturn(new Coordinate(0,0)).when(robot).getCurrentCoordinate();
        doReturn(true).when(table).validCoordinate(any(Coordinate.class));
        controller.processUserInstruction("RIGHT");
        verify(robot).setDirection(Direction.EAST);
    }

    @Test
    public void testProcessUserInstructionREPORT() throws Exception {
        doReturn(true).when(robot).isPlaced();
        doReturn(Direction.EAST).when(robot).getDirection();
        doReturn(new Coordinate(1,2)).when(robot).getCurrentCoordinate();
        controller.processUserInstruction("REPORT");
        verify(outputStream).println(any(String.class));
    }


    @Test
    public void runActualSimulationMovementTests(){
        Robot robot = new Robot();
        controller = new RoboController(robot,new Table(5,5),outputStream);
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("Instructions.txt"));
        scanner = scanner.useDelimiter("\\n");
        String instruction;
        while(true){
            try {
                instruction = scanner.next().trim();
                try{
                    controller.processUserInstruction(instruction);
                } catch(InvalidUserCommandException e){
                    //ignore as results must match after run
                }

                if(instruction != null && instruction.startsWith("RESULT:")){
                    //check result matches
                    String expectedResult = instruction.split(":")[1].trim();
                    assertEquals(expectedResult, robot.toString());
                    //reset robot for next test
                    robot.setDirection(null);
                    robot.setCurrentCoordinate(null);
                }
            } catch(NoSuchElementException e){
                break;
            } catch(Exception e){
                fail("Error occured during test, MSG:" + e.getMessage());
            }
        }
    }

}