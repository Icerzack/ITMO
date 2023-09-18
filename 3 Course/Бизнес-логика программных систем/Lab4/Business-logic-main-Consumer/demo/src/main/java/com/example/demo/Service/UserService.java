package com.example.demo.Service;

import com.example.demo.Dao.Order.OrderEntity;
import com.example.demo.Dao.Order.OrderRepository;
import com.example.demo.Dao.Payment.PaymentsEntity;
import com.example.demo.Dao.Payment.PaymentsRepository;
import com.example.demo.Dao.User.UserEntity;
import com.example.demo.Dao.User.UserRepository;
import com.example.demo.Dao.UserPayments.UserPaymentEntity;
import com.example.demo.Dao.UserPayments.UserPaymentRepository;
import com.example.demo.Dto.PaymentType;
import com.example.demo.Dto.Requests.PerformPaymentRequest;
import com.example.demo.Dto.Responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.*;
import java.sql.Timestamp;
import java.util.Objects;
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
    private OrderRepository orderRepository;
    @Autowired
    private JtaTransactionManager transactionManager;

    public AddPhoneResponse addPhone(Long id, String phone) {
        AddPhoneResponse addPhoneResponse = new AddPhoneResponse();
        if(!phone.matches("[-+]?\\d+")){
            addPhoneResponse.setResult(false);
            return addPhoneResponse;
        }
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            optionalEntity.get().setPhoneNumber(phone);
            userRepository.save(optionalEntity.get());

            addPhoneResponse.setResult(true);
        } else {
            addPhoneResponse.setResult(false);
        }
        return addPhoneResponse;
    }

    public CheckPhoneResponse checkPhone(Long id) {
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
        return checkPhoneResponse;
    }

    public ResponseEntity<DeleteUserResponse> deleteUser(Long id) {
        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            userRepository.delete(optionalEntity.get());
            deleteUserResponse.setResult(true);
        } else {
            deleteUserResponse.setResult(false);
        }
        return ResponseEntity.ok(deleteUserResponse);
    }

    public boolean checkPassword(Long id, String password) {
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            String truePassword = optionalEntity.get().getPassword();
            return Objects.equals(truePassword, password);
        }
        return false;
    }

    public boolean isIdAdmin(Long id) {
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            String trueRole = optionalEntity.get().getRole();
            return Objects.equals(trueRole, "ADMIN");
        }
        return false;
    }

    public AddPaymentResponse addPayment(Long userId, String cardNum, String cardDate, String cardCVV) {
        AddPaymentResponse addPaymentResponse = new AddPaymentResponse();
        if(!cardNum.matches("[-+]?\\d+") || cardNum.length() < 13 || cardNum.length() > 19 || !cardCVV.matches("[-+]?\\d+") || cardCVV.length() != 3){
            addPaymentResponse.setResult(false);
            return addPaymentResponse;
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
                newPaymentEntity.setCardCVV(cardCVV);
                paymentRepository.save(newPaymentEntity);
            } else {
                newPaymentEntity = paymentEntity.get();
            }

            if (userPaymentEntity.isPresent()){
                userPaymentEntity.get().setPayment(newPaymentEntity);
                userPaymentRepository.save(userPaymentEntity.get());
                addPaymentResponse.setResult(true);
                return addPaymentResponse;
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

        return addPaymentResponse;
    }

    public CheckPaymentResponse checkPayment(Long id) {
        CheckPaymentResponse checkPaymentResponse = new CheckPaymentResponse();
        Optional<UserEntity> optionalEntity = userRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Optional<PaymentsEntity> optionalPaymentEntity = userPaymentRepository.findPaymentByUserId(id);
            if(optionalPaymentEntity.isPresent()){
                checkPaymentResponse.setCardNumber(optionalPaymentEntity.get().getCardNum());
                checkPaymentResponse.setCardDate(optionalPaymentEntity.get().getCardDate());
                checkPaymentResponse.setCardCVV(optionalPaymentEntity.get().getCardCVV());
                checkPaymentResponse.setResult(true);

                return checkPaymentResponse;
            }
        }
        checkPaymentResponse.setCardNumber("");
        checkPaymentResponse.setCardDate("");
        checkPaymentResponse.setCardCVV("");
        checkPaymentResponse.setResult(false);

        return checkPaymentResponse;
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

//    public boolean addOrder(PerformPaymentRequest performPaymentRequest) {
//        OrderEntity order = new OrderEntity();
//        Optional<UserEntity> userEntity = userRepository.findById(performPaymentRequest.getUserId());
//        order.setUser(userEntity.get());
//        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
//        order.setAddress(performPaymentRequest.getAddress());
//        Optional<PaymentsEntity> paymentsEntity = paymentRepository.findByCardNum(performPaymentRequest.getCardNum());
//        if (paymentsEntity.isPresent()){
//            order.setPaymentId(paymentsEntity.get().getId());
//            order.setPaymentType(PaymentType.card);
//        } else {
//            order.setPaymentType(PaymentType.cash);
//        }
//        order.setCost(performPaymentRequest.getCost());
//        orderRepository.save(order);
//        return true;
//    }

    @Component
    public class KafkaListeners {

        @KafkaListener(
                topics = "perform-test3",
                groupId = "consumer-perform-test3"
        )
        public boolean listenerOrders1(String data) {
            PerformPaymentRequest performPaymentRequest = new PerformPaymentRequest();
            try {
                performPaymentRequest = deserializeData(data);
                System.out.println("Listener1 received: " + performPaymentRequest + "!!!");
            } catch (Exception e) {
                System.out.println("Error deserializing PerformPaymentRequest: " + e.getMessage());
            }
            return addOrderToDB(performPaymentRequest);
        }

        @KafkaListener(
                topics = "perform-test3",
                groupId = "consumer-perform-test3"
        )
        public boolean listenerOrders2(String data) {
            PerformPaymentRequest performPaymentRequest = new PerformPaymentRequest();
            try {
                performPaymentRequest = deserializeData(data);
                System.out.println("Listener2 received: " + performPaymentRequest + "!!!");
            } catch (Exception e) {
                System.out.println("Error deserializing PerformPaymentRequest: " + e.getMessage());
            }
            return addOrderToDB(performPaymentRequest);
        }

        private boolean addOrderToDB(PerformPaymentRequest performPaymentRequest) {
            OrderEntity order = new OrderEntity();
            Optional<UserEntity> userEntity = userRepository.findById(performPaymentRequest.getUserId());
            order.setUser(userEntity.get());
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setAddress(performPaymentRequest.getAddress());
            Optional<PaymentsEntity> paymentsEntity = paymentRepository.findByCardNum(performPaymentRequest.getCardNum());
            if (paymentsEntity.isPresent()) {
                order.setPaymentId(paymentsEntity.get().getId());
                order.setPaymentType(PaymentType.card);
            } else {
                order.setPaymentType(PaymentType.cash);
            }
            order.setCost(performPaymentRequest.getCost());
            orderRepository.save(order);
            return true;
        }

        private PerformPaymentRequest deserializeData(String data) {
            String[] parts = data.split(",");
            long userId = Long.parseLong(parts[0].split(":")[1].trim());
            String cardNum = parts[1].split(":")[1].trim().replaceAll("\"", "");
            String cardDate = parts[2].split(":")[1].trim().replaceAll("\"", "");
            String cardCVV = parts[3].split(":")[1].trim().replaceAll("\"", "");
            double cost = Double.parseDouble(parts[4].split(":")[1].trim());
            String address = parts[5].substring(parts[5].indexOf(":") + 1).trim().replaceAll("\"", "").replaceAll("}", "");

            return new PerformPaymentRequest(userId, cardNum, cardDate, cardCVV, cost, address);
        }
    }
}
