package Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Represent StatisticsServlet with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class StatisticsServlet extends javax.servlet.http.HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    String urlPath = req.getPathInfo();
    ServletContext sc = this.getServletContext();
    if (urlPath == null || urlPath.isEmpty()) {
      JsonObject jsonObject = new JsonObject();
      String API = "POST /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
      if (sc.getAttribute(API) == null) {
        jsonObject.addProperty(API, "no requests.");
      } else {
        jsonObject.addProperty(API, new Gson().toJson(sc.getAttribute(API)));
      }
      API = "GET /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
      if (sc.getAttribute(API) == null) {
        jsonObject.addProperty(API, "no requests.");
      } else {
        jsonObject.addProperty(API, new Gson().toJson(sc.getAttribute(API)));
      }
      API = "POST /resorts/{resortID}/seasons";
      if (sc.getAttribute(API) == null) {
        jsonObject.addProperty(API, "no requests.");
      } else {
        jsonObject.addProperty(API, new Gson().toJson(sc.getAttribute(API)));
      }

      String jsonString = new Gson().toJson(jsonObject);
      PrintWriter out = res.getWriter();
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      out.print(jsonString);
      out.flush();
    }else{
      String[] urlParts = urlPath.split("/");
      System.out.println(urlParts[0]);
      if(urlParts.length>1 && urlParts[1].equals("clear")){
        sc.setAttribute("POST /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}",null);
        sc.setAttribute("GET /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}",null);
        sc.setAttribute("POST /resorts/{resortID}/seasons",null);
      }
    }
  }
}
