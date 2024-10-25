public class Car extends Thread {
    private final String code;
    private final Bridge bridge;
    private final FIFO queue;

    public Car(String code, Bridge bridge, FIFO queue) {
        this.code = code;
        this.bridge = bridge;
        this.queue = queue;
        System.out.println(this.code + " from direction " + this.queue.getDirection());
    }

    public String getCode() {
        return code;
    }

    public FIFO getQueue() {
        return queue;
    }

    @Override
    public void run() {
        try {
            bridge.enterBridge(this);
            System.out.println(code + " is crossing the bridge.");
            Thread.sleep(1000); // Simulate cross time
            bridge.exitBridge(this);
            System.out.println(code + " has left the bridge.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}