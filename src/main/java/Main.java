import java.util.Scanner;

public class Main {
    // scanner used there and in SystemSimulation as having multiple scanners can get messy easily
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\nProvide number of elevators in the system");
        int elevatorsCount = Integer.parseInt(scanner.nextLine());

        System.out.println("Provide number of floors in the building (counting starts from 0)");
        int floorsCount = Integer.parseInt(scanner.nextLine());

        SystemSimulation systemSimulation = new SystemSimulation(elevatorsCount, floorsCount);
        systemSimulation.run();
    }
}
