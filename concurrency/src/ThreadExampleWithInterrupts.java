public class ThreadExampleWithInterrupts {

    static void threadMessage(final String message) {
        final String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s \n", threadName, message);
    }

    private static class MessageLoop implements Runnable{
        public void run() {
            final String[] messages = {
                    "Example 1",
                    "Example 2",
                    "Example 3",
                    "Example 4",
                    "Example 5"
            };

            try {
                for(String message: messages) {
                    Thread.sleep(4000);
                    threadMessage(message);
                }

            } catch (final InterruptedException ex) {
                threadMessage("I am not done yet!");
            }
        }
    }

    public static void main(String args[])
        throws InterruptedException{

        long patience = 1000 * 10;
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]);
            } catch (final NumberFormatException ex) {
                System.err.println("Argument should be an long");
                System.exit(1);
            }
        }

        threadMessage("Starting messageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for thread messageloop to complete");
        while(t.isAlive()) {
            threadMessage("Still waiting");
            t.join(1000);
            System.out.format("%d || %d", patience, System.currentTimeMillis() - startTime);
            if (System.currentTimeMillis() - startTime > patience) {
                threadMessage("Tired of waiting");
                t.interrupt();
                t.join();
            }
        }
        threadMessage("Finally!");
    }
}
