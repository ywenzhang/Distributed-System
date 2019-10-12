import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SkierServlet extends javax.servlet.http.HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
    Integer obj = 1;
    //System.out.println(isValid);
    if (isValid.equals("error")) {
      obj = 0;
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_CREATED);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
    }
    String jsonString = new Gson().toJson(obj);
    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    out.print(jsonString);
    out.flush();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String urlPath = req.getPathInfo();

    // check we have a URL!
    if (urlPath == null || urlPath.isEmpty()) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }
    Integer obj = 1;
    String[] urlParts = urlPath.split("/");
    // and now validate url path and return the response status code
    // (and maybe also some value if input is valid)

    //System.out.println(urlPath);
    String isValid = isUrlValid(urlParts,"GET");
    //System.out.println(isValid);
    if (isValid.equals("error")) {
      obj = 0;
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      res.setStatus(HttpServletResponse.SC_CREATED);
      // do any sophisticated processing with urlParts which contains all the url params
      // TODO: process url params in `urlParts`
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

