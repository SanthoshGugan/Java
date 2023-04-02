import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerProblem {

    public static void displayThread(final String message) {
        final var threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s\n", threadName, message);
    }

    private static class Drop {

        private String message;
        private boolean isEmpty = true;

        public synchronized void put(final String message) {
            while(!isEmpty) {
                try {
                    wait();
                } catch (final InterruptedException exception) {
                    displayThread("I havent put yet!");
                }
            }
            displayThread("Putting - "+message);
            this.message = message;
            this.isEmpty = false;
            notifyAll();
        }


        public synchronized String take() {
            while(isEmpty) {
                try {
                    wait();
                } catch (final InterruptedException exception) {
                    displayThread("I havent put yet!");
                }
            }
            displayThread("taking - "+message);
            isEmpty = true;
            notifyAll();
            return message;
        }
    }

    private static class Producer implements Runnable {
        private Drop drop;
        private final List<String> messages = List.of(
                "Hello",
            "How is your consumer space?",
            "do you like to switch places?",
            "it is too boring to be producer",
            "is consuming interesting?",
                "Done!"
        );

        public Producer(final Drop drop) {
            this.drop = drop;
        }

        public void run() {
            Random random = new Random();
            for(int i=0;i<messages.size();i++) {
                    long waitTime = random.nextLong(5000);
                    drop.put(messages.get(i));
                    try {
                        Thread.sleep(waitTime);
                    } catch (final InterruptedException exception) {
                        displayThread("Not completed with produce!");
                    }
            }
        }
    }

    private static class Consumer implements Runnable {
        private Drop drop;
        private List<String> messages = new ArrayList<>();

        public Consumer(final Drop drop) {
            this.drop = drop;
        }


        public void run() {
            final Random random = new Random();
            for(String message = drop.take(); !message.equals("Done!");message = drop.take()) {
                long waitTime = random.nextLong(5000);
                messages.add(message);
                try {
                    Thread.sleep(waitTime);
                } catch (final InterruptedException exception) {
                    displayThread("I am not done consuming!");
                }
            }
        }
    }

    public static void main(String args[]) {
        final Drop drop = new Drop();
        final Producer producer = new Producer(drop);
        final Consumer consumer = new Consumer(drop);
        (new Thread(producer)).start();
        (new Thread(consumer)).start();
    }

}
