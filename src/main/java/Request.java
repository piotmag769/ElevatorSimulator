public class Request {

    private final int floor;
    private final int destination;
    private final Direction direction;

    public Request(int floor, int destination) {
        this.floor = floor;
        this.destination = destination;

        if (destination > floor)
            this.direction = Direction.UP;
        else
            this.direction = Direction.DOWN;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    public int getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Request{" +
                "floor=" + floor +
                ", destination=" + destination +
                ", direction=" + direction +
                '}';
    }
}
