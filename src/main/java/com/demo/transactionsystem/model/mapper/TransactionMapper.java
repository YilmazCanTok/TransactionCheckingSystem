package com.demo.transactionsystem.model.mapper;

import com.demo.transactionsystem.model.dto.TransactionDTO;
import com.demo.transactionsystem.model.entity.Transaction;

public class TransactionMapper {
    public static TransactionDTO toDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransaction_date(transaction.getTransaction_date());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setBrand_model(transaction.getBrand_model());
        return transactionDTO;
    }

    public static Transaction toEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransaction_date(transactionDTO.getTransaction_date());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setBrand_model(transactionDTO.getBrand_model());
        return transaction;
    }
}
