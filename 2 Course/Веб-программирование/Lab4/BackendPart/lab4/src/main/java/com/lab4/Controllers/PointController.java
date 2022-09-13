package com.lab4.Controllers;

import com.lab4.DataClasses.RadiusData;
import com.lab4.Repos.PointRepository;
import com.lab4.DataClasses.PointData;
import com.lab4.Models.Point;
import com.lab4.Models.User;
import com.lab4.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/points")
public class PointController {
    private final UserService userService;

    private final PointRepository pointRepository;

    @Autowired
    public PointController(PointRepository pointRepository, UserService userService) {
        this.pointRepository = pointRepository;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping
    ResponseEntity<?> getUserPoints() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity e = ResponseEntity.ok(pointRepository.findByUser(user));
        return e;
    }

    @CrossOrigin
    @PostMapping("/update")
    ResponseEntity<?> updatePoints(@RequestBody RadiusData radiusData) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Point> points = pointRepository.findByUser(user);
        for (Point p : points) {
            p.setR(radiusData.getR());
            p.setResult(Point.checkHit(p.getX(), p.getY(), p.getR()));
            pointRepository.save(p);
        }
        return ResponseEntity.ok(points);
    }

    @CrossOrigin
    @PostMapping
    ResponseEntity<?> addPoint(@RequestBody PointData pointData) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(pointRepository.save(
            new Point(
                pointData.getX(),
                pointData.getY(),
                pointData.getR(),
                user
        )));
    }

    @CrossOrigin
    @DeleteMapping
    ResponseEntity<?> deleteUserPoints() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(pointRepository.deleteByUser(user));
    }
}