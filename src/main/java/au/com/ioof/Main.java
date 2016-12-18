package au.com.ioof;

import au.com.ioof.control.RoboController;
import au.com.ioof.exception.InvalidUserCommandException;
import au.com.ioof.model.Robot;
import au.com.ioof.model.Table;

import java.util.Scanner;

/**
 * Main class that starts up the application
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Robot...");
        Robot robot = new Robot();
        Table table = new Table(5, 5);
        RoboController controller = new RoboController(robot, table, System.out);

        System.out.println("Robot started, valid commands:\n" +
                "  PLACE [X],[Y],[F]\n" +
                "  MOVE\n" +
                "  LEFT\n" +
                "  RIGHT\n" +
                "  REPORT\n" +
                "  EXIT\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Please enter command and then press enter : ");
            String commandInput = scanner.nextLine();
            if ("EXIT".equalsIgnoreCase(commandInput)) {
                System.out.println("Shutting Down");
                break;
            }

            try {
                controller.processUserInstruction(commandInput);
            } catch (InvalidUserCommandException e) {
                System.out.println("Invalid user command issued: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
