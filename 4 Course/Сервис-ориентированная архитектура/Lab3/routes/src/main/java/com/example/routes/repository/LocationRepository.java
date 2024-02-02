package com.example.routes.repository;

import com.example.routes.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Transactional(readOnly = true)
    Optional<LocationEntity> findLocationEntityById(Long locationId);

    // Метод для нахождения локации по ее координатам (name)
    @Transactional(readOnly = true)
    @Query("SELECT l FROM LocationEntity l WHERE l.coordinates.x = :x and l.coordinates.y = :y")
    Optional<LocationEntity> findLocationEntityByCoordinates(@Param("x") int x, @Param("y") Float y);

    // Метод для обновления имени локации (name)
    @Modifying
    @Transactional
    @Query("UPDATE LocationEntity l SET l.name = :name WHERE l.id = :id")
    int updateNameLocationById(@Param("name") String name, @Param("id") Long id);

    // Метод для удаления локации по ее идентификатору (id)
    @Modifying
    @Transactional
    @Query("DELETE FROM LocationEntity l WHERE l.id = :id")
    void deleteLocationEntityById(@Param("id") Long id);
}
