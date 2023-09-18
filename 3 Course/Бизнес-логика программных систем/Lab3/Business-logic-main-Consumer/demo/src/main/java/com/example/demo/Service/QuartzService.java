package com.example.demo.Service;

import com.example.demo.Dao.Payment.PaymentsEntity;
import com.example.demo.Dao.Payment.PaymentsRepository;
import com.example.demo.Dao.User.UserEntity;
import com.example.demo.Dao.User.UserRepository;
import com.example.demo.Dao.UserPayments.UserPaymentEntity;
import com.example.demo.Dao.UserPayments.UserPaymentRepository;
import com.example.demo.Dto.Responses.CheckPaymentResponse;
import com.example.demo.Dto.Responses.PerformPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuartzService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    public void deleteUnusedAccounts() {

        List<UserEntity> listOfUsers = userRepository.findAll();
        List<UserEntity> usersToDelete = new ArrayList<>();

        for (UserEntity user : listOfUsers) {
            Timestamp last = user.getLast_update();
            String lastString = last.toString().split(" ")[0];

            LocalDate specifiedDate = LocalDate.parse(lastString);
            LocalDate today = LocalDate.now();

            long daysDifference = ChronoUnit.DAYS.between(specifiedDate, today);

            if (daysDifference > 365) {
                usersToDelete.add(user);
            }
        }

        userRepository.deleteAll(usersToDelete);

    }

    public void deleteNonValidCards() {

        List<UserEntity> listOfUsers = userRepository.findAll();
        List<PaymentsEntity> cardsToDelete = new ArrayList<>();

        for (UserEntity user : listOfUsers) {
            Optional<PaymentsEntity> optionalPaymentEntity = userPaymentRepository.findPaymentByUserId(user.getId());
            if(optionalPaymentEntity.isPresent()){
                String date = optionalPaymentEntity.get().getCardDate();
                LocalDate currentDate = LocalDate.now();

                YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yy"));
                LocalDate withDay = yearMonth.atDay(1);

                if (withDay.isBefore(currentDate)) {
                    cardsToDelete.add(optionalPaymentEntity.get());
                }

            }
        }

        paymentsRepository.deleteAll(cardsToDelete);
    }
}
