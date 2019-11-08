
import io.swagger.client.*;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represent RequestGenerator with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class RequestGenerator {
  private int skierIdStart;
  private int skierIdEnd;
  private int startTime;
  private int endTime;
  private ArrayList<Result> resultList;
  private SkiersApi api;
  private ApiClient client;

  public RequestGenerator(int skierIdStart, int skierIdEnd, int startTime, int endTime) {
    this.skierIdStart = skierIdStart;
    this.skierIdEnd = skierIdEnd;
    this.startTime = startTime;
    this.endTime = endTime;
    this.api = new SkiersApi();
    this.client = this.api.getApiClient();
    this.client.setBasePath("http://load-balancer-92a96ffe0ddc5a13.elb.us-east-1.amazonaws.com:8080/server_war/");
    this.resultList = new ArrayList<>();
  }

  public void sendRequest() {
    int time = ThreadLocalRandom.current().nextInt(this.startTime, this.endTime + 1);
    int skierId = ThreadLocalRandom.current().nextInt(this.skierIdStart, this.skierIdEnd + 1);
    int lift = ThreadLocalRandom.current().nextInt(5, 60 + 1);
    LiftRide liftRide = new LiftRide();
    liftRide.setLiftID(lift);
    liftRide.setTime(time);
    long currentTime = System.currentTimeMillis();
    try {
      ApiResponse<Void> response = api.writeNewLiftRideWithHttpInfo(liftRide, 1, "2016", "19", skierId);
      this.resultList.add(new Result(currentTime, "POST", System.currentTimeMillis() - currentTime, response.getStatusCode()));
    }catch(ApiException e){
      this.resultList.add(new Result(currentTime, "POST", System.currentTimeMillis() - currentTime, e.getCode()));
    }
  }

  public void sendPOSTandGETRequests() {
    int time = ThreadLocalRandom.current().nextInt(this.startTime, this.endTime + 1);
    int skierId = ThreadLocalRandom.current().nextInt(this.skierIdStart, this.skierIdEnd + 1);
    int lift = ThreadLocalRandom.current().nextInt(5, 60 + 1);
    LiftRide liftRide = new LiftRide();
    liftRide.setLiftID(lift);
    liftRide.setTime(time);
    long currentTime = System.currentTimeMillis();
    try {
      ApiResponse<Void> response = api.writeNewLiftRideWithHttpInfo(liftRide, 1, "2016", "19", skierId);
      this.resultList.add(new Result(currentTime, "POST", System.currentTimeMillis() - currentTime, response.getStatusCode()));
    }catch(ApiException e){
      this.resultList.add(new Result(currentTime, "POST", System.currentTimeMillis() - currentTime, e.getCode()));
    }
    ApiResponse<Integer>  result = null;
    currentTime = System.currentTimeMillis();
    try {
      result = api.getSkierDayVerticalWithHttpInfo(1,"2016","19",skierId);
      //System.out.println(result.getStatusCode());
      this.resultList.add(new Result(currentTime, "GET", System.currentTimeMillis() - currentTime, result.getStatusCode()));
    } catch (ApiException e) {
      this.resultList.add(new Result(currentTime, "GET", System.currentTimeMillis() - currentTime, e.getCode()));
    }
  }

  public ArrayList<Result> getResultList() {
    return resultList;
  }
}
