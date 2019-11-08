package Servlet;

import Dao.LiftRideDao;
import Dao.VerticalDayDao;
import Model.LiftRideModel;
import Model.VerticalDayModel;
import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SkierServlet extends javax.servlet.http.HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    long start = System.currentTimeMillis();
    String urlPath = req.getPathInfo();
    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }

    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    //System.out.println(urlPath);
    String isValid = isUrlValid(urlParts,"POST");
    String obj = "The lift ride info is wrong. ";
    //System.out.println(isValid);
    if (isValid.equals("error")) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      // do any sophisticated processing with urlParts which contains all the url params
      int skierId = Integer.parseInt(urlParts[7]);
      int resortId = Integer.parseInt(urlParts[1]);
      int seasonId = Integer.parseInt(urlParts[3]);
      int dayId = Integer.parseInt(urlParts[5]);
      BufferedReader reader = req.getReader();
      String LiftRideInfo = reader.readLine();
      String[] timeAndLiftId = LiftRideInfo.split("[:,]");
      if (timeAndLiftId.length != 4 || !timeAndLiftId[0].equals("{\"time\"") || !timeAndLiftId[2].equals("\"liftID\"")){
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      }else {
        int time = Integer.parseInt(timeAndLiftId[1]);
        //System.out.println(time);
        int liftId = Integer.parseInt(timeAndLiftId[3].substring(0, timeAndLiftId[3].length() - 1));
        //System.out.println(liftId);
        String PK = skierId + "/"  +seasonId + "/" + dayId + "/" + time;
        //System.out.println(PK);
        LiftRideDao liftRideDao = new LiftRideDao();
        liftRideDao.createLiftRide(new LiftRideModel(skierId, resortId, seasonId, dayId, time, liftId, PK));
        res.setStatus(HttpServletResponse.SC_CREATED);
        obj = "Successfully wrote the lift ride. ";
        VerticalDayDao verticalDayDao = new VerticalDayDao();
        verticalDayDao.createOrUpdateVerticalDay(new VerticalDayModel(skierId, resortId, seasonId, dayId,liftId*10));
      }
    }
    String jsonString = new Gson().toJson(obj);
    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print(jsonString);
    out.flush();
    long end = System.currentTimeMillis();
    long responseTime = end - start;
    ServletContext sc = this.getServletContext();
    String API = "POST /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
    if (sc.getAttribute(API) == null){
      HashMap<String,Long> hashMap = new HashMap<>();
      hashMap.put("count",Long.valueOf(1));
      hashMap.put("max",responseTime);
      hashMap.put("average",responseTime);
      hashMap.put("method",Long.valueOf(1));
      sc.setAttribute(API,hashMap);
    }else{
      HashMap<String,Long> hashMap = (HashMap<String, Long>) sc.getAttribute(API);
      long count = hashMap.get("count");
      long averageToUpdate = hashMap.get("average");
      long maxToUpdate = hashMap.get("max");
      if(maxToUpdate<responseTime){
        hashMap.put("max",responseTime);
      }
      hashMap.put("average", (averageToUpdate*count+responseTime)/(count+1));
      hashMap.put("count", count+1);
    }
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    long start = System.currentTimeMillis();
    String urlPath = req.getPathInfo();
    //System.out.println(urlPath);
    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }
    String obj = "Success";
    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    //System.out.println(urlPath);
    String isValid = isUrlValid(urlParts,"GET");
    //System.out.println(isValid);
    if (isValid.equals("error")) {
      obj = "Url is invalid";
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_CREATED);
      // do any sophisticated processing with urlParts which contains all the url params
      if(urlParts.length == 8){
        int skierId = Integer.parseInt(urlParts[7]);
        int resortId = Integer.parseInt(urlParts[1]);
        int seasonId = Integer.parseInt(urlParts[3]);
        int dayId = Integer.parseInt(urlParts[5]);
        //System.out.println(2);
        VerticalDayDao verticalDayDao = new VerticalDayDao();
        //System.out.println(2);
        String key = skierId+"/"+resortId+"/"+seasonId+"/"+dayId;
        //System.out.println(key);
        int result = verticalDayDao.getVerticalDay(key);
        if (result == -1){
          obj = "No vertical on that day.";
        }else{
          obj = String.valueOf(result);
        }
        long end = System.currentTimeMillis();
        long responseTime = end - start;
        ServletContext sc = this.getServletContext();
        String API = "GET /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
        if (sc.getAttribute(API) == null){
          HashMap<String,Long> hashMap = new HashMap<>();
          hashMap.put("count",Long.valueOf(1));
          hashMap.put("max",responseTime);
          hashMap.put("average",responseTime);
          hashMap.put("method",Long.valueOf(1));
          sc.setAttribute(API,hashMap);
        }else{
          HashMap<String,Long> hashMap = (HashMap<String, Long>) sc.getAttribute(API);
          long count = hashMap.get("count");
          long averageToUpdate = hashMap.get("average");
          long maxToUpdate = hashMap.get("max");
          if(maxToUpdate<responseTime){
            hashMap.put("max",responseTime);
          }
          hashMap.put("average", (averageToUpdate*count+responseTime)/(count+1));
          hashMap.put("count", count+1);
        }
      }
    }
    String jsonString = new Gson().toJson(obj);
    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print(jsonString);
    out.flush();
  }
  private String isUrlValid(String[] urlParts,String method) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlParts.length == 8 && urlParts[0].equals("")
        && urlParts[2].equals("seasons")
        && urlParts[4].equals("days")
        && urlParts[6].equals("skiers")) {
      //System.out.println("hi");
      try {
        Integer.parseInt(urlParts[1]);
        Integer.parseInt(urlParts[3]);
        Integer.parseInt(urlParts[5]);
        Integer.parseInt(urlParts[7]);
        return "skier";
      } catch (NumberFormatException | NullPointerException nfe) {
        return "error";
      }
    }
    if (urlParts.length == 3 && urlParts[0].equals("")
        && urlParts[2].equals("vertical")
        && method.equals("GET")) {
      try {
        Integer.parseInt(urlParts[1]);
        return "vertical";
      } catch (NumberFormatException | NullPointerException nfe) {
        return "error";
      }
    } else {
      return "error";
    }
  }
}

