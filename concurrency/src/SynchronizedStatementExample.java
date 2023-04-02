public class SynchronizedStatementExample {
    private long c1 = 0;
    private long c2 = 0;

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void incrC1() {
        synchronized (lock1) {
            c1++;
        }
        displayThread("c1 :"+c1);
    }

    public void incrC2() {
        synchronized (lock2) {
            c2++;
        }
        displayThread("c2 :"+c2);
    }

    public static void displayThread(final String message) {
        final var threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s\n", threadName, message);
    }

    private static class CounterRunnable implements Runnable {
        private SynchronizedStatementExample synchronizedStatementExample;
        private boolean isC1;
        public CounterRunnable(final SynchronizedStatementExample synchronizedStatementExample, final boolean isC1) {
            this.synchronizedStatementExample = synchronizedStatementExample;
            this.isC1 = isC1;
        }

        private void incrC1() {
            this.synchronizedStatementExample.incrC1();
        }

        private void incrC2() {
            this.synchronizedStatementExample.incrC2();
        }

        public void run() {
            if(isC1) incrC1();
            else incrC2();
        }
    }

    public static void main(String args[]) {
        final SynchronizedStatementExample synchronizedStatementExample = new SynchronizedStatementExample();
        final Thread t1 = new Thread(new CounterRunnable(synchronizedStatementExample, true));
        final Thread t2 = new Thread(new CounterRunnable(synchronizedStatementExample, false));

        t1.start();
        t2.start();
    }
}
