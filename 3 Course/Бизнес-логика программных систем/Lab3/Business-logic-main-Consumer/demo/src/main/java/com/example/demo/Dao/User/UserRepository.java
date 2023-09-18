package com.example.demo.Dao.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findById(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.phoneNumber = :phoneNumber")
    Optional<UserEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
