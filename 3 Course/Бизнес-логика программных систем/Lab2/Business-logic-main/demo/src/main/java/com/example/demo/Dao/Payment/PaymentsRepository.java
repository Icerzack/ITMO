package com.example.demo.Dao.Payment;

import com.example.demo.Dao.UserPayments.UserPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {
    @Query("SELECT p FROM PaymentsEntity p WHERE p.cardNum = :cardNum")
    Optional<PaymentsEntity> findByCardNum(@Param("cardNum") String cardNum);

    @Query("SELECT p FROM PaymentsEntity p WHERE p.id = :id")
    Optional<PaymentsEntity> findByPaymentId(@Param("id") Long id);

}