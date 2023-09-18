package com.example.demo.Config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
public class JtaConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean
    public UserTransaction atomikosUserTransaction() throws SystemException {
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(10000);
        return userTransaction;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws SystemException {
        UserTransaction userTransaction = atomikosUserTransaction();
        TransactionManager transactionManager = atomikosTransactionManager();
        return new JtaTransactionManager(userTransaction, transactionManager);
    }
}
