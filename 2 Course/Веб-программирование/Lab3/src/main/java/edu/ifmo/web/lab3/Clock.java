package edu.ifmo.web.lab3;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@ViewScoped
public class Clock implements Serializable {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss");

    public String getTime() {
        return dtf.format(LocalDateTime.now());
    }
}
