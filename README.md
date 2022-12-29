# ElevatorSimulator

## Algorithm description

The algorithm used by me is a greedy algorithm and is based on the following idea: when a button is pressed, find the elevator that is going in the same way that the request is (or is in an idle state) and is the closest to the floor the request was made from. Request movement from that elevator. Request movements from elevators periodically until a request is picked up to be sure not to ignore any request.

When it comes to the behaviour of a single elevator:
1. At first, every elevator is in idle state.
2. If possible, pick up every request on the way that has the same direction as the elevator.
3. Until there are floors that it needs to visit that are in current direction keep going in that direction.
4. If there are no more floors to visit in its current direction, and it needs to change the direction to finish or pick up requests - change the direction.

## User manual
Program was tested using JDK 16. Make sure your JDK is compatible with the project.

To run the program execute the following command:
  ```
  ./gradlew run
  ```
and follow instructions that appear on the standard output.

To avoid gradle command prompt use:
  ```
  ./gradlew -q --console plain run 
  ```
