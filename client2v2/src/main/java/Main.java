import java.io.IOException;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {
    MultithreadClient multiThread = new MultithreadClient();
    Long wallTime = multiThread.run();
    Processing.processData(multiThread.getQueue(),wallTime, multiThread.getNUMTHREADS());
  }
}