package com.lab4.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(generator="some_seq_gen_name1")
    @SequenceGenerator(name="some_seq_gen_name1", sequenceName="SOME_SEQ_POINT", allocationSize=1)
    private long id;

    private double x;
    private double y;
    private double r;
    private String time;
    private boolean result;
    private String owner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Point(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        this.result = Point.checkHit(x, y , r);
        this.owner = user.getUsername();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        time = format.format(date);
    }

    @Override
    public String toString(){
        return "point{" + "x = " + x + ", y = " + y + ", r = " + r + "}";
    }

    public static boolean checkHit(double x, double y, double r){
        if(x >= 0 && y >= 0){ // 1 четверть
            return x*x + y*y <= r*r;
        }
        else if(x < 0 && y >= 0){ // 2 четверть
            return false;
        }
        else if(x < 0 && y < 0){ // 3 четверть
            return -x <= r && -y <= r/2;
        }
        else{ // 4 четверть
            return x-y<=r;
        }
    }
}
