package com.example.cloudsql.Servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ResortServlet")
public class ResortServlet extends HttpServlet {
  protected void doPOST(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    long start = System.currentTimeMillis();
    res.setContentType("text/plain");
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

    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
      res.getWriter().write("It works!");
    }
    long end = System.currentTimeMillis();
    long responseTime = end - start;
    ServletContext sc = this.getServletContext();
    String API = "POST /resorts/{resortID}/seasons";
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

  private boolean isUrlValid(String[] urlParts) {
    // TODO: validate the request url path according to the API spec
    if (urlParts.length == 1 && urlParts[0].equals("")) {
      return true;
    }
    if (urlParts.length == 3 && urlParts[0].equals("")
        && urlParts[2].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[1]);
        return true;
      } catch (NumberFormatException | NullPointerException nfe) {
        return false;
      }
    }
    else{
      return false;
    }
  }
}
