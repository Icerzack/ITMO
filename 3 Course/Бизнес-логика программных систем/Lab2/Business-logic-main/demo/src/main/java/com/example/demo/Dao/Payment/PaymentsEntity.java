package com.example.demo.Dao.Payment;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "payments")
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "card_num")
    private String cardNum;
    @Column(nullable = false, name = "card_date")
    private String cardDate;
    @Column(nullable = false, name = "card_cvv")
    private String cardCvv;

}
