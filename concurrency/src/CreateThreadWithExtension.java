public class CreateThreadWithExtension extends Thread{
    public void run() {
        System.out.println("This is a thread!");
    }

    public static void main(String args[]) {
        (new CreateThreadWithExtension()).run();
        (new CreateThreadWithExtension()).run();
    }
}
