package com.example.routes.repository;

import com.example.routes.dto.QueryDTO;
import com.example.routes.entity.RouteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    @Transactional(readOnly = true)
    Optional<RouteEntity> findRouteEntityById(Long routeId);
    @Transactional(readOnly = true)
    @Query("SELECT r FROM RouteEntity r")
    List<RouteEntity> findAllRoutesEntity();

    // Метод для нахождения route по его имени (name)
    @Transactional(readOnly = true)
    @Query("SELECT r FROM RouteEntity r WHERE r.name = :name")
    Optional<RouteEntity> findRouteEntityByName(@Param("name") String name);

    // Метод для удаления route по его идентификатору (id)
    @Modifying
    @Transactional
    @Query("DELETE FROM RouteEntity r WHERE r.id = :id")
    void deleteRouteEntityById(@Param("id") Long id);

    // Метод для удаления route по его идентификатору (id)
    @Modifying
    @Transactional
    @Query("DELETE FROM RouteEntity r WHERE r.distance = :distance")
    void deleteRoutesByDistance(@Param("distance") float distance);

    @Query("SELECT r FROM RouteEntity r ORDER BY r.id")
    List<RouteEntity> findByPageAndElementsCount(
            @Param("pageable") Pageable pageable
    );
}

