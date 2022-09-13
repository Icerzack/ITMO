package edu.ifmo.web.lab3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PointEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;

    private double x;
    private double y;
    private double r;
    private boolean doesHit;

    public PointEntity(double x, double y, double r, boolean doesHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.doesHit = doesHit;
    }

    public PointEntity() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isDoesHit() {
        return doesHit;
    }

    public void setDoesHit(boolean doesHit) {
        this.doesHit = doesHit;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
