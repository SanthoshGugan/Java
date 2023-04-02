public class CreateThreadWithRunnable implements Runnable{
    public void run(){
        System.out.println("This is a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new CreateThreadWithRunnable())).run();
        (new Thread(new CreateThreadWithRunnable())).run();
    }
}
