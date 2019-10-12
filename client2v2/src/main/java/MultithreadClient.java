import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

/**
 * Represent MultithreadClient with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class MultithreadClient {
  private static final int NUMTHREADS = 256;
  private static final int NUMSKIERS = 20000;
  private static final int NUMRUNS = 20;
  private static final int PHASE1= NUMTHREADS/4;
  private static final int PHASE2 = NUMTHREADS;
  private static final int PHASE3 = NUMTHREADS/4;
  private final CountDownLatch startSignal1 = new CountDownLatch(1);
  private final CountDownLatch startSignal2 = new CountDownLatch(PHASE1/10);
  private final CountDownLatch startSignal3 = new CountDownLatch(PHASE2/10);
  private final CountDownLatch endSignal = new CountDownLatch(NUMTHREADS*3/2);
  private ConcurrentLinkedDeque queue = new ConcurrentLinkedDeque();

  public MultithreadClient() {
  }

  //public synchronized void inc() {
    //val++;
  //}

 // public int getVal() {
    //return val;
 // }

  public long run() throws InterruptedException {
    for (int i = 0; i < PHASE1; i++) {
      int finalI_Phase1 = i;
      Runnable thread =  () -> {
        try {
          // wait for the main thread to tell us to start
          this.startSignal1.await();
          RequestGenerator rg = new RequestGenerator((int)(finalI_Phase1*(double)NUMSKIERS/PHASE1+1),(int)((finalI_Phase1+1)*(double)NUMSKIERS/PHASE1),1,90);
          for(int j = 0 ; j < (int)((NUMRUNS*0.1)*(double)NUMSKIERS/PHASE1);j++){
            rg.sendRequest();
            //this.inc();
          }
          //this.inc();
          this.queue.addAll(rg.getResultList());

        } catch (InterruptedException e) {
        } finally {
          this.startSignal2.countDown();
          this.endSignal.countDown();
        }
      };
      new Thread(thread).start();
    }

    for (int i = 0; i < PHASE2; i++) {
      int finalI_Phase2 = i;
      Runnable thread =  () -> {
        try {
          // wait for the main thread to tell us to start
          this.startSignal2.await();
          RequestGenerator rg = new RequestGenerator((int)(finalI_Phase2*(double)NUMSKIERS/PHASE2+1),(int)((finalI_Phase2+1)*(double)NUMSKIERS/PHASE2),91,360);
          for(int j = 0 ; j < (int)((NUMRUNS*0.8)*(double)NUMSKIERS/PHASE2);j++){
            rg.sendRequest();
            //this.inc();
          }
          //this.inc();
          this.queue.addAll(rg.getResultList());
        } catch (InterruptedException e) {
        } finally {
          // we've finished - let the main thread know
          this.startSignal3.countDown();
          this.endSignal.countDown();
        }
      };
      new Thread(thread).start();

    }

    for (int i = 0; i < PHASE3; i++) {
      int finalI_Phase3 = i;
      Runnable thread =  () -> {
        try {
          // wait for the main thread to tell us to start
          this.startSignal3.await();
          RequestGenerator rg = new RequestGenerator((int)(finalI_Phase3*(double)NUMSKIERS/PHASE3+1),(int)((finalI_Phase3+1)*(double)NUMSKIERS/PHASE3),361,420);
          for(int j = 0 ; j < (int)((NUMRUNS*0.1)*(double)NUMSKIERS/PHASE3);j++){
            rg.sendRequest();
            //this.inc();
          }
          //this.inc();
          this.queue.addAll(rg.getResultList());
        } catch (InterruptedException e) {
        } finally {
          // we've finished - let the main thread know
          this.endSignal.countDown();
        }
      };
      new Thread(thread).start();

    }
    long startTime = System.currentTimeMillis();
    this.startSignal1.countDown();
    this.endSignal.await();
    long endTime = System.currentTimeMillis();
    return endTime-startTime;
  }

  public ConcurrentLinkedDeque getQueue() {
    return queue;
  }

  public static int getNUMTHREADS() {
    return NUMTHREADS;
  }
}
