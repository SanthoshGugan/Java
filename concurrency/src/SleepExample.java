public class SleepExample{

    public static void main(String arg[]) throws InterruptedException{
        final String[] messages = {
                "Example 1",
                "Example 2",
                "Example 3",
                "Example 4"
        };

        for(String message: messages) {
            Thread.sleep(4000);
            System.out.println(message);
        }
    }
}
