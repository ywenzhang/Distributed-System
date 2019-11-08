import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

/**
 * Represent MultithreadClient with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class MultithreadClient {
  private int NUMTHREADS;
  private static final int NUMSKIERS = 20000;//20000
  private static final int NUMRUNS = 20;
  private int PHASE1;
  private int PHASE2;
  private int PHASE3;
  private final CountDownLatch startSignal1 = new CountDownLatch(1);
  private CountDownLatch startSignal2;
  private CountDownLatch startSignal3;
  private CountDownLatch endSignal;
  private ConcurrentLinkedDeque queue = new ConcurrentLinkedDeque();

  public MultithreadClient(int number_of_threads) {
    this.NUMTHREADS = number_of_threads;
    this.PHASE1 = NUMTHREADS/4;
    this.PHASE2 = NUMTHREADS;
    this.PHASE3 = NUMTHREADS/4;
    this.startSignal2 = new CountDownLatch(PHASE1/10);
    this.startSignal3 = new CountDownLatch(PHASE2/10);
    this.endSignal =  new CountDownLatch(NUMTHREADS*3/2);
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
            rg.sendPOSTandGETRequests();
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

  public int getNUMTHREADS() {
    return NUMTHREADS;
  }
}
