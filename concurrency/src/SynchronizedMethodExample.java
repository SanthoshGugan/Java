public class SynchronizedMethodExample {
    private int counter = 0;

    public static void displayThread(final String message) {
        final var threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s\n", threadName, message);
    }
    public synchronized void incr() {
        counter++;
    }

    public synchronized void decr() {
        counter--;
    }

    public synchronized void display() {
        displayThread("Counter : %d".formatted(counter));
    }

    private static class UseCounterRunnable implements Runnable {
        private SynchronizedMethodExample synchronizedMethodExample;
        public UseCounterRunnable(final SynchronizedMethodExample synchronizedMethodExample) {
            this.synchronizedMethodExample = synchronizedMethodExample;
        }
        public void run() {
            for(int i=0;i<10;i++) {
                try {
                    synchronizedMethodExample.incr();
                    synchronizedMethodExample.display();
                } catch (final Exception ex) {
                    displayThread("I am not done yet!");
                }
            }
        }
    }
    public static void main(String args[]) {
        final SynchronizedMethodExample synchronizedMethodExample = new SynchronizedMethodExample();
        final Thread t1 = new Thread(new UseCounterRunnable(synchronizedMethodExample));
        final Thread t2 = new Thread(new UseCounterRunnable(synchronizedMethodExample));

        t2.start();
        try {
            t1.join();
            t1.start();
        } catch (Exception ex) {
            System.out.printf("exception %s", ex);
        }

    }
}
