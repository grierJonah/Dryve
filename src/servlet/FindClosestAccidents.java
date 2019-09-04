package servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.AccidentsDao;
import model.Accidents;
import model.Users;

@WebServlet("/findClosestAccidents")
public class FindClosestAccidents extends HttpServlet {

  protected AccidentsDao accidentsDao;

  @Override
  public void init() throws ServletException {
    this.accidentsDao = AccidentsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    System.out.println("doGet of find closest accidents was called");
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<Accidents> accidents = new ArrayList<>();


    // Retrieve and validate position
    // position is retrieved from the URL query string.
    String x = req.getParameter("x");
    String y = req.getParameter("y");
    System.out.println("looking at position X: " + x + " and Y: " + y);

    if (x == null || x.trim().isEmpty() || y == null || y.trim().isEmpty()) {
      messages.put("success", "Please enter a valid position.");
      System.out.println(req.getParameter("x") + " " + req.getParameter("y"));
    } else {
      // Retrieve BlogUsers, and store as a message.
      try {
        accidents = accidentsDao.getTenClosestAccidentsByLoc(Double.parseDouble(x), Double.parseDouble(y));
        System.out.println("accidents from the servlet: " + accidents.toString());
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + x + " " + y);
      // Save the previous search term, so it can be used as the default
      // in the input box when rendering FindUsers.jsp.
      messages.put("previousX", x);
      messages.put("previousY", y);
    }
    req.setAttribute("accidents", accidents);

    req.getRequestDispatcher("/FindAccidents.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    System.out.println("doPost of find closest accidents was called");
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<Accidents> accidents = new ArrayList<>();


    // Retrieve and validate position
    // position is retrieved from the URL query string.
    String x = req.getParameter("x");
    String y = req.getParameter("y");
    System.out.println("looking at position X: " + x + " and Y: " + y);

    if (x == null || x.trim().isEmpty() || y == null || y.trim().isEmpty()) {
      messages.put("success", "Please enter a valid position.");
      System.out.println(req.getParameter("x") + " " + req.getParameter("y"));
    } else {
      // Retrieve BlogUsers, and store as a message.
      try {
        accidents = accidentsDao.getTenClosestAccidentsByLoc(Double.parseDouble(x), Double.parseDouble(y));
        System.out.println("accidents: " + accidents.toString());
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + x + " " + y);
      // Save the previous search term, so it can be used as the default
      // in the input box when rendering FindUsers.jsp.
      messages.put("previousX", x);
      messages.put("previousY", y);
    }
    req.setAttribute("accidents", accidents);

    req.getRequestDispatcher("/FindAccidents.jsp").forward(req, resp);
  }
}
