package com.lab4.Repos;

import com.lab4.Models.Point;
import com.lab4.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
