import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RateLimitedQueue {

    private int size;
    private int currentSize = 0;
    private Queue<String> queue;

    public static void displayThread(final String message) {
        final var threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s\n", threadName, message);
    }


    public RateLimitedQueue(final int size, final Queue<String> queue) {
        this.size = size;
        this.queue = queue;
    }

    public synchronized void add(final String message) {
        try {
            while(currentSize == size) {
                displayThread("rate limited: "+message);
                wait();
            }
            queue.add(message);
            currentSize++;
            displayThread("Added to queue : "+message);
        } catch (final InterruptedException exception) {
            displayThread("I am not done adding!");
        }
    }

    public synchronized void remove() {
        if (currentSize == 0) return;
        currentSize--;
        displayThread("removing from queue : " + queue.poll());
        notifyAll();
    }

    public synchronized int getCurrentSize() {
        return currentSize;
    }

    public static void main(String args[]) {
        final Queue<String> queue = new PriorityQueue<>();
        final RateLimitedQueue rateLimitedQueue = new RateLimitedQueue(10, queue);
        (new Thread(() -> {
            try{
                for (int i = 0; i < 25; i++) {
                    rateLimitedQueue.add("" + i);
                    Thread.sleep(100);
                }
            } catch (final InterruptedException exception) {
                displayThread("I am not done with addition queue");
            }
        })).start();

        (new Thread(() -> {
            try {
                displayThread(rateLimitedQueue.getCurrentSize()+ "size");
                while (rateLimitedQueue.getCurrentSize() > 0) {
                    Thread.sleep(600);
                    rateLimitedQueue.remove();
                }
            } catch (final InterruptedException exception) {
                displayThread("I am not done with removing queue");
            }
        })).start();
    }
}
