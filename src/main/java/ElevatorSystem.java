import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ElevatorSystem {

    private final List<ElevatorInstance> elevators = new ArrayList<>();
    private final Multimap<Integer, Request> waitingRequests = LinkedListMultimap.create();
    private final int floorsCount;

    public ElevatorSystem(int elevatorsCount, int floorsCount) {
        this.floorsCount = floorsCount;
        IntStream.range(0, elevatorsCount).forEach(id -> elevators.add(new ElevatorInstance(id, waitingRequests)));
    }

    public void requestElevator(Request request) {
        waitingRequests.put(request.getFloor(), request);
    }

    public void assignRequests() {
        waitingRequests.forEach(($, request) -> assignRequestToElevator(request));
    }

    public void assignRequestToElevator(Request request) {
        ElevatorInstance bestChoice = null;
        int minimalDistance = floorsCount;
        int distance;
        for (ElevatorInstance elevator : elevators) {
            distance = elevator.getDistanceFromFloor(request.getFloor());

            if (distance < minimalDistance && elevator.canAddRequest(request)) {
                bestChoice = elevator;
                minimalDistance = distance;
            }
        }

        if (bestChoice != null) {
            bestChoice.addRequestByUpdatingDestination(request);
        }
    }

    public int[][] getStatusesOfElevators() {
        int[][] statuses = new int[elevators.size()][2];

        for (int i = 0; i < elevators.size(); i++)
            statuses[i] = elevators.get(i).getStatus();

        return statuses;
    }

    public void doIterationStep() {
        assignRequests();
        elevators.forEach(ElevatorInstance::doIterationStep);
    }

    public void updateElevatorState(int id, int newFloor, int newDestination) {
        elevators.get(id).updateState(newFloor, newDestination);
    }
}
