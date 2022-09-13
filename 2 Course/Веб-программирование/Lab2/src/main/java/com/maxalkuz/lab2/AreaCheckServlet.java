package com.maxalkuz.lab2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ArrayList tableRows = (ArrayList) getServletContext().getAttribute("tableRows");
        if (tableRows == null) {
            tableRows = new ArrayList<>();
            getServletContext().setAttribute("tableRows", tableRows);
        }
        double x = Double.parseDouble(req.getParameter("x"));
        String y = req.getParameter("y");
        String r = req.getParameter("r");
        if(y.length() >= 6 && y.contains(".")){
            y = y.substring(0,6);
        }
        if(r.length() >= 6 && r.contains(".")){
            r = r.substring(0,6);
        }
        String key = req.getParameter("key");
        try (PrintWriter writer = resp.getWriter()) {
            if (checkData(x, Double.parseDouble(y), Double.parseDouble(r), key)) {
                tableRows.add(new Point(x, Double.parseDouble(y), Double.parseDouble(r)).toHtmlCode());
                writer.println(tableRows.get(tableRows.size() - 1));
            } else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean checkData(double x, double y, double r, String key) {
        Double[] xValues = {-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        if (key.equals("button")) {
            return (Arrays.asList(xValues).contains(x) && (y > -5 && y < 5) && (r > 2 && r < 5));
        }
        else if (key.equals("svg")){
            return (r > 2 && r < 5);
        }
        else return false;
    }
    public static double roundDown6(double d) {
        return Math.floor(d * 1e6) / 1e6;
    }
}