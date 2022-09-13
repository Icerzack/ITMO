package com.maxalkuz.lab2;

import java.util.Date;

public class Point {

    private final double x, y, r;
    private final boolean coordsStatus;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        coordsStatus = checkCoordinates(x, y, r);
    }

    private boolean checkCoordinates(double x, double y, double r) {
        return  (((x >= 0) && (x <= r) && (y <= 0) && (y >= -r/2)) && (y >= 0.5*x - r/2) ||
                ((x >= 0) && (x <= r) && (y >= 0) && (y <= r/2)) ||
                ((Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r/2, 2))) && (x <= 0) && (y <= 0)));
    }


    public String toHtmlCode() {
        return "<tr>" +
                "<td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td style='color: " + ((coordsStatus) ? "green" : "red") + "'>" + coordsStatus + "</td>" +
                "<td>" + new Date() + "</td>" +
                "</tr>";
    }
}