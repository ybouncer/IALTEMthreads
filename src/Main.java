import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of cars for the test: ");
        int carsNumber = scanner.nextInt();

        System.out.print("Enter bridge capacity: ");
        int bridgeCapacity = scanner.nextInt();

        scanner.close();

        System.out.print("The directions of the cars will be configured randomly\n\n");

        Direction[] directions = Direction.values();
        Bridge bridge = new Bridge(bridgeCapacity);

        FIFO leftQueue = new FIFO(Direction.LEFT);
        FIFO rightQueue = new FIFO(Direction.RIGHT);

        System.out.println("CARS IN COMPETITION\n===================");
        Car[] cars = new Car[carsNumber];
        for (int i = 0; i < cars.length; i++) {
            Direction randomDirection = directions[(int) (Math.random() * directions.length)];
            int priority = i; // Priority based on the order of creation
            if (randomDirection == Direction.LEFT) {
                cars[i] = new Car("Car" + (i + 1), bridge, leftQueue);
            } else {
                cars[i] = new Car("Car" + (i + 1), bridge, rightQueue);
            }
        }
        for (Car car : cars) {
            car.start();
        }

        for (Car car : cars) {
            car.join();
        }

        System.out.println("\n===========================\n");
    }
}