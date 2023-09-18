package com.example.demo.Dao.Order;

import com.example.demo.Dao.Payment.PaymentsEntity;
import com.example.demo.Dto.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // Метод для поиска заказа по идентификатору пользователя (userId)
    List<OrderEntity> findByUserId(Long userId);

    // Метод для поиска заказа по идентификатору оплаты (paymentId)
    List<OrderEntity> findByPaymentId(Long paymentId);

    // Метод для поиска заказа по типу оплаты (paymentType)
    List<OrderEntity> findByPaymentType(PaymentType paymentType);

    // Метод для обновления адреса заказа по его идентификатору (id)
    @Modifying
    @Query("UPDATE OrderEntity o SET o.address = ?1 WHERE o.id = ?2")
    int updateAddressById(String address, Long id);

    // Метод для удаления заказа по его идентификатору (id)
    void deleteById(Long id);
}