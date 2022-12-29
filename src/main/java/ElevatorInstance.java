import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class ElevatorInstance {
    private final int id;
    private final Multimap<Integer, Request> waitingRequests;
    private final Multimap<Integer, Request> pickedUpRequests = MultimapBuilder.hashKeys().arrayListValues().build();
    private int floor = 0;
    private int destination = 0;
    private Direction direction = Direction.IDLE;


    public ElevatorInstance(int id, Multimap<Integer, Request> waitingRequests) {
        this.id = id;
        this.waitingRequests = waitingRequests;
    }

    public void doIterationStep() {
        pickUpRequests();

        switch (direction) {
            case UP -> floor++;
            case DOWN -> floor--;
        }

        pickUpRequests(); // two calls of pickUpRequests needed - requests could be added while waiting for the next step
        finishRequests();

        if (destination == floor) {
            direction = Direction.IDLE;
        }
    }

    public void pickUpRequests() {
        var requestsToPickUp = filterRequestsElevatorShouldPickUp();

        removeFromWaitingAddToPickedUp(requestsToPickUp);

        updateDestination(requestsToPickUp);

        requestsToPickUp.forEach(request -> System.out.println("Elevator " + id + " PICKED UP " + request));
    }

    private List<Request> filterRequestsElevatorShouldPickUp() {
        var requestsOnCurrentFloor = waitingRequests.get(floor);
        return requestsOnCurrentFloor.stream()
                .filter(request -> request.getDirection() == direction)
                .collect(Collectors.toList());
    }

    private void removeFromWaitingAddToPickedUp(List<Request> requestsToPickUp) {
        requestsToPickUp.forEach(request -> {
            waitingRequests.remove(floor, request);
            pickedUpRequests.put(request.getDestination(), request);
        });
    }

    private void updateDestination(List<Request> requestsToPickUp) {
        switch (direction) {
            case UP -> requestsToPickUp.forEach(request -> destination =
                    Math.max(request.getDestination(), destination));
            case DOWN -> requestsToPickUp.forEach(request -> destination =
                    Math.min(request.getDestination(), destination));
        }
    }

    public void finishRequests() {
        var requestsFinished = pickedUpRequests.removeAll(floor);

        requestsFinished.forEach(request -> System.out.println("Elevator " + id + " FINISHED " + request));
    }

    public int getDistanceFromFloor(int floorOfRequest) {
        return Math.abs(floor - floorOfRequest);
    }

    private boolean floorReachedByMovingToCurrentDestination(int floorOfRequest) {
        if (floorOfRequest == floor)
            return true;

        if (floorOfRequest - floor > 0)
            return direction == Direction.UP;

        return direction == Direction.DOWN;
    }

    public boolean canAddRequest(Request request) {
        Direction directionOfRequest = request.getDirection();
        int floorOfRequest = request.getFloor();

        return (directionOfRequest == direction && floorReachedByMovingToCurrentDestination(floorOfRequest)) ||
                direction == Direction.IDLE || floorOfRequest == destination;
    }

    public void addRequestByUpdatingDestination(Request request) {
        switch (direction) {
            case IDLE -> assignNewDirectionAndDestination(request);

            // if it is already moving: make sure it travels far enough to pick up the request
            case UP -> destination = Math.max(request.getFloor(), destination);
            case DOWN -> destination = Math.min(request.getFloor(), destination);
        }
    }

    private void assignNewDirectionAndDestination(Request request) {
        if (floor == request.getFloor()) {
            direction = request.getDirection();
            destination = request.getDestination();
        } else {
            // travel to pick up the request
            direction = (request.getFloor() > floor) ? Direction.UP : Direction.DOWN;
            destination = request.getFloor();
        }
    }

    public void updateState(int newFloor, int newDestination) {
        floor = newFloor;
        destination = newDestination;

        direction = (destination > floor) ? Direction.UP : Direction.DOWN;
        if (destination == floor) {
            direction = Direction.IDLE;
        }
    }

    public int[] getStatus() {
        return new int[]{floor, destination};
    }
}
