import java.io.IOException;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {
    int number_of_thread = Integer.valueOf(args[0]);
    MultithreadClient multiThread = new MultithreadClient(number_of_thread);
    Long wallTime = multiThread.run();
    Processing.processData(multiThread.getQueue(),wallTime, multiThread.getNUMTHREADS());
  }
}