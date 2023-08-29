package com.demo.transactionsystem.service;

import com.demo.transactionsystem.model.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransaction(Integer id);

    void addTransaction(Transaction transaction);

    Transaction updateTransaction(Integer id, Transaction transaction);

    boolean deleteTransaction(Integer id);
}
