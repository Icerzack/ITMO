package com.example.demo.Server;

import com.example.demo.Dao.Payment.PaymentsEntity;
import com.example.demo.Dao.Payment.PaymentsRepository;
import com.example.demo.Dao.User.UserEntity;
import com.example.demo.Dao.User.UserRepository;
import com.example.demo.Dao.UserPayments.UserPaymentEntity;
import com.example.demo.Dao.UserPayments.UserPaymentRepository;
import com.example.demo.Dto.Responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.*;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserPaymentRepository userPaymentRepository;
    @Autowired
    private PaymentsRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JtaTransactionManager transactionManager;

    public ResponseEntity<AddPhoneResponse> addPhone(Long id, String phone) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if(!phone.matches("[-+]?\\d+")){
            addPhoneResponse.setResult(false);
            return ResponseEntity.ok(addPhoneResponse);
        }
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            optionalEntity.get().setPhoneNumber(phone);
            userRepository.save(optionalEntity.get());

            addPhoneResponse.setResult(true);
        } else {
            addPhoneResponse.setResult(false);
        }
        return ResponseEntity.ok(addPhoneResponse);
    }

    public ResponseEntity<CheckPhoneResponse> checkPhone(Long id) {
        CheckPhoneResponse checkPhoneResponse = new CheckPhoneResponse();
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            String phone = optionalEntity.get().getPhoneNumber();
            checkPhoneResponse.setResult(true);
            checkPhoneResponse.setPhone(phone);
        } else {
            checkPhoneResponse.setResult(false);
            checkPhoneResponse.setPhone("");
        }
        return ResponseEntity.ok(checkPhoneResponse);
    }

    public ResponseEntity<DeletePhoneResponse> deletePhone(Long id) {
        DeletePhoneResponse deletePhoneResponse = new DeletePhoneResponse();
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            optionalEntity.get().setPhoneNumber("");
            userRepository.save(optionalEntity.get());
            deletePhoneResponse.setResult(true);
        } else {
            deletePhoneResponse.setResult(false);
        }
        return ResponseEntity.ok(deletePhoneResponse);
    }

    public ResponseEntity<AddPaymentResponse> addPayment(Long userId, String cardNum, String cardDate, String cardCvv) {
        AddPaymentResponse addPaymentResponse = new AddPaymentResponse();
        if(!cardNum.matches("[-+]?\\d+") || cardNum.length() < 13 || cardNum.length() > 19 || !cardCvv.matches("[-+]?\\d+") || cardCvv.length() != 3){
            addPaymentResponse.setResult(false);
            return ResponseEntity.ok(addPaymentResponse);
        }
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<PaymentsEntity> paymentEntity = paymentRepository.findByCardNum(cardNum);
        Optional<UserPaymentEntity> userPaymentEntity = userPaymentRepository.findByUserId(userId);

        UserTransaction userTransaction = transactionManager.getUserTransaction();

        try {
            userTransaction.begin();
        } catch (NotSupportedException | SystemException e) {
            throw new RuntimeException(e);
        }
        try {

            PaymentsEntity newPaymentEntity = new PaymentsEntity();
            if(!paymentEntity.isPresent()){
                newPaymentEntity.setCardNum(cardNum);
                newPaymentEntity.setCardDate(cardDate);
                newPaymentEntity.setCardCvv(cardCvv);
                paymentRepository.save(newPaymentEntity);
            } else {
                newPaymentEntity = paymentEntity.get();
            }

            if (userPaymentEntity.isPresent()){
                userPaymentEntity.get().setPayment(newPaymentEntity);
                userPaymentRepository.save(userPaymentEntity.get());
                addPaymentResponse.setResult(true);
                return ResponseEntity.ok(addPaymentResponse);
            }

            if (userEntity.isPresent()) {
                UserPaymentEntity newUserPaymentEntity = new UserPaymentEntity();
                newUserPaymentEntity.setUser(userEntity.get());
                newUserPaymentEntity.setPayment(newPaymentEntity);
                userPaymentRepository.save(newUserPaymentEntity);
                addPaymentResponse.setResult(true);
            } else {
                addPaymentResponse.setResult(false);
            }

            userTransaction.commit();
        } catch (Exception e) {
            // Откатить транзакцию при ошибке
            try {
                    userTransaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            try {
                throw e;
            } catch (RollbackException | SystemException | HeuristicRollbackException | HeuristicMixedException ex) {
                throw new RuntimeException(ex);
            }
        }

        return ResponseEntity.ok(addPaymentResponse);
    }

    public ResponseEntity<CheckPaymentResponse> checkPayment(Long id) {
        CheckPaymentResponse checkPaymentResponse = new CheckPaymentResponse();
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Optional<PaymentsEntity> optionalPaymentEntity = userPaymentRepository.findPaymentByUserId(id);
            if(optionalPaymentEntity.isPresent()){
                checkPaymentResponse.setCardNumber(optionalPaymentEntity.get().getCardNum());
                checkPaymentResponse.setCardDate(optionalPaymentEntity.get().getCardDate());
                checkPaymentResponse.setCardCvv(optionalPaymentEntity.get().getCardCvv());
                checkPaymentResponse.setResult(true);

                return ResponseEntity.ok(checkPaymentResponse);
            }
        }
        checkPaymentResponse.setCardNumber("");
        checkPaymentResponse.setCardDate("");
        checkPaymentResponse.setCardCvv("");
        checkPaymentResponse.setResult(false);

        return ResponseEntity.ok(checkPaymentResponse);
    }

    public ResponseEntity<DeletePaymentResponse> deletePayment(Long id) {
        DeletePaymentResponse deletePaymentResponse = new DeletePaymentResponse();
        Optional<UserPaymentEntity> userPaymentOptional = userPaymentRepository.findById(id);

        if (userPaymentOptional.isPresent()) {
            UserPaymentEntity userPayment = userPaymentOptional.get();
            userPaymentRepository.delete(userPayment);
            deletePaymentResponse.setResult(true);
        } else {
            deletePaymentResponse.setResult(false);
        }
        return ResponseEntity.ok(deletePaymentResponse);

    }
}
