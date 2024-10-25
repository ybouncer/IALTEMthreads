import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Bridge {
    private final int capacity;
    private final Semaphore semaphore;
    private final Object lock = new Object();
    private int carsOnBridge = 0;
    private Direction currentDirection = null;
    private final Queue<Car> queue = new LinkedList<>();

    public Bridge(int capacity) {
        this.capacity = capacity;
        this.semaphore = new Semaphore(capacity);
    }

    public void enterBridge(Car car) throws InterruptedException {
        synchronized (lock) {
            queue.add(car);
            while (currentDirection != null && currentDirection != car.getQueue().getDirection() || queue.peek() != car) {
                lock.wait();
            }
            queue.poll();
            if (currentDirection == null) {
                currentDirection = car.getQueue().getDirection();
            }
            carsOnBridge++;
        }
        semaphore.acquire();
    }

    public void exitBridge(Car car) {
        semaphore.release();
        synchronized (lock) {
            carsOnBridge--;
            if (carsOnBridge == 0) {
                currentDirection = null;
                lock.notifyAll();
            }
        }
    }
}