import java.util.Arrays;

public class SystemSimulation {

    private final ElevatorSystem elevatorSystem;
    private final int floorsCount;
    private final int elevatorsCount;

    public SystemSimulation(int elevatorsCount, int floorsCount) {
        elevatorSystem = new ElevatorSystem(elevatorsCount, floorsCount);
        this.floorsCount = floorsCount;
        this.elevatorsCount = elevatorsCount;
    }

    public void run() {
        System.out.println("""
                Help:
                    request - add a person that wants to use elevator, you will be asked to provide more info later
                    status - display status of all elevator (int tab[][] where tab[0] = {position, destination} of elevator with id 0)
                    step - step of a simulation
                    update - update position and destination of an elevator, you will be asked to provide more info later
                    exit - exit simulation
                """);

        String instruction;
        int floor, destination, id;

        loop:
        while (true) {
            System.out.println("Provide next instruction");

            instruction = Main.scanner.nextLine();

            switch (instruction) {
                case "status" -> System.out.println(Arrays.deepToString(elevatorSystem.getStatusesOfElevators()));
                case "step" -> elevatorSystem.doIterationStep();
                case "update", "request" -> {
                    System.out.println("Provide floor number");
                    floor = Integer.parseInt(Main.scanner.nextLine());

                    System.out.println("Provide destination number");
                    destination = Integer.parseInt(Main.scanner.nextLine());

                    if (floor >= floorsCount || floor < 0 || destination < 0 || destination >= floorsCount) {
                        System.out.println("At least one of provided floors is invalid");
                        break;
                    }

                    if (instruction.equals("request")) {
                        if (floor == destination) {
                            System.out.println("You cannot travel to the same floor you are on");
                            break;
                        }
                        elevatorSystem.requestElevator(new Request(floor, destination));
                    } else {
                        System.out.println("Provide elevator id");
                        id = Integer.parseInt(Main.scanner.nextLine());

                        if (id >= elevatorsCount || id < 0) {
                            System.out.println("Incorrect elevator id");
                            break;
                        }
                        elevatorSystem.updateElevatorState(id, floor, destination);
                    }
                }
                case "exit" -> {
                    break loop;
                }
            }
        }
    }
}
