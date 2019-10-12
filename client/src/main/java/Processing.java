import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Represent Processing with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class Processing {
  public static void processData(ConcurrentLinkedDeque resultQueue, long wallTime, int numberOfThreads){
    long successful = 0;
    long unsuccessful = 0;
    for (Iterator it = resultQueue.iterator(); it.hasNext(); ) {
      Result result = (Result) it.next();
      if (result.getResponseCode() == 201){
        successful += 1;
      }else{
        unsuccessful += 1;
      }
    }
    System.out.println("Total Number of Threads:" + numberOfThreads);
    System.out.println("Number of Successful Posts: " + successful);
    System.out.println("Number of Unsccessful Posts: " + unsuccessful);
    System.out.println("Run Time:" + wallTime);
  }
}
